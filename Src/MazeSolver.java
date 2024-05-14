import java.util.ArrayList;

public class MazeSolver {

    int start;
    int end;
    Integer idNow;
    int lengthNow;
    int lengthMin;
    Integer idNext;
    int p = 0;
    ArrayList<Node> nodeMap;
    private Graph graph;

    public boolean[] save;

    public int solveMaze(Graph graph, int start, int end) {

        this.graph = graph;

        this.start = start;
        this.end = end;
        nodeMap = new ArrayList<>();

        for (int i = 0; i < graph.getNodes().size(); i++) {

            nodeMap.add(new Node(i));

        }

        this.save = new boolean[nodeMap.size()];

        solve();

        return 0;
    }

    private void solve() {

        idNext = start;

        while (p != 1) {

            idNow = idNext;
            nodeMap.get(idNow).visited = true;

            idNext = whichNext();
            if (idNext == -1) {
                endDeadEnd();
            }

        }

    }

    private int whichNext() {

        int y = nodeMap.get(idNow).directionToMin * 2;
        Integer ID_n = null;

        while (y != 8) {
            ID_n = graph.getNodeValue(idNow, y);
            // następny nieodwiedzony, a długość połączenia nie równa 0;
            if (ID_n != null) {
                if (nodeMap.get(ID_n).visited == false) {
                    break;
                }
            }
            y += 2;
        }

        if (y == 8 || idNow == end) {
            ID_n = -1;
        } else {
            lengthNow += graph.getNodeValue(idNow, y + 1);
            nodeMap.get(ID_n).directionFrom = searchForDirection(ID_n);
        }
        return ID_n;

    }

    private void endDeadEnd() {

        if (idNow != start) {
            if (idNow == end) {
                if (lengthMin == 0 || lengthNow > lengthMin) {

                    lengthMin = lengthNow;
                    saveSolution();

                }
            }
            
            lengthNow -= graph.getNodeValue(idNow, ((int) nodeMap.get(idNow).directionFrom) * 2 + 1);
            idNext = graph.getNodeValue(idNow, (int) nodeMap.get(idNow).directionFrom * 2);

            nodeMap.get(idNext).directionToMin = searchForDirection(idNext) + 1;
            nodeMap.get(idNow).visited = false;

        } else {

            this.p = 1;
        }
    }

    private int searchForDirection(int idNext) {

        int i = 0;
        Integer ID = graph.getNodeValue(idNext, i);
        while (idNow != ID) {
            i += 2;
            ID = graph.getNodeValue(idNext, i);
        }

        return i / 2;
    }

    public void saveSolution() {

        for (int i = 0; i < nodeMap.size(); i++) {
            this.save[i] = nodeMap.get(i).visited;
        }
    }

}
