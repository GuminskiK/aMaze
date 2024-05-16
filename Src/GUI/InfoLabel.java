package GUI;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Core.Observer;

public class InfoLabel extends JLabel implements Observer {

    InfoLabel(){

        this.setText("Info");
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setPreferredSize(new Dimension(300,50));
        
    }

    @Override
    public void update(String message) {
        
        //System.out.println("INFO: " + message);

        switch(message){
            case "getFile":
                this.setText("Please choose a maze to load (File-> Load Maze)");
                break;
            default:
                break;
        }
    }


}
