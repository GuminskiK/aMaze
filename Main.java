import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JMenuItem;

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
        
        File file = ramka.menuBar.file;
        FileReader fileReader = new FileReader();
        fileReader.CountRowsColumns(file); 
        char[][] x = fileReader.ReadFileTXT(file);

        ramka.ContentPanel.addPanel(fileReader.columns, fileReader.rows);

        MazeCreator mazeCreator = new MazeCreator();
        int wait = mazeCreator.CreateMaze(ramka.ContentPanel.MazePanel, x, fileReader.columns, fileReader.rows);

    }
        
}
