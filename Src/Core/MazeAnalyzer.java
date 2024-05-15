package Core;
import java.io.File;

import GUI.CustomPanel;

public class MazeAnalyzer {

    private int start;
    private int end;

    private Maze maze;
    private Graph graph;
    private CustomPanel customPanel;

    private int G;
    private int L;
    private int P;
    private int D;
    private int h;

    private int currentRows;

    public int analyzeMaze(File file, String type, CustomPanel customPanel, Maze maze, Graph graph) {

        this.maze = maze;
        this.graph = graph;
        this.customPanel = customPanel;

        char[][] x = new char[3][maze.getColumns()];

        this.start = -2;
        this.end = -2;

        Analyze(file, x);

        return 0;
    }

    private void Analyze(File file, char[][] x) {

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

                            this.start = graph.getCurrentNodes();
                            maze.setStart(i, currentRows - 1);
                            this.customPanel.changeStartPos(i, currentRows);

                        } else {

                            this.end = graph.getCurrentNodes();

                            maze.setEnd(i, currentRows - 1);
                            this.customPanel.changeEndPos(i, currentRows);

                        }

                        graph.addNode();

                        Numbers[i] = 0;
                        ID[i] = graph.getCurrentNodes();

                        graph.setNodeValue(graph.getCurrentNodes(), 8, i); // x
                        graph.setNodeValue(graph.getCurrentNodes(), 9, currentRows - 1); // y

                        graph.addCurrentNodes();
                        graph.addNode();
                    }

                    G++;
                    h++;

                }
                // lewo
                if (x[1][i - 1] == ' ' || x[1][i - 1] == 'P' || x[1][i - 1] == 'K') {

                    if (x[1][i - 1] == 'P' || x[1][i - 1] == 'K') {

                        if (x[1][i - 1] == 'P') {

                            this.start = graph.getCurrentNodes();
                            maze.setStart(i - 1, currentRows);
                            this.customPanel.changeStartPos(i, currentRows);

                        } else {

                            this.end = graph.getCurrentNodes();
                            maze.setEnd(i - 1, currentRows);
                            this.customPanel.changeEndPos(i, currentRows);

                        }

                        graph.addNode();

                        Numbers[i - 1] = 0;
                        ID[i - 1] = graph.getCurrentNodes();

                        graph.setNodeValue(graph.getCurrentNodes(), 8, i - 1); // x
                        graph.setNodeValue(graph.getCurrentNodes(), 9, currentRows); // y

                        graph.addCurrentNodes();
                        graph.addNode();

                    }

                    L++;
                    h++;

                }
                // dół
                if (x[2][i] == ' ' || x[2][i] == 'P' || x[2][i] == 'K') {

                    if (x[2][i] == 'P' || x[2][i] == 'K') {

                        if (x[2][i] == 'P') {

                            this.start = graph.getCurrentNodes();
                            maze.setStart(i, currentRows + 1);
                            this.customPanel.changeStartPos(i, currentRows);

                        } else {

                            this.end = graph.getCurrentNodes();
                            maze.setEnd(i, currentRows + 1);
                            this.customPanel.changeEndPos(i, currentRows);

                        }

                        graph.addNode();

                        graph.setNodeValue(graph.getCurrentNodes(), 0, graph.getCurrentNodes() + 1);
                        graph.setNodeValue(graph.getCurrentNodes(), 1, 1);

                        graph.setNodeValue(graph.getCurrentNodes(), 8, i);
                        graph.setNodeValue(graph.getCurrentNodes(), 9, currentRows + 1);

                        graph.addCurrentNodes();
                        graph.addNode();

                        graph.setNodeValue(graph.getCurrentNodes(), 4, graph.getCurrentNodes() - 1);
                        graph.setNodeValue(graph.getCurrentNodes(), 5, 1);

                    }

                    D++;
                    h++;

                }
                // prawo
                if (x[1][i + 1] == ' ' || x[1][i + 1] == 'P' || x[1][i + 1] == 'K') {

                    if (x[1][i + 1] == 'P' || x[1][i + 1] == 'K') {

                        if (x[1][i + 1] == 'P') {

                            this.start = graph.getCurrentNodes();
                            maze.setStart(i + 1, currentRows);
                            this.customPanel.changeStartPos(i, currentRows);

                        } else {

                            this.end = graph.getCurrentNodes();
                            maze.setEnd(i + 1, currentRows);
                            this.customPanel.changeEndPos(i, currentRows);

                        }

                        graph.addNode();

                        graph.setNodeValue(graph.getCurrentNodes(), 6, graph.getCurrentNodes() + 1);
                        graph.setNodeValue(graph.getCurrentNodes(), 7, 1);

                        Numbers[i + 1] = 0;

                        ID[i + 1] = graph.getCurrentNodes();

                        graph.setNodeValue(graph.getCurrentNodes(), 8, i + 1 );
                        graph.setNodeValue(graph.getCurrentNodes(), 9, currentRows);

                        graph.addCurrentNodes();
                        graph.addNode();

                        graph.setNodeValue(graph.getCurrentNodes(), 2, graph.getCurrentNodes() - 1);
                        graph.setNodeValue(graph.getCurrentNodes(), 3, 1);
                    }

                    P++;
                    h++;

                }

                // jeśli node (skręt, zaułek, rozdroże w labiryncie)
                if (h == 1 || h >= 3 || h == 2 && ((G == 1 || D == 1) && (L == 1 || P == 1))) {

                    Link(Numbers, ID, i, G, L);

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

    private void Link(int[] Numbers, int[] ID, int i, int G, int L) {

        if (start != graph.getCurrentNodes() - 1 && end != graph.getCurrentNodes() - 1) {
            graph.addNode();
        }
        if (G == 1) {

            graph.setNodeValue(graph.getCurrentNodes(), 1, Numbers[i] + 1);
            graph.setNodeValue(graph.getCurrentNodes(), 0, ID[i]);

            graph.setNodeValue(ID[i], 5, Numbers[i] + 1);
            graph.setNodeValue(ID[i], 4, this.graph.getCurrentNodes());

            ID[i] = this.graph.getCurrentNodes();
            Numbers[i] = 0;

        }

        if (L == 1) {

            ID[i] = this.graph.getCurrentNodes();
            Numbers[i] = 0;

            graph.setNodeValue(graph.getCurrentNodes(), 7, Numbers[i - 1] + 1);
            graph.setNodeValue(graph.getCurrentNodes(), 6, ID[i - 1]);

            graph.setNodeValue(ID[i - 1], 3, Numbers[i - 1] + 1);
            graph.setNodeValue(ID[i - 1], 2, this.graph.getCurrentNodes());

        }

        if (G != 1 && L != 1) {
            ID[i] = this.graph.getCurrentNodes();
            Numbers[i] = 0;
        }

        graph.setNodeValue(graph.getCurrentNodes(), 8, i);
        graph.setNodeValue(graph.getCurrentNodes(), 9, currentRows);

        graph.addCurrentNodes();

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

        checkIfEqual(maze.getCharFromMaze(custom[0], custom[1] - 1), maze.getCharFromMaze(custom[0] - 1, custom[1]),
                maze.getCharFromMaze(custom[0], custom[1] + 1), maze.getCharFromMaze(custom[0] + 1, custom[1]), ' ');

        if (h == 1 || (h == 2 && ((G == 1 || D == 1) && (L == 1 || P == 1))) || h >= 3) { // zaulek

            d = SearchForNode(custom[0], custom[1]);
            if (c == 'S') {
                this.start = d;
            } else {
                end = d;
            }

        } else {

            if (c == 'S' && start != -2) {

                graph.setNodeValue(start, 8, custom[0]);
                graph.setNodeValue(start, 9, custom[1]);
                object = start;
            } else if (c == 'E' && end != -2) {

                graph.setNodeValue(end, 8, custom[0]);
                graph.setNodeValue(end, 9, custom[1]);

                object = end;

            } else if (c == 'S') {

                graph.addNode();
                graph.setNodeValue(graph.getCurrentNodes(), 8, custom[0] + 1);
                graph.setNodeValue(graph.getCurrentNodes(), 9, custom[1]);
                start = graph.getCurrentNodes();
                graph.addCurrentNodes();

                object = start;

            } else if (c == 'E') {

                graph.addNode();
                graph.setNodeValue(graph.getCurrentNodes(), 8, custom[0] + 1);
                graph.setNodeValue(graph.getCurrentNodes(), 9, custom[1]);
                end = graph.getCurrentNodes();
                graph.addCurrentNodes();

                object = end;

            }

            if (h == 2 && P == 1) { // poziom
                z = new int[] { 6, 7, 2, 3, 2, 3 };
            } else { // pion
                z = new int[] { 0, 1, 4, 5, 0, 1 };
            }

            ID1 = searchCustom(z[4], custom, c);
            d = SearchForNode((ID1[0]), ID1[1]);

            graph.setNodeValue(object, z[0], d);
            graph.setNodeValue(object, z[1], ID1[2]);

            graph.setNodeValue(d, z[2], object);
            graph.setNodeValue(d, z[3], ID1[2]);

            ID2 = searchCustom(z[5], custom, c);
            d = SearchForNode((ID2[0]), ID2[1]);

            graph.setNodeValue(object, z[2], d);
            graph.setNodeValue(object, z[3], ID2[2]);

            graph.setNodeValue(d, z[0], object);
            graph.setNodeValue(d, z[1], ID2[2]);

        }
    }

    private int[] searchCustom(int mod, int[] custom, char c) {

        Integer x = 0;
        int distance = 0;
        int[] z = new int[3];
        int[] customSave = new int[] { custom[0], custom[1] };

        while (x != 1) {

            checkIfEqual(maze.getCharFromMaze(customSave[0], customSave[1] - 1),
                    maze.getCharFromMaze(customSave[0] - 1, customSave[1]),
                    maze.getCharFromMaze(customSave[0], customSave[1] + 1),
                    maze.getCharFromMaze(customSave[0] + 1, customSave[1]), ' ');

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

    private int SearchForNode(int x, int y) {

        int i = 0;
        Integer X = x;
        Integer Y = y;

        while ( !(graph.getNodeValue(i, 8)).equals(X) || !(graph.getNodeValue(i, 9)).equals(Y)) {

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
            if (graph.getNodeValue(obj, y) != null) {
                switch (y) {
                    case 0:
                        graph.setNodeValue(graph.getNodeValue(obj, y), 4, null);
                        graph.setNodeValue(graph.getNodeValue(obj, y), 5, null);
                        break;
                    case 2:
                        graph.setNodeValue(graph.getNodeValue(obj, y), 6, null);
                        graph.setNodeValue(graph.getNodeValue(obj, y), 7, null);
                        break;
                    case 4:
                        graph.setNodeValue(graph.getNodeValue(obj, y), 0, null);
                        graph.setNodeValue(graph.getNodeValue(obj, y), 1, null);
                        break;
                    case 6:
                        graph.setNodeValue(graph.getNodeValue(obj, y), 2, null);
                        graph.setNodeValue(graph.getNodeValue(obj, y), 3, null);
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
            graph.setNodeValue(obj, i, null);
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
