package es.gobcan.istac.idxmanager.web.buscador.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.arte.acom.configuration.ConfigurationService;

import es.gobcan.istac.idxmanager.domain.modelo.IndexacionEnumDomain;
import es.gobcan.istac.idxmanager.web.buscador.mvc.domain.BusquedaWrapper;

@Component
@Scope(value = "singleton")
public class WebUtils {

    protected static Log         log                = LogFactory.getLog(WebUtils.class);

    @Autowired
    private ConfigurationService configurationService;

    private String               urlPublicacionJaxi = null;
    private String               urlPxJaxi          = null;
    private String               urlPxJaxiDescarga  = null;

    public String getUrlPublicacionJaxi() {
        if (StringUtils.isEmpty(urlPublicacionJaxi)) {
            StringBuilder buff = new StringBuilder(configurationService.getProperties().getProperty("istac.idxmanager.solr.urljaxi"));
            if (buff.charAt(buff.length() - 1) != '/') {
                buff.append("/");
            }
            buff.append("menu.do?uripub=");
            urlPublicacionJaxi = buff.toString();
        }
        return urlPublicacionJaxi;
    }

    public String getUrlPxJaxi() {
        if (StringUtils.isEmpty(urlPxJaxi)) {
            StringBuilder buff = new StringBuilder(configurationService.getProperties().getProperty("istac.idxmanager.solr.urljaxi"));
            if (buff.charAt(buff.length() - 1) != '/') {
                buff.append("/");
            }
            buff.append("tabla.do?uripx=");
            urlPxJaxi = buff.toString();
        }
        return urlPxJaxi;
    }

    public String getUrlPxJaxiDescarga() {
        if (StringUtils.isEmpty(urlPxJaxiDescarga)) {
            StringBuilder buff = new StringBuilder(configurationService.getProperties().getProperty("istac.idxmanager.solr.urljaxi"));
            if (buff.charAt(buff.length() - 1) != '/') {
                buff.append("/");
            }
            buff.append("descarga.do?uripx=");
            urlPxJaxiDescarga = buff.toString();
        }
        return urlPxJaxiDescarga;
    }

    public static String generarUrl(String urlBase, BusquedaWrapper busquedaWrapper) {
        URIBuilder urlBuilder = null;
        try {
            urlBuilder = new URIBuilder(urlBase);
            // userQuery
            if (!StringUtils.isEmpty(busquedaWrapper.getBusqueda().getUserQuery())) {
                urlBuilder.addParameter("userQuery", busquedaWrapper.getBusqueda().getUserQuery());
            }

            // Search Type
            if (!StringUtils.isEmpty(busquedaWrapper.getBusqueda().getSearchType())) {
                urlBuilder.addParameter("searchType", busquedaWrapper.getBusqueda().getSearchType());
            }

            // Filtro texto
            if (!StringUtils.isEmpty(busquedaWrapper.getBusqueda().getFiltroTexto())) {
                urlBuilder.addParameter("filtroTexto", busquedaWrapper.getBusqueda().getFiltroTexto());
            }

            // Filtro texto query
            if (!StringUtils.isEmpty(busquedaWrapper.getBusqueda().getFiltroTextoQuery())) {
                urlBuilder.addParameter("filtroTextoQuery", busquedaWrapper.getBusqueda().getFiltroTextoQuery());
            }

            // Filtro seccion
            if (!StringUtils.isEmpty(busquedaWrapper.getBusqueda().getFiltroSeccion())) {
                urlBuilder.addParameter("filtroSeccion", busquedaWrapper.getBusqueda().getFiltroSeccion());
            }

            // Filtro seccion area
            if (!StringUtils.isEmpty(busquedaWrapper.getBusqueda().getFiltroSeccionArea())) {
                urlBuilder.addParameter("filtroSeccionArea", busquedaWrapper.getBusqueda().getFiltroSeccionArea());
            }

            // Filtro seccion operacion
            if (!StringUtils.isEmpty(busquedaWrapper.getBusqueda().getFiltroSeccionOperacion())) {
                urlBuilder.addParameter("filtroSeccionOperacion", busquedaWrapper.getBusqueda().getFiltroSeccionOperacion());
            }

            // startResult
            if (!StringUtils.isEmpty(busquedaWrapper.getBusqueda().getStartResult())) {
                urlBuilder.addParameter("startResult", busquedaWrapper.getBusqueda().getStartResult());
            }

            // pestania
            if (!StringUtils.isEmpty(busquedaWrapper.getBusqueda().getPestania())) {
                urlBuilder.addParameter("pestania", busquedaWrapper.getBusqueda().getPestania());
            }

            // sort
            if (!StringUtils.isEmpty(busquedaWrapper.getBusqueda().getSort())) {
                urlBuilder.addParameter("sort", "1");
            }

            // ff_select
            if (!StringUtils.isEmpty(busquedaWrapper.getBusqueda().getFf_select())) {
                urlBuilder.addParameter("ff_select", busquedaWrapper.getBusqueda().getFf_select());
            }

            generateUrlParamsForFacets(busquedaWrapper, urlBuilder);

            // formato
            if (!StringUtils.isEmpty(busquedaWrapper.getBusqueda().getFormato())) {
                urlBuilder.addParameter(IndexacionEnumDomain.FACET_FORMATO_FF.getCampo(), busquedaWrapper.getBusqueda().getFormato());
            }

        } catch (Exception e) {
            log.error("WebUtils::generarUrl: " + e);
            return "#";
        }

        return urlBuilder.toString();
    }

    public static String getFullURL(HttpServletRequest request, boolean includeQueryString) {
        StringBuffer requestURL = request.getRequestURL();
        int indexOfWEBINF = requestURL.indexOf("WEB-INF");
        if (indexOfWEBINF != -1) {
            requestURL = new StringBuffer(requestURL.substring(0, indexOfWEBINF));
        }

        if (includeQueryString) {
            String queryString = request.getQueryString();
            if (queryString != null) {
                requestURL.append('?').append(queryString);
            }
        }
        return requestURL.toString();
    }

    /**************************************************************************
     * PRIVADOS
     **************************************************************************/

    private static void generateUrlParamsForFacets(BusquedaWrapper busquedaWrapper, URIBuilder urlBuilder) {
        // Coverage Spatial
        if (!StringUtils.isEmpty(busquedaWrapper.getBusqueda().getCvgSCodFF())) {
            urlBuilder.addParameter("cvgSCodFF", busquedaWrapper.getBusqueda().getCvgSCodFF());
        }

        // Coverage Temporal
        if (!StringUtils.isEmpty(busquedaWrapper.getBusqueda().getCvgTCodFF())) {
            urlBuilder.addParameter("cvgTCodFF", busquedaWrapper.getBusqueda().getCvgTCodFF());
        }

        // Survey Code
        if (!StringUtils.isEmpty(busquedaWrapper.getBusqueda().getSvyCodFF())) {
            urlBuilder.addParameter("svyCodFF", busquedaWrapper.getBusqueda().getSvyCodFF());
        }

        // Subject Code
        if (!StringUtils.isEmpty(busquedaWrapper.getBusqueda().getSubCodFF())) {
            urlBuilder.addParameter("subCodFF", busquedaWrapper.getBusqueda().getSubCodFF());
        }
    }

}
