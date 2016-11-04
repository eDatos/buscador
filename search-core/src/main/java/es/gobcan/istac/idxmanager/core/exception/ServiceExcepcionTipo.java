package es.gobcan.istac.idxmanager.core.exception;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import es.gobcan.istac.idxmanager.domain.util.LocaleUtil;

public enum ServiceExcepcionTipo {
    //@formatter:off
    SERVICE_GENERAL,
    // SERVICE_ABRIR_SESION,
    // SERVICE_PARSEAR_RECURSO,
    // SERVICE_TICKET_ALFRESCO,
    // SERVICE_CONTENIDO_NUCLEO_ALFRESCO,
    // SERVICE_NODO_NUCLEO_METADATOS_ALFRESCO,
    // SERVICE_NODE_CONTENT_ALFRESCO,
    // SERVICE_RECURSOS_PUBLICADOS_ALFRESCO,
    // SERVICE_REINDEXACION,
    // SERVICE_REINDEXACION_INPROGRESS,
    // SERVICE_REINDEXACION_RECOMENDADOS,
    // SERVICE_REINDEXACION_WEB,
//    SERVICE_CRAWLER,
    SERVICE_SEARCH_QUERY_PARSER;
    //@formatter:on

    private static final Map<ServiceExcepcionTipo, String> MENSAJE_MAP = new HashMap<ServiceExcepcionTipo, String>();

    static {
        MENSAJE_MAP.put(ServiceExcepcionTipo.SERVICE_GENERAL, "excepcion.service.general");
        // MENSAJE_MAP.put(ServiceExcepcionTipo.SERVICE_ABRIR_SESION, "excepcion.service.abrir_sesion");
        // MENSAJE_MAP.put(ServiceExcepcionTipo.SERVICE_PARSEAR_RECURSO, "excepcion.service.parsear_recurso");
        // MENSAJE_MAP.put(ServiceExcepcionTipo.SERVICE_TICKET_ALFRESCO, "excepcion.service.ticket_alfresco");
        // MENSAJE_MAP.put(ServiceExcepcionTipo.SERVICE_CONTENIDO_NUCLEO_ALFRESCO, "excepcion.service.contenido_nucleo_alfresco");
        // MENSAJE_MAP.put(ServiceExcepcionTipo.SERVICE_NODO_NUCLEO_METADATOS_ALFRESCO, "excepcion.service.nodo_metadatos_alfresco");
        // MENSAJE_MAP.put(ServiceExcepcionTipo.SERVICE_NODE_CONTENT_ALFRESCO, "excepcion.service.node_content_alfresco");
        // MENSAJE_MAP.put(ServiceExcepcionTipo.SERVICE_RECURSOS_PUBLICADOS_ALFRESCO, "excepcion.service.recursos_publicados_alfresco");
        // MENSAJE_MAP.put(ServiceExcepcionTipo.SERVICE_REINDEXACION, "excepcion.service.reindexacion");
        // MENSAJE_MAP.put(ServiceExcepcionTipo.SERVICE_REINDEXACION_INPROGRESS, "excepcion.service.reindexacion.inprogress");
        // MENSAJE_MAP.put(ServiceExcepcionTipo.SERVICE_REINDEXACION_RECOMENDADOS, "excepcion.service.reindexacion.recomendados");
        // MENSAJE_MAP.put(ServiceExcepcionTipo.SERVICE_REINDEXACION_WEB, "excepcion.service.reindexacion.web");
        // MENSAJE_MAP.put(ServiceExcepcionTipo.SERVICE_CRAWLER, "excepcion.service.indexacion.crawler");
        MENSAJE_MAP.put(ServiceExcepcionTipo.SERVICE_SEARCH_QUERY_PARSER, "excepcion.service.busqueda.parser");
    }

    /**
     * Returns a localized message for this reason type and locale.
     *
     * @param locale The locale.
     * @return A localized message given a reason type and locale.
     */
    public String getMessageForReasonType(Locale locale) {
        return LocaleUtil.getLocalizedMessageFromBundle("i18n/mensajes-service", MENSAJE_MAP.get(this), locale);
    }

    /**
     * Returns a message for this reason type in the default locale.
     *
     * @return A message message for this reason type in the default locale.
     */
    public String getMessageForReasonType() {
        return getMessageForReasonType(null);
    }

    /**
     * Returns a lower case string of this enum.
     *
     * @return a lower case string of this enum
     */
    public String lowerCaseString() {
        return toString().toLowerCase();
    }
}
