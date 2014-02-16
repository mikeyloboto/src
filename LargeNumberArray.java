/********************************
 * COSC 2336.001                 *
 * Spring 2014                   *
 * Dmitrii Kondratev             *
 * Array implementation class    *
 * for Assignment #1             *
 *                               *
 * This class handles creation   *
 * and testing                   *
 * several LargeNumber objects.  *
 * using array implementation    *
 *********************************/

import java.util.Scanner;

public class LargeNumberArray implements LargeNumberInterface {

	private final int MAX_DIGIT = 5000;
	// this array stores digits backwards. Do not forget about that!!!
	// not anymore
	private int[] lDecimal;
	private int[] rDecimal;
	private int numDigitsL;
	private int numDigitsR;
	private boolean isNegative;
	private boolean decimalExists;

	public LargeNumberArray() {
		numDigitsL = 0;
		numDigitsR = 0;
		lDecimal = new int[MAX_DIGIT];
		rDecimal = new int[MAX_DIGIT];
		isNegative = false;
		decimalExists = false;
	}

	/**********************************
	 * inputValue() method            *
	 * Promts user to enter           * 
	 * his/her large number           *
	 * and turn it into array         *
	 * only works if user enters      *
	 * valid number, without          *
	 * illegal symbols, only one      *
	 * decimal point and possibly     *
	 * a minus sign in the beginning  *
	 * of negative numbers            *
	 **********************************/
	public void inputValue() {
		boolean inputValidated = false;
		Scanner par1 = new Scanner(System.in);
		System.out.println("Input you large number: ");
		while (!inputValidated) {
			numDigitsL = 0;
			numDigitsR = 0;
			lDecimal = new int[MAX_DIGIT];
			rDecimal = new int[MAX_DIGIT];
			String input = par1.nextLine();
			// par1.close();
			// par1.reset();
			inputValidated = true;
			int decimalPos = 0;

			if (input.charAt(0) == '-')
				this.isNegative = true;
			else
				this.isNegative = false;

			// if number is negative, skipping first character from processing.

			int var1;
			if (isNegative) {
				var1 = 1;
			} else
				var1 = 0;

			for (int i = var1; i < input.length(); i++) {
				if (input.charAt(i) != '.') {
					if (input.charAt(i) > 57 || input.charAt(i) < 48) {
						// Handle idiotic input here:
						System.out
								.println("Please check your input and try again: ");
						inputValidated = false;
						// par1 = new Scanner(System.in);
						break;

					} else {
						lDecimal[i - var1] = input.charAt(i) - 48;
						// Converting from ASCII to int, that's why subtracting
						// 48
						numDigitsL++;
					}
				} else {
					decimalExists = true;
					decimalPos = i;
					break;
				}

			}
			if (decimalExists) {

				for (int i = decimalPos + 1; i < input.length(); i++) {
					if (input.charAt(i) > 57 || input.charAt(i) < 48) {
						// Handle idiotic input here:
						System.out
								.println("Please check your input and try again: ");
						inputValidated = false;
						// par1 = new Scanner(System.in);
						break;
					} else {
						rDecimal[i - (decimalPos + 1)] = input.charAt(i) - 48;
						numDigitsR++;
					}
				}
			}
		}

		{
			int temp = 0;
			for (int i = 0; i < (this.numDigitsL) / 2; i++) {
				temp = lDecimal[i];
				lDecimal[i] = lDecimal[this.numDigitsL - 1 - i];
				lDecimal[this.numDigitsL - 1 - i] = temp;
			}
		}
		this.toNegative();
		this.removeRZeros();
	}
	
	public void add(LargeNumberInterface par1) {
		boolean carryOne = false;
		boolean takeOne = false;
		int maxNumR = 0;
		int maxNumL = 0;

		{
			if (this.numDigitsR > par1.getNumRDigits()) {
				maxNumR = this.numDigitsR;
			} else {
				maxNumR = par1.getNumRDigits();
			}
			if (this.numDigitsL > par1.getNumLDigits()) {
				maxNumL = this.numDigitsL;
			} else {
				maxNumL = par1.getNumLDigits();
			}
		}
		{
			for (int i = maxNumR - 1; i >= 0; i--) {
				if (carryOne) {
					this.rDecimal[i] += 1;
					carryOne = false;
				}
				if (takeOne)
				{
					this.rDecimal[i] -= 1;
					takeOne = false;
				}
				this.rDecimal[i] += par1.getRDigits()[i];
				if (this.rDecimal[i] > 9) {
					this.rDecimal[i] -= 10;
					carryOne = true;
				}
				if( this.isNegative)
				{
					if (this.rDecimal[i] < -9) {
						this.rDecimal[i] += 10;
						takeOne = true;
					}
				}
				else
				if (this.rDecimal[i] < 0) {
					this.rDecimal[i] += 10;
					takeOne = true;
				}
			}
		}
		{

			for (int i = 0; i < maxNumL; i++) {
				if (carryOne) {
					this.lDecimal[i] += 1;
					carryOne = false;
				}
				if (takeOne)
				{
					this.lDecimal[i] -= 1;
					takeOne = false;
				}
				this.lDecimal[i] += par1.getLDigits()[i];
				if (this.lDecimal[i] > 9) {
					this.lDecimal[i] -= 10;
					carryOne = true;
				}
				if (this.isNegative)
				{
					if (this.lDecimal[i] < 0) {
						this.lDecimal[i] += 10;
						takeOne = true;
					}
				}
				else
				if (this.lDecimal[i] < -9) {
					this.lDecimal[i] += 10;
					takeOne = true;
				}
				
			}
			if (carryOne)
			{
				this.numDigitsL+=1;
				this.lDecimal[numDigitsL - 1] += 1;
			}
			if (takeOne)
			{
				this.numDigitsL+=1;
				this.lDecimal[numDigitsL - 1] -= 1;
			}
			if (lDecimal[numDigitsL - 1] < 0)
			{
				this.isNegative = true;
			}
			else
			{
				this.isNegative = false;
			}
		}
		this.toNegative();
		this.removeRZeros();
	}

	public void subtract(LargeNumberInterface par1) {
		LargeNumberArray var1 = (LargeNumberArray) par1;
		var1.negate();
		this.add(var1);
		
	}

	/****************************
	 * Nothing interesting here *
	 ****************************/
	public int getNumLDigits() {
		return this.numDigitsL;
	}

	public int getNumRDigits() {
		return this.numDigitsR;
	}

	public int[] getLDigits() {
		return this.lDecimal;
	}

	public int[] getRDigits() {
		return this.rDecimal;
	}

	public void printValue() {
		System.out.println("Here is your large number: ");
		System.out.println(this.toString());
	}

	/*******************************
	 * toString() method           *
	 * returns String with         *
	 * the value of current large  *
	 * number.                     *
	 *******************************/
	public String toString() {
		String toOutput = "";
		if (isNegative)
			toOutput += "-";
		for (int i = numDigitsL - 1; i >= 0; i--) {
			toOutput += Math.abs(lDecimal[i]);
		}
		if (decimalExists) {
			toOutput += ".";
			for (int i = 0; i < numDigitsR; i++) {
				toOutput += Math.abs(rDecimal[i]);
			}
		}
		return toOutput;
	}

	private void removeRZeros()
	{
		for(int i = MAX_DIGIT - 1; i >= 0; i--)
		{
			if (rDecimal[i]==0)
			{
				this.numDigitsR = i + 1;
				break;
			}
		}
		if (this.numDigitsR==0)
		{
			this.decimalExists = false;
		}
	}
	
	private void toNegative()
	{
		if (isNegative)
		{
			for (int i = 0; i < numDigitsL; i++) {
				if (lDecimal[i] >= 0) {
					lDecimal[i] = 0 - lDecimal[i];
				}
			}
			for (int i = 0; i < numDigitsR; i++)
			{
				if (rDecimal[i] >= 0) {
					rDecimal[i] = 0 - rDecimal[i];
				}
			}
		}
		else
		{
			for (int i = 0; i < numDigitsL; i++) {
				if (lDecimal[i] >= 0) {
					lDecimal[i] = 0 - lDecimal[i];
				}
			}
			for (int i = 0; i < numDigitsR; i++)
			{
				if (rDecimal[i] >= 0) {
					rDecimal[i] = 0 - rDecimal[i];
				}
			}
		}
	}

	public void negate()
	{
		if (this.isNegative)
			this.isNegative = false;
		else
			this.isNegative = true;
		this.toNegative();
		this.printValue();
	}
}
