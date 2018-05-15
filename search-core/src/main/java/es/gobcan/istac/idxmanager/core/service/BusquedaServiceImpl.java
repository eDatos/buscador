package es.gobcan.istac.idxmanager.core.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gobcan.istac.idxmanager.core.exception.ServiceExcepcion;
import es.gobcan.istac.idxmanager.core.exception.ServiceExcepcionTipo;
import es.gobcan.istac.idxmanager.core.util.ServiceUtils;
import es.gobcan.istac.idxmanager.domain.dom.TypeNMDomain;
import es.gobcan.istac.idxmanager.domain.modelo.IndexacionEnumDomain;
import es.gobcan.istac.idxmanager.domain.mvc.Busqueda;
import es.gobcan.istac.idxmanager.domain.mvc.enumerated.FiltroSeccionEnum;
import es.gobcan.istac.idxmanager.domain.mvc.enumerated.FiltroTextoEnum;

@Service
public class BusquedaServiceImpl implements BusquedaService {

    protected Log               log                   = LogFactory.getLog(BusquedaService.class);

    private static final String SELECT_QT             = "/select";
    private static final String BROWSE_QT             = "/browse";
    private static final String SPONSORED_QT          = "/sponsored";

    private static final int    MAX_SUGGESTED_RESULTS = 4;

    @Autowired
    private SolrService         solr                  = null;

    @Autowired
    private AuditoriaService    auditoriaService;

    @Override
    public QueryResponse ejecutarQuery(Busqueda busqueda, int resultByPage) throws ServiceExcepcion {
        try {
            SolrQuery solrQuery = new SolrQuery();

            // OPCIONES
            // Busqueda con Facet
            solrQuery.setFacet(true);
            // Num minimo de cuentas para considerar Facet
            solrQuery.setFacetMinCount(1);

            // 1er resultado
            solrQuery.setStart(Integer.valueOf(busqueda.getStartResult()) * resultByPage);
            // numero de resultados que nos traemos
            solrQuery.setRows(resultByPage);

            // QUERY
            processOptions(busqueda, solrQuery);

            // Ordenacion por fecha
            if (StringUtils.isNotEmpty(busqueda.getSort())) {
                solrQuery.setSort(IndexacionEnumDomain.NM_LAST_UPDATE.getCampo(), SolrQuery.ORDER.desc);
            }

            incluirFacets(busqueda, solrQuery);
            setFiltroTipoRecurso(busqueda, solrQuery);
            setFiltrosParaFacets(busqueda, solrQuery);

            // Maximo tiempo para efectuar la consulta 10000 milisegundos
            solrQuery.setTimeAllowed(10000);

            auditoriaService.auditar(busqueda);
            return solr.ejecutarQuery(solrQuery);
        } catch (Exception e) {
            log.error("BusquedaServiceImpl::ejecutarQuery: " + e);
            new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_SEARCH_QUERY_PARSER);
        }

        return null;
    }

    private void incluirFacets(Busqueda busqueda, SolrQuery solrQuery) {
        // FACETS
        if (StringUtils.isNotEmpty(busqueda.getFf_select())) {
            // Solo calcular el Facet pedido
            solrQuery.addFacetField(busqueda.getFf_select());
        } else {
            // Calcular todos los Facets
            solrQuery.addFacetField(IndexacionEnumDomain.FACET_SUBJECT_KEYVALUE_FF.getCampo());
            solrQuery.addFacetField(IndexacionEnumDomain.FACET_SURVEY_KEYVALUE_FF.getCampo());
            // solrQuery.addFacetField(IndexacionEnumDomain.FACET_COVERAGE_TEMPORAL_KEYVALUE_FF.getCampo());
            // solrQuery.addFacetField(IndexacionEnumDomain.FACET_COVERAGE_SPATIAL_KEYVALUE_FF.getCampo());
            solrQuery.addFacetField(IndexacionEnumDomain.FACET_FORMATO_FF.getCampo());
        }
    }

    private void setFiltroTipoRecurso(Busqueda busqueda, SolrQuery solrQuery) {
        if (StringUtils.isNotEmpty(busqueda.getPestania())) {
            if (busqueda.getPestania().equals(TypeNMDomain.DATASET_DSC.getSiglas())) {
                solrQuery.addFilterQuery(ServiceUtils.getFilterQueryOrString(IndexacionEnumDomain.NM_TYPE.getCampo(), busqueda.getPestania()));
            } else if (busqueda.getPestania().equals(TypeNMDomain.COLLECTION_DSP.getSiglas())) {
                solrQuery.addFilterQuery(ServiceUtils.getFilterQueryString(IndexacionEnumDomain.NM_TYPE.getCampo(), busqueda.getPestania()));
            }
        }
    }

    private void setFiltrosParaFacets(Busqueda busqueda, SolrQuery solrQuery) {
        if (StringUtils.isNotEmpty(busqueda.getSubCodFF())) {
            solrQuery.addFilterQuery(ServiceUtils.getFilterQueryString(IndexacionEnumDomain.NM_SUBJECT_CODES.getCampo(), busqueda.getSubCodFF()));
        }
        if (StringUtils.isNotEmpty(busqueda.getSvyCodFF())) {
            solrQuery.addFilterQuery(ServiceUtils.getFilterQueryString(IndexacionEnumDomain.NM_SURVEY_CODE.getCampo(), busqueda.getSvyCodFF()));
        }
        if (StringUtils.isNotEmpty(busqueda.getCvgSCodFF())) {
            solrQuery.addFilterQuery(ServiceUtils.getFilterQueryString(IndexacionEnumDomain.NM_COVERAGE_SPATIAL_CODES.getCampo(), busqueda.getCvgSCodFF()));
        }
        if (StringUtils.isNotEmpty(busqueda.getCvgTCodFF())) {
            solrQuery.addFilterQuery(ServiceUtils.getFilterQueryString(IndexacionEnumDomain.NM_COVERAGE_TEMPORAL_CODES.getCampo(), busqueda.getCvgTCodFF()));
        }
        if (StringUtils.isNotEmpty(busqueda.getFormato())) {
            solrQuery.addFilterQuery(ServiceUtils.getFilterQueryString(IndexacionEnumDomain.FACET_FORMATO_FF.getCampo(), busqueda.getFormato()));
        }
    }

    @Override
    public SolrDocumentList ejecutarQueryForSuggested(Busqueda busqueda) throws ServiceExcepcion {
        try {
            SolrQuery solrQuery = new SolrQuery();

            // // Query avanzado
            processOptions(busqueda, solrQuery);

            // Debemos especificar que usaremos el request Handler especial para recomendaciones
            solrQuery.setRequestHandler(SPONSORED_QT);

            // Maximo tiempo para efectuar la consulta 10000 milisegundos
            solrQuery.setTimeAllowed(10000);
            QueryResponse response = solr.ejecutarQuery(solrQuery);
            if (response == null) {
                return null;
            }
            // Transformamos el SolrDocumentList de Patrocinados a nuestro SolrDocumentList
            return processPriority(response);
        } catch (Exception e) {
            log.error("BusquedaServiceImpl::ejecutarQuery: " + e);
            new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_SEARCH_QUERY_PARSER);
        }

        return null;
    }
    private SolrDocumentList processPriority(QueryResponse response) {
        SolrDocumentList docList = response.getResults();
        Map<String, Integer> processed = new HashMap<String, Integer>();
        Map<String, SolrDocument> docsDef = new HashMap<String, SolrDocument>();

        // Procesamos los enlaces y contamos cuantas veces se repiten
        procesarEnlacesContandoRepeticiones(docList, processed, docsDef);

        return ordenarResultados(docList, processed, docsDef);
    }

    private SolrDocumentList ordenarResultados(SolrDocumentList docList, Map<String, Integer> processed, Map<String, SolrDocument> docsDef) {
        // Damos prioridad en la lista a los que mas veces aparecen
        // Guardamos primero los que han aparecido n veces ... hasta 2 veces
        SolrDocumentList priority = new SolrDocumentList();
        int maxTimes = docList.size();
        for (int i = maxTimes; i > 1 && priority.size() < MAX_SUGGESTED_RESULTS; i--) {
            for (Map.Entry<String, Integer> entry : processed.entrySet()) {
                int times = entry.getValue();
                if (times == i && priority.size() < MAX_SUGGESTED_RESULTS) {
                    priority.add(docsDef.get(entry.getKey()));
                }
            }
        }

        if (priority.size() == MAX_SUGGESTED_RESULTS) {
            return priority;
        }

        // Para los que solo aparecen una vez se sigue el orden marcado por solr
        for (SolrDocument sDoc : docList) {
            int curLink = 0;
            while (sDoc.containsKey(IndexacionEnumDomain.CUS_TITLE_PREFIX.getCampo() + curLink + "_s")) {
                String url = (String) sDoc.get(IndexacionEnumDomain.CUS_URL_PREFIX.getCampo() + curLink + "_s");
                if (processed.get(url) == 1 && priority.size() < MAX_SUGGESTED_RESULTS) {
                    priority.add(docsDef.get(url));
                }
                curLink++;
            }
        }

        return priority;
    }

    private void procesarEnlacesContandoRepeticiones(SolrDocumentList docList, Map<String, Integer> processed, Map<String, SolrDocument> docsDef) {
        for (int i = 0; i < docList.size(); i++) {
            SolrDocument solrDoc = docList.get(i);
            int curLink = 0;
            while (solrDoc.containsKey(IndexacionEnumDomain.CUS_TITLE_PREFIX.getCampo() + curLink + "_s")) {
                String url = (String) solrDoc.get(IndexacionEnumDomain.CUS_URL_PREFIX.getCampo() + curLink + "_s");
                if (processed.get(url) == null) {
                    String title = (String) solrDoc.get(IndexacionEnumDomain.CUS_TITLE_PREFIX.getCampo() + curLink + "_s");
                    String description = (String) solrDoc.get(IndexacionEnumDomain.CUS_DESC_PREFIX.getCampo() + curLink + "_s");
                    SolrDocument newDoc = new SolrDocument();
                    newDoc.addField(IndexacionEnumDomain.CUS_TITLE_PREFIX.getCampo(), title);
                    newDoc.addField(IndexacionEnumDomain.CUS_DESC_PREFIX.getCampo(), description);
                    newDoc.addField(IndexacionEnumDomain.CUS_URL_PREFIX.getCampo(), url);
                    newDoc.addField(IndexacionEnumDomain.ID.getCampo(), url);
                    docsDef.put(url, newDoc);
                    processed.put(url, 1);
                } else {
                    int old = processed.get(url);
                    processed.put(url, old + 1);
                }
                curLink++;
            }
        }
    }

    @Override
    public QueryResponse ejecutarInitialFacetQuery() throws ServiceExcepcion {
        try {
            SolrQuery solrQuery = new SolrQuery();

            // Default handler
            solrQuery.setRequestHandler(SELECT_QT);

            // OPCIONES
            solrQuery.setFacet(true); // Busqueda con Facet
            solrQuery.setFacetMinCount(0); // N. minimo de cuentas para considerar Facet
            solrQuery.setStart(0); // 1er resultado
            solrQuery.setRows(0); // El query es vacio, no queremos ningún resultado

            // FACETS
            solrQuery.addFacetField(IndexacionEnumDomain.FACET_SUBJECT_KEYVALUE_FF.getCampo());
            solrQuery.addFacetField(IndexacionEnumDomain.FACET_SURVEY_KEYVALUE_FF.getCampo());
            // solrQuery.addFacetField(IndexacionEnumDomain.FACET_COVERAGE_TEMPORAL_KEYVALUE_FF.getCampo());
            // solrQuery.addFacetField(IndexacionEnumDomain.FACET_COVERAGE_SPATIAL_KEYVALUE_FF.getCampo());
            solrQuery.setFacetLimit(-1); // Todos los facet, por defecto está limitado a 100

            solrQuery.setQuery("*:*");

            return solr.ejecutarQuery(solrQuery);
        } catch (Exception e) {
            log.error("BusquedaServiceImpl::ejecutarQuery: " + e);
            new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_SEARCH_QUERY_PARSER);
        }

        return null;
    }

    /**************************************************************************
     * PRIVADOS
     **************************************************************************/
    private void processOptions(Busqueda busqueda, SolrQuery solrQuery) {
        StringBuilder str = new StringBuilder(128);
        boolean isAvanzado = false;

        // Detecta búsqueda avanzada: Select o búsqueda normal Browse
        if (!StringUtils.isEmpty(busqueda.getSearchType())) {
            isAvanzado = true;
            busqueda.setUserQuery(busqueda.getFiltroTextoQuery());
        } else {
            // Limpieza de campos avanzados
            busqueda.setFiltroTexto(null);
            busqueda.setFiltroTextoQuery(null);
            busqueda.setFiltroSeccion(null);
            busqueda.setFiltroSeccionArea(null);
            busqueda.setFiltroSeccionOperacion(null);
        }

        if (isAvanzado) {
            solrQuery.setRequestHandler(SELECT_QT);

            // Filtros de texto
            if (!StringUtils.isEmpty(busqueda.getFiltroTextoQuery())) {
                FiltroTextoEnum filtroTextoQuery = FiltroTextoEnum.fromId(busqueda.getFiltroTexto()); // Tipo de filtro
                if (FiltroTextoEnum.ALGUNA_DE_LAS_PALABRAS.equals(filtroTextoQuery)) {
                    solrQuery.setParam("q.op", "OR");
                    str.append("text_all").append(":").append(busqueda.getFiltroTextoQuery());
                } else if (FiltroTextoEnum.FRASE_EXACTA.equals(filtroTextoQuery)) {
                    // Con la frase exacta
                    str.append("text_all").append(":").append("\"").append(busqueda.getFiltroTextoQuery()).append("\"");
                } else if (FiltroTextoEnum.TODAS_LAS_PALABRAS.equals(filtroTextoQuery)) {
                    solrQuery.setParam("q.op", "AND");
                    str.append("text_all").append(":").append(busqueda.getFiltroTextoQuery());
                } else {
                    busqueda.setFiltroTexto(null);
                }
            }

            // Filtros de sección
            if (!StringUtils.isEmpty(busqueda.getFiltroSeccion())) {
                FiltroSeccionEnum filtroSeccionQuery = FiltroSeccionEnum.fromId(busqueda.getFiltroSeccion());
                if (FiltroSeccionEnum.AREA_TEMATICA.equals(filtroSeccionQuery) && StringUtils.isNotBlank(busqueda.getFiltroSeccionArea())) {
                    str.append(" +").append(IndexacionEnumDomain.NM_SUBJECT_CODES.getCampo()).append(":").append(busqueda.getFiltroSeccionArea());
                } else if (FiltroSeccionEnum.OPERACION_ESTADISTICA.equals(filtroSeccionQuery) && StringUtils.isNotBlank(busqueda.getFiltroSeccionOperacion())) {
                    str.append(" +").append(IndexacionEnumDomain.NM_SURVEY_CODE.getCampo()).append(":").append(busqueda.getFiltroSeccionOperacion());
                } else {
                    busqueda.setFiltroSeccion(null);
                }
            }

        } else {
            solrQuery.setRequestHandler(BROWSE_QT);
            str.append(busqueda.getUserQuery());
        }

        solrQuery.setQuery(str.toString());
    }
}
