import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Menu extends JMenuBar implements ActionListener {

    JMenu fileMenu;
    JMenu helpMenu;

    public JMenuItem loadItem;
    JMenuItem helpItem;

    public File file;
    public Integer done;

    ActionListener listener;
    ActionListener helpListener;

    String fileType;


    Menu(ActionListener listener, ActionListener helpListener){

        done = 0;
        this.listener = listener;
        this.helpListener = helpListener;

        fileMenu = new JMenu("File");
        helpMenu = new JMenu("Help");

        loadItem = new JMenuItem("Load");
        helpItem = new JMenuItem("Help");

        loadItem.addActionListener(this);
        helpItem.addActionListener(this);

        fileMenu.setMnemonic(KeyEvent.VK_F); // alt + F
        helpMenu.setMnemonic(KeyEvent.VK_H); // alt + H
        loadItem.setMnemonic(KeyEvent.VK_L); //L for load

        fileMenu.add(loadItem);
        helpMenu.add(helpItem);

        this.add(fileMenu);
        this.add(helpMenu);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == loadItem){
            
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filtertxt = new FileNameExtensionFilter("Text files", "txt");
            FileNameExtensionFilter filterbin = new FileNameExtensionFilter("Binary files", "bin");

            fileChooser.setCurrentDirectory(new File("./Files"));
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(filtertxt);
            fileChooser.addChoosableFileFilter(filterbin);

            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                this.fileType = file.getAbsolutePath().substring(file.getAbsolutePath().length() - 3);
                this.file = file;
                this.done = 1;
                this.listener.actionPerformed(e);
            }

        }

        if(e.getSource() == helpItem){
            
            helpListener.actionPerformed(e);
        }
    }

    public void setloadEnabled(boolean x){
        loadItem.setEnabled(x);
    }
    
    
}
