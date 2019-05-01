package util;

import helper.CalculatorHelper;

import static app.ConsoleMain.customerInfo;

public class ResultUtil {

    public static double roundedCost;

    public static void printResult() {

        roundedCost = 0.0;

        for (String name : customerInfo.keySet()) {

            System.out.println( "Name: " + name + "\t-\tAmount: " + customerInfo.get( name ) );
            roundedCost = roundedCost + customerInfo.get( name );

        }

        roundedCost = CalculatorHelper.round( roundedCost, 4 );

        System.out.println( "\nThe total rounded value is:\t" + roundedCost );

    }

}
