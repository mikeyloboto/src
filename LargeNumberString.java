/********************************
 * COSC 2336.001                 *
 * Spring 2014                   *
 * Dmitrii Kondratev             *
 * String implementation class   *
 * for Assignment #1             *
 *                               *
 * This class handles creation   *
 * and testing                   *
 * several LargeNumber objects.  *
 * using String implementation   *
 * For reference view            *
 * LargeNumberArray class        *
 *********************************/

import java.util.Scanner;

public class LargeNumberString implements LargeNumberInterface {

	private final int MAX_DIGIT = 100;
	// this array stores digits backwards. Do not forget about that!!!
	// not anymore
	private String lDecimal;
	private String rDecimal;
	private int numDigitsL;
	private int numDigitsR;
	private boolean isNegative;
	private boolean decimalExists;

	public LargeNumberString() {
		numDigitsL = 0;
		numDigitsR = 0;
		lDecimal = new String();
		rDecimal = new String();
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
			lDecimal = new String();
			rDecimal = new String();
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
						lDecimal += input.charAt(i) - 48;
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
						rDecimal += input.charAt(i) - 48;
						numDigitsR++;
					}
				}
			}
		}

		{

			lDecimal = new StringBuffer(lDecimal).reverse().toString();

		}
		this.toNegative();
		this.removeRZeros();
	}

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
		;
		{
			for (int i = maxNumR - 1; i >= 0; i--) {
				int temp = 0;
				if (carryOne) {
					temp = this.getRDigits(i) + 1;
					carryOne = false;
				}
				if (takeOne)
				{
					temp = this.getRDigits(i) - 1;
					takeOne = false;
				}
				temp += this.getRDigits(i) + par1.getRDigits(i);
				if (temp > 9) {
					temp -= 10;
					carryOne = true;
				}
				if( this.isNegative)
				{
					if (temp < -9) {
						temp += 10;
						takeOne = true;
					}
				}
				else
				if (temp < 0) {
					temp += 10;
					takeOne = true;
				}
				Integer tempInt = new Integer(temp);
				rOutput.replace(i, i, tempInt.toString());
			}
		}
		
		{

			for (int i = 0; i < maxNumL; i++) {
				int temp = 0;
				if (carryOne) {
					temp += 1;
					carryOne = false;
				}
				if (takeOne)
				{
					temp -= 1;
					takeOne = false;
				}
				temp += this.getLDigits(i) + par1.getLDigits(i);
				if (temp > 9) {
					temp -= 10;
					carryOne = true;
				}
				else 
				if (this.isNegative)
				{
					if (temp < -9) {
						temp += 10;
						takeOne = true;
					}
				}
				Integer tempInt = new Integer(temp);
				lOutput.replace(i, i, tempInt.toString());
//				else
//				if (this.lDecimal[i] < -9) {
//					this.lDecimal[i] += 10;
//					takeOne = true;
//				}
				
			}
			
			
					
			if (carryOne)
			{
				this.numDigitsL+=1;
				int temp = this.getLDigits(numDigitsL - 1) + 1;
				lOutput.replace(numDigitsL - 1, numDigitsL - 1, new Integer(temp).toString());
			}
			if (takeOne)
			{
				this.numDigitsL+=1;
				int temp = this.getLDigits(numDigitsL - 1) - 1;
				lOutput.replace(numDigitsL - 1, numDigitsL - 1, new Integer(temp).toString());
			}
			if (this.getLDigits(numDigitsL - 1) < 0)
			{
				this.isNegative = true;
			}
			else
			{
				this.isNegative = false;
			}
		}
		this.lDecimal = lOutput.toString();
		this.rDecimal = rOutput.toString();
		this.toNegative();
		this.removeRZeros();
	}

	public void subtract(LargeNumberInterface par1) {
		LargeNumberString var1 = (LargeNumberString)par1;
		var1.negate();
		var1.decimalExists = true;
		this.add(par1);
		
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

	public int getLDigits(int par1) {
		if (par1 >= this.lDecimal.length()) {
			return 0;
		} else
			return this.lDecimal.charAt(par1) - 48;
	}

	public int getRDigits(int par1) {
		if (par1 >= this.rDecimal.length())
			return 0;
		return this.rDecimal.charAt(par1) - 48;
	}

	public void printValue() {
		// System.out.println("Here is your large number: ");
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
			toOutput += Math.abs(this.getLDigits(i));
		}
		if (decimalExists) {
			toOutput += ".";
			for (int i = 0; i < numDigitsR; i++) {
				toOutput += Math.abs(this.getRDigits(i));
			}
		}
		return toOutput;
	}

	private void removeRZeros() {
		for (int i = this.rDecimal.length()- 1; i >= 0; i--) {
			if (this.getRDigits(i) != 0) {
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

	private void toNegative() {
		StringBuilder lOut = new StringBuilder(this.lDecimal);
		StringBuilder rOut = new StringBuilder(this.rDecimal);
		if (isNegative) {
			for (int i = 0; i < numDigitsL; i++) {

				lOut.replace(i, i,
						new Integer(0 - Math.abs(this.getLDigits(i)))
								.toString());

			}
			for (int i = 0; i < numDigitsR; i++) {

				rOut.replace(i, i,
						new Integer(0 - Math.abs(this.getRDigits(i)))
								.toString());

			}
		} else {
			for (int i = 0; i < numDigitsL; i++) {

				lOut.replace(i, i,
						new Integer(Math.abs(this.getLDigits(i))).toString());

			}
			for (int i = 0; i < numDigitsR; i++) {

				rOut.replace(i, i,
						new Integer(Math.abs(this.getRDigits(i))).toString());

			}
		}
		this.rDecimal = rOut.toString();
		this.lDecimal = lOut.toString();
	}

	public void isDecimal(boolean par1)
	{
		this.decimalExists = par1;
	}
	public void negate()
	{
		if (this.isNegative)
			this.isNegative = false;
		else	
			this.isNegative = true;
		this.toNegative();
		//this.printValue();
	}
	
	public void addZeroR()
	{
		this.rDecimal += "0";
		this.numDigitsR++;
	}
	
	public void addZeroL()
	{
		this.lDecimal += "0";
		this.numDigitsL++;
	}
}
