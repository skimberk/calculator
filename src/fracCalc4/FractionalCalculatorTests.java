package fracCalc4;

import java.util.ArrayList;
import org.junit.Test;
import testHelp.*;

public class FractionalCalculatorTests 
{
	@Test
	public void FracCalcShouldPrintGreetingFirst()
	{
		String response = ConsoleTester.getOutput("fracCalc4.FractionalCalculator", "1_1/2 + 3/4\r\nquit");
		verify.that(response).matches("\\AWelcome to the Fraction Calculator!");
	}

	@Test
	public void FracCalcShouldPrintGoodbyeLast()
	{
		String response = ConsoleTester.getOutput("fracCalc4.FractionalCalculator", "1_1/2 + 3/4\r\nquit");
		verify.that(response).matches("Goodbye!\\Z");
	}

	@Test
	public void FracCalcShouldQuitImmediatelyIfRequested()
	{
		String response = ConsoleTester.getOutput("fracCalc4.FractionalCalculator", "quit");
		verify.that(response).matches("Welcome to the Fraction Calculator!(\\s|\\n)+Enter an expression \\(or \"quit\"\\):(\\s|\\n)+Goodbye!");
	}
	
	@Test
	public void FracCalcShouldHandleMultipleInputsBeforeQuitting()
	{
		String response = ConsoleTester.getOutput("fracCalc4.FractionalCalculator", "1/3 + 2/3\r\n4/5 + 5/6\r\nquit");
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
		String response = ConsoleTester.getOutput("fracCalc4.FractionalCalculator", "2/3 + 1/6\r\nquit");
		ArrayList<String> answers = getAnswersFromOutput(response);
		verify.that(answers.get(0))
			.isFraction()
			.isEquivalentTo("5/6");
	}

	@Test
	public void FracCalcShouldHandleZeroes()
	{
		String response = ConsoleTester.getOutput("fracCalc4.FractionalCalculator", "0 * 4_2/3\r\nquit");
		ArrayList<String> answers = getAnswersFromOutput(response);
		verify.that(answers.get(0))
			.isFraction()
			.isEquivalentTo("0");
	}
	
	@Test
	public void FracCalcShouldPrintReducedAnswer()
	{
		String response = ConsoleTester.getOutput("fracCalc4.FractionalCalculator", "1_2/3 + 1/3\r\nquit");
		ArrayList<String> answers = getAnswersFromOutput(response);
		verify.that(answers.get(0)).equals("2");
	}
	
	@Test
	public void FracCalcShouldPrintReducedNegativeAnswer()
	{
		String response = ConsoleTester.getOutput("fracCalc4.FractionalCalculator", "1_3/10 * -4\r\nquit");
		ArrayList<String> answers = getAnswersFromOutput(response);
		verify.that(answers.get(0)).equals("-5_1/5");
	}
	
	@Test
	public void ConvertFunctionShouldHandleWholeNumbers()
	{
		String output = fracCalc4.FractionalCalculator.convertToFraction("42");
		verify.that(output).equals("42/1");
	}
	
	@Test
	public void ConvertFunctionShouldHandleMixedNumbers()
	{
		String output = fracCalc4.FractionalCalculator.convertToFraction("3_7/8");
		verify.that(output).equals("31/8");
	}
	
	@Test
	public void ConvertFunctionShouldHandleProperFractions()
	{
		String output = fracCalc4.FractionalCalculator.convertToFraction("9/10");
		verify.that(output).equals("9/10");
	}
	
	@Test
	public void ConvertFunctionShouldHandleImproperFractions()
	{
		String output = fracCalc4.FractionalCalculator.convertToFraction("7/3");
		verify.that(output).equals("7/3");
	}
	
	@Test
	public void ConvertFunctionShouldHandleZero()
	{
		String output = fracCalc4.FractionalCalculator.convertToFraction("0");
		verify.that(output).equals("0/1");
	}
	
	@Test
	public void ConvertFunctionShouldHandleNegativeWholeNumbers()
	{
		String output = fracCalc4.FractionalCalculator.convertToFraction("-19");
		verify.that(output).equals("-19/1");
	}
	
	@Test
	public void ConvertFunctionShouldHandleNegativeMixedNumbers()
	{
		String output = fracCalc4.FractionalCalculator.convertToFraction("-1_2/3");
		verify.that(output).equals("-5/3");
	}
	
	@Test
	public void ConvertFunctionShouldHandleNegativeFractions()
	{
		String output = fracCalc4.FractionalCalculator.convertToFraction("-5/6");
		verify.that(output).equals("-5/6");
	}
	
	@Test
	public void CalculateFunctionShouldAddFractionsCorrectly()
	{
		String output = fracCalc4.FractionalCalculator.calculate("3/5", "+", "7/5");
		verify.that(output).isFraction().isEquivalentTo("2");
	}
	
	@Test
	public void CalculateFunctionShouldSubstractFractionsCorrectly()
	{
		String output = fracCalc4.FractionalCalculator.calculate("3/5", "-", "7/5");
		verify.that(output).isFraction().isEquivalentTo("-4/5");
	}
	
	@Test
	public void CalculateFunctionShouldMultiplyFractionsCorrectly()
	{
		String output = fracCalc4.FractionalCalculator.calculate("3/5", "*", "7/5");
		verify.that(output).isFraction().isEquivalentTo("21/25");
	}
	
	@Test
	public void CalculateFunctionShouldDivideFractionsCorrectly()
	{
		String output = fracCalc4.FractionalCalculator.calculate("3/5", "/", "7/5");
		verify.that(output).isFraction().isEquivalentTo("3/7");
	}
	
	@Test
	public void ReduceFunctionShouldReduceCorrectly()
	{
		String output = fracCalc4.FractionalCalculator.reduce("14/6");
		verify.that(output).equals("7/3");
	}
	
	@Test
	public void ReduceFunctionShouldReduceNegativeFractions()
	{
		String output = fracCalc4.FractionalCalculator.reduce("-9/6");
		verify.that(output).equals("-3/2");
	}
	
	@Test
	public void ReduceFunctionShouldHandleZero()
	{
		String output = fracCalc4.FractionalCalculator.reduce("0/3");
		verify.that(output).matches("0/");
	}
	
	@Test
	public void ReduceFunctionShouldNotChangeFractionsAlreadyInLowestTerms()
	{
		String output = fracCalc4.FractionalCalculator.reduce("3/17");
		verify.that(output).equals("3/17");
	}
	
	@Test
	public void ConvertToMixedFunctionShouldConvertWholeNumbers()
	{
		String output = fracCalc4.FractionalCalculator.convertToMixed("6/1");
		verify.that(output).equals("6");
	}
	
	@Test
	public void ConvertToMixedFunctionShouldConvertNegativeNumbers()
	{
		String output = fracCalc4.FractionalCalculator.convertToMixed("-12/7");
		verify.that(output).equals("-1_5/7");
	}
	
	@Test
	public void ConvertToMixedFunctionShouldLeaveProperFractionsAlone()
	{
		String output = fracCalc4.FractionalCalculator.convertToMixed("4/19");
		verify.that(output).equals("4/19");
	}
	
	@Test
	public void ConvertToMixedFunctionShouldConvertImproperFractions()
	{
		String output = fracCalc4.FractionalCalculator.convertToMixed("19/4");
		verify.that(output).equals("4_3/4");
	}
	
	@Test
	public void ConvertToMixedFunctionShouldHandleZero()
	{
		String output = fracCalc4.FractionalCalculator.convertToMixed("0/1");
		verify.that(output).equals("0");
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
