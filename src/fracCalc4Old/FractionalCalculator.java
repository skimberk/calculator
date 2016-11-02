/* _____     _               _____              _ _ _   
* |  ___|   | |             /  __ \            | (_) |  
* | |____  _| |_ _ __ __ _  | /  \/_ __ ___  __| |_| |_ 
* |  __\ \/ / __| '__/ _` | | |   | '__/ _ \/ _` | | __|
* | |___>  <| |_| | | (_| | | \__/\ | |  __/ (_| | | |_ 
* \____/_/\_\\__|_|  \__,_|  \____/_|  \___|\__,_|_|\__|
* 
✓ Handles errors robustly.
✓ Shows help content (when asked politely).
✓ Handles more than one expression per line.
✓ Uses correct order of operations. 
*
★ Does other cool stuff:
★ Approximates pi (just type in "pi").
*
*/

package fracCalc4Old;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Fraction {
	public static String fractionRegex = "^([+-]?)(\\d+)/(\\d+)$";
	public static String mixedNumberRegex = "^([+-]?)(\\d+)_(\\d+)/(\\d+)$";
	public static String integerRegex = "^([+-]?)(\\d+)$";
	
	private int numerator;
	private int denominator;

	public Fraction(String input) {
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
		else if(input.matches(integerRegex)) {
			numerator = Integer.parseInt(input);
			denominator = 1;
		}
		else {
			throw new RuntimeException("Invalid number: " + input);
		}
		
		reduce();
	}
	
	public String toString(boolean mixedNumber) {
		int fullNumber = numerator / denominator;
		int mixedNumerator = Math.abs(numerator % denominator);

		if(mixedNumber && fullNumber != 0) {
			if(mixedNumerator == 0) {
				return String.valueOf(fullNumber);
			}
			else {
				return fullNumber + "_" + mixedNumerator + "/" + denominator;
			}
		}
		else {
			if(denominator == 1) {
				return String.valueOf(numerator);
			}
			else {
				return numerator + "/" + denominator;
			}
		}
	}
	
	public String toString() {
		return this.toString(true);
	}
	
	public int getNumerator() {
		return numerator;
	}
	
	public int getDenominator() {
		return denominator;
	}
	
	private static int gcd(int a, int b) {
		if(b == 0) {
			return a;
		}
		
		return gcd(b, a % b);
	}
	
	private void reduce() {
		int gcd = Math.abs(gcd(numerator, denominator));
		
		numerator = numerator / gcd;
		denominator = denominator / gcd;
	}
	
	public void add(Fraction fraction) {
		numerator = numerator * fraction.getDenominator() + fraction.getNumerator() * denominator;
		denominator = denominator * fraction.getDenominator();
		
		reduce();
	}
	
	public void subtract(Fraction fraction) {
		numerator = numerator * fraction.getDenominator() - fraction.getNumerator() * denominator;
		denominator = denominator * fraction.getDenominator();
		
		reduce();
	}
	
	public void multiply(Fraction fraction) {
		numerator *= fraction.getNumerator();
		denominator *= fraction.getDenominator();
		
		reduce();
	}
	
	public void divide(Fraction fraction) {
		numerator *= fraction.getDenominator();
		denominator *= fraction.getNumerator();
		
		reduce();
	}
}

public class FractionalCalculator {
	public static boolean isValidOperator(String input) {
		return input.equals("+") ||
			   input.equals("-") ||
			   input.equals("*") ||
			   input.equals("/");
	}

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
	
	public static void calculateParsedInput(ArrayList<Object> input) {
		while(input.size() > 1) {
			int operatorIndex = input.indexOf("*");
			
			if(operatorIndex == -1) {
				operatorIndex = input.indexOf("/");
			}
			
			if(operatorIndex == -1) {
				operatorIndex = 1;
			}
			
			String operator = (String) input.get(operatorIndex);
			Fraction leftFraction = (Fraction) input.get(operatorIndex - 1);
			Fraction rightFraction = (Fraction) input.get(operatorIndex + 1);
			
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
			
			input.remove(operatorIndex + 1);
			input.remove(operatorIndex);
		}
	}
	
	public static double approximatePi() {
		int inside = 0;
		int total = 0;
		
		for(int i = 0; i < 10000000; i++) {
			double x = Math.random();
			double y = Math.random();
			
			if(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) <= 1.0) {
				inside++;
			}
			
			total++;
		}
		
		return 4.0 * inside / total;
	}
	
	public static String reduce(String fraction) {
		Fraction realFraction = new Fraction(fraction);
		
		return realFraction.getNumerator() + "/" + realFraction.getDenominator();
	}
	
	public static String convertToMixed(String fraction) {
		return new Fraction(fraction).toString();
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		
		System.out.println("Welcome to the Fraction Calculator!");
		
		while(true) {
			System.out.print("Enter an expression (or \"quit\"): ");
			
			String input = in.nextLine();
			
			if(input.equals("quit")) {
				break;
			}
			else if(input.equals("pi")) {
				System.out.println("Approximating pi (badly)...");
				System.out.println(approximatePi());
				
				continue;
			}
			else if(input.equals("help")) {
				System.out.println("This little application is a fractional calculator.");
				System.out.println("As you may have guessed, it handles basic math");
				System.out.println("involving fractions.");
				System.out.println();
				System.out.println("Valid operators: '+', '-', '*', and '/'.");
				System.out.println();
				System.out.println("Example calculations:");
				System.out.println("1/2 + 3/4");
				System.out.println("1_1/3 * 6");
				System.out.println("1 + 2/3 / -2/3");
				System.out.println("-3 * 2_1/7");
				System.out.println("2 + 3 * 4");

				continue;
			}
			
			ArrayList<Object> parsedInput;
			
			try {
				parsedInput = parseExpression(input);
			}
			catch(RuntimeException e) {
				System.out.println(e.getMessage());
				continue;
			}
			
			calculateParsedInput(parsedInput);
			
			System.out.println(parsedInput.get(0));
		}
		
		System.out.println("Goodbye!");
	}
}
