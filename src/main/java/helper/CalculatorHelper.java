package helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static app.ConsoleMain.customerInfo;
import static util.CalculatorUtil.errorMap;
import static util.CalculatorUtil.totalDiscountAmount;

public class CalculatorHelper {

    private static double[] roundMultiplier = { 1.0, 2.0, 4.0, 5.0, 10.0, 20.0 };
    private static int selector = 0;

    public static void getOptimal() {

        double totalRoundedAmount = 0;
        HashMap<String, Double> tempMap = new HashMap<>();

        for ( String name : customerInfo.keySet() ) {

            double value = customerInfo.get( name );
            double optimalValue;

            BigDecimal bigDecimal = new BigDecimal( value );

            optimalValue = ( Math.round( bigDecimal.doubleValue() * roundMultiplier[ selector ] ) / roundMultiplier[ selector ] );

            totalRoundedAmount = totalRoundedAmount + optimalValue;

            tempMap.put( name, optimalValue );

        }

        double error = Math.abs( totalDiscountAmount - totalRoundedAmount ) / totalDiscountAmount;

        double threshHold = 0.02;
        if ( !( error > threshHold) )
            errorMap.put( error, tempMap );

        selector++;

        if ( !( selector > roundMultiplier.length - 1 ) ) {
            getOptimal();
        }

        selector = 0;

    }

    public static double round(double value, int places) {

        if ( places < 0 )
            throw new IllegalArgumentException();

        BigDecimal bigDecimal = new BigDecimal( value );

        bigDecimal = bigDecimal.setScale( places + 1, RoundingMode.FLOOR ).setScale( places, RoundingMode.CEILING );

        return bigDecimal.doubleValue();

    }

    public static boolean inRange(int begin, double number, int end ) {

        return begin <= number && number < end;

    }

    static String[] removeArrayElementByIndex(String[] arr, int index) {

        List<String> list = new ArrayList<>(Arrays.asList(arr));

        list.remove( index );
        list.removeIf( Objects::isNull );

        arr = list.toArray( arr );

        return arr;

    }

}
