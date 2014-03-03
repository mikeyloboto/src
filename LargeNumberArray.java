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

	private final int MAX_DIGIT = 100;
	// this array stores digits backwards. Do not forget about that!!!
	// not anymore
	private int[] lDecimal;
	private int[] rDecimal;
	private int numDigitsL;
	private int numDigitsR;
	private boolean isNegative;
	boolean decimalExists;

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
		System.out.print("Input a number>  ");
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
		//Originally this array was in reverse order
		//This invert it:
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
	//Addition of two Large Numbers
	//including carrying the one, taking one fron next digit, for adding negtive numbers.
	
	public void add(LargeNumberInterface par1) {
		boolean carryOne = false;
		boolean takeOne = false;
		int maxNumR = 0;
		int maxNumL = 0;
		this.isDecimal(true);
		par1.isDecimal(true);
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
				this.rDecimal[i] += par1.getRDigits(i);
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
				this.lDecimal[i] += par1.getLDigits(i);
				if (this.lDecimal[i] > 9) {
					this.lDecimal[i] -= 10;
					carryOne = true;
				}
				else 
				if (this.isNegative)
				{
					if (this.lDecimal[i] < -9) {
						this.lDecimal[i] += 10;
						takeOne = true;
					}
				}
//				else
//				if (this.lDecimal[i] < -9) {
//					this.lDecimal[i] += 10;
//					takeOne = true;
//				}
				
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
	//Subtraction.
	//This part is not working.
	//Spent about 3 hours trying to debug it.
	//It is capable of subtracting two numbers, but if second number
	//is larger than the first one, and they have a fraction part
	//(not integers) it produces complete bul..wrong information
	
	public void subtract(LargeNumberInterface par1) {
		LargeNumberArray var1 = (LargeNumberArray)par1;
		var1.negate();
		var1.decimalExists = true;
		this.add(par1);
		
	}

	/****************************
	 * Nothing interesting here *
	 ****************************/
	
	
	//retrieving number of positions in either one of the arrays,
	//and retrieving a digit at a certain position in either one of the arrays
	//is handled by the next four methods: 
	public int getNumLDigits() {
		return this.numDigitsL;
	}

	public int getNumRDigits() {
		return this.numDigitsR;
	}

	public int getLDigits(int par1) {
		return this.lDecimal[par1];
	}

	public int getRDigits(int par1) {
		return this.rDecimal[par1];
	}
	
	
	
	public void printValue() {
		//System.out.println("Here is your large number: ");
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
	
	
	//Slowly going from the last possible right digit, this method
	//consumes unborn digits (zeros). Implementing that 
	//ruthless murderer was necessary precaution
	//caused by fast increase in population of useless
	//zeros, incapable of providing and storing
	//any sort of useful information.
	private void removeRZeros() {
		for (int i = MAX_DIGIT - 1; i >= 0; i--) {
			if (rDecimal[i] != 0) {
				this.numDigitsR = (i + 1);
				break;
			}
			else
				this.numDigitsR = 0;
		}
		if (this.numDigitsR == 0) {
			this.decimalExists = false;
		}
	}
	//If any number is negative, every digit in this number is negative.
	//I have no idea, how I managed to realize that, but
	//it seems to work.
	//This method turns negative numbers into NEGATIVE!, not just positive with the "-"
	//in front of it.
	//Doing opposite for positive numbers.
	private void toNegative() {
		if (isNegative) {
			for (int i = 0; i < numDigitsL; i++) {

				lDecimal[i] = 0 - Math.abs(lDecimal[i]);

			}
			for (int i = 0; i < numDigitsR; i++) {

				rDecimal[i] = 0 - Math.abs(rDecimal[i]);

			}
		} else {
			for (int i = 0; i < numDigitsL; i++) {

				lDecimal[i] = Math.abs(lDecimal[i]);

			}
			for (int i = 0; i < numDigitsR; i++) {

				rDecimal[i] = Math.abs(rDecimal[i]);

			}
		}
	}
	
	//Checks if the number has any digits after decimal point.
	//no point in drawing that ugly point, if there is nothing
	//but an ancient horror of nothingness after if.
	public void isDecimal(boolean par1)
	{
		this.decimalExists = par1;
	}
	
	//Turns positive numbers into negative, and vice versa.
	public void negate()
	{
		if (this.isNegative)
			this.isNegative = false;
		else
			this.isNegative = true;
		this.toNegative();
		//this.printValue();
	}

	@Override
	public void multiplyByTen() {
		int lastR = this.getRDigits(0);
		for (int i = 0; i < this.getNumRDigits(); i++)
		{
			this.rDecimal[i] = this.rDecimal[i+1];
		}
		if (this.numDigitsR > 0)
		{
			this.rDecimal[this.getNumRDigits() - 1] = 0;
		}
		
		for (int i = this.getNumLDigits(); i > 0; i--)
		{
			this.lDecimal[i] = this.lDecimal[i-1];
		}
		this.lDecimal[0] = lastR;
		
		this.numDigitsL += 1;
		this.numDigitsR -= 1;
	}
}
