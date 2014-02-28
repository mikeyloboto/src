public class Node {
	private int value;
	private Node next;
	
	public Node()
	{
		this.value = 0;
		this.next = null;
	}
	public Node(int par1)
	{
		this.value = par1;
		this.next = null;
	}
	
	public void setNext(Node par1)
	{
		this.next = par1;
	}
	
	public void setValue(int par1)
	{
		this.value = par1;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
	public Node getNext()
	{
		if (this.next==null)
		{
			this.next = new Node();
		}
		return this.next;
		
	}
	
	public void removeNext()
	{
		this.next = null;
	}
}
