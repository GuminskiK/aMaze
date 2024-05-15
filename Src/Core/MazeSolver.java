package Core;

import java.util.ArrayList;

public class MazeSolver {

    private int start;
    private int end;
    private Integer idNow;
    private int lengthNow;
    private int lengthMin;
    private Integer idNext;
    private int p = 0;

    private ArrayList<Node> nodeMap;
    private ArrayList<SolutionBlock> solutionBlocks;

    private Graph graph;

    private ArrayList<SolutionBlock> solution;

    private int mode;

    public int solveMaze(Graph graph, int start, int end, int mode) {

        this.graph = graph;
        this.mode = mode;
        this.start = start;
        this.end = end;

        nodeMap = new ArrayList<>();
        solutionBlocks = new ArrayList<>();

        for (int i = 0; i < graph.getNodes().size(); i++) {

            nodeMap.add(new Node(i));

        }

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
        Integer idNext = null;

        while (y != 8) {
            idNext = graph.getNodeValue(idNow, y);
            // następny nieodwiedzony, a długość połączenia nie równa 0;
            if (idNext != null) {
                if (nodeMap.get(idNext).visited == false) {
                    break;
                }
            }
            y += 2;
        }

        if (y == 8 || idNow == end) {
            idNext = -1;
        } else {
            lengthNow += graph.getNodeValue(idNow, y + 1);
            nodeMap.get(idNext).directionFrom = searchForDirection(idNext, idNow);
            solutionBlocks.add(new SolutionBlock(idNow, idNext, graph.getNodeValue(idNow, y + 1), searchForDirection(idNow, idNext)));
        }
        return idNext;

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
            solutionBlocks.remove(solutionBlocks.size() - 1);
            idNext = graph.getNodeValue(idNow, (int) nodeMap.get(idNow).directionFrom * 2);

            if (mode == 0){ //shortest
                nodeMap.get(idNext).directionToMin = searchForDirection(idNext, idNow) + 1;
                nodeMap.get(idNow).visited = false; 
            } else { //whole
                nodeMap.get(idNext).directionToMin = searchForDirection(idNext, idNow);
            }
            

        } else {

            this.p = 1;
        }
    }

    private int searchForDirection(Integer idNext, Integer idNow) {

        int i = 0;
        Integer ID = graph.getNodeValue(idNext, i);
        while ( !idNow.equals(ID)) {
            i += 2;
            ID = graph.getNodeValue(idNext, i);
        }

        return i / 2;
    }

    public void saveSolution() {

        solution = new ArrayList<>();
        for (SolutionBlock solutionBlock : solutionBlocks) {
            try {
                solution.add((SolutionBlock) solutionBlock.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

    }

    public ArrayList<SolutionBlock> getSolution(){
        return this.solution;
    }

}
