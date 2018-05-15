package es.gobcan.istac.idxmanager.core.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public final class JSR310DateTimeFormatters {

    public static final DateTimeFormatter FORMATTER_YYYYMMDDHH24MISS;
    public static final DateTimeFormatter FORMATTER_YYYYMM;
    public static final DateTimeFormatter FORMATTER_HUMAN_DATETIME_SHORT;

    static {
        FORMATTER_YYYYMM = DateTimeFormatter.ofPattern("yyyyMM").withZone(ZoneId.systemDefault());
        FORMATTER_YYYYMMDDHH24MISS = DateTimeFormatter.ofPattern("yyyyMMddkkmmss").withZone(ZoneId.systemDefault());
        FORMATTER_HUMAN_DATETIME_SHORT = DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm:ss");
    }

    public static String formatInyyyyMMddhh24miss(Instant instant) {
        return FORMATTER_YYYYMMDDHH24MISS.format(instant);
    }

    public static DateTimeFormatter formatterHumanDatetimeShort() {
        return FORMATTER_HUMAN_DATETIME_SHORT;
    }

    public static String formatInYYYYMM(ZonedDateTime now) {
        return FORMATTER_YYYYMM.format(now);
    }
}
