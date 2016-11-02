package fracCalc4OOP;

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