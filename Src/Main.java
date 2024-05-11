import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main {

    static MyFrame ramka;
    static MazeAnalyzer mazeAnalyzer;
    static FileReader fileReader;
    static Maze maze;
    static File file;
    static int columns;
    static Color lastStartColor = new Color (0,0,0);
    static Color lastEndColor = new Color (0,0,0);
    static Integer[] oldCustomStart = new Integer[2];
    static Integer[] oldCustomEnd = new Integer[2];
    static int StartEndSwitch = 0;
    static char[][] x;
    static int NoStartEnd = 0;
    

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

        ramka = new MyFrame(readListener, analyzeListener, shortestListener, helpListener, customStartListener, customEndListener, wholeListener, customListener);
        ramka.menuBar.setexportEnabled(false);
        ramka.infoLabel.setText("Proszę załadować labirynt Files->Load Maze.");
        
        
    }

    private static void help(){
        ramka.ContentPanel.setHelpEnabled();
    }

    private static void Read(){

        reset();
        ramka.infoLabel.setText("Ładowanie labiryntu...");
        ramka.menuBar.setloadEnabled(false);
        ramka.ToolPanel.ToolEnable(false, new int[]{0,1,2,3,4,5});

        file = ramka.menuBar.file;
        fileReader = new FileReader();
        
        if (ramka.menuBar.fileType.equals("txt")){
            
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
        
        maze = new Maze(fileReader.columns, fileReader.rows, x);
        
        //rysowanie
        ramka.ContentPanel.addPanel(fileReader.columns, fileReader.rows, maze);

        ramka.ToolPanel.ToolEnable(true, new int[]{0}); //Analyze

        StartEndSwitch = 0;

        ramka.infoLabel.setText("Proszę nacisnąć Analyze maze by przeanalizować labirynt.");

    }

    private static void Analyze(){
        
        ramka.infoLabel.setText("Analizowanie w toku...");
        ramka.ToolPanel.ToolEnable(false, new int[]{0});

        mazeAnalyzer = new MazeAnalyzer();
        mazeAnalyzer.analyzeMaze(file, ramka.menuBar.fileType, ramka.ToolPanel.customPanel, maze);

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


        ramka.ToolPanel.ToolEnable(true, new int[]{1,2});

        NoStartEnd = 0;

        if (ramka.ToolPanel.customPanel.ifNull()[0] == true || ramka.ToolPanel.customPanel.ifNull()[1] == true){

            if (ramka.ToolPanel.customPanel.ifNull()[0] == true && ramka.ToolPanel.customPanel.ifNull()[1] == true){
                ramka.infoLabel.setText("Proszę wybrać Start i End.");
                ramka.ToolPanel.ToolEnable(true, new int[]{3,4,5});
                ramka.ToolPanel.ToolEnable(false, new int[]{1,2});
                NoStartEnd += 2;
            } else if (ramka.ToolPanel.customPanel.ifNull()[0] == true){
                ramka.infoLabel.setText("Proszę wybrać Start.");
                ramka.ToolPanel.ToolEnable(true, new int[]{3,4});
                ramka.ToolPanel.ToolEnable(false, new int[]{1,2});

                NoStartEnd++;
            }
            else if (ramka.ToolPanel.customPanel.ifNull()[1] == true){
                ramka.infoLabel.setText("Proszę wybrać End.");
                ramka.ToolPanel.ToolEnable(true, new int[]{3,5});
                NoStartEnd++;
            }

        } else {
            ramka.infoLabel.setText("Można wybrać tryb rozwiązywania labiryntu, a także nowy Start/End");
        }
        

    }

    private static void Shortest(){

        ramka.ToolPanel.ToolEnable(false, new int[]{0,1,2,3,4});
        ramka.infoLabel.setText("Szukam najkrótszego rozwiązania labiryntu...");
        MazeSolver mazeSolver = new MazeSolver();
        mazeSolver.solveMaze(mazeAnalyzer.nodes, mazeAnalyzer.Start, mazeAnalyzer.End);

        SolutionWriter solutionWriter = new SolutionWriter();
        solutionWriter.WriteSolution(mazeSolver.save, maze, mazeAnalyzer.Start, mazeAnalyzer.End ,mazeAnalyzer.nodes);
        ramka.ContentPanel.mazePanel.rePaint(maze);
        ramka.infoLabel.setText("<html>Znaleziono najkrótsze rozwiązanie labiryntu, by załadować inny labirynt wybierz Files->Load Maze, by wyeskportować rozwiązanie wybierz Files-> Export Solution.</html>");
        ramka.menuBar.setloadEnabled(true);
        ramka.menuBar.setexportEnabled(true);
    }

    private static void Whole(){

        ramka.ToolPanel.ToolEnable(false, new int[]{0,1,2,3,4});

        SolutionWriterWhole solutionWriterWhole = new SolutionWriterWhole();
        solutionWriterWhole.solveMaze(mazeAnalyzer.nodes, mazeAnalyzer.Start, mazeAnalyzer.End, maze);
        ramka.ContentPanel.mazePanel.rePaint(maze);
        ramka.infoLabel.setText("<html> Znaleziono rozwiązanie labiryntu, by załadować inny labirynt wybierz Files->Load Maze, by wyeskportować rozwiązanie wybierz Files-> Export Solution. </html>");
        ramka.menuBar.setloadEnabled(true);
        ramka.menuBar.setexportEnabled(true);

    }

    private static void customChange( char c){

        char colorNr;
        int[] switchSE;
        int[] customObject = new int[2];

        if (c == 'S'){
            customObject[0] = ramka.ContentPanel.customStart[0];
            customObject[1] = ramka.ContentPanel.customStart[1];
        } else {
            customObject[0] = ramka.ContentPanel.customEnd[0];
            customObject[1] = ramka.ContentPanel.customEnd[1];
        }

        if (maze.getCharFromMaze(customObject[0], customObject[1]) == ' '){
            
            if (c == 'S'){

                colorNr = 'P';
                switchSE = new int[]{4,5};
                ramka.ToolPanel.customPanel.changeStartPos(customObject[0], customObject[1]);
                ramka.infoLabel.setText("Wybrano nowy Start. Trwa jego lokalizowanie...");

            } else {

                colorNr = 'K';
                switchSE = new int[]{5,4};
                ramka.ToolPanel.customPanel.changeEndPos(customObject[0], customObject[1]);
                ramka.infoLabel.setText("Wybrano nowy End. Trwa jego lokalizowanie...");

            }

            ramka.ToolPanel.ToolEnable(false, new int[] {2});

            mazeAnalyzer.customAnalyzer(customObject, c);

            maze.setCharFromMaze(customObject[0], customObject[1], colorNr);
            ramka.ContentPanel.mazePanel.rePaint(maze);

            if (c == 'S'){
                maze.setStart(customObject[0], customObject[1]);
            } else {
                maze.setEnd(customObject[0], customObject[1]);
            }

            if (NoStartEnd == 2){
                NoStartEnd--;
            } else {
                ramka.ToolPanel.ToolEnable(true, new int[]{2});
            }
            
            StartEndSwitch++;

            if (StartEndSwitch == 2){
                ramka.ToolPanel.ToolEnable(false, switchSE);
            } else {
                ramka.ToolPanel.ToolEnable(false, new int[]{switchSE[0]});
                ramka.ToolPanel.ToolEnable(true, new int[]{switchSE[1]});
            }

            if (c == 'S'){
                if (StartEndSwitch == 2){
                    ramka.infoLabel.setText("Wybierz tryb rozwiązywania.");
                } else {
                    ramka.infoLabel.setText("Wybór Startu zakończył się pomyślnie. Możesz teraz wybrać End (PickEnd) lub tryb rozwiązywania.");
                }
                

            } else {
                if (StartEndSwitch == 2){
                    ramka.infoLabel.setText("Wybierz tryb rozwiązywania.");
                } else {
                    ramka.infoLabel.setText("Wybór Endu zakończył się pomyślnie. Możesz teraz wybrać Start (PickStart) lub tryb rozwiązywania.");
                }
            }


        } else {

            if (c == 'S'){
                ramka.ContentPanel.customStart[0] = oldCustomStart[0];
                ramka.ContentPanel.customStart[1] = oldCustomStart[1];
                ramka.customError();
                ramka.ToolPanel.ToolEnable(true, new int[]{4});
            } else {
                ramka.ContentPanel.customEnd[0] = oldCustomEnd[0];
                ramka.ContentPanel.customEnd[1] = oldCustomEnd[1];
                ramka.customError();
                ramka.ToolPanel.ToolEnable(true, new int[]{5});
            }
             
        }
    }

    static void InOutWall(){

        maze.setCharFromMaze(maze.getStart()[0], maze.getStart()[1], 'X');
        maze.setCharFromMaze(maze.getEnd()[0], maze.getEnd()[1], 'X');
        
        ramka.ContentPanel.mazePanel.rePaint(maze);
/* 
        if( StartEndSwitch == 0){
            ramka.ToolPanel.ToolEnable(true, new int[]{2,3});
        }
*/
        ramka.ToolPanel.ToolEnable(false, new int[]{1});
        
    }

    private static void reset(){
        NoStartEnd = 0;
        ramka.ToolPanel.customPanel.changeStartPos(null,null);
        ramka.ToolPanel.customPanel.changeEndPos(null,null);
        ramka.menuBar.setexportEnabled(false);
    }
}
