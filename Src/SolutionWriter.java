
public class SolutionWriter {

    int start;
    int end;
    int idNext;
    int idNow;
    int p = 0;
    int lengthNow;

    Maze maze;
    Graph graph;

    public void WriteSolution(boolean[] solution, Maze maze, int start, int end, Graph graph){

        this.graph = graph;
        this.maze = maze;
        this.start = start;
        this.end = end;

        solve(solution);
        maze.setCharFromMaze(maze.getStart()[0], maze.getStart()[1], 'P');
        System.out.println("Długość: " + lengthNow);

    }

    private void drawingSolution(int y){

        int length = graph.getNodeValue(idNow, y + 1);
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
                    maze.setCharFromMaze(graph.getNodeValue(idNow, 8) - 1, graph.getNodeValue(idNow, 9) + i , 'R');
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
    //mode shortest
    private int solve( boolean[] solution){

        idNext = start;
        lengthNow = 0;

        while (p != 1){
            
            idNow = idNext;
            if( idNow == end){
                break;
            }
            solution[idNow] = false;
            idNext = whichNext(solution);

        }
        return 0;
        
    }

    private int whichNext(boolean[] solution){
        
        int y = 0;
        Integer ID_n = 0;

        while (y != 8){
            ID_n = graph.getNodeValue(idNow, y);
            //następny nieodwiedzony, a długość połączenia nie równa 0;
            if ( ID_n != null){ 
                if( solution[ID_n] == true){
                    break;
                }
            }
            y += 2;
        }

        lengthNow += graph.getNodeValue(idNow, y + 1);

        drawingSolution(y);

        return ID_n;

    }

}
