package com.volkmatrix.common.utils;

import com.volkmatrix.common.exception.ApiServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

public class
WapDateUtils {
  private static final Logger LOGGER = LoggerFactory.getLogger(WapDateUtils.class);

  public static long getCurrentEpochDateTime() {
    try {
      LocalDateTime date = LocalDateTime.now(ZoneId.of("GMT"));
      ZonedDateTime zdt = date.atZone(ZoneId.of("GMT"));
      long millis = zdt.toInstant().toEpochMilli();
      return millis;

    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new ApiServiceException(e.getMessage());
    }
  }

  public static long getCurrentEpochDateTimeSummary() {
    try {
      LocalDateTime now = LocalDateTime.now();
//			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      String formatDateTime = now.format(format);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date dt = sdf.parse(formatDateTime);
      long millis = dt.getTime();
      return millis;

    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new ApiServiceException(e.getMessage());
    }
  }

  public static String getCurrentEpochDateTime(String zone) {
    try {
      LocalDateTime date = LocalDateTime.now(ZoneId.of(zone));

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");


      String formatDateTime = date.format(formatter);

      return date.getDayOfWeek().name().substring(0, 3) + "," + date.getDayOfMonth() + " " + date.getMonth().name().substring(0, 3) + " " + date.getYear() + "," + formatDateTime;

    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new ApiServiceException(e.getMessage());
    }
  }

  public static void main(String args[]) {
    //    long tm=stringDateToEpoch("2022-07-01", "yyyy-MM-dd");
    long dt = 1702924200000l;
    long tm = 37800000;
    long dtTm = dt + tm;
    // System.out.print(tm);
    LocalDateTime localDateTime = localDateTimeFromEpoch(dtTm, "");
    System.out.println(localDateTime);

    Month month = localDateTime.getMonth();

    // Fetch the month name using TextStyle and Locale
    String monthName = month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

    String formatDateTime = localDateTime.format(formatter);

    // Print the result
    System.out.println("Month Name: " + monthName + " " + localDateTime.getDayOfMonth() + "," + localDateTime.getYear());

    System.out.println("TIME:" + localDateTime.getHour() + ":" + localDateTime.getMinute());


  }

  public static Long stringDateToEpoch(String timestamp, String format) {
    if (timestamp == null) return null;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      Date dt = sdf.parse(timestamp);
      long epoch = dt.getTime();
      return epoch;
    } catch (ParseException | ApiServiceException e) {
      throw new ApiServiceException("Error in DateUtils in stringDateToEpoch Method", e);
    }
  }

  public static String stringDateFromEpoch(long timestamp, String zone) {

    try {
      LocalDate ld = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
      return ld.toString();
    } catch (Exception e) {
      throw new ApiServiceException("Error in DateUtils in stringDateToEpoch Method", e);
    }
  }

  public static LocalDateTime localDateTimeFromEpoch(long timestamp, String zone) {

    try {
      LocalDateTime ld = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
      return ld;
    } catch (Exception e) {
      throw new ApiServiceException("Error in DateUtils in stringDateToEpoch Method", e);
    }
  }

  public static LocalDate stringDateToLocalDate(String timestamp, String format) {
    if (timestamp == null) return null;
    try {

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
      LocalDate dateTime = LocalDate.parse(timestamp, formatter);
      return dateTime;
    } catch (Exception e) {
      throw new RuntimeException("Error in DateUtils in stringDateToLocalDateTime Method", e);
    }
  }

  public static String convertTimestampToStringDateTime(long timestamp, String zone) {
    try {
      // Convert seconds to milliseconds
      Instant instant = Instant.ofEpochMilli(timestamp * 1000);

      // Format the LocalDateTime as a string
      LocalDateTime localDateTime = instant.atZone(ZoneId.of(zone)).toLocalDateTime();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
      return localDateTime.format(formatter);
    } catch (Exception e) {
      // Handle exceptions, e.g., log or throw a custom exception
      throw new ApiServiceException("Error converting timestamp to string date and time", e);
    }
  }

}
