package com.calculator.joker.controller;

import com.calculator.joker.model.RequestModel;
import com.calculator.joker.model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.calculator.joker.service.JokerService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {

    @Autowired
    JokerService jokerService;

    @RequestMapping(method = RequestMethod.POST, path = "/jokerDiscountCalculator")
    public ResponseModel response(@RequestBody RequestModel requestModel) {

        return jokerService.getResponse( requestModel );

    }

}
