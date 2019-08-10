package com.calculator.joker.api.controller;

import com.calculator.joker.api.model.ResponseModel;
import com.calculator.joker.api.model.ValidationErrorModel;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.calculator.joker.api.service.JokerService;

import javax.servlet.http.HttpServletRequest;

import static com.calculator.joker.api.util.ObjectMapperUtil.*;
import static com.calculator.joker.popup.model.HtmlParser.parseErrorPage;
import static com.calculator.joker.popup.model.HtmlParser.parseResultPage;

import static com.calculator.joker.api.model.ValidationErrorModel.errorMessage;
import static util.LoggerUtil.*;

@RestController
public class RequestController {

    private final static String VALID_HTTP_METHOD = "POST";
    private final static String APPLICATION_JSON = "application/json";
    private final static String FORM_POST = "application/x-www-form-urlencoded";
    private final static String TEXT_HTML = "text/html";

    private final
    JokerService jokerService;

    public RequestController(JokerService jokerService) {
        this.jokerService = jokerService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/jokerDiscountCalculator")
    public ResponseEntity<String> indexJson(HttpServletRequest request, HttpEntity<String> httpEntity,
                                        @RequestHeader(value = HttpHeaders.CONTENT_TYPE, defaultValue = APPLICATION_JSON) String contentType) {

        errorMessage = null;

        final String payload = httpEntity.getBody();
        final String httpResponseBody;
        String temp_httpResponseBody;

        HttpHeaders headersRequest = new HttpHeaders();

        httpEntity.getHeaders().forEach(
                ( name, value ) -> headersRequest.set( name, String.valueOf( value ) )
        );

        headersRequest.set( HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

        restRequestLogger( request.getMethod(), headersRequest, beautifyJsonToString( payload ) );

        if ( !request.getMethod().equals( VALID_HTTP_METHOD ) || payload == null) {

            logger.error( "Invalid request with payload { " + payload + " }" );
            return new ResponseEntity<>( "Invalid Request", HttpStatus.METHOD_NOT_ALLOWED );

        }

        if ( !contentType.replace(" ", "").toLowerCase().trim().contains(APPLICATION_JSON) )
            return new ResponseEntity<>("Unsupported Media Type", HttpStatus.UNSUPPORTED_MEDIA_TYPE);


        return testAimed(payload);

    }

    public static ResponseEntity<String> testAimed(String payload) {
        final String httpResponseBody;

        String temp_httpResponseBody;

        final ResponseModel validResponse = JokerService.getResponse( payload );
        temp_httpResponseBody = validResponse.toString();

        if ( errorMessage != null ) {

            final ValidationErrorModel failedResponse = JokerService.getError();
            temp_httpResponseBody = failedResponse.toString();

        }

        httpResponseBody = temp_httpResponseBody;

        HttpHeaders headersResponse = new HttpHeaders();
        headersResponse.set( HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

        restResponseLogger( beautifyJsonToString( payload ), headersResponse, httpResponseBody );

        return new ResponseEntity<>(httpResponseBody, headersResponse, HttpStatus.OK);

    }


    @RequestMapping(method = RequestMethod.POST, path = "/jokerExtension")
    public ResponseEntity<String> indexFormPost(HttpServletRequest request, HttpEntity<String> httpEntity,
                                        @RequestHeader(value = HttpHeaders.CONTENT_TYPE, defaultValue = FORM_POST) String contentType) {

        errorMessage = null;

        final String payload = httpEntity.getBody();
        String temp_httpResponseBody;

        HttpHeaders headersRequest = new HttpHeaders();

        httpEntity.getHeaders().forEach(
                ( name, value ) -> headersRequest.set( name, String.valueOf( value ) )
        );

        headersRequest.set( HttpHeaders.CONTENT_TYPE, FORM_POST);
        String jsonFormattedPayload = beautifyFormToJson( payload );

        restRequestLogger( request.getMethod(), headersRequest, beautifyJsonToString( jsonFormattedPayload ) );

        if ( !request.getMethod().equals( VALID_HTTP_METHOD ) || jsonFormattedPayload == null) {

            logger.error( "Invalid request with payload { " + jsonFormattedPayload + " }" );
            return new ResponseEntity<>( "Invalid Request", HttpStatus.METHOD_NOT_ALLOWED );

        }

        if ( !contentType.replace(" ", "").toLowerCase().trim().contains(FORM_POST) ){
            logger.error( "Unsupported Media Type: " + contentType );
            return new ResponseEntity<>("Unsupported Media Type", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }


        final ResponseModel validResponse = JokerService.getResponse( jsonFormattedPayload );
        temp_httpResponseBody = validResponse.toString();

        if ( errorMessage != null ) {

            final ValidationErrorModel failedResponse = jokerService.getError();
            temp_httpResponseBody = failedResponse.toString();

        }

        Document htmlResponseBody = errorMessage == null ? parseResultPage( temp_httpResponseBody ) : parseErrorPage();

        HttpHeaders headersResponse = new HttpHeaders();
        headersResponse.set( HttpHeaders.CONTENT_TYPE, TEXT_HTML);

        restResponseLogger( beautifyJsonToString( jsonFormattedPayload ), headersResponse, htmlResponseBody.toString() );

        return new ResponseEntity<>(htmlResponseBody.toString(), headersResponse, HttpStatus.OK);

    }

}
