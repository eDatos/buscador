package es.gobcan.istac.idxmanager.web.buscador.mvc.domain;

import es.gobcan.istac.idxmanager.domain.modelo.IndexacionEnumDomain;
import es.gobcan.istac.idxmanager.domain.mvc.Busqueda;

public class BusquedaWrapper {

    private FacetValueWeb facetValueWeb;
    private Busqueda busqueda;

    public BusquedaWrapper(Busqueda previousSearch) {
        busqueda = new Busqueda(previousSearch);
    }

    public FacetValueWeb getFacetValueWeb() {
        return facetValueWeb;
    }

    public void setFacetValueWeb(FacetValueWeb facetValueWeb) {
        this.facetValueWeb = facetValueWeb;
        applyFacetValue();
    }

    public Busqueda getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(Busqueda busqueda) {
        this.busqueda = busqueda;
    }

    public void applySelectFacetForDetails(FacetFieldWeb facetFieldWeb) {
        if (facetFieldWeb != null) {
            getBusqueda().setFf_select(facetFieldWeb.getName());
        } else {
            getBusqueda().setFf_select(null);
        }
    }

    private void applyFacetValue() {
        if (facetValueWeb == null) {
            return;
        }

        IndexacionEnumDomain indexedFieldEnum = IndexacionEnumDomain.get(facetValueWeb.getParentField().getName());

        if (IndexacionEnumDomain.FACET_COVERAGE_SPATIAL_KEYVALUE_FF.equals(indexedFieldEnum)) {
            getBusqueda().setCvgSCodFF(facetValueWeb.getKey());
        } else if (IndexacionEnumDomain.FACET_COVERAGE_TEMPORAL_KEYVALUE_FF.equals(indexedFieldEnum)) {
            getBusqueda().setCvgTCodFF(facetValueWeb.getKey());
        } else if (IndexacionEnumDomain.FACET_FORMATO_FF.equals(indexedFieldEnum)) {
            getBusqueda().setFormato(facetValueWeb.getName());
        } else if (IndexacionEnumDomain.FACET_SUBJECT_KEYVALUE_FF.equals(indexedFieldEnum)) {
            getBusqueda().setSubCodFF(facetValueWeb.getKey());
        } else if (IndexacionEnumDomain.FACET_SURVEY_KEYVALUE_FF.equals(indexedFieldEnum)) {
            getBusqueda().setSvyCodFF(facetValueWeb.getKey());
        }
    }
}
