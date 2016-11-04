package es.gobcan.istac.idxmanager.core.service;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.BinaryResponseParser;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;
import org.springframework.beans.factory.InitializingBean;

import es.gobcan.istac.idxmanager.core.exception.ServiceExcepcion;
import es.gobcan.istac.idxmanager.core.exception.ServiceExcepcionTipo;
import es.gobcan.istac.idxmanager.core.util.ServiceUtils;

public class SolrServiceImpl implements InitializingBean, SolrService {

    protected static Log log = LogFactory.getLog(SolrService.class);

    public static final int DEFAULT_CONNECTION_TIMEOUT = 60000; // default socket connection timeout in ms
    public static final String DEFAULT_ALL_QUERY = "*:*";

    private SolrClient solrClient = null;

    private String solrUrl;
    private String coreOrCollection;
    private boolean cloudServer = false;

    public void setSolrUrl(String solrUrl) {
        this.solrUrl = solrUrl;
    }

    public void setCoreOrCollection(String coreOrCollection) {
        this.coreOrCollection = coreOrCollection;
    }

    public void setCloudServer(boolean cloudServer) {
        this.cloudServer = cloudServer;
    }

    @Override
    public void setSolrClient(SolrClient solrClient) {
        this.solrClient = solrClient;
    }

    /**
     * Comprobacion de propiedades despues de la inicializacion del bean e inicializaciones
     */
    @Override
    public void afterPropertiesSet() throws Exception {

        log.info("Comprobacion de propiedades tras la construccion del bean de SolR");

        ServiceUtils.checkRequired("solrUrl", solrUrl);

        log.info("   - solrUrl = " + solrUrl);
        log.info("Terminada la comprobacion de propiedades del bean de SolR");

        if (cloudServer) {
            solrClient = createCloudSolrClient();
        } else {
            solrClient = createHttpSolrClient();
        }
    }

    private boolean checkSolrIsAlive() {
        if (solrClient == null) {
            return false;
        }

        SolrPingResponse ping;
        try {
            ping = solrClient.ping();
            if (ping.getStatus() != 0) {
                return false;
            } ;

        } catch (SolrServerException | IOException e) {
            log.error("Solr no ha respondido satisfactoriamente a una solicitud de ping:  " + e);
            throw new RuntimeException(e);
        }

        return true;
    }

    public SolrClient createHttpSolrClient() {
        try {
            // setup the server...
            HttpSolrClient client = new HttpSolrClient(solrUrl + "/" + coreOrCollection);
            client.setConnectionTimeout(DEFAULT_CONNECTION_TIMEOUT);
            client.setDefaultMaxConnectionsPerHost(100);
            client.setMaxTotalConnections(100);
            // client.setUseMultiPartPost(random().nextBoolean());

            // explicitly uses the binary codec for communication.
            client.setParser(new BinaryResponseParser());
            client.setRequestWriter(new BinaryRequestWriter());

            return client;
        } catch (Exception e) {
            log.error("Imposible abrir una conexión directa con Solr: " + e);
            throw new RuntimeException(e);
        }
    }

    public SolrClient createCloudSolrClient() {
        try (CloudSolrClient client = new CloudSolrClient(solrUrl)) {
            client.setDefaultCollection(coreOrCollection);
            client.setZkClientTimeout(DEFAULT_CONNECTION_TIMEOUT);

            // explicitly uses the binary codec for communication.
            client.setParser(new BinaryResponseParser());
            client.setRequestWriter(new BinaryRequestWriter());

            client.connect();
            return client;
        } catch (Exception e) {
            log.error("Imposible abrir una conexión con Solr a través de Zookeeper" + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void borrarTodoAndCommit() throws ServiceExcepcion {
        try {
            solrClient.deleteByQuery(DEFAULT_ALL_QUERY);
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            log.error("Se ha producido un error borrando todos los documentos del índice de Lucene", e);
            throw new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_GENERAL);
        }
    }

    @Override
    public void borrarWithoutCommit(String query) throws ServiceExcepcion {
        try {
            solrClient.deleteByQuery(query);
        } catch (SolrServerException | IOException e) {
            log.error("Se ha produciendo un error borrando los documentos resultado del query: " + query, e);
            throw new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_GENERAL);
        }
    }

    @Override
    public void insertarDoc(SolrInputDocument solrInputDocument) throws ServiceExcepcion {
        try {
            solrClient.add(solrInputDocument);
        } catch (SolrServerException | IOException e) {
            log.error("Se ha producido un error insertando el documento " + solrInputDocument + e);
            throw new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_GENERAL);
        }
    }

    @Override
    public void commitANDoptimize() throws ServiceExcepcion {
        try {
            solrClient.commit();
            solrClient.optimize();
        } catch (SolrServerException | IOException e) {
            log.error("Imposible de realizar un commit + optimize", e);
            throw new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_GENERAL);
        }
    }

    @Override
    public void commit() throws ServiceExcepcion {
        try {
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            log.error("Imposible de realizar un commit" + e);
            throw new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_GENERAL);
        }
    }

    @Override
    public void eliminarDoc(String id) throws ServiceExcepcion {
        try {
            solrClient.deleteById(id);
        } catch (SolrServerException | IOException e) {
            log.error("Imposible eliminar el documento con ID: " + id, e);
            throw new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_GENERAL);
        }
    }

    @Override
    public QueryResponse ejecutarQuery(SolrQuery solrQuery) throws ServiceExcepcion {
        try {
            return solrClient.query(solrQuery);
        } catch (SolrServerException | IOException e) {
            throw new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_GENERAL);
        }
    }

    /**
     * Para utilizar con Solr Cell
     *
     * @param contentStreamUpdateRequest
     * @return
     * @throws ServiceExcepcion
     */
    public NamedList<Object> ejecutarContentStreamUpdReq(ContentStreamUpdateRequest contentStreamUpdateRequest) throws ServiceExcepcion {
        try {
            return solrClient.request(contentStreamUpdateRequest);
        } catch (SolrServerException | IOException e) {
            log.error(":ejecutarRequest: " + e);
            throw new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_GENERAL);
        }
    }

}
