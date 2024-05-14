import java.util.ArrayList;

public class SolutionWriterWhole {

    int start;
    int end;
    Integer idNow;
    int lengthNow;
    int lengthMin;
    Integer idNext;
    int p = 0;
    ArrayList<Node> nodeMap;
    Maze maze;
    Graph graph;

    public boolean[] save;

    public int solveMaze( int start, int end, Maze maze, Graph graph){

        this.graph = graph;
        this.maze = maze;
        this.start = start;
        this.end = end;
        nodeMap = new ArrayList<>();
        
        
        for (int i = 0; i < graph.getNodes().size(); i++){

            nodeMap.add(new Node(i));
            
        }

        this.save = new boolean[nodeMap.size()];

        solve();
        maze.setCharFromMaze(maze.getStart()[0], maze.getStart()[1], 'P');
        maze.setCharFromMaze(maze.getEnd()[0], maze.getEnd()[1], 'K');
        
        return 0;
    }

    private void solve(){

        idNext = start;

        while (p != 1){
            
            idNow = idNext;
            
            maze.setCharFromMaze(graph.getNodeValue(idNow, 8) - 1 , graph.getNodeValue(idNow, 9), 'R');
            nodeMap.get(idNow).visited = true;

            idNext = whichNext();
            if ( idNext == -1){
                endDeadEnd();
            }

        }
        
    }

    private int whichNext(){
        
        int y = nodeMap.get(idNow).directionToMin * 2;
        Integer ID_n = 0;

        while (y != 8){
            ID_n = graph.getNodeValue(idNow, y);
            //następny nieodwiedzony, a długość połączenia nie równa 0;
            if ( ID_n != null){ 
                if( nodeMap.get(ID_n).visited == false){
                    break;
                }
            }
            y += 2;
        }

        if (y == 8){
            ID_n = -1;
        } else {
            lengthNow += graph.getNodeValue(idNow, y + 1);
            nodeMap.get(ID_n).directionFrom = searchForDirection(ID_n); 
        }

        drawingSolution(y);

        return ID_n;

    }

    private void endDeadEnd(){

        if(idNow != start){
            if(idNow == end){
                if( lengthMin == 0 || lengthNow > lengthMin){

                    lengthMin = lengthNow;
                    saveSolution();

                } 
            }
             
            lengthNow -= graph.getNodeValue(idNow, ((int) nodeMap.get(idNow).directionFrom) * 2 + 1);
            idNext = graph.getNodeValue(idNow, (int) nodeMap.get(idNow).directionFrom * 2);
          
            nodeMap.get(idNext).directionToMin = searchForDirection(idNext);

        } else {

            this.p = 1;
        }
    }

    private int searchForDirection(int idNext){
        
        int i = 0;
        Integer ID = graph.getNodeValue(idNext, i);
        while ( idNow != ID){
            i += 2;
            ID = graph.getNodeValue(idNext, i);
        }

        return i/2;
    }

    public void saveSolution(){

        for( int i = 0; i < nodeMap.size(); i++){
            this.save [i] = nodeMap.get(i).visited; 
        } 
    }

    private void drawingSolution(int y){
        //idNow
        int length = graph.getNodeValue(idNow, y+1);
        int direction = y/2;

        switch (direction) {
            case 0:
                for (int i = 0; i < length; i++){
                    maze.setCharFromMaze(graph.getNodeValue(idNow, 8) - 1, graph.getNodeValue(idNow, 9) - i, 'R');
                }
                break;
            case 1:
                for (int i = 0; i < length; i++){
                    maze.setCharFromMaze(graph.getNodeValue(idNow, 8) + i - 1, graph.getNodeValue(idNow, 9), 'R');
                }
                break;
            case 2:
                for (int i = 0; i < length; i++){
                    maze.setCharFromMaze(graph.getNodeValue(idNow, 8) - 1, graph.getNodeValue(idNow, 9) + i, 'R');
                }
                break;
            case 3:
                for (int i = 0; i < length; i++){
                    maze.setCharFromMaze(graph.getNodeValue(idNow, 8) - i - 1, graph.getNodeValue(idNow, 9), 'R');
                }
                break;
            default:
                break;
        }

    }
}
