package com.farhan.wgsuniversity.utilities;

public class InputValidation {
    private static final String alphaNumericOnly = "^[a-zA-Z0-9 ]*$";
    private static final String numbersOnly = "[0-9]+";

    public static boolean validateName(String value) {
        return value.trim().matches(alphaNumericOnly);
    }

    public static boolean validatePhoneNumber(String value) {
        return value.trim().matches(numbersOnly) && (value.trim().length() >= 10 && value.trim().length() <= 13);
    }

    public static boolean validateYear(String value) {
        return value.trim().length() == 4 && Short.parseShort(value.trim()) > 0;
    }

    public static boolean validateThreeDigitsId(String value) {
        return value.trim().length() == 3 && value.trim().matches(numbersOnly);
    }
}
