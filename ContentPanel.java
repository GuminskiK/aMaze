import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ContentPanel extends JPanel {

    JPanel MazePanel;
    JPanel HelpPanel;
    int x;
    Border border = BorderFactory.createLineBorder(Color.black, 5);
    boolean on;

    ContentPanel(){

        this.setBackground(Color.white);

        HelpPanel = new JPanel();
        MazePanel = new JPanel();

        this.MazePanel.setVisible(false);
        this.HelpPanel.setVisible(false);

        Help();

        this.add(HelpPanel);
        this.add(MazePanel);
        on = false;
                
    }

    public void addPanel (int columns, int rows){

        HelpPanel.setVisible(false);
        Component[] components = this.getComponents();
        if ( components.length != 0){
            this.remove(components[1]);
        }
        x++;
        MazePanel = new JPanel();
        MazePanel.setSize(columns * 10, rows * 10);
        MazePanel.setLayout(new GridLayout(columns, rows, 0,0));
        MazePanel.setBorder(border);
        MazePanel.setVisible(false);
        this.add(MazePanel);
        on = true;
        
    }

    private void Help(){

        JLabel help = new JLabel();
        JButton backButton = new JButton("Back");

        help.setText("Tutaj znajdzie się help do tego programu");

        backButton.addActionListener(

            (e) -> {this.MazePanel.setVisible(on);
                    this.HelpPanel.setVisible(false);}

        );
        
        this.HelpPanel.add(help);
        this.HelpPanel.add(backButton);
    }

    public void help(){
        System.out.println("help");
        this.MazePanel.setVisible(false);
        this.HelpPanel.setVisible(true);
    }

}
