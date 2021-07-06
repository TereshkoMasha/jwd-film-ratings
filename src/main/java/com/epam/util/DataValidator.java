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

    private DataValidator() {

    }

    private static final String REGEX_FOR_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

    private static final String REGEX_FOR_LOGIN = "^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\\d.-]{1,20}$";

    /**
     * Checks password and login during registration.
     *
     * @param login - unique value
     * @return {@code true} if the password and login have been verified
     */
    public boolean validatePasswordLogin(String password, String login) {
        if (checkParameterLength(password) && checkParameterLength(login)) {
            return validateLogin(login) && validatePassword(password);
        }
        return false;
    }

    /**
     * Password check.
     *
     * @param password minimum eight characters,
     *                 at least one letter, one number and one special character
     * @return {@code true} if the password was verified, {@code false} otherwise
     */
    private boolean validatePassword(String password) {
        return Pattern.matches(REGEX_FOR_PASSWORD, password);
    }

    /**
     * @param login asserts that the match must ends with a letter or digit.
     *              Must starts with a letter.
     *              [A-Za-z\d.-]{0,19} matches the chars according to the pattern present inside the char class.
     *              And the number of matched chars must be from 1 to 19.
     * @return {@code true} if the login was verified, {@code false} otherwise
     */
    private boolean validateLogin(String login) {
        return Pattern.matches(REGEX_FOR_LOGIN, login);
    }


    private boolean checkParameterLength(String parameter) {
        return parameter.length() > 0 && parameter.length() <= MAX_LENGTH;
    }
}
