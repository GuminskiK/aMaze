package Core;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class TerminalInterface implements Observer {

    Scanner scanner = new Scanner(System.in);
    boolean isFileOk = false;
    String filePath;

    int currentRow;
    int currentColumn;
    int lastDirection;

    Watched watched;
    Thread threadInput;
    Thread threadInS;
    Thread threadInE;
    boolean isFileLoaded = false;
    boolean isStartCorrectlyChosen = false;
    boolean isEndCorrectlyChosen = false;
    boolean endScanning = false;
    boolean startInputBlock = false;
    boolean endInputBlock = false;

    String scanned = "";
    Watched lineScanned;
    int whatAreWeScanningFor = 0;

    boolean filePathScanned = false;

    boolean TerminalWasUsed = false;

    Maze maze;

    TerminalInterface(Watched watched, Maze maze) {

        this.watched = watched;
        this.maze = maze;
    }

    private void welcome() {
        System.out.println("Welcome to the aMaze app, where you can solve your maze!");
        System.out.println("Please give me a filePath to your maze that you would like me to solve.");
        scanningNextLine();
    }

    private void getFilePath() {

        whatAreWeScanningFor = 1;

        Thread threadGetFilePath = new Thread(() -> {
            while (!isFileLoaded) {

                try {
                    System.out.println("waiting");
                    synchronized (this) {
                        wait();
                    }

                    System.out.println("let s go");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                filePath = scanned;
                File file = new File(filePath);

                if (file.exists()) {
                    isFileLoaded = true;
                    Main.setFilePath(filePath);
                    Main.setFileType(file.getAbsolutePath().substring(file.getAbsolutePath().length() - 3));
                    watched.setMessage("gotFile");
                    break;
                } else {
                    if (isFileLoaded) {
                        System.out.println("Loading the maze had been succesful");
                    } else {
                        System.out.println("Something wrong with this filePath. Can you give me a proper one?");
                    }

                }
            }
            System.out.println("ENDED");
        });

        threadGetFilePath.start();

    }

    private void scannerManager() {
        switch (whatAreWeScanningFor) {
            case 1:
                synchronized (this) {
                    this.notifyAll();
                }
                System.out.println("Notified");
                break;
            case 2:
                synchronized (this) {
                    this.notifyAll();
                }
                System.out.println("Notified");
                break;
            default:
                break;
        }
    }

    private void scanningNextLine() {
        lineScanned = new Watched();
        lineScanned.registerObserver(this);

        threadInput = new Thread(() -> {
            while (!endScanning) {
                scanned = scanner.nextLine();
                System.out.println("Wczytano: " + scanned);
                lineScanned.setMessage("lineScanned");
            }
        });
        threadInput.start();
    }

    private void gotFile() {
        isFileLoaded = true;
    }

    private void wasRead() {
        if (TerminalWasUsed) {
            watched.setMessage("analyze");
        }
    }

    private void analyzed() {
        if (TerminalWasUsed) {
            watched.setMessage("shortest");
        }
    }

    private void solved() {
        System.out.println("The maze has been successfully solved");
        if (TerminalWasUsed) {
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

    private void noS(char c) {

        whatAreWeScanningFor = 2;
        threadInS = new Thread(() -> {
            while (!isStartCorrectlyChosen) {
                System.out.println("Please type in coordinates for a new Start:");
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int startX = Integer.parseInt(scanned);
                System.out.println("StartX " + startX);
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int startY = Integer.parseInt(scanned);
                System.out.println("StartY " + startY);

                if (Main.ifPath(new int[] { startX, startY }) && !startInputBlock) {
                    isStartCorrectlyChosen = true;
                    System.out.println(startX + " " + startY);
                    maze.setNewStartPosition(startX, startY);
                    watched.setMessage("StartEndNewPositionS");
                    if (c == 'B') {
                        noE();
                    }
                } else if(startInputBlock){
                    System.out.println("Start has already been chosen");
                    break;
                }
            }
        });

        threadInS.start();

    }

    private void noE() {

        threadInE = new Thread(() -> {
            while (!isEndCorrectlyChosen) {
                System.out.println("Please type in coordinates for a new End:");
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int endX = Integer.parseInt(scanned);
                System.out.println("EndX " + endX);
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int endY = Integer.parseInt(scanned);
                System.out.println("EndY " + endY);

                if (Main.ifPath(new int[] { endX, endY }) && !endInputBlock) {
                    isEndCorrectlyChosen = true;
                    System.out.println(endX + " " + endY);
                    maze.setNewEndPosition(endX, endY);
                    watched.setMessage("StartEndNewPositionE");
                } else if( endInputBlock){
                    System.out.println("End had already been chosen");
                    break;
                }
            }
        });

        threadInE.start();

    }

    private void blockStartInputs(){
        startInputBlock = true;
    }

    private void blockEndIputs(){
        endInputBlock = true;
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
                scannerManager();
                break;
            case "wasRead":
                wasRead();
                break;
            case "analyzed":
                analyzed();
                break;
            case "solved":
                solved();
                break;
            case "noStartEnd":
                noS('B');
                scannerManager();
                break;
            case "noStart":
                noS('S');
                scannerManager();
                break;
            case "noEnd":
                noE();
                scannerManager();
                break;
            case "lineScanned":
                scannerManager();
                break;
            case "StartEndNewPositionS":
                blockStartInputs();
                break;
            case "StartEndNewPositionE":
                blockEndIputs();
                break;
            default:
                break;
        }
    }
}
