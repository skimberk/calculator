package fracCalc4Old;

import java.util.Stack;

public class RPN {
	// This list of operators should be in order
	// from highest to lowest precedence, with
	// operators of equal precedence being
	// grouped together.
	private static final String operators = " ^ */ +- ";
			
	// A Regex for identifying valid numbers
	private static final String validNumberRegex = "^[+-]?([0-9]*\\.[0-9]+|[0-9]+)$";

	public static String infixToPostfix(String infix) {
		
		Stack<String> stack = new Stack<>();
		String postfix = "";
		
		String[] tokens = infix.split(" ");
		
		for(String token : tokens) {
			if(operators.indexOf(token) != -1) {
				// This token is an operator
				while(!stack.empty() && operators.indexOf(stack.peek()) != -1 &&
					  (operators.indexOf(token) > operators.indexOf(stack.peek()) ||
					   stack.peek().equals(operators.charAt(operators.indexOf(token) - 1)))) {
					
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
			else if(token.matches(validNumberRegex)) {
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
	
	public static double evaluatePostfix(String postfix) {
		String[] tokenStrings = postfix.split(" ");
		
		Stack<Double> stack = new Stack<>();
		
		for(String tokenString : tokenStrings) {
			if(operators.contains(tokenString)) {
				double b = stack.pop();
				double a = stack.pop();
				
				if(tokenString.equals("+")) {
					stack.push(a + b);
				}
				else if(tokenString.equals("-")) {
					stack.push(a - b);
				}
				else if(tokenString.equals("*")) {
					stack.push(a * b);
				}
				else if(tokenString.equals("/")) {
					stack.push(a / b);
				}
				else if(tokenString.equals("^")) {
					stack.push(Math.pow(a, b));
				}
			}
			else if(tokenString.matches(validNumberRegex)) {
				stack.push(Double.parseDouble(tokenString));
			}
			else {
				throw new RuntimeException("Invalid token:" + tokenString);
			}
		}
		
		return stack.pop();
	}
	
	public static void main(String[] args) {
		String postfix = infixToPostfix("( 1 + 3 * 5 ) / ( 2 ^ 3 - 2 )");
		
		System.out.println(postfix);
		System.out.println(evaluatePostfix(postfix));
	}
}
