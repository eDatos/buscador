package es.gobcan.istac.idxmanager.core.util;

import org.apache.commons.lang.StringUtils;

public class ServiceUtils {

    public static final int MAX_TIKA_LIMIT = 1500000;

    public static String getFilterQueryString(String fqField, String fqFieldValue) {
        if (StringUtils.isNotEmpty(fqFieldValue)) {
            StringBuilder fqFilter = new StringBuilder();
            fqFilter.append(fqField).append(":\"").append(fqFieldValue).append("\"");
            return fqFilter.toString();
        }
        return null;
    }

    public static String getFilterQueryOrString(String fqField, String... fqValues) {
        if (fqValues != null && fqValues.length > 0) {
            StringBuilder fqFilter = new StringBuilder();
            fqFilter.append(fqField).append(":(");
            for (int i = 0; i < fqValues.length; i++) {
                if (StringUtils.isNotEmpty(fqValues[i])) {
                    fqFilter.append("\"").append(fqValues[i]).append("\"");
                }
            }
            fqFilter.append(")");
            return fqFilter.toString();
        }
        return null;
    }

    public static void checkRequired(String propertyName, String propertyValue) throws Exception {
        if (StringUtils.isBlank(propertyValue)) {
            throw new Exception("   Parametro \"" + propertyName + "\" requerido");
        }
    }
}
