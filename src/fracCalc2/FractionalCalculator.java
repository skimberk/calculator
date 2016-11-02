// Sebastian Kimberk

package fracCalc2;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// A class used to store fractions
class Fraction {
	// Regular expressions for identifying whether numbers are valid
	// and splits them into their parts (numerators and denominators)
	public static String fractionRegex = "^([+-]?)(\\d+)/(\\d+)$";
	public static String mixedNumberRegex = "^([+-]?)(\\d+)_(\\d+)/(\\d+)$";
	public static String integerRegex = "^([+-]?)(\\d+)$";
	
	private int numerator;
	private int denominator;

	// Parses a number in a string to identify the numerator and denominator
	public Fraction(String input) {
		// Parses a fraction
		if(input.matches(fractionRegex)) {
			Pattern pattern = Pattern.compile(fractionRegex);
			Matcher matcher = pattern.matcher(input);
			
			matcher.find();
			
			numerator = Integer.parseInt(matcher.group(2));
			denominator = Integer.parseInt(matcher.group(3));
			
			if(matcher.group(1).equals("-")) {
				numerator *= -1;
			}
		}
		// Parses a mixed number
		else if(input.matches(mixedNumberRegex)) {
			Pattern pattern = Pattern.compile(mixedNumberRegex);
			Matcher matcher = pattern.matcher(input);
			
			matcher.find();

			int wholeNumber = Integer.parseInt(matcher.group(2));
			denominator = Integer.parseInt(matcher.group(4));
			numerator = Integer.parseInt(matcher.group(3)) + wholeNumber * denominator;
			
			if(matcher.group(1).equals("-")) {
				numerator *= -1;
			}
		}
		// Parses an integer
		else if(input.matches(integerRegex)) {
			numerator = Integer.parseInt(input);
			denominator = 1;
		}
		// Throws an error if string matches nothing
		else {
			throw new RuntimeException("Invalid number: " + input);
		}
	}
	
	
	// Returns a string version of the fraction
	public String toString() {
		return numerator + "/" + denominator;
	}
}

public class FractionalCalculator {
	// A string containing all valid operators
	public static String operators = "+-*/";

	// Determines whether a string is a valid operator
	// Ex: +, -, *, /
	public static boolean isValidOperator(String input) {
		return operators.contains(input);
	}

	// Takes an expression string, checks for validity, and returns an ArrayList
	// of strings (operators) and Fractions
	public static ArrayList<Object> parseExpression(String input) {
		String[] split = input.split(" ");
		
		if(split.length < 3) {
			throw new RuntimeException("You need at least two numbers and an operator");
		}
		else if(split.length % 2 == 0) {
			throw new RuntimeException("You need an operator between every two numbers");
		}
		
		ArrayList<Object> parsed = new ArrayList<Object>();
		
		for(int i = 0; i < split.length; i++) {
			if(i % 2 == 0) {
				parsed.add(new Fraction(split[i]));
			}
			else if(i % 2 == 1) {
				if(isValidOperator(split[i])) {
					parsed.add(split[i]);
				}
				else {
					throw new RuntimeException("Invalid operator: " + split[i]);
				}
			}
		}
		
		return parsed;
	}
	
	// Creates a Fraction from a string
	// and returns the string version
	public static String convertToFraction(String input) {
		return new Fraction(input).toString();
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		
		System.out.println("Welcome to the Fraction Calculator!");
		
		// Loops until user types quit
		while(true) {
			System.out.print("Enter an expression (or \"quit\"): ");
			
			String input = in.nextLine();
			
			// Breaks the loop, and quits program
			if(input.equals("quit")) {
				break;
			}
			
			ArrayList<Object> parsedInput = parseExpression(input);
			
			// Currently we're not accepting multiple operators
			if(parsedInput.size() > 3) {
				throw new RuntimeException("You entered too many arguments");
			}
			
			System.out.println("Left operand: " + parsedInput.get(0));
			System.out.println("Operator: " + parsedInput.get(1));
			System.out.println("Right operand: " + parsedInput.get(2));
		}
		
		System.out.println("Goodbye!");
	}
}
