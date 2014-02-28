public interface LargeNumberInterface {
	abstract public void inputValue();

	abstract public void add(LargeNumberInterface par1);

	abstract public void subtract(LargeNumberInterface x);

	abstract public void printValue();

	abstract public int getNumLDigits();

	abstract public int getNumRDigits();

	abstract public int getLDigits(int par1);

	abstract public int getRDigits(int par1);

	abstract public void isDecimal(boolean par1);
	
	//I greatly recommend using Notepad++ in class, it will make life much easier.
	//Also I recommend Blind Guardian's album "Nightfall In Meddle-Earth". It is a great fusion
	//between medieval music and German Power Metal, based on Tolkien's "Silmarillion"
}
