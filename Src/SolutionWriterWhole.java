import java.util.ArrayList;

public class SolutionWriterWhole {

    int Start;
    int End;
    Integer ID_now;
    int length_now;
    int length_min;
    Integer ID_next;
    int p = 0;
    ArrayList<Node> nodeMap;
    Maze maze;
    Graph graph;

    public boolean[] save;

    public int solveMaze( int Start, int End, Maze maze, Graph graph){

        this.graph = graph;
        this.maze = maze;
        this.Start = Start;
        this.End = End;
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

        ID_next = Start;

        while (p != 1){
            
            ID_now = ID_next;
            
            maze.setCharFromMaze(graph.getNodeValue(ID_now, 8) - 1 , graph.getNodeValue(ID_now, 9), 'R');
            nodeMap.get(ID_now).visited = true;

            ID_next = whichNext();
            if ( ID_next == -1){
                endDeadEnd();
            }

        }
        
    }

    private int whichNext(){
        
        int y = nodeMap.get(ID_now).directionToMin * 2;
        Integer ID_n = 0;

        while (y != 8){
            ID_n = graph.getNodeValue(ID_now, y);
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
            length_now += graph.getNodeValue(ID_now, y + 1);
            nodeMap.get(ID_n).directionFrom = searchForDirection(ID_n); 
        }

        drawingSolution(y);

        return ID_n;

    }

    private void endDeadEnd(){

        if(ID_now != Start){
            if(ID_now == End){
                if( length_min == 0 || length_now > length_min){

                    length_min = length_now;
                    saveSolution();

                } 
            }
             
            length_now -= graph.getNodeValue(ID_now, ((int) nodeMap.get(ID_now).directionFrom) * 2 + 1);
            ID_next = graph.getNodeValue(ID_now, (int) nodeMap.get(ID_now).directionFrom * 2);
          
            nodeMap.get(ID_next).directionToMin = searchForDirection(ID_next);

        } else {

            this.p = 1;
        }
    }

    private int searchForDirection(int ID_next){
        
        int i = 0;
        Integer ID = graph.getNodeValue(ID_next, i);
        while ( ID_now != ID){
            i += 2;
            ID = graph.getNodeValue(ID_next, i);
        }

        return i/2;
    }

    public void saveSolution(){

        for( int i = 0; i < nodeMap.size(); i++){
            this.save [i] = nodeMap.get(i).visited; 
        } 
    }

    private void drawingSolution(int y){
        //ID_now
        int length = graph.getNodeValue(ID_now, y+1);
        int direction = y/2;

        switch (direction) {
            case 0:
                for (int i = 0; i < length; i++){
                    maze.setCharFromMaze(graph.getNodeValue(ID_now, 8) - 1, graph.getNodeValue(ID_now, 9) - i, 'R');
                }
                break;
            case 1:
                for (int i = 0; i < length; i++){
                    maze.setCharFromMaze(graph.getNodeValue(ID_now, 8) + i - 1, graph.getNodeValue(ID_now, 9), 'R');
                }
                break;
            case 2:
                for (int i = 0; i < length; i++){
                    maze.setCharFromMaze(graph.getNodeValue(ID_now, 8) - 1, graph.getNodeValue(ID_now, 9) + i, 'R');
                }
                break;
            case 3:
                for (int i = 0; i < length; i++){
                    maze.setCharFromMaze(graph.getNodeValue(ID_now, 8) - i - 1, graph.getNodeValue(ID_now, 9), 'R');
                }
                break;
            default:
                break;
        }

    }
}
