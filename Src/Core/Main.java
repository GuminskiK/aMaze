package Core;
import java.io.File;

import GUI.Frame;

public class Main {

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

    static char[][] x;

    static Watched watched;
    static MazeSolver mazeSolver;

    public static void main(String[] args) {

        watched = new Watched();

        maze = new Maze();

        frame = new Frame(maze, watched);

        terminalInterface = new TerminalInterface(watched, maze);

        watched.registerObserver(terminalInterface);
        watched.registerObserver(frame);

        watched.registerObserver(new Observer() {

            @Override
            public void update(String message) {
                //System.out.println("Main: " + message);
                switch (message) {
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

    private static void reset() {

        maze.reset();
        watched.setMessage("reset");
    }

    private static void read() {

        file = new File(filePath);
        fileReader = new FileReader();

        if (fileType.equals("txt")) {

            fileReader.countRowsColumns(file);
            x = fileReader.readFileTXT(file);

        } else {

            /*
             * fileReader.readNumberOfRowsColumns(file);
             * x = fileReader.ReadFileBIN(file);
             */
        }

        maze.setColumns(fileReader.getColumns());
        maze.setRows(fileReader.getRows());
        maze.setMaze(x);
        maze.mazeToMazeCells();

        watched.setMessage("toDraw");

    }

    private static void wasDrawn() {
        watched.setMessage("wasRead");
    }

    private static void analyze() {

        graph = new Graph();

        mazeAnalyzer = new MazeAnalyzer();
        mazeAnalyzer.analyzeMaze(file, fileType, frame.getToolPanel().getChooseStartEndPanel(), maze, graph);

        if (maze.getStart()[0] == null) {
            oldStartPosition[0] = null;
            oldStartPosition[1] = null;
        } else {
            oldStartPosition[0] = maze.getStart()[0];
            oldStartPosition[1] = maze.getStart()[1];
        }

        if (maze.getEnd()[0] == null) {
            oldEndPosition[0] = null;
            oldEndPosition[1] = null;
        } else {
            oldEndPosition[0] = maze.getEnd()[0];
            oldEndPosition[1] = maze.getEnd()[1];
        }

        frame.getToolPanel().toolEnable(true, new int[] { 1, 2 });

        if (frame.getToolPanel().getChooseStartEndPanel().ifNull()[0] == true
                || frame.getToolPanel().getChooseStartEndPanel().ifNull()[1] == true) {

            if (frame.getToolPanel().getChooseStartEndPanel().ifNull()[0] == true
                    && frame.getToolPanel().getChooseStartEndPanel().ifNull()[1] == true) {
                frame.getOuterContentPanel().getInfoLabel().setText("Please choose Start and End");
                frame.getToolPanel().toolEnable(true, new int[] { 3, 4, 5 });
                frame.getToolPanel().toolEnable(false, new int[] { 1, 2 });
                frame.getToolPanel().getChooseStartEndPanel().setTypeStartEnabled(true);
                frame.getToolPanel().getChooseStartEndPanel().setTypeEndEnabled(true);
                watched.setMessage("noStartEnd");
            } else if (frame.getToolPanel().getChooseStartEndPanel().ifNull()[0] == true) {
                frame.getOuterContentPanel().getInfoLabel().setText("Please choose Start.");
                frame.getToolPanel().toolEnable(true, new int[] { 3, 4 });
                frame.getToolPanel().toolEnable(false, new int[] { 1, 2 });
                frame.getToolPanel().getChooseStartEndPanel().setTypeStartEnabled(true);
                frame.getToolPanel().getChooseStartEndPanel().setTypeEndEnabled(false);

                maze.setEndLocated(true);
                watched.setMessage("noStart");
            } else if (frame.getToolPanel().getChooseStartEndPanel().ifNull()[1] == true) {
                frame.getOuterContentPanel().getInfoLabel().setText("Please choose End.");
                frame.getToolPanel().toolEnable(true, new int[] { 3, 5 });
                frame.getToolPanel().getChooseStartEndPanel().setTypeStartEnabled(false);
                frame.getToolPanel().getChooseStartEndPanel().setTypeEndEnabled(true);

                maze.setStartLocated(true);
                watched.setMessage("noEnd");
            }

        } else {
            frame.getOuterContentPanel().getInfoLabel().setText("You can choose solving alghorithm or change position of Start and End");
            maze.setStartLocated(true);
            maze.setEndLocated(true);
        }
        if (maze.getStartLocated() && maze.getEndLocated()) {
            watched.setMessage("analyzed");
        }

    }

    private static void shortest() {

        mazeSolver = new MazeSolver();
        mazeSolver.solveMaze(graph, mazeAnalyzer.getStart(), mazeAnalyzer.getEnd(), 0);

        SolutionDrawer solutionWriter = new SolutionDrawer();
        solutionWriter.drawSolution(mazeSolver.getSolution(), maze);

        watched.setMessage("solved");
    }

    private static void whole() {

        mazeSolver = new MazeSolver();
        mazeSolver.solveMaze(graph, mazeAnalyzer.getStart(), mazeAnalyzer.getEnd(), 1);

        SolutionDrawer solutionWriter = new SolutionDrawer();
        solutionWriter.drawSolution(mazeSolver.getSolution(), maze);

        watched.setMessage("solved");
    }

    private static void setStartEndNewPosition(char c) {

        int[] customObject = new int[2];

        if (c == 'S') {
            customObject[0] = maze.getNewStartPosition()[0];
            customObject[1] = maze.getNewStartPosition()[1];
        } else {
            customObject[0] = maze.getNewEndPosition()[0];
            customObject[1] = maze.getNewEndPosition()[1];
        }

        if (maze.getStartLocated() && c == 'S') {
            maze.setCharFromMazeInChar2DArray(maze.getStart()[0], maze.getStart()[1], 'P');
            maze.changeMazeCellToWall(maze.getStart()[1], maze.getStart()[0]);
        }

        if (maze.getEndLocated() && c == 'E') {
            maze.setCharFromMazeInChar2DArray(maze.getEnd()[0], maze.getEnd()[1], 'K');
            maze.changeMazeCellToWall(maze.getEnd()[1], maze.getEnd()[0]);
        }

        if (ifPath(customObject)) {

            if (c == 'S') {
                maze.setStartLocated(true);
                maze.setStartChanged(true);

                maze.changeMazeCellToStart(customObject[1], customObject[0]);
                maze.setStart(customObject[0], customObject[1]);
                frame.getToolPanel().getChooseStartEndPanel().changeStartPos(customObject[0], customObject[1]);
                watched.setMessage("StartChanged");

            } else {
                maze.setEndLocated(true);
                maze.setEndChanged(true);

                maze.changeMazeCellToEnd(customObject[1], customObject[0]);
                maze.setEnd(customObject[0], customObject[1]);
                frame.getToolPanel().getChooseStartEndPanel().changeEndPos(customObject[0], customObject[1]);
                watched.setMessage("EndChanged");
            }

            mazeAnalyzer.customAnalyzer(customObject, c);
            maze.setCharFromMazeInChar2DArray(customObject[1], customObject[0], 'X');

            frame.getOuterContentPanel().getContentPanel().getMazePanel().rePaint(maze);

        } else {

            if (c == 'S') {
                maze.setNewStartPosition(oldStartPosition[0], oldStartPosition[1]);
                watched.setMessage("changeStartError");
            } else {
                maze.setNewEndPosition(oldEndPosition[0], oldEndPosition[1]);
                watched.setMessage("changeEndError");
            }
        }
    }

    static void changeStartEndIntoWall() {

        maze.changeMazeCellToWall(maze.getStart()[1], maze.getStart()[0]);
        maze.setCharFromMazeInChar2DArray(maze.getStart()[0], maze.getStart()[1], 'X');
        maze.changeMazeCellToWall(maze.getEnd()[1], maze.getEnd()[0]);
        maze.setCharFromMazeInChar2DArray(maze.getEnd()[0], maze.getEnd()[1], 'X');

        frame.getOuterContentPanel().getContentPanel().getMazePanel().rePaint(maze);
        frame.getToolPanel().toolEnable(false, new int[] { 1 });

    }

    public static boolean ifPath(int[] customObject) {

        boolean x = false;
        if (customObject[0] < maze.getColumns() && customObject[1] < maze.getColumns()) {
            if (maze.getCharFromMazeInChar2DArray(customObject[0], customObject[1]) == ' ') {
                x = true;
            }
        }

        return x;
    }

    public static void setFileType(String fileType2) {
        fileType = fileType2;
    }

    public static void setFilePath(String filePath2) {
        filePath = filePath2;
    }

}
