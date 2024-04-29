import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;

public class MazeCreator {

    public int CreateMaze(JPanel panel, char[][] x, int columns, int rows){

        for(int y = 0; y < rows; y ++){
            for (int i = 0; i < columns; i++) {

                if (x[y][i] == 'X') {
                    panel.add(new Wall());
                } else {
                    panel.add(new Path());
                }
            }
        }
        panel.setVisible(true);
        return 0;
    }
}
