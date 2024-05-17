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

    int startX;
    int startY;
    int endX;
    int endY;

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

        Thread threadGetFilePath = new Thread(() -> {
            while (!isFileLoaded) {

                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                filePath = scanned;
                File file = new File(filePath);

                if (file.exists()) {
                    isFileLoaded = true;
                    Main.setFilePath(filePath);
                    Main.setFileType(file.getAbsolutePath().substring(file.getAbsolutePath().length() - 3));
                    TerminalWasUsed = true;
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
        });

        threadGetFilePath.start();

    }

    private void scannerManager() {

        synchronized (this) {
            this.notifyAll();
        }
        System.out.println("Notified");

    }

    private void scanningNextLine() {
        lineScanned = new Watched();
        lineScanned.registerObserver(this);

        threadInput = new Thread(() -> {
            while (!endScanning) {
                scanned = scanner.nextLine();
                System.out.println("Loaded: " + scanned);
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
        System.out.println("Please give me a filePath to your maze that you would like me to solve.");
        reset();
        getFilePath();
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

    private void reset() {
        isFileOk = false;
        isFileLoaded = false;
        isStartCorrectlyChosen = false;
        isEndCorrectlyChosen = false;
        endScanning = false;
        startInputBlock = false;
        endInputBlock = false;
        scanned = "";

        filePathScanned = false;
        TerminalWasUsed = false;

    }

    private void noS(char c) {

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

                try {
                    if(Main.startChanged){
                        break;
                    }
                    startX = Integer.parseInt(scanned);
                    
                } catch (NumberFormatException e) {
                }

                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    if(Main.startChanged){
                        break;
                    }
                    startY = Integer.parseInt(scanned);
                } catch (NumberFormatException e) {
                }

                if (Main.ifPath(new int[] { startX, startY }) && !startInputBlock) {
                    isStartCorrectlyChosen = true;
                    maze.setNewStartPosition(startX, startY);
                    watched.setMessage("StartEndNewPositionS");
                    if (c == 'B') {
                        noE();
                    }

                } else if (startInputBlock) {
                    System.out.println("Start has already been chosen");
                    break;
                }

                if (Main.startLocated && Main.endLocated) {
                    watched.setMessage("shortest");
                    break;
                }
            }
            System.out.println("ENDED");
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

                try {
                    if(Main.endChanged){
                        break;
                    }
                    endX = Integer.parseInt(scanned);
                } catch (NumberFormatException e) {
                }

                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    if(Main.endChanged){
                        break;
                    }
                    endY = Integer.parseInt(scanned);
                } catch (NumberFormatException e) {
                }

                if (Main.ifPath(new int[] { endX, endY }) && !endInputBlock) {
                    isEndCorrectlyChosen = true;
                    maze.setNewEndPosition(endX, endY);
                    watched.setMessage("StartEndNewPositionE");
                } else if (endInputBlock) {
                    System.out.println("End had already been chosen");
                    break;
                }

                if (Main.startLocated && Main.endLocated) {
                    watched.setMessage("shortest");
                    break;
                }
            }
        });

        threadInE.start();

    }

    private void blockStartInputs() {
        startInputBlock = true;
    }

    private void blockEndIputs() {
        endInputBlock = true;
    }

    @Override
    public void update(String message) {

        // System.out.println("Terminal: " + message);

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
