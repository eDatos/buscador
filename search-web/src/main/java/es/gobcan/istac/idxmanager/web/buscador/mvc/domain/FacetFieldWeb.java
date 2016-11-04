package es.gobcan.istac.idxmanager.web.buscador.mvc.domain;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.apache.solr.client.solrj.response.FacetField;

import com.ibm.icu.text.Collator;
import com.ibm.icu.util.ULocale;

public class FacetFieldWeb {

    private final String LANGUAGE_TAG = "es_ES";

    private String name;
    private List<FacetValueWeb> values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FacetValueWeb> getValues() {
        if (values == null) {
            values = new LinkedList<FacetValueWeb>();
        }
        return values;
    }

    public void setValues(List<FacetValueWeb> values) {
        this.values = values;
    }

    public FacetFieldWeb(String facetFieldName) {
        name = facetFieldName;
    }

    public void addSimpleFacetCountFromSolr(FacetField facetField) {
        for (FacetField.Count count : facetField.getValues()) {
            getValues().add(new FacetValueWeb(this, count.getName(), count.getCount()));
        }
    }

    public void sortValues(boolean sorByName) {
        // Sort by Facet Value
        Collections.sort(values, new FacetValueWebComparator(sorByName));
    }

    private class FacetValueWebComparator implements Comparator<FacetValueWeb> {

        private Collator collator;
        private boolean sorByName = true;

        public FacetValueWebComparator(boolean sorByName) {
            Collator collator = Collator.getInstance(ULocale.forLanguageTag(LANGUAGE_TAG));
            collator.setStrength(Collator.PRIMARY);
            this.collator = collator;
            this.sorByName = sorByName;
        }

        @Override
        public int compare(FacetValueWeb countA, FacetValueWeb countB) {
            if (sorByName) {
                // Name ascending
                return collator.compare(countA.getName().trim(), countB.getName().trim());
            } else {
                // Key descending
                return collator.compare(countB.getKey().trim(), countA.getKey().trim());
            }
        }

    }

}
