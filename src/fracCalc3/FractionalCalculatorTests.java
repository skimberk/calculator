package fracCalc3;

import java.util.ArrayList;
import org.junit.Test;
import testHelp.*;

public class FractionalCalculatorTests 
{
	@Test
	public void FracCalcShouldPrintGreetingFirst()
	{
		String response = ConsoleTester.getOutput("fracCalc3.FractionalCalculator", "1_1/2 + 3/4\r\nquit");
		verify.that(response).matches("\\AWelcome to the Fraction Calculator!");
	}

	@Test
	public void FracCalcShouldPrintGoodbyeLast()
	{
		String response = ConsoleTester.getOutput("fracCalc3.FractionalCalculator", "1_1/2 + 3/4\r\nquit");
		verify.that(response).matches("Goodbye!\\Z");
	}

	@Test
	public void FracCalcShouldQuitImmediatelyIfRequested()
	{
		String response = ConsoleTester.getOutput("fracCalc3.FractionalCalculator", "quit");
		verify.that(response).matches("Welcome to the Fraction Calculator!(\\s|\\n)+Enter an expression \\(or \"quit\"\\):(\\s|\\n)+Goodbye!");
	}
	
	@Test
	public void FracCalcShouldHandleMultipleInputsBeforeQuitting()
	{
		String response = ConsoleTester.getOutput("fracCalc3.FractionalCalculator", "1/3 + 2/3\r\n4/5 + 5/6\r\nquit");
		ArrayList<String> answers = getAnswersFromOutput(response);
		verify.that(answers.size()).equals(2);
		verify.that(answers.get(0))
			.isFraction()
			.isEquivalentTo("1");
		verify.that(answers.get(1))
			.isFraction()
			.isEquivalentTo("49/30");
	}
	
	@Test
	public void FracCalcShouldAddFractionsCorrectly()
	{
		String response = ConsoleTester.getOutput("fracCalc3.FractionalCalculator", "2/3 + 1/6\r\nquit");
		ArrayList<String> answers = getAnswersFromOutput(response);
		verify.that(answers.get(0))
			.isFraction()
			.isEquivalentTo("5/6");
	}

	@Test
	public void FracCalcShouldHandleZeroes()
	{
		String response = ConsoleTester.getOutput("fracCalc3.FractionalCalculator", "0 * 4_2/3\r\nquit");
		ArrayList<String> answers = getAnswersFromOutput(response);
		verify.that(answers.get(0))
			.isFraction()
			.isEquivalentTo("0");
	}
	
	@Test
	public void ConvertFunctionShouldHandleWholeNumbers()
	{
		String output = fracCalc3.FractionalCalculator.convertToFraction("42");
		verify.that(output).equals("42/1");
	}
	
	@Test
	public void ConvertFunctionShouldHandleMixedNumbers()
	{
		String output = fracCalc3.FractionalCalculator.convertToFraction("3_7/8");
		verify.that(output).equals("31/8");
	}
	
	@Test
	public void ConvertFunctionShouldHandleProperFractions()
	{
		String output = fracCalc3.FractionalCalculator.convertToFraction("9/10");
		verify.that(output).equals("9/10");
	}
	
	@Test
	public void ConvertFunctionShouldHandleImproperFractions()
	{
		String output = fracCalc3.FractionalCalculator.convertToFraction("7/3");
		verify.that(output).equals("7/3");
	}
	
	@Test
	public void ConvertFunctionShouldHandleZero()
	{
		String output = fracCalc3.FractionalCalculator.convertToFraction("0");
		verify.that(output).equals("0/1");
	}
	
	@Test
	public void ConvertFunctionShouldHandleNegativeWholeNumbers()
	{
		String output = fracCalc3.FractionalCalculator.convertToFraction("-19");
		verify.that(output).equals("-19/1");
	}
	
	@Test
	public void ConvertFunctionShouldHandleNegativeMixedNumbers()
	{
		String output = fracCalc3.FractionalCalculator.convertToFraction("-1_2/3");
		verify.that(output).equals("-5/3");
	}
	
	@Test
	public void ConvertFunctionShouldHandleNegativeFractions()
	{
		String output = fracCalc3.FractionalCalculator.convertToFraction("-5/6");
		verify.that(output).equals("-5/6");
	}
	
	@Test
	public void CalculateFunctionShouldAddFractionsCorrectly()
	{
		String output = fracCalc3.FractionalCalculator.calculate("3/5", "+", "7/5");
		verify.that(output).isFraction().isEquivalentTo("2");
	}
	
	@Test
	public void CalculateFunctionShouldSubstractFractionsCorrectly()
	{
		String output = fracCalc3.FractionalCalculator.calculate("3/5", "-", "7/5");
		verify.that(output).isFraction().isEquivalentTo("-4/5");
	}
	
	@Test
	public void CalculateFunctionShouldMultiplyFractionsCorrectly()
	{
		String output = fracCalc3.FractionalCalculator.calculate("3/5", "*", "7/5");
		verify.that(output).isFraction().isEquivalentTo("21/25");
	}
	
	@Test
	public void CalculateFunctionShouldDivideFractionsCorrectly()
	{
		String output = fracCalc3.FractionalCalculator.calculate("3/5", "/", "7/5");
		verify.that(output).isFraction().isEquivalentTo("3/7");
	}
	
	private static ArrayList<String> getAnswersFromOutput(String output)
	{
		ArrayList<String> answers = new ArrayList<String>();
		String[] lines = output.split("\\n");
		for (int i = 1; i < lines.length - 1; i++)
			answers.add(lines[i].substring(lines[i].indexOf(": ") + 2).trim());
		return answers;
	}
}