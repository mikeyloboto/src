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
			for (int i = maxNumR; i > 0; i--) {
				if (carryOne) {
					this.setL(i, this.getLDigits(i) + 1);
					carryOne = false;
				}
				if (takeOne)
				{
					this.setR(i, this.getRDigits(i) - 1);
					takeOne = false;
				}
				this.setR(i, this.getRDigits(i) + par1.getRDigits(i));
				if (this.getRDigits(i) > 9) {
					this.setR(i, this.getRDigits(i) - 10);
					carryOne = true;
				}
				if( this.isNegative)
				{
					if (this.getRDigits(i) < -9) {
						this.setR(i, this.getRDigits(i) + 10);
						takeOne = true;
					}
				}
				else
				if (this.getRDigits(i) < 0) {
					this.setR(i, this.getRDigits(i) + 10);
					takeOne = true;
				}
			}
		}
		{

			for (int i = 1; i <= maxNumL; i++) {
				if (carryOne) {
					this.setL(i, this.getLDigits(i) + 1);
					carryOne = false;
				}
				if (takeOne)
				{
					this.setL(i, this.getLDigits(i) - 1);
					takeOne = false;
				}
				this.setL(i, this.getLDigits(i) + par1.getLDigits(i));
				if (this.getLDigits(i) > 9) {
					this.setL(i, this.getLDigits(i) - 10);
					carryOne = true;
				}
				else 
				if (this.isNegative)
				{
					if (this.getLDigits(i) < -9) {
						this.setL(i, this.getLDigits(i) + 10);
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
				this.setL(numDigitsL - 1, this.getLDigits(numDigitsL - 1) + 1);
			}
			if (takeOne)
			{
				this.numDigitsL+=1;
				this.setL(numDigitsL - 1, this.getLDigits(numDigitsL - 1) - 1);
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
		this.toNegative();
		//this.removeRZeros();
		
	}

	@Override
	public void subtract(LargeNumberInterface x) {
		LargeNumberLinkedList var1 = (LargeNumberLinkedList)x;
		var1.negate();
		var1.decimalExists = true;
		this.add(x);
		
	}

	private void negate() {
		if (this.isNegative)
			this.isNegative = false;
		else
			this.isNegative = true;
		this.toNegative();
		//this.printValue();
	}
	public void printValue() {
		System.out.println(this.toString());
	}

	public String toString() {
		String toOutput = "";
		if (isNegative)
			toOutput += "-";
		for (int i = numDigitsL; i > 0; i--) {
			toOutput += Math.abs(getLDigits(i));
		}
		if (decimalExists) {
			toOutput += ".";
			for (int i = 1; i <= numDigitsR; i++) {
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
			for (int i = 1; i <= numDigitsL; i++) {

				setL(i, 0 - Math.abs(getLDigits(i)));

			}
			for (int i = 1; i <= numDigitsR; i++) {

				setR(i, 0 - Math.abs(getRDigits(i)));

			}
		} else {
			for (int i = 1; i <= numDigitsL; i++) {

				setL(i, Math.abs(getLDigits(i)));

			}
			for (int i = 1; i <= numDigitsR; i++) {

				setR(i, Math.abs(getRDigits(i)));

			}
		}
	}
	@Override
	public void multiplyByTen() {
		int lastR = this.getRDigits(0);
		for (int i = 1; i < this.getNumRDigits(); i++)
		{
			this.setR(i, this.getRDigits(i+1));
		}
		this.setR(this.getNumRDigits(), 0);
		
		for (int i = this.getNumLDigits(); i > 0; i--)
		{
			this.setL(i, this.getLDigits(i-1));
		}
		this.setL(0, lastR);
		
		this.numDigitsL += 1;
		this.numDigitsR -= 1;
	}

}
