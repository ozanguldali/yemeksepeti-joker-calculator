package com.calculator.joker.controller;

import com.calculator.joker.model.ResponseModel;
import com.calculator.joker.model.ValidationErrorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.calculator.joker.service.JokerService;

import javax.servlet.http.HttpServletRequest;

import static util.LoggerUtil.logger;

import static com.calculator.joker.model.ValidationErrorModel.errorMessage;
import static com.calculator.joker.util.ObjectMapperUtil.beautify;

@RestController
public class RequestController {

    private final static String VALID_HTTP_METHOD = "POST";
    private final static String VALID_CONTENT_TYPE = "application/json";

    @Autowired
    JokerService jokerService;

    @RequestMapping(method = RequestMethod.POST, path = "/jokerDiscountCalculator")
    public ResponseEntity<String> index(HttpServletRequest request, HttpEntity<String> httpEntity,
                                        @RequestHeader(value = HttpHeaders.CONTENT_TYPE, defaultValue = VALID_CONTENT_TYPE) String contentType) {

        errorMessage = null;

        final String payload = httpEntity.getBody();
        final String httpResponseBody;
        String temp_httpResponseBody;

        HttpHeaders headersRequest = new HttpHeaders();

        httpEntity.getHeaders().forEach(
                ( name, value ) -> headersRequest.set( name, String.valueOf( value ) )
        );

        headersRequest.set( HttpHeaders.CONTENT_TYPE, VALID_CONTENT_TYPE );

        logger.trace( "\n|<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<|\n" );
        logger.trace( String.format(
                    "Got a request with\n" +
                        "Method:\t'{ %s }'\n" +
                        "Headers:\t'{ %s }'\n" +
                        "Body:\t' %s '\n",
                request.getMethod(), headersRequest, beautify( payload ) )
        );
        logger.trace( "\n|<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<|\n" );

        if ( !request.getMethod().equals( VALID_HTTP_METHOD ) || payload == null) {

            logger.error( "Invalid request with payload { " + payload + " }" );
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

        HttpHeaders headersResponse = new HttpHeaders();
        headersResponse.set( HttpHeaders.CONTENT_TYPE, VALID_CONTENT_TYPE );

        logger.trace( "\n|>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|\n" );
        logger.trace( String.format(
                    "Response of the request ' %s ':\n" +
                        "Headers:\t'{ %s }'\n" +
                        "Body:\t' %s '\n",
                beautify( payload ), headersResponse, httpResponseBody )
        );
        logger.trace( "\n|>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|\n" );

        return new ResponseEntity<>(httpResponseBody, headersResponse, HttpStatus.OK);

    }

}
