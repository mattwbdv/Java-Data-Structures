// @author - mdk0027@auburn.edu

import java.util.*;

public class WordSearchClass implements WordSearchGame {

    private TreeSet<String> dictionary = new TreeSet<>();
    private String[][] board;
    private Boolean[][] boardVisited;

    private int boardDimensions;

    public DoubleEndedClass() {
        dictionary = null; 
        // fallback board 
        boardDimensions = 4;
        board = new String[boardDimensions][boardDimensions];
        board[0][0] = "E"; 
        board[0][1] = "E"; 
        board[0][2] = "C"; 
        board[0][3] = "A"; 
        board[1][0] = "A"; 
        board[1][1] = "L"; 
        board[1][2] = "E"; 
        board[1][3] = "P"; 
        board[2][0] = "H"; 
        board[2][1] = "N"; 
        board[2][2] = "B"; 
        board[2][3] = "O"; 
        board[3][0] = "Q"; 
        board[3][1] = "T"; 
        board[3][2] = "T"; 
        board[3][3] = "Y";  

        // fallback visited board tracker
        boardVisited = new Boolean[boardDimensions][boardDimensions];
        boardVisited[0][0] = false; 
        boardVisited[0][1] = false; 
        boardVisited[0][2] = false;
        boardVisited[0][3] = false;
        boardVisited[1][0] = false;
        boardVisited[1][1] = false; 
        boardVisited[1][2] = false;
        boardVisited[1][3] = false; 
        boardVisited[2][0] = false;
        boardVisited[2][1] = false;
        boardVisited[2][2] = false;
        boardVisited[2][3] = false;
        boardVisited[3][0] = false; 
        boardVisited[3][1] = false;
        boardVisited[3][2] = false;
        boardVisited[3][3] = false;  
    }

    // completed, loads dictionary
    public void loadLexicon(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException();
        }
        lexicon = new TreeSet<String>();
        try {
            Scanner s = new Scanner(new BufferedReader(new FileReader(new File(fileName))));
            while (s.hasNext()) {
                String str = s.next();
                boolean added = lexicon.add(str.toUpperCase());
                s.nextLine();
            }
            lexLoaded = true;
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

        boardDimensions = Math.sqrt(letterArray.length);
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
                strBoard += "\n";
            }
            for (int j = 0; j < boardDimensions; j++) {
                stringBoard += board[i][j] + " ";
            }
        }
        return stringBoard;
    }

    // needs work - open question, is wordSoFar right on line 124? | do I return
    // path on line 108?
    public List<Integer> isOnBoard(String wordToCheck) {
        List<Int> path = new ArrayList<>();

        for (int i = 0; i < boardDimensions; i++) { // for each board position
            for (int j = 0; j < boardDimensions; j++) {
                if (board[i][j].isValidPrefix()) { // if the contents of this position are a prefix for wordToCheck
                    String wordSoFar = board[i][j];
                    if (DFSOneWord(i, j, wordToCheck, wordSoFar, path)) {
                        return path; // if depth first search -- RETURN PATH
                    }
                }
                index++;
            }
        }
        return path;
    }

    // depth first search
    public boolean DFSOneWord(int i, int j, String wordToCheck, StringBuilder wordSoFar, List<Integer> path) {

        String = currentChar = "";

        // base case -- RETURN FALSE
        // is the position on the board?
        if (board[i][j] == null) {
            return false;
        }

        // already visited this position?
        if (boardVisited[i][j] == true) {
            return false;
        }

        // a dead end? (wordSoFar does not begin wordToCheck)
        if (isOnBoard(wordToCheck) == false) {
            return false;
        }

        // not a base case
        // visit and process this position
        currentChar = board[i][j];
        temp = new Point(i, j);

        // mark it visited
        boardVisited[i][j] = true;

        // add the contents of the current board position to wordSoFar
        wordSoFar += currentChar;

        // add the row-major number to path
        path.add(i * boardDimensions + j);

        // is wordSoFar = wordToCheck? -- RETURN TRUE
        if (wordSoFar == wordToCheck) {
            return true;
        }

        // continue the search for each neighbor - THIS MIGHT NOT CAPTURE DIAGONALS 
        for (int m = i - 1; m < i + 1; m++) { // for each neighbor
            for (int n = j - 1; n < j + 1; n++) {
                if (DFSOneWord(m, n, wordToCheck, wordSoFar, path)) { // recursively "if" call DFSOneWord(i delta, j
                                                                      // delta, wordToCheck, wordSoFar, path) -- RETURN
                                                                      // TRUE
                    return true;
                }
            }
        }


        // clean up and backtrack -- RETURN FALSE
        // undo of the visit
        // mark unvisited
        boardVisited[i][j] = false;

        // remove boardPosition contents from wordsofar
        wordSoFar.remove(currentChar);
        // remove row-major number from path
        path.remove(i * boardDimensions + j); 

        return false;

    }

    // needs work
    // calls isOnBoard for each string in words and if it meets minimum WordLength
    // requirement it returns its score
    public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
        for ( String s: words) {
            if(isOnBoard(s) && isOnBoard(s).length >= minimumWordLength) {
                return 1 + isOnBoard(s).length - minimumWordLength;
            }
        }
        return 0;
    }

    // needs work - WHAT DO I DO WITH MINIMUM WORD LENGTH?
    public SortedSet<String> getAllValidWords(int minimumWordLength) {
        // iterate over lexicon and ask if each word isOnBoard

        int tmp = 0;
        for ( String s: dictionary ) {
            if(isOnBoard(s)) {
                tmp++;
            }
        }
        return tmp;
    }

}