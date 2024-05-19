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

    MazeSolver(Graph graph, int start, int end, int mode){
        
        this.graph = graph;
        this.mode = mode;
        this.start = start;
        this.end = end;

        nodeMap = new ArrayList<>();
        solutionBlocks = new ArrayList<>();
    }

    public void solveMaze() {

        
        for (int i = 0; i < graph.getNodesConnections().size(); i++) {

            nodeMap.add(new Node());

        }

        solve();
        
        if(mode == 1){
            saveSolution();
        }
    }

    private void solve() {

        idNext = start;

        while (p != 1) {

            idNow = idNext;
            nodeMap.get(idNow).setVisited(true);

            idNext = whichNext();
            if (idNext == -1) {
                endDeadEnd();
            }

        }

    }

    private int whichNext() {

        int y = nodeMap.get(idNow).getDirectionToMin() * 2;
        Integer idNext = null;

        while (y != 8) {
            idNext = graph.getNodeConnectionsValue(idNow, y);
            // następny nieodwiedzony, a długość połączenia nie równa 0;
            if (idNext != null) {
                if (nodeMap.get(idNext).getVisited() == false) {
                    break;
                }
            }
            y += 2;
        }

        if (y == 8 || idNow == end) {
            idNext = -1;
        } else {
            lengthNow += graph.getNodeConnectionsValue(idNow, y + 1);
            nodeMap.get(idNext).setDirectionFrom(searchForDirection(idNext, idNow));
            solutionBlocks.add(new SolutionBlock(idNow, idNext, graph.getNodeConnectionsValue(idNow, y + 1), searchForDirection(idNow, idNext)));
        }
        return idNext;

    }

    private void endDeadEnd() {

        if (idNow != start) {
            if (idNow == end) {
                if (lengthMin == 0 || lengthNow > lengthMin) {

                    lengthMin = lengthNow;
                    if (mode == 0){
                        saveSolution();
                    }
                    

                }
            }
            
            lengthNow -= graph.getNodeConnectionsValue(idNow, ((int) nodeMap.get(idNow).getDirectionFrom()) * 2 + 1);
            //solutionBlocks.remove(solutionBlocks.size() - 1);
            idNext = graph.getNodeConnectionsValue(idNow, (int) nodeMap.get(idNow).getDirectionFrom() * 2);

            if (mode == 0){ //shortest
                solutionBlocks.remove(solutionBlocks.size() - 1);
                nodeMap.get(idNext).setDirectionToMin(searchForDirection(idNext, idNow) + 1);
                nodeMap.get(idNow).setVisited(false);
            } else { //whole
                solutionBlocks.add(new SolutionBlock(idNow, idNext, graph.getNodeConnectionsValue(idNow, ((int) nodeMap.get(idNow).getDirectionFrom()) * 2 + 1), searchForDirection(idNow, idNext)));
                nodeMap.get(idNext).setDirectionToMin(searchForDirection(idNext, idNow));;
            }
            

        } else {

            this.p = 1;
        }
    }

    private int searchForDirection(Integer id1, Integer id2) {

        int i = 0;
        Integer ID = graph.getNodeConnectionsValue(id1, i);
        while ( !id2.equals(ID)) {
            i += 2;
            ID = graph.getNodeConnectionsValue(id1, i);
        }

        return i / 2;
    }

    private void saveSolution() {

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
