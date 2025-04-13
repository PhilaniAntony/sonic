package app.sonic.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class DataUtil {

    public static String generateRandomLetters( int length, boolean hasLetters, boolean hasNumeric) {
        return RandomStringUtils.random(length, hasLetters, hasNumeric);
    }

    public static String generatePastDate(String dateFormat) {
        try {
            int year = LocalDate.now().getYear() - 1;

            Random random = new Random();
            int month = random.nextInt(12) + 1;
            int day = random.nextInt(28) + 1;

            return DateTimeFormatter.ofPattern(dateFormat).format(LocalDate.of(year, month, day));
        } catch (IllegalArgumentException e) {
            return "Invalid date format. Please provide a valid format.";
        }
    }

    public static String getCurrentDate(String dateFormat) {
        return DateTimeFormatter.ofPattern(dateFormat).format(LocalDate.now());
    }
}