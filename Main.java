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

        ramka = new MyFrame(readListener, analyzeListener, shortestListener, helpListener, customStartListener, customEndListener);
        
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
        ramka.ToolPanel.ToolEnable(true, 0);

    }

    private static void Analyze(){
        
        mazeAnalyzer = new MazeAnalyzer();
        int wait = mazeAnalyzer.analyzeMaze(file, fileReader.columns, fileReader.rows);

        ramka.ToolPanel.ToolEnable(false, 0);
        ramka.ToolPanel.ToolEnable(true, 1);
        ramka.ToolPanel.ToolEnable(true, 2);
        ramka.ToolPanel.ToolEnable(true, 3);
    }

    private static void Shortest(){

        MazeSolver mazeSolver = new MazeSolver();
        int wait = mazeSolver.solveMaze(mazeAnalyzer.nodes, mazeAnalyzer.Start, mazeAnalyzer.End);

        SolutionWriter solutionWriter = new SolutionWriter();
        solutionWriter.WriteSolution(mazeSolver.save, mazeCreator.maze, mazeAnalyzer.Start, mazeAnalyzer.End ,mazeAnalyzer.nodes, fileReader.columns, mazeAnalyzer.StartPos, mazeAnalyzer.EndPos);
        ramka.menuBar.setloadEnabled(true);
    }

    private static void CheckIfCustomStart(){
    
        mazeCreator.maze.get( columns * ramka.ContentPanel.customStart[1] + ramka.ContentPanel.customStart[0]).setBackground(Color.GREEN);
        mazeCreator.maze.get( mazeAnalyzer.StartPos).setBackground(Color.BLACK);
        mazeAnalyzer.StartPos = columns * ramka.ContentPanel.customStart[1] + ramka.ContentPanel.customStart[0];

    }

    private static void CheckIfCustomEnd(){

        mazeCreator.maze.get( columns * ramka.ContentPanel.customEnd[1] + ramka.ContentPanel.customEnd[0]).setBackground(Color.PINK);
        mazeCreator.maze.get( mazeAnalyzer.EndPos).setBackground(Color.BLACK);
        mazeAnalyzer.EndPos = columns * ramka.ContentPanel.customEnd[1] + ramka.ContentPanel.customEnd[0];
    }
}
