package util;

import java.util.Collections;
import java.util.HashMap;

import static app.Main.customerInfo;
import static helper.CalculatorHelper.*;

public class CalculatorUtil {

    public static HashMap<Double, HashMap<String, Double>> errorMap = new HashMap<>();

    private static double totalAmount = 0;
    public static double totalDiscountAmount = 0;
    private static double discountPercent = 0;

    private static final int firstLevel = 30;
    private static final int firstDiscount = 10;
    private static final int secondLevel = 40;
    private static final int secondDiscount = 15;
    private static final int thirdLevel = 70;
    private static final int thirdDiscount = 25;
    private static final int fourthLevel = 120;
    private static final int fourthDiscount = 45;

    public static void calculateTotalDiscount() {

        for ( String name : customerInfo.keySet() ) {

            totalAmount = totalAmount + customerInfo.get( name );

        }

        System.out.println( "\nThe total amount is:\t" + totalAmount );

        if ( inRange( firstLevel, totalAmount, secondLevel ) ) {

            totalDiscountAmount = totalAmount - firstDiscount;

            System.out.println( "The amount of discount is\t" + firstDiscount );
            System.out.println( "The updated joker amount is\t" + totalDiscountAmount );

        } else if ( inRange( secondLevel, totalAmount, thirdLevel ) ) {

            totalDiscountAmount = totalAmount - secondDiscount;

            System.out.println( "The amount of discount is\t" + secondDiscount );
            System.out.println( "The updated joker amount is\t" + totalDiscountAmount );

        } else if ( inRange( thirdLevel, totalAmount, fourthLevel ) ) {

            totalDiscountAmount = totalAmount - thirdDiscount;

            System.out.println( "The amount of discount is\t" + thirdDiscount );
            System.out.println( "The updated joker amount is\t" + totalDiscountAmount );

        } else if ( fourthLevel <= totalAmount ) {

            totalDiscountAmount = totalAmount - fourthDiscount;

            System.out.println( "The amount of discount is\t" + fourthDiscount );
            System.out.println( "The updated joker amount is\t" + totalDiscountAmount );

        } else {

            totalDiscountAmount = totalAmount;

            System.out.println( "You are NOT RICH to deserve a discount..." );
            System.out.println( "The total amount is:\t" + totalDiscountAmount );

        }

        discountPercent = ( totalAmount - totalDiscountAmount ) / totalAmount;

        System.out.println( "The percentage of discount which will be applied to each customer is: " +
                discountPercent * 100 + " %\n" );

    }

    public static void calculateCustomerDiscount() {

        customerInfo.forEach(
                ( name, amount ) -> {

            amount = amount - ( amount * discountPercent );
            amount = round( amount, 2 );

            customerInfo.put( name, amount );

        }
        );

        getOptimal();

        HashMap<String, Double> tempMap;

        tempMap = errorMap.get( Collections.min( errorMap.keySet() ) );

        for ( String name : customerInfo.keySet() )
            customerInfo.put( name, tempMap.get( name ) );

    }

}
