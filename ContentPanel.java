import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class ContentPanel extends JPanel {

    JPanel MazePanel;
    int x;

    ContentPanel(){

        this.setBackground(Color.green);
                
    }

    public void addPanel (int columns, int rows){

        Component[] components = this.getComponents();
        if ( components.length != 0){
            this.remove(components[0]);
        }
        x++;
        MazePanel = new JPanel();
        MazePanel.setSize(columns * 10, rows * 10);
        MazePanel.setLayout(new GridLayout(columns, rows, 0,0));
        MazePanel.setVisible(false);
        this.add(MazePanel);
        

    }

}
