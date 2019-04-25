import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Main {

//    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat( "##.##" );

    private static double[] roundMultiplier = { 1.0, 2.0, 4.0, 5.0, 10.0, 20.0, 50.0, 100.0 };
    private static int selector = 0;

    private static HashMap<Double, HashMap<String, Double>> errorMap = new HashMap<>();

    private static final int firstLevel = 30;
    private static final int firstDiscount = 10;
    private static final int secondLevel = 40;
    private static final int secondDiscount = 15;
    private static final int thirdLevel = 70;
    private static final int thirdDiscount = 25;
    private static final int fourthLevel = 120;
    private static final int fourthDiscount = 45;

    private static final int customerSize = getCustomerSize();
    private static final HashMap<String, Double> customerInfo = new HashMap<>();

    private static double totalAmount = 0;
    private static double totalDiscountAmount = 0;
    private static double discountPercent = 0;

    public static void main(String[] args) {

        if ( customerSize == 0 )
            System.out.println( "\nThe customer number is invalid:" + customerSize);

        getCustomerInfo();

        calculateTotalDiscount();

        calculateCustomerDiscount();

        printResult();

    }

    private static int getCustomerSize() {

        int customerSize = 0;

        System.out.print( "\nPlease enter the number of customers:\t" );

        try {
            Scanner scanner = new Scanner( System.in );
            customerSize = Integer.parseInt( scanner.nextLine() );

        } catch ( Exception e ) {

            e.printStackTrace();
            System.exit( 0 );

        }

        if ( !( customerSize > 0 ) ) {

            System.out.println( "\nThe entered customer size is not valid, must be greater than zero!!" );

            getCustomerSize();

        }

        return customerSize;

    }

    private static void getCustomerInfo() {

        String row = "";

        System.out.print( "\nPlease enter the name and food price of customers:\n" +
                "Ex: Kubra Ozcan 17.50\n");

        Scanner scanner = new Scanner( System.in );

        for (int i = 0; i < customerSize; i++ ) {

            try {

                row = scanner.nextLine();

            } catch ( Exception e ) {

                e.printStackTrace();

            }

            row = row.trim();

            if ( row.equals( "" ) ) {

                System.out.println( "\nThe entered row is not valid, the length is zero!!" );

                i--;

            } else {

                String[] customerInfoArray = row.split( " " );

                int customerInfoArrayLength = customerInfoArray.length;

                if ( customerInfoArrayLength < 2 ) {

                    System.out.println( "\nThe info is missing\n" );
                    System.exit( 0 );

                } else if ( customerInfoArrayLength > 5 ) {

                    String[] tempArray = new String[ 3 ];

                    System.out.println( "\nThe info is too long\n" );

                    customerInfoArray[ 2 ] = customerInfoArray[ customerInfoArrayLength - 1 ];

                    for ( int k = 3; k < customerInfoArrayLength; k++ )
                        tempArray = removeArrayElementByIndex( customerInfoArray, 3 );

                    customerInfoArray = Arrays.copyOfRange( tempArray, 0, 3 );

                    customerInfoArrayLength = customerInfoArray.length;

                }

                double price = 0;
                StringBuilder name = new StringBuilder();

                try {

                    price = Double.valueOf( customerInfoArray[ customerInfoArrayLength - 1 ] );

                } catch (Exception e) {

                    System.out.println( "\nThe entered price amount is not double" );
                    e.printStackTrace();
                    System.exit( 0 );

                }

                if ( !( price > 0 ) ) {

                    System.out.println( "\nThe entered price amount is not valid, must be greater than zero!!" );
                    System.exit( 0 );

                }

                for ( int j = 0; j < customerInfoArrayLength - 1; j++ )
                    name.append(" ").append(customerInfoArray[ j ]);

                if ( customerInfo.containsKey( name.toString() ) )
                    name.append( "_" ).append( i );

                customerInfo.put( name.toString(), price );

            }

        }

    }

    private static void calculateTotalDiscount() {

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

    private static void calculateCustomerDiscount() {

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

    private static void getOptimal() {

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

    }

    private static double round(double value, int places) {

        if ( places < 0 )
            throw new IllegalArgumentException();

        BigDecimal bigDecimal = new BigDecimal( value );

        bigDecimal = bigDecimal.setScale( places + 1, RoundingMode.FLOOR ).setScale( places, RoundingMode.CEILING );

        return bigDecimal.doubleValue();

    }

    private static boolean inRange( int begin, double number, int end ) {

        return begin <= number && number < end;

    }

    private static String[] removeArrayElementByIndex(String[] arr, int index) {

        List<String> list = new ArrayList<>(Arrays.asList(arr));

        list.remove( index );
        list.removeIf( Objects::isNull );

        arr = list.toArray( arr );

        return arr;

    }

    private static void printResult() {

        double total = 0.0;

        for (String name : customerInfo.keySet()) {

            System.out.println( "Name: " + name + "\t-\tAmount: " + customerInfo.get(name) );
            total = total + customerInfo.get( name );

        }

        System.out.println( "\nThe total rounded value is:\t" + round( total, 4 ) );

    }

}
