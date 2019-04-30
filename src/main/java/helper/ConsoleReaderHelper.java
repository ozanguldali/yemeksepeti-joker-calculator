package helper;

import java.util.Arrays;
import java.util.Scanner;

import static app.ConsoleMain.customerSize;
import static app.ConsoleMain.customerInfo;
import static helper.CalculatorHelper.removeArrayElementByIndex;
import static util.ResultUtil.printResult;

public class ConsoleReaderHelper {

    public static int getCustomerSize() {

        int customerSize = 0;

        System.out.print( "\nPlease enter the number of customers:\t" );

        try {

            Scanner scanner = new Scanner( System.in );

            customerSize = Integer.parseInt( scanner.nextLine().trim() );

        } catch (Exception e) {

            e.printStackTrace();
            System.exit( 0 );

        }

        if ( !( customerSize > 0 ) ) {

            System.out.println( "\nThe entered customer size is not valid, must be greater than zero!!" );

            getCustomerSize();

        }

        return customerSize;

    }

    public static void getCustomerInfo() {

        String row = "";

        System.out.print( "\nPlease enter the name and food price of customers:\n" +
                "Ex: Kubra Ozcan 17.50\n");

        Scanner scanner = new Scanner( System.in );

        for ( int i = 0; i < customerSize; i++ ) {

            try {

                row = scanner.nextLine();

            } catch (Exception e) {

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
                    name.append(" ").append( customerInfoArray[ j ] );

                if ( customerInfo.containsKey( name.toString() ) )
                    name.append( "_" ).append( i );

                customerInfo.put( name.toString(), price );

            }

        }

        printResult();

    }

}
