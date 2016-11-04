package es.gobcan.istac.idxmanager.web.buscador.mvc.busqueda;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibm.icu.text.Collator;
import com.ibm.icu.util.ULocale;

import es.gobcan.istac.idxmanager.core.service.BusquedaService;
import es.gobcan.istac.idxmanager.domain.modelo.IndexacionEnumDomain;

@Component
public class FiltrosComponent {

    protected Log log = LogFactory.getLog(FiltrosComponent.class);

    @Autowired
    private BusquedaService busquedaService;

    private Map<String, String> filtroSeccionAreaMap = null;
    private Map<String, String> filtroSeccionOperacionMap = null;

    // TODO Mejora: Poner este componente como un servicio y controlar la cache cuando se indexan cosas en solr en vez de cada cierto intervalo.
    private DateTime lastRefresh = null;

    public Map<String, String> getFiltroSeccionAreaMap() {
        if (filtroSeccionAreaMap == null || reload()) {
            facetInicialesHandler();
        }
        return filtroSeccionAreaMap;
    }

    public Map<String, String> getFiltroSeccionOperacionMap() {
        if (filtroSeccionOperacionMap == null || reload()) {
            facetInicialesHandler();
        }
        return filtroSeccionOperacionMap;
    }

    /**
     * Si entre la última petición y la actual han transcurrido más de 5 minutos se recarga el bean de filtros
     *
     * @return
     */
    private boolean reload() {
        Duration dur = new Duration(lastRefresh, DateTime.now());
        return dur.getStandardMinutes() > 5;
    }

    private void facetInicialesHandler() {
        lastRefresh = DateTime.now();
        QueryResponse queryResponse = null;
        try {
            queryResponse = busquedaService.ejecutarInitialFacetQuery();
            if (queryResponse != null) {
                filtroSeccionAreaMap = createFiltroSeccionMap(queryResponse.getFacetField(IndexacionEnumDomain.FACET_SUBJECT_KEYVALUE_FF.getCampo()));
                filtroSeccionOperacionMap = createFiltroSeccionMap(queryResponse.getFacetField(IndexacionEnumDomain.FACET_SURVEY_KEYVALUE_FF.getCampo()));
            }
        } catch (Exception e) {
            log.warn("Error Obteniendo Facets Iniciales: ", e);
        }
    }

    private static Map<String, String> createFiltroSeccionMap(FacetField facetField) {
        Map<String, String> filtro = new LinkedHashMap<String, String>();

        // Sort by Facet Value
        Collections.sort(facetField.getValues(), new KeyValueFacetComparator());

        // Build linkedHashMap
        for (Count count : facetField.getValues()) {
            String nameFacet = count.getName();
            String key = getFacetKeyValue(nameFacet)[0];
            String value = getFacetKeyValue(nameFacet)[1];
            if (key != null && value != null) {
                filtro.put(key, value);
            }
        }

        return filtro;
    }

    /**
     * Calculate Key-Value Pair from composite Facet
     *
     * @param name
     * @return String[0] Facet key, String[1] Facet Value
     */
    public static String[] getFacetKeyValue(String name) {
        int lastIndexOf = StringUtils.indexOf(name, "#");
        if (lastIndexOf != -1) {
            String[] result = new String[2];
            result[0] = name.substring(0, lastIndexOf);
            result[1] = name.substring(lastIndexOf + 1);
            return result;
        }
        return null;
    }

    private static class KeyValueFacetComparator implements Comparator<Count> {

        private Collator collator;

        public KeyValueFacetComparator() {
            Collator collator = Collator.getInstance(ULocale.forLanguageTag("es_ES"));
            collator.setStrength(Collator.PRIMARY);
            this.collator = collator;
        }

        @Override
        public int compare(Count countA, Count countB) {
            String valueA = getFacetKeyValue(countA.getName())[1];
            String valueB = getFacetKeyValue(countB.getName())[1];
            return collator.compare(valueA.trim(), valueB.trim());
        }

    }
}
