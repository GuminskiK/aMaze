package Core;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class TerminalInterface {

    Scanner scanner;
    boolean isFileOk = false;
    String filePath;

    int currentRow;
    int currentColumn;
    int lastDirection;

    TerminalInterface() {

        Welcome();
    }

    private void Welcome() {
        System.out.println("Welcome to the aMaze app, where you can solve your maze!");
        System.out.println("Please give me a filePath to your maze that you would like me to solve.");
        getFilePath();
    }

    private void getFilePath() {

        scanner = new Scanner(System.in);
        filePath = scanner.nextLine();
        File file = new File(filePath);

        if (file.exists()) {
            isFileOk = true;
            //start solving
            
        }else{
            System.out.println("Something wrong with this filePath. Can you give me a proper one?");
            getFilePath();
        }
    }

    private void writeSolution(ArrayList<SolutionBlock> solution, Maze maze){
        currentColumn = maze.getStart()[0];
        currentRow = maze.getStart()[1];

        for ( SolutionBlock solutionBlock: solution){
            writeSolutionBlock(solutionBlock.getDirection(), solutionBlock.getSteps());
        }
    }

    private void writeSolutionBlock(int direction, int length){
        String directionString = "";
        switch (direction) {
            case 0:
                currentRow -= length;
                directionString = "North";
                break;
            case 1:
                currentColumn += length;
                directionString = "East";
                break;
            case 2:
                currentRow += length;
                directionString = "South";
                break;
            case 3:
                currentColumn -= length;
                directionString = "West";
                break;
            default:
                break;
            

        }           
        
        if (lastDirection == direction){
            System.out.println("Go another" + length + " steps.");
        } else{
            System.out.println("Go " + directionString + " " + length + " steps.");
        }
    }
}
