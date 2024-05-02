
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.plaf.FontUIResource;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ContentPanel extends JPanel {

    //public JPanel MazePanel;
    public MazePanel mazePanel;
    JPanel HelpPanel;
    int x;
    Border border = BorderFactory.createLineBorder(Color.GRAY, 5);
    boolean on;

    int[] customStart;
    int[] customEnd;

    ContentPanel(){

        this.setBackground(Color.white);

        HelpPanel = new JPanel();
        //MazePanel = new JPanel();

        //this.MazePanel.setVisible(false);
        this.HelpPanel.setVisible(false);

        Help();

        this.add(HelpPanel);
        //this.add(MazePanel);
        on = false;

        this.customStart = new int[2];
        this.customStart[0] = -1;
        this.customStart[1] = -1;
        this.customEnd = new int[2];
        this.customEnd[0] = -1;
        this.customEnd[1] = -1;

    }

    public void addPanel (int columns, int rows, int[] path){

        HelpPanel.setVisible(false);
        Component[] components = this.getComponents();
        if ( components.length != 0){
            //this.remove(components[1]);
        }
        x++;
        this.mazePanel = new MazePanel(columns, rows, path);
        //mazePanel.rePaint(2);
        this.add(mazePanel);
        mazePanel.setVisible(true);
        on = true;

        
        
    }

    private void Help(){

        JLabel help = new JLabel();
        JButton backButton = new JButton("Back");
        JTextArea area = new JTextArea();

        this.HelpPanel.setPreferredSize(new Dimension(420, 500));
        help.setText("Help");
        help.setFont( new FontUIResource("Arial", Font.BOLD, 32));
        area.setPreferredSize(new Dimension(400,400));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setText("Witamy w pomocy dotyczącej korzystanie za aplikacji aMaze, która pomoże Ci rozwiązać Twój labirynt. \n\n" + 
        "Program obsługuje labirynty zapisane w postaci pliku binarnego lub tekstowego, takiego że: \n - Ścieżka -> \" \" \n - Ściana -> X \n - Wejście -> P \n - Wyjście -> K \n" + 
        "\n Aby skorzystać z programu należy wybrac w pasku menu File -> load -> wybrać plik z labiryntem, po załadowaniu -> Analyze (z panelu bocznego). Następnie" + 
        " możemy albo ustawić nowy Start lub End za pomocą wybrania takiej opcji z panelu, a następnie kliknięcia na labirynt w odpoiednio wybranym miejscu (musi być to na ścieżce). " + 
        "Po wybraniu lub nie przechodzimy do Shortest, jeśli chcemy znależć najkrótszą drogę lub Whole by przejść cały labirynt.");

        backButton.addActionListener(

            (e) -> {//this.MazePanel.setVisible(true);
                    this.HelpPanel.setVisible(false);}

        );
        
        this.HelpPanel.add(help);
        this.HelpPanel.add(area);
        this.HelpPanel.add(backButton);
    }

    public void setHelpEnabled(){
        System.out.println("help");
        //this.MazePanel.setVisible(false);
        this.HelpPanel.setVisible(true);
    }

    public void start(ActionListener listener, char c, ContentPanel contentPanel, ActionListener customListener){

        mazePanel.addMouseListener(new MouseAdapter() {
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
                mazePanel.removeMouseListener(mazePanel.getMouseListeners()[0]);
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
