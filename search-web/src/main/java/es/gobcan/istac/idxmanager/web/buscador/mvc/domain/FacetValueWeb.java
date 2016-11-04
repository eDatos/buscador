package es.gobcan.istac.idxmanager.web.buscador.mvc.domain;

import org.apache.solr.client.solrj.util.ClientUtils;

public class FacetValueWeb {

    private FacetFieldWeb parentField;
    private String key;
    private String name;
    private long count = 0;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public FacetFieldWeb getParentField() {
        return parentField;
    }

    public void setParentField(FacetFieldWeb parentField) {
        this.parentField = parentField;
    }

    public FacetValueWeb(FacetFieldWeb parentField, String key, String value, long count) {
        this.parentField = parentField;
        this.key = key;
        name = value;
        this.count = count;
    }

    public FacetValueWeb(FacetFieldWeb parentField, String value, long count) {
        this.parentField = parentField;
        name = value;
        this.count = count;
    }

    @Override
    public String toString() {
        return name + " (" + count + ")";
    }

    public String getAsFilterQuery() {
        if (parentField.getName().equals("facet_queries")) {
            return name;
        }
        return ClientUtils.escapeQueryChars(parentField.getName()) + ":" + ClientUtils.escapeQueryChars(getName());
    }

}
