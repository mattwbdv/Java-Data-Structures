import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Defines a library of selection methods on Collections.
 *
 * @author  Matt Koenig (mdk0027@auburn.edu)
 * @author  Professor, Original Author Dean Hendrix (dh@auburn.edu)
 * @version June 1, 2020
 *
 */

public final class Selector {

/**
 * Can't instantiate this class.
 *
 * D O   N O T   C H A N G E   T H I S   C O N S T R U C T O R
 *
 */
   private Selector() { }


   /**
    * Returns the minimum value in the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty, this method throws a
    * NoSuchElementException. This method will not change coll in any way.
    *
    * @param coll    the Collection from which the minimum is selected
    * @param comp    the Comparator that defines the total order on T
    * @return        the minimum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T min(Collection<T> coll, Comparator<T> comp) {
         // throws error if incorrect input
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      
      Iterator<T> itr = coll.iterator();
         
         // pulls out the first then loops to find any lower 
      T newMin = itr.next();
      while (itr.hasNext()) {
         T challenger = itr.next();
         if (comp.compare(challenger, newMin) < 0) {
            newMin = challenger;
         }
      }
      return newMin;

   }
   


   /**
    * Selects the maximum value in the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty, this method throws a
    * NoSuchElementException. This method will not change coll in any way.
    *
    * @param coll    the Collection from which the maximum is selected
    * @param comp    the Comparator that defines the total order on T
    * @return        the maximum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T max(Collection<T> coll, Comparator<T> comp) {
      // throws error if incorrect input
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      
      Iterator<T> itr = coll.iterator();
         
      // pulls out the first then loops to find any lower 
      T newMin = itr.next();
      while (itr.hasNext()) {
         T challenger = itr.next();
         if (comp.compare(challenger, newMin) > 0) {
            newMin = challenger;
         }
      }
      return newMin;

   }



   /**
    * Selects the kth minimum value from the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty or if there is no kth minimum
    * value, this method throws a NoSuchElementException. This method will not
    * change coll in any way.
    *
    * @param coll    the Collection from which the kth minimum is selected
    * @param k       the k-selection value
    * @param comp    the Comparator that defines the total order on T
    * @return        the kth minimum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T kmin(Collection<T> coll, int k, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty() || k > coll.size() || k < 1) {
         throw new NoSuchElementException();
      }
      //Collection to arraylist for sorting 
      ArrayList<T> sortedItems = new ArrayList<T>();
      Iterator<T> itr = coll.iterator();
      while (itr.hasNext()) {
         sortedItems.add(itr.next());
      }

      //Sort the list
      java.util.Collections.sort(sortedItems, comp); 

      //Solve single item use-case
      if (k == 1) {
         return sortedItems.get(0);
      }

      int uniques2 = 1;
      T kmax = null;
      T last = sortedItems.get(0);

      for (int j = 0; j < sortedItems.size() - 1; j++) {
         if (sortedItems.get(j + 1).equals(last) != true) {
            uniques2++;

            if (k == uniques2) {
               kmax = sortedItems.get(j + 1);
            }
            
         } 

         last = sortedItems.get(j + 1);
      }

      if (k > uniques2) {
         throw new NoSuchElementException();
      }

      return kmax;
   }


   /**
    * Selects the kth maximum value from the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty or if there is no kth maximum
    * value, this method throws a NoSuchElementException. This method will not
    * change coll in any way.
    *
    * @param coll    the Collection from which the kth maximum is selected
    * @param k       the k-selection value
    * @param comp    the Comparator that defines the total order on T
    * @return        the kth maximum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T kmax(Collection<T> coll, int k, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty() || k > coll.size() || k < 1) {
         throw new NoSuchElementException();
      }
      //Collection to arraylist for sorting 
      ArrayList<T> sortedItems = new ArrayList<T>();
      Iterator<T> itr = coll.iterator();
      while (itr.hasNext()) {
         sortedItems.add(itr.next());
      }

      //Sort the list
      java.util.Collections.sort(sortedItems, comp); 

      //Solve single item use-case
      if (k == 1) {
         return sortedItems.get(sortedItems.size() - 1);
      }

      int uniques2 = 1;
      T kmax = null;
      T last = sortedItems.get(sortedItems.size() - 1);

      for (int j = sortedItems.size() - 1; j > 0; j--) {
         if (sortedItems.get(j - 1).equals(last) != true) {
            uniques2++;

            if (k == uniques2) {
               kmax = sortedItems.get(j - 1);
            }

         } 

         last = sortedItems.get(j - 1);
      }

      if (k > uniques2) {
         throw new NoSuchElementException();
      }

      return kmax;
   }


   /**
    * Returns a new Collection containing all the values in the Collection coll
    * that are greater than or equal to low and less than or equal to high, as
    * defined by the Comparator comp. The returned collection must contain only
    * these values and no others. The values low and high themselves do not have
    * to be in coll. Any duplicate values that are in coll must also be in the
    * returned Collection. If no values in coll fall into the specified range or
    * if coll is empty, this method throws a NoSuchElementException. If either
    * coll or comp is null, this method throws an IllegalArgumentException. This
    * method will not change coll in any way.
    *
    * @param coll    the Collection from which the range values are selected
    * @param low     the lower bound of the range
    * @param high    the upper bound of the range
    * @param comp    the Comparator that defines the total order on T
    * @return        a Collection of values between low and high
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> Collection<T> range(Collection<T> coll, T low, T high, Comparator<T> comp) {
      // throws error if incorrect input
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      
      // throw a new iterator 
      Iterator<T> itr = coll.iterator();
         
      // create new arraylist and add to it all the items that qualify 
      ArrayList<T> newArray = new ArrayList<T>();
      while (itr.hasNext()) {
         T item = itr.next();
         if (comp.compare(item, low) >= 0 && comp.compare(item, high) <= 0) {
            newArray.add(item);
         }
      }
      if (newArray.isEmpty()) {
         throw new NoSuchElementException();
      }
      return newArray;
   }
   


   /**
    * Returns the smallest value in the Collection coll that is greater than
    * or equal to key, as defined by the Comparator comp. The value of key
    * does not have to be in coll. If coll or comp is null, this method throws
    * an IllegalArgumentException. If coll is empty or if there is no
    * qualifying value, this method throws a NoSuchElementException. This
    * method will not change coll in any way.
    *
    * @param coll    the Collection from which the ceiling value is selected
    * @param key     the reference value
    * @param comp    the Comparator that defines the total order on T
    * @return        the ceiling value of key in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T ceiling(Collection<T> coll, T key, Comparator<T> comp) {
      // throws error if incorrect input
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }

      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      
      // throw a new iterator 
      Iterator<T> itr = coll.iterator();

      // finds if eligible value exists
      boolean eligible = false;
      while (itr.hasNext()) {
         T current = itr.next();
         if (comp.compare(current, key) >= 0) {
            eligible = true;
         }
      }

      if (eligible == false) {
         throw new NoSuchElementException();

      }

      // we know eligible value exists, finding it    
      Iterator<T> itr2 = coll.iterator();

      T floor = null;
      boolean found = false;
      while (itr2.hasNext()) {
         T item = itr2.next();
         if (found == false && comp.compare(item, key) >= 0) {
            found = true;
            floor = item;
         }  
         if (found == true && comp.compare(item, key) >= 0 && comp.compare(item, floor) <= 0) {
            floor = item;
         }
      }
      return floor;

   }



   /**
    * Returns the largest value in the Collection coll that is less than
    * or equal to key, as defined by the Comparator comp. The value of key
    * does not have to be in coll. If coll or comp is null, this method throws
    * an IllegalArgumentException. If coll is empty or if there is no
    * qualifying value, this method throws a NoSuchElementException. This
    * method will not change coll in any way.
    *
    * @param coll    the Collection from which the floor value is selected
    * @param key     the reference value
    * @param comp    the Comparator that defines the total order on T
    * @return        the floor value of key in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T floor(Collection<T> coll, T key, Comparator<T> comp) {
      // throws error if incorrect input
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }

      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      
      // throw a new iterator 
      Iterator<T> itr = coll.iterator();

      // finds if eligible value exists
      boolean eligible = false;
      while (itr.hasNext()) {
         T current = itr.next();
         if (comp.compare(current, key) <= 0) {
            eligible = true;
         }
      }

      if (eligible == false) {
         throw new NoSuchElementException();

      }

      // we know eligible value exists, finding it    
      Iterator<T> itr2 = coll.iterator();

      T floor = null;
      boolean found = false;
      while (itr2.hasNext()) {
         T item = itr2.next();
         if (found == false && comp.compare(item, key) <= 0) {
            found = true;
            floor = item;
         }  
         if (found == true && comp.compare(item, key) <= 0 && comp.compare(item, floor) >= 0) {
            floor = item;
         }
      }
      return floor;

   }
}
