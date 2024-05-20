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

    private void getFile(){
        
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

    private void toDraw(){

        getOuterContentPanel().getContentPanel().addPanel(maze.getColumns(), maze.getRows());
        getToolPanel().toolEnable(true, new int[]{0});
        getOuterContentPanel().getContentPanel().repaint();

        watched.setMessage("wasDrawn");
    }

    private void wasRead(){

        getMenu().setLoadEnabled(false);
        getToolPanel().toolEnable(false, new int[]{0,1,2,3,4,5});
        getToolPanel().toolEnable(true, new int[]{0});
    }

    private void analyze(){
        getToolPanel().toolEnable(false, new int[]{0});
    }
    
    private void solving(){

        getToolPanel().toolEnable(false, new int[]{0,1,2,3,4});
    }

    private void solved(){

        getOuterContentPanel().getContentPanel().getMazePanel().rePaint(maze);
        getMenu().setLoadEnabled(true);
        getMenu().setExportEnabled(true);

    }

    private void startChanged(){
        
        toolPanel.getChooseStartEndPanel().enableStartInputs(false);

        toolPanel.toolEnable(true, new int[]{2});

        if ( !maze.getEndChanged()){
            toolPanel.getChooseStartEndPanel().enableEndInputs(true);
        }
    }

    private void endChanged(){

        
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

    private void noStartEnd(){
        toolPanel.getChooseStartEndPanel().setVisible(true);
    }

    private void noStart(){
        toolPanel.getChooseStartEndPanel().setVisible(true);
    }

    private void noEnd(){
        toolPanel.getChooseStartEndPanel().setVisible(true);
    }

    @Override
    public void update(String message) {
        //System.out.println("Frame: "  + message);

        switch(message){
            case "start":
                start();
                break;
            case "getFile":
                getFile();
                break;
            case "reset":
                reset();
                break;
            case "toDraw":
                toDraw();
                break;
            case "wasRead":
                wasRead();
                break;
            case "analyze":
                analyze();
                break;
            case "shortest":
                solving();
                break;
            case "whole":
                solving();
                break;
            case "solved":
                solved();
                break;
            case "StartEndNewPositionS":
                toolPanel.getChooseStartEndPanel().enableStartInputs(false);
                break;
            case "StartEndNewPositionE":
                toolPanel.getChooseStartEndPanel().enableEndInputs(false);
                break;
            case "StartChanged":
                startChanged();
                break;
            case "EndChanged":
                endChanged();
                break;
            case "changeStartError":
                changeStartError();
                break;
            case "changeEndError":
                changeEndError();
                break;
            case "noStartEnd":
                noStartEnd();
                break;
            case "noStart":
                noEnd();
                break;
            case "noEnd":
                noStart();
                break;
            default:
                break;
            
        }
    }

}
