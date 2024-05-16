package GUI;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Core.Maze;
import Core.Observer;
import Core.Watched;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class Frame extends JFrame implements Observer{

    private  Menu menuBar;
    private  ToolPanel toolPanel;
    private  OuterContentPanel outerContentPanel;

    private Maze maze;
    private Watched watched;

    public Frame(Maze maze, Watched watched){
        this.maze = maze;
        this.watched = watched;
    }

    public void customError(){
        JOptionPane.showMessageDialog(this, "You can pick new location only on the maze's path! Try again!", "customError", JOptionPane.ERROR_MESSAGE);
    }

    //gettery i settery

    public Menu getMenu(){
        return this.menuBar;
    }

    public ToolPanel getToolPanel(){
        return this.toolPanel;
    }

    public OuterContentPanel getOuterContentPanel(){
        return this.outerContentPanel;
    }

    //etapy
    private void start(){
        
        ImageIcon logo = new ImageIcon("Files/logo.jpeg");
        this.setIconImage(logo.getImage());

        this.menuBar = new Menu(this, watched, outerContentPanel);
        this.outerContentPanel = new OuterContentPanel(maze, watched);
        this.toolPanel = new ToolPanel( watched, outerContentPanel, maze);

        this.setTitle("aMaze");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(720, 405);
        this.setMinimumSize(new Dimension(900, 480));
        this.setLayout(new BorderLayout());

        this.setJMenuBar(menuBar);
        this.add(toolPanel, BorderLayout.WEST);
        this.add(outerContentPanel);
        this.setVisible(true);
        
    }

    private void getFile(){
        
        menuBar.setloadEnabled(true);
        menuBar.setexportEnabled(false);
        
    }

    @Override
    public void update(String message) {
        System.out.println("Frame: "  + message);

        switch(message){
            case "start":
                start();
                break;
            case "getFile":
                getFile();
                break;
            default:
                break;
            
        }
    }

}
