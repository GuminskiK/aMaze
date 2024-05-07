import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main {

    static MyFrame ramka;
    static MazeAnalyzer mazeAnalyzer;
    static MazeCreator mazeCreator;
    static FileReader fileReader;
    static File file;
    static int columns;
    static Color lastStartColor = new Color (0,0,0);
    static Color lastEndColor = new Color (0,0,0);
    static int[] oldCustomStart = new int[2];
    static int[] oldCustomEnd = new int[2];
    static int StartEndSwitch = 0;
    static int[] path;
    static char[][] x;
    

    public static void main (String[] args){

        ActionListener readListener = new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                Read();
            }

        };

        ActionListener analyzeListener = new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                Analyze();
            }

        };

        ActionListener shortestListener = new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                Shortest();
            }

        };

        ActionListener wholeListener = new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                Whole();
            }

        };

        ActionListener helpListener = new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                help();
            }
        };

        ActionListener customStartListener = new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                customChange('S');
            }
        };

        ActionListener customEndListener = new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                customChange('E');
            }
        };

        ActionListener customListener = new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                InOutWall();
            }
        };

        ramka = new MyFrame(readListener, analyzeListener, shortestListener, helpListener, customStartListener, customEndListener, wholeListener, customListener);
        ramka.infoLabel.setText("Proszę załadować labirynt Files->Load.");
        
        
    }

    private static void help(){
        ramka.ContentPanel.setHelpEnabled();
    }

    private static void Read(){
        
        ramka.infoLabel.setText("Ładowanie labiryntu...");
        ramka.menuBar.setloadEnabled(false);
        ramka.ToolPanel.ToolEnable(false, new int[]{0,1,2,3,4,5});

        file = ramka.menuBar.file;
        fileReader = new FileReader();

        //char [][]x =  new char[columns][fileReader.rows];
        //to nie działa do końca bo skad wezmę wymiary za nim przeczytam
        
        if (ramka.menuBar.fileType.equals("txt")){
            
            fileReader.CountRowsColumns(file);
            columns = fileReader.columns;
            x = fileReader.ReadFileTXT(file);

        } else {
            //czytanie binarne
            //inczaej wywali się program, pozdrawiam
        }

        //createMaze
        mazeCreator = new MazeCreator();

        mazeCreator.CreateMaze( x, fileReader.columns, fileReader.rows);
        path = mazeCreator.path;
        
        //rysowanie
        ramka.ContentPanel.addPanel(fileReader.columns, fileReader.rows, path);
        

        ramka.ToolPanel.ToolEnable(true, new int[]{0}); //Analyze

        StartEndSwitch = 0;

        ramka.infoLabel.setText("Proszę nacisnąć Analyze maze by przeanalizować labirynt.");

    }

    private static void Analyze(){
        
        ramka.infoLabel.setText("Analizowanie w toku...");
        ramka.ToolPanel.ToolEnable(false, new int[]{0});

        mazeAnalyzer = new MazeAnalyzer();
        mazeAnalyzer.analyzeMaze(file, fileReader.columns, fileReader.rows, ramka.menuBar.fileType, ramka.ToolPanel.customPanel);

        oldCustomStart[0] = mazeAnalyzer.StartPos/columns;
        oldCustomStart[1] = mazeAnalyzer.StartPos%columns;
        oldCustomEnd[0] = mazeAnalyzer.EndPos/columns;
        oldCustomEnd[1] = mazeAnalyzer.EndPos%columns;

        ramka.ToolPanel.ToolEnable(true, new int[]{1,2});

        ramka.infoLabel.setText("Można wybrać tryb rozwiązywania labiryntu, a także nowy Start/End");

    }

    private static void Shortest(){

        ramka.ToolPanel.ToolEnable(false, new int[]{0,1,2,3,4});
        ramka.infoLabel.setText("Szukam najkrótszego rozwiązania labiryntu...");
        MazeSolver mazeSolver = new MazeSolver();
        mazeSolver.solveMaze(mazeAnalyzer.nodes, mazeAnalyzer.Start, mazeAnalyzer.End, 0);

        SolutionWriter solutionWriter = new SolutionWriter();
        solutionWriter.WriteSolution(mazeSolver.save, path, mazeAnalyzer.Start, mazeAnalyzer.End ,mazeAnalyzer.nodes, fileReader.columns, mazeAnalyzer.StartPos, mazeAnalyzer.EndPos);
        ramka.ContentPanel.mazePanel.rePaint(1, path);
        ramka.menuBar.setloadEnabled(true);
        ramka.infoLabel.setText("Znaleziono najkrótsze rozwiązanie labiryntu, by załadować inny labirynt wybierz Files->Load");
    }

    private static void Whole(){

        ramka.ToolPanel.ToolEnable(false, new int[]{0,1,2,3,4});

        SolutionWriterWhole solutionWriterWhole = new SolutionWriterWhole();
        solutionWriterWhole.solveMaze(mazeAnalyzer.nodes, mazeAnalyzer.Start, mazeAnalyzer.End, 1, path, columns, mazeAnalyzer.StartPos, mazeAnalyzer.EndPos);
        ramka.ContentPanel.mazePanel.rePaint(1, path);
        ramka.menuBar.setloadEnabled(true);

    }

    private static void customChange( char c){

        int colorNr;
        int[] switchSE;
        int[] customObject = new int[2];

        if (c == 'S'){
            customObject[0] = ramka.ContentPanel.customStart[0];
            customObject[1] = ramka.ContentPanel.customStart[1];
        } else {
            customObject[0] = ramka.ContentPanel.customEnd[0];
            customObject[1] = ramka.ContentPanel.customEnd[1];
        }

        if (mazeCreator.path[columns * customObject[1] + customObject[0]] == 1){
            
            if (c == 'S'){

                colorNr = 3;
                switchSE = new int[]{4,5};
                ramka.ToolPanel.customPanel.changeStartPos(customObject[0], customObject[1]);
                ramka.infoLabel.setText("Wybrano nowy Start. Trwa jego lokalizowanie...");

            } else {

                colorNr = 4;
                switchSE = new int[]{5,4};
                ramka.ToolPanel.customPanel.changeEndPos(customObject[0], customObject[1]);
                ramka.infoLabel.setText("Wybrano nowy End. Trwa jego lokalizowanie...");

            }

            ramka.ToolPanel.ToolEnable(false, new int[] {2});

            mazeAnalyzer.customAnalyzer( path, customObject , columns, c);

            path[columns * customObject[1] + customObject[0]] = colorNr;
            ramka.ContentPanel.mazePanel.rePaint(1, path);

            if (c == 'S'){
                mazeAnalyzer.StartPos = columns * customObject[1] + customObject[0];
            } else {
                mazeAnalyzer.EndPos = columns * customObject[1] + customObject[0];
            }
            
            ramka.ToolPanel.ToolEnable(true, new int[]{2});

            StartEndSwitch++;

            if (StartEndSwitch == 2){
                ramka.ToolPanel.ToolEnable(false, switchSE);
            } else {
                ramka.ToolPanel.ToolEnable(false, new int[]{switchSE[0]});
                ramka.ToolPanel.ToolEnable(true, new int[]{switchSE[1]});
            }

            if (c == 'S'){
                if (StartEndSwitch == 2){
                    ramka.infoLabel.setText("Wybierz tryb rozwiązywania.");
                } else {
                    ramka.infoLabel.setText("Wybór Startu zakończył się pomyślnie. Możesz teraz wybrać End (PickEnd) lub tryb rozwiązywania.");
                }
                

            } else {
                if (StartEndSwitch == 2){
                    ramka.infoLabel.setText("Wybierz tryb rozwiązywania.");
                } else {
                    ramka.infoLabel.setText("Wybór Endu zakończył się pomyślnie. Możesz teraz wybrać Start (PickStart) lub tryb rozwiązywania.");
                }
            }


        } else {

            if (c == 'S'){
                ramka.ContentPanel.customStart[0] = oldCustomStart[0];
                ramka.ContentPanel.customStart[1] = oldCustomStart[1];
                ramka.customError();
                ramka.ToolPanel.ToolEnable(true, new int[]{4});
            } else {
                ramka.ContentPanel.customEnd[0] = oldCustomEnd[0];
                ramka.ContentPanel.customEnd[1] = oldCustomEnd[1];
                ramka.customError();
                ramka.ToolPanel.ToolEnable(true, new int[]{5});
            }
             
        }
    }

    static void InOutWall(){

        path[mazeAnalyzer.StartPos] = 0;
        path[mazeAnalyzer.EndPos] = 0;
        
        ramka.ContentPanel.mazePanel.rePaint(1, path);
/* 
        if( StartEndSwitch == 0){
            ramka.ToolPanel.ToolEnable(true, new int[]{2,3});
        }
*/
        ramka.ToolPanel.ToolEnable(false, new int[]{1});
        
    }
}
