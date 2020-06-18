public class Driver {

   public static void main(String[] args) {
        int[] a1 = { -3, -7, -3, -3, -1, -9, -1, -1, -1, -5 };
        // int[] a2 = { 5, 9, 1, 7, 3 };
        // int[] a3 = { 8, 7, 6, 5, 4 };
        // int[] a4 = { 7 };



            // kmin
            System.out.println(toString(a1) + " 5 kmin = " + Selector.kmin(a1, 5));
            // System.out.println(toString(a2) + " 3 kmin = " + Selector.kmin(a2, 3));
            // System.out.println(toString(a3) + " 5 kmin = " + Selector.kmin(a3, 5));
            // System.out.println(toString(a4) + " 3 kmin = " + Selector.kmin(a4, 1));
            System.out.println();
         
            // kmax
            System.out.println(toString(a1) + " 1 kmax = " + Selector.kmax(a1, 1));
            // System.out.println(toString(a2) + " 3 kmax = " + Selector.kmax(a2, 3));
            // System.out.println(toString(a3) + " 5 kmax = " + Selector.kmax(a3, 5));
            // System.out.println(toString(a4) + " 3 kmax = " + Selector.kmax(a4, 1));
            System.out.println();
    }

    /**
     * Creates a string representation of an array.
     * 
     * @param a the provided array
     * @return a string representation of array a
     */
    static String toString(int[] a) {
        StringBuilder s = new StringBuilder();
        s.append("[");
        for (int i : a) {
            s.append(i + ", ");
        }
        s.delete(s.length() - 2, s.length());
        s.append("]");
        return s.toString();
    }

}