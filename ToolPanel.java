import java.awt.Color;
import java.awt.Dimension;

import java.awt.Font;


import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolPanel extends JPanel{

    JButton start;
    JButton end;
    JButton shortest;

    Font font = new Font( "Sans", Font.BOLD, 10);

    ToolPanel(){

        this.setBackground(Color.DARK_GRAY);
        this.setPreferredSize(new Dimension(100,50));
        //this.setLayout();

        ToolButton start = new ToolButton("Start", font);
        ToolButton end = new ToolButton("End", font);
        ToolButton shortest = new ToolButton("Shortest", font);

        this.add(start);
        this.add(end);
        this.add(shortest);

    }

}
