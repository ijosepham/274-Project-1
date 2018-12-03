/**
 * @author: Joey Pham
 * @date: 24 September 2018
 * @description: user is asked if they'd like to hunt for treasure or not. if they want to hunt, prompt them for coordinates to search.
 *               will keep prompting if area is alraedy searched. else, check the spot and the surrounding area. user is given a hint, X, or P.
 *               X if treasure, P if pirate, H if adjacent and/or diagonal to treasure, or C if none of the above. if the spot is H or X, reveal pirate's location.
 *               to win, find all the treasures without getting caught by the pirate. the pirate moves in a random direction after each guess. 
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Math;
import java.io.File;

public class Project1 { // program 2
    public static void main(String[] args) {
        String [][] answerKey = loadAnswerKey();
        hunt(answerKey, false); // pass in arrays, 5 treasuresLeft, intial pirate location, and not yet repeated
    }
    
    /**
     *plays the game for the user. will keep looping game until user quits/says no to replay
     *@param repeated this to keep track if repeated or not. if replayed x times, will say goodbye x times. this solves that problem.
     */
    public static void hunt(String[][] answerKey, boolean repeated) {
        // initialize
        String [][] userBoard = MapGenerator.createBlankMap(); // creates ~'s
        //String [][] cheatBoard = answerKey;
        String [][] pirateBoard = new String[9][9]; // create map for pirate location
        int pirateY = (int) (Math.random() * 9); // rand spot for pirate
        int pirateX = (int) (Math.random() * 9); // 0-8
        int treasuresLeft = 5; // treasure counter
        boolean wantReplay = true; 
        boolean isGameFinished = false; // if lost/won/quit
        boolean isNew = false; // spot checked is new or naw
        int y = 0;
        int x = 0;
        String clue = "";
        boolean quit = false; // this made because if game lost, menuchoice = 2 which is same as quit option
        // so quit option is here to show INTENDED quit, while losing is unintended quit
        boolean firstTime = true; // this made so wont prompt replay if quits immediately

        // start program
        displayBoard(userBoard, treasuresLeft, false, pirateY, pirateX); // show the board, then go into hunt
        //cheats(answerKey, cheatBoard, true, pirateY, pirateX);
        int menuChoice = getMenuChoice(); // the displays menu and asks if wanna play
        if (menuChoice == 2) {
            quit = true;
        }
        // should go straight to middle of all while loops after this
        while (wantReplay && !quit) { // if wanna replay 
            while (!isGameFinished) { // if quit/lost/won
                while (menuChoice != 2) { // if not quit yet
                    while (!isNew) { // check if coordinate isn't already revealed
                        y = getY(); // prompt for y, row
                        x = getX(); // x, column
                        isNew = isNewSpot(y, x, userBoard); // check if new, loops until is new
                    }
                    // immediately checks if spot is pirate. if pirate, stop game. if not, keep going
                    if (y == pirateY && x == pirateX) { // if spot is pirate's info 
                        userBoard[y][x] = "P"; // set the spot as P for pirate B^)
                        displayBoard(userBoard, treasuresLeft, false, pirateY, pirateX); // prints board with P
                        System.out.print("\n" + "Yar! I got ye! Give me back any treasure ye stole!" + "\n" + "You lost!" + "\n");
                        menuChoice = 2; // leave current while loop
                        isGameFinished = true; // lose, jump to next while loop
                    } else { // if spot isn't a pirate
                        clue = giveClue(answerKey, y, x); // gives back treasure, hot, cold
                        userBoard[y][x] = clue; // assign the spot to the hint
                        if (clue == "X") { // if spot is X
                            treasuresLeft = treasuresLeft - 1; // decrement treasure 
                            displayBoard(userBoard, treasuresLeft, false, pirateY, pirateX); // reprint map with X and pirate
                            if (treasuresLeft == 0) { // if all treasure is found
                                System.out.print("\n" + "All me booty is missing! Yar! I'll get ye next time!" + "\n" + "You won!" + "\n");
                                isGameFinished = true;
                                menuChoice = 2; // should jump to replay after this
                            } else { // won't print this out if it's the last treasure found
                                System.out.print("\n" + "Me booty!");
                            }
                        } else if (clue == "H") { // if spot H
                            displayBoard(userBoard, treasuresLeft, true, pirateY, pirateX); // reprint map with the hint and pirate
                            System.out.print("\n" + "Get away from me booty!");
                        } else { // if spot is cold, no pirate
                            displayBoard(userBoard, treasuresLeft, false, pirateY, pirateX);
                        } 
                    }
                    if (!isGameFinished) { // gets here if user hasn't lost or won
                        // move pirate 
                        int up = (pirateY - 1); // directions
                        int down = (pirateY + 1);
                        int left = (pirateX - 1);
                        int right = (pirateX + 1);
                        boolean moved = false;
                        int [] randYDir = {up, down}; // array for y movements
                        int [] randXDir = {left, right}; // array for x movements
                        int [][] randDir = {randYDir, randXDir}; // array of arrays
                        while (!moved) {
                            try{ // please work ***********IF pirateY GOES OUT OF BOUNDS, WILL keep trying
                                int [] dir = randDir[(int) (Math.random() * 2)]; // choose random array
                                if (dir == randYDir) { // if y array chosen
                                    pirateY = dir[(int) (Math.random() * 2)]; // choose random y dir
                                    pirateBoard[pirateY][pirateX] = "P"; // update board
                                } else if (dir == randXDir) { // if x array
                                    pirateX = dir[(int) (Math.random() * 2)]; // choose random x dir
                                    pirateBoard[pirateY][pirateX] = "P"; // update board
                                }
                                moved = true;
                            } catch (ArrayIndexOutOfBoundsException exception) {
                            }
                        }
                        //cheats(answerKey, cheatBoard,  true, pirateY, pirateX);
                        menuChoice = getMenuChoice(); // ask if wanna keep hunting
                        isNew = false; // revert back to false so will keep checking for new x,y values
                        if (menuChoice == 2) { // if they choose to quit
                            quit = true; // need this because if game lost, menuChoice = 2 also, so quit to distinguish intended stop
                        }
                    }
                    if (firstTime == true && !isGameFinished){ // if quit on first prompt
                        firstTime = false; // flip these so they go through loops again
                        isNew = false;
                    }
                }
                if (quit == true || (firstTime == true && quit == true)) { // don't ask for replay if user intentionally quit
                    wantReplay = false;
                    isGameFinished = true;
                } // skips this statement if won/lost, not intentionally quit
            }
            if (quit != true) { // if the user lost/won, but didn't quit the game, comes here
                wantReplay = getReplay(); // prompts for replay
                if (wantReplay == true) { // if want replay
                    System.out.print("\n"); // space to look better
                    hunt(answerKey, true); // recurse
                    quit = true; // only gets here if wants to stop playing, will leave the loop and end program 
                } // skip if user doesnt wanna play anymore
            }
        } // comes here when done   
        if (!repeated) { // only says this once, cause will print multiple times if multiple games are played
            System.out.println("\n" +"Yar! Scram, ye landlubber!");
        }
    }

    /**
     *reads in the treasuresLeft.txt file and writes it to an array. returns the array after finishing writing to it
     *@return array of a new map of X's
     */
    public static String[][] loadAnswerKey() { // read in map from treasure.txt and store into 2d array return array
        String[][] answerKey = new String [9][9]; // initialize
        try { // so it doesn't crash
            MapGenerator.generateMap(); // create a new treasure file from program 1
            Scanner read = new Scanner(new File("treasures.txt")); // reads the file
            do { // as long as we're not at the end of the file
                for (int y = 0; y < answerKey.length; y++) { // map.length == height
                    for(int x = 0; x < answerKey[y].length ; x++) { // width
                        String nextInput = read.next(); // reads the next integer and assigns it to a var
                        answerKey[y][x] = nextInput; // assigns the var to current position
                    }
                }
            } while (read.hasNext()); // while still have tokens
            read.close();
        } catch (FileNotFoundException fnf) { // stops from crashing if no txt file found
            System.out.println("File was not found");
        }
        return answerKey; // return array
    }

    /** 
     *resets the userBoard to be a 9x9 of ~'s'
     */
    public static void clearBoard(String[][] userBoard) {
        userBoard = MapGenerator.createBlankMap(); // calls in the function of creating 9x9 of ~'s from the program 1
    }

    /** 
     *prompts user for a column number and then converts it for coding 
     *@return column value converted from layman's to coding
     */
    public static int getX() {
        System.out.print("Enter a column number 1-9: "); // prompt
        int x = (CheckInput.getIntRange(1, 9) - 1); // makes sure users number is 1-9, then converts to 0-8
        System.out.print("\n");
        return x; // return converted value
    }
    
    /**
     *prompts user for a row letter, returns a number accordingly
     *@return row number converted from row letter
     */
    public static int getY() {
        Scanner input = new Scanner(System.in);
        System.out.print("\n" + "Enter a row letter A-I: "); // prompt
        String row = input.next(); // assign to variable to check with
        while (0 < 1) { // will keep looping until returns something
            if (row.equalsIgnoreCase("a")) { // if input is A/a
                return 0; // returns translated value of A -> 0
            } else if (row.equalsIgnoreCase("b")) {
                return 1;
            } else if (row.equalsIgnoreCase("c")) {
                return 2;
            } else if (row.equalsIgnoreCase("d")) {
                return 3;
            } else if (row.equalsIgnoreCase("e")) {
                return 4;
            } else if (row.equalsIgnoreCase("f")) {
                return 5;
            } else if (row.equalsIgnoreCase("g")) {
                return 6;
            } else if (row.equalsIgnoreCase("h")) {
                return 7;
            } else if (row.equalsIgnoreCase("i")) {
                return 8;
            } else { // if not any of the letters, comes here
                System.out.print("Please enter a letter within the range: ");
                row = input.next();
            }
        }
    }

    /**
     *prompts user for what they wanna do, play or nay. returns number of menu choice
     *@return 1 if hunt, 2 if quit
     */
    public static int getMenuChoice() {
        int menuChoice = 1; // initialize
        System.out.print("\n" + "Hunt for Delicious Booty?"); // print menu
        System.out.print("\n" + "1. Hunt!");
        System.out.print("\n" + "2. Quit");
        System.out.print("\n" + "Enter a number: "); // prompt
        menuChoice = CheckInput.getIntRange(1, 2); // checks if in menu range and converts to int
        return menuChoice;
    }

    /**
     *asks the user if they wanna keep playing and returns t/f accordingly
     *@return true if wants to play again, false if not
     */
    public static boolean getReplay() {
        System.out.print("\n" + "Play again? (Y/N): "); // prompt
        boolean wantReplay = CheckInput.getYesNo(); // return t/f
        return wantReplay;
    }

    /**
     *if the spot at the userBoard is a ~, that means it hasn't been checked yet
     *@param y passed in to check that coordinate
             x passed in to check coordinate
             userBoard passed in to check spot inside of array
     *@return true if spot is new, false if checked alraedy
     */
    public static boolean isNewSpot(int y, int x, String[][] userBoard) {
        if (!(userBoard[y][x].equals("~"))) { // if spot of display board is not a ~, which is an H, C, or X
            System.out.print("You have already checked this spot."); 
            return false;
        } else { // if spot is a ~
            return true;
        }
    }

    /**
     *prints what the user has revealed. if an H or X was just revealed, show the pirate also
     *@param userBoard passes this in so it can print it's values
     *       treasuresLeft so it can print the amount of treasuresLeft left for the user
     *       showPirate previous step to displayBoard is getclue. if get clue == H||X, showPirate = true.
     *                  if showPirate == true, print the P on the map, revealing pirate's location
     *        pirateY y value of pirate to print at
     *        pirateX x value of pirate to print at
     */
    public static void displayBoard(String[][] userBoard, int treasuresLeft, boolean showPirate, int pirateY, int pirateX) {
        System.out.print(treasuresLeft + " remaining treasures." + "\n"); // treasure counter for user
        System.out.print("  1 2 3 4 5 6 7 8 9"); // column numbers
        String hint = userBoard[pirateY][pirateX]; // keep track of the value from before temp replacing with P
        if (showPirate) { // if clue == H||X, assign to userBoard to print it out in the next step
            userBoard[pirateY][pirateX] = "P"; // change value so it'll print out the P
        }    
        for (int y = 0; y < userBoard.length; y++) { // .length == height
            for(int x = 0; x < userBoard[y].length ; x++) { // [y].length = width
                if (x == 0 && y == 0) { // first value of first row, show row value, A
                    System.out.print("\n" + "A " + userBoard[y][x]);
                } else if (x == 0 && y == 1) { // first value of second row, show row value, A
                    System.out.print("\n" + "B " + userBoard[y][x]);
                } else if (x == 0 && y == 2) { // first value of third row, show row value, A
                    System.out.print("\n" + "C " + userBoard[y][x]);
                } else if (x == 0 && y == 3) { // first value of fourth row, show row value, A
                    System.out.print("\n" + "D " + userBoard[y][x]);
                } else if (x == 0 && y == 4) { // first value of fifth row, show row value, A
                    System.out.print("\n" + "E " + userBoard[y][x]);
                } else if (x == 0 && y == 5) { // first value of sixth row, show row value, A
                    System.out.print("\n" + "F " + userBoard[y][x]);
                } else if (x == 0 && y == 6) { // first value of seventh row, show row value, A
                    System.out.print("\n" + "G " + userBoard[y][x]);
                } else if (x == 0 && y == 7) { // first value of eight row, show row value, A
                    System.out.print("\n" + "H " + userBoard[y][x]);
                } else if (x == 0 && y == 8) { // first value of ninth row, show row value, A
                    System.out.print("\n" + "I " + userBoard[y][x]);
                } else { // any value after the first in a row
                    System.out.print(" " + userBoard[y][x]);
                }
            }
        }
        if (showPirate) { // reverts value of the array back to normal. "P" was just temporary
            userBoard[pirateY][pirateX] = hint; // reverts value back after showing pirate
        }   
    }

    /**
     *checks spot in the answerkey of user's y and x and surrounding area. if spot is X, returns X.
     *if surrounding area is X, returns H. else, return C
     *@param answerKey array to compare to
     *       y to check spot and surrounding area
     *       x for coordinate
     *@return clue letter, "H", "C", or "X" 
     */
    public static String giveClue(String [][] answerKey, int y, int x) {
        // initialize
        String toReturn = "";
        int attempt = 0;
        int up = (y - 1);
        int down = (y + 1);
        int left = (x - 1);
        int right = (x + 1);
        if (answerKey[y][x].equals("X")) { // if spot is X
            return "X";
        }
        // if spot isn't X, start checking surroundings
        while (toReturn.equals("")) {
            try{ // go through all 8 spots to look for H
                if (attempt == 0){ // attempt starts at 0, check spot up and to the left of spot given
                    if (answerKey[up][left].equals("X")) { // if spot up and left == X
                        toReturn = "H"; // returns Hot
                    } else { // gets here if up left ISN'T out of bounds and up left spot isn't X
                        attempt = attempt + 1; // try the next spot 
                    }
                }
                if (attempt == 1) {
                    if (answerKey[up][x].equals("X")) {
                        toReturn = "H";
                    } else {
                        attempt = attempt + 1;
                    }
                }
                if (attempt == 2) {
                    if (answerKey[up][right].equals("X")) {
                        toReturn = "H";
                    } else {
                        attempt = attempt + 1;
                    }
                }
                if (attempt == 3) {
                    if (answerKey[y][left].equals("X")) {
                        toReturn = "H";
                    } else {
                        attempt = attempt + 1;
                    }
                }
                if (attempt == 4) {
                    if (answerKey[y][right].equals("X")) {
                        toReturn = "H";
                    } else {
                        attempt = attempt + 1;
                    }
                }
                if (attempt == 5) {
                    if (answerKey[down][left].equals("X")) {
                        toReturn = "H";
                    } else {
                        attempt = attempt + 1;
                    }
                }
                if (attempt == 6) {
                    if (answerKey[down][x].equals("X")) {
                        toReturn = "H";
                    } else {
                        attempt = attempt + 1;
                    }
                }
                if (attempt == 7) {
                    if (answerKey[down][right].equals("X")) {
                        toReturn = "H";
                    } else {
                        attempt = attempt + 1;
                    }
                }
                if (attempt == 8) {
                    toReturn = "C";
                }
            } catch (ArrayIndexOutOfBoundsException exception) { // if out of bounds
                attempt = attempt + 1; // increase attempt to scan the next spot if previous was out of bounds
            }
        }
        return toReturn;
    }
    
    /**
     * prints out the answerkey
     * @param answerKey array of the location of the treasures and the pirate
     * @param cheatBoard array to display 
     * @param showPirate whether or not to show the pirate
     * @param pirateY current pirate y location
     * @param pirateX current pirate x location
     */
    public static void cheats(String[][] answerKey, String[][] cheatBoard, boolean showPirate, int pirateY, int pirateX) {
        System.out.print("\n" + "\n" + "  1 2 3 4 5 6 7 8 9"); // column numbers
        String hint = cheatBoard[pirateY][pirateX]; // keep track of the value from before temp replacing with P
        if (showPirate) { // if clue == H||X, assign to userBoard to print it out in the next step
            cheatBoard[pirateY][pirateX] = "P"; // change value so it'll print out the P
        }    
        for (int y = 0; y < cheatBoard.length; y++) { // .length == height
            for(int x = 0; x < cheatBoard[y].length ; x++) { // [y].length = width
                if (x == 0 && y == 0) { // first value of first row, show row value, A
                    System.out.print("\n" + "A " + cheatBoard[y][x]);
                } else if (x == 0 && y == 1) { // first value of second row, show row value, A
                    System.out.print("\n" + "B " + cheatBoard[y][x]);
                } else if (x == 0 && y == 2) { // first value of third row, show row value, A
                    System.out.print("\n" + "C " + cheatBoard[y][x]);
                } else if (x == 0 && y == 3) { // first value of fourth row, show row value, A
                    System.out.print("\n" + "D " + cheatBoard[y][x]);
                } else if (x == 0 && y == 4) { // first value of fifth row, show row value, A
                    System.out.print("\n" + "E " + cheatBoard[y][x]);
                } else if (x == 0 && y == 5) { // first value of sixth row, show row value, A
                    System.out.print("\n" + "F " + cheatBoard[y][x]);
                } else if (x == 0 && y == 6) { // first value of seventh row, show row value, A
                    System.out.print("\n" + "G " + cheatBoard[y][x]);
                } else if (x == 0 && y == 7) { // first value of eight row, show row value, A
                    System.out.print("\n" + "H " + cheatBoard[y][x]);
                } else if (x == 0 && y == 8) { // first value of ninth row, show row value, A
                    System.out.print("\n" + "I " + cheatBoard[y][x]);
                } else { // any value after the first in a row
                    System.out.print(" " + cheatBoard[y][x]);
                }
            }
        }
        if (showPirate) { // reverts value of the array back to normal. "P" was just temporary
            cheatBoard[pirateY][pirateX] = hint; // reverts value back after showing pirate
        }   
    }
}