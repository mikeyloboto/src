import java.util.Scanner;


public class LargeNumberLinkedList implements LargeNumberInterface {
	
	private Node lDecimal;
	private Node rDecimal;
	private int numDigitsL;
	private int numDigitsR;
	private boolean isNegative;
	private boolean decimalExists;
	
	private final int MAX_ITEMS = 100;
	
	
	public LargeNumberLinkedList()
	{
		numDigitsL = 0;
		numDigitsR = 0;
		lDecimal = new Node();
		rDecimal = new Node();
		this.isNegative = false;
		this.decimalExists = false;
		
		
	}
	@Override
	public void inputValue() {
		// TODO Auto-generated method stub
		boolean inputValidated = false;
		Scanner par1 = new Scanner(System.in);
		System.out.print("Input a number>  ");
		while (!inputValidated) {
			numDigitsL = 0;
			numDigitsR = 0;
			lDecimal = new Node();
			rDecimal = new Node();
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
						setL(i - var1, input.charAt(i) - 48);
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
						setR(i - (decimalPos + 1), input.charAt(i) - 48);
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
				temp = getLDigits(i);
				setL(i, getLDigits(this.numDigitsL - 1 - i));
				setL(this.numDigitsL - 1 - i, temp);
			}
		}
		this.toNegative();
		//this.removeRZeros();
	}

	@Override
	public void add(LargeNumberInterface par1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void subtract(LargeNumberInterface x) {
		// TODO Auto-generated method stub
		
	}

	public void printValue() {
		//System.out.println("Here is your large number: ");
		System.out.println(this.toString());
	}

	public String toString() {
		String toOutput = "";
		if (isNegative)
			toOutput += "-";
		for (int i = numDigitsL - 1; i >= 0; i--) {
			toOutput += Math.abs(getLDigits(i));
		}
		if (decimalExists) {
			toOutput += ".";
			for (int i = 0; i < numDigitsR; i++) {
				toOutput += Math.abs(getRDigits(i));
			}
		}
		return toOutput;
	}

	@Override
	public int getNumLDigits() {
		return this.numDigitsL;
	}

	@Override
	public int getNumRDigits() {
		return this.numDigitsR;
	}

	@Override
	public int getLDigits(int par1) {
		if (par1 <= numDigitsL)
		{
			Node point = lDecimal;
			for (int i = 1; i < par1; i++)
			{
				point = point.getNext();
			}
			return point.getValue();
		}
		else return 0;
	}

	@Override
	public int getRDigits(int par1) {
		if (par1 <= numDigitsR)
		{
			Node point = rDecimal;
			for (int i = 1; i < par1; i++)
			{
				point = point.getNext();
			}
			return point.getValue();
		}
		else return 0;
	}

	@Override
	public void isDecimal(boolean par1) {
		this.decimalExists = true;
	}
	
	private void setL(int pos, int val)
	{
		Node point = lDecimal;
		for (int i = 1; i < pos; i++)
		{
			point = point.getNext();
		}
		point.setValue(val);
	}
	private void setR(int pos, int val)
	{
		Node point = rDecimal;
		for (int i = 1; i < pos; i++)
		{
			point = point.getNext();
		}
		point.setValue(val);
	}
	
	private void toNegative() {
		if (isNegative) {
			for (int i = 0; i < numDigitsL; i++) {

				setL(i, 0 - Math.abs(getLDigits(i)));

			}
			for (int i = 0; i < numDigitsR; i++) {

				setR(i, 0 - Math.abs(getRDigits(i)));

			}
		} else {
			for (int i = 0; i < numDigitsL; i++) {

				setL(i, Math.abs(getLDigits(i)));

			}
			for (int i = 0; i < numDigitsR; i++) {

				setR(i, Math.abs(getRDigits(i)));

			}
		}
	}

}
