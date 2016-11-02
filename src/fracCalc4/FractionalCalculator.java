// Sebastian Kimberk. Extra credit on the next bunch of lines (in case they got hidden, expand them)
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
★ Correctly handles parentheses.
★ Can do integer exponents.
★ Works without expressions being spaced out.
★ Approximates pi (just type in "pi").
★ Stores the last answer, which can be accessed with 'ans'.
*
*/

package fracCalc4;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

//A class used to store fractions
class Fraction {
	// A regular expression used to identify valid integers.
	public static String integerRegex = "^([-]?)(\\d+)$";
	
	private int numerator;
	private int denominator;

	// Constructs the Fraction based off a given integer (in a string)
	public Fraction(String input) {
		// The input is a valid integer
		if(input.matches(integerRegex)) {
			numerator = Integer.parseInt(input);
			denominator = 1;
		}
		// The input is not a valid integer
		else {
			throw new RuntimeException("Invalid number: " + input);
		}
		
		// Reduce fraction
		reduce();
	}
	
	// Return the fraction as a string. It can either be
	// returned as a mixed number or a fraction.
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
			if(denominator == 1 && mixedNumber) {
				return String.valueOf(numerator);
			}
			else {
				return numerator + "/" + denominator;
			}
		}
	}
	
	// Returns the fraction as a string, which takes the
	// form of a mixed number.
	public String toString() {
		return this.toString(true);
	}
	
	// Returns the fraction's numerator
	public int getNumerator() {
		return numerator;
	}
	
	// Returns the fraction's denominator
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
		
		numerator /= gcd;
		denominator /= gcd;
		
		if(denominator < 0) {
			numerator *= -1;
			denominator *= -1;
		}
	}
	
	// Given another fraction the method operates on this fraction
	// with the given fraction and operator.
	public void doOperation(String operator, Fraction fraction) {
		// Adds given fraction
		if(operator.equals("+")) {
			numerator = numerator * fraction.getDenominator() + fraction.getNumerator() * denominator;
			denominator = denominator * fraction.getDenominator();
		}
		// Subtracts given fraction
		else if(operator.equals("-")) {
			numerator = numerator * fraction.getDenominator() - fraction.getNumerator() * denominator;
			denominator = denominator * fraction.getDenominator();
		}
		// Multiplies by given fraction
		else if(operator.equals("*")) {
			numerator *= fraction.getNumerator();
			denominator *= fraction.getDenominator();
		}
		// Divides by given fraction
		else if(operator.equals("/")) {
			numerator *= fraction.getDenominator();
			denominator *= fraction.getNumerator();
		}
		// Exponentiates by given fraction
		else if(operator.equals("^")) {
			if(fraction.getDenominator() == 1) {
				numerator = (int) Math.pow(numerator, Math.abs(fraction.getNumerator()));
				denominator = (int) Math.pow(denominator, Math.abs(fraction.getNumerator()));
				
				if(fraction.getNumerator() < 0) {
					int temp = numerator;
					
					numerator = denominator;
					denominator = temp;
				}
			}
		}
		// Invalid operator
		else {
			throw new RuntimeException("Invalid operator: " + operator);
		}
		
		// Reduces fraction
		reduce();
	}
}

// A class which represents the associativity and
// precedence of operators.
class Operator {
	public static final int LEFT_ASSOC = 0;
	public static final int RIGHT_ASSOC = 1;
	
	private int precedence;
	private int associativity;
	
	// Constructs class given precedence and associativity.
	public Operator(int precedence, int associativity) {
		this.precedence = precedence;
		this.associativity = associativity;
	}
	
	// Returns precedence
	public int getPrecendence() {
		return precedence;
	}
	
	// Returns associativity
	public int getAssociativity() {
		return associativity;
	}
	
	// Checks whether the given operator has lower
	// precedence than this operator.
	public boolean isInferiorTo(Operator operator) {
		if(operator.getAssociativity() == RIGHT_ASSOC) {
			return precedence < operator.getPrecendence();
		}
		else {
			return precedence <= operator.getPrecendence();
		}
	}
}

// Class parse expressions and execute them
class Calculate {
	// This map should contain all allowed operators.
	private static Map<String, Operator> operators = new HashMap<String, Operator>();
	static {
		operators.put("+", new Operator(1, Operator.LEFT_ASSOC));
		operators.put("-", new Operator(1, Operator.LEFT_ASSOC));
		
		operators.put("*", new Operator(2, Operator.LEFT_ASSOC));
		operators.put("/", new Operator(2, Operator.LEFT_ASSOC));

		operators.put("^", new Operator(3, Operator.RIGHT_ASSOC));
	}
	
	// Creates a Regular Expression for matching operators
	private static String operatorsRegex = "\\(|\\)|";
	static {
		for(String operator : operators.keySet()) {
			operatorsRegex += "\\" + operator + "|";
		}
		
		operatorsRegex = operatorsRegex.substring(0, operatorsRegex.length() - 1);
	}

	// Converts infix expressions to postfix expressions
	// which are easier to evaluate. This method uses
	// the Shunting Yard algorithm.
	public static String infixToPostfix(String infix) {
		Stack<String> stack = new Stack<>();
		String postfix = "";
		
		String[] tokens = infix.split(" ");
		
		for(String token : tokens) {
			if(operators.containsKey(token)) {
				// This token is an operator
				while(!stack.empty() && operators.containsKey(stack.peek()) &&
					  operators.get(token).isInferiorTo(operators.get(stack.peek()))) {
					
					if(stack.peek().equals("(")) {
						throw new RuntimeException("Infix contains mismatched parentheses");
					}

					postfix += stack.pop() + " ";
				}
				
				stack.push(token);
			}
			else if(token.equals("(")) {
				// This token is a left parenthesis
				stack.push(token);
			}
			else if(token.equals(")")) {
				// This token is a right parenthesis
				boolean foundLeftParenthesis = false;

				while(!stack.empty()) {
					if(stack.peek().equals("(")) {
						stack.pop();
						foundLeftParenthesis = true;
						break;
					}
					else {
						postfix += stack.pop() + " ";
					}
				}
				
				if(!foundLeftParenthesis) {
					throw new RuntimeException("Infix contains mismatched parentheses");
				}
			}
			else if(token.matches("^[-]?\\d+(?:\\.\\d+)?$")) {
				// This token is a number
				postfix += token + " ";
			}
			else {
				throw new RuntimeException("Invalid token: " + token);
			}
		}
		
		while(!stack.empty()) {
			if(stack.peek().equals("(")) {
				throw new RuntimeException("Infix contains mismatched parentheses");
			}
			else {
				postfix += stack.pop() + " ";
			}
		}
		
		return postfix;
	}
	
	// Evaluates an expression which is in the postfix format.
	// Returns a Fraction.
	public static Fraction evaluatePostfix(String postfix) {
		String[] tokenStrings = postfix.split(" ");
		
		Stack<Fraction> stack = new Stack<>();
		
		for(String tokenString : tokenStrings) {
			if(operators.containsKey(tokenString)) {
				Fraction b = stack.pop();
				Fraction a = stack.pop();
				
				a.doOperation(tokenString, b);
				
				stack.push(a);
			}
			else {
				stack.push(new Fraction(tokenString));
			}
		}
		
		return stack.pop();
	}

	// Takes an expression, and tries to make sense of it.
	// Enables implicit multiplication, and tries to infer where
	// spaces should be placed, if spaces aren't put there.
	// Also places parentheses around fractions, so other operators don't
	// take precedence.
	public static String prettifyExpression(String input) {
		String prettified = input;
		
		// Convert mixed numbers to improper fractions
		prettified = prettified.replaceAll("([-]?)(\\d+)_(\\d+)/(\\d+)", "(($1$2*$4+$1$3)/$4)");
		
		// Put parentheses around fractions
		prettified = prettified.replaceAll("(?:^| )([-]?\\d+/\\d+)(?:$| )", "($1)");
		
		// Remove all spaces
		prettified = prettified.replaceAll("\\s", "");
		
		// Allow implicit multiplication
		// Ex: 2(4) -> 2*(4)
		prettified = prettified.replaceAll("(\\d+(?:\\.\\d+)?)\\(", "$1*(");
		
		// Insert spaces around operators
		prettified = prettified.replaceAll("(" + operatorsRegex + ")", " $1 ");
		
		// Replace instances of more than one space with one space
		// and remove whitespace at beginning and end.
		prettified = prettified.replaceAll("\\s+", " ").replaceAll("^\\s+|\\s+$", "");
		
		// Remove spaces in negative numbers
		// (separating minus sign and number).
		prettified = prettified.replaceAll("(" + operatorsRegex + ") \\- (\\d+(?:\\.\\d+)?)", "$1 -$2")
							   .replaceAll("^\\- ", "-");
		
		return prettified;
	}
	
	// Shortcut method. Takes an infix expression, prettifies it,
	// and evaluates it.
	public static Fraction evaluateInfix(String infix) {
		return evaluatePostfix(infixToPostfix(prettifyExpression(infix)));
	}
}

// Main FractionalCalculator class
public class FractionalCalculator {
	// Approximates PI (really badly)
	// Generates a bunch of points, and determines the ratio
	// of points inside the circle to the total number of points.
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
	
	// Reduces a fraction.
	public static String reduce(String fraction) {
		Fraction realFraction = Calculate.evaluateInfix(fraction);
		
		return realFraction.getNumerator() + "/" + realFraction.getDenominator();
	}
	
	// Converts a fraction to a mixed number.
	public static String convertToMixed(String fraction) {
		return Calculate.evaluateInfix(fraction).toString();
	}
	
	// Converts a mixed number to a fraction.
	public static String convertToFraction(String fraction) {
		return Calculate.evaluateInfix(fraction).toString(false);
	}
	
	// Calculates with two fractions using an operator.
	public static String calculate(String one, String operator, String two) {
		return Calculate.evaluateInfix(one + " " + operator + " " + two).toString(false);
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		
		System.out.println("Welcome to the Fraction Calculator!");
		
		// Stores the last answer
		String lastAnswer = "";
		
		while(true) {
			System.out.print("Enter an expression (or \"quit\"): ");
			
			String input = in.nextLine();
			
			// Quits program (by ending the loop).
			if(input.equals("quit")) {
				break;
			}
			// (Badly) approximates pi.
			else if(input.equals("pi")) {
				System.out.println("Approximating pi (badly)...");
				System.out.println(approximatePi());
			}
			// Gives help.
			else if(input.equals("help")) {
				System.out.println("This little application is a fractional calculator.");
				System.out.println("As you may have guessed, it handles basic math");
				System.out.println("involving fractions.");
				System.out.println();
				System.out.println("Valid operators: '+', '-', '*', '/', and '^'.");
				System.out.println();
				System.out.println("Example calculations:");
				System.out.println("1/2 + 3/4");
				System.out.println("1_1/3 * 6");
				System.out.println("1 + 2/3 / -2/3");
				System.out.println("-3 * 2_1/7");
				System.out.println("2 + 3 * 4");
				System.out.println("-3 * 2_1/7 ^ (1 + 2/2) / 1");
				System.out.println("1/2 ^ -1");
			}
			// Calculates with fractions!
			else {
				try {
					// Replaces all instances of 'ans' with the last answer
					// then stores the evaluated result in a variable.
					Fraction result = Calculate.evaluateInfix(input.replaceAll("ans", lastAnswer));
					
					// Stores the last answer
					lastAnswer = result.toString();
					
					// Prints out the result
					System.out.println(result.toString());
				}
				catch(RuntimeException e) {
					System.out.println("Error: " + e.getMessage());
				}
			}
		}
		
		// Say goodbye before the program quits.
		System.out.println("Goodbye!");
	}
}
