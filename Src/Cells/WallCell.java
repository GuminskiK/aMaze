package Cells;
import java.awt.Color;
public class WallCell  extends MazeCell{

    public WallCell(int x, int y){

        this.color = Color.BLACK;
        this.x = x;
        this.y = y;
    }
}
