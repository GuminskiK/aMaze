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
                CheckIfCustomStart();
            }
        };

        ActionListener customEndListener = new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                CheckIfCustomEnd();
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
        fileReader.CountRowsColumns(file); 
        char[][] x = fileReader.ReadFileTXT(file);
        columns = fileReader.columns;
        ramka.ContentPanel.addPanel(fileReader.columns, fileReader.rows);

        mazeCreator = new MazeCreator();
        int wait = mazeCreator.CreateMaze(ramka.ContentPanel.MazePanel, x, fileReader.columns, fileReader.rows);

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
        solutionWriter.WriteSolution(mazeSolver.save, mazeCreator.maze, mazeAnalyzer.Start, mazeAnalyzer.End ,mazeAnalyzer.nodes, fileReader.columns, mazeAnalyzer.StartPos, mazeAnalyzer.EndPos);
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
        solutionWriterWhole.solveMaze(mazeAnalyzer.nodes, mazeAnalyzer.Start, mazeAnalyzer.End, 1, mazeCreator.maze, columns, mazeAnalyzer.StartPos, mazeAnalyzer.EndPos);

        ramka.menuBar.setloadEnabled(true);

    }

    private static void CheckIfCustomStart(){
        
        if (mazeCreator.path[columns * ramka.ContentPanel.customStart[1] + ramka.ContentPanel.customStart[0]] == 1){
            //System.out.println("path");
            ramka.ToolPanel.ToolEnable(false, 3);
            ramka.ToolPanel.ToolEnable(false, 4);

            oldCustomStart[0] = ramka.ContentPanel.customStart[0];
            oldCustomStart[1] = ramka.ContentPanel.customStart[1]; 

            //do testu
            mazeAnalyzer.customAnalyzer( mazeCreator.path, ramka.ContentPanel.customStart , columns, 'S');

            //mazeCreator.maze.get( mazeAnalyzer.StartPos).setBackground(lastStartColor);
            //lastStartColor = mazeCreator.maze.get( columns * ramka.ContentPanel.customStart[1] + ramka.ContentPanel.customStart[0]).getBackground();
       
            mazeCreator.maze.get( columns * ramka.ContentPanel.customStart[1] + ramka.ContentPanel.customStart[0]).setBackground(Color.GREEN);
            mazeAnalyzer.StartPos = columns * ramka.ContentPanel.customStart[1] + ramka.ContentPanel.customStart[0];

            StartEndSwitch++;
    
            ramka.ToolPanel.ToolEnable(true, 3);
            ramka.ToolPanel.ToolEnable(true, 4);
            ramka.ToolPanel.ToolEnable(true, 5);

            if (StartEndSwitch == 2){
                ramka.ToolPanel.ToolEnable(false, 2);
                ramka.ToolPanel.ToolEnable(false, 3);
            } else {
                ramka.ToolPanel.ToolEnable(false, 2);
                ramka.ToolPanel.ToolEnable(true, 3);
            }

        } else {
            ramka.ContentPanel.customStart[0] = oldCustomStart[0];
            ramka.ContentPanel.customStart[1] = oldCustomStart[1]; 
            ramka.customError();
        }

    }

    private static void CheckIfCustomEnd(){
        
        if (mazeCreator.path[columns * ramka.ContentPanel.customEnd[1] + ramka.ContentPanel.customEnd[0]] == 1){
            //System.out.println("path");
            ramka.ToolPanel.ToolEnable(false, 3);
            ramka.ToolPanel.ToolEnable(false, 4);

            oldCustomEnd[0] = ramka.ContentPanel.customEnd[0];
            oldCustomEnd[1] = ramka.ContentPanel.customEnd[1]; 

            mazeAnalyzer.customAnalyzer( mazeCreator.path, ramka.ContentPanel.customEnd , columns, 'E');

            //mazeCreator.maze.get( mazeAnalyzer.EndPos).setBackground(lastEndColor);
            //lastEndColor = mazeCreator.maze.get( columns * ramka.ContentPanel.customEnd[1] + ramka.ContentPanel.customEnd[0]).getBackground();

            mazeCreator.maze.get( columns * ramka.ContentPanel.customEnd[1] + ramka.ContentPanel.customEnd[0]).setBackground(Color.PINK);
            mazeAnalyzer.EndPos = columns * ramka.ContentPanel.customEnd[1] + ramka.ContentPanel.customEnd[0];

            ramka.ToolPanel.ToolEnable(true, 3);
            ramka.ToolPanel.ToolEnable(true, 4);
            ramka.ToolPanel.ToolEnable(true, 5);

            StartEndSwitch++;

            if (StartEndSwitch == 2){
                ramka.ToolPanel.ToolEnable(false, 2);
                ramka.ToolPanel.ToolEnable(false, 3);
            } else {
                ramka.ToolPanel.ToolEnable(false, 2);
                ramka.ToolPanel.ToolEnable(true, 3);
            }

        } else {
            ramka.ContentPanel.customEnd[0] = oldCustomEnd[0];
            ramka.ContentPanel.customEnd[1] = oldCustomEnd[1];
            ramka.customError(); 
        }

    }

    static void InOutWall(){

        mazeCreator.maze.get(mazeAnalyzer.StartPos).setBackground(Color.BLACK);
        mazeCreator.maze.get(mazeAnalyzer.EndPos).setBackground(Color.BLACK);

        System.out.println(mazeAnalyzer.Start);
        System.out.println(mazeAnalyzer.End);

        mazeCreator.path[mazeAnalyzer.StartPos] = 0;
        mazeCreator.path[mazeAnalyzer.EndPos] = 0;

        if( StartEndSwitch == 0){
            ramka.ToolPanel.ToolEnable(true, 2);
            ramka.ToolPanel.ToolEnable(true, 3);
        }

        ramka.ToolPanel.ToolEnable(false, 1);
        

    }
}
