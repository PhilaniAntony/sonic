package app.sonic.test.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class DataUtil {

    public static String generateRandomLetters( int length, boolean hasLetters, boolean hasNumeric) {
        return RandomStringUtils.random(length, hasLetters, hasNumeric);
    }
}