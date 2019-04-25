package util;

import helper.CalculatorHelper;

import static app.Main.customerInfo;

public class ResultUtil {

    public static void printResult() {

        double total = 0.0;

        for (String name : customerInfo.keySet()) {

            System.out.println( "Name: " + name + "\t-\tAmount: " + customerInfo.get(name) );
            total = total + customerInfo.get( name );

        }

        System.out.println( "\nThe total rounded value is:\t" + CalculatorHelper.round( total, 4 ) );

    }

}
