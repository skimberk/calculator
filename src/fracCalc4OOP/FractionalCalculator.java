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

package fracCalc4OOP;

import java.util.Scanner;

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
