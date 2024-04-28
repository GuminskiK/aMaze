import java.awt.Dimension;
import javax.swing.JLabel;

public abstract class Tile extends JLabel{

    int x;
    int y;

    Tile(){
        
        this.setPreferredSize(new Dimension(10,10));
        this.setOpaque(true);
        
    }
}
