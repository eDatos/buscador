package es.gobcan.istac.idxmanager.web.buscador.util;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrDocument;

import es.gobcan.istac.idxmanager.domain.dom.TypeNMDomain;
import es.gobcan.istac.idxmanager.domain.modelo.IndexacionEnumDomain;

public class MetamacPortalWebUtils {

    private static final String COLON                              = ":";
    private static final String LEFT_PARENTHESIS                   = "(";
    private static final String RIGHT_PARENTHESIS                  = ")";

    private static final String PAGE_DATA_RESOURCE                 = "data.html";
    private static final String PAGE_COLLECTION_RESOURCE           = "collection.html";
    private static final String URL_SEPARATOR                      = "/";
    private static final String URL_QUERY_SEPARATOR                = "?";
    private static final String URL_QUERY_EQUALS                   = "=";
    private static final String URL_QUERY_AND                      = "&";
    private static final String URL_QUERY_PARAMETER_RESOURCEID     = "resourceId";
    private static final String URL_QUERY_PARAMETER_AGENCYID       = "agencyId";
    private static final String URL_QUERY_PARAMETER_VERSION        = "version";
    private static final String URL_QUERY_PARAMETER_RESOURCETYPE   = "resourceType";

    private static final String URL_QUERY_RESOURCE_TYPE_DATASET    = "dataset";
    private static final String URL_QUERY_RESOURCE_TYPE_COLLECTION = "collection";

    public static String buildDatasetVersionUrl(SolrDocument item, TypeNMDomain typeNMDomain, String endpointUrl) {
        StringBuilder builder = new StringBuilder();
        builder.append(buildEndpointUrl(endpointUrl));
        builder.append(PAGE_DATA_RESOURCE);
        builder.append(URL_QUERY_SEPARATOR);
        builder.append(buildQueryParametersForVersionableResource(item, typeNMDomain));

        return builder.toString();
    }

    public static String buildPublicationVersionUrl(SolrDocument item, TypeNMDomain typeNMDomain, String endpointUrl) {
        StringBuilder builder = new StringBuilder();
        builder.append(buildEndpointUrl(endpointUrl));
        builder.append(PAGE_COLLECTION_RESOURCE);
        builder.append(URL_QUERY_SEPARATOR);
        builder.append(buildQueryParametersForNotVersionableResource(item, typeNMDomain));

        return builder.toString();
    }

    private static String buildEndpointUrl(String endpointUrl) {
        StringBuilder builder = new StringBuilder().append(endpointUrl);

        if (!StringUtils.isBlank(endpointUrl) && !endpointUrl.endsWith(URL_SEPARATOR)) {
            builder.append(URL_SEPARATOR);
        }

        return builder.toString();
    }

    private static String buildQueryParametersForVersionableResource(SolrDocument item, TypeNMDomain typeNMDomain) {
        StringBuilder builder = new StringBuilder();

        String parametersForNotVersionableResource = buildQueryParametersForNotVersionableResource(item, typeNMDomain);
        builder.append(parametersForNotVersionableResource);
        builder.append(URL_QUERY_AND);
        builder.append(URL_QUERY_PARAMETER_VERSION);
        builder.append(URL_QUERY_EQUALS);

        String[] splitUrnStructure = splitUrnStructure((String) item.getFieldValue(IndexacionEnumDomain.ID.getCampo()));
        String versionLogic = splitUrnStructure[2];
        builder.append(versionLogic);

        return builder.toString();
    }

    private static String buildQueryParametersForNotVersionableResource(SolrDocument item, TypeNMDomain typeNMDomain) {
        StringBuilder builder = new StringBuilder();

        String[] splitUrnStructure = splitUrnStructure((String) item.getFieldValue(IndexacionEnumDomain.ID.getCampo()));

        String maintainerCode = splitUrnStructure[0];
        String code = splitUrnStructure[1];
        String resourceType = determinateResourceType(typeNMDomain);

        builder.append(URL_QUERY_PARAMETER_RESOURCETYPE);
        builder.append(URL_QUERY_EQUALS);
        builder.append(resourceType);
        builder.append(URL_QUERY_AND);
        builder.append(URL_QUERY_PARAMETER_AGENCYID);
        builder.append(URL_QUERY_EQUALS);
        builder.append(maintainerCode);
        builder.append(URL_QUERY_AND);
        builder.append(URL_QUERY_PARAMETER_RESOURCEID);
        builder.append(URL_QUERY_EQUALS);
        builder.append(code);

        return builder.toString();

    }

    private static String determinateResourceType(TypeNMDomain typeNMDomain) {

        switch (typeNMDomain) {
            case DATASET_DSC:
                return URL_QUERY_RESOURCE_TYPE_DATASET;
            case COLLECTION_DSP:
                return URL_QUERY_RESOURCE_TYPE_COLLECTION;

            default:
                return null;
        }
    }

    /**
     * Splits an Structure URN in agency, resourceId and version
     * <p>
     * For example: For the URN urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=INE:KF_DIV_18(1.0) returns {INE, KF_DIV_18, 1.0}
     */
    public static String[] splitUrnStructure(String urn) {
        // Sample result: INE:KF_DIV_18(1.0)
        String tripletIdentifier = removePrefix(urn);
        return splitUrnWithoutPrefixStructure(tripletIdentifier);
    }

    public static String[] splitUrnWithoutPrefixStructure(String tripletIdentifier) {
        String agencyID = StringUtils.substringBefore(tripletIdentifier, COLON.toString());
        String resourceID = StringUtils.substringBetween(tripletIdentifier, COLON, LEFT_PARENTHESIS);
        String version = StringUtils.substringBetween(tripletIdentifier, LEFT_PARENTHESIS, RIGHT_PARENTHESIS);

        return new String[]{agencyID, resourceID, version};
    }

    /**
     * Returns the URN without its prefix.
     * <p>
     * For example:
     * <p>
     * Given the URN urn:sdmx:org.sdmx.infomodel.base.AgencyScheme=ECB:AGENCIES(1.0), returns ECB:AGENCIES(1.0)
     *
     * @param urn
     * @return
     */
    public static String removePrefix(String urn) {
        if (StringUtils.isBlank(urn)) {
            throw new IllegalArgumentException("URN can not be blank");
        }
        String[] splitUrn = urn.split("=");
        if (splitUrn.length != 2) {
            throw new IllegalArgumentException("URN does not have a valid format");
        }
        return splitUrn[1];
    }
}
