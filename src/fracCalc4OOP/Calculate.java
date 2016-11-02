package fracCalc4OOP;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

//Class parse expressions and execute them
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
