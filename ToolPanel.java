import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class ToolPanel extends JPanel{

    JButton analyze = new JButton();

    JButton start = new JButton();
    JButton end = new JButton();

    JButton shortest = new JButton();


    Font font = new Font( "Dialog", Font.BOLD, 10);

    ToolPanel(ContentPanel contentPanel, ActionListener analyzeListener,ActionListener shortestListener, ActionListener customStartListener, ActionListener customEndListener){

        this.setBackground(Color.DARK_GRAY);
        this.setPreferredSize(new Dimension(100,50));
        //this.setLayout();

        CreateToolButton( start, "Start", font);
        CreateToolButton( end, "End", font);
        CreateToolButton( shortest, "Shortest", font);
        CreateToolButton( analyze, "Analyze", font);

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

        start.addActionListener(

            (e) -> {    start.setEnabled(false);
                        contentPanel.start(Start, 'S', contentPanel, customStartListener); }

        );

        end.addActionListener(

            (e) -> {    end.setEnabled(false);
                        contentPanel.start(End, 'E', contentPanel, customEndListener); }

        );
    
        shortest.addActionListener(
    
            (e) -> shortestListener.actionPerformed(e)

        );
        
        this.add(analyze);
        this.add(start);
        this.add(end);
        this.add(shortest);

    }

    private void CreateToolButton( JButton button ,String txt, Font font){

        button.setText(txt);
        button.setFont(font);
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(80, 30));
        button.setEnabled(false);
    }

    public void ToolEnable(boolean x, int y){
        
        switch(y){

            case 0:
                analyze.setEnabled(x);
                break;
            case 1:
                start.setEnabled(x);
                break;
            case 2:
                end.setEnabled(x);
                break;
            case 3:
                shortest.setEnabled(x);
                break;
            default:
                break;
        }
    }

}
