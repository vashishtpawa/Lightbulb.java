/* Lightbulb Version 2
 * April 30, 2022
 * Vashisht
 * This program is for a cool lightbulb game bro */

import java.util.Scanner;
import java.util.Random;

public class Lightbulb {
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        greeting(); //Calls greeting method
    
        int[][] grid = initialGrid(); //Calls initialGrid method return values

        //Declares winCondition value
        boolean winCondition = false;
       
        //Initializes row and col values as 0 at start
        int row = 0;
        int col = 0;
        
        int turnCounter = 0; //Counts number of turns taken, increments each iteration of while loop. Outputs at end.

        int[] inputArr = new int[2]; //Declares an empty array of length 2 to store row and column values into

        while(winCondition != true){ //Loops until winCondition is proven true
            displayGrid(grid); //Prints ligthbulb grid via method call

            inputArr = takeInput(inputArr); //Calls method to take inputs between 1 - 5
            row = inputArr[0]; //Assigns row value from array[0] returned from method
            col = inputArr[1]; //Assigns col value from array[1] returned from method

            grid = flipDecide(grid, row, col); //Flips value at user inputted coords as well as sorrounding values
            turnCounter++;
            winCondition = winChecker(winCondition, grid); //Checks win condition via method
        }

        displayGrid(grid); //Prints final lightbulb grid

        System.out.print("\nYou win! It took you: " + turnCounter + " tries."); //Win statement :)
    }


    public static void greeting(){//Method dictating instructions       
        System.out.print("Lightbulb Game!\n");
        System.out.print("How to Play: Enter the x and y coordinates of a light to turn it off (0) or on (1).\nAll the sorrounding, non-diagonal lights will be flipped.\n");
        System.out.print("Goal: Try to turn off all of the lights. Good luck!\n");
    }

    public static void displayGrid(int[][] grid){ //Displays grid via nested for loop

        System.out.println(""); //Prints lightbulb grid
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                System.out.print(grid[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public static int[] takeInput(int[] inputArray) //Asks for input. Only takes in values 1 - 5; repeats prompt otherwise
    {
            Scanner in = new Scanner(System.in);
            boolean oneToFive = false;

            while(oneToFive == false){ //Loops until oneToFive is proven true
                System.out.print("\nEnter row of the bulb you want to flip: "); //Prompts user for row
                inputArray[0] = in.nextInt() - 1;
    
                if(inputArray[0] > 4 || inputArray[0] < 0) //If user input less than 1 or more than 5, asks again
                System.out.println("Please enter a number between 1 and 5.\n");

                else{oneToFive = true;}; //Sets oneToFive to true, ending loop
            }

            oneToFive = false; //Sets oneToFive to false
            while(oneToFive == false){ //Loops until oneToFive is proven true
                System.out.print("Enter column of the bulb you want to flip: "); //Prompts user for column
                inputArray[1] = in.nextInt() - 1;

                if(inputArray[1] > 4 || inputArray[1] < 0) //If user input less than 1 or more than 5, asks again
                System.out.println("Please enter a number between 1 and 5.\n\n");

                else{oneToFive = true;}; //Sets oneToFive to true, ending loop
            }

            return inputArray;
        }

    public static int[][] initialGrid(){//Declares, assigns values to, and returns initial grid array
        Random rand = new Random();
       
        int[][] grid = new int[5][5]; //2D array of size 5x5
        
        for(int i = 0; i < 5; i++){ //Assigns either 0 or 1 to every value in the array
            for(int j = 0; j < 5; j++){
                grid[i][j] = rand.nextInt(2);
            }
        }

        return(grid);
    }

    public static int flip(int value){//Switches values from 1 to 0 and vice versa, returns value
        
        if(value == 1){
            value = 0;
         }

        else if(value == 0){
            value = 1;
        }

        return value;        
    }

    public static int[][] flipDecide(int[][] grid, int row, int col){ //Determines which neighbouring to flip

        grid[row][col] = flip(grid[row][col]); //Flips value at user inputted coords

        if(row >= 1 && col >= 1 && row <= 3 && col <= 3){ //Flips all sorrounding values if coord entered isn't on the border of the grid (i.e. both bigger than 1 and less than 5)
            grid[row][col+1] = flip(grid[row][col+1]);
            grid[row+1][col] = flip(grid[row+1][col]);
            grid[row][col-1] = flip(grid[row][col-1]);
            grid[row-1][col] = flip(grid[row-1][col]);
        }

        else if(row == 0 || col == 0 || row == 4 || col == 4){ //If either row or col is a border grid, then selectively chooses which to flip

            if(row >= 1 && row <= 3){ //If row value isn't on the border of the grid, flips row above and below (same col)
                grid[row-1][col] = flip(grid[row-1][col]);
                grid[row+1][col] = flip(grid[row+1][col]);
            }

            if(col >= 1 && col <= 3){ //If column value isn't on the border of the grid, flips columns left and right (same row)
                grid[row][col-1] = flip(grid[row][col-1]);
                grid[row][col+1] = flip(grid[row][col+1]);
            }

            //^^Only ONE of these can be true; if both were, then this else if statement wouldn't function. Only way both of the above if statements
            //are false is if coord's entered are either (1, 1) or (5, 5); (1, 2) to (1, 4) and (5, 2) too (5, 4) are affected
            //Basically, the if statements below 100% flip the value in the first or last col/row; the if statements above flip any values above/below/left/right as needed

            if(row < 1){ //If coord is on first row, row below is flipped
                grid[row+1][col] = flip(grid[row+1][col]);
            }

            if(col < 1){ //If coord is on first column, column to the right is flipped
                grid[row][col+1] = flip(grid[row][col+1]);
            }
            
            if(row > 3){ //If coord is on the last row, row above is flipped
                grid[row-1][col] = flip(grid[row-1][col]);
            }

            if(col > 3){ //If coords is on the last column, column to the left is flipped
                grid[row][col-1] = flip(grid[row][col-1]);
            }
        }

        return grid; //Returns grid to main
    }

    public static boolean winChecker(boolean winCondition, int[][] grid){ //Checks if game won by adding up all values of 2D array
        int valueSum = 0;
        winCondition = false;

        for(int i = 0; i < 5; i++){  //Checks if user won by summing up all the grid values
            for(int j = 0; j < 5; j++){
                valueSum += grid[i][j];
            }
        }
        
        if(valueSum == 0){winCondition = true;} //If sum = 0, sets and returns win condition as true, ending loop

        return winCondition;
    }

}
