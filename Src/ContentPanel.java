
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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
        this.HelpPanel.setVisible(false);

        Help();

        this.add(HelpPanel);
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
        if ( components.length != 0 && on){
            this.remove(components[1]);
        }
        x++;

        this.mazePanel = new MazePanel(columns, rows, path);
        this.add(mazePanel);
        mazePanel.setVisible(true);
        on = true;

        
        
    }

    private void Help(){

        JLabel help = new JLabel();
        JButton backButton = new JButton("Back");
        JTextArea area = new JTextArea();

        this.HelpPanel.setPreferredSize(new Dimension(420, 650));
        help.setText("Help");
        help.setFont( new FontUIResource("Arial", Font.BOLD, 32));
        area.setPreferredSize(new Dimension(400,550));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setText("Witamy w pomocy dotyczącej korzystanie za aplikacji aMaze, która pomoże Ci rozwiązać labirynt. \n\n" + 
        "Program obsługuje labirynty zapisane w postaci pliku tekstowego, takiego że: \n - Ścieżka -> \" \" \n - Ściana -> X \n - Wejście -> P \n - Wyjście -> K \n" + 
        "a także pliku binarnego takiego, że: \n - Zawiera nagłówek pliku: \n  a) File Id w 32 bitach\n  b) Escape w 8 bitach\n  c) Liczbę kolumn labiryntu w 16 bitach" 
        + "\n d) Liczbę wierszy labiryntu w 16 bitach\n e) Współrzędne wejścia do labiryntu (X w 16 bitach i Y w 16 bitach) \n  f) Współrzędne wyjścia z labiryntu (X w 16 bitach i Y w 16 bitach)" +
        "\n g) 96 bitów zarezerwowanych do przyszłego wykorzystania \n h) Liczbę słów kodowych w 32 bitach \n i) Offset w pliku do sekcji zawierającej rozwiązanie w 32 bitach," +
        "\n k) Separator w 8 bitach \n l) Ściana w 8 bitach \n m) Path w 8 bitach" + "\n-Słowa kodowe będace odzwierciedleniem jak wygląda labirynt: \n  a)Separator - 8bitów \n b)Wartość słowa kodowego - 8bitów"+
        "\n  c)Liczba wystąpień (gdzie 0 oznacza jedno wystąpienie)- 8 bitów"
        +"\n Aby skorzystać z programu należy wybrac w pasku menu File -> load -> wybrać plik z labiryntem, po załadowaniu -> Analyze (z panelu bocznego). Następnie" + 
        " możemy albo ustawić nowy Start lub End za pomocą wybrania takiej opcji z panelu (najpierw trzeba je odblokować poprzez wybranie opcji custom), a następnie kliknięcia na labirynt w odpowiednio wybranym miejscu (musi być to na ścieżce). " + 
        "Po wybraniu lub nie przechodzimy do Shortest, jeśli chcemy znależć najkrótszą drogę lub Whole by przejść cały labirynt.");

        backButton.addActionListener(

            (e) -> {    Component[] components = this.getComponents();
                        if ( components.length > 1){
                            this.mazePanel.setVisible(true);
                        }
                        this.HelpPanel.setVisible(false);}

        );
        
        this.HelpPanel.add(help);
        this.HelpPanel.add(area);
        this.HelpPanel.add(backButton);
    }

    public void setHelpEnabled(){
        System.out.println("help");
        Component[] components = this.getComponents();
        if ( components.length > 1){
            this.mazePanel.setVisible(false);
        }
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
    }

}
