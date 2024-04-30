import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;

public class SolutionWriterWhole {

    int Start;
    int End;
    int ID_now;
    int length_now;
    int length_min;
    int ID_next;
    int p = 0;
    ArrayList<Node> nodeMap;

    public boolean[] save;
    int mode;

    public int solveMaze(ArrayList<Integer[]> nodes, int Start, int End, int mode, ArrayList<JLabel> panels, int columns, int StartPos, int EndPos){

        this.mode = mode;
        this.Start = Start;
        this.End = End;
        nodeMap = new ArrayList<>();
        drawStartEnd(nodes, panels, columns, StartPos, 0);
        drawStartEnd(nodes, panels, columns, EndPos, 1);
        
        for (int i = 0; i < nodes.size(); i++){

            nodeMap.add(new Node(i));
            
        }

        this.save = new boolean[nodeMap.size()];

        solve(nodes, panels, columns);
        
        return 0;
    }

    private void solve(ArrayList<Integer[]> nodes, ArrayList<JLabel> panels, int columns){

        ID_next = Start;

        while (p != 1){
            
            ID_now = ID_next;
            panels.get(nodes.get(ID_now)[8] + (nodes.get(ID_now)[9]) * columns - 1).setBackground(Color.red);
            nodeMap.get(ID_now).visited = true;

            ID_next = whichNext(nodes, panels, columns);
            if ( ID_next == -1){
                endDeadEnd(nodes);
            }

        }
        
    }

    private int whichNext(ArrayList<Integer[]> nodes, ArrayList<JLabel> panels, int columns){
        
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

        drawingSolution(nodes, panels, columns, y);

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
            if (mode == 0){
                nodeMap.get(ID_next).directionToMin = searchForDirection(nodes, ID_next) +1;
                nodeMap.get(ID_now).visited = false;
            } else {
                nodeMap.get(ID_next).directionToMin = searchForDirection(nodes, ID_next);
            }

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

        for( int i = 0; i < nodeMap.size(); i++){
            this.save [i] = nodeMap.get(i).visited; 
        } 
    }

    private void drawingSolution(ArrayList<Integer[]> nodes, ArrayList<JLabel> panels, int columns, int y){
        //ID_now
        int length = nodes.get(ID_now)[y+1];
        int direction = y/2;

        switch (direction) {
            case 0:
                for (int i = 0; i < length; i++){
                    panels.get(nodes.get(ID_now)[8] + (nodes.get(ID_now)[9] - i) * columns - 1).setBackground(Color.red);
                }
                break;
            case 1:
                for (int i = 0; i < length; i++){
                    panels.get(nodes.get(ID_now)[8] + nodes.get(ID_now)[9] * columns - 1 + i).setBackground(Color.red);
                }
                break;
            case 2:
                for (int i = 0; i < length; i++){
                    panels.get(nodes.get(ID_now)[8] + (nodes.get(ID_now)[9] + i)* columns - 1).setBackground(Color.red);
                }
                break;
            case 3:
                for (int i = 0; i < length; i++){
                    panels.get(nodes.get(ID_now)[8] + nodes.get(ID_now)[9] * columns - 1 - i).setBackground(Color.red);
                }
                break;
            default:
                break;
        }

    }

    private void drawStartEnd(ArrayList<Integer[]> nodes, ArrayList<JLabel> panels, int columns, int Pos, int x){

        if (x == 0){
            panels.get(Pos).setBackground(Color.GREEN);
        } else {
            panels.get(Pos).setBackground(Color.PINK);
        }
    }
}
