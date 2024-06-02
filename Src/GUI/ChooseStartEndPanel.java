package GUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Core.Maze;
import Core.Watched;

public class ChooseStartEndPanel extends JPanel {

    private JPanel startPanel;
    private JLabel start;
    private JButton pickStart = new JButton();
    private JTextArea currentPosStart;
    private JTextArea xYStart;

    private Integer startPosX = null;
    private Integer startPosY = null;

    private JPanel endPanel;
    private JLabel end;
    private JButton pickEnd = new JButton();
    private JTextArea currentPosEnd;
    private JTextArea xYEnd;

    private Integer endPosX = null;
    private Integer endPosY = null;

    private JButton typeStart = new JButton();
    private JButton typeEnd = new JButton();

    private int typeStartOn;
    private int typeEndOn;

    private Font font = new Font("Dialog", Font.BOLD, 10);

    ChooseStartEndPanel(ContentPanel contentPanel, JComboBox<String> modes, JLabel infoLabel, Maze maze, Watched watched) {

        this.typeStartOn = 0;
        this.typeEndOn = 0;

        start = new JLabel("start");

        createToolButton(pickStart, "Pick Start");
        pickStart.addActionListener(

                (e) -> {
                    modes.setEnabled(false);
                    pickStart.setEnabled(false);
                    pickEnd.setEnabled(false);
                    typeEnd.setEnabled(false);
                    typeStart.setEnabled(false);
                    infoLabel.setText("Click on Path on the maze to choose new Start");
                    contentPanel.start('S', watched);
                    maze.pickCustom(0);
                });

        createToolButton(typeStart, "Type Start");
        typeStart.setVisible(true);

        typeStart.addActionListener(

                (e) -> {
                    if (this.typeStartOn == 0) {
                        

                        modes.setEnabled(false);
                        pickStart.setEnabled(false);
                        pickEnd.setEnabled(false);
                        typeEnd.setEnabled(false);
                        infoLabel.setText("Type new Start coordinates in");
                        currentPosStart.setEditable(true);
                        this.typeStartOn++;
                        maze.pickCustom(1);
                        infoLabel.setText("When you finish typing coordinates in, please click \"Done\"");
                        typeStart.setText("Done");

                    } else {

                        currentPosStart.setEditable(false);
                        typeStart.setText("Type Start");

                        Pattern pattern = Pattern.compile("\\d+");
                        Matcher matcher = pattern.matcher(currentPosStart.getText());
                        int firstInt = 0;
                        int secondInt = 0;
                        int found = 0;
                        
                        if (matcher.find()) {
                            firstInt = Integer.parseInt(matcher.group());
                        }
                
                        if (matcher.find()) {
                            secondInt = Integer.parseInt(matcher.group());
                        }
                        
                        if( found == 2){
                            this.typeStartOn = 0;
                            JOptionPane.showMessageDialog(contentPanel, "Incorrect coordinates. They should look like: <integer> <integer>", "typeError", JOptionPane.ERROR_MESSAGE);
                        } else {
                            maze.setNewStartPosition(firstInt, secondInt);
                            watched.setMessage("StartEndNewPositionS");
                        }
                    }

                });

        currentPosStart = new JTextArea();
        currentPosStart.setEditable(false);

        currentPosStart.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                }
            }
        });

        xYStart = new JTextArea();
        xYStart.setText("X:\nY:");
        xYStart.setEditable(false);

        startPanel = new JPanel();
        startPanel.setPreferredSize(new Dimension(140, 120));
        startPanel.setBackground(Color.green);

        startPanel.add(start);
        startPanel.add(pickStart);
        startPanel.add(typeStart);
        startPanel.add(xYStart);
        startPanel.add(currentPosStart);
        

        startPanel.setVisible(true);

        end = new JLabel("end");

        createToolButton(pickEnd, "Pick End");
        pickEnd.addActionListener(

                (e) -> {
                    modes.setEnabled(false);
                    pickStart.setEnabled(false);
                    pickEnd.setEnabled(false);
                    typeEnd.setEnabled(false);
                    typeStart.setEnabled(false);
                    infoLabel.setText("Click on Path on the maze to choose new End");
                    contentPanel.start('E', watched);
                    maze.pickCustom(0);
                });
        createToolButton(typeEnd, "Type End");
        typeEnd.setVisible(true);
        typeEnd.addActionListener(

                (e) -> {
                    if (typeEndOn == 0) {
                        typeEndOn++;

                        modes.setEnabled(false);
                        pickStart.setEnabled(false);
                        pickEnd.setEnabled(false);
                        typeStart.setEnabled(false);
                        infoLabel.setText("Type new End coordinates in");
                        currentPosEnd.setEditable(true);
                        maze.pickCustom(1);
                        infoLabel.setText("When you finish typing coordinates in, please click \"Done\"");
                        typeEnd.setText("Done");
                    } else {

                        currentPosEnd.setEditable(false);
                        typeEnd.setText("Type End");

                        Pattern pattern = Pattern.compile("\\d+");
                        Matcher matcher = pattern.matcher(currentPosEnd.getText());

                        int firstInt = 0;
                        int secondInt = 0;
                        int found = 0;

                        if (matcher.find()) {
                            firstInt = Integer.parseInt(matcher.group());
                        }
                
                        if (matcher.find()) {
                            secondInt = Integer.parseInt(matcher.group());
                        }
                        
                        if( found == 2){
                            this.typeEndOn = 0;
                            JOptionPane.showMessageDialog(contentPanel, "Incorrect coordinates. They should look like: <integer> <integer>", "typeError", JOptionPane.ERROR_MESSAGE);
                        } else {
                            maze.setNewEndPosition(firstInt, secondInt);
                            watched.setMessage("StartEndNewPositionE");
                        }
                    }

                });

        currentPosEnd = new JTextArea();
        currentPosEnd.setEditable(false);
        
        currentPosEnd.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                }
            }
        });

        xYEnd = new JTextArea();
        xYEnd.setText("X:\nY:");
        xYEnd.setEditable(false);

        endPanel = new JPanel();
        endPanel.setPreferredSize(new Dimension(140, 120));
        endPanel.setBackground(Color.pink);

        endPanel.add(end);
        endPanel.add(pickEnd);
        endPanel.add(typeEnd);
        
        endPanel.add(xYEnd);
        endPanel.add(currentPosEnd);
        endPanel.setVisible(true);

        this.add(startPanel);
        this.add(endPanel);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.setPreferredSize(new Dimension(140, 240));
        this.setVisible(true);
    }

    private void createToolButton(JButton button, String txt) {

        button.setText(txt);
        button.setFont(font);
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(120, 20));
        button.setEnabled(true);
    }

    public void enableStartInputs( boolean x){

        pickStart.setEnabled(x);
        typeStart.setEnabled(x);

    }

    public void enableEndInputs( boolean x){

        pickEnd.setEnabled(x);
        typeEnd.setEnabled(x);
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
        if ( startPosX != null && startPosY != null){
            currentPosStart.setText(startPosX + "\n" + startPosY);
        } else {
            currentPosStart.setText(" \n ");
        }
        
    }

    public void changeEndPos(Integer endPosX, Integer endPosY) {
        this.endPosX = endPosX;
        this.endPosY = endPosY;
        if ( endPosX != null && endPosY != null){
            currentPosEnd.setText(endPosX + "\n" + endPosY);
        } else {
            currentPosEnd.setText(" \n ");
        }
        
    }

    public boolean[] ifNull() {
        boolean[] Null = new boolean[2];
        if (startPosX == null || startPosY == null) {
            Null[0] = true;
        }
        if (endPosX == null || endPosY == null) {
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
