package com.calculator.joker.service;

import com.calculator.joker.model.RequestModel;
import com.calculator.joker.model.ResponseModel;
import com.calculator.joker.model.Customer;
import org.springframework.stereotype.Service;
import util.CalculatorUtil;
import util.ResultUtil;

import java.util.HashMap;

import static app.ConsoleMain.customerInfo;
import static app.ConsoleMain.customerSize;

@Service
public class JokerService {

    private static HashMap<String, Double> customerInfoService = new HashMap<>();

    public ResponseModel getResponse(RequestModel requestModel) {

        customerInfoService.clear();
        customerInfo.clear();
        customerSize = 0;

        for ( Customer customer : requestModel.getCustomers() ) {

            String userName = customer.getName();
            String userCost = customer.getCost();

            double userCostDouble = Double.valueOf( userCost );

            customerInfo.put( userName, userCostDouble );

            customerSize++;

        }

        CalculatorUtil.calculateTotalDiscount();

        CalculatorUtil.calculateCustomerDiscount();

        ResultUtil.printResult();

        customerInfoService = (HashMap<String, Double>) customerInfo.clone();

        return new ResponseModel( customerInfoService );

    }

}
