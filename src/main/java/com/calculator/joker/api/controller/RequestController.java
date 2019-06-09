package com.calculator.joker.api.controller;

import com.calculator.joker.api.model.ResponseModel;
import com.calculator.joker.api.model.ValidationErrorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.calculator.joker.api.service.JokerService;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.calculator.joker.api.util.ObjectMapperUtil.*;
import static com.calculator.joker.popup.model.generateJokerResultPage.parseHtmlFile;
import static util.LoggerUtil.logger;

import static com.calculator.joker.api.model.ValidationErrorModel.errorMessage;

@RestController
public class RequestController {

    private final static String VALID_HTTP_METHOD = "POST";
    private final static String APPLICATION_JSON = "application/json";
    private final static String FORM_POST = "application/x-www-form-urlencoded";

    @Autowired
    JokerService jokerService;

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

        logger.trace( "\n|<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<|\n" );
        logger.trace( String.format(
                    "Got a request with\n" +
                        "Method:\t'{ %s }'\n" +
                        "Headers:\t'{ %s }'\n" +
                        "Body:\t' %s '\n",
                request.getMethod(), headersRequest, beautifyJsonToString( payload ) )
        );
        logger.trace( "\n|<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<|\n" );

        if ( !request.getMethod().equals( VALID_HTTP_METHOD ) || payload == null) {

            logger.error( "Invalid request with payload { " + payload + " }" );
            return new ResponseEntity<>( "Invalid Request", HttpStatus.METHOD_NOT_ALLOWED );

        }

        if ( !contentType.replace(" ", "").toLowerCase().trim().contains(APPLICATION_JSON) )
            return new ResponseEntity<>("Unsupported Media Type", HttpStatus.UNSUPPORTED_MEDIA_TYPE);


        final ResponseModel validResponse = jokerService.getResponse( payload );
        temp_httpResponseBody = validResponse.toString();

        if ( errorMessage != null ) {

            final ValidationErrorModel failedResponse = jokerService.getError();
            temp_httpResponseBody = failedResponse.toString();

        }

        httpResponseBody = temp_httpResponseBody;

        HttpHeaders headersResponse = new HttpHeaders();
        headersResponse.set( HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

        logger.trace( "\n|>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|\n" );
        logger.trace( String.format(
                    "Response of the request ' %s ':\n" +
                        "Headers:\t'{ %s }'\n" +
                        "Body:\t' %s '\n",
                beautifyJsonToString( payload ), headersResponse, httpResponseBody )
        );
        logger.trace( "\n|>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|\n" );

        return new ResponseEntity<>(httpResponseBody, headersResponse, HttpStatus.OK);

    }


    @RequestMapping(method = RequestMethod.POST, path = "/jokerExtension")
    public void indexFormPost(HttpServletRequest request, HttpEntity<String> httpEntity,
                                        @RequestHeader(value = HttpHeaders.CONTENT_TYPE, defaultValue = FORM_POST) String contentType) {

        errorMessage = null;

        final String payload = httpEntity.getBody();
        final String httpResponseBody;
        String temp_httpResponseBody;

        HttpHeaders headersRequest = new HttpHeaders();

        httpEntity.getHeaders().forEach(
                ( name, value ) -> headersRequest.set( name, String.valueOf( value ) )
        );

        headersRequest.set( HttpHeaders.CONTENT_TYPE, FORM_POST);
        String jsonFormattedPayload = beautifyFormToJson( payload );

        logger.trace( "\n|<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<|\n" );
        logger.trace( String.format(
                "Got a request with\n" +
                        "Method:\t'{ %s }'\n" +
                        "Headers:\t'{ %s }'\n" +
                        "Body:\t' %s '\n",
                request.getMethod(), headersRequest, beautifyJsonToString( jsonFormattedPayload ) )
        );
        logger.trace( "\n|<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<|\n" );


        if ( !request.getMethod().equals( VALID_HTTP_METHOD ) || jsonFormattedPayload == null) {

            logger.error( "Invalid request with payload { " + jsonFormattedPayload + " }" );
//            return new ResponseEntity<>( "Invalid Request", HttpStatus.METHOD_NOT_ALLOWED );

        }

        if ( !contentType.replace(" ", "").toLowerCase().trim().contains(FORM_POST) ){
            logger.error( "Unsupported Media Type: " + contentType );
//            return new ResponseEntity<>("Unsupported Media Type", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }


        final ResponseModel validResponse = jokerService.getResponse( jsonFormattedPayload );
        temp_httpResponseBody = validResponse.toString();

        if ( errorMessage != null ) {

            final ValidationErrorModel failedResponse = jokerService.getError();
            temp_httpResponseBody = failedResponse.toString();

        }

        parseHtmlFile( temp_httpResponseBody );

        httpResponseBody = beautifyJsonToForm( temp_httpResponseBody );

        String encodedHttpResponseBody = null;

        try {

            encodedHttpResponseBody = URLEncoder.encode(httpResponseBody, StandardCharsets.UTF_8.name());

        } catch (UnsupportedEncodingException e) {
            errorMessage = "Unsupported Encoding";
//            return null;
        }

        HttpHeaders headersResponse = new HttpHeaders();
        headersResponse.set( HttpHeaders.CONTENT_TYPE, FORM_POST);

        logger.trace( "\n|>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|\n" );
        logger.trace( String.format(
                "Response of the request ' %s ':\n" +
                        "Headers:\t'{ %s }'\n" +
                        "Body:\t' %s '\n",
                beautifyJsonToString( jsonFormattedPayload ), headersResponse, httpResponseBody )
        );
        logger.trace( "\n|>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|\n" );

        MediaType mediaType = MediaType.parse( FORM_POST );

        assert encodedHttpResponseBody != null;
        RequestBody requestBody = RequestBody.create(mediaType, encodedHttpResponseBody);

        Request.Builder requestBuilder = new Request.Builder()
                .post( requestBody )
                .addHeader( "Cache-Control", "no-cache" );

        requestBuilder.url( request.getRequestURL().toString() );

        requestBuilder.build();

//        return new ResponseEntity<>(encodedHttpResponseBody, headersResponse, HttpStatus.OK);

    }

}
