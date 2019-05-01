package com.calculator.joker.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ValidationErrorModel {

    public static String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {

        final String str = "{" +
                "\"errorMessage\":\"" + errorMessage + "\"" +
                "}";

        return beautify(str);

    }

    String beautify(String json) {
        ObjectMapper mapper = new ObjectMapper();
        Object obj = null;
        try {
            obj = mapper.readValue(json, Object.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;

    }

}
