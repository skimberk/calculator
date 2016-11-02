// Sebastian Kimberk

package fracCalc1;

import java.util.Scanner;

public class FractionalCalculator {
	// Regular expressions for identifying whether numbers are valid
	// and splits them into their parts (numerators and denominators)
	public static String fractionRegex = "^([+-]?)(\\d+)/(\\d+)$";
	public static String mixedNumberRegex = "^([+-]?)(\\d+)_(\\d+)/(\\d+)$";
	public static String integerRegex = "^([+-]?)(\\d+)$";
	
	// A string containing all valid operators
	public static String operators = "+-*/";

	// Determines whether a string is a valid number
	// Ex: 1/4, 1_1/4, 5
	public static boolean isValidNumber(String input) {
		return input.matches(fractionRegex) ||
			   input.matches(mixedNumberRegex) ||
			   input.matches(integerRegex);
	}
	
	// Determines whether a string is a valid operator
	// Ex: +, -, *, /
	public static boolean isValidOperator(String input) {
		return operators.contains(input);
	}

	// Takes an expression string, checks for validity, and returns an array of strings
	// with the individual parts of the expression
	public static String[] parseExpression(String input) {
		String[] split = input.split(" ");
		
		if(split.length < 3) {
			throw new RuntimeException("You need at least two numbers and an operator");
		}
		else if(split.length % 2 == 0) {
			throw new RuntimeException("You need an operator between every two numbers");
		}
		
		for(int i = 0; i < split.length; i++) {
			if(i % 2 == 0 && !isValidNumber(split[i])) {
				throw new RuntimeException("Invalid number: " + split[i]);
			}
			else if(i % 2 == 1 && !isValidOperator(split[i])) {
				throw new RuntimeException("Invalid operator: " + split[i]);
			}
		}
		
		return split;
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		
		System.out.println("Welcome to the Fraction Calculator!");
		System.out.print("Enter an expression (or \"quit\"): ");
		
		String[] parsedInput = parseExpression(in.nextLine());
		
		// Currently we're not accepting multiple operators
		if(parsedInput.length > 3) {
			throw new RuntimeException("You entered too many arguments");
		}
		
		System.out.println("Left operand: " + parsedInput[0]);
		System.out.println("Operator: " + parsedInput[1]);
		System.out.println("Right operand: " + parsedInput[2]);
		
		System.out.println("Goodbye!");
	}
}
