package com.volkmatrix.common.utils;

import com.volkmatrix.common.enums.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class WapDateTimeUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(WapDateTimeUtils.class);

    public static void main(String args[]) {
        LocalDate today = LocalDate.now();
        long todayEpoch = WapDateTimeUtils.getEpochFromLocalDate(today, TimeZone.GMT.getValue());
        System.out.println(getCurrentEpochDateTimeStr(TimeZone.IST.getValue()));

        //long tm=DateUtils.getCurrentEpochDateTime();
        //System.out.println(tm);
    }

    public static long getCurrentEpochDateTime(String timeZone) {
        try {
            LocalDateTime date = LocalDateTime.now(ZoneId.of(timeZone));
            ZonedDateTime zdt = date.atZone(ZoneId.of(timeZone));
            long millis = zdt.toInstant().toEpochMilli();
            return millis;

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


    public static String getCurrentEpochDateTimeStr(String zone) {
        try {
            LocalDateTime date = LocalDateTime.now(ZoneId.of(zone));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

            String formatDateTime = date.format(formatter);

            //System.out.println(formatDateTime);
            return date.getDayOfWeek().name().substring(0, 3) + "," + date.getDayOfMonth() + " " + date.getMonth().name().substring(0, 3) + " " + date.getYear() + "," + formatDateTime;

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public static String getEpochDateStr(long epochDtTm) {
        try {
            LocalDateTime dateTm = WapDateUtils.localDateTimeFromEpoch(epochDtTm,"");
            
            Month month = dateTm.getMonth();

            // Fetch the month name using TextStyle and Locale
            String monthName = month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

           
            String formatDateTime = monthName+" "+dateTm.getDayOfMonth()+","+dateTm.getYear();
            
            System.out.println("Format Date: " + formatDateTime);

            
            return formatDateTime;

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public static String getEpochTimeStr(long epochDtTm) {
        try {
            LocalDateTime dateTm = WapDateUtils.localDateTimeFromEpoch(epochDtTm,"");
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

            String formatDateTime = dateTm.format(formatter);

            
            return formatDateTime;

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


    public static long getEpochFromLocalDate(LocalDate date, String timeZone) {
        try {
            LocalDateTime localDateTime = date.atStartOfDay();
            ZoneId indiaZoneId = ZoneId.of(timeZone);
            ZoneOffset indiaZoneOffset = indiaZoneId.getRules().getOffset(Instant.now());

            Instant instant = localDateTime.toInstant(indiaZoneOffset);
            // Convert LocalDateTime to epoch timestamp
            //  long epochTimestamp = localDateTime.toEpochSecond(java.time.ZoneOffset.UTC)*1000;

            return instant.toEpochMilli();

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String fetchDay(LocalDate date) {
        try {
            LocalDateTime localDateTime = date.atStartOfDay();

            // Convert LocalDateTime to epoch timestamp
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            String dayOfWeekString = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

            return dayOfWeekString;

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
