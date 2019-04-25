package app;

import helper.ConsoleReaderHelper;
import util.CalculatorUtil;
import util.ResultUtil;

import java.util.*;

public class Main {

    public static final int customerSize = ConsoleReaderHelper.getCustomerSize();
    public static final HashMap<String, Double> customerInfo = new HashMap<>();

    public static void main(String[] args) {

        if ( customerSize == 0 )
            System.out.println( "\nThe customer number is invalid:" + customerSize);

        ConsoleReaderHelper.getCustomerInfo();

        CalculatorUtil.calculateTotalDiscount();

        CalculatorUtil.calculateCustomerDiscount();

        ResultUtil.printResult();

    }

}
