//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           (MineSweeper)
// Files:           CConfig.java)
// Course:          (200, fall, 2017)
//
// Author:          (Thomas Grutsch)
// Email:           (tgrutsch@wisc.edu)
// Lecturer's Name: (Jim Williams)
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates 
// strangers, etc do.  If you received no outside help from either type of 
// source, then please explicitly indicate NONE.
//
// Persons:         (Jim Williams: helped me print the arrayMap)
// Online Sources:  (https://stackoverflow.com/questions/8023659/java-check-if-integer-is-multiple-of-a-number
//						-used in printMap method to print out multiples of 5.)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.Random;
import java.util.Scanner;

/**
 * This class runs the game MineSweeper. 
 * Constants used can be found in Config.java.
 * 
 * @author Thomas Grutsch
 *
 */

public class MineSweeper {

    /**
     * This is the main method for Mine Sweeper game!
     * This method contains the within game and play again loops and calls
     * the various supporting methods.
     *  
     * @param args (unused)
     */
    public static void main(String[] args) {
    	Scanner scnr = new Scanner(System.in);
    	Random rand =  new Random(Config.SEED);
	int width = 0; //columns
	int height = 0; //rows
	char playAgain = '-'; //for user to play the game again
	String userYOrN = ""; //used with playAgain
	int row = 0; //used to get a user row and column to check 
	int column = 0;
	int min = 1; //for promptUser with row and column 
	int numOfMines = 0; //this is so you don't call placeMines multiple times
    	
	System.out.println("Welcome to Mine Sweeper!"); // welcome message
	
    	do {  		
    		//gets user width and height 
    		String promptString = "What width of map would you like (" + 
    				Config.MIN_SIZE + " - " + Config.MAX_SIZE + "): ";
    		width = promptUser(scnr, promptString, Config.MIN_SIZE, Config.MAX_SIZE);
    		promptString = "What height of map would you like (" + 
    				Config.MIN_SIZE + " - " + Config.MAX_SIZE + "): ";
    		height = promptUser(scnr, promptString, Config.MIN_SIZE, Config.MAX_SIZE);
    	
    		System.out.println(""); //corrects spacing
    		
    		// initializes array and calling eraseMap method
    		char[][] mapArray = new char[height][width];
    		eraseMap(mapArray);
    		
    		//intializing mine array with userInput 
    		boolean[][] mapMine = new boolean[height][width];
    		
    		//places mine in array and prints number of mines
    		numOfMines = placeMines(mapMine, rand);
    		System.out.println("Mines: " + numOfMines);
    		
    		//calling simplePrintMap to print the map
    		printMap(mapArray);
    		
    		do { 
    			//prompts user for row and col
    			promptString = "row: ";
    			row = promptUser(scnr, promptString, min, height);
    			promptString = "column: ";
    			column = promptUser(scnr, promptString, min, width);
    		
    			//calls sweepLocation and checks if sweepAllNei.. needs to be called
    			sweepLocation(mapArray, mapMine, row - 1, column - 1);
    			if (mapArray[row - 1][column- 1] == Config.NO_NEARBY_MINE) {
    				sweepAllNeighbors(mapArray, mapMine, row - 1, column - 1);
    			}
    			
    			//updates map + says number of Mines
    			//determines if allSafeLocations has been reached 
    			if (allSafeLocationsSwept(mapArray, mapMine) || 
    					mapArray[row - 1][column- 1] == Config.SWEPT_MINE) {
    				showMines(mapArray, mapMine); 
    				printMap(mapArray);
    			} else {
    				System.out.println(); //to get the spacing right from columns to mines: #
    				System.out.println("Mines: " + numOfMines);
    				printMap(mapArray);
    			}
    		
    		// checks to see if allSafeLoc.. is false or if user Swept mine
    		} while (!allSafeLocationsSwept(mapArray, mapMine) 
    				&& mapArray[row - 1][column - 1] != Config.SWEPT_MINE); //makes sure user didn't hit a mine.
   
    		//this prints the Win/Lost message
   		if (allSafeLocationsSwept(mapArray, mapMine)) {
   			System.out.println("You Win!");
   		} else {
   			System.out.println("Sorry, you lost.");
   		}
    		
    		// asks user if they want to play again
    		System.out.print("Would you like to play again (y/n)? ");
    		userYOrN = scnr.next();
    		playAgain = userYOrN.charAt(0);
    	
    	} while (playAgain == 'y' || playAgain == 'Y'); //do is at the beginning of main
    	
    	//goodbye statement
    	System.out.println("Thank you for playing Mine Sweeper!");
    		
    }


    /**
     * This method prompts the user for a number, verifies that it is between min
     * and max, inclusive, before returning the number.  
     * 
     * If the number entered is not between min and max then the user is shown 
     * an error message and given another opportunity to enter a number.
     * If min is 1 and max is 5 the error message is:
     *      Expected a number from 1 to 5.  
     * 
     * If the user enters characters, words or anything other than a valid int then 
     * the user is shown the same message.  The entering of characters other
     * than a valid int is detected using Scanner's methods (hasNextInt) and
     * does not use exception handling.
     * 
     * Do not use constants in this method, only use the min and max passed
     * in parameters for all comparisons and messages.
     * Do not create an instance of Scanner in this method, pass the reference
     * to the Scanner in main, to this method.
     * The entire prompt should be passed in and printed out.
     *
     * @param in  The reference to the instance of Scanner created in main.
     * @param prompt  The text prompt that is shown once to the user.
     * @param min  The minimum value that the user must enter.
     * @param max  The maximum value that the user must enter.
     * @return The integer that the user entered that is between min and max, 
     *          inclusive.
     */
    public static int promptUser(Scanner in, String prompt, int min, int max) {
    		int userValue = 0;
    		System.out.print(prompt);
    		
    		//getting width then height from user
    		do {
    			if (in.hasNextInt()) {
    				userValue = in.nextInt();
    				in.nextLine();
    				if (userValue < min || userValue > max) {
        				System.out.println("Expected a number from " + min + 
        					" to " + max + ".");
        			}
    			} else {
    				in.nextLine();
    				System.out.println("Expected a number from " + min + 
        					" to " + max + ".");
    				
    				userValue = max + 1; //ensures do-while goes to false.	
    			} 
    		} while (userValue < min || userValue > max);
    		
        return userValue;
    }

    /**
     * This initializes the map char array passed in such that all
     * elements have the Config.UNSWEPT character.
     * Within this method only use the actual size of the array. Don't
     * assume the size of the array.
     * This method does not print out anything. This method does not
     * return anything.
     * 
     * @param map An allocated array. After this method call all elements
     *      in the array have the same character, Config.UNSWEPT. 
     */
    public static void eraseMap(char[][] map) {
    		int r = 0; //row
    		int c = 0; //column
    		
    		//gives every element the UWSEPT variable
    		for (r = 0; r < map.length; ++r) {
    			for (c = 0; c < map[r].length; ++c) {
    				map[r][c] = Config.UNSWEPT; 
    			}
    		}
    	
        return; 
    }    

    /**
     * This prints out a version of the map without the row and column numbers.
     * A map with width 4 and height 6 would look like the following: 
     *  . . . .
     *  . . . .
     *  . . . .
     *  . . . .
     *  . . . .
     *  . . . .
     * For each location in the map a space is printed followed by the 
     * character in the map location.
     * @param map The map to print out.
     */
    public static void simplePrintMap(char[][] map) {
    		int row = 0; //row
    		int col = 0; //column
    		
    		//actually prints the map
    		for (row = 0; row < map.length; ++row) {
    			for (col = 0; col < map[row].length; ++col) { 
    				System.out.print(" " + map[row][col]); 
    			}
    			System.out.println();
    		}
    	
        return; 
    }

    /**
     * This prints out the map. This shows numbers of the columns
     * and rows on the top and left side, respectively. 
     * map[0][0] is row 1, column 1 when shown to the user.
     * The first column, last column and every multiple of 5 are shown.
     * 
     * To print out a 2 digit number with a leading space if the number
     * is less than 10, you may use:
     *     System.out.printf("%2d", 1); 
     * 
     * @param map The map to print out.
     */
    public static void printMap(char[][] map) {
    		char widthChar = '-'; //char for header
    		char heightChar = '|'; //char for side bar
    		int row = 0;
    		
    		System.out.print("   "); //corrects spacing
    		
    		//prints the header
    		for (int i = 1; i <= map[row].length; ++i) { 
    			if (i % 5 == 0 || i == 1) { // gets us multiples of 5
    				if (i < 10 && i != 1) { // this is to check if it needs to print a space first
    					System.out.printf("%2d", i);
    				} else {
    					System.out.print(i);
    				}
    			} else if (i == map[row].length) {
    				if (i < 10) {
    					System.out.printf("%2d", i);
    				} else {
    					System.out.print(i);
    				}
    			} else {
    				System.out.print(widthChar + "" + widthChar);
    			}
    		}
    		
    		System.out.println(); //corrects spacing
    		
    		//prints the side bar
    		for (row = 0; row < map.length; ++row) {
    			for (int col = 0; col < map[row].length; ++col) {
    				int row1 = row + 1;
    				//always have a space before int/char except if int >= 10
    				if (row1 % 5 == 0 || row1 == 1) {
    					if (row1 < 10) {
        					System.out.printf("%2d", row1); //space in front
        				} else {
        					System.out.print(row1); //no space in front
        				}
    					while (col < map[row].length) { //prints map
    						System.out.print(" " + map[row][col]);
    						++col;
    					}
    				} else if (row1 == map.length) {
    					if (row1 < 10) {
        					System.out.printf("%2d", row1); //space in front
        				} else {
        					System.out.print(row1); //no space in front
        				}
    					while (col < map[row].length) { //prints the map
    						System.out.print(" " + map[row][col]);
    						++col;
    					}
    				} else {
    					System.out.print(" " + heightChar); //space in front
    					while (col < map[row].length) { //prints map
    						System.out.print(" " + map[row][col]);
    						++col;
    					}
    				}
    			}
			System.out.println();
		}
    	
        return; 
    }

    /**
     * This method initializes the boolean mines array passed in. A true value for
     * an element in the mines array means that location has a mine, false means
     * the location does not have a mine. The MINE_PROBABILITY is used to determine
     * whether a particular location has a mine. The randGen parameter contains the
     * reference to the instance of Random created in the main method.
     * 
     * Access the elements in the mines array with row then column (e.g., mines[row][col]).
     * 
     * Access the elements in the array solely using the actual size of the mines
     * array passed in, do not use constants. 
     * 
     * A MINE_PROBABILITY of 0.3 indicates that a particular location has a
     * 30% chance of having a mine.  For each location the result of
     *      randGen.nextFloat() < Config.MINE_PROBABILITY 
     * determines whether that location has a mine.
     * 
     * This method does not print out anything.
     *  
     * @param mines  The array of boolean that tracks the locations of the mines.
     * @param randGen The reference to the instance of the Random number generator
     *      created in the main method.
     * @return The number of mines in the mines array.
     */
    public static int placeMines(boolean[][] mines, Random randGen) {
    		int numMines = 0;
    		int row = 0;
    		int col = 0;
    		
    		// places mines
    		for (row = 0; row < mines.length; ++row) {
    			for (col = 0; col < mines[row].length; ++col) {
    				mines[row][col] = randGen.nextFloat() < Config.MINE_PROBABILITY; //how does this set anything to true
    			}
    		}
    		
    		// counts mines
    		for (row = 0; row < mines.length; ++row) {
    			for (col = 0; col < mines[row].length; ++col) {
    				if (mines[row][col] == true) {
    	    			++numMines;
    	    			}
    			}
    		}
    		
        return numMines;
    }

    /**
     * This method returns the number of mines in the 8 neighboring locations.
     * For locations along an edge of the array, neighboring locations outside of 
     * the mines array do not contain mines. This method does not print out anything.
     * 
     * If the row or col arguments are outside the mines array, then return -1.
     * This method (or any part of this program) should not use exception handling.
     * 
     * @param mines The array showing where the mines are located.
     * @param row The row, 0-based, of a location.
     * @param col The col, 0-based, of a location.
     * @return The number of mines in the 8 surrounding locations or -1 if row or col
     *      are invalid.
     */
    public static int numNearbyMines( boolean [][]mines, int row, int col) {
    		int numAdjMines = 0;
    		final int INVALID_INPUT = -1; //if user somehow enters invalid row/col
    		
    		if (row >= 0 && col >= 0 && row < mines.length && col < mines[row].length) {
    			if (row - 1 >= 0 && col - 1 >= 0) { //top left 
    				if (mines[row - 1][col - 1] == true) {
    					++numAdjMines;
    				}
    			} 
    			if (row - 1 >= 0) { //top middle
    				if (mines[row - 1][col] == true) {
    					++numAdjMines;
    				}
    			} 
    			if (row - 1 >= 0 && col + 1 < mines[row].length) { //top right
    				if (mines[row - 1][col + 1] == true) {
    					++numAdjMines;
    				}
    			} 
    			if (col - 1 >= 0) { //middle left
    				if (mines[row][col - 1] == true) {
    					++numAdjMines;
    				}
    			} 
    			if (col + 1 < mines[row].length) { //middle right
    				if (mines[row][col + 1] == true) {
    					++numAdjMines;
    				}
    			} 
    			if (row + 1 < mines.length && col - 1 >= 0) { //bottom left 
    				if (mines[row + 1][col - 1] == true) {
    					++numAdjMines;
    				}
    			} 
    			if (row + 1 < mines.length) { //bottom middle
    				if (mines[row + 1][col] == true) {
    					++numAdjMines;
    				}
    			} 
    			if (row + 1 < mines.length && col + 1 < mines[row].length) { //bottom right 
    				if (mines[row + 1][col + 1] == true) {
    					++numAdjMines;
    				}
    			} 
    		} else {
    			numAdjMines = INVALID_INPUT;
    		} 
    		
        return numAdjMines; 
    }

    /**
     * This updates the map with each unswept mine shown with the Config.HIDDEN_MINE
     * character. Swept mines will already be mapped and so should not be changed.
     * This method does not print out anything.
     * 
     * @param map  An array containing the map. On return the map shows unswept mines.
     * @param mines An array indicating which locations have mines.  No changes
     *      are made to the mines array.
     */
    public static void showMines(char[][] map, boolean[][] mines) {
    		int row = 0;
    		int col = 0;
    		
    		for (row = 0; row < map.length; ++row) {
    			for (col = 0; col < map[row].length; ++col) {
    				if ((mines[row][col] == true) && (map[row][col] == Config.UNSWEPT)) {
    					map[row][col] = Config.HIDDEN_MINE;
    		    		}
    			}
    		}
        return; 
    }

    /**
     * Returns whether all the safe (non-mine) locations have been swept. In 
     * other words, whether all unswept locations have mines. 
     * This method does not print out anything.
     * 
     * @param map The map showing touched locations that is unchanged by this method.
     * @param mines The mines array that is unchanged by this method.
     * @return whether all non-mine locations are swept.
     */
    public static boolean allSafeLocationsSwept(char[][] map, boolean[][] mines) {
    		boolean allFinished = true;
    		int row = 0;
    		int col = 0;
    		
    		//checks if all false locations (on mine map) is UNSWEPT (on map map).
    		for (row = 0; row < mines.length; ++row) {
    			for (col = 0; col < mines[row].length; ++col) {
    				if (mines[row][col] == false && map[row][col] == Config.UNSWEPT) {
    					allFinished = false;
    					break; //gets us out of the loop 
    				} 
    			}
    		}
    		
        return allFinished;
    }

    /**
     * This method sweeps the specified row and col.
     *   - If the row and col specify a location outside the map array then 
     *     return -3 without changing the map.
     *   - If the location has already been swept then return -2 without changing
     *     the map.
     *   - If there is a mine in the location then the map for the corresponding
     *     location is updated with Config.SWEPT_MINE and return -1.
     *   - If there is not a mine then the number of nearby mines is determined 
     *     by calling the numNearbyMines method. 
     *        - If there are 1 to 8 nearby mines then the map is updated with 
     *          the characters '1'..'8' indicating the number of nearby mines.
     *        - If the location has 0 nearby mines then the map is updated with
     *          the Config.NO_NEARBY_MINE character.
     *        - Return the number of nearbyMines.
     *        
     * @param map The map showing swept locations.
     * @param mines The array showing where the mines are located.
     * @param row The row, 0-based, of a location.
     * @param col The col, 0-based, of a location.
     * @return the number of nearby mines, -1 if the location is a mine, -2 if 
     * the location has already been swept, -3 if the location is off the map.
     */
    public static int sweepLocation(char[][] map, boolean[][] mines, int row, int col) {
    		int returnVal = 0;
        final int OUTSIDE_INDEX = -3;
        final int ALREADY_SWEPT = -2;
        final int MINE_THERE = -1;
        
        if (row < 0 || row >= map.length || col < 0 || col >= map[row].length) { //checks for invalid input
        		returnVal = OUTSIDE_INDEX;
        } else if (map[row][col] != Config.UNSWEPT) { //checks if already swept
        		returnVal = ALREADY_SWEPT;
        } else if (mines[row][col] == true) { //checks if the user hit a mine 
        		map[row][col] = Config.SWEPT_MINE;
        		returnVal = MINE_THERE;
        } else { //returns the number of nearby mines 
        		returnVal = numNearbyMines(mines, row, col);
        		if (returnVal == 0) { //if 0 gives map = " "
        			map[row][col] = Config.NO_NEARBY_MINE;
        		} else { //gives map a number from 1-8
        			map[row][col] = (char)('0' + returnVal);
        		}
        }
    	
    		return returnVal; 
    }

    /**
     * This method iterates through all 8 neighboring locations and calls sweepLocation
     * for each. It does not call sweepLocation for its own location, just the neighboring
     * locations.
     * @param map The map showing touched locations.
     * @param mines The array showing where the mines are located.
     * @param row The row, 0-based, of a location.
     * @param col The col, 0-based, of a location.
     */
    public static void sweepAllNeighbors(char [][]map, boolean[][] mines, int row, int col) {
    		int counter = 0;
    		
		if (row >= 0 && col >= 0 && row < mines.length && col < mines[row].length) {
			if (row - 1 >= 0 && col - 1 >= 0) { //top left 
					sweepLocation(map, mines, row - 1, col - 1);
						
			} 
			if (row - 1 >= 0) { //top middle
					sweepLocation(map, mines, row - 1, col);
					
			} 
			if (row - 1 >= 0 && col + 1 < mines[row].length) { //top right
					sweepLocation(map, mines, row - 1, col + 1);
					
			} 
			if (col - 1 >= 0) { //middle left
					sweepLocation(map, mines, row, col - 1);	
					
			} 
			if (col + 1 < mines[row].length) { //middle right
					sweepLocation(map, mines, row, col + 1);
					//System.out.print(sweepLocation(map, mines, row, col + 1));
					
			} 
			if (row + 1 < mines.length && col - 1 >= 0) { //bottom left 
					sweepLocation(map, mines, row + 1, col - 1);	
					
			} 
			if (row + 1 < mines.length) { //bottom middle
					sweepLocation(map, mines, row + 1, col);
					
			} 
			if (row + 1 < mines.length && col + 1 < mines[row].length) { //bottom right 
					sweepLocation(map, mines, row + 1, col + 1);
					
			} 
		} 
		//need a terminating condition
		
	
		
		/*  stackoverflow error keeps happening, but the code appears to work 
		if (sweepLocation(map, mines, row - 1, col - 1) == -2) { //top left
			sweepAllNeighbors(map, mines, row - 1, col - 1);
		}
		
		if (sweepLocation(map, mines, row - 1, col) == -2) { //top middle
			sweepAllNeighbors(map, mines, row - 1, col);
		}
		
		if (sweepLocation(map, mines, row - 1, col + 1) == -2) { //top right
			sweepAllNeighbors(map, mines, row - 1, col + 1);
		} */
	while () {	
		if (sweepLocation(map, mines, row, col - 1) == -2) { //middle left
			sweepAllNeighbors(map, mines, row, col - 1);
		} 
	}
		if (sweepLocation(map, mines, row, col + 1) == -2) { //middle right
			sweepAllNeighbors(map, mines, row, col + 1);
		}
		
		if (sweepLocation(map, mines, row + 1, col - 1) == -2) { //bottom right
			sweepAllNeighbors(map, mines, row + 1, col - 1);
		}
		
		if (sweepLocation(map, mines, row + 1, col) == -2) { //bottom middle
			sweepAllNeighbors(map, mines, row + 1, col);
		}
		
		if (sweepLocation(map, mines, row + 1, col + 1) == -2) { //bottom left
			sweepAllNeighbors(map, mines, row + 1, col + 1);
		}  
		
	
		
		
        return; 
    }
}
