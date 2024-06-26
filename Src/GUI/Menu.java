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

import Core.Main;
import Core.Watched;

public class Menu extends JMenuBar implements ActionListener {

    private JMenu fileMenu;
    private JMenu helpMenu;
    private Frame frame;
    private OuterContentPanel outerContentPanel;
    private Watched watched;

    private JMenuItem loadItem;
    private JMenuItem exportItem;
    private JMenuItem helpItem;

    private File file;
    private File fileToSave;

    private String fileType;

    Menu(Frame frame, Watched watched, OuterContentPanel outerContentPanel) {

        this.frame = frame;
        this.watched = watched;
        this.outerContentPanel = outerContentPanel;

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
                Main.setFilePath(file.getAbsolutePath());
                Main.setFileType(file.getAbsolutePath().substring(file.getAbsolutePath().length() - 3));
                this.file = file;
                this.watched.setMessage("gotFile");
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
                fileToSave = fileChooser.getSelectedFile();
                if (!fileToSave.getAbsolutePath().toLowerCase().endsWith(".bin")) {
                    fileToSave = new File(fileToSave.getAbsolutePath() + ".bin");
                }

                try {
                    FileWriter writer = new FileWriter(fileToSave);
                    writer.write("I can write in this file!");
                    writer.close();
                    Main.setFilePathToExport(fileToSave.getAbsolutePath());
                    watched.setMessage("export");

                } catch (IOException z) {
                    JOptionPane.showMessageDialog(frame, "An error occured while trying to export the solution: " + z.getMessage());
                }
            }
        }

        if (e.getSource() == helpItem) {

            outerContentPanel.getContentPanel().setHelpEnabled();
        }
    }

    public void setLoadEnabled(boolean x) {
        loadItem.setEnabled(x);
    }

    public void setExportEnabled(boolean x){
        exportItem.setEnabled(x);
    }

    public File getFile(){
        return this.file;
    }

    public String getFileType(){
        return this.fileType;
    }

}
