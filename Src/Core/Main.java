package Core;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import GUI.MyFrame;

public class Main {

    static MyFrame frame;
    static MazeAnalyzer mazeAnalyzer;
    static FileReader fileReader;
    static Maze maze;
    static Graph graph;
    static File file;
    static int columns;
    static Color lastStartColor = new Color (0,0,0);
    static Color lastEndColor = new Color (0,0,0);
    static Integer[] oldCustomStart = new Integer[2];
    static Integer[] oldCustomEnd = new Integer[2];
    static int startEndSwitch = 0;
    static char[][] x;
    static int noStartEnd = 0;
    static int startEndInNoStartEnd = 0;
    

    public static void main (String[] args){

        ActionListener readListener = new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                Read();
            }

        };

        ActionListener analyzeListener = new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                Analyze();
            }

        };

        ActionListener shortestListener = new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                Shortest();
            }

        };

        ActionListener wholeListener = new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                Whole();
            }

        };

        ActionListener helpListener = new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                help();
            }
        };

        ActionListener customStartListener = new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                customChange('S');
            }
        };

        ActionListener customEndListener = new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                customChange('E');
            }
        };

        ActionListener customListener = new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                InOutWall();
            }
        };
        maze = new Maze();
        frame = new MyFrame(readListener, analyzeListener, shortestListener, helpListener, customStartListener, 
        customEndListener, wholeListener, customListener, maze);
        frame.getMenu().setexportEnabled(false);
        frame.getInfoLabel().setText("Proszę załadować labirynt Files->Load Maze.");
        
        
    }

    private static void help(){
        frame.getContentPanel().setHelpEnabled();
    }

    private static void Read(){

        reset();
        frame.getInfoLabel().setText("Ładowanie labiryntu...");
        frame.getMenu().setloadEnabled(false);
        frame.getToolPanel().ToolEnable(false, new int[]{0,1,2,3,4,5});

        file = frame.getMenu().getFile();
        fileReader = new FileReader();
        
        if (frame.getMenu().getFileType().equals("txt")){
            
            fileReader.CountRowsColumns(file);
            x = fileReader.ReadFileTXT(file);
            
        } else {

            /*  
            fileReader.readNumberOfRowsColumns(file);
            x = fileReader.ReadFileBIN(file);
            */
        }
        
        maze.setColumns(fileReader.getColumns());
        maze.setRows(fileReader.getRows());
        maze.setMaze(x);
        
        //rysowanie
        frame.getContentPanel().addPanel(fileReader.getColumns(), fileReader.getRows(), maze);

        frame.getToolPanel().ToolEnable(true, new int[]{0}); //Analyze

        startEndSwitch = 0;

        frame.getInfoLabel().setText("Proszę nacisnąć Analyze maze by przeanalizować labirynt.");

    }

    private static void Analyze(){
        
        frame.getInfoLabel().setText("Analizowanie w toku...");
        frame.getToolPanel().ToolEnable(false, new int[]{0});

        graph = new Graph();

        mazeAnalyzer = new MazeAnalyzer();
        mazeAnalyzer.analyzeMaze(file, frame.getMenu().getFileType(), frame.getToolPanel().getCustomPanel(), maze, graph);

        if (maze.getStart()[0] == null){
            oldCustomStart[0] = null;
            oldCustomStart[1] = null;
        } else {
            oldCustomStart[0] = maze.getStart()[0];
            oldCustomStart[1] = maze.getStart()[1];
        }
        
        if (maze.getEnd()[0] == null){
            oldCustomEnd[0] = null;
            oldCustomEnd[1] = null;
        } else{
            oldCustomEnd[0] = maze.getEnd()[0];
            oldCustomEnd[1] = maze.getEnd()[1];
        }


        frame.getToolPanel().ToolEnable(true, new int[]{1,2});

        noStartEnd = 0;

        if (frame.getToolPanel().getCustomPanel().ifNull()[0] == true || frame.getToolPanel().getCustomPanel().ifNull()[1] == true){

            if (frame.getToolPanel().getCustomPanel().ifNull()[0] == true && frame.getToolPanel().getCustomPanel().ifNull()[1] == true){
                frame.getInfoLabel().setText("Proszę wybrać Start i End.");
                frame.getToolPanel().ToolEnable(true, new int[]{3,4,5});
                frame.getToolPanel().ToolEnable(false, new int[]{1,2});
                frame.getToolPanel().getCustomPanel().setTypeStartEnabled(true);
                frame.getToolPanel().getCustomPanel().setTypeEndEnabled(true);
                noStartEnd += 2;
            } else if (frame.getToolPanel().getCustomPanel().ifNull()[0] == true){
                frame.getInfoLabel().setText("Proszę wybrać Start.");
                frame.getToolPanel().ToolEnable(true, new int[]{3,4});
                frame.getToolPanel().ToolEnable(false, new int[]{1,2});
                frame.getToolPanel().getCustomPanel().setTypeStartEnabled(true);
                frame.getToolPanel().getCustomPanel().setTypeEndEnabled(false);

                noStartEnd++;
                startEndInNoStartEnd = 1;
            }
            else if (frame.getToolPanel().getCustomPanel().ifNull()[1] == true){
                frame.getInfoLabel().setText("Proszę wybrać End.");
                frame.getToolPanel().ToolEnable(true, new int[]{3,5});
                frame.getToolPanel().getCustomPanel().setTypeStartEnabled(false);
                frame.getToolPanel().getCustomPanel().setTypeEndEnabled(true);
                noStartEnd++;
                startEndInNoStartEnd = 2;
            }

        } else {
            frame.getInfoLabel().setText("Można wybrać tryb rozwiązywania labiryntu, a także nowy Start/End");
        }
        

    }

    private static void Shortest(){

        frame.getToolPanel().ToolEnable(false, new int[]{0,1,2,3,4});
        frame.getInfoLabel().setText("Szukam najkrótszego rozwiązania labiryntu...");
        MazeSolver mazeSolver = new MazeSolver();
        mazeSolver.solveMaze(graph, mazeAnalyzer.getStart(), mazeAnalyzer.getEnd());

        SolutionWriter solutionWriter = new SolutionWriter();
        solutionWriter.WriteSolution(mazeSolver.getSave(), maze, mazeAnalyzer.getStart(), mazeAnalyzer.getEnd(), graph);
        frame.getContentPanel().getMazePanel().rePaint(maze);
        frame.getInfoLabel().setText("<html>Znaleziono najkrótsze rozwiązanie labiryntu, by załadować inny labirynt wybierz Files->Load Maze, by wyeskportować rozwiązanie wybierz Files-> Export Solution.</html>");
        frame.getMenu().setloadEnabled(true);
        frame.getMenu().setexportEnabled(true);
    }

    private static void Whole(){

        frame.getToolPanel().ToolEnable(false, new int[]{0,1,2,3,4});

        SolutionWriterWhole solutionWriterWhole = new SolutionWriterWhole();
        solutionWriterWhole.solveMaze(mazeAnalyzer.getStart(), mazeAnalyzer.getEnd(), maze, graph);
        frame.getContentPanel().getMazePanel().rePaint(maze);
        frame.getInfoLabel().setText("<html> Znaleziono rozwiązanie labiryntu, by załadować inny labirynt wybierz Files->Load Maze, by wyeskportować rozwiązanie wybierz Files-> Export Solution. </html>");
        frame.getMenu().setloadEnabled(true);
        frame.getMenu().setexportEnabled(true);

    }

    private static void customChange( char c){

        char colorNr;
        int[] switchSE;
        int[] customObject = new int[2];

        if (c == 'S'){
            customObject[0] = maze.getCustomStart()[0];
            customObject[1] = maze.getCustomStart()[1];
        } else {
            customObject[0] = maze.getCustomEnd()[0];
            customObject[1] = maze.getCustomEnd()[1];
        }

        if(startEndInNoStartEnd != 0){
            if(startEndInNoStartEnd == 2 && c == 'S'){
                maze.setCharFromMaze(maze.getStart()[0], maze.getStart()[1], 'X');
            } else if (startEndInNoStartEnd == 1 && c == 'E') {
                maze.setCharFromMaze(maze.getEnd()[0], maze.getEnd()[1], 'X');
            }
        }

        if (  ifPath(customObject) ){
            
            if (c == 'S'){

                colorNr = 'P';
                switchSE = new int[]{4,5};
                frame.getToolPanel().getCustomPanel().changeStartPos(customObject[0], customObject[1]);
                frame.getInfoLabel().setText("Wybrano nowy Start. Trwa jego lokalizowanie...");

            } else {

                colorNr = 'K';
                switchSE = new int[]{5,4};
                frame.getToolPanel().getCustomPanel().changeEndPos(customObject[0], customObject[1]);
                frame.getInfoLabel().setText("Wybrano nowy End. Trwa jego lokalizowanie...");

            }

            frame.getToolPanel().ToolEnable(false, new int[] {2});

            mazeAnalyzer.customAnalyzer(customObject, c);

            maze.setCharFromMaze(customObject[0], customObject[1], colorNr);
            frame.getContentPanel().getMazePanel().rePaint(maze);

            if (c == 'S'){
                maze.setStart(customObject[0], customObject[1]);
            } else {
                maze.setEnd(customObject[0], customObject[1]);
            }

            if (noStartEnd == 2){
                noStartEnd--;
            } else {
                frame.getToolPanel().ToolEnable(true, new int[]{2});
            }
            
            startEndSwitch++;

            if (startEndSwitch == 2){
                frame.getToolPanel().ToolEnable(false, switchSE);
                frame.getToolPanel().getCustomPanel().setTypeStartEnabled(false);
                frame.getToolPanel().getCustomPanel().setTypeEndEnabled(false);
            } else {
                if (c == 'S'){
                    frame.getToolPanel().getCustomPanel().setTypeStartEnabled(false);
                    frame.getToolPanel().getCustomPanel().setTypeEndEnabled(true);
                } else {
                    frame.getToolPanel().getCustomPanel().setTypeStartEnabled(true);
                    frame.getToolPanel().getCustomPanel().setTypeEndEnabled(false);    
                }
                frame.getToolPanel().ToolEnable(false, new int[]{switchSE[0]});
                frame.getToolPanel().ToolEnable(true, new int[]{switchSE[1]});
            }

            if (c == 'S'){
                if (startEndSwitch == 2){
                    frame.getInfoLabel().setText("Wybierz tryb rozwiązywania.");
                } else {
                    frame.getInfoLabel().setText("Wybór Startu zakończył się pomyślnie. Możesz teraz wybrać End (PickEnd) lub tryb rozwiązywania.");
                }
                

            } else {
                if (startEndSwitch == 2){
                    frame.getInfoLabel().setText("Wybierz tryb rozwiązywania.");
                } else {
                    frame.getInfoLabel().setText("Wybór Endu zakończył się pomyślnie. Możesz teraz wybrać Start (PickStart) lub tryb rozwiązywania.");
                }
            }


        } else {

            if (c == 'S'){
                maze.setCustomStart(oldCustomStart[0], oldCustomStart[1]); 
                frame.customError();
                if (maze.whoPickedCustom() == 0){
                    frame.getToolPanel().ToolEnable(true, new int[]{4});
                } else {
                    frame.getToolPanel().getCustomPanel().setTypeStartEnabled(true);
                    frame.getToolPanel().getCustomPanel().setTypeStartOn(0);
                }
                
            } else {
                maze.setCustomEnd(oldCustomEnd[0], oldCustomEnd[1]);
                frame.customError();
                if (maze.whoPickedCustom() == 0){
                    frame.getToolPanel().ToolEnable(true, new int[]{5});
                } else {
                    frame.getToolPanel().getCustomPanel().setTypeEndEnabled(true);
                    frame.getToolPanel().getCustomPanel().setTypeEndOn(0);
                }
                
            }
             
        }
    }

    static void InOutWall(){

        maze.setCharFromMaze(maze.getStart()[0], maze.getStart()[1], 'X');
        maze.setCharFromMaze(maze.getEnd()[0], maze.getEnd()[1], 'X');
        
        frame.getContentPanel().getMazePanel().rePaint(maze);
/* 
        if( startEndSwitch == 0){
            frame.getToolPanel().ToolEnable(true, new int[]{2,3});
        }
*/
        frame.getToolPanel().ToolEnable(false, new int[]{1});
        
    }

    private static void reset(){
        noStartEnd = 0;
        startEndInNoStartEnd = 0;
        frame.getToolPanel().getCustomPanel().changeStartPos(null,null);
        frame.getToolPanel().getCustomPanel().changeEndPos(null,null);
        frame.getMenu().setexportEnabled(false);
        frame.getToolPanel().getCustomPanel().reset();
        frame.getToolPanel().getCustomPanel().setTypeStartEnabled(true);
        frame.getToolPanel().getCustomPanel().setTypeEndEnabled(true);
        frame.getToolPanel().resetComboBoX();
        maze.reset();
    }

    private static boolean ifPath(int[] customObject){

        boolean x = false;
        if (customObject[0] < maze.getColumns() && customObject[1] < maze.getColumns()){
            if (maze.getCharFromMaze(customObject[0], customObject[1]) == ' '){
                x = true;
            }
        }

        return x;
    }
}
