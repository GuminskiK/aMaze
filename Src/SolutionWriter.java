import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;

public class SolutionWriter {

    int Start;
    int End;
    int ID_next;
    int ID_now;
    int p = 0;
    int length_now;

    public void WriteSolution(boolean[] solution, ArrayList<JLabel> panels, int Start, int End, ArrayList<Integer[]> nodes, int columns, int StartPos, int EndPos){

        this.Start = Start;
        this.End = End;

        int x = solve(nodes, solution, panels, columns);
        drawStartEnd(nodes, panels, columns, StartPos, 0);
        drawStartEnd(nodes, panels, columns, EndPos, 1);
        System.out.println(length_now);

    }

    private void drawStartEnd(ArrayList<Integer[]> nodes, ArrayList<JLabel> panels, int columns, int Pos, int x){

        if (x == 0){
            panels.get(Pos).setBackground(Color.GREEN);
        } else {
            panels.get(Pos).setBackground(Color.PINK);
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
    //mode shortest
    private int solve(ArrayList<Integer[]> nodes, boolean[] solution, ArrayList<JLabel> panels, int columns){

        ID_next = Start;
        length_now = 0;

        while (p != 1){
            
            ID_now = ID_next;
            if( ID_now == End){
                break;
            }
            solution[ID_now] = false;
            ID_next = whichNext(nodes, solution, panels, columns);

        }
        panels.get(nodes.get(ID_now)[8] + nodes.get(ID_now)[9] * columns - 1 ).setBackground(Color.RED);

        return 0;
        
    }

    private int whichNext(ArrayList<Integer[]> nodes, boolean[] solution, ArrayList<JLabel> panels, int columns){
        
        int y = 0;
        int ID_n = 0;

        while (y != 8){
            ID_n = (int)nodes.get(ID_now)[y];
            //następny nieodwiedzony, a długość połączenia nie równa 0;
            if ( ID_n != -1){ 
                if( solution[ID_n] == true){
                    break;
                }
            }
            y += 2;
        }

        length_now += nodes.get(ID_now)[y+1];

        drawingSolution(nodes, panels, columns, y);

        return ID_n;

    }

}
