/********************************
* COSC 2336.001                 *
* Spring 2014                   *
* Dr. Leonard Brown             *
* Main class for Assignment #1  *
*                               *
* This class creates and tests  *
* several LargeNumber objects.  *
*********************************/

class Assignment1 {
   public static void main (String[] args) {
      LargeNumberArray a, b, c;
      LargeNumberString r, s, t;


      System.out.println("Array Implementation");
      a = new LargeNumberArray();
      b = new LargeNumberArray();
      c = new LargeNumberArray();  
      a.inputValue();
      b.inputValue();
      c.inputValue();
      a.add(b);
      a.printValue();
      a.subtract(c);
      a.printValue();
      System.out.println();

      System.out.println("String Implementation");
      r = new LargeNumberString();
      s = new LargeNumberString();
      t = new LargeNumberString();
      r.inputValue();
      s.inputValue();
      t.inputValue();
      r.add(s);
      r.printValue();
      r.subtract(t);
      r.printValue();
      System.out.println();

   }
}
