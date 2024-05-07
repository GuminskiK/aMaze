
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame{

    public Menu menuBar;
    public ContentPanel ContentPanel;
    public ToolPanel ToolPanel;
    JPanel outerContent;
    JLabel infoLabel = new JLabel();


    MyFrame(ActionListener readListener, ActionListener analyzeListener, ActionListener shortestListener, ActionListener helpListener, ActionListener customStartListener, ActionListener customEndListener, ActionListener wholeListener, ActionListener customListener){


        this.menuBar = new Menu(readListener, helpListener);
        this.ContentPanel = new ContentPanel();
        this.ToolPanel = new ToolPanel(this.ContentPanel, analyzeListener, shortestListener, customStartListener, customEndListener, wholeListener, customListener, infoLabel);
        JScrollPane scrollPane = new JScrollPane(ContentPanel);
        
        infoLabel.setText("Info");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);


        outerContent = new JPanel();
        outerContent.setLayout(new BorderLayout());
        outerContent.add(scrollPane);
        outerContent.add(infoLabel, BorderLayout.SOUTH);
        

        this.setTitle("aMaze");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(720, 405);
        this.setMinimumSize(new Dimension(900, 480));
        this.setVisible(true);
        this.setLayout(new BorderLayout());

        ImageIcon logo = new ImageIcon("Files/logo.jpeg");
        this.setIconImage(logo.getImage());

        this.setJMenuBar(menuBar);
        this.add(ToolPanel, BorderLayout.WEST);
        //this.add(ContentPanel);
        this.add(outerContent);
        this.setVisible(true);
           
    }

    public void customError(){
        JOptionPane.showMessageDialog(this, "Można wybrać tylko miejsce na ścieżce! Spróbuj ponownie!", "customError", JOptionPane.ERROR_MESSAGE);
    }

}
