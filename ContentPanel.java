
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ContentPanel extends JPanel {

    public JPanel MazePanel;
    JPanel HelpPanel;
    int x;
    Border border = BorderFactory.createLineBorder(Color.GRAY, 5);
    boolean on;

    int[] customStart;
    int[] customEnd;

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

        this.customStart = new int[2];
        this.customStart[0] = -1;
        this.customStart[1] = -1;
        this.customEnd = new int[2];
        this.customEnd[0] = -1;
        this.customEnd[1] = -1;

    }

    public void addPanel (int columns, int rows){

        HelpPanel.setVisible(false);
        Component[] components = this.getComponents();
        if ( components.length != 0){
            this.remove(components[1]);
        }
        x++;
        this.MazePanel = new JPanel();
        this.MazePanel.setSize(columns * 10, rows * 10);
        this.MazePanel.setLayout(new GridLayout(columns, rows, 0,0));
        this.MazePanel.setBorder(border);
        this.MazePanel.setVisible(false);

        this.add(MazePanel);
        on = true;

        
        
    }

    private void Help(){

        JLabel help = new JLabel();
        JButton backButton = new JButton("Back");

        help.setText("Tutaj znajdzie się help do tego programu");

        backButton.addActionListener(

            (e) -> {this.MazePanel.setVisible(true);
                    this.HelpPanel.setVisible(false);}

        );
        
        this.HelpPanel.add(help);
        this.HelpPanel.add(backButton);
    }

    public void setHelpEnabled(){
        System.out.println("help");
        this.MazePanel.setVisible(false);
        this.HelpPanel.setVisible(true);
    }

    public void start(ActionListener listener, char c, ContentPanel contentPanel, ActionListener customListener){

        MazePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                if ( c == 'S'){
                    contentPanel.customStart[0] = (x-5)/10;
                    contentPanel.customStart[1] = (y-5)/10;
                    saveCustom(customStart, 'S');
                } else {
                    contentPanel.customEnd[0] = (x-5)/10;
                    contentPanel.customEnd[1] = (y-5)/10;
                    saveCustom(customEnd, 'E');
                }
                MazePanel.removeMouseListener(MazePanel.getMouseListeners()[0]);
                listener.actionPerformed(null);
                customListener.actionPerformed(null);
            }
        });
        
    }

    public void saveCustom (int[] s, char c){

        if (c == 'S'){
            this.customStart[0] = s[0];
            this.customStart[1] = s[1];
        } else {
            this.customEnd[0] = s[0];
            this.customEnd[1] = s[1];
        }
        //System.out.println(this.customStart[0] + " " + this.customStart[1]);
        //System.out.println(this.customEnd[0] + " " + this.customEnd[1]);
    }

}
