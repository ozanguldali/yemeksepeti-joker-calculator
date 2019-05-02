package com.calculator.joker.controller;

import com.calculator.joker.model.ResponseModel;
import com.calculator.joker.model.ValidationErrorModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.calculator.joker.service.JokerService;

import javax.servlet.http.HttpServletRequest;

import static com.calculator.joker.model.ValidationErrorModel.errorMessage;

@RestController
public class RequestController {

    private final static String VALID_HTTP_METHOD = "POST";
    private final static String VALID_CONTENT_TYPE = "application/json";

    public static Logger logger = LoggerFactory.getLogger( RequestController.class );

    @Autowired
    JokerService jokerService;

    @RequestMapping(method = RequestMethod.POST, path = "/jokerDiscountCalculator")
    public ResponseEntity<String> index(HttpServletRequest request, HttpEntity<String> httpEntity,
                                        @RequestHeader(value = HttpHeaders.CONTENT_TYPE, defaultValue = VALID_CONTENT_TYPE,
                                                required = true) String contentType) {

        errorMessage = null;

        final String payload = httpEntity.getBody();
        final String httpResponseBody;
        String temp_httpResponseBody;

        logger.trace( "Got a request with http method '{}' with header '{}' and body:\n'{}'\n",
                request.getMethod(), contentType, payload );

        if ( !request.getMethod().equals( VALID_HTTP_METHOD ) || payload == null) {

            logger.error("Invalid request with payload {}", payload);
            return new ResponseEntity<>( "Invalid Request", HttpStatus.METHOD_NOT_ALLOWED );

        }

        if ( !contentType.replace(" ", "").toLowerCase().trim().contains( VALID_CONTENT_TYPE ) )
            return new ResponseEntity<>("Unsupported Media Type", HttpStatus.UNSUPPORTED_MEDIA_TYPE);


        final ResponseModel validResponse = jokerService.getResponse( payload );
        temp_httpResponseBody = validResponse.toString();

        if ( errorMessage != null ) {

            final ValidationErrorModel failedResponse = jokerService.getError();
            temp_httpResponseBody = failedResponse.toString();

        }

        httpResponseBody = temp_httpResponseBody;
        logger.trace( "Response: \n'{}'", httpResponseBody );

        HttpHeaders headers = new HttpHeaders();
        headers.set( HttpHeaders.CONTENT_TYPE, VALID_CONTENT_TYPE );

        return new ResponseEntity<>(httpResponseBody, headers, HttpStatus.OK);

    }

}
