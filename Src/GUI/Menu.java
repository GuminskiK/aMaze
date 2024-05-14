package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Menu extends JMenuBar implements ActionListener {

    private JMenu fileMenu;
    private JMenu helpMenu;
    private MyFrame frame;

    private JMenuItem loadItem;
    private JMenuItem exportItem;
    private JMenuItem helpItem;

    private File file;
    private File fileToSave;
    private Integer done;

    private ActionListener listener;
    private ActionListener helpListener;

    private String fileType;

    Menu(ActionListener listener, ActionListener helpListener, MyFrame frame) {

        done = 0;
        this.frame = frame;
        this.listener = listener;
        this.helpListener = helpListener;

        fileMenu = new JMenu("File");
        helpMenu = new JMenu("Help");

        loadItem = new JMenuItem("Load Maze");
        exportItem = new JMenuItem("Export Solution");
        helpItem = new JMenuItem("Help");

        loadItem.addActionListener(this);
        exportItem.addActionListener(this);
        helpItem.addActionListener(this);

        fileMenu.add(loadItem);
        fileMenu.add(exportItem);
        helpMenu.add(helpItem);

        this.add(fileMenu);
        this.add(helpMenu);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == loadItem) {

            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filtertxt = new FileNameExtensionFilter("Text files", "txt");
            FileNameExtensionFilter filterbin = new FileNameExtensionFilter("Binary files", "bin");

            fileChooser.setCurrentDirectory(new File("./Files"));
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(filtertxt);
            fileChooser.addChoosableFileFilter(filterbin);

            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                this.fileType = file.getAbsolutePath().substring(file.getAbsolutePath().length() - 3);
                this.file = file;
                this.done = 1;
                this.listener.actionPerformed(e);
            }

        }

        if (e.getSource() == exportItem) {

            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filterbin = new FileNameExtensionFilter("Binary files", "bin");

            fileChooser.setCurrentDirectory(new File("./Files"));
            fileChooser.setDialogTitle("Export Solution");
            fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
            fileChooser.setApproveButtonText("Export");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(filterbin);

            int response = fileChooser.showSaveDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                if (!fileToSave.getAbsolutePath().toLowerCase().endsWith(".bin")) {
                    fileToSave = new File(fileToSave.getAbsolutePath() + ".bin");
                }

                this.fileToSave = fileToSave;
                try {
                    FileWriter writer = new FileWriter(fileToSave);
                    writer.write("Piszę w pliku");
                    writer.close();
                    JOptionPane.showMessageDialog(frame, "Plik wyeksportowany pomyślnie!");

                } catch (IOException z) {
                    JOptionPane.showMessageDialog(frame, "Wystąpił błąd podczas próby eksportowania pliku: " + z.getMessage());
                }
            }
        }

        if (e.getSource() == helpItem) {

            helpListener.actionPerformed(e);
        }
    }

    public void setloadEnabled(boolean x) {
        loadItem.setEnabled(x);
    }

    public void setexportEnabled(boolean x){
        exportItem.setEnabled(x);
    }

    public File getFile(){
        return this.file;
    }

    public String getFileType(){
        return this.fileType;
    }

}
