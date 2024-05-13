import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class CustomPanel extends JPanel {

    JPanel StartPanel;
    JLabel Start;
    JButton PickStart = new JButton();
    JTextArea CurrentPosStart;

    Integer StartPosX = null;
    Integer StartPosY = null;

    JPanel EndPanel;
    JLabel End;
    JButton PickEnd = new JButton();
    JTextArea CurrentPosEnd;

    Integer EndPosX = null;
    Integer EndPosY = null;

    JButton typeStart = new JButton();
    JButton typeEnd = new JButton();

    int typeStartOn;
    int typeEndOn;

    Font font = new Font("Dialog", Font.BOLD, 10);

    CustomPanel(ContentPanel contentPanel, JComboBox modes, ActionListener customStartListener,
            ActionListener customEndListener, JLabel infoLabel, ActionListener customListener, Maze maze) {

        this.typeStartOn = 0;
        this.typeEndOn = 0;

        ActionListener StartAL = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // PickEnd.setEnabled(true);
            }

        };

        ActionListener EndAL = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // PickStart.setEnabled(true);
            }

        };

        Start = new JLabel("Start");

        CreateToolButton(PickStart, "Pick Start", font);
        PickStart.addActionListener(

                (e) -> {
                    modes.setEnabled(false);
                    PickStart.setEnabled(false);
                    PickEnd.setEnabled(false);
                    typeEnd.setEnabled(false);
                    typeStart.setEnabled(false);
                    infoLabel.setText("Kliknij na ściężkę labiryntu by wybrać nowy Start");
                    contentPanel.start(StartAL, 'S', contentPanel, customStartListener);
                    maze.pickCustom(0);
                });

        CreateToolButton(typeStart, "Type Start", font);
        typeStart.setVisible(true);

        typeStart.addActionListener(

                (e) -> {
                    System.out.println(this.typeStartOn);
                    if (this.typeStartOn == 0) {
                        

                        modes.setEnabled(false);
                        PickStart.setEnabled(false);
                        PickEnd.setEnabled(false);
                        typeEnd.setEnabled(false);
                        infoLabel.setText("Wpisz koordynaty nowego Startu");
                        CurrentPosStart.setEditable(true);
                        this.typeStartOn++;
                        maze.pickCustom(1);
                    } else {

                        CurrentPosStart.setEditable(false);

                        Pattern pattern = Pattern.compile("X:(\\d+)\nY:(\\d+)");
                        Matcher matcher = pattern.matcher(CurrentPosStart.getText());

                        if (matcher.find()) {
                            Integer number1 = Integer.valueOf(matcher.group(1));
                            Integer number2 = Integer.valueOf(matcher.group(2));
                            System.out.println("Number 1: " + number1);
                            System.out.println("Number 2: " + number2);
                            maze.setCustomStart(number1, number2);
                            customStartListener.actionPerformed(e);

                        } else {
                            this.typeStartOn = 0;
                            JOptionPane.showMessageDialog(contentPanel, "Nie poprawne koordynaty. Powinny być w postaci X:<liczba całkowita> Y:<liczba całkowita>", "typeError", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                });

        CurrentPosStart = new JTextArea();
        CurrentPosStart.setText("X:" + StartPosX + "\nY:" + StartPosY);
        CurrentPosStart.setEditable(false);

        StartPanel = new JPanel();
        StartPanel.setPreferredSize(new Dimension(140, 120));
        StartPanel.setBackground(Color.green);

        StartPanel.add(Start);
        StartPanel.add(PickStart);
        StartPanel.add(typeStart);
        StartPanel.add(CurrentPosStart);

        StartPanel.setVisible(true);

        End = new JLabel("End");

        CreateToolButton(PickEnd, "Pick End", font);
        PickEnd.addActionListener(

                (e) -> {
                    modes.setEnabled(false);
                    PickStart.setEnabled(false);
                    PickEnd.setEnabled(false);
                    typeEnd.setEnabled(false);
                    typeStart.setEnabled(false);
                    infoLabel.setText("Kliknij na ściężkę labiryntu by wybrać nowy End");
                    contentPanel.start(EndAL, 'E', contentPanel, customEndListener);
                    maze.pickCustom(0);
                });
        CreateToolButton(typeEnd, "Type End", font);
        typeEnd.setVisible(true);
        typeEnd.addActionListener(

                (e) -> {
                    if (typeEndOn == 0) {
                        typeEndOn++;

                        modes.setEnabled(false);
                        PickStart.setEnabled(false);
                        PickEnd.setEnabled(false);
                        typeStart.setEnabled(false);
                        infoLabel.setText("Wpisz koordynaty nowego Endu");
                        CurrentPosEnd.setEditable(true);
                        maze.pickCustom(1);
                    } else {

                        CurrentPosEnd.setEditable(false);
                        Pattern pattern = Pattern.compile("X:(\\d+)\nY:(\\d+)");
                        Matcher matcher = pattern.matcher(CurrentPosEnd.getText());

                        if (matcher.find()) {
                            Integer number1 = Integer.valueOf(matcher.group(1));
                            Integer number2 = Integer.valueOf(matcher.group(2));
                            System.out.println("Number 1: " + number1);
                            System.out.println("Number 2: " + number2);
                            maze.setCustomEnd(number1, number2);
                            customEndListener.actionPerformed(e);

                        } else {
                            typeEndOn = 0;
                            JOptionPane.showMessageDialog(contentPanel, "Nie poprawne koordynaty. Powinny być w postaci X:<liczba całkowita> Y:<liczba całkowita>", "typeError", JOptionPane.ERROR_MESSAGE);
                            
                        }
                    }

                });

        CurrentPosEnd = new JTextArea();
        CurrentPosEnd.setText("X:" + EndPosX + "\nY:" + EndPosY);
        CurrentPosEnd.setEditable(false);

        EndPanel = new JPanel();
        EndPanel.setPreferredSize(new Dimension(140, 120));
        EndPanel.setBackground(Color.pink);

        EndPanel.add(End);
        EndPanel.add(PickEnd);
        EndPanel.add(typeEnd);
        EndPanel.add(CurrentPosEnd);
        EndPanel.setVisible(true);

        this.add(StartPanel);
        this.add(EndPanel);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.setPreferredSize(new Dimension(140, 240));
        this.setVisible(true);
    }

    private void CreateToolButton(JButton button, String txt, Font font) {

        button.setText(txt);
        button.setFont(font);
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(120, 20));
        button.setEnabled(true);
    }

    public void setPickStartEnabled(boolean x) {
        PickStart.setEnabled(x);
    }

    public void setPickEndEnabled(boolean x) {
        PickEnd.setEnabled(x);
    }

    public void setTypeStartEnabled(boolean x) {
        typeStart.setEnabled(x);
    }

    public void setTypeEndEnabled(boolean x) {
        typeEnd.setEnabled(x);
    }

    public void changeStartPos(Integer StartPosX, Integer StartPosY) {
        this.StartPosX = StartPosX;
        this.StartPosY = StartPosY;
        CurrentPosStart.setText("X:" + StartPosX + "\nY:" + StartPosY);
    }

    public void changeEndPos(Integer EndPosX, Integer EndPosY) {
        this.EndPosX = EndPosX;
        this.EndPosY = EndPosY;
        CurrentPosEnd.setText("X:" + EndPosX + "\nY:" + EndPosY);
    }

    public boolean[] ifNull() {
        boolean[] Null = new boolean[2];
        if (StartPosX == null) {
            Null[0] = true;
        }
        if (EndPosX == null) {
            Null[1] = true;
        }
        return Null;
    }

    public void reset(){
        this.typeStartOn = 0;
        this.typeEndOn = 0;
    }
}
