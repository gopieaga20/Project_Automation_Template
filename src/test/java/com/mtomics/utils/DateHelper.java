package com.mtomics.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * DateHelper class provides date and time utility methods
 */
public class DateHelper {

    private static final Logger logger = LogManager.getLogger(DateHelper.class);

    // Common date formats
    public static final String FORMAT_MMDDYYYY = "MM/dd/yyyy";
    public static final String FORMAT_DDMMYYYY = "dd/MM/yyyy";
    public static final String FORMAT_YYYYMMDD = "yyyy-MM-dd";
    public static final String FORMAT_FULL_DATE = "MMMM dd, yyyy";
    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_TIME_12HR = "hh:mm a";
    public static final String FORMAT_TIME_24HR = "HH:mm";

    /**
     * Get current date in specified format
     * 
     * @param format Date format
     * @return Formatted date string
     */
    public static String getCurrentDate(String format) {
        try {
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            String formattedDate = currentDate.format(formatter);
            logger.debug("Current date: {}", formattedDate);
            return formattedDate;
        } catch (Exception e) {
            logger.error("Failed to get current date: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Get current date and time in specified format
     * 
     * @param format DateTime format
     * @return Formatted datetime string
     */
    public static String getCurrentDateTime(String format) {
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            String formattedDateTime = currentDateTime.format(formatter);
            logger.debug("Current datetime: {}", formattedDateTime);
            return formattedDateTime;
        } catch (Exception e) {
            logger.error("Failed to get current datetime: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Get future date by adding days
     * 
     * @param days   Number of days to add
     * @param format Date format
     * @return Future date string
     */
    public static String getFutureDate(int days, String format) {
        try {
            LocalDate futureDate = LocalDate.now().plusDays(days);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            String formattedDate = futureDate.format(formatter);
            logger.debug("Future date (+{} days): {}", days, formattedDate);
            return formattedDate;
        } catch (Exception e) {
            logger.error("Failed to get future date: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Get past date by subtracting days
     * 
     * @param days   Number of days to subtract
     * @param format Date format
     * @return Past date string
     */
    public static String getPastDate(int days, String format) {
        try {
            LocalDate pastDate = LocalDate.now().minusDays(days);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            String formattedDate = pastDate.format(formatter);
            logger.debug("Past date (-{} days): {}", days, formattedDate);
            return formattedDate;
        } catch (Exception e) {
            logger.error("Failed to get past date: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Get future date by adding months
     * 
     * @param months Number of months to add
     * @param format Date format
     * @return Future date string
     */
    public static String getFutureDateByMonths(int months, String format) {
        try {
            LocalDate futureDate = LocalDate.now().plusMonths(months);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return futureDate.format(formatter);
        } catch (Exception e) {
            logger.error("Failed to get future date by months: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Get past date by subtracting months
     * 
     * @param months Number of months to subtract
     * @param format Date format
     * @return Past date string
     */
    public static String getPastDateByMonths(int months, String format) {
        try {
            LocalDate pastDate = LocalDate.now().minusMonths(months);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return pastDate.format(formatter);
        } catch (Exception e) {
            logger.error("Failed to get past date by months: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Convert date from one format to another
     * 
     * @param dateString Date string
     * @param fromFormat Current format
     * @param toFormat   Target format
     * @return Converted date string
     */
    public static String convertDateFormat(String dateString, String fromFormat, String toFormat) {
        try {
            SimpleDateFormat fromFormatter = new SimpleDateFormat(fromFormat);
            SimpleDateFormat toFormatter = new SimpleDateFormat(toFormat);
            Date date = fromFormatter.parse(dateString);
            return toFormatter.format(date);
        } catch (ParseException e) {
            logger.error("Failed to convert date format: {}", e.getMessage());
            throw new RuntimeException("Date conversion failed", e);
        }
    }

    /**
     * Get day of week
     * 
     * @return Day name (e.g., Monday, Tuesday)
     */
    public static String getDayOfWeek() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getDayOfWeek().toString();
    }

    /**
     * Get month name
     * 
     * @return Month name (e.g., January, February)
     */
    public static String getMonthName() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getMonth().toString();
    }

    /**
     * Get year
     * 
     * @return Current year
     */
    public static int getYear() {
        return LocalDate.now().getYear();
    }

    /**
     * Calculate days between two dates
     * 
     * @param startDate Start date string
     * @param endDate   End date string
     * @param format    Date format
     * @return Number of days
     */
    public static long getDaysBetween(String startDate, String endDate, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);
            return ChronoUnit.DAYS.between(start, end);
        } catch (Exception e) {
            logger.error("Failed to calculate days between dates: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Check if date is in the past
     * 
     * @param dateString Date string
     * @param format     Date format
     * @return boolean
     */
    public static boolean isDateInPast(String dateString, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDate date = LocalDate.parse(dateString, formatter);
            return date.isBefore(LocalDate.now());
        } catch (Exception e) {
            logger.error("Failed to check if date is in past: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Check if date is in the future
     * 
     * @param dateString Date string
     * @param format     Date format
     * @return boolean
     */
    public static boolean isDateInFuture(String dateString, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDate date = LocalDate.parse(dateString, formatter);
            return date.isAfter(LocalDate.now());
        } catch (Exception e) {
            logger.error("Failed to check if date is in future: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Get timestamp for file naming
     * 
     * @return Timestamp string (yyyyMMdd_HHmmss)
     */
    public static String getTimestamp() {
        return getCurrentDateTime("yyyyMMdd_HHmmss");
    }

    /**
     * Get age from date of birth
     * 
     * @param dobString Date of birth string
     * @param format    Date format
     * @return Age in years
     */
    public static int getAge(String dobString, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDate dob = LocalDate.parse(dobString, formatter);
            return (int) ChronoUnit.YEARS.between(dob, LocalDate.now());
        } catch (Exception e) {
            logger.error("Failed to calculate age: {}", e.getMessage());
            throw e;
        }
    }
}
