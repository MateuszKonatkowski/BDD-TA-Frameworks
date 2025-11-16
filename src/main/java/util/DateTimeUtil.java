package util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

//Utility class for getting the current system date and time as formatted strings.
public class DateTimeUtil {

    public static String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String getCurrentTime() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss"));
    }
}