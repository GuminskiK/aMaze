package Core;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class TerminalInterface implements Observer {

    Scanner scanner;
    boolean isFileOk = false;
    String filePath;

    int currentRow;
    int currentColumn;
    int lastDirection;

    Watched watched;
    Thread threadInput;
    boolean isFileLoaded = false;

    boolean TerminalWasUsed = false;

    TerminalInterface(Watched watched){

        this.watched = watched;
    }

    private void welcome() {
        System.out.println("Welcome to the aMaze app, where you can solve your maze!");
        System.out.println("Please give me a filePath to your maze that you would like me to solve.");
    }

    private void getFilePath() {

        threadInput = new Thread(() -> {
            scanner = new Scanner(System.in);
            while (!isFileLoaded) {
                filePath = scanner.nextLine();
                File file = new File(filePath);

                if (file.exists()) {
                    isFileLoaded = true;
                    Main.setFilePath(filePath);
                    Main.setFileType(file.getAbsolutePath().substring(file.getAbsolutePath().length() - 3));
                    watched.setMessage("gotFile");
                } else {
                    System.out.println("Something wrong with this filePath. Can you give me a proper one?");
                }
            }
        });

        threadInput.start();
    }

    

    private void gotFile(){
        isFileLoaded = true;
        System.out.println("Loading the maze had been succesful");
    }

    private void wasRead(){
        if ( TerminalWasUsed){
            watched.setMessage("analyze");
        }
    }

    private void analyzed(){
        if ( TerminalWasUsed){
            watched.setMessage("shortest");
        }
    }

    private void solved(){
        System.out.println("The maze has been successfully solved");
        if (TerminalWasUsed){
            writeSolution(Main.mazeSolver.getSolution(), Main.maze);
        }
    }

    private void writeSolution(ArrayList<SolutionBlock> solution, Maze maze) {
        currentColumn = maze.getStart()[0];
        currentRow = maze.getStart()[1];
        lastDirection = 5;

        for (SolutionBlock solutionBlock : solution) {
            writeSolutionBlock(solutionBlock.getDirection(), solutionBlock.getSteps());
        }
    }

    private void writeSolutionBlock(int direction, int length) {
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

        if (lastDirection == direction) {
            System.out.println("Go another " + length + " steps.");
        } else {
            System.out.println("Go " + directionString + " " + length + " steps.");
        }
        lastDirection = direction;
    }

    @Override
    public void update(String message) {

        System.out.println("Terminal: " + message);

        switch (message) {
            case "start":
                welcome();
                break;
            case "getFile":
                getFilePath();
                break;
            case "gotFile":
                gotFile();
                break;
            case "wasRead":
                wasRead();
            case "analyzed":
                analyzed();
                break;
            case "solved":
                solved();
                break;
            default:
                break;
        }
    }
}
