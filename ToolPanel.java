import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolPanel extends JPanel {

    JButton start;
    JButton end;
    JButton shortest;

    Font font = new Font( "Sans", Font.BOLD, 10);

    ToolPanel(){

        this.setBackground(Color.DARK_GRAY);
        this.setPreferredSize(new Dimension(100,50));
        //this.setLayout();

        start = new JButton();
        end = new JButton();
        shortest = new JButton();

        start.setText("Start");
        end.setText("End");
        shortest.setText("Shortest");

        start.setFont(font);
        end.setFont(font);
        shortest.setFont(font);

        start.setFocusable(false);
        end.setFocusable(false);
        shortest.setFocusable(false);

        start.setPreferredSize(new Dimension(80, 30));
        end.setPreferredSize(new Dimension(80, 30));
        shortest.setPreferredSize(new Dimension(80, 30));

        this.add(start);
        this.add(end);
        this.add(shortest);
    }
}
