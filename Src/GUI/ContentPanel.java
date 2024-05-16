package GUI;

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

import Core.Maze;
import Core.Watched;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ContentPanel extends JPanel {

    private MazePanel mazePanel;
    private JPanel helpPanel;
    private int x;
    private Border border = BorderFactory.createLineBorder(Color.GRAY, 5);
    private boolean on;
    private Maze maze;

    ContentPanel(Maze maze){

        this.maze = maze;
        this.setBackground(Color.white);

        helpPanel = new JPanel();
        this.helpPanel.setVisible(false);

        help();

        this.add(helpPanel);
        on = false;

    }

    public void addPanel (int columns, int rows){

        helpPanel.setVisible(false);
        Component[] components = this.getComponents();
        if ( components.length != 0 && on){
            this.remove(components[1]);
        }
        x++;

        this.mazePanel = new MazePanel(columns, rows, maze);
        this.add(mazePanel);
        mazePanel.setVisible(true);
        on = true;

        
        
    }

    private void help(){

        JLabel help = new JLabel();
        JButton backButton = new JButton("Back");
        JTextArea area = new JTextArea();

        this.helpPanel.setPreferredSize(new Dimension(420, 650));
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
        +"\n Aby skorzystać z programu należy wybrac w pasku menu File -> Load Maze -> wybrać plik z labiryntem, po załadowaniu -> Analyze Maze (z panelu bocznego). Następnie" + 
        " możemy albo ustawić nowy Start lub End za pomocą wybrania takiej opcji z panelu (najpierw trzeba je odblokować poprzez wybranie opcji custom), a następnie kliknięcia na labirynt w odpowiednio wybranym miejscu (musi być to na ścieżce). " + 
        "Po wybraniu lub nie przechodzimy do Shortest, jeśli chcemy znależć najkrótszą drogę lub Whole by przejść cały labirynt.");

        area.setText(" Welcome to the aMaze app help. This app will help you solve your maze! \n" + 
        "The program supprts mazes in .txt and .bin files in which: \n" + 
        "A) .txt files:\n" + 
        "   1. Path is presented by space \n" +
        "   2. Wall is presented by \"X\" \n" +
        "   3. Start is presented by \"P\" \n" +
        "   4. End is presented by \"K\" \n" +
        "B) .bin files:\n" + 
        "   1.Contains the header of the file, in which: \n" +
        "       a) File Id is in 32 bits. \n" +
        "       b) Escape is in 8 bits. \n" +
        "       c) The number of maze's columns is in 16 bits. \n" +
        "       d) The number of maze's rows is in 16 bits. \n" +
        "       e) Start coordinates are in (X in 16 bits and Y in 16 bits) \n" +
        "       f) End coordinates are in (X in 16 bits and Y in 16 bits) \n" +
        "       g) 96 bits are reserved for future use \n" +
        "       h) Number of code words is in 32 bits \n" +
        "       i) Offset in the file to the solution part of the file is in 32 bits \n" +
        "       j) Separator is in 8 bits \n" + 
        "       k) Wall is in 8 bits \n" + 
        "       l) Path is in 8 bits \n" + 
        "   2. Code words that reflect what the maze looks like: \n" +
        "       a) Separator - 8bits \n" +
        "       b) Value of the code word - 8bits \n" +
        "       c) The number of occurences - 8 bits \n" + //
        "\n" +
        "How to use app and what is it cappable of? \n" + 
        "\n" +
        "IMPORTANT all the information you need to know how to use app are in the bottom label.\n" +
        "\n" +
        "First you should choose the file with maze, by selecting File->Load Maze the the window will pop up, in which you can choose your file. \n" +
        "The you should analyze the maze, by clicking \"Analyze Maze\". If your maze doesnt have Start and End you will have to choose it (Look down). \n" +
        "After that you can (if you haven't done it already) choose new Start and End position or select solving mode. \n" +
        "\n" + 
        "How to choose Start/ End? \n" +
        "You can do it by clicking Pick Start/End and then clicking on the path on the maze or by clicking Type Start/ End typing coordinates in and submiting it in by clicking \"Done\". \n" +
        "\n" +
        "Then you can solve the maze just by selecting one of available solving modes. \n" +
        "After that you can export solution (File->Export Solution) or load another maze (File -> Load Maze)"
        );

        backButton.addActionListener(

            (e) -> {    Component[] components = this.getComponents();
                        if ( components.length > 1){
                            this.mazePanel.setVisible(true);
                        }
                        this.helpPanel.setVisible(false);}

        );
        
        this.helpPanel.add(help);
        this.helpPanel.add(area);
        this.helpPanel.add(backButton);
    }

    public void setHelpEnabled(){
        System.out.println("help");
        Component[] components = this.getComponents();
        if ( components.length > 1){
            this.mazePanel.setVisible(false);
        }
        this.helpPanel.setVisible(true);
    }

    public void start(char c, Watched watched){

        mazePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                if ( c == 'S'){
                    maze.setNewStartPosition((x-5)/10, (y-5)/10);
                } else {
                    maze.setNewEndPosition((x-5)/10, (y-5)/10);
                }
                mazePanel.removeMouseListener(mazePanel.getMouseListeners()[0]);
                if (c == 'S'){
                    watched.setMessage("StartEndNewPositionS");
                } else {
                    watched.setMessage("StartEndNewPositionE");
                }
                
            }
        });
        
    }

    public MazePanel getMazePanel(){
        return this.mazePanel;
    }

}
