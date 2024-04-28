import java.io.File;

public class Main {

    public static void main (String[] args){

        MyFrame ramka = new MyFrame();

        File file;
        do{
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            file = ramka.menuBar.file;
        } while ( ramka.menuBar.done == 0);

        FileReader fileReader = new FileReader();
        fileReader.CountRowsColumns(file); 
        char[][] x = fileReader.ReadFileTXT(file);

        ramka.ContentPanel.addPanel(fileReader.columns, fileReader.rows);

        MazeCreator mazeCreator = new MazeCreator();
        int wait = mazeCreator.CreateMaze(ramka.ContentPanel.MazePanel, x, fileReader.columns, fileReader.rows);
        //ramka.ContentPanel.MazePanel.repaint();







    }
        
}
