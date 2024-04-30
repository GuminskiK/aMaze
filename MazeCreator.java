import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MazeCreator {

    ArrayList<JLabel> maze;
    public int CreateMaze(JPanel panel, char[][] x, int columns, int rows){

        maze = new ArrayList<JLabel>();
        int z = 0;
        for(int y = 0; y < rows; y ++){
            for (int i = 0; i < columns; i++) {

                if (x[y][i] == 'X') {
                    maze.add(new Wall());
                    panel.add(maze.get(z));
                } else {
                    maze.add(new Path());
                    panel.add(maze.get(z));
                }
                z++;

            }
        }

        panel.setVisible(true);
        return 0;
    }
}
