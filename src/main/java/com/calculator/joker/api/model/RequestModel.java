package com.calculator.joker.api.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import static util.LoggerUtil.logger;

import static com.calculator.joker.api.model.ValidationErrorModel.errorMessage;

public class RequestModel {

    private final static JsonParser jsonParser = new JsonParser();

    public RequestModel(String payload) {

        errorMessage = null;

        JsonObject jsonObject = new JsonObject();

        try {

            jsonObject = jsonParser.parse( payload ).getAsJsonObject();

        } catch (Exception e) {

            logger.error( "Request is not a valid Json Object:\t" + e.getMessage() );
            errorMessage = "Request is not a valid Json Object";

        }

        try {

            JsonArray jsonArray = jsonObject.getAsJsonArray("customers");

            if ( jsonArray == null )
                errorMessage = "'customers' is not included in the request";

            else {

                if ( jsonArray.size() < 1 )
                    errorMessage = "'customers' array object is empty, users are not included in the request";
                else {

                    List<Customer> customerList = new ArrayList<>();

                    jsonArray.forEach(
                            ( value ) -> {

                                try {

                                    JsonObject customerObject = value.getAsJsonObject();

                                    String name = customerObject.get( "name" ).getAsString();
                                    String cost = customerObject.get( "cost" ).getAsString();

                                    Customer customer = new Customer();
                                    customer.setName( name );
                                    customer.setCost( cost );

                                    customerList.add( customer );

                                } catch (Exception e) {

                                    logger.error( "The element of 'customers' is not a valid Json Object:\t" + e.getMessage() );
                                    errorMessage = "The element of 'customers' is not a valid Json Object";

                                }

                            }
                    );

                    setCustomers( customerList );

                }

            }

        } catch (Exception e) {

            logger.error( "'customers' is not a valid Json Array:\t" + e.getMessage() );
            errorMessage = "'customers' is not a valid Json Array";

        }

    }

    private List<Customer> customers;

    public List<Customer> getCustomers() {
        return customers;
    }

    private void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

}
