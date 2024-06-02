package GUI;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Core.Main;
import Core.Maze;
import Core.Observer;

public class InfoLabel extends JLabel implements Observer {

    Maze maze;

    InfoLabel(Maze maze){

        this.maze = maze;
        this.setText("Info");
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setPreferredSize(new Dimension(300,50));
        
    }

    private void startChanged(){
        
        if( maze.getStartLocated() && maze.getEndLocated()){
            this.setText("<html> The choice of a new Start was successful. Now you can choose new End location or choose solving mode (the old End location will be used).<html>");
        } else {
            this.setText("The choice of a new Start was successful. Now you can choose new End location.");
        }

        if( maze.getStartChanged() && maze.getEndChanged()){
            this.setText("Choose solving mode.");
        }
    }

    private void endChanged() {
        
        if( maze.getStartLocated() && maze.getEndLocated()){
            this.setText("<html>The choice of a new End was successful. Now you can choose new Start location or choose solving mode (the old Start location will be used).<html>");
        } else{
            this.setText("The choice of a new End was successful. Now you can choose new Start location.");
        }

        if( maze.getStartChanged() && maze.getEndChanged()){
           this.setText("Choose solving mode.");
        }

    }


    @Override
    public void update(String message) {
        
        //System.out.println("INFO: " + message);

        switch(message){
            case "getFile":
                this.setText("Please choose a maze to load (File-> Load Maze)");
                break;
            case "gotFile":
                this.setText("Loading maze...");
                break;
            case "toDraw":
                this.setText("Drawing maze...");
                break;
            case "wasRead":
                this.setText("<html> The maze has been loaded. It has " + maze.getColumns() + " columns and " + maze.getRows() + " rows. Please click Analyze maze button to analyze the maze.<html>");
                break;
            case "analyze":
                this.setText("Analysis in progress.");
                break;
            case "shortest":
                this.setText("Searching for the shortest solution to the maze.");
                break;
            case "whole":
                this.setText("Searching for the shortest solution to the maze.");
                break;
            case "solved":
                if (Main.getMazeSolver().getLengthMin() <= 255){
                    this.setText("<html> The solution to the maze has been found. It has " + Main.getMazeSolver().getLengthMin() + " steps. You can now load another maze ( File -> Load Maze ) or export solution (File-> Export Solution )</html>");
                } else {
                    this.setText("<html> The solution to the maze has been found. It has " + Main.getMazeSolver().getLengthMin() + " steps. You can now load another maze ( File -> Load Maze ).</html>");
                }
                break;
            case "noStartEnd":
                this.setText("Choose Start and End");
                break;
            case "noStart":
                this.setText("Choose Start");
                break;
            case "noEnd":
                this.setText("Choose End");
                break;
            case "StartChanged":
                startChanged();
                break;
            case "EndChanged":
                endChanged();
                break;
            case "exported":
                this.setText("Solution successfully exported. You can now load another maze ( File -> Load Maze ).");
                break;
            default:
                break;
        }
    }




}
