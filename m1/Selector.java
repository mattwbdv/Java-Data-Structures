import java.util.Arrays;

/**
 * Defines a library of selection methods on arrays of ints.
 *
 * @author Matt Koenig (mdk0027@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version May 24, 2020
 *
 */
public final class Selector {

    /**
     * Can't instantiate this class.
     *
     * D O N O T C H A N G E T H I S C O N S T R U C T O R
     *
     */
   private Selector() {
   }

    /**
     * Selects the minimum value from the array a. This method throws
     * IllegalArgumentException if a is null or has zero length. The array a is not
     * changed by this method.
     */
   public static int min(final int[] a) {
        // throws error if incorrect input
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int min = a[0];

        // iterates and finds min
      for (int j = 0; j < a.length; j++) {
         if (a[j] < min) {
            min = a[j];
         }
      }
      return min;
   }

    /**
     * Selects the maximum value from the array a. This method throws
     * IllegalArgumentException if a is null or has zero length. The array a is not
     * changed by this method.
     */
   public static int max(final int[] a) {
        // throws error if incorrect input
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int max = a[0];
        // iterates and finds max
      for (int j = 0; j < a.length; j++) {
         if (a[j] > max) {
            max = a[j];
         }
      }
      return max;
   }

    /**
     * Selects the kth minimum value from the array a. This method throws
     * IllegalArgumentException if a is null, has zero length, or if there is no kth
     * minimum value. Note that there is no kth minimum value if k < 1, k >
     * a.length, or if k is larger than the number of distinct values in the array.
     * The array a is not changed by this method.
     */
   public static int kmin(final int[] a, final int k) {
      if (a == null || a.length == 0 || k > a.length || k < 1) {
         throw new IllegalArgumentException();
      }

      int[] b = new int[a.length];
      for (int e = 0; e < a.length; e++) {
         b[e] = a[e];
      }

      Arrays.sort(b);

      int uniques = 1;
      for (int i = 0; i < b.length - 1; i++) {
         if (b[i] != b[i + 1]) {
            uniques++;
         }
      }

      if (k > uniques) {
         throw new IllegalArgumentException();

      }

      int counter = 0;
      int[] c = new int[uniques];
      for (int s = 0; s < b.length - 1; s++) {
         if (b[s] != b[s + 1]) {
            c[counter] = b[s];
            counter++;
         }
      }
      if (b.length > 2 && b[b.length - 1] == b[b.length - 2]) {
         c[counter] = b[b.length - 1];
      }
      
      if (b.length > 1) {
         if (b[b.length - 2] != b[b.length - 1]) {
            c[counter] = b[b.length - 1];
         } else if (b.length == 2 && b[b.length - 2] == b[b.length - 1]) {
            return b[0];
         }
      } else {
         return b[0];
      }

      final int answer = c[k - 1];
      return answer;
   }

    /**
     * Selects the kth maximum value from the array a. This method throws
     * IllegalArgumentException if a is null, has zero length, or if there is no kth
     * maximum value. Note that there is no kth maximum value if k < 1, k >
     * a.length, or if k is larger than the number of distinct values in the array.
     * The array a is not changed by this method.
     */
   public static int kmax(final int[] a, final int k) {
      // find any that clearly violate terms 
      if (a == null || a.length == 0 || k > a.length || k < 1) {
         throw new IllegalArgumentException();
      }

      // build sorted array 
      int[] b = new int[a.length];
      for (int e = 0; e < a.length; e++) {
         b[e] = a[e];
      }
      Arrays.sort(b);

      // determine number of unique values in array 
      int uniques = 1;
      for (int i = 0; i < b.length - 1; i++) {
         if (b[i] != b[i + 1]) {
            uniques++;
         }
      }

      // throw out anything looking for ineligible unique value 
      if (k > uniques) {
         throw new IllegalArgumentException();
      }

      // build new array of just unique values 
      int counter = 0;
      int[] c = new int[uniques];
      for (int s = 0; s < b.length - 1; s++) {
         if (b[s] != b[s + 1]) {
            c[counter] = b[s];
            counter++;
         }
      } 
      if (b.length > 2 && b[b.length - 1] == b[b.length - 2]) {
         c[counter] = b[b.length - 1];
      }
      if (b.length > 1) {
         if (b[b.length - 2] != b[b.length - 1]) {
            c[counter] = b[b.length - 1];
         } else if (b.length == 2 && b[b.length - 2] == b[b.length - 1]) {
            return b[0];
         }
      } else {
         return b[0];
      }

      // return answer
      final int answer = c[c.length - k];
      return answer;
   }
    

    /**
     * Returns an array containing all the values in a in the range [low..high];
     * that is, all the values that are greater than or equal to low and less than
     * or equal to high, including duplicate values. The length of the returned
     * array is the same as the number of values in the range [low..high]. If there
     * are no qualifying values, this method returns a zero-length array. Note that
     * low and high do not have to be actual values in a. This method throws an
     * IllegalArgumentException if a is null or has zero length. The array a is not
     * changed by this method.
     */
   public static int[] range(final int[] a, final int low, final int high) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int arrayLength = 0;

        // grab length of array we need to make
      for (int i = 0; i < a.length; i++) {
         if (a[i] >= low && a[i] <= high) {
            arrayLength++;
         }
      }

        // build new array
      final int[] newArray = new int[arrayLength];
      int arrayPosition = 0;
        // newArray[0] = low;
        // newArray[arrayLength + 1] = high;

      for (int i = 0; i < a.length; i++) {
         if (a[i] >= low && a[i] <= high) {
            final int newNum = a[i];
            newArray[arrayPosition] = newNum;
            arrayPosition++;
         }
      }
      return newArray;
   }

    /**
     * Returns the smallest value in a that is greater than or equal to the given
     * key. This method throws an IllegalArgumentException if a is null or has zero
     * length, or if there is no qualifying value. Note that key does not have to be
     * an actual value in a. The array a is not changed by this method.
     */
   public static int ceiling(final int[] a, int key) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }

      boolean eligible = false;
      for (int j = 0; j < a.length; j++) {
         if (a[j] >= key) {
            eligible = true;
         }
      }

      if (eligible == false) {
         throw new IllegalArgumentException();

      }

      int ceiling = 0;
      boolean found = false;
      while (found == false) {
         for (int i = 0; i < a.length; i++) {
            ceiling = a[i];
            if (ceiling == key) {
               found = true;
               break;
            }
         }
         key++;
      }
      return ceiling;
   }

    /**
     * Returns the largest value in a that is less than or equal to the given key.
     * This method throws an IllegalArgumentException if a is null or has zero
     * length, or if there is no qualifying value. Note that key does not have to be
     * an actual value in a. The array a is not changed by this method.
     */
   public static int floor(final int[] a, int key) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }

      boolean eligible = false;
      for (int j = 0; j < a.length; j++) {
         if (a[j] <= key) {
            eligible = true;
         }
      }

      if (eligible == false) {
         throw new IllegalArgumentException();

      }

      int ceiling = 0;
      boolean found = false;
      while (found == false) {
         for (int i = 0; i < a.length; i++) {
            ceiling = a[i];
            if (ceiling == key) {
               found = true;
               break;
            }
         }
         key--;
      }
      return ceiling;
   }

}
