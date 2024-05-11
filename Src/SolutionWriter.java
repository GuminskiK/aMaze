
public class SolutionWriter {

    int Start;
    int End;
    int ID_next;
    int ID_now;
    int p = 0;
    int length_now;

    Maze maze;
    Graph graph;

    public void WriteSolution(boolean[] solution, Maze maze, int Start, int End, Graph graph){

        this.graph = graph;
        this.maze = maze;
        this.Start = Start;
        this.End = End;

        solve(solution);
        maze.setCharFromMaze(maze.getStart()[0], maze.getStart()[1], 'P');
        System.out.println("Długość: " + length_now);

    }

    private void drawingSolution(int y){

        int length = graph.getNodeValue(ID_now, y + 1);
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
                    maze.setCharFromMaze(graph.getNodeValue(ID_now, 8) - 1, graph.getNodeValue(ID_now, 9) + i , 'R');
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
    //mode shortest
    private int solve( boolean[] solution){

        ID_next = Start;
        length_now = 0;

        while (p != 1){
            
            ID_now = ID_next;
            if( ID_now == End){
                break;
            }
            solution[ID_now] = false;
            ID_next = whichNext(solution);

        }
        return 0;
        
    }

    private int whichNext(boolean[] solution){
        
        int y = 0;
        Integer ID_n = 0;

        while (y != 8){
            ID_n = graph.getNodeValue(ID_now, y);
            //następny nieodwiedzony, a długość połączenia nie równa 0;
            if ( ID_n != null){ 
                if( solution[ID_n] == true){
                    break;
                }
            }
            y += 2;
        }

        length_now += graph.getNodeValue(ID_now, y + 1);

        drawingSolution(y);

        return ID_n;

    }

}
