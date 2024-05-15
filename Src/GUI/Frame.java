package GUI;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import Core.Maze;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

public class Frame extends JFrame{

    private  Menu menuBar;
    private  ContentPanel contentPanel;
    private  ToolPanel toolPanel;
    private  JPanel outerContent;
    private  JLabel infoLabel = new JLabel();


    public Frame(ActionListener readListener, ActionListener analyzeListener, ActionListener shortestListener, 
    ActionListener helpListener, ActionListener customStartListener, ActionListener customEndListener,
    ActionListener wholeListener, ActionListener customListener, Maze maze){


        this.menuBar = new Menu(readListener, helpListener, this);
        this.contentPanel = new ContentPanel(maze);
        this.toolPanel = new ToolPanel(this.contentPanel, analyzeListener, shortestListener, customStartListener, 
        customEndListener, wholeListener, customListener, infoLabel, maze);
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        
        infoLabel.setText("Info");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setVerticalAlignment(SwingConstants.CENTER);
        infoLabel.setPreferredSize(new Dimension(300,50));


        outerContent = new JPanel();
        outerContent.setLayout(new BorderLayout());
        outerContent.add(scrollPane);
        outerContent.add(infoLabel, BorderLayout.SOUTH);
        

        this.setTitle("aMaze");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(720, 405);
        this.setMinimumSize(new Dimension(900, 480));
        this.setVisible(true);
        this.setLayout(new BorderLayout());

        ImageIcon logo = new ImageIcon("Files/logo.jpeg");
        this.setIconImage(logo.getImage());

        this.setJMenuBar(menuBar);
        this.add(toolPanel, BorderLayout.WEST);
        //this.add(contentPanel);
        this.add(outerContent);
        this.setVisible(true);
           
    }

    public void customError(){
        JOptionPane.showMessageDialog(this, "Można wybrać tylko miejsce na ścieżce! Spróbuj ponownie!", "customError", JOptionPane.ERROR_MESSAGE);
    }


    //gettery i settery

    public Menu getMenu(){
        return this.menuBar;
    }

    public JLabel getInfoLabel(){
        return this.infoLabel;
    }

    public ContentPanel getContentPanel(){
        return this.contentPanel;
    }

    public ToolPanel getToolPanel(){
        return this.toolPanel;
    }

}
