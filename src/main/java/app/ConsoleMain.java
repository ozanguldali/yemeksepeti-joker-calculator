package app;

import helper.ConsoleReaderHelper;
import util.CalculatorUtil;
import util.ResultUtil;

import java.util.*;

public class ConsoleMain {

    public static int customerSize = 0;
    public static final HashMap<String, Double> customerInfo = new HashMap<>();

    public static void main(String[] args) {

        customerSize = ConsoleReaderHelper.getCustomerSize();

        if ( customerSize == 0 )
            System.out.println( "\nThe customer number is invalid:" + customerSize);

        ConsoleReaderHelper.getCustomerInfo();

        CalculatorUtil.calculateTotalDiscount();

        CalculatorUtil.calculateCustomerDiscount();

        ResultUtil.printResult();

    }

}
