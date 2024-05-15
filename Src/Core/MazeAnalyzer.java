package Core;
import java.io.File;

import GUI.ChooseStartEndPanel;

public class MazeAnalyzer {

    private int start;
    private int end;

    private Maze maze;
    private Graph graph;
    private ChooseStartEndPanel chooseStartEndPanel;

    private int G;
    private int L;
    private int P;
    private int D;
    private int h;

    private int currentRows;

    public int analyzeMaze(File file, String type, ChooseStartEndPanel chooseStartEndPanel, Maze maze, Graph graph) {

        this.maze = maze;
        this.graph = graph;
        this.chooseStartEndPanel = chooseStartEndPanel;

        char[][] x = new char[3][maze.getColumns()];

        this.start = -2;
        this.end = -2;

        analyze(file, x);

        return 0;
    }

    private void analyze(File file, char[][] x) {

        int[] ID = new int[maze.getColumns()];
        int[] Numbers = new int[maze.getColumns()];
        this.currentRows = 0;

        for (int i = 0; i < maze.getColumns(); i++) {

            ID[i] = -1;
            Numbers[i] = -1;
        }

        for (int i = 0; i < 3; i++) {

            System.arraycopy(maze.getMaze()[i], 0, x[i], 0, maze.getColumns());

        }
        currentRows = 1;
        ifNode(x, ID, Numbers);
        currentRows++;

        for (int i = 0; i < maze.getRows() - 3; i++) {

            
            // Przepisanie na wyższe wiersze w x
            System.arraycopy(x[1], 0, x[0], 0, maze.getColumns());
            System.arraycopy(x[2], 0, x[1], 0, maze.getColumns());

            System.arraycopy(maze.getMaze()[i + 3], 0, x[2], 0, maze.getColumns());
            ifNode(x, ID, Numbers);
            currentRows++;
        }

    }

    private void ifNode(char[][] x, int[] ID, int[] Numbers) {

        for (int i = 1; i < maze.getColumns() - 1; i++) {

            if (x[1][i] == ' ') {

                G = 0;
                P = 0;
                D = 0;
                L = 0;
                h = 0;

                // góra
                if (x[0][i] == ' ' || x[0][i] == 'P' || x[0][i] == 'K') {

                    if (x[0][i] == 'P' || x[0][i] == 'K') {

                        if (x[0][i] == 'P') {

                            this.start = graph.getCurrentNodesConnectionsNumber();
                            maze.setStart(i, currentRows - 1);
                            this.chooseStartEndPanel.changeStartPos(i, currentRows);

                        } else {

                            this.end = graph.getCurrentNodesConnectionsNumber();

                            maze.setEnd(i, currentRows - 1);
                            this.chooseStartEndPanel.changeEndPos(i, currentRows);

                        }

                        graph.addNodeConnection();

                        Numbers[i] = 0;
                        ID[i] = graph.getCurrentNodesConnectionsNumber();

                        graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 8, i); // x
                        graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 9, currentRows - 1); // y

                        graph.increaseCurrentNodesConnectionsNumber();
                        graph.addNodeConnection();
                    }

                    G++;
                    h++;

                }
                // lewo
                if (x[1][i - 1] == ' ' || x[1][i - 1] == 'P' || x[1][i - 1] == 'K') {

                    if (x[1][i - 1] == 'P' || x[1][i - 1] == 'K') {

                        if (x[1][i - 1] == 'P') {

                            this.start = graph.getCurrentNodesConnectionsNumber();
                            maze.setStart(i - 1, currentRows);
                            this.chooseStartEndPanel.changeStartPos(i, currentRows);

                        } else {

                            this.end = graph.getCurrentNodesConnectionsNumber();
                            maze.setEnd(i - 1, currentRows);
                            this.chooseStartEndPanel.changeEndPos(i, currentRows);

                        }

                        graph.addNodeConnection();

                        Numbers[i - 1] = 0;
                        ID[i - 1] = graph.getCurrentNodesConnectionsNumber();

                        graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 8, i - 1); // x
                        graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 9, currentRows); // y

                        graph.increaseCurrentNodesConnectionsNumber();
                        graph.addNodeConnection();

                    }

                    L++;
                    h++;

                }
                // dół
                if (x[2][i] == ' ' || x[2][i] == 'P' || x[2][i] == 'K') {

                    if (x[2][i] == 'P' || x[2][i] == 'K') {

                        if (x[2][i] == 'P') {

                            this.start = graph.getCurrentNodesConnectionsNumber();
                            maze.setStart(i, currentRows + 1);
                            this.chooseStartEndPanel.changeStartPos(i, currentRows);

                        } else {

                            this.end = graph.getCurrentNodesConnectionsNumber();
                            maze.setEnd(i, currentRows + 1);
                            this.chooseStartEndPanel.changeEndPos(i, currentRows);

                        }

                        graph.addNodeConnection();

                        graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 0, graph.getCurrentNodesConnectionsNumber() + 1);
                        graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 1, 1);

                        graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 8, i);
                        graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 9, currentRows + 1);

                        graph.increaseCurrentNodesConnectionsNumber();
                        graph.addNodeConnection();

                        graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 4, graph.getCurrentNodesConnectionsNumber() - 1);
                        graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 5, 1);

                    }

                    D++;
                    h++;

                }
                // prawo
                if (x[1][i + 1] == ' ' || x[1][i + 1] == 'P' || x[1][i + 1] == 'K') {

                    if (x[1][i + 1] == 'P' || x[1][i + 1] == 'K') {

                        if (x[1][i + 1] == 'P') {

                            this.start = graph.getCurrentNodesConnectionsNumber();
                            maze.setStart(i + 1, currentRows);
                            this.chooseStartEndPanel.changeStartPos(i, currentRows);

                        } else {

                            this.end = graph.getCurrentNodesConnectionsNumber();
                            maze.setEnd(i + 1, currentRows);
                            this.chooseStartEndPanel.changeEndPos(i, currentRows);

                        }

                        graph.addNodeConnection();

                        graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 6, graph.getCurrentNodesConnectionsNumber() + 1);
                        graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 7, 1);

                        Numbers[i + 1] = 0;

                        ID[i + 1] = graph.getCurrentNodesConnectionsNumber();

                        graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 8, i + 1 );
                        graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 9, currentRows);

                        graph.increaseCurrentNodesConnectionsNumber();
                        graph.addNodeConnection();

                        graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 2, graph.getCurrentNodesConnectionsNumber() - 1);
                        graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 3, 1);
                    }

                    P++;
                    h++;

                }

                // jeśli node (skręt, zaułek, rozdroże w labiryncie)
                if (h == 1 || h >= 3 || h == 2 && ((G == 1 || D == 1) && (L == 1 || P == 1))) {

                    link(Numbers, ID, i, G, L);

                } else { // połączenia

                    if (L == 1 && P == 1) {
                        ID[i] = ID[i - 1];
                        Numbers[i] = Numbers[i - 1] + 1;

                    } else if (G == 1 && D == 1) {
                        Numbers[i] = Numbers[i] + 1;

                    }
                }

            } else {
                ID[i] = -1;
                Numbers[i] = -1;
            }
        }

    }

    private void link(int[] Numbers, int[] ID, int i, int G, int L) {

        if (start != graph.getCurrentNodesConnectionsNumber() - 1 && end != graph.getCurrentNodesConnectionsNumber() - 1) {
            graph.addNodeConnection();
        }
        if (G == 1) {

            graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 1, Numbers[i] + 1);
            graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 0, ID[i]);

            graph.setNodeConnectionsValue(ID[i], 5, Numbers[i] + 1);
            graph.setNodeConnectionsValue(ID[i], 4, this.graph.getCurrentNodesConnectionsNumber());

            ID[i] = this.graph.getCurrentNodesConnectionsNumber();
            Numbers[i] = 0;

        }

        if (L == 1) {

            ID[i] = this.graph.getCurrentNodesConnectionsNumber();
            Numbers[i] = 0;

            graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 7, Numbers[i - 1] + 1);
            graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 6, ID[i - 1]);

            graph.setNodeConnectionsValue(ID[i - 1], 3, Numbers[i - 1] + 1);
            graph.setNodeConnectionsValue(ID[i - 1], 2, this.graph.getCurrentNodesConnectionsNumber());

        }

        if (G != 1 && L != 1) {
            ID[i] = this.graph.getCurrentNodesConnectionsNumber();
            Numbers[i] = 0;
        }

        graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 8, i);
        graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 9, currentRows);

        graph.increaseCurrentNodesConnectionsNumber();

    }

    public void customAnalyzer(int[] custom, char c) {

        int[] ID1, ID2;
        int d;
        int object = 0;
        int z[];

        if (c == 'S' && maze.getStart()[0] != null) {
            Clear(c);
        }

        if (c == 'E' && maze.getEnd()[0] != null) {
            Clear(c);
        }

        checkIfEqual(maze.getCharFromMazeInChar2DArray(custom[0], custom[1] - 1), maze.getCharFromMazeInChar2DArray(custom[0] - 1, custom[1]),
                maze.getCharFromMazeInChar2DArray(custom[0], custom[1] + 1), maze.getCharFromMazeInChar2DArray(custom[0] + 1, custom[1]), ' ');

        if (h == 1 || (h == 2 && ((G == 1 || D == 1) && (L == 1 || P == 1))) || h >= 3) { // zaulek

            d = searchForNode(custom[0], custom[1]);
            if (c == 'S') {
                this.start = d;
            } else {
                end = d;
            }

        } else {

            if (c == 'S' && start != -2) {

                graph.setNodeConnectionsValue(start, 8, custom[0]);
                graph.setNodeConnectionsValue(start, 9, custom[1]);
                object = start;
            } else if (c == 'E' && end != -2) {

                graph.setNodeConnectionsValue(end, 8, custom[0]);
                graph.setNodeConnectionsValue(end, 9, custom[1]);

                object = end;

            } else if (c == 'S') {

                graph.addNodeConnection();
                graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 8, custom[0] + 1);
                graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 9, custom[1]);
                start = graph.getCurrentNodesConnectionsNumber();
                graph.increaseCurrentNodesConnectionsNumber();

                object = start;

            } else if (c == 'E') {

                graph.addNodeConnection();
                graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 8, custom[0] + 1);
                graph.setNodeConnectionsValue(graph.getCurrentNodesConnectionsNumber(), 9, custom[1]);
                end = graph.getCurrentNodesConnectionsNumber();
                graph.increaseCurrentNodesConnectionsNumber();

                object = end;

            }

            if (h == 2 && P == 1) { // poziom
                z = new int[] { 6, 7, 2, 3, 2, 3 };
            } else { // pion
                z = new int[] { 0, 1, 4, 5, 0, 1 };
            }

            ID1 = searchCustom(z[4], custom, c);
            d = searchForNode((ID1[0]), ID1[1]);

            graph.setNodeConnectionsValue(object, z[0], d);
            graph.setNodeConnectionsValue(object, z[1], ID1[2]);

            graph.setNodeConnectionsValue(d, z[2], object);
            graph.setNodeConnectionsValue(d, z[3], ID1[2]);

            ID2 = searchCustom(z[5], custom, c);
            d = searchForNode((ID2[0]), ID2[1]);

            graph.setNodeConnectionsValue(object, z[2], d);
            graph.setNodeConnectionsValue(object, z[3], ID2[2]);

            graph.setNodeConnectionsValue(d, z[0], object);
            graph.setNodeConnectionsValue(d, z[1], ID2[2]);

        }
    }

    private int[] searchCustom(int mod, int[] custom, char c) {

        Integer x = 0;
        int distance = 0;
        int[] z = new int[3];
        int[] customSave = new int[] { custom[0], custom[1] };

        while (x != 1) {

            checkIfEqual(maze.getCharFromMazeInChar2DArray(customSave[0], customSave[1] - 1),
                    maze.getCharFromMazeInChar2DArray(customSave[0] - 1, customSave[1]),
                    maze.getCharFromMazeInChar2DArray(customSave[0], customSave[1] + 1),
                    maze.getCharFromMazeInChar2DArray(customSave[0] + 1, customSave[1]), ' ');

            if (h == 1 || (h == 2 && ((G == 1 || D == 1) && (L == 1 || P == 1))) || h >= 3) { // zaulek
                x = 1;
                z[0] = customSave[0];
                z[1] = customSave[1];
                z[2] = distance;
                return z;
            }

            switch (mod) {
                case 0:
                    customSave[1] -= 1;
                    break;
                case 1:
                    customSave[1] += 1;
                    break;
                case 2:
                    customSave[0] -= 1;
                    break;
                case 3:
                    customSave[0] += 1;
                    break;
                default:
                    break;
            }

            distance++;
        }
        // zmien na id noda
        return z;
    }

    private int searchForNode(int x, int y) {

        int i = 0;
        Integer X = x;
        Integer Y = y;

        while ( !(graph.getNodeConnectionsValue(i, 8)).equals(X) || !(graph.getNodeConnectionsValue(i, 9)).equals(Y)) {

            i++;
        }

        return i;
    }

    private void Clear(char c) {

        int y = 0;
        int obj = 0;

        if (c == 'S') {
            obj = start;
        } else {
            obj = end;
        }

        while (y != 8) {
            if (obj == -2) {
                break;
            }
            if (graph.getNodeConnectionsValue(obj, y) != null) {
                switch (y) {
                    case 0:
                        graph.setNodeConnectionsValue(graph.getNodeConnectionsValue(obj, y), 4, null);
                        graph.setNodeConnectionsValue(graph.getNodeConnectionsValue(obj, y), 5, null);
                        break;
                    case 2:
                        graph.setNodeConnectionsValue(graph.getNodeConnectionsValue(obj, y), 6, null);
                        graph.setNodeConnectionsValue(graph.getNodeConnectionsValue(obj, y), 7, null);
                        break;
                    case 4:
                        graph.setNodeConnectionsValue(graph.getNodeConnectionsValue(obj, y), 0, null);
                        graph.setNodeConnectionsValue(graph.getNodeConnectionsValue(obj, y), 1, null);
                        break;
                    case 6:
                        graph.setNodeConnectionsValue(graph.getNodeConnectionsValue(obj, y), 2, null);
                        graph.setNodeConnectionsValue(graph.getNodeConnectionsValue(obj, y), 3, null);
                        break;
                    default:
                        break;
                }
            }

            y += 2;
        }

        for (int i = 0; i < 8; i++) {
            if (obj == -2) {
                break;
            }
            graph.setNodeConnectionsValue(obj, i, null);
        }
    }

    private void checkIfEqual(char x1, char x2, char x3, char x4, int y) {

        this.G = 0;
        this.L = 0;
        this.P = 0;
        this.D = 0;
        this.h = 0;
        // góra
        if (x1 == ' ' || x1 == 'P' || x1 == 'K') {

            h++;
            G++;

        }
        // lewo
        if (x2 == ' ' || x2 == 'P' || x2 == 'K') {

            h++;
            L++;
        }
        // dół
        if (x3 == ' ' || x3 == 'P' || x3 == 'K') {

            h++;
            D++;
        }
        // prawo
        if (x4 == ' ' || x4 == 'P' || x4 == 'K') {

            P++;
            h++;
        }
    }

    public int getStart(){
        return this.start;
    }

    public int getEnd(){
        return this.end;
    }
}
