import java.util.ArrayList;

public class MazeSolver {

    int Start;
    int End;
    Integer ID_now;
    int length_now;
    int length_min;
    Integer ID_next;
    int p = 0;
    ArrayList<Node> nodeMap;
    private Graph graph;

    public boolean[] save;

    public int solveMaze(Graph graph, int Start, int End) {

        this.graph = graph;

        this.Start = Start;
        this.End = End;
        nodeMap = new ArrayList<>();

        for (int i = 0; i < graph.getNodes().size(); i++) {

            nodeMap.add(new Node(i));

        }

        this.save = new boolean[nodeMap.size()];

        solve();

        return 0;
    }

    private void solve() {

        ID_next = Start;

        while (p != 1) {

            ID_now = ID_next;
            nodeMap.get(ID_now).visited = true;

            ID_next = whichNext();
            if (ID_next == -1) {
                endDeadEnd();
            }

        }

    }

    private int whichNext() {

        int y = nodeMap.get(ID_now).directionToMin * 2;
        Integer ID_n = null;

        while (y != 8) {
            ID_n = graph.getNodeValue(ID_now, y);
            // następny nieodwiedzony, a długość połączenia nie równa 0;
            if (ID_n != null) {
                if (nodeMap.get(ID_n).visited == false) {
                    break;
                }
            }
            y += 2;
        }

        if (y == 8 || ID_now == End) {
            ID_n = -1;
        } else {
            length_now += graph.getNodeValue(ID_now, y + 1);
            nodeMap.get(ID_n).directionFrom = searchForDirection(ID_n);
        }
        return ID_n;

    }

    private void endDeadEnd() {

        if (ID_now != Start) {
            if (ID_now == End) {
                if (length_min == 0 || length_now > length_min) {

                    length_min = length_now;
                    saveSolution();

                }
            }
            
            length_now -= graph.getNodeValue(ID_now, ((int) nodeMap.get(ID_now).directionFrom) * 2 + 1);
            ID_next = graph.getNodeValue(ID_now, (int) nodeMap.get(ID_now).directionFrom * 2);

            nodeMap.get(ID_next).directionToMin = searchForDirection(ID_next) + 1;
            nodeMap.get(ID_now).visited = false;

        } else {

            this.p = 1;
        }
    }

    private int searchForDirection(int ID_next) {

        int i = 0;
        Integer ID = graph.getNodeValue(ID_next, i);
        while (ID_now != ID) {
            i += 2;
            ID = graph.getNodeValue(ID_next, i);
        }

        return i / 2;
    }

    public void saveSolution() {

        for (int i = 0; i < nodeMap.size(); i++) {
            this.save[i] = nodeMap.get(i).visited;
        }
    }

}
