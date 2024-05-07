import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;


public class ToolPanel extends JPanel{

    JButton analyze = new JButton();

    JButton custom = new JButton();
    JButton start = new JButton();
    JButton end = new JButton();

    JComboBox modes;


    Font font = new Font( "Dialog", Font.BOLD, 10);

    ToolPanel(ContentPanel contentPanel, ActionListener analyzeListener,ActionListener shortestListener, ActionListener customStartListener, ActionListener customEndListener, ActionListener wholeListener, ActionListener customListener){

        this.setBackground(Color.DARK_GRAY);
        this.setPreferredSize(new Dimension(150,50));
        //this.setLayout();
        
        CreateToolButton( analyze, "Analyze maze", font);
        CreateToolButton( custom, "Choose Start/End", font);
        CreateToolButton( start, "Start", font);
        CreateToolButton( end, "End", font);

        ActionListener Start = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                start.setEnabled(true);
            }

        };

        ActionListener End = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                end.setEnabled(true);
            }

        };

        analyze.addActionListener(

            (e) -> {    analyze.setEnabled(false);
                        analyzeListener.actionPerformed(e);}

        );

        custom.addActionListener(
            
            (e) -> customListener.actionPerformed(e)
        );
        
        start.addActionListener(

            (e) -> {    start.setEnabled(false);
                        end.setEnabled(false);
                        modes.setEnabled(false);
                        contentPanel.start(Start, 'S', contentPanel, customStartListener); }

        );

        end.addActionListener(

            (e) -> {    end.setEnabled(false);
                        start.setEnabled(false);
                        modes.setEnabled(false);
                        contentPanel.start(End, 'E', contentPanel, customEndListener); }

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
        
        this.add(analyze);
        this.add(custom);
        this.add(start);
        this.add(end);
        this.add(modes);

    }

    private void CreateToolButton( JButton button ,String txt, Font font){

        button.setText(txt);
        button.setFont(font);
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(140, 30));
        button.setEnabled(false);
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
                    start.setEnabled(x);
                    break;
                case 3:
                    end.setEnabled(x);
                    break;
                case 4:
                    modes.setEnabled(x);
                    break;
                default:
                    break;
            }
        }    
    }

}
