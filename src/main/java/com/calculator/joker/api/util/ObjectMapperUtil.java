package com.calculator.joker.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.calculator.joker.api.model.ValidationErrorModel.errorMessage;

public class ObjectMapperUtil {

    public static String beautifyJsonToString(String json) {

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

    public static String beautifyFormToJson(String form) {

        String decoded;

        try {

            decoded = URLDecoder.decode(form, StandardCharsets.UTF_8.name());

        } catch (UnsupportedEncodingException e) {
            errorMessage = "Unsupported Encoding";
            return null;
        }

        assert decoded != null;
        String[] array = decoded.split( "&" );

        HashMap<Integer, HashMap<String, String>> hashMap = new HashMap<>();
        Object cloneMap = null;
        HashMap<String, String> tempHashMap = new HashMap<>();

        JsonObject jsonObject = new JsonObject();

        JsonArray customerArray = new JsonArray();

        JsonObject userObject = new JsonObject();

        for (String s : array) {

            String[] pair = s.split("=");

            StringBuilder valuePair = new StringBuilder();

            for ( int k = 1; k < pair.length; k++ )
                valuePair.append( pair[ k ] );

            String[] firstSplitArr = pair[ 0 ].split( "\\[" );
            String firstSplit = firstSplitArr[ firstSplitArr.length - 1 ];

            String key = firstSplitArr[ 0 ];

            String[] secondSplitArr = firstSplit.split( "]" );
            String secondSplit = secondSplitArr[ 0 ];

            tempHashMap.put( key, valuePair.toString() );
            if ( tempHashMap.keySet().size() % 2 == 0 ) {

                cloneMap = tempHashMap.clone();
                tempHashMap.clear();

            }

            hashMap.put( Integer.valueOf( secondSplit ), (HashMap<String, String>) cloneMap);


        }

        hashMap.forEach( (index, map) -> {

            userObject.addProperty( "name", hashMap.get( index ).get( "name" ) );
            userObject.addProperty( "cost", hashMap.get( index ).get( "cost" ) );

            HashMap tempMap = new HashMap<>();

            try {
                tempMap = new ObjectMapper().readValue( userObject.toString(), HashMap.class );
            } catch (IOException e) {
                e.printStackTrace();
            }

            Object tempObject = tempMap.clone();

            Gson gson = new Gson();
            String json = gson.toJson( tempObject );

            JsonObject tempJsonObject = new JsonParser().parse( json ).getAsJsonObject();

            customerArray.add( tempJsonObject );


        } );

        jsonObject.add( "customers", customerArray );

        String jsonFormatted = jsonObject.toString();

        userObject.remove("name");
        userObject.remove("cost");

        return jsonFormatted;

    }

    public static String beautifyJsonToForm(String json) {

        JsonObject jsonObject = new JsonParser().parse( json ).getAsJsonObject();

        StringBuilder form = new StringBuilder();

        form.append( "totalActualCost=" ).append( jsonObject.get("totalActualCost" ).toString() ).
                append( "&" ).
                append( "totalDiscountedCost=" ).append( jsonObject.get( "totalDiscountedCost" ).toString() ).
                append( "&" ).
                append( "totalDiscountRatio=" ).append( jsonObject.get( "totalDiscountRatio" ).toString() ).
                append( "&" ).
                append( "totalRoundedCost=" ).append( jsonObject.get( "totalRoundedCost" ).toString() );

        JsonArray jsonArray = jsonObject.get( "customers" ).getAsJsonArray();


        AtomicInteger iterator = new AtomicInteger();

        jsonArray.forEach(

                (

                        jsonElement -> {

                            String nameKey = "name[" + iterator + "]=";
                            String nameValue = new JsonParser().parse( String.valueOf( jsonElement ) ).getAsJsonObject().
                                    get( "name" ).toString();

                            String costKey = "cost[" + iterator + "]=";
                            String costValue = new JsonParser().parse( String.valueOf( jsonElement ) ).getAsJsonObject().
                                    get( "cost" ).toString();

                            form.append( "&" ).
                                    append( nameKey ).append( nameValue ).
                                    append( "&" ).
                                    append( costKey ).append( costValue );

                            iterator.set( iterator.get() + 1 );

                        }

                )

        );

        return form.toString().replace( "\"", "" );

    }

}
