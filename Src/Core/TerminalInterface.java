package Core;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class TerminalInterface implements Observer {

    private Scanner scanner = new Scanner(System.in);
    private boolean isFileOk = false;
    private String filePath;

    private int currentRow;
    private int currentColumn;
    private int lastDirection;

    private int startX;
    private int startY;
    private int endX;
    private int endY;

    private Watched watched;
    private Thread threadInput;
    private Thread threadInS;
    private Thread threadInE;
    private boolean isFileLoaded = false;
    private boolean isStartCorrectlyChosen = false;
    private boolean isEndCorrectlyChosen = false;
    private boolean endScanning = false;
    private boolean startInputBlock = false;
    private boolean endInputBlock = false;

    private String scanned = "";
    private Watched lineScanned;

    private boolean filePathScanned = false;

    private boolean terminalWasUsed = false;

    private Maze maze;

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
                    terminalWasUsed = true;
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

    private void notifier() {

        synchronized (this) {
            this.notifyAll();
        }

    }

    private void scanningNextLine() {
        lineScanned = new Watched();
        lineScanned.registerObserver(this);

        threadInput = new Thread(() -> {
            while (!endScanning) {
                scanned = scanner.nextLine();
                lineScanned.setMessage("lineScanned");
            }
        });
        threadInput.start();
    }

    private void gotFile() {
        isFileLoaded = true;
    }

    private void wasRead() {
        if (terminalWasUsed) {
            watched.setMessage("analyze");
        }
    }

    private void analyzed() {
        if (terminalWasUsed) {
            watched.setMessage("shortest");
        }
    }

    private void solved() {

        

        System.out.println("The maze has been successfully solved");
        if (terminalWasUsed) {
            writeSolution(Main.getMazeSolver().getSolution());
        }
        System.out.println("Please give me a filePath to your maze that you would like me to solve.");
        reset();
        getFilePath();
    }

    private void writeSolution(ArrayList<SolutionBlock> solution) {
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
        terminalWasUsed = false;

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
                    if(maze.getStartChanged()){
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
                    if(maze.getStartChanged()){
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

                if (maze.getStartLocated() && maze.getEndLocated()) {
                    watched.setMessage("shortest");
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

                try {
                    if(maze.getEndChanged()){
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
                    if(maze.getEndChanged()){
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

                if (maze.getStartLocated() && maze.getEndLocated()) {
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
                notifier();
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
                notifier();
                break;
            case "noStart":
                noS('S');
                notifier();
                break;
            case "noEnd":
                noE();
                notifier();
                break;
            case "lineScanned":
                notifier();
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
