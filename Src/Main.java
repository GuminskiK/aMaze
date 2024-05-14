import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
        frame.menuBar.setexportEnabled(false);
        frame.infoLabel.setText("Proszę załadować labirynt Files->Load Maze.");
        
        
    }

    private static void help(){
        frame.contentPanel.setHelpEnabled();
    }

    private static void Read(){

        reset();
        frame.infoLabel.setText("Ładowanie labiryntu...");
        frame.menuBar.setloadEnabled(false);
        frame.toolPanel.ToolEnable(false, new int[]{0,1,2,3,4,5});

        file = frame.menuBar.file;
        fileReader = new FileReader();
        
        if (frame.menuBar.fileType.equals("txt")){
            
            fileReader.CountRowsColumns(file);
            columns = fileReader.columns;
            x = fileReader.ReadFileTXT(file);
            
        } else {

            /*  
            fileReader.readNumberOfRowsColumns(file);
            columns = fileReader.columns;
            x = fileReader.ReadFileBIN(file);
            */
        }
        
        maze.setColumns(fileReader.columns);
        maze.setRows(fileReader.rows);
        maze.setMaze(x);
        
        //rysowanie
        frame.contentPanel.addPanel(fileReader.columns, fileReader.rows, maze);

        frame.toolPanel.ToolEnable(true, new int[]{0}); //Analyze

        startEndSwitch = 0;

        frame.infoLabel.setText("Proszę nacisnąć Analyze maze by przeanalizować labirynt.");

    }

    private static void Analyze(){
        
        frame.infoLabel.setText("Analizowanie w toku...");
        frame.toolPanel.ToolEnable(false, new int[]{0});

        graph = new Graph();

        mazeAnalyzer = new MazeAnalyzer();
        mazeAnalyzer.analyzeMaze(file, frame.menuBar.fileType, frame.toolPanel.customPanel, maze, graph);

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


        frame.toolPanel.ToolEnable(true, new int[]{1,2});

        noStartEnd = 0;

        if (frame.toolPanel.customPanel.ifNull()[0] == true || frame.toolPanel.customPanel.ifNull()[1] == true){

            if (frame.toolPanel.customPanel.ifNull()[0] == true && frame.toolPanel.customPanel.ifNull()[1] == true){
                frame.infoLabel.setText("Proszę wybrać Start i End.");
                frame.toolPanel.ToolEnable(true, new int[]{3,4,5});
                frame.toolPanel.ToolEnable(false, new int[]{1,2});
                frame.toolPanel.customPanel.setTypeStartEnabled(true);
                frame.toolPanel.customPanel.setTypeEndEnabled(true);
                noStartEnd += 2;
            } else if (frame.toolPanel.customPanel.ifNull()[0] == true){
                frame.infoLabel.setText("Proszę wybrać Start.");
                frame.toolPanel.ToolEnable(true, new int[]{3,4});
                frame.toolPanel.ToolEnable(false, new int[]{1,2});
                frame.toolPanel.customPanel.setTypeStartEnabled(true);
                frame.toolPanel.customPanel.setTypeEndEnabled(false);

                noStartEnd++;
                startEndInNoStartEnd = 1;
            }
            else if (frame.toolPanel.customPanel.ifNull()[1] == true){
                frame.infoLabel.setText("Proszę wybrać End.");
                frame.toolPanel.ToolEnable(true, new int[]{3,5});
                frame.toolPanel.customPanel.setTypeStartEnabled(false);
                frame.toolPanel.customPanel.setTypeEndEnabled(true);
                noStartEnd++;
                startEndInNoStartEnd = 2;
            }

        } else {
            frame.infoLabel.setText("Można wybrać tryb rozwiązywania labiryntu, a także nowy Start/End");
        }
        

    }

    private static void Shortest(){

        frame.toolPanel.ToolEnable(false, new int[]{0,1,2,3,4});
        frame.infoLabel.setText("Szukam najkrótszego rozwiązania labiryntu...");
        MazeSolver mazeSolver = new MazeSolver();
        mazeSolver.solveMaze(graph, mazeAnalyzer.start, mazeAnalyzer.end);

        SolutionWriter solutionWriter = new SolutionWriter();
        solutionWriter.WriteSolution(mazeSolver.save, maze, mazeAnalyzer.start, mazeAnalyzer.end, graph);
        frame.contentPanel.mazePanel.rePaint(maze);
        frame.infoLabel.setText("<html>Znaleziono najkrótsze rozwiązanie labiryntu, by załadować inny labirynt wybierz Files->Load Maze, by wyeskportować rozwiązanie wybierz Files-> Export Solution.</html>");
        frame.menuBar.setloadEnabled(true);
        frame.menuBar.setexportEnabled(true);
    }

    private static void Whole(){

        frame.toolPanel.ToolEnable(false, new int[]{0,1,2,3,4});

        SolutionWriterWhole solutionWriterWhole = new SolutionWriterWhole();
        solutionWriterWhole.solveMaze(mazeAnalyzer.start, mazeAnalyzer.end, maze, graph);
        frame.contentPanel.mazePanel.rePaint(maze);
        frame.infoLabel.setText("<html> Znaleziono rozwiązanie labiryntu, by załadować inny labirynt wybierz Files->Load Maze, by wyeskportować rozwiązanie wybierz Files-> Export Solution. </html>");
        frame.menuBar.setloadEnabled(true);
        frame.menuBar.setexportEnabled(true);

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
                frame.toolPanel.customPanel.changeStartPos(customObject[0], customObject[1]);
                frame.infoLabel.setText("Wybrano nowy Start. Trwa jego lokalizowanie...");

            } else {

                colorNr = 'K';
                switchSE = new int[]{5,4};
                frame.toolPanel.customPanel.changeEndPos(customObject[0], customObject[1]);
                frame.infoLabel.setText("Wybrano nowy End. Trwa jego lokalizowanie...");

            }

            frame.toolPanel.ToolEnable(false, new int[] {2});

            mazeAnalyzer.customAnalyzer(customObject, c);

            maze.setCharFromMaze(customObject[0], customObject[1], colorNr);
            frame.contentPanel.mazePanel.rePaint(maze);

            if (c == 'S'){
                maze.setStart(customObject[0], customObject[1]);
            } else {
                maze.setEnd(customObject[0], customObject[1]);
            }

            if (noStartEnd == 2){
                noStartEnd--;
            } else {
                frame.toolPanel.ToolEnable(true, new int[]{2});
            }
            
            startEndSwitch++;

            if (startEndSwitch == 2){
                frame.toolPanel.ToolEnable(false, switchSE);
                frame.toolPanel.customPanel.setTypeStartEnabled(false);
                frame.toolPanel.customPanel.setTypeEndEnabled(false);
            } else {
                if (c == 'S'){
                    frame.toolPanel.customPanel.setTypeStartEnabled(false);
                    frame.toolPanel.customPanel.setTypeEndEnabled(true);
                } else {
                    frame.toolPanel.customPanel.setTypeStartEnabled(true);
                    frame.toolPanel.customPanel.setTypeEndEnabled(false);    
                }
                frame.toolPanel.ToolEnable(false, new int[]{switchSE[0]});
                frame.toolPanel.ToolEnable(true, new int[]{switchSE[1]});
            }

            if (c == 'S'){
                if (startEndSwitch == 2){
                    frame.infoLabel.setText("Wybierz tryb rozwiązywania.");
                } else {
                    frame.infoLabel.setText("Wybór Startu zakończył się pomyślnie. Możesz teraz wybrać End (PickEnd) lub tryb rozwiązywania.");
                }
                

            } else {
                if (startEndSwitch == 2){
                    frame.infoLabel.setText("Wybierz tryb rozwiązywania.");
                } else {
                    frame.infoLabel.setText("Wybór Endu zakończył się pomyślnie. Możesz teraz wybrać Start (PickStart) lub tryb rozwiązywania.");
                }
            }


        } else {

            if (c == 'S'){
                maze.setCustomStart(oldCustomStart[0], oldCustomStart[1]); 
                frame.customError();
                if (maze.whoPickedCustom() == 0){
                    frame.toolPanel.ToolEnable(true, new int[]{4});
                } else {
                    frame.toolPanel.customPanel.setTypeStartEnabled(true);
                    frame.toolPanel.customPanel.typeStartOn = 0;
                }
                
            } else {
                maze.setCustomEnd(oldCustomEnd[0], oldCustomEnd[1]);
                frame.customError();
                if (maze.whoPickedCustom() == 0){
                    frame.toolPanel.ToolEnable(true, new int[]{5});
                } else {
                    frame.toolPanel.customPanel.setTypeEndEnabled(true);
                    frame.toolPanel.customPanel.typeEndOn = 0;
                }
                
            }
             
        }
    }

    static void InOutWall(){

        maze.setCharFromMaze(maze.getStart()[0], maze.getStart()[1], 'X');
        maze.setCharFromMaze(maze.getEnd()[0], maze.getEnd()[1], 'X');
        
        frame.contentPanel.mazePanel.rePaint(maze);
/* 
        if( startEndSwitch == 0){
            frame.toolPanel.ToolEnable(true, new int[]{2,3});
        }
*/
        frame.toolPanel.ToolEnable(false, new int[]{1});
        
    }

    private static void reset(){
        noStartEnd = 0;
        startEndInNoStartEnd = 0;
        frame.toolPanel.customPanel.changeStartPos(null,null);
        frame.toolPanel.customPanel.changeEndPos(null,null);
        frame.menuBar.setexportEnabled(false);
        frame.toolPanel.customPanel.reset();
        frame.toolPanel.customPanel.setTypeStartEnabled(true);
        frame.toolPanel.customPanel.setTypeEndEnabled(true);
        frame.toolPanel.resetComboBoX();
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
