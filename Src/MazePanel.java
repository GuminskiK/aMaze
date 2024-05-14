import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class MazePanel extends JPanel{
    
    Border border = BorderFactory.createLineBorder(Color.GRAY, 5);
    Maze maze;
    int columns;
    int rows;
    int mode = 1;

    MazePanel(int columns, int rows, Maze maze){


        this.setPreferredSize(new Dimension(columns * 10 + 10, rows * 10 + 10));
        this.setBorder(border);
        this.setVisible(false);
        this.maze = maze;
        this.columns = columns;
        this.rows = rows;

    }

    public void rePaint(Maze maze){
        
        this.maze = maze;
        this.repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int maxWidthInPixels = columns * 10;

        for (int y = 0; y < maze.getRows(); y++){
            for (int x = 0; x < maze.getColumns(); x++){

                switch(maze.getCharFromMaze(x, y)){
                    case 'X':
                        g.setColor(Color.BLACK);
                        break;
                    case ' ':
                        g.setColor(Color.WHITE);
                        break;
                    case 'R':
                        g.setColor(Color.RED);
                        break;
                    case 'P':
                        g.setColor(Color.GREEN);
                        break;
                    case 'K':
                        g.setColor(Color.PINK);
                        break;
                    default:
                        break;
                }

                g.fillRect( (x % maxWidthInPixels) * 10 + 5 , y * 10 + 5, 10, 10);
            }
        }
    }
}
