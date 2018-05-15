package es.gobcan.istac.idxmanager.core.util;

import org.apache.commons.csv.CSVFormat;

public class CSVFormatFactory {

    private static CSVFormat csvFormat = null;

    public static CSVFormat getCSVFormat() {
        if (csvFormat == null) {
            CSVFormat format = CSVFormat.newFormat(';').withQuote('"').withRecordSeparator("\r\n").withIgnoreEmptyLines(true);
            csvFormat = format;
        }
        return csvFormat;
    }

}
