package GUI;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Core.Maze;
import Core.Watched;

public class OuterContentPanel extends JPanel {

    private InfoLabel infoLabel;
    private JScrollPane scrollPane;
    private ContentPanel contentPanel;

    OuterContentPanel(Maze maze, Watched watched){

        contentPanel = new ContentPanel(maze);
        infoLabel = new InfoLabel(maze);
        scrollPane = new JScrollPane(contentPanel);
        

        this.setLayout(new BorderLayout());
        this.add(scrollPane);
        this.add(infoLabel, BorderLayout.SOUTH);
    }

    public InfoLabel getInfoLabel(){
        return this.infoLabel;
    }

    public ContentPanel getContentPanel(){
        return this.contentPanel;
    }
    
}
