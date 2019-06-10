package com.calculator.joker.popup.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class generateJokerResultPage {

    private static final String PROJECT_DIR = System.getProperty("user.dir");
    private static final String SLASH = System.getProperty("file.separator");
    private static final String JOKER_RESULT_HTML_FILE = PROJECT_DIR + SLASH + "src" + SLASH + "main" + SLASH + "webapp" + SLASH + "jokerResult.html";

    public static Document parseHtmlFile(String jsonString) {

        JsonObject jsonObject = new JsonParser().parse( jsonString ).getAsJsonObject();

        Document htmlFile = null;

        String htmlString;

        try {

            htmlFile = Jsoup.parse( new File( JOKER_RESULT_HTML_FILE ),  "ISO-8859-1" );

        } catch (IOException e) {

            e.printStackTrace();
        }

        assert htmlFile != null;
        htmlString = htmlFile.toString();

        htmlString = htmlString.replace( "${totalActualCost}", jsonObject.get( "totalActualCost" ).getAsString() ).
                                replace( "${totalDiscountRatio}", jsonObject.get( "totalDiscountRatio" ).getAsString() ).
                                replace( "${totalDiscountedCost}", jsonObject.get( "totalDiscountedCost" ).getAsString() ).
                                replace( "${totalRoundedCost}", jsonObject.get( "totalRoundedCost" ).getAsString() );


        final String[] name = new String[1];
        final String[] cost = new String[1];

        StringBuilder tableContent = new StringBuilder();

        JsonArray jsonArray = jsonObject.getAsJsonArray( "customers" );


        jsonArray.forEach(

                jsonElement -> {

                    tableContent.append( "<tr>" );

                    name[ 0 ] = new JsonParser().parse( String.valueOf( jsonElement ) ).getAsJsonObject().
                            get( "name" ).toString();

                    cost[ 0 ] = new JsonParser().parse( String.valueOf( jsonElement ) ).getAsJsonObject().
                            get( "cost" ).toString();

                    tableContent.append( "<td>" ).append( name[ 0 ] ).append( "</td>" ).
                                append( "<td>" ).append( cost[ 0 ] ).append( "</td>" );

                    tableContent.append( "</tr>" );

                }

        );


        htmlString = htmlString.replace( "${tableContent}", tableContent.toString().replace( "\"", "" ) );

        htmlFile = Jsoup.parse( htmlString );

        return htmlFile;

    }

}
