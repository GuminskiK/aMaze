package Core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import GUI.Frame;

public class Main{

    static TerminalInterface terminalInterface;
    static Frame frame;

    static MazeAnalyzer mazeAnalyzer;
    static FileReader fileReader;

    static Maze maze;
    static Graph graph;

    static File file;
    static String fileType;
    static String filePath;

    static Integer[] oldStartPosition = new Integer[2];
    static Integer[] oldEndPosition = new Integer[2];

    static int startEndSwitch = 0;

    static char[][] x;

    static int noStartEnd = 0;
    static int startEndInNoStartEnd = 0;

    static Watched watched;
    static MazeSolver mazeSolver;

    public static void main (String[] args){

        ActionListener customListener = new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                changeStartEndIntoWall();
            }
        };

        watched = new Watched();

        maze = new Maze();
        
        frame = new Frame(maze, watched);

        terminalInterface = new TerminalInterface(watched);


        watched.registerObserver(terminalInterface);
        watched.registerObserver(frame);

        watched.registerObserver( new Observer() {

            @Override
            public void update(String message) {
                System.out.println("Main: "  + message);
                switch(message){
                    case "started":
                        watched.setMessage("getFile");
                        break;
                    case "gotFile":
                        reset();
                        break;
                    case "read":
                        read();
                        break;
                    case "reseted":
                        read();
                        break;
                    case "wasDrawn":
                        wasDrawn();
                        break;
                    case "analyze":
                        analyze();
                        break;
                    case "shortest":
                        shortest();
                        break;
                    case "whole":
                        whole();
                        break;
                    case "StartEndNewPositionS":
                        setStartEndNewPosition('S');
                        break;
                    case "StartEndNewPositionE":
                        setStartEndNewPosition('E');
                        break;
                    case "StartEndNewPosition":
                        changeStartEndIntoWall();
                        break;
                    default:
                        break;
                }
            }
            
        });

        watched.setMessage("start");
    }

    private static void reset(){

        noStartEnd = 0;
        startEndInNoStartEnd = 0;
        maze.reset();

        watched.setMessage("reset");
    }


    private static void read(){

        file = new File(filePath);
        fileReader = new FileReader();
        
        if (fileType.equals("txt")){
            
            fileReader.countRowsColumns(file);
            x = fileReader.readFileTXT(file);
            
        } else {

            /*  
            fileReader.readNumberOfRowsColumns(file);
            x = fileReader.ReadFileBIN(file);
            */
        }
        
        maze.setColumns(fileReader.getColumns());
        maze.setRows(fileReader.getRows());
        maze.setMaze(x);
        maze.mazeToMazeCells();
        
        watched.setMessage("toDraw");

    }

    private static void wasDrawn(){
        startEndSwitch = 0;
        watched.setMessage("wasRead");
    }

    private static void analyze(){

        graph = new Graph();

        mazeAnalyzer = new MazeAnalyzer();
        mazeAnalyzer.analyzeMaze(file, fileType, frame.getToolPanel().getChooseStartEndPanel(), maze, graph);

        if (maze.getStart()[0] == null){
            oldStartPosition[0] = null;
            oldStartPosition[1] = null;
        } else {
            oldStartPosition[0] = maze.getStart()[0];
            oldStartPosition[1] = maze.getStart()[1];
        }
        
        if (maze.getEnd()[0] == null){
            oldEndPosition[0] = null;
            oldEndPosition[1] = null;
        } else{
            oldEndPosition[0] = maze.getEnd()[0];
            oldEndPosition[1] = maze.getEnd()[1];
        }


        frame.getToolPanel().toolEnable(true, new int[]{1,2});

        noStartEnd = 0;

        if (frame.getToolPanel().getChooseStartEndPanel().ifNull()[0] == true || frame.getToolPanel().getChooseStartEndPanel().ifNull()[1] == true){

            if (frame.getToolPanel().getChooseStartEndPanel().ifNull()[0] == true && frame.getToolPanel().getChooseStartEndPanel().ifNull()[1] == true){
                frame.getOuterContentPanel().getInfoLabel().setText("Please choose Start and End");
                frame.getToolPanel().toolEnable(true, new int[]{3,4,5});
                frame.getToolPanel().toolEnable(false, new int[]{1,2});
                frame.getToolPanel().getChooseStartEndPanel().setTypeStartEnabled(true);
                frame.getToolPanel().getChooseStartEndPanel().setTypeEndEnabled(true);
                noStartEnd += 2;
            } else if (frame.getToolPanel().getChooseStartEndPanel().ifNull()[0] == true){
                frame.getOuterContentPanel().getInfoLabel().setText("Please choose Start.");
                frame.getToolPanel().toolEnable(true, new int[]{3,4});
                frame.getToolPanel().toolEnable(false, new int[]{1,2});
                frame.getToolPanel().getChooseStartEndPanel().setTypeStartEnabled(true);
                frame.getToolPanel().getChooseStartEndPanel().setTypeEndEnabled(false);

                noStartEnd++;
                startEndInNoStartEnd = 1;
            }
            else if (frame.getToolPanel().getChooseStartEndPanel().ifNull()[1] == true){
                frame.getOuterContentPanel().getInfoLabel().setText("Please choose End.");
                frame.getToolPanel().toolEnable(true, new int[]{3,5});
                frame.getToolPanel().getChooseStartEndPanel().setTypeStartEnabled(false);
                frame.getToolPanel().getChooseStartEndPanel().setTypeEndEnabled(true);
                noStartEnd++;
                startEndInNoStartEnd = 2;
            }

        } else {
            frame.getOuterContentPanel().getInfoLabel().setText("Ypu can choose solving alghorithm or change position of Start and End");
        }
        
        watched.setMessage("analyzed");
    }

    private static void shortest(){

        mazeSolver = new MazeSolver();
        mazeSolver.solveMaze(graph, mazeAnalyzer.getStart(), mazeAnalyzer.getEnd(), 0);

        SolutionDrawer solutionWriter = new SolutionDrawer();
        solutionWriter.drawSolution(mazeSolver.getSolution(), maze);

        watched.setMessage("solved");
    }

    private static void whole(){

        mazeSolver = new MazeSolver();
        mazeSolver.solveMaze(graph, mazeAnalyzer.getStart(), mazeAnalyzer.getEnd(), 1);

        SolutionDrawer solutionWriter = new SolutionDrawer();
        solutionWriter.drawSolution(mazeSolver.getSolution(), maze);

        watched.setMessage("solved");
    }

    private static void setStartEndNewPosition( char c){

        int[] switchSE;
        int[] customObject = new int[2];

        if (c == 'S'){
            customObject[0] = maze.getnewStartPosition()[0];
            customObject[1] = maze.getnewStartPosition()[1];
        } else {
            customObject[0] = maze.getNewEndPosition()[0];
            customObject[1] = maze.getNewEndPosition()[1];
        }

        if(startEndInNoStartEnd != 0){
            if(startEndInNoStartEnd == 2 && c == 'S'){
                maze.changeMazeCellToWall(maze.getStart()[1], maze.getStart()[0]);
                maze.setCharFromMazeInChar2DArray(maze.getStart()[0], maze.getStart()[1], 'P');
                
            } else if (startEndInNoStartEnd == 1 && c == 'E') {
                maze.changeMazeCellToWall(maze.getEnd()[1], maze.getEnd()[0]);
                maze.setCharFromMazeInChar2DArray(maze.getEnd()[0], maze.getEnd()[1], 'K');
            }
        }

        if (  ifPath(customObject) ){
            
            if (c == 'S'){
                switchSE = new int[]{4,5};
                frame.getToolPanel().getChooseStartEndPanel().changeStartPos(customObject[0], customObject[1]);
                frame.getOuterContentPanel().getInfoLabel().setText(" You have chosen a new Start location. Wybrano nowy Start. Locating in the progress...");

            } else {
                switchSE = new int[]{5,4};
                frame.getToolPanel().getChooseStartEndPanel().changeEndPos(customObject[0], customObject[1]);
                frame.getOuterContentPanel().getInfoLabel().setText("You have chosen a new End location. Wybrano nowy Start. Locating in the progress...");

            }

            frame.getToolPanel().toolEnable(false, new int[] {2});

            mazeAnalyzer.customAnalyzer(customObject, c);

            if (c == 'S'){
                maze.changeMazeCellToStart(customObject[1], customObject[0]);
                maze.setCharFromMazeInChar2DArray(customObject[0], customObject[1], 'X');
                maze.setStart(customObject[0], customObject[1]);
            } else {
                maze.changeMazeCellToEnd(customObject[1], customObject[0]);
                maze.setCharFromMazeInChar2DArray(customObject[0], customObject[1], 'X');
                maze.setEnd(customObject[0], customObject[1]);
            }
            
            frame.getOuterContentPanel().getContentPanel().getMazePanel().rePaint(maze);

            if (noStartEnd == 2){
                noStartEnd--;
            } else {
                frame.getToolPanel().toolEnable(true, new int[]{2});
            }
            
            startEndSwitch++;

            if (startEndSwitch == 2){
                frame.getToolPanel().toolEnable(false, switchSE);
                frame.getToolPanel().getChooseStartEndPanel().setTypeStartEnabled(false);
                frame.getToolPanel().getChooseStartEndPanel().setTypeEndEnabled(false);
            } else {
                if (c == 'S'){
                    frame.getToolPanel().getChooseStartEndPanel().setTypeStartEnabled(false);
                    frame.getToolPanel().getChooseStartEndPanel().setTypeEndEnabled(true);
                } else {
                    frame.getToolPanel().getChooseStartEndPanel().setTypeStartEnabled(true);
                    frame.getToolPanel().getChooseStartEndPanel().setTypeEndEnabled(false);    
                }
                frame.getToolPanel().toolEnable(false, new int[]{switchSE[0]});
                frame.getToolPanel().toolEnable(true, new int[]{switchSE[1]});
            }

            if (c == 'S'){
                if (startEndSwitch == 2){
                    frame.getOuterContentPanel().getInfoLabel().setText("Choose solving mode.");
                } else {
                    frame.getOuterContentPanel().getInfoLabel().setText("The choice of a new Start was successful. Now you can choose new End location or choose solving mode.");
                }
                

            } else {
                if (startEndSwitch == 2){
                    frame.getOuterContentPanel().getInfoLabel().setText("Choose solving mode.");
                } else {
                    frame.getOuterContentPanel().getInfoLabel().setText("The choice of a new End was successful. Now ypu can choose new Start location or choose solving mode.");
                }
            }


        } else {

            if (c == 'S'){
                maze.setNewStartPosition(oldStartPosition[0], oldStartPosition[1]); 
                frame.customError();
                if (maze.whoPickedCustom() == 0){
                    frame.getToolPanel().toolEnable(true, new int[]{4});
                } else {
                    frame.getToolPanel().getChooseStartEndPanel().setTypeStartEnabled(true);
                    frame.getToolPanel().getChooseStartEndPanel().setTypeStartOn(0);
                }
                
            } else {
                maze.setNewEndPosition(oldEndPosition[0], oldEndPosition[1]);
                frame.customError();
                if (maze.whoPickedCustom() == 0){
                    frame.getToolPanel().toolEnable(true, new int[]{5});
                } else {
                    frame.getToolPanel().getChooseStartEndPanel().setTypeEndEnabled(true);
                    frame.getToolPanel().getChooseStartEndPanel().setTypeEndOn(0);
                }
                
            }
             
        }
    }

    static void changeStartEndIntoWall(){

        maze.changeMazeCellToWall(maze.getStart()[1], maze.getStart()[0]);
        maze.setCharFromMazeInChar2DArray(maze.getStart()[0] , maze.getStart()[1], 'X');
        maze.changeMazeCellToWall(maze.getEnd()[1], maze.getEnd()[0]);
        maze.setCharFromMazeInChar2DArray(maze.getEnd()[0], maze.getEnd()[1], 'X');
        
        frame.getOuterContentPanel().getContentPanel().getMazePanel().rePaint(maze);
        frame.getToolPanel().toolEnable(false, new int[]{1});
        
    }



    private static boolean ifPath(int[] customObject){

        boolean x = false;
        if (customObject[0] < maze.getColumns() && customObject[1] < maze.getColumns()){
            if (maze.getCharFromMazeInChar2DArray(customObject[0], customObject[1]) == ' '){
                x = true;
            }
        }

        return x;
    }

    public static void setFileType(String fileType2){
        fileType = fileType2;
    }

    public static void setFilePath(String filePath2){
        filePath = filePath2;
    }

}
