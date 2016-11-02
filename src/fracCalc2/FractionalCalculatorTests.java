package fracCalc2;

import org.junit.Test;
import testHelp.*;

public class FractionalCalculatorTests 
{
	@Test
	public void FracCalcShouldPrintGreetingFirst()
	{
		String response = ConsoleTester.getOutput("fracCalc2.FractionalCalculator", "1_1/2 + 3/4\r\nquit");
		verify.that(response).matches("\\AWelcome to the Fraction Calculator!");
	}

	@Test
	public void FracCalcShouldPrintGoodbyeLast()
	{
		String response = ConsoleTester.getOutput("fracCalc2.FractionalCalculator", "1_1/2 + 3/4\r\nquit");
		verify.that(response).matches("Goodbye!\\Z");
	}

	@Test
	public void FracCalcShouldPrintLeftOperand()
	{
		String response = ConsoleTester.getOutput("fracCalc2.FractionalCalculator", "1_1/2 + 3/4\r\nquit");
		verify.that(response).matches("Left operand: 3/2[\\r\\n]+");
	}

	@Test
	public void FracCalcShouldPrintOperator()
	{
		String response = ConsoleTester.getOutput("fracCalc2.FractionalCalculator", "1_1/2 + 3/4\r\nquit");
		verify.that(response).matches("[\\r\\n]+Operator: \\+[\\r\\n]+");
	}

	@Test
	public void FracCalcShouldPrintRightOperand()
	{
		String response = ConsoleTester.getOutput("fracCalc2.FractionalCalculator", "1_1/2 + 3/4\r\nquit");
		verify.that(response).matches("[\\r\\n]+Right operand: 3/4[\\r\\n]+");
	}

	@Test
	public void FracCalcShouldPrintNegativesAndZeroes()
	{
		String response = ConsoleTester.getOutput("fracCalc2.FractionalCalculator", "0 * -4_4/5\r\nquit");
		verify.that(response).matches("Left operand: 0/1[\\s\\r\\n]+Operator: \\*[\\s\\r\\n]+Right operand: -24/5");
	}
	
	@Test
	public void FracCalcShouldPrintWholeNumbersOverOne()
	{
		String response = ConsoleTester.getOutput("fracCalc2.FractionalCalculator", "-3 / 13\r\nquit");
		verify.that(response).matches("Left operand: -3/1[\\s\\r\\n]+Operator: \\/[\\s\\r\\n]+Right operand: 13/1");
	}
	
	@Test
	public void FracCalcShouldQuitImmediatelyIfRequested()
	{
		String response = ConsoleTester.getOutput("fracCalc2.FractionalCalculator", "quit");
		verify.that(response).matches("Welcome to the Fraction Calculator!(\\s|\\n)+Enter an expression \\(or \"quit\"\\):(\\s|\\n)+Goodbye!");
	}
	
	@Test
	public void FracCalcShouldHandleMultipleInputsBeforeQuitting()
	{
		String response = ConsoleTester.getOutput("fracCalc2.FractionalCalculator", "1/3 + 2/3\r\n4/5 + 5/6\r\nquit");
		verify.that(response).matches("2/3"); // don't check the whole thing. It's enough to see 
		verify.that(response).matches("5/6"); // if part of each expression showed up
	}
	
	@Test
	public void ConvertFunctionShouldHandleWholeNumbers()
	{
		String output = fracCalc2.FractionalCalculator.convertToFraction("42");
		verify.that(output).equals("42/1");
	}
	
	@Test
	public void ConvertFunctionShouldHandleMixedNumbers()
	{
		String output = fracCalc2.FractionalCalculator.convertToFraction("3_7/8");
		verify.that(output).equals("31/8");
	}
	
	@Test
	public void ConvertFunctionShouldHandleProperFractions()
	{
		String output = fracCalc2.FractionalCalculator.convertToFraction("9/10");
		verify.that(output).equals("9/10");
	}
	
	@Test
	public void ConvertFunctionShouldHandleImproperFractions()
	{
		String output = fracCalc2.FractionalCalculator.convertToFraction("7/3");
		verify.that(output).equals("7/3");
	}
	
	@Test
	public void ConvertFunctionShouldHandleZero()
	{
		String output = fracCalc2.FractionalCalculator.convertToFraction("0");
		verify.that(output).equals("0/1");
	}
	
	@Test
	public void ConvertFunctionShouldHandleNegativeWholeNumbers()
	{
		String output = fracCalc2.FractionalCalculator.convertToFraction("-19");
		verify.that(output).equals("-19/1");
	}
	
	@Test
	public void ConvertFunctionShouldHandleNegativeMixedNumbers()
	{
		String output = fracCalc2.FractionalCalculator.convertToFraction("-1_2/3");
		verify.that(output).equals("-5/3");
	}
	
	@Test
	public void ConvertFunctionShouldHandleNegativeFractions()
	{
		String output = fracCalc2.FractionalCalculator.convertToFraction("-5/6");
		verify.that(output).equals("-5/6");
	}
}