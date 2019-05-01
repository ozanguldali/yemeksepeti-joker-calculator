package com.calculator.joker.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import static com.calculator.joker.model.ValidationErrorModel.errorMessage;

public class RequestModel {

    final static JsonParser jsonParser = new JsonParser();

    public RequestModel(String payload) {

        errorMessage = null;

        JsonObject jsonObject = new JsonObject();

        try {

            jsonObject = jsonParser.parse( payload ).getAsJsonObject();

        } catch (Exception e) {

            e.printStackTrace();
            errorMessage = "Request is not a valid Json Object";

        }

        try {

            JsonArray jsonArray = jsonObject.getAsJsonArray("customers");

            if ( jsonArray == null )
                errorMessage = "'customers' is not a valid Json Array";

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

                                e.printStackTrace();
                                errorMessage = "The element of 'customers' is not a valid Json Object";

                            }

                        }
                );

                setCustomers( customerList );

            }

        } catch (Exception e) {

            e.printStackTrace();
            errorMessage = "'customers' is not a valid Json Array";

        }

    }

    public List<Customer> customers;

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

}
