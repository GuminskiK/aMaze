import java.util.ArrayList;

public class MazeSolver {

    int Start;
    int End;
    int ID_now;
    int length_now;
    int length_min;
    int ID_next;
    int p = 0;
    ArrayList<Node> nodeMap;

    public boolean[] save;

    public int solveMaze(ArrayList<Integer[]> nodes, int Start, int End){

        this.Start = Start;
        this.End = End;
        nodeMap = new ArrayList<>();

        for (int i = 0; i < nodes.size(); i++){

            nodeMap.add(new Node(i));
            
        }

        solve(nodes);
        
        return 0;
    }

    private void solve(ArrayList<Integer[]> nodes){

        ID_next = Start;

        while (p != 1){
            
            ID_now = ID_next;
            nodeMap.get(ID_now).visited = true;

            ID_next = whichNext(nodes);
            if ( ID_next == -1){
                endDeadEnd(nodes);
            }

        }
        
    }

    private int whichNext(ArrayList<Integer[]> nodes){
        
        int y = nodeMap.get(ID_now).directionToMin * 2;
        int ID_n = 0;

        while (y != 8){
            ID_n = (int)nodes.get(ID_now)[y];
            //następny nieodwiedzony, a długość połączenia nie równa 0;
            if ( ID_n != -1){ 
                if( nodeMap.get(ID_n).visited == false){
                    break;
                }
            }
            y += 2;
        }

        if (y == 8){
            ID_n = -1;
        } else {
            length_now += nodes.get(ID_now)[y+1];
            nodeMap.get(ID_n).directionFrom = searchForDirection(nodes, ID_n); 
        }
        return ID_n;

    }

    private void endDeadEnd(ArrayList<Integer[]> nodes){

        if(ID_now != Start){
            if(ID_now == End){
                if( length_min == 0 || length_now > length_min){

                    length_min = length_now;
                    saveSolution();

                } 
            }
             
            length_now -= nodes.get(ID_now)[((int) nodeMap.get(ID_now).directionFrom) * 2 + 1];
            ID_next = nodes.get(ID_now)[(int) nodeMap.get(ID_now).directionFrom * 2];
            nodeMap.get(ID_next).directionToMin = searchForDirection(nodes, ID_next) +1;
            nodeMap.get(ID_now).visited = false;

        } else {

            this.p = 1;
        }
    }

    private int searchForDirection(ArrayList<Integer[]> nodes, int ID_next){
        
        int i = 0;
        int ID = nodes.get(ID_next)[i];
        while ( ID_now != ID){
            i += 2;
            ID = nodes.get(ID_next)[i];
        }

        return i/2;
    }

    public void saveSolution(){

        this.save = new boolean[nodeMap.size()];
        System.out.println("Save");
        for( int i = 0; i < nodeMap.size(); i++){
            this.save [i] = nodeMap.get(i).visited; 
        } 
    }



}
