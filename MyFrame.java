
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame{

    public Menu menuBar;
    public ContentPanel ContentPanel;
    public ToolPanel ToolPanel;


    MyFrame(ActionListener readListener, ActionListener analyzeListener, ActionListener shortestListener, ActionListener helpListener, ActionListener customStartListener, ActionListener customEndListener, ActionListener wholeListener){


        this.menuBar = new Menu(readListener, helpListener);
        this.ContentPanel = new ContentPanel();
        this.ToolPanel = new ToolPanel(this.ContentPanel, analyzeListener, shortestListener, customStartListener, customEndListener, wholeListener);
        JScrollPane scrollPane = new JScrollPane(ContentPanel);

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
        this.add(scrollPane);
        this.setVisible(true);
           
    }

}
