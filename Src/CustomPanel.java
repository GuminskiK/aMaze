import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class CustomPanel extends JPanel{

    JPanel StartPanel;
    JLabel Start;
    JButton PickStart = new JButton();
    JTextArea CurrentPosStart;

    Integer StartPosX = null;
    Integer StartPosY = null;


    JPanel EndPanel;
    JLabel End;
    JButton PickEnd = new JButton();
    JTextArea CurrentPosEnd;

    Integer EndPosX = null;
    Integer EndPosY = null;

    Font font = new Font( "Dialog", Font.BOLD, 10);
    
    CustomPanel(ContentPanel contentPanel, JComboBox modes, ActionListener customStartListener, ActionListener customEndListener, JLabel infoLabel){
        

        ActionListener StartAL = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                //PickEnd.setEnabled(true);
            }

        };

        ActionListener EndAL = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                //PickStart.setEnabled(true);
            }

        };

        Start = new JLabel("Start");

        CreateToolButton( PickStart, "Pick Start", font);
        PickStart.addActionListener(

            (e) -> {modes.setEnabled(false);
                    PickStart.setEnabled(false);
                    PickEnd.setEnabled(false);
                    infoLabel.setText("Kliknij na ściężkę labiryntu by wybrać nowy Start");
                    contentPanel.start(StartAL, 'S', contentPanel, customStartListener);}
        );
        
        CurrentPosStart = new JTextArea();
        CurrentPosStart.setText("X:"+ StartPosX +"\nY:" + StartPosY);
        CurrentPosStart.setEditable(false);

        StartPanel = new JPanel();
        StartPanel.setPreferredSize(new Dimension(140,100));
        StartPanel.setBackground(Color.green);

        StartPanel.add(Start);
        StartPanel.add(PickStart);
        StartPanel.add(CurrentPosStart);
        StartPanel.setVisible(true);



        End = new JLabel("End");
    
        CreateToolButton( PickEnd, "Pick End", font);
        PickEnd.addActionListener(

            (e) -> {modes.setEnabled(false); 
                    PickStart.setEnabled(false);
                    PickEnd.setEnabled(false);
                    infoLabel.setText("Kliknij na ściężkę labiryntu by wybrać nowy End");
                    contentPanel.start(EndAL, 'E', contentPanel, customEndListener);}
        );

        CurrentPosEnd = new JTextArea();
        CurrentPosEnd.setText("X:"+ EndPosX +"\nY:" + EndPosY);
        CurrentPosEnd.setEditable(false);

        EndPanel = new JPanel();
        EndPanel.setPreferredSize(new Dimension(140,100));
        EndPanel.setBackground(Color.pink);

        EndPanel.add(End);
        EndPanel.add(PickEnd);
        EndPanel.add(CurrentPosEnd);
        EndPanel.setVisible(true);

        this.add(StartPanel);
        this.add(EndPanel);
        this.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        this.setPreferredSize(new Dimension(140, 200));
        this.setVisible(true);
    }

    private void CreateToolButton( JButton button, String txt, Font font){

        button.setText(txt);
        button.setFont(font);
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(140, 30));
        button.setEnabled(true);
    }

    public void setPickStartEnabled(boolean x){
        PickStart.setEnabled(x);
    }

    public void setPickEndEnabled(boolean x){
        PickEnd.setEnabled(x);
    }

    public void changeStartPos(Integer StartPosX, Integer StartPosY){
        this.StartPosX = StartPosX;
        this.StartPosY = StartPosY;
        CurrentPosStart.setText("X:"+ StartPosX +"\nY:" + StartPosY);
    }

    public void changeEndPos(Integer EndPosX, Integer EndPosY){
        this.EndPosX = EndPosX;
        this.EndPosY = EndPosY;
        CurrentPosEnd.setText("X:"+ EndPosX +"\nY:" + EndPosY);
    }
}

