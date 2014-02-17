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
}
