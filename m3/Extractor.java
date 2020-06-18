import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Extractor.java. Implements feature extraction for collinear points in two
 * dimensional data.
 *
 * @author Matt Koenig (mdk0027@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version June 12, 2020
 *
 */
public class Extractor {

   /** raw data: all (x,y) points from source data. */
   private Point[] points;

   /** lines identified from raw data. */
   private SortedSet<Line> lines;

   /**
    * Builds an extractor based on the points in the file named by filename.
    */
   public Extractor(final String filename) {
      try {
         final Scanner readFile = new Scanner(new File(filename));
         final int lines = readFile.nextInt();
         points = new Point[lines];
         int x = 0;
         int y = 0;
         for (int i = 0; i < lines; i++) {
            x = readFile.nextInt();
            y = readFile.nextInt();
            points[i] = new Point(x, y);
         }
         readFile.close();
      } catch (final Exception e) {
         System.out.println("Most likely: file not found!");
      }

   }

   /**
    * Builds an extractor based on the points in the Collection named by pcoll.
    *
    * THIS METHOD IS PROVIDED FOR YOU AND MUST NOT BE CHANGED.
    */
   public Extractor(final Collection<Point> pcoll) {
      points = pcoll.toArray(new Point[] {});
   }

   /**
    * Returns a sorted set of all line segments of exactly four collinear points.
    * Uses a brute-force combinatorial strategy. Returns an empty set if there are
    * no qualifying line segments.
    */
   public SortedSet<Line> getLinesBrute() {
      Line test = new Line();
      lines = new TreeSet<Line>();
      final int length = points.length;

      for (int i = 0; i < length; i++) {
         for (int j = 0; j < i; j++) {
            for (int a = 0; a < j; a++) {
               for (int g = 0; g < a; g++) {
                  test.add(points[i]);
                  test.add(points[j]);
                  test.add(points[a]);
                  test.add(points[g]);
                  if (test.length() == 4) {
                     lines.add(test);
                  }
                  test = new Line();

               }
            }
         }
      }
      return lines;
   }

   /**
    * Returns a sorted set of all line segments of exactly four collinear points.
    * Uses a more elegant strategy of checking against a reference point. Returns
    * an empty set if there are no qualifying line segments.
    */
   public SortedSet<Line> getLinesFast() {
      lines = new TreeSet<Line>();

      // Create a copy of points. This copy will
      // be sorted with respect to the slopeTo a
      // given reference point on each iteration
      // of the loop below.
      Point[] pointsBySlope = Arrays.<Point>copyOf(points, points.length);
      // Arrange points in ascending natural order.
      // This ensures that the reference points are
      // considered in ascending natural order, and
      // thus line segments are discovered starting
      // with their first (minimum) point.
      Arrays.sort(points);

      // On each iteration of the loop below, identify a set of
      // three or more points that are mutually collinear with
      // the reference point.
      for (int referencePoint = 0; referencePoint < points.length; referencePoint++) {

         // Sort into ascending order of slope to the reference point.
         // Ensures that points with equal slope to the reference point
         // will be in contiguous array indices.
         Arrays.<Point>sort(pointsBySlope, points[referencePoint].slopeOrder);

         // value for the number of points on the same line
         int sameLine = 0;
         // loop through subline and compare
         for (int subline = 0; subline < points.length - 1; subline += sameLine) {

            // within this run, see how many in a row are colinear
            sameLine = 0;
            while (subline + sameLine < points.length && points[referencePoint].slopeOrder
                  .compare(pointsBySlope[subline], pointsBySlope[subline + sameLine]) == 0) {
               sameLine++;
            }

            // check for run length >= 3 and if yes then add those to the line
            if (sameLine >= 3) {
               Line temp = new Line();
               temp.add(points[referencePoint]);
               int a = 0;
               while (a < sameLine) {
                  temp.add(pointsBySlope[a + subline]);
                  a++;
               }

               // add to the final line
               lines.add(temp);

            }

         }
      }

      // return the final line
      return lines;
   }
}