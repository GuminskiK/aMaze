import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ToolPanel extends JPanel{

    JButton analyze = new JButton();
    JButton custom = new JButton();

    CustomPanel customPanel;

    JComboBox modes;


    Font font = new Font( "Dialog", Font.BOLD, 10);

    ToolPanel(ContentPanel contentPanel, ActionListener analyzeListener,ActionListener shortestListener,
     ActionListener customStartListener, ActionListener customEndListener, ActionListener wholeListener, ActionListener customListener, JLabel infoLabel, Maze maze){

        this.setBackground(Color.DARK_GRAY);
        this.setPreferredSize(new Dimension(150,50));
        //this.setLayout();
        
        CreateToolButton( analyze, "Analyze maze", font);
        CreateToolButton( custom, "Choose Start/End", font);

        analyze.addActionListener(

            (e) -> {    analyze.setEnabled(false);
                        analyzeListener.actionPerformed(e);}

        );

        custom.addActionListener(

            (e) -> {ToolEnable(true, new int[]{3,4,5});
                    ToolEnable(false, new int[]{2});
                    customListener.actionPerformed(e);
                    infoLabel.setText("By wybrać nowy Start/End naciśnij Pick Start/Pick End lub Type Start/ Type End.");}
        );
        
        String[] modesList = new String[]{"-Choose algorithm-","Shortest", "Whole"}; 
        modes = new JComboBox(modesList);
        modes.setPreferredSize(new Dimension(140, 30));
        modes.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (modes.getSelectedItem() == "Shortest"){
                    shortestListener.actionPerformed(e);
                } else if (modes.getSelectedItem() == "Whole"){
                    wholeListener.actionPerformed(e);
                }
            }
        });
        modes.setEnabled(false);
        

        customPanel = new CustomPanel(contentPanel, modes, customStartListener, customEndListener, infoLabel, customListener, maze);
        customPanel.setVisible(false);

        this.add(analyze);
        this.add(custom);
        this.add(customPanel);
        this.add(modes);

    }

    private void CreateToolButton( JButton button ,String txt, Font font){

        button.setText(txt);
        button.setFont(font);
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(140, 30));
        button.setEnabled(false);
    }

    public void resetComboBoX(){
        modes.setSelectedIndex(0);
    }

    public void ToolEnable(boolean x, int[] y){
        
        for (int i = 0; i < y.length; i++){
            
            switch(y[i]){

                case 0:
                    analyze.setEnabled(x);
                    break;
                case 1:
                    custom.setEnabled(x);
                    break;
                case 2:
                    modes.setEnabled(x);
                    break;
                case 3:
                    customPanel.setVisible(x);
                    break;
                case 4:
                    customPanel.setPickStartEnabled(x);
                    break;
                case 5:
                    customPanel.setPickEndEnabled(x);
                    break;
                default:
                    break;
            }
        }    
    }

}
