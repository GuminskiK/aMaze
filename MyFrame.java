
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class MyFrame extends JFrame{

    public Menu menuBar;
    public ContentPanel ContentPanel;

    MyFrame(){


        this.menuBar = new Menu();
        ToolPanel ToolPanel = new ToolPanel();
        this.ContentPanel = new ContentPanel();
        JScrollPane scrollPane = new JScrollPane(ContentPanel);

        this.setTitle("aMaze");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(720, 405);
        this.setMinimumSize(new Dimension(320, 220));
        this.setVisible(true);
        this.setLayout(new BorderLayout());

        this.setJMenuBar(menuBar);
        this.add(ToolPanel, BorderLayout.WEST);
        //this.add(ContentPanel);
        this.add(scrollPane);
        this.setVisible(true);
           
    }

}
