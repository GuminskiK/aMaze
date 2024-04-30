import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main {

    static MyFrame ramka;
    static MazeAnalyzer mazeAnalyzer;
    static MazeCreator mazeCreator;
    static FileReader fileReader;
    static File file;

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

        ActionListener helpListener = new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                help();
            }
        };

        ramka = new MyFrame(readListener, analyzeListener, helpListener);
        
    }

    private static void help(){
        ramka.ContentPanel.setHelpEnabled();
    }

    private static void Read(){
        
        ramka.menuBar.setloadEnabled(false);
        ramka.ToolPanel.EnableButton(false);
        file = ramka.menuBar.file;
        fileReader = new FileReader();
        fileReader.CountRowsColumns(file); 
        char[][] x = fileReader.ReadFileTXT(file);

        ramka.ContentPanel.addPanel(fileReader.columns, fileReader.rows);

        mazeCreator = new MazeCreator();
        int wait = mazeCreator.CreateMaze(ramka.ContentPanel.MazePanel, x, fileReader.columns, fileReader.rows);

        ramka.ToolPanel.ShortestEnable(true);
    }
    private static void Analyze(){

        mazeAnalyzer = new MazeAnalyzer();
        int wait = mazeAnalyzer.analyzeMaze(file, fileReader.columns, fileReader.rows);
        ramka.ToolPanel.EnableButton(true);

        Solve();
    }

    private static void Solve(){

        MazeSolver mazeSolver = new MazeSolver();
        int wait = mazeSolver.solveMaze(mazeAnalyzer.nodes, mazeAnalyzer.Start, mazeAnalyzer.End);

        SolutionWriter solutionWriter = new SolutionWriter();
        solutionWriter.WriteSolution(mazeSolver.save, mazeCreator.maze, mazeAnalyzer.Start, mazeAnalyzer.End ,mazeAnalyzer.nodes, fileReader.columns, mazeAnalyzer.StartDirection, mazeAnalyzer.EndDirection);
        ramka.menuBar.setloadEnabled(true);
    }
        
}
