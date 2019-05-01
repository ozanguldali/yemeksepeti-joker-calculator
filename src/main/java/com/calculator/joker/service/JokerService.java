package com.calculator.joker.service;

import com.calculator.joker.model.RequestModel;
import com.calculator.joker.model.ResponseModel;
import com.calculator.joker.model.Customer;
import com.calculator.joker.model.ValidationErrorModel;
import org.springframework.stereotype.Service;
import util.CalculatorUtil;
import util.ResultUtil;

import java.util.HashMap;
import java.util.List;

import static app.ConsoleMain.customerInfo;
import static app.ConsoleMain.customerSize;
import static com.calculator.joker.model.ValidationErrorModel.errorMessage;

@Service
public class JokerService {

    private static HashMap<String, Double> customerInfoService = new HashMap<>();

    public ResponseModel getResponse(String payload) {

        RequestModel requestModel = new RequestModel( payload );

        customerInfoService.clear();
        customerInfo.clear();
        customerSize = 0;
        List<Customer> customerList;

        try {

            customerList = requestModel.getCustomers();

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseModel( e.getMessage() );

        }

        if ( customerList != null ) {

            for ( Customer customer : customerList ) {

                String userName;

                try {

                    userName = customer.getName();

                } catch (Exception e) {

                    e.printStackTrace();

                    return new ResponseModel( e.getMessage() );

                }

                String userCost;

                try {

                    userCost = customer.getCost();

                } catch (Exception e) {

                    e.printStackTrace();

                    return new ResponseModel( e.getMessage() );

                }

                double userCostDouble = Double.valueOf( userCost );

                customerInfo.put( userName, userCostDouble );

                customerSize++;

            }

            CalculatorUtil.calculateTotalDiscount();

            CalculatorUtil.calculateCustomerDiscount();

            ResultUtil.printResult();

            customerInfoService = (HashMap<String, Double>) customerInfo.clone();

        }

        return new ResponseModel( customerInfoService );

    }

    public ValidationErrorModel getError() {
        return new ValidationErrorModel();
    }

}
