package com.calculator.joker.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static app.ConsoleMain.customerSize;

public class ResponseModel {

    private ResponseModel responseModel;

    private List<Customer> customers;

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

        customerInfoService.clear();
        customerSize = 0;

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

}
