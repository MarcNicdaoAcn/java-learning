package com.pluralsight.calcengine;

import java.time.LocalDate;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        double[] leftVals = { 100.0d, 25.0d, 225.0d, 11.0d };
        double[] rightVals = { 50.0d, 92.0d, 17.0d, 3.0d };
        char[] opCodes = { 'd', 'a', 's', 'm' };
        double[] results = new double[opCodes.length];

        if (args.length == 1 && args[0].equals("interactive")) {
            executeInteractively();
            return;
        }

        if (!(args.length < 1) && !(args.length == 3)) {
            System.out.println("Please enter a char code and 2 numbers");
            return;
        }

        if (args.length < 1) {
            for (int i = 0; i < opCodes.length; i++) {
                results[i] = run(opCodes[i], leftVals[i], rightVals[i]);
            }

            for (double number : results) {
                System.out.println(number);
            }
        }

        if (args.length == 3) {
            validateArgs(args);
        }

    }

    static void executeInteractively() {
        System.out.println("Enter an operation and two numbers:");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        String[] parts = userInput.split(" ");
        performOperation(parts);
        scanner.close();
    }

    private static void performOperation(String[] parts) {
        char opCode = opCodeFromString(parts[0]);
        if(opCode == 'w'){
            handleWhen(parts);
            return;
        }

        double leftVal = valueFromWord(parts[1]);
        double rightVal = valueFromWord(parts[2]);
        double result = run(opCode, leftVal, rightVal);
        displayResult(opCode, leftVal, rightVal, result);
    }

    private static void handleWhen(String[] parts) {
        LocalDate startDate = LocalDate.parse(parts[1]);
        long daysToAdd = (long) valueFromWord(parts[2]);
        LocalDate newDate = startDate.plusDays(daysToAdd);
        String output = String.format("%s plus %d days is %s", startDate, daysToAdd, newDate);
        System.out.println(output);
    }

    private static void displayResult(char opCode, double leftVal, double rightVal, double result) {
        char symbol = symbolFromOpCode(opCode);
        //equivalent of the string format using stringbuilder
      // StringBuilder builder = new StringBuilder(20);
      // builder.append(leftVal);
      // builder.append(" ");
      // builder.append(symbol);
      // builder.append(" ");
      // builder.append(rightVal);
      // builder.append(" = ");
      // builder.append(result);
      // String output = builder.toString();
      // System.out.println(output);

        String output = String.format("%.3f %c %.3f = %.3f", leftVal, symbol, rightVal, result);
        System.out.println(output);
    }

    private static char symbolFromOpCode(char opCode) {
        char[] opCodes = { 'a', 's', 'm', 'd' };
        char[] symbols = { '+', '-', '*', '/' };
        char symbol = ' ';
        for (int index = 0; index < opCodes.length; index++) {
            if (opCode == opCodes[index]) {
                symbol = symbols[index];
                break;
            }
        }
        return symbol;
    }

    private static void validateArgs(String[] args) {
        double leftVal, rightVal;
        char opCode;

        if (!(args[0] instanceof String) || args[0].length() > 1) {
            System.out.println("Op code must be one of the following: a, s, m, d");
            return;
        } else {
            opCode = args[0].charAt(0);
        }

        try {
            leftVal = Double.parseDouble(args[1]);
        } catch (Exception e) {
            System.out.println("Must input a valid number as second argument");
            return;
        }

        try {
            rightVal = Double.parseDouble(args[2]);
        } catch (Exception e) {
            System.out.println("Must input a valid number as third argument");
            return;
        }
        System.out.println(run(opCode, leftVal, rightVal));

    }

    public static double run(char opCode, double leftVal, double rightVal) {
        double result;
        switch (opCode) {
            case 'a':
                result = leftVal + rightVal;
                break;
            case 's':
                result = leftVal - rightVal;
                break;
            case 'm':
                result = leftVal * rightVal;
                break;
            case 'd':
                result = rightVal != 0 ? leftVal / rightVal : 0;
                break;
            default:
                System.out.println("opCode: " + opCode + " is not valid");
                result = 0.0d;
                break;
        }
        return result;
    }

    static char opCodeFromString(String operationName) {
        char opCode = operationName.charAt(0);
        return opCode;
    }

    static double valueFromWord(String word) {
        String[] numberWords = {
                "zero",
                "one",
                "two",
                "three",
                "four",
                "five",
                "six",
                "seven",
                "eight",
                "nine"
        };

        double value = -1d;

        for (int index = 0; index < numberWords.length; index++) {
            if (word.equals(numberWords[index])) {
                value = index;
                break;
            }
        }
        if(value == -1d){
            value = Double.parseDouble(word);
        }


        return value;
    }

}
