package GUI;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Core.Main;
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
        
        this.menu.setloadEnabled(true);
        this.menu.setexportEnabled(false);
        
    }

    private void reset(){

        getToolPanel().getChooseStartEndPanel().changeStartPos(null,null);
        getToolPanel().getChooseStartEndPanel().changeEndPos(null,null);
        getMenu().setexportEnabled(false);
        getToolPanel().getChooseStartEndPanel().reset();
        getToolPanel().getChooseStartEndPanel().setTypeStartEnabled(true);
        getToolPanel().getChooseStartEndPanel().setTypeEndEnabled(true);
        getToolPanel().resetComboBoX();

        watched.setMessage("read");

    }

    private void toDraw(){

        getOuterContentPanel().getContentPanel().addPanel(maze.getColumns(), maze.getRows());
        getToolPanel().toolEnable(true, new int[]{0});
        watched.setMessage("wasDrawn");
    }

    private void wasRead(){

        getMenu().setloadEnabled(false);
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
        getMenu().setloadEnabled(true);
        getMenu().setexportEnabled(true);

    }

    private void EnableStartInputs( boolean x){

        toolPanel.getChooseStartEndPanel().setPickStartEnabled(x);
        toolPanel.getChooseStartEndPanel().setTypeStartEnabled(x);

    }

    private void EnableEndInputs( boolean x){

        toolPanel.getChooseStartEndPanel().setPickEndEnabled(x);
        toolPanel.getChooseStartEndPanel().setTypeEndEnabled(x);
    }

    private void startChanged(){
        
        EnableStartInputs(false);

        if( Main.startLocated && Main.endLocated){
            toolPanel.toolEnable(true, new int[]{2});
            outerContentPanel.getInfoLabel().setText("The choice of a new Start was successful. Now you can choose new End location or choose solving mode.");
        } else {
            toolPanel.toolEnable(true, new int[]{2});
            outerContentPanel.getInfoLabel().setText("The choice of a new Start was successful. Now you can choose new End location..");
        }

        if( Main.startChanged && Main.endChanged){
            outerContentPanel.getInfoLabel().setText("Choose solving mode.");
        }

        if ( !Main.endChanged){
            EnableEndInputs(true);
        }
    }

    private void endChanged(){

        
        EnableEndInputs(false);

        if( Main.startLocated && Main.endLocated){
            toolPanel.toolEnable(true, new int[]{2});
            outerContentPanel.getInfoLabel().setText("The choice of a new End was successful. Now you can choose new Start location or choose solving mode.");
        } else{
            outerContentPanel.getInfoLabel().setText("The choice of a new End was successful. Now you can choose new Start location.");
        }

        if( Main.startChanged && Main.endChanged){
            outerContentPanel.getInfoLabel().setText("Choose solving mode.");
        }

        if ( !Main.startChanged){
            EnableStartInputs(true);
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
                EnableStartInputs(false);
                break;
            case "StartEndNewPositionE":
                EnableEndInputs(false);
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
