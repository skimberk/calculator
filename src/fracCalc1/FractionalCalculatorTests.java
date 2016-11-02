package fracCalc1;

import org.junit.Test;
import testHelp.*;

public class FractionalCalculatorTests {
	@Test
	public void FracCalcShouldPrintGreetingFirst() {
		String response = ConsoleTester.getOutput(
				"fracCalc1.FractionalCalculator", "1_1/2 + 3/4");
		verify.that(response).matches("\\AWelcome to the Fraction Calculator!");
	}

	@Test
	public void FracCalcShouldPrintGoodbyeLast() {
		String response = ConsoleTester.getOutput(
				"fracCalc1.FractionalCalculator", "1_1/2 + 3/4");
		verify.that(response).matches("Goodbye!\\Z");
	}

	@Test
	public void FracCalcShouldPrintLeftOperand() {
		String response = ConsoleTester.getOutput(
				"fracCalc1.FractionalCalculator", "1_1/2 + 3/4");
		verify.that(response).matches("Left operand: 1_1/2[\\r\\n]+");
	}

	@Test
	public void FracCalcShouldPrintOperator() {
		String response = ConsoleTester.getOutput(
				"fracCalc1.FractionalCalculator", "1_1/2 + 3/4");
		verify.that(response).matches("[\\r\\n]+Operator: \\+[\\r\\n]+");
	}

	@Test
	public void FracCalcShouldPrintRightOperand() {
		String response = ConsoleTester.getOutput(
				"fracCalc1.FractionalCalculator", "1_1/2 + 3/4");
		verify.that(response).matches("[\\r\\n]+Right operand: 3/4[\\r\\n]+");
	}

	@Test
	public void FracCalcShouldPrintNegativesAndZeroes() {
		String response = ConsoleTester.getOutput(
				"fracCalc1.FractionalCalculator", "0 * -4_4/5");
		verify.that(response)
				.matches(
						"Left operand: 0[\\s\\r\\n]+Operator: \\*[\\s\\r\\n]+Right operand: -4_4/5");
	}
}