import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class ToolPanel extends JPanel{

    JButton analyze = new JButton();

    JButton custom = new JButton();
    JButton start = new JButton();
    JButton end = new JButton();

    JButton shortest = new JButton();
    JButton whole = new JButton();


    Font font = new Font( "Dialog", Font.BOLD, 10);

    ToolPanel(ContentPanel contentPanel, ActionListener analyzeListener,ActionListener shortestListener, ActionListener customStartListener, ActionListener customEndListener, ActionListener wholeListener, ActionListener customListener){

        this.setBackground(Color.DARK_GRAY);
        this.setPreferredSize(new Dimension(100,50));
        //this.setLayout();
        
        CreateToolButton( analyze, "Analyze", font);
        CreateToolButton( custom, "Custom", font);
        CreateToolButton( start, "Start", font);
        CreateToolButton( end, "End", font);
        CreateToolButton( shortest, "Shortest", font);
        CreateToolButton( whole, "Whole", font);

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
                        shortest.setEnabled(false);
                        whole.setEnabled(false);
                        contentPanel.start(Start, 'S', contentPanel, customStartListener); }

        );

        end.addActionListener(

            (e) -> {    end.setEnabled(false);
                        start.setEnabled(false);
                        shortest.setEnabled(false);
                        whole.setEnabled(false);
                        contentPanel.start(End, 'E', contentPanel, customEndListener); }

        );
    
        shortest.addActionListener(
    
            (e) -> shortestListener.actionPerformed(e)

        );

        whole.addActionListener(
    
            (e) -> wholeListener.actionPerformed(e)

        );
        
        this.add(analyze);
        this.add(custom);
        this.add(start);
        this.add(end);
        this.add(shortest);
        this.add(whole);

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
                custom.setEnabled(x);
                break;
            case 2:
                start.setEnabled(x);
                break;
            case 3:
                end.setEnabled(x);
                break;
            case 4:
                shortest.setEnabled(x);
                break;
            case 5:
                whole.setEnabled(x);
                break;
            default:
                break;
        }
    }

}
