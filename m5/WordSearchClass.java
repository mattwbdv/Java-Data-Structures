// @author - mdk0027@auburn.edu

import java.util.*;
import java.io.*;

public class WordSearchClass implements WordSearchGame {

    private TreeSet<String> dictionary = new TreeSet<>();
    private String[][] board; // game board
    private boolean[][] boardVisited; // tracker board
    private ArrayList<Integer> path;
    private int boardDimensions;
    private String wordSoFar;

    public WordSearchClass() {
        dictionary = null;
        board = new String[boardDimensions][boardDimensions];
        boardVisited = new boolean[boardDimensions][boardDimensions];
    }

//     checks if it's on the board
    public List<Integer> isOnBoard(String wordToCheck) {

        if (wordToCheck == null) {
            throw new IllegalArgumentException();
        }
        if (dictionary == null) {
            throw new IllegalStateException();
        }

        createNullBoard();
        wordToCheck = wordToCheck.toUpperCase();
        path = new ArrayList<Integer>();

        for (int i = 0; i < boardDimensions; i++) { // for each board position
            for (int j = 0; j < boardDimensions; j++) {
                wordSoFar = "";

                // single letter words
//                if (wordToCheck.equals(board[i][j])) { // MIGHT SUCK, word to check == char?
//                    path.add((i * boardDimensions) + j);
//                }
                if ((wordToCheck.startsWith(board[i][j]))) { // if the contents of this position are a prefix
//                    wordToCheck = board[i][j];
//                    wordSoFar = board[i][j];
                    if (DFSOneWord(i, j, wordToCheck)) {
                        return path; // if depth first search -- RETURN PATH
                    }
                }
            }
        }
        return path;
    }

    // POTENTIALLY COMPLETE
    private boolean DFSOneWord(int i, int j, String wordToCheck) {
        wordToCheck = wordToCheck.toUpperCase();
        if (i < 0 || j < 0 || i >= boardDimensions || j >= boardDimensions) {
            return false;
        }

        if (boardVisited[i][j]) {
            return false;
        }

        if (!wordToCheck.startsWith(wordSoFar.toString())) {
            return false;
        }

        // Now we should visit and consider this position
        int rowMajor = (i * boardDimensions) + j;
        path.add(rowMajor); // add the row-major number of the current board position to path;

        boardVisited[i][j] = true; // mark as visited

        wordSoFar = wordSoFar.concat((board[i][j].toUpperCase())); // add the contents of the current board position to wordSoFar


        // is wordSoFar = wordToCheck? -- RETURN TRUE - might want to remove?
        if (wordSoFar.equals(wordToCheck)) {
            return true;
        }

        // continue the search for each neighbor
        for (int m = i - 1; m < i + 2; m++) { // for each neighbor
            for (int n = j - 1; n < j + 2; n++) {
                if (!(m == i && n == j) && (DFSOneWord(m, n, wordToCheck))) {
                    return true;
                }
            }
        }

        // clean up and backtrack -- RETURN FALSE
        if (wordSoFar.length() > 0) {
            int endIndex = wordSoFar.length() - board[i][j].length();
            wordSoFar = wordSoFar.substring(0, endIndex);
        }
        path.remove(Integer.valueOf(rowMajor)); // remove row-major number from path
        boardVisited[i][j] = false; // set the current board position as not visited
        return false;

    }

    // POTENTIALLY COMPLETE
    public SortedSet<String> getAllValidWords(int minimumWordLength) {
        if (dictionary == null) {
            throw new IllegalStateException();
        }
        if (minimumWordLength < 1) {
            throw new IllegalArgumentException();
        }

        // iterate over lexicon and ask if each word isOnBoard
        SortedSet<String> answer = new TreeSet();
        for (String s : dictionary) {
            if (s.length() >= minimumWordLength) {
                if (!isOnBoard(s).isEmpty()) {
                    answer.add(s);
                }
            }
        }
        return answer;
    }

    ////// COMPLETED METHODS /////////////

    // COMPLETED - gets the board in string format for the console
    public String getBoard() {
        String stringBoard = "";
        for (int i = 0; i < boardDimensions; i++) {
            if (i > 0) {
                stringBoard += "\n";
            }
            for (int j = 0; j < boardDimensions; j++) {
                stringBoard += board[i][j] + " ";
            }
        }
        return stringBoard;
    }

    // COMPLETED - makes null board
    public void createNullBoard() {
        boardVisited = new boolean[boardDimensions][boardDimensions];
        for (boolean[] v : boardVisited) {
            Arrays.fill(v, false);
        }
    }

    // COMPLETED - calls isOnBoard for each string in words and if it meets minimum
    // WordLength
    // requirement it returns its score
    public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
        if (minimumWordLength < 1) {
            throw new IllegalArgumentException();
        }
        if (dictionary == null) {
            throw new IllegalStateException();
        }
        Iterator<String> itr = words.iterator();
        int score = 0;
        while (itr.hasNext()) {
            String tmp = itr.next();
            if (tmp.length() >= minimumWordLength && isValidWord(tmp) && !isOnBoard(tmp).isEmpty()) {
                score += (tmp.length() - minimumWordLength) + 1;
            }
        }
        return score;
    }

    // COMPLETED - Loads dictionary from file
    public void loadLexicon(String fileName) {
        createNullBoard();
        if (fileName == null) {
            throw new IllegalArgumentException();
        }
        dictionary = new TreeSet<String>();
        try {
            Scanner s = new Scanner(new BufferedReader(new FileReader(new File(fileName))));
            while (s.hasNext()) {
                String str = s.next();
                boolean added = dictionary.add(str.toUpperCase());
                s.nextLine();
            }
            boolean lexLoaded = true;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error loading word list: " + fileName + ": " + e);
        }
    }

    // COMPLETED - checks if a word is in the dictionary
    public boolean isValidWord(String wordToCheck) {
        return dictionary.contains(wordToCheck.toUpperCase());
    }

    // COMPLETED - checks if a string is a prefix for a word in the dictionary
    public boolean isValidPrefix(String prefixToCheck) {
        prefixToCheck = prefixToCheck.toUpperCase();
        String tmp = dictionary.ceiling(prefixToCheck);
        if (tmp != null) {
            return tmp.startsWith(prefixToCheck);
        }
        return false;
    }

    // COMPLETED - sets the board
    public void setBoard(String[] letterArray) {
        if (letterArray == null) {
            throw new IllegalArgumentException();
        }

        boardDimensions = (int) Math.sqrt(letterArray.length);
        board = new String[boardDimensions][boardDimensions];
        boardVisited = new boolean[boardDimensions][boardDimensions];

        if ((boardDimensions * boardDimensions) != letterArray.length) {
            throw new IllegalArgumentException();
        }

        int index = 0;
        for (int i = 0; i < boardDimensions; i++) {
            for (int j = 0; j < boardDimensions; j++) {
                board[i][j] = letterArray[index];
                index++;
            }
        }
        createNullBoard();

    }

}