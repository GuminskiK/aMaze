package Core;

import java.io.File;

import GUI.Frame;

public class Main {

    private static TerminalInterface terminalInterface;
    private static Frame frame;

    private static MazeAnalyzer mazeAnalyzer;
    private static FileReader fileReader;

    private static Maze maze;
    private static Graph graph;

    private static File file;
    private static String fileType;
    private static String filePath;

    private static String filePathToExport;

    private static Integer[] oldStartPosition = new Integer[2];
    private static Integer[] oldEndPosition = new Integer[2];

    private static char[][] x;

    private static Watched watched;
    private static MazeSolver mazeSolver;

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
                // System.out.println("Main: " + message);
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
                        watched.setMessage("wasRead");
                        break;
                    case "analyze":
                        analyze();
                        break;
                    case "shortest":
                        solveShortestPath();
                        break;
                    case "whole":
                        solveWholePath();
                        break;
                    case "StartEndNewPositionS":
                        setStartEndNewPosition('S');
                        break;
                    case "StartEndNewPositionE":
                        setStartEndNewPosition('E');
                        break;
                    case "StartEndNewPosition":
                        changeStartIntoWallPath();
                        changeEndIntoWallPath();
                        break;
                    case "export":
                        export();
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
        fileReader = new FileReader(file, maze);

        if (fileType.equals("txt")) {

            fileReader.countRowsColumns();
            x = fileReader.readFileTXT();

        } else {

            fileReader.readNumberOfRowsColumns();
            x = fileReader.ReadFileBIN();

        }

        maze.setColumns(fileReader.getColumns());
        maze.setRows(fileReader.getRows());
        maze.setMaze(x);
        maze.mazeToMazeCells();

        watched.setMessage("toDraw");

    }

    private static void analyze() {

        graph = new Graph();

        mazeAnalyzer = new MazeAnalyzer(frame.getToolPanel().getChooseStartEndPanel(), maze, graph);
        mazeAnalyzer.analyze();

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
            frame.getOuterContentPanel().getInfoLabel()
                    .setText("You can choose solving alghorithm or change position of Start and End");
            maze.setStartLocated(true);
            maze.setEndLocated(true);
        }
        if (maze.getStartLocated() && maze.getEndLocated()) {
            watched.setMessage("analyzed");
        }

    }

    private static void solveShortestPath() {

        mazeSolver = new MazeSolver(graph, mazeAnalyzer.getStart(), mazeAnalyzer.getEnd(), 0);
        mazeSolver.solveMaze();

        SolutionDrawer solutionWriter = new SolutionDrawer(maze);
        solutionWriter.drawSolution(mazeSolver.getSolution());

        watched.setMessage("solved");
    }

    private static void solveWholePath() {

        mazeSolver = new MazeSolver(graph, mazeAnalyzer.getStart(), mazeAnalyzer.getEnd(), 1);
        mazeSolver.solveMaze();

        SolutionDrawer solutionWriter = new SolutionDrawer(maze);
        solutionWriter.drawSolution(mazeSolver.getSolution());

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
            changeStartIntoWallPath();
        }

        if (maze.getEndLocated() && c == 'E') {
            changeEndIntoWallPath();
        }

        if (ifPath(customObject)) {

            if (c == 'S') {
                maze.setStartLocated(true);
                maze.setStartChanged(true);

                maze.setCharFromMazeInChar2DArray(customObject[0], customObject[1], 'P');
                maze.changeMazeCellToStart(customObject[1], customObject[0]);
                maze.setStart(customObject[0], customObject[1]);
                frame.getToolPanel().getChooseStartEndPanel().changeStartPos(customObject[0], customObject[1]);
                watched.setMessage("StartChanged");

            } else {
                maze.setEndLocated(true);
                maze.setEndChanged(true);

                maze.setCharFromMazeInChar2DArray(customObject[0], customObject[1], 'K');
                maze.changeMazeCellToEnd(customObject[1], customObject[0]);
                maze.setEnd(customObject[0], customObject[1]);
                frame.getToolPanel().getChooseStartEndPanel().changeEndPos(customObject[0], customObject[1]);
                watched.setMessage("EndChanged");
            }

            mazeAnalyzer.newStartEndAnalyzer(customObject, c);

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

    private static void changeStartIntoWallPath() {

        if (maze.getColumns() == maze.getStart()[1] + 1 || maze.getRows() == maze.getStart()[0] + 1 || maze.getStart()[1]== 0 || maze.getStart()[0] == 0) {

            maze.changeMazeCellToWall(maze.getStart()[1], maze.getStart()[0]);
            maze.setCharFromMazeInChar2DArray(maze.getStart()[0], maze.getStart()[1], 'X');

        } else {

            maze.changeMazeCellToPath(maze.getStart()[1], maze.getStart()[0]);
            maze.setCharFromMazeInChar2DArray(maze.getStart()[0], maze.getStart()[1], ' ');

        }

        frame.getOuterContentPanel().getContentPanel().getMazePanel().rePaint(maze);
        frame.getToolPanel().toolEnable(false, new int[] { 1 });

    }

    private static void changeEndIntoWallPath(){

        if (maze.getColumns() == maze.getEnd()[1] + 1 || maze.getRows() == maze.getEnd()[0] + 1 || maze.getEnd()[1] == 0 || maze.getEnd()[0] == 0) {

            maze.changeMazeCellToWall(maze.getEnd()[1], maze.getEnd()[0]);
            maze.setCharFromMazeInChar2DArray(maze.getEnd()[0], maze.getEnd()[1], 'X');

        } else {

            maze.changeMazeCellToPath(maze.getEnd()[1], maze.getEnd()[0]);
            maze.setCharFromMazeInChar2DArray(maze.getEnd()[0], maze.getEnd()[1], ' ');

        }

        frame.getOuterContentPanel().getContentPanel().getMazePanel().rePaint(maze);
        frame.getToolPanel().toolEnable(false, new int[] { 1 });
    }

    public static boolean ifPath(int[] customObject) {

        boolean x = false;
        if (customObject[0] < maze.getColumns() && customObject[1] < maze.getRows()) {
            if (maze.getCharFromMazeInChar2DArray(customObject[0], customObject[1]) == ' ') {
                x = true;
            }
        }

        return x;
    }

    private static void export() {
        SolutionExporter solutionExporter = new SolutionExporter(mazeSolver.getSolution(), maze, filePathToExport,
                mazeSolver.getLengthMin(), watched);
        solutionExporter.exportSolution();
    }

    public static void setFileType(String fileType2) {
        fileType = fileType2;
    }

    public static void setFilePath(String filePath2) {
        filePath = filePath2;
    }

    public static MazeSolver getMazeSolver() {
        return mazeSolver;
    }

    public static void setFilePathToExport(String filePath) {
        filePathToExport = filePath;
    }
}
