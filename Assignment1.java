/********************************
* COSC 2336.001                 *
* Spring 2014                   *
* Dr. Leonard Brown             *
* Main class for Assignment #2  *
*                               *
* This class creates and tests  *
* several LargeNumber objects.  *
*********************************/

class Assignment1 {
   public static void main (String[] args) {
      LargeNumberArray a, b, c;
      LargeNumberString r, s, t;
      LargeNumberLinkedList u, v, w;


      System.out.println("Array Implementation");
      a = new LargeNumberArray();
      b = new LargeNumberArray();
      c = new LargeNumberArray();  
      a.inputValue();
      b.inputValue();
      c.inputValue();
      a.add(b);
      a.printValue();
      a.multiplyByTen();
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
      r.multiplyByTen();
      r.printValue();
      r.subtract(t);
      r.printValue();
      System.out.println();
      
      System.out.println("Linked List Implementation");
      u = new LargeNumberLinkedList();
      v = new LargeNumberLinkedList();
      w = new LargeNumberLinkedList();
      u.inputValue();
      v.inputValue();
      w.inputValue();
      u.add(v);
      u.printValue();
      u.multiplyByTen();
      u.printValue();
      u.subtract(w);
      u.printValue();
      System.out.println();

   }
}
