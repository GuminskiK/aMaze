import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main {

    static MyFrame ramka;
    static MazeAnalyzer mazeAnalyzer;
    static MazeCreator mazeCreator;
    static FileReader fileReader;
    static File file;
    static int columns;
    static Color lastStartColor = new Color (0,0,0);
    static Color lastEndColor = new Color (0,0,0);
    static int[] oldCustomStart = new int[2];
    static int[] oldCustomEnd = new int[2];
    static int StartEndSwitch = 0;
    static int[] path;
    static char[][] x;
    

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
        
    }

    private static void help(){
        ramka.ContentPanel.setHelpEnabled();
    }

    private static void Read(){
        
        ramka.menuBar.setloadEnabled(false);
        file = ramka.menuBar.file;
        fileReader = new FileReader();

        //char [][]x =  new char[columns][fileReader.rows];
        //to nie działa do końca bo skad wezmę wymiary za nim przeczytam
        
        if (ramka.menuBar.fileType.equals("txt")){
            
            fileReader.CountRowsColumns(file);
            columns = fileReader.columns;
            x = fileReader.ReadFileTXT(file);

        } else {
            //czytanie binarne
            //inczaej wywali się program, pozdrawiam
        }

        //createMaze
        mazeCreator = new MazeCreator();

        int wait = mazeCreator.CreateMaze( x, fileReader.columns, fileReader.rows);
        path = mazeCreator.path;
        
        //rysowanie
        ramka.ContentPanel.addPanel(fileReader.columns, fileReader.rows, path);
        

        ramka.ToolPanel.ToolEnable(true, 0); //Analyze

        StartEndSwitch = 0;

    }

    private static void Analyze(){
        
        ramka.ToolPanel.ToolEnable(false, 0);

        mazeAnalyzer = new MazeAnalyzer();
        int wait = mazeAnalyzer.analyzeMaze(file, fileReader.columns, fileReader.rows);

        oldCustomStart[0] = mazeAnalyzer.StartPos/columns;
        oldCustomStart[1] = mazeAnalyzer.StartPos%columns;
        oldCustomEnd[0] = mazeAnalyzer.EndPos/columns;
        oldCustomEnd[1] = mazeAnalyzer.EndPos%columns;

        ramka.ToolPanel.ToolEnable(true, 1);
        ramka.ToolPanel.ToolEnable(true, 4);
        ramka.ToolPanel.ToolEnable(true, 5);
    }

    private static void Shortest(){

        ramka.ToolPanel.ToolEnable(false, 1);
        ramka.ToolPanel.ToolEnable(false, 2);
        ramka.ToolPanel.ToolEnable(false, 3);
        ramka.ToolPanel.ToolEnable(false, 4);
        ramka.ToolPanel.ToolEnable(false, 5);

        MazeSolver mazeSolver = new MazeSolver();
        int wait = mazeSolver.solveMaze(mazeAnalyzer.nodes, mazeAnalyzer.Start, mazeAnalyzer.End, 0);

        SolutionWriter solutionWriter = new SolutionWriter();
        solutionWriter.WriteSolution(mazeSolver.save, path, mazeAnalyzer.Start, mazeAnalyzer.End ,mazeAnalyzer.nodes, fileReader.columns, mazeAnalyzer.StartPos, mazeAnalyzer.EndPos);
        ramka.ContentPanel.mazePanel.rePaint(1, path);
        ramka.menuBar.setloadEnabled(true);
    }

    private static void Whole(){

        ramka.ToolPanel.ToolEnable(false, 1);
        ramka.ToolPanel.ToolEnable(false, 2);
        ramka.ToolPanel.ToolEnable(false, 3);
        ramka.ToolPanel.ToolEnable(false, 4);
        ramka.ToolPanel.ToolEnable(false, 5);

        //tylko dla wersji podstawowej XD
        /*
        for (int i = 0; i < columns * fileReader.rows ; i++ ){
            if(mazeCreator.path[i] == 1){
                mazeCreator.maze.get(i).setBackground(Color.RED);
            }
        }
        panels.get(mazeAnalyzer.StartPos).setBackground(Color.GREEN);
        panels.get(mazeAnalyzer.EndPos).setBackground(Color.PINK);
        */
        SolutionWriterWhole solutionWriterWhole = new SolutionWriterWhole();
        solutionWriterWhole.solveMaze(mazeAnalyzer.nodes, mazeAnalyzer.Start, mazeAnalyzer.End, 1, path, columns, mazeAnalyzer.StartPos, mazeAnalyzer.EndPos);
        ramka.ContentPanel.mazePanel.rePaint(1, path);
        ramka.menuBar.setloadEnabled(true);

    }

    private static void customChange( char c){

        int colorNr;
        int[] switchSE;
        int[] customObject = new int[2];

        if (c == 'S'){
            customObject[0] = ramka.ContentPanel.customStart[0];
            customObject[1] = ramka.ContentPanel.customStart[1];
        } else {
            customObject[0] = ramka.ContentPanel.customEnd[0];
            customObject[1] = ramka.ContentPanel.customEnd[1];
        }

        if (mazeCreator.path[columns * customObject[1] + customObject[0]] == 1){
            
            if (c == 'S'){

                colorNr = 3;
                switchSE = new int[]{2,3};

            } else {

                colorNr = 4;
                switchSE = new int[]{3,2};

            }

            ramka.ToolPanel.ToolEnable(false, 4);
            ramka.ToolPanel.ToolEnable(false, 5);

            mazeAnalyzer.customAnalyzer( path, customObject , columns, c);

            path[columns * customObject[1] + customObject[0]] = colorNr;
            ramka.ContentPanel.mazePanel.rePaint(1, path);

            if (c == 'S'){
                mazeAnalyzer.StartPos = columns * customObject[1] + customObject[0];
            } else {
                mazeAnalyzer.EndPos = columns * customObject[1] + customObject[0];
            }
            
            ramka.ToolPanel.ToolEnable(true, 4);
            ramka.ToolPanel.ToolEnable(true, 5);

            StartEndSwitch++;

            if (StartEndSwitch == 2){
                ramka.ToolPanel.ToolEnable(false, switchSE[0]);
                ramka.ToolPanel.ToolEnable(false, switchSE[1]);
            } else {
                ramka.ToolPanel.ToolEnable(false, switchSE[0]);
                ramka.ToolPanel.ToolEnable(true, switchSE[1]);
            }
            
        } else {

            if (c == 'S'){
                ramka.ContentPanel.customStart[0] = oldCustomStart[0];
                ramka.ContentPanel.customStart[1] = oldCustomStart[1]; 
                ramka.customError();
            } else {
                ramka.ContentPanel.customEnd[0] = oldCustomEnd[0];
                ramka.ContentPanel.customEnd[1] = oldCustomEnd[1];
                ramka.customError();
            }
             
        }
    }

    static void InOutWall(){

        path[mazeAnalyzer.StartPos] = 0;
        path[mazeAnalyzer.EndPos] = 0;
        
        ramka.ContentPanel.mazePanel.rePaint(1, path);

        if( StartEndSwitch == 0){
            ramka.ToolPanel.ToolEnable(true, 2);
            ramka.ToolPanel.ToolEnable(true, 3);
        }

        ramka.ToolPanel.ToolEnable(false, 1);
        

    }
}
