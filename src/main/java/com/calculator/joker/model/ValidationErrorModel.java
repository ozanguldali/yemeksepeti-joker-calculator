package com.calculator.joker.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static com.calculator.joker.util.ObjectMapperUtil.beautify;

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

        return beautify(str);

    }

}
