package com.calculator.joker.api.service;

import com.calculator.joker.api.model.RequestModel;
import com.calculator.joker.api.model.ResponseModel;
import com.calculator.joker.api.model.Customer;
import com.calculator.joker.api.model.ValidationErrorModel;
import org.springframework.stereotype.Service;
import util.CalculatorUtil;
import util.ResultUtil;

import java.util.HashMap;
import java.util.List;

import static util.LoggerUtil.logger;

import static app.ConsoleMain.customerInfo;
import static app.ConsoleMain.customerSize;
import static com.calculator.joker.api.model.ValidationErrorModel.errorMessage;

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

        int userNameCounter = 1;

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

                double userCostDouble = 0;

                try {

                    userCostDouble = Double.valueOf( userCost );

                } catch (Exception e) {

                    logger.error( "customers[i].cost is not a valid double element:\t" + e.getMessage() );
                    errorMessage = "customers[i].cost is not a valid double element";

                }

                if ( customerInfo.containsKey( userName ) ) {

                    userName = userName + "_" + userNameCounter;
                    userNameCounter++;

                }

                customerInfo.put( userName, userCostDouble );

                customerSize++;

            }

            if ( errorMessage == null )
                CalculatorUtil.calculateTotalDiscount();

            if ( errorMessage == null )
                CalculatorUtil.calculateCustomerDiscount();

            if ( errorMessage == null )
                ResultUtil.printResult();

            customerInfoService = (HashMap<String, Double>) customerInfo.clone();

        }

        return new ResponseModel( customerInfoService );

    }

    public ValidationErrorModel getError() {
        return new ValidationErrorModel();
    }

}
