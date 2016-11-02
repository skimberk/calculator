package fracCalc4OOP;

//A class which represents the associativity and
//precedence of operators.
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
