import java.io.*;
import java.util.*;

import java.util.stream.Collectors;

/**
 * Provides an implementation of the WordLadderGame interface. The lexicon is stored as a HashSet of
 * Strings.
 *
 * @author Matt Koenig (mdk0027@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version 2018-04-06
 */
public class Doublets implements WordLadderGame {

  // The word list used to validate words.
  // Must be instantiated and populated in the constructor.
  private HashSet<String> lexicon;
  private Node first;
  private int size;
  private Node last;

  private class Node {

    private String element;
    private Node next;

    public Node(Node val) {
      element = val.element;
      next = val.next;
    }
    public Node(String val, Node i) {
      element = val;
      next = i;
    }

  }

  /**
   * Instantiates a new instance of Doublets with the lexicon populated with the strings in the
   * provided InputStream. The InputStream can be formatted in different ways as long as the first
   * string on each line is a word to be stored in the lexicon.
   */
  public Doublets(InputStream in) {
    try {
      lexicon = new HashSet<String>();
      Scanner s = new Scanner(new BufferedReader(new InputStreamReader(in)));
      while (s.hasNext()) {
        String str = s.next();
        lexicon.add(str.toUpperCase());
        s.nextLine();
      }
      in.close();
    } catch (java.io.IOException e) {
      System.err.println("Error reading from InputStream.");
      System.exit(1);
    }
  }

  //////////////////////////////////////////////////////////////
  // ADD IMPLEMENTATIONS FOR ALL WordLadderGame METHODS HERE  //
  //////////////////////////////////////////////////////////////

  public boolean isWord(String str) {
    return lexicon.contains(str.toUpperCase());
  }

  public int getWordCount() {
    return lexicon.size();
  }

  public int getHammingDistance(String str1, String str2) {
    int distance = 0;
    if (str1.length() != str2.length()) {
      return -1;
    } else {
      for (int i = 0; i < str1.length(); i++) {
        if (str1.charAt(i) != str2.charAt(i)) {
          distance++;
        }
      }
    }
    return distance;
  }

  public List<String> getNeighbors(String word) {
    List<String> neighbors = new ArrayList<String>();

    word = word.toUpperCase();
    for (int i = 0; i < word.length(); i++) {
      for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
        StringBuilder tmp = new StringBuilder(word);
        tmp.setCharAt(i, alphabet);
        if ((isWord(tmp.toString().toUpperCase())) && (!tmp.toString().toUpperCase().equals(word.toUpperCase())) && (getHammingDistance(tmp.toString().toUpperCase(), word.toUpperCase()) == 1)) {
          neighbors.add(tmp.toString());
        }
      }
    }
    return neighbors;
    }

  public boolean isWordLadder(List<String> sequence) {
    if (sequence.size() == 0) {
      return false;
    }
    for (int i = 0; i < sequence.size() - 1; i++) {
      if ((!isWord(sequence.get(i)))
          || (getHammingDistance(sequence.get(i), sequence.get(i + 1)) != 1)) {
        return false;
      }
      }
    return true;
  }

  public List<String> getMinLadder(String start, String end) {
    start = start.toUpperCase();
    end = end.toUpperCase();
    List<String> answer = new ArrayList<String>();
    if (start.equals(end)) {
      answer.add(start.toLowerCase());
      return answer;
    } else if (start.length() != end.length()) {
      return answer;
    } else if (!isWord(start) || !isWord(end)) {
      return answer;
    }

    try {
    Deque<Node> queue = new LinkedList<Node>();
    TreeSet<String> memory = new TreeSet<>();
    Node first = new Node(start, null);

    queue.addLast(first);
    memory.add(first.element);

    Node tmp = queue.removeFirst();
    while(!tmp.element.equals(end)) {
      List<String> neighbors = getNeighbors(tmp.element);
      for (String s: neighbors) {
        if(!memory.contains(s)) {
          Node newNode = new Node(s, tmp);
          queue.addLast(newNode);
          memory.add(newNode.element);
          }
        }
      tmp = queue.removeFirst();
    }


      Node tmp2 = tmp;
      while (tmp2.next != null) {
        answer.add(tmp2.element);
        tmp2 = tmp2.next;
      }
    answer.add(first.element);

      Collections.reverse(answer);

    return answer;
} catch (NoSuchElementException ex) {
      return answer;


    }

  }

}
