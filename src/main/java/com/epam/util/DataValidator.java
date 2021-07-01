package com.epam.util;

import java.util.regex.Pattern;

public class DataValidator {
    private static DataValidator dataValidator;
    private static final int MAX_LENGTH = 40;

    public static DataValidator getInstance() {
        if (dataValidator == null) {
            dataValidator = new DataValidator();
        }
        return dataValidator;
    }

    /**
     * Minimum eight characters, at least one letter, one number and one special character
     */
    private static final String REGEX_FOR_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

    /**
     * Asserts that the match must ends with a letter or digit.
     * Must starts with a letter.
     * [A-Za-z\d.-]{0,19} matches the chars according to the pattern present inside the char class.
     * And the number of matched chars must be from 1 to 19.
     */
    private static final String REGEX_FOR_LOGIN = "^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\\d.-]{1,20}$";

    public boolean validatePasswordLogin(String password, String login) {
        if (checkParameterLength(password) && checkParameterLength(login)) {
            return validateLogin(login) && validatePassword(password);
        }
        return false;
    }

    private boolean validatePassword(String password) {
        return Pattern.matches(REGEX_FOR_PASSWORD, password);
    }

    private boolean validateLogin(String login) {
        return Pattern.matches(REGEX_FOR_LOGIN, login);
    }

    private boolean checkParameterLength(String parameter) {
        return parameter.length() > 0 && parameter.length() <= MAX_LENGTH;
    }
}
