/**
 * This file contains testing methods for the MineSweeper project.
 * These methods are intended to serve several objectives:
 * 1) provide an example of a way to incrementally test your code
 * 2) provide example method calls for the MineSweeper methods
 * 3) provide examples of creating, accessing and modifying arrays
 *    
 * Toward these objectives, the expectation is that part of the 
 * grade for the MineSweeper project is to write some tests and
 * write header comments summarizing the tests that have been written. 
 * Specific places are noted with FIXME but add any other comments 
 * you feel would be useful.
 * 
 * Some of the provided comments within this file explain
 * Java code as they are intended to help you learn Java.  However,
 * your comments and comments in professional code, should
 * summarize the purpose of the code, not explain the meaning
 * of the specific Java constructs.
 *    
 */

import java.util.Random;
import java.util.Scanner;


/**
 * This class contains a few methods for testing methods in the MineSweeper
 * class as they are developed. These methods are all private as they are only
 * intended for use within this class.
 * 
 * @author Jim Williams
 * @author FIXME add your name here when you add tests and comment the tests
 *
 */
public class TestMineSweeper {

    /**
     * This is the main method that runs the various tests. Uncomment the tests
     * when you are ready for them to run.
     * 
     * @param args  (unused)
     */
    public static void main(String [] args) {

        // Milestone 1
        //testing the main loop, promptUser and simplePrintMap, since they have
        //a variety of output, is probably easiest using a tool such as diffchecker.com
        //and comparing to the examples provided.
        //testEraseMap(); //good to go
        
        // Milestone 2
        //testPlaceMines();  //good to go
        testNumNearbyMines(); //good to go
        //testShowMines(); //good to go
        //testAllSafeLocationsSwept(); //good to go
        
        // Milestone 3
        //testSweepLocation(); //good to go
        //testSweepAllNeighbors(); //good to go
        //testing printMap, due to printed output is probably easiest using a 
        //tool such as diffchecker.com and comparing to the examples provided.
    }
    
    /**
     * This is intended to run some tests on the eraseMap method. 
     * 1. tests the smallest array possible 
     * 2. tests the largest possible
     * 3. tests if a rectangle works
     * 4. tests the opposite rectangle
     * 5. tests a big height, little width
     * 6. tests a big width, little height
     */
    private static void testEraseMap() {
        //Review the eraseMap method header carefully and write
        //tests to check whether the method is working correctly.
    		char[][] map;
    		boolean error = false;
    		
    		//map = new char[3][3];
    		//map = new char[20][20];
    		//map = new char[4][5];
    		//map = new char[5][4];
    		//map = new char[12][3];
    		map = new char[4][13];
    		
    		MineSweeper.eraseMap(map);
    		
    		for (int i = 0; i < map.length; ++i) {
    			for (int j = 0; j < map[i].length; ++j) {
    				if (map[i][j] != Config.UNSWEPT) {
    					System.out.println("Error: map does not equal UNSWEPT at " + i +
    							" and " + j + ".");
    					error = true;
    				} 
    			}
    		}
    		
    		if (!error) {
    			System.out.print("Test passed.");
    		}
        
    }      
    
    /*
     * Calculate the number of elements in array1 with different values from 
     * those in array2
     */
    private static int getDiffCells(boolean[][] array1, boolean[][] array2) {
        int counter = 0;
        for(int i = 0 ; i < array1.length; i++){
            for(int j = 0 ; j < array1[i].length; j++){
                if(array1[i][j] != array2[i][j]) 
                    counter++;
            }
        }
        return counter;
    }    
    
    /**
     * This runs some tests on the placeMines method. 
     * 1. Uses different seed(123) to test if the method placeMines works correctly.
     * with a 5 by 5 array there should be 3 mines.
     * 2. Calls placeMines 10000 times to see if there are consistently large or small numbers of mines.
     *   		The average number of mines seems to be around 5-7.
     */
    private static void testPlaceMines() {
        boolean error = false;
        
        //These expected values were generated with a Random instance set with
        //seed of 123 and MINE_PROBABILITY is 0.2.
       boolean [][] expectedMap = new boolean[][]{
            {false,false,false,false,false},
            {false,false,false,false,false},
            {false,false,false,true,true},
            {false,false,false,false,false},
            {false,false,true,false,false}};
        int expectedNumMines = 3;
        
            
        Random studentRandGen = new Random( 123);
        boolean [][] studentMap = new boolean[5][5];
        int studentNumMines = MineSweeper.placeMines( studentMap, studentRandGen);
        
        if ( studentNumMines != expectedNumMines) {
            error = true;
            System.out.println("testPlaceMines 1: expectedNumMines=" + expectedNumMines + " studentNumMines=" + studentNumMines);
        }
        int diffCells = getDiffCells( expectedMap, studentMap);
        if ( diffCells != 0) {
            error = true;
            System.out.println("testPlaceMines 2: mine map differs.");
        }
       

        if (error) {
            System.out.println("testPlaceMines: failed");
        } else {
            System.out.println("testPlaceMines: passed");
        }      
        
        for (int i = 0; i < 10000; ++i) {
        		studentNumMines = MineSweeper.placeMines( studentMap, studentRandGen);
        		System.out.println(studentNumMines);
        }
   
        
    }
    
    /**
     * This runs some tests on the numNearbyMines method. 
     * 1. Tests to see if numNearbyMines works in the middle of an array. (1,1)
     * 2. Again, tests the "middle" of an array but this time goes to edges of array.(2,1)
     * 3. This tests a corner case to see of there is any array out-of-bounds indexes 
     * 4. This tests an edge case at 3, 0.
     */
    private static void testNumNearbyMines() {
        boolean error = false;

        boolean [][] mines = new boolean[][]{
            {false,false,true,false,false},
            {false,false,false,false,false},
            {false,true,false,true,true},
            {false,false,false,false,false},
            {false,false,true, false ,false}};
        int numNearbyMines = MineSweeper.numNearbyMines( mines, 1, 1);
        
        if ( numNearbyMines != 2) {
            error = true;
            System.out.println("testNumNearbyMines 1: numNearbyMines=" + numNearbyMines + " expected mines=2");
        }
        
       numNearbyMines = MineSweeper.numNearbyMines( mines, 2, 1);
        
        if ( numNearbyMines != 0) {
            error = true;
            System.out.println("testNumNearbyMines 2: numNearbyMines=" + numNearbyMines + " expected mines=0");
        }        
        
        // Can you think of other tests that would make sure your method works correctly?
        // if so, add them.
        
        numNearbyMines = MineSweeper.numNearbyMines( mines, 0, 0);
        
        if ( numNearbyMines != 0) {
            error = true;
            System.out.println("testNumNearbyMines 3: numNearbyMines=" + numNearbyMines + " expected mines=0");
        } 
        
        numNearbyMines = MineSweeper.numNearbyMines( mines, 3, 0);
        
        if ( numNearbyMines != 1) {
            error = true;
            System.out.println("testNumNearbyMines 4: numNearbyMines=" + numNearbyMines + " expected mines=1");
        } 
        

        if (error) {
            System.out.println("testNumNearbyMines: failed");
        } else {
            System.out.println("testNumNearbyMines: passed");
        }
    }
    
    /**
     * This runs some tests on the showMines method. 
     * 1. This tests an array for any mines mapped or not mapped throughout the array.
     * 2. This tests specifically for mapped mines on the 4 corners of the array.
     */
    private static void testShowMines() {
        boolean error = false;
        

        boolean [][] mines = new boolean[][]{
            {false,false,true,false,false},
            {false,false,false,false,false},
            {false,true,false,false,false},
            {false,false,false,false,false},
            {false,false,true,false,false}};
            
        char [][] map = new char[mines.length][mines[0].length];
        map[0][2] = Config.UNSWEPT;
        map[2][1] = Config.UNSWEPT;
        map[4][2] = Config.UNSWEPT;
        
        MineSweeper.showMines( map, mines);
        if ( !(map[0][2] == Config.HIDDEN_MINE && map[2][1] == Config.HIDDEN_MINE && map[4][2] == Config.HIDDEN_MINE)) {
            error = true;
            System.out.println("testShowMines 1: a mine not mapped");
        }
        if ( map[0][0] == Config.HIDDEN_MINE || map[0][4] == Config.HIDDEN_MINE || map[4][4] == Config.HIDDEN_MINE) {
            error = true;
            System.out.println("testShowMines 2: unexpected showing of mine.");
        }
        
        //test 2
        boolean [][] mines2 = new boolean[][]{
            {true,false,true,false,false,true},
            {false,false,false,false,false,false},
            {false,true,false,false,false,false},
            {false,false,false,false,false,false},
            {true,false,false,true,false,true}};
            
        char [][] map2 = new char[mines2.length][mines2[0].length];
        map2[0][0] = Config.UNSWEPT;
        map2[4][0] = Config.UNSWEPT;
        map2[4][5] = Config.UNSWEPT;
        map2[0][5] = Config.UNSWEPT;
        MineSweeper.showMines( map2, mines2);
        if ( !(map2[0][5] == Config.HIDDEN_MINE && map2[4][5] == Config.HIDDEN_MINE && map2[4][0] == Config.HIDDEN_MINE && map2[0][0] == Config.HIDDEN_MINE)) {
            error = true;
            System.out.println("testShowMines 2: a mine not mapped");
        }
        

        if (error) {
            System.out.println("testShowMines: failed");
        } else {
            System.out.println("testShowMines: passed");
        }        
    }    
    
    /**
     * This is intended to run some tests on the allSafeLocationsSwept method.
     * 1. Tests a map with all UNSWEPT chars(which is why eraseMap is called), allSafeLoc.. should come back false. 
     * 2. Uses same mine map, but this time I manually set the map with chars. Should come back true.
     * 3. Again uses same mine map and same char map. This time I changed one to Config.UNSWEPT so AllSafeLoc.. should come back false.
     */
    private static void testAllSafeLocationsSwept() {
    		boolean error = false;
    		
    		boolean mines[][] = new boolean[][] {
    			{false, false, false, false},
    			{true, false, true, false}, 
    			{true, true, false, false},
    			{false, false, false, false}
    		};
    		
    		char[][] map = new char[mines.length][mines[0].length]; 
    		MineSweeper.eraseMap(map);
    		
    		if (MineSweeper.allSafeLocationsSwept( map, mines)) {
    			error = true;
    			System.out.println("allSafeLocationsSwept should have been false, came back true.");
    		}
    		//test 2
    		boolean mines2[][] = new boolean[][] { //making new arrays for new tests.
    			{false, false, false, false},
    			{true, false, true, false}, 
    			{true, true, false, false},
    			{false, false, false, false}
    		};
    		
    		char map2[][] = new char[][] {
    			{'q','q','q','q'},
    			{Config.UNSWEPT, 'q', Config.UNSWEPT, 'q'}, 
    			{Config.UNSWEPT, Config.UNSWEPT, 'q', 'q'},
    			{'q','q','q','q'}
    		};
    		
    		if (!MineSweeper.allSafeLocationsSwept( map2, mines2)) {
    			error = true;
    			System.out.println("(2). allSafeLocationsSwept should have been true, came back false.");
    		}
    		//test 3
    		boolean mines3[][] = new boolean[][] {
    			{false, false, false, false},
    			{true, false, true, false}, 
    			{true, true, false, false},
    			{false, false, false, false}
    		};
    		
    		char map3[][] = new char[][] {
    			{Config.UNSWEPT,'q','q','q'},
    			{Config.UNSWEPT, 'q', Config.UNSWEPT, 'q'}, 
    			{Config.UNSWEPT, Config.UNSWEPT, 'q', 'q'},
    			{'q','q','q','q'}
    		};
    		
    		if (MineSweeper.allSafeLocationsSwept( map3, mines3)) {
    			error = true;
    			System.out.println("(3). allSafeLocationsSwept should have been false, came back true.");
    		}
    		
    		
    		if (!error) {
    			System.out.println("Test Passed.");
    		} else {
    			System.out.println("Test Failed.");
    		}
    		
    }      

    
    /**
     * This is intended to run some tests on the sweepLocation method. 
     * 1. Tests to see if an invalid row/col returns -3. 
     * 2. Tests to see if an already swept location returns -2.
     * 3. Tests to see if an inputed col/row that lands on a mine returns -1
     * 4. Tests to see if SweepLocation will return 4 since there are 4 mines around [1][1]
     */
    private static void testSweepLocation() {
    		boolean mines[][] = new boolean[][] {
			{false, false, false, false},
			{true, false, true, false}, 
			{true, true, false, false},
			{false, false, false, false}
		};
		
		char[][] map = new char[mines.length][mines[0].length]; 
		MineSweeper.eraseMap(map);
		
		//test 1.
		if (MineSweeper.sweepLocation(map, mines, -1, -1) != -3) {
			System.out.println("Test 1: failed.");
		} else {
			System.out.println("Test 1: passed.");
		}
		
		//test 2
		map[0][0] = 'q';
		if (MineSweeper.sweepLocation(map, mines, 0, 0) != -2) {
			System.out.println("Test 2: failed.");
		} else {
			System.out.println("Test 2: passed.");
		}
    		
		//test 3
		if (MineSweeper.sweepLocation(map, mines, 1, 0) != -1) {
			System.out.println("Test 3: failed.");
		} else {
			System.out.println("Test 3: passed.");
		}
		
		//test 4
		if (MineSweeper.sweepLocation(map, mines, 1, 1) != 4) {
			System.out.println("Test 4: failed.");
		} else {
			System.out.println("Test 4: passed.");
		}
    }      
    
    /**
     * This is intended to run some tests on the sweepAllNeighbors method. 
     * 1. This test checks to see if sweepAllNeighbors checks the inputed row/col.
     * 2. This 3 part test checks if SweepAllNeighbors it can sweep a corner case correctly.
     */
    private static void testSweepAllNeighbors() {
    		boolean error = false; 
    	
    		boolean mines[][] = new boolean[][] {
			{false, false, false, false, true},
			{true, false, true, false, false}, 
			{true, true, false, false, false},
			{false, false, false, false, false}
		};
		
		char[][] map = new char[mines.length][mines[0].length]; 
		MineSweeper.eraseMap(map);
	
		MineSweeper.sweepAllNeighbors(map, mines, 2, 2);
		//test one
		if (map[2][2] != Config.UNSWEPT) {
			error = true;
			System.out.println("Test 1 Failed. Should have been '.'");
		} else {
			System.out.println("Test 1 passed.");
		}
		
		MineSweeper.sweepAllNeighbors(map, mines, 3, 4);
		//test two
		if (map[3][3] != Config.NO_NEARBY_MINE) {
			error = true;
			System.out.println("Test 2.1 Failed. Should have been ' '");
		} else {
			System.out.println("Test 2.1 passed.");
		}
		if (map[2][3] != '1') {
			error = true;
			System.out.println("Test 2.2 Failed. Should have been '1'");
		} else {
			System.out.println("Test 2.2 passed.");
		} 
		if (map[2][4] != Config.NO_NEARBY_MINE) {
			error = true;
			System.out.println("Test 2.3 Failed. Should have been ' '");
		} else {
			System.out.println("Test 2.3 passed.");
		}
		
		if (error) {
    			System.out.println("Tests failed.");
		} else {
    			System.out.println("Tests passed.");
		}
		
    }      
}
