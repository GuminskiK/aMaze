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

    private  Menu menu;
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
        return this.menu;
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

        this.outerContentPanel = new OuterContentPanel(maze, watched);

        this.toolPanel = new ToolPanel( watched, outerContentPanel, maze);
        menu = new Menu(this, watched, outerContentPanel);

        this.setTitle("aMaze");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(720, 405);
        this.setMinimumSize(new Dimension(900, 480));
        this.setLayout(new BorderLayout());

        this.setJMenuBar(menu);
        this.add(toolPanel, BorderLayout.WEST);
        this.add(outerContentPanel);
        this.setVisible(true);

        watched.registerObserver(this.outerContentPanel.getInfoLabel());
        watched.setMessage("started");
        
    }

    private void setLoadItemsToGetFile(){
        
        this.menu.setLoadEnabled(true);
        this.menu.setExportEnabled(false);
        
    }

    private void reset(){

        getToolPanel().getChooseStartEndPanel().changeStartPos(null,null);
        getToolPanel().getChooseStartEndPanel().changeEndPos(null,null);
        getMenu().setExportEnabled(false);
        getToolPanel().getChooseStartEndPanel().reset();
        getToolPanel().getChooseStartEndPanel().setTypeStartEnabled(true);
        getToolPanel().getChooseStartEndPanel().setTypeEndEnabled(true);
        getToolPanel().resetComboBox();

        watched.setMessage("read");

    }

    private void draw(){

        getOuterContentPanel().getContentPanel().addPanel(maze.getColumns(), maze.getRows());
        getToolPanel().toolEnable(true, new int[]{0});
        getOuterContentPanel().getContentPanel().repaint();

        watched.setMessage("wasDrawn");
    }

    private void afterWasRead(){

        getMenu().setLoadEnabled(false);
        getToolPanel().toolEnable(false, new int[]{0,1,2,3,4,5});
        getToolPanel().toolEnable(true, new int[]{0});
    }

    
    private void afterMazeSolved(){

        getOuterContentPanel().getContentPanel().getMazePanel().rePaint(maze);
        getMenu().setLoadEnabled(true);
        getMenu().setExportEnabled(true);

    }

    private void afterStartChanged(){
        
        toolPanel.getChooseStartEndPanel().enableStartInputs(false);

        toolPanel.toolEnable(true, new int[]{2});

        if ( !maze.getEndChanged()){
            toolPanel.getChooseStartEndPanel().enableEndInputs(true);
        }
    }

    private void afterEndChanged(){

        toolPanel.getChooseStartEndPanel().enableEndInputs(false);

        toolPanel.toolEnable(true, new int[]{2});

        if ( !maze.getStartChanged()){
            toolPanel.getChooseStartEndPanel().enableStartInputs(true);
        }
    }

    private void changeStartError(){
        customError();
        if (maze.whoPickedCustom() == 0) {
            toolPanel.toolEnable(true, new int[] { 4 });
        } else {
            toolPanel.getChooseStartEndPanel().setTypeStartEnabled(true);
            toolPanel.getChooseStartEndPanel().setTypeStartOn(0);
        }
    }

    private void changeEndError(){
        customError();
        if (maze.whoPickedCustom() == 0) {
            toolPanel.toolEnable(true, new int[] {5});
        } else {
            toolPanel.getChooseStartEndPanel().setTypeEndEnabled(true);
            toolPanel.getChooseStartEndPanel().setTypeEndOn(0);
        }
    }

    @Override
    public void update(String message) {
        //System.out.println("Frame: "  + message);
        //message.perform(this);
        switch(message){
            case "start":
                start();
                break;
            case "getFile":
                setLoadItemsToGetFile();
                break;
            case "reset":
                reset();
                break;
            case "toDraw":
                draw();
                break;
            case "wasRead":
                afterWasRead();
                break;
            case "analyze":
                getToolPanel().toolEnable(false, new int[]{0});
                break;
            case "shortest":
                getToolPanel().toolEnable(false, new int[]{0,1,2,3,4});
                break;
            case "whole":
                getToolPanel().toolEnable(false, new int[]{0,1,2,3,4});
                break;
            case "solved":
                afterMazeSolved();
                break;
            case "StartEndNewPositionS":
                toolPanel.getChooseStartEndPanel().enableStartInputs(false);
                break;
            case "StartEndNewPositionE":
                toolPanel.getChooseStartEndPanel().enableEndInputs(false);
                break;
            case "StartChanged":
                afterStartChanged();
                break;
            case "EndChanged":
                afterEndChanged();
                break;
            case "changeStartError":
                changeStartError();
                break;
            case "changeEndError":
                changeEndError();
                break;
            case "noStartEnd":
                toolPanel.getChooseStartEndPanel().setVisible(true);
                break;
            case "noStart":
                toolPanel.getChooseStartEndPanel().setVisible(true);
                break;
            case "noEnd":
                toolPanel.getChooseStartEndPanel().setVisible(true);
                break;
            default:
                break;
            
        }
    }

}
