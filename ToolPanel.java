import java.awt.Color;
import java.awt.Dimension;

import java.awt.Font;


import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolPanel extends JPanel{

    JButton start = new JButton();;
    JButton end = new JButton();;
    JButton shortest = new JButton();;

    Font font = new Font( "Dialog", Font.BOLD, 10);

    ToolPanel(){

        this.setBackground(Color.DARK_GRAY);
        this.setPreferredSize(new Dimension(100,50));
        //this.setLayout();

        CreateToolButton( start, "Start", font);
        CreateToolButton( end, "End", font);
        CreateToolButton( shortest, "Shortest", font);

        start.addActionListener(

            (e) -> System.out.println("Start")

        );

        end.addActionListener(

            (e) -> System.out.println("End")

        );
    
        shortest.addActionListener(
    
            (e) -> System.out.println("Shortest")

        );

        this.add(start);
        this.add(end);
        this.add(shortest);

    }

    private void CreateToolButton( JButton button ,String txt, Font font){

        button.setText(txt);
        button.setFont(font);
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(80, 30));
        button.setEnabled(false);
    }

    public void EnableButton(boolean x){

        start.setEnabled(x);
        end.setEnabled(x);
        shortest.setEnabled(x);
    }

}
