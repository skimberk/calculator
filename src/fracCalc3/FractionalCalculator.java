// Sebastian Kimberk

package fracCalc3;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//A class used to store fractions
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
		
		// Reduces fraction
		reduce();
	}
	
	// Returns a string version of the fraction
	public String toString() {
		return numerator + "/" + denominator;
	}
	
	// Returns the numerator of the fraction
	public int getNumerator() {
		return numerator;
	}
	
	// Returns the denominator of the fraction
	public int getDenominator() {
		return denominator;
	}
	
	// Finds the greatest common divisor given two integers
	private static int gcd(int a, int b) {
		if(b == 0) {
			return a;
		}
		
		return gcd(b, a % b);
	}
	
	// Reduces the fraction by dividing the numerator and denominator
	// by their gcd.
	private void reduce() {
		int gcd = Math.abs(gcd(numerator, denominator));
		
		numerator = numerator / gcd;
		denominator = denominator / gcd;
	}
	
	// Adds another fraction to this fraction.
	public void add(Fraction fraction) {
		numerator = numerator * fraction.getDenominator() + fraction.getNumerator() * denominator;
		denominator = denominator * fraction.getDenominator();
		
		// Reduces fraction
		reduce();
	}
	
	// Subtracts another fraction from this fraction.
	public void subtract(Fraction fraction) {
		numerator = numerator * fraction.getDenominator() - fraction.getNumerator() * denominator;
		denominator = denominator * fraction.getDenominator();
		
		// Reduces fraction
		reduce();
	}
	
	// Multiplies this fraction by another fraction.
	public void multiply(Fraction fraction) {
		numerator *= fraction.getNumerator();
		denominator *= fraction.getDenominator();
		
		// Reduces fraction
		reduce();
	}
	
	// Divides this fraction by another fraction.
	public void divide(Fraction fraction) {
		numerator *= fraction.getDenominator();
		denominator *= fraction.getNumerator();
		
		// Reduces fraction
		reduce();
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
	
	// Finds the result of using the given operator on the two
	// given fractions. Returns the string version of the result.
	public static String calculate(String left, String operator, String right) {
		Fraction leftFraction = new Fraction(left);
		Fraction rightFraction = new Fraction(right);

		if(operator.equals("+")) {
			leftFraction.add(rightFraction);
		}
		else if(operator.equals("-")) {
			leftFraction.subtract(rightFraction);
		}
		else if(operator.equals("*")) {
			leftFraction.multiply(rightFraction);
		}
		else if(operator.equals("/")) {
			leftFraction.divide(rightFraction);
		}
		
		return leftFraction.toString();
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
			
			System.out.println(calculate(parsedInput.get(0).toString(),
										 parsedInput.get(1).toString(),
										 parsedInput.get(2).toString()));
		}
		
		System.out.println("Goodbye!");
	}
}
