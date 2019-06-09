package com.calculator.joker.api.model;

import static com.calculator.joker.api.util.ObjectMapperUtil.beautifyJsonToString;

public class ValidationErrorModel {

    public static String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ValidationErrorModel.errorMessage = errorMessage;
    }

    @Override
    public String toString() {

        final String str = "{" +
                "\"errorMessage\":\"" + errorMessage + "\"" +
                "}";

        return beautifyJsonToString(str);

    }

}
