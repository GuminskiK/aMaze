import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class MazePanel extends JPanel{
    
    Border border = BorderFactory.createLineBorder(Color.GRAY, 5);
    int path[];
    int columns;
    int rows;
    int mode = 1;

    MazePanel(int columns, int rows, int path[]){


        this.setPreferredSize(new Dimension(columns * 10 + 10, rows * 10 + 10));
        this.setBorder(border);
        this.setVisible(false);
        this.path = path;
        this.columns = columns;
        this.rows = rows;

    }

    public void rePaint(int mode, int[] path){
        this.path = path;
        this.repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (mode == 1){
            int columnMax = columns * 10;

            for (int i = 0; i < path.length; i++){
                //wall czy path
                if( path[i] == 0){
                    g.setColor(Color.BLACK);
                } else if (path[i] == 1){
                    g.setColor(Color.WHITE);
                } else if (path[i] == 2){
                    g.setColor(Color.RED);
                } else if (path[i] == 3){
                    g.setColor(Color.GREEN);
                }else {
                    g.setColor(Color.PINK);
                }

                g.fillRect( (i * 10) % columnMax + 5 , (i /columns) * 10 + 5, 10, 10);
            }
        } 
        // Tutaj możesz rysować na panelu
        
    }



}
