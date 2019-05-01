package com.calculator.joker.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static app.ConsoleMain.customerSize;
import static util.CalculatorUtil.discountPercent;
import static util.CalculatorUtil.totalAmount;
import static util.CalculatorUtil.totalDiscountAmount;
import static util.ResultUtil.roundedCost;

public class ResponseModel {

    private ResponseModel responseModel;

    private List<Customer> customers;

    private String totalActualCost;
    private String totalDiscountedCost;
    private String totalDiscountRatio;
    private String totalRoundedCost;

    private ResponseModel() {

    }

    public ResponseModel(HashMap<String, Double> customerInfoService) {

        responseModel = new ResponseModel();

        customers = new LinkedList<>();

        customerInfoService.forEach(

                (name, discountedCost) -> {

                    Customer customer = new Customer();

                    customer.setName( name );
                    customer.setCost( String.valueOf( discountedCost )  );

                    customers.add(customer);

                }

        );

        responseModel.setCustomers( customers );

        totalActualCost = String.valueOf( totalAmount );
        totalDiscountedCost = String.valueOf( totalDiscountAmount );
        totalDiscountRatio = String.valueOf( discountPercent );
        totalRoundedCost = String.valueOf( roundedCost );

        customerInfoService.clear();
        customerSize = 0;

    }

    public ResponseModel(String errorMessage) {

        ValidationErrorModel.errorMessage = errorMessage;

    }

    public List<Customer> getCustomers() {
        return customers;
    }

    private void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public void setResponseModel(ResponseModel responseModel) {
        this.responseModel = responseModel;
    }

    public String getTotalActualCost() {
        return totalActualCost;
    }

    public void setTotalActualCost(String totalActualCost) {
        this.totalActualCost = totalActualCost;
    }

    public String getTotalDiscountedCost() {
        return totalDiscountedCost;
    }

    public void setTotalDiscountedCost(String totalDiscountedCost) {
        this.totalDiscountedCost = totalDiscountedCost;
    }

    public String getTotalRoundedCost() {
        return totalRoundedCost;
    }

    public void setTotalRoundedCost(String totalRoundedCost) {
        this.totalRoundedCost = totalRoundedCost;
    }

    public String getTotalDiscountRatio() {
        return totalDiscountRatio;
    }

    public void setTotalDiscountRatio(String totalDiscountRatio) {
        this.totalDiscountRatio = totalDiscountRatio;
    }

    @Override
    public String toString() {

        final String[] str = {"{" +
                "\"customers\": ["};

        customers.forEach(
                ( customer ) -> {

                    str[0] = str[0] + "{" +
                                    "\"name\":\"" + customer.getName() + "\"," +
                                    "\"cost\":\"" + customer.getCost() + "\"" +
                                "},";

                }
        );

        if ( str[0].endsWith( "," ) )
            str[0] = str[0].substring( 0, str[0].length() - 1 );

        str[0] = str[0] + "]," +
                "\"totalActualCost\":\"" + totalActualCost + "\"," +
                "\"totalDiscountedCost\":\"" + totalDiscountedCost + "\"," +
                "\"totalDiscountRatio\":\"" + totalDiscountRatio + "\"," +
                "\"totalRoundedCost\":\"" + totalRoundedCost + "\"" +
                "}";

        return beautify(str[0]);

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
