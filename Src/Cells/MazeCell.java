package Cells;
import java.awt.Color;
import java.awt.Graphics;

public abstract class MazeCell {

    Color color;
    int x;
    int y;

    public void draw(Graphics g){
        
        g.setColor(color);
        g.fillRect(x, y, 10, 10);

    }

}   
