package es.gobcan.istac.idxmanager.web.buscador.mvc.busqueda;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.gobcan.istac.idxmanager.core.service.BusquedaService;
import es.gobcan.istac.idxmanager.domain.modelo.IndexacionEnumDomain;
import es.gobcan.istac.idxmanager.domain.mvc.Busqueda;
import es.gobcan.istac.idxmanager.domain.mvc.enumerated.FiltroSeccionEnum;
import es.gobcan.istac.idxmanager.domain.mvc.enumerated.FiltroTextoEnum;
import es.gobcan.istac.idxmanager.web.buscador.mvc.domain.FacetFieldWeb;
import es.gobcan.istac.idxmanager.web.buscador.mvc.domain.FacetValueWeb;
import es.gobcan.istac.idxmanager.web.buscador.util.PaginacionUtils;
import es.gobcan.istac.idxmanager.web.buscador.util.WebUtils;
import es.gobcan.istac.idxmanager.web.buscador.validation.BusquedaValidator;

@Controller
public class BusquedaController {

    protected Log log = LogFactory.getLog(BusquedaController.class);

    private static final int RESULTS_BY_PAGE = 10;
    private static final int NUMER_OF_VISIBLE_PAGES_IN_PAGINATION = 5;

    @Autowired
    private WebUtils webUtils;

    @Autowired
    private BusquedaService busquedaService;

    @Autowired
    private FiltrosComponent filtrosComponent;

    @ModelAttribute("filtroTextoMap")
    public Map<String, String> getFiltroTextoMap() {

        Map<String, String> filtro = new LinkedHashMap<String, String>();
        filtro.put(FiltroTextoEnum.ALGUNA_DE_LAS_PALABRAS.getId(), FiltroTextoEnum.ALGUNA_DE_LAS_PALABRAS.getField());
        filtro.put(FiltroTextoEnum.FRASE_EXACTA.getId(), FiltroTextoEnum.FRASE_EXACTA.getField());
        filtro.put(FiltroTextoEnum.TODAS_LAS_PALABRAS.getId(), FiltroTextoEnum.TODAS_LAS_PALABRAS.getField());

        return filtro;
    }

    @ModelAttribute("filtroSeccionMap")
    public Map<String, String> getFiltroSeccionMap() {

        Map<String, String> filtro = new LinkedHashMap<String, String>();
        filtro.put(FiltroSeccionEnum.AREA_TEMATICA.getId(), FiltroSeccionEnum.AREA_TEMATICA.getField());
        filtro.put(FiltroSeccionEnum.OPERACION_ESTADISTICA.getId(), FiltroSeccionEnum.OPERACION_ESTADISTICA.getField());

        return filtro;
    }

    @ModelAttribute("filtroSeccionAreaMap")
    public Map<String, String> getFiltroSeccionAreaMap() {
        return filtrosComponent.getFiltroSeccionAreaMap();
    }

    @ModelAttribute("filtroSeccionOperacionMap")
    public Map<String, String> getFiltroSeccionOperacionMap() {
        return filtrosComponent.getFiltroSeccionOperacionMap();
    }

    /**
     * VISTA INICIAL CUANDO SE ACCEDE A LA APLICACION
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getCreateForm(Model model) {
        model.addAttribute(new Busqueda());
        return "/busqueda";
    }

    /**
     * PROCESADO DEL FORMULARIO DE BUSQUEDA
     *
     * @param busqueda
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(value = {"/busca", "/busca/**"}, method = RequestMethod.GET)
    public String read(@Valid Busqueda busqueda, BindingResult result, Model model) {
        BusquedaValidator.validate(busqueda, result);

        // Si errores al validar el formulario
        if (result.hasErrors()) {
            return "/busqueda";
        }

        // Se realizan dos busquedas, la primera la busqueda normal, despues los sugeridos
        QueryResponse queryResponse;

        SolrDocumentList solrDocumentList = null;
        try {
            queryResponse = busquedaService.ejecutarQuery(busqueda, RESULTS_BY_PAGE);
            if (queryResponse != null) {
                solrDocumentList = queryResponse.getResults();
            }
        } catch (Exception e) {
            log.warn("Error procesando formulario de busqueda: ", e);
            result.rejectValue("userQuery", "error.interno");
            return "/busqueda";
        }

        // Búsqueda de sugeridos
        SolrDocumentList solrSuggestedDocumentList = null;
        if (solrDocumentList == null || solrDocumentList.isEmpty()) {
            result.rejectValue("userQuery", "error.noencontrado", "No se han encontrado resultados");
        } else {
            try {
                solrSuggestedDocumentList = busquedaService.ejecutarQueryForSuggested(busqueda);
            } catch (Exception e) {
                log.warn("Error procesando formulario de busqueda: ", e);
                result.rejectValue("userQuery", "error.interno");
                return "/busqueda";
            }
        }

        processBindingModel(solrDocumentList, solrSuggestedDocumentList, queryResponse, model, busqueda);
        return "busqueda/lista";
    }

    private void processBindingModel(SolrDocumentList solrDocumentList, SolrDocumentList solrSuggestedDocumentList, QueryResponse queryResponse, Model model, Busqueda busqueda) {
        // Datos
        model.addAttribute("documentList", solrDocumentList);
        model.addAttribute("suggestedDocumentList", solrSuggestedDocumentList);
        model.addAttribute("facetedList", processFacetResponseFromSolr(queryResponse, false));
        model.addAttribute("highlightingList", queryResponse.getHighlighting());

        // Útiles para paginación
        long[] paginacionInfo = PaginacionUtils.obtenerInfoPaginacion(((SolrDocumentList) queryResponse.getResponse().get("response")).getStart(),
                ((SolrDocumentList) queryResponse.getResponse().get("response")).getNumFound(), RESULTS_BY_PAGE, NUMER_OF_VISIBLE_PAGES_IN_PAGINATION);
        model.addAttribute("startPage", paginacionInfo[0]);
        model.addAttribute("currentPage", paginacionInfo[1]);
        model.addAttribute("finalPage", paginacionInfo[2]);
        model.addAttribute("resultByPage", RESULTS_BY_PAGE);
        model.addAttribute("numResult", ((SolrDocumentList) queryResponse.getResponse().get("response")).getNumFound());

        // Útiles para construcción de enlaces a Jaxi
        model.addAttribute("urlPublicacionJaxi", webUtils.getUrlPublicacionJaxi());
        model.addAttribute("urlPxJaxi", webUtils.getUrlPxJaxi());
        model.addAttribute("urlPxJaxiDescarga", webUtils.getUrlPxJaxiDescarga());

        // Texto a mostrar en la nube de Facets Seleccionados para los Facet que tienen mapping por código
        model.addAttribute("subAreaFilterSelectTitle", filtrosComponent.getFiltroSeccionAreaMap().get(busqueda.getSubCodFF()));
        model.addAttribute("surveyFilterSelectTitle", filtrosComponent.getFiltroSeccionOperacionMap().get(busqueda.getSvyCodFF()));
        // model.addAttribute("cvgTFilterSelectTitle", filtrosComponent.getFiltroCoverageTemporalMap().get(busqueda.getCvgTCodFF()));
        // model.addAttribute("cvgSFilterSelectTitle", filtrosComponent.getFiltroCoverageSpatialMap().get(busqueda.getCvgSCodFF()));
    }

    private List<FacetFieldWeb> processFacetResponseFromSolr(QueryResponse queryResponse, boolean sort) {
        List<FacetFieldWeb> result = new LinkedList<FacetFieldWeb>();
        for (FacetField facetField : queryResponse.getFacetFields()) {
            FacetFieldWeb facetFieldWeb = new FacetFieldWeb(facetField.getName());

            if (IndexacionEnumDomain.FACET_SUBJECT_KEYVALUE_FF.getCampo().equals(facetField.getName()) || IndexacionEnumDomain.FACET_SURVEY_KEYVALUE_FF.getCampo().equals(facetField.getName())
                    || IndexacionEnumDomain.FACET_COVERAGE_TEMPORAL_KEYVALUE_FF.getCampo().equals(facetField.getName())
                    || IndexacionEnumDomain.FACET_COVERAGE_SPATIAL_KEYVALUE_FF.getCampo().equals(facetField.getName())) {
                for (FacetField.Count count : facetField.getValues()) {
                    String[] facetKeyValue = FiltrosComponent.getFacetKeyValue(count.getName());
                    facetFieldWeb.getValues().add(new FacetValueWeb(facetFieldWeb, facetKeyValue[0], facetKeyValue[1], count.getCount()));
                }
            } else {
                facetFieldWeb.addSimpleFacetCountFromSolr(facetField);
            }

            if (sort) {
                if (IndexacionEnumDomain.FACET_COVERAGE_TEMPORAL_KEYVALUE_FF.getCampo().equals(facetField.getName())
                        || IndexacionEnumDomain.FACET_COVERAGE_SPATIAL_KEYVALUE_FF.getCampo().equals(facetField.getName())) {
                    facetFieldWeb.sortValues(false); // Sort by key
                } else {
                    facetFieldWeb.sortValues(true); // Sort by name
                }
            }
            result.add(facetFieldWeb);
        }

        return result;
    }

    /**
     * Peticion "mas" para las facets.
     *
     * @param busqueda
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(value = {"/facets", "/facets/**"})
    public String facetsHandler(@Valid Busqueda busqueda, BindingResult result, Model model) {
        // Si errores al validar el formulario
        if (result.hasErrors()) {
            return "/busqueda";
        }

        // Busqueda
        SolrDocumentList solrDocumentList = null;
        QueryResponse queryResponse = null;
        try {
            queryResponse = busquedaService.ejecutarQuery(busqueda, RESULTS_BY_PAGE);
            if (queryResponse != null) {
                solrDocumentList = queryResponse.getResults();
            }
        } catch (Exception e) {
            log.warn("Error procesando facets handlers: ", e);
            result.rejectValue("userQuery", "error.interno");
            return "/busqueda";
        }

        if (solrDocumentList == null || solrDocumentList.isEmpty()) {
            result.rejectValue("userQuery", "error.noencontrado", "No se han encontrado resultados");
        } else {
            List<FacetField> facetFieldList = queryResponse.getFacetFields();
            int xiter = facetFieldList.size() / 3;
            int xiterAnt = xiter - 1;
            if (xiterAnt < 0) {
                xiterAnt = 0;
            }
            model.addAttribute("facetedList", processFacetResponseFromSolr(queryResponse, true));
            model.addAttribute("endOne", xiterAnt + (facetFieldList.size() & 3));
            model.addAttribute("beginTwo", xiter + (facetFieldList.size() & 3));
            model.addAttribute("endTwo", xiter + xiterAnt + (facetFieldList.size() & 3));
            model.addAttribute("beginThree", xiter * 2 + (facetFieldList.size() & 3));
            return "busqueda/facetedsList";
        }

        return "/busqueda";
    }
}
