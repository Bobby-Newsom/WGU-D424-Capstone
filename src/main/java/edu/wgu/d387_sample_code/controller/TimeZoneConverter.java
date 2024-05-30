package edu.wgu.d387_sample_code.controller;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@CrossOrigin(origins = "http://localhost:4200")
public class TimeZoneConverter {

    public static String getTime(ZonedDateTime zonedDateTime) {

        //format to HH:mm
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        //Time zones
        //EST
        ZonedDateTime est = zonedDateTime.withZoneSameInstant(ZoneId.of("America/New_York"));

        //MST
        ZonedDateTime mst = zonedDateTime.withZoneSameInstant(ZoneId.of("America/Denver"));

        //UTC
        ZonedDateTime utc = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));

        String presentationTimes = est.format(dateTimeFormatter) + " EST | " + mst.format(dateTimeFormatter) + " MST | "
                + utc.format(dateTimeFormatter) + " UTC";

        return presentationTimes;
    }

}
