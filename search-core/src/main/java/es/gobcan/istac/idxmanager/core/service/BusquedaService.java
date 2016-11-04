package es.gobcan.istac.idxmanager.core.service;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import es.gobcan.istac.idxmanager.core.exception.ServiceExcepcion;
import es.gobcan.istac.idxmanager.domain.mvc.Busqueda;

public interface BusquedaService {

    QueryResponse ejecutarQuery(Busqueda busqueda, int resultByPage) throws ServiceExcepcion;

    SolrDocumentList ejecutarQueryForSuggested(Busqueda busqueda) throws ServiceExcepcion;

    QueryResponse ejecutarInitialFacetQuery() throws ServiceExcepcion;
}
