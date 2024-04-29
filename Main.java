import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main {

    static MyFrame ramka;
    public static void main (String[] args){

        ActionListener listener = new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                Skryt();
            }

        };

        ramka = new MyFrame(listener);
        
    }

    private static void Skryt(){
        
        ramka.ToolPanel.EnableButton(false);
        File file = ramka.menuBar.file;
        FileReader fileReader = new FileReader();
        fileReader.CountRowsColumns(file); 
        char[][] x = fileReader.ReadFileTXT(file);

        ramka.ContentPanel.addPanel(fileReader.columns, fileReader.rows);

        MazeCreator mazeCreator = new MazeCreator();
        int wait = mazeCreator.CreateMaze(ramka.ContentPanel.MazePanel, x, fileReader.columns, fileReader.rows);

        MazeAnalyzer mazeAnalyzer = new MazeAnalyzer();
        wait = mazeAnalyzer.analyzeMaze(file, fileReader.columns, fileReader.rows);
        ramka.ToolPanel.EnableButton(true);


    }
        
}
