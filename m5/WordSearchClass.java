// @author - mdk0027@auburn.edu

import java.util.*;
import java.io.*;

public class WordSearchClass implements WordSearchGame {

    private TreeSet<String> dictionary = new TreeSet<>();
    private String[][] board;
    private Boolean[][] boardVisited;

    private int boardDimensions;

    public WordSearchClass() {
        dictionary = null;
        // // fallback board
        // boardDimensions = 4;
        // board = new String[boardDimensions][boardDimensions];
        // board[0][0] = "E";
        // board[0][1] = "E";
        // board[0][2] = "C";
        // board[0][3] = "A";
        // board[1][0] = "A";
        // board[1][1] = "L";
        // board[1][2] = "E";
        // board[1][3] = "P";
        // board[2][0] = "H";
        // board[2][1] = "N";
        // board[2][2] = "B";
        // board[2][3] = "O";
        // board[3][0] = "Q";
        // board[3][1] = "T";
        // board[3][2] = "T";
        // board[3][3] = "Y";

        // // fallback visited board tracker
        // boardVisited = new Boolean[boardDimensions][boardDimensions];
        // boardVisited[0][0] = false;
        // boardVisited[0][1] = false;
        // boardVisited[0][2] = false;
        // boardVisited[0][3] = false;
        // boardVisited[1][0] = false;
        // boardVisited[1][1] = false;
        // boardVisited[1][2] = false;
        // boardVisited[1][3] = false;
        // boardVisited[2][0] = false;
        // boardVisited[2][1] = false;
        // boardVisited[2][2] = false;
        // boardVisited[2][3] = false;
        // boardVisited[3][0] = false;
        // boardVisited[3][1] = false;
        // boardVisited[3][2] = false;
        // boardVisited[3][3] = false;
    }

    // completed, loads dictionary
    public void loadLexicon(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException();
        }
        TreeSet<String> lexicon = new TreeSet<String>();
        try {
            Scanner s = new Scanner(new BufferedReader(new FileReader(new File(fileName))));
            while (s.hasNext()) {
                String str = s.next();
                boolean added = lexicon.add(str.toUpperCase());
                s.nextLine();
            }
            Boolean lexLoaded = true;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error loading word list: " + fileName + ": " + e);
        }
    }

    // completed, checks if a word is in the dictionary
    public boolean isValidWord(String wordToCheck) {
        return dictionary.contains(wordToCheck.toUpperCase());
    }

    // completed, checks if a string is a prefix for a word in the dictionary
    public boolean isValidPrefix(String prefixToCheck) {
        return dictionary.ceiling(prefixToCheck) != null;
    }

    // completed, sets the board
    public void setBoard(String[] letterArray) {
        if (letterArray == null) {
            throw new IllegalArgumentException();
        }

        boardDimensions = (int) Math.sqrt(letterArray.length);
        board = new String[boardDimensions][boardDimensions];

        int index = 0;
        for (int i = 0; i < boardDimensions; i++) {
            for (int j = 0; j < boardDimensions; j++) {
                board[i][j] = letterArray[index];
                index++;
            }
        }
    }

    // completed, gets the board in string format for the console
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

    // checks if it's on the board
    public List<Integer> isOnBoard(String wordToCheck) {
        List<Integer> path = new ArrayList<>();

        for (int i = 0; i < boardDimensions; i++) { // for each board position
            for (int j = 0; j < boardDimensions; j++) {
                if ((wordToCheck.startsWith(board[i][j]))) { // if the contents of this position are a prefix for
                                                             // wordToCheck
                    wordToCheck = board[i][j];

                    StringBuilder wordSoFar = new StringBuilder();
                    if (DFSOneWord(i, j, wordSoFar, wordToCheck, path)) {
                        return path; // if depth first search -- RETURN PATH
                    }
                }
            }
        }
        return path;
    }

    // depth first search
    private boolean DFSOneWord(int i, int j, StringBuilder wordSoFar, String wordToCheck, List<Integer> path) {
        if (i < 0 || j < 0 || i > boardDimensions || j > boardDimensions) {
            return false;
        }

        if (boardVisited[i][j] == true) {
            return false;
        }

        if (wordToCheck.startsWith(wordSoFar.toString()) == false) {
            return false;
        }

        // Now we should visit and consider this position
        int stringLength = board[i][j].toString().length();
        int rowMajor = (i * boardDimensions) + j;

        boardVisited[i][j] = true; // mark as visited

        wordSoFar.append((board[i][j])); // add the contents of the current board position to wordSoFar

        path.add(rowMajor); // add the row-major number of the current board position to path;

        // is wordSoFar = wordToCheck? -- RETURN TRUE
        if (wordSoFar.equals(wordToCheck)) {
            return true;
        }

        // continue the search for each neighbor - THIS MIGHT NOT CAPTURE DIAGONALS
        for (int m = i - 1; m < i + 2; m++) { // for each neighbor
            for (int n = j - 1; n < j + 2; n++) {
                if (DFSOneWord(m, n, wordSoFar, wordToCheck, path)) {
                    return true;
                }
            }
        }

        // clean up and backtrack -- RETURN FALSE
        wordSoFar.delete(wordSoFar.length() - stringLength, wordSoFar.length()); // remove boardPosition contents from
                                                                                 // wordsofar

        path.remove(Integer.valueOf(rowMajor)); // remove row-major number from path
        boardVisited[i][j] = false; // set the current board position as not visited
        return false;

    }

    // needs work
    // calls isOnBoard for each string in words and if it meets minimum WordLength
    // requirement it returns its score
    public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
        for (String s : words) {
            if (isOnBoard(s) != null && (s.length() >= minimumWordLength)) {
                return 1 + isOnBoard(s).size() - minimumWordLength;
            }
        }
        return 0;
    }

    // needs work - WHAT DO I DO WITH MINIMUM WORD LENGTH?
    public SortedSet<String> getAllValidWords(int minimumWordLength) {
        // iterate over lexicon and ask if each word isOnBoard

        SortedSet<String> answer = new TreeSet();
        for (String s : dictionary) {
            if (s.length() > minimumWordLength) {
                if (isOnBoard(s) != null) {
                    answer.add(s);
                }
            }
        }
        return answer;
    }

}