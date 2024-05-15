package GUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Cells.MazeCell;
import Core.Maze;

public class MazePanel extends JPanel{
    
    private Border border = BorderFactory.createLineBorder(Color.GRAY, 5);
    private Maze maze;


    MazePanel(int columns, int rows, Maze maze){


        this.setPreferredSize(new Dimension(columns * 10 + 10, rows * 10 + 10));
        this.setBorder(border);
        this.setVisible(false);
        this.maze = maze;

    }

    public void rePaint(Maze maze){
        
        this.maze = maze;
        this.repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        MazeCell[][] mazeCells = maze.getMazeCells();
        
        for (int y = 0; y < maze.getRows(); y++){
            for (int x = 0; x < maze.getColumns(); x++){
                mazeCells[y][x].draw(g);
            }
        }
    }
}
