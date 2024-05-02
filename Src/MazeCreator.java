import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MazeCreator {

    ArrayList<JLabel> maze;
    int [] path;
    public int CreateMaze(char[][] x, int columns, int rows){

        int z = 0;
        path = new int[columns * rows];
        for(int y = 0; y < rows; y ++){
            for (int i = 0; i < columns; i++) {

                if (x[y][i] == 'X') {
                    path[z] = 0;
                } else {
                    path[z] = 1;
                }
                z++;

            }
        }
        return 0;
    }
}
