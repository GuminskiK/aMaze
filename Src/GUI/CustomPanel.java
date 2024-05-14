package GUI;
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

import Core.Maze;

public class CustomPanel extends JPanel {

    private JPanel startPanel;
    private JLabel start;
    private JButton pickStart = new JButton();
    private JTextArea currentPosStart;

    private Integer startPosX = null;
    private Integer startPosY = null;

    private JPanel endPanel;
    private JLabel end;
    private JButton pickEnd = new JButton();
    private JTextArea currentPosEnd;

    private Integer endPosX = null;
    private Integer endPosY = null;

    private JButton typeStart = new JButton();
    private JButton typeEnd = new JButton();

    private int typeStartOn;
    private int typeEndOn;

    private Font font = new Font("Dialog", Font.BOLD, 10);

    CustomPanel(ContentPanel contentPanel, JComboBox modes, ActionListener customStartListener,
            ActionListener customEndListener, JLabel infoLabel, ActionListener customListener, Maze maze) {

        this.typeStartOn = 0;
        this.typeEndOn = 0;

        ActionListener StartAL = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // pickEnd.setEnabled(true);
            }

        };

        ActionListener EndAL = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // pickStart.setEnabled(true);
            }

        };

        start = new JLabel("start");

        CreateToolButton(pickStart, "Pick Start", font);
        pickStart.addActionListener(

                (e) -> {
                    modes.setEnabled(false);
                    pickStart.setEnabled(false);
                    pickEnd.setEnabled(false);
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
                        pickStart.setEnabled(false);
                        pickEnd.setEnabled(false);
                        typeEnd.setEnabled(false);
                        infoLabel.setText("Wpisz koordynaty nowego Startu");
                        currentPosStart.setEditable(true);
                        this.typeStartOn++;
                        maze.pickCustom(1);
                    } else {

                        currentPosStart.setEditable(false);

                        Pattern pattern = Pattern.compile("X:(\\d+)\nY:(\\d+)");
                        Matcher matcher = pattern.matcher(currentPosStart.getText());

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

        currentPosStart = new JTextArea();
        currentPosStart.setText("X:" + startPosX + "\nY:" + startPosY);
        currentPosStart.setEditable(false);

        startPanel = new JPanel();
        startPanel.setPreferredSize(new Dimension(140, 120));
        startPanel.setBackground(Color.green);

        startPanel.add(start);
        startPanel.add(pickStart);
        startPanel.add(typeStart);
        startPanel.add(currentPosStart);

        startPanel.setVisible(true);

        end = new JLabel("end");

        CreateToolButton(pickEnd, "Pick End", font);
        pickEnd.addActionListener(

                (e) -> {
                    modes.setEnabled(false);
                    pickStart.setEnabled(false);
                    pickEnd.setEnabled(false);
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
                        pickStart.setEnabled(false);
                        pickEnd.setEnabled(false);
                        typeStart.setEnabled(false);
                        infoLabel.setText("Wpisz koordynaty nowego Endu");
                        currentPosEnd.setEditable(true);
                        maze.pickCustom(1);
                    } else {

                        currentPosEnd.setEditable(false);
                        Pattern pattern = Pattern.compile("X:(\\d+)\nY:(\\d+)");
                        Matcher matcher = pattern.matcher(currentPosEnd.getText());

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

        currentPosEnd = new JTextArea();
        currentPosEnd.setText("X:" + endPosX + "\nY:" + endPosY);
        currentPosEnd.setEditable(false);

        endPanel = new JPanel();
        endPanel.setPreferredSize(new Dimension(140, 120));
        endPanel.setBackground(Color.pink);

        endPanel.add(end);
        endPanel.add(pickEnd);
        endPanel.add(typeEnd);
        endPanel.add(currentPosEnd);
        endPanel.setVisible(true);

        this.add(startPanel);
        this.add(endPanel);
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
        pickStart.setEnabled(x);
    }

    public void setPickEndEnabled(boolean x) {
        pickEnd.setEnabled(x);
    }

    public void setTypeStartEnabled(boolean x) {
        typeStart.setEnabled(x);
    }

    public void setTypeEndEnabled(boolean x) {
        typeEnd.setEnabled(x);
    }

    public void changeStartPos(Integer startPosX, Integer startPosY) {
        this.startPosX = startPosX;
        this.startPosY = startPosY;
        currentPosStart.setText("X:" + startPosX + "\nY:" + startPosY);
    }

    public void changeEndPos(Integer endPosX, Integer endPosY) {
        this.endPosX = endPosX;
        this.endPosY = endPosY;
        currentPosEnd.setText("X:" + endPosX + "\nY:" + endPosY);
    }

    public boolean[] ifNull() {
        boolean[] Null = new boolean[2];
        if (startPosX == null) {
            Null[0] = true;
        }
        if (endPosX == null) {
            Null[1] = true;
        }
        return Null;
    }

    public void reset(){
        this.typeStartOn = 0;
        this.typeEndOn = 0;
    }



    //gettery i settery

    public void setTypeEndOn(int typeEndOn){
        this.typeEndOn = typeEndOn;
    }

    public void setTypeStartOn(int typeStartOn){
        this.typeStartOn = typeStartOn;
    }
}
