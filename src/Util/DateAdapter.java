package Util;

import java.time.Instant;
import java.time.LocalDate;
import java.sql.Date;
import java.time.ZoneId;

public class DateAdapter {
    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
