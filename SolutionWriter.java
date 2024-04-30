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

    public void WriteSolution(boolean[] solution, ArrayList<JLabel> panels, int Start, int End, ArrayList<Integer[]> nodes, int columns, int StartDirection, int EndDirection){

        this.Start = Start;
        this.End = End;

        drawStartEnd(nodes, panels, columns, Start, StartDirection);
        drawStartEnd(nodes, panels, columns, End, EndDirection);

        int x = solve(nodes, solution, panels, columns);
        System.out.println(length_now);

    }

    private int solve(ArrayList<Integer[]> nodes, boolean[] solution, ArrayList<JLabel> panels, int columns){

        ID_next = Start;
        length_now = 2; // dodajemy krok wejscia do i wyjscia z labiryntu

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
    private void drawStartEnd(ArrayList<Integer[]> nodes, ArrayList<JLabel> panels, int columns, int object, int direction){

        switch (direction) {
            case 2:
                panels.get(nodes.get(object)[8] + (nodes.get(object)[9] - 1) * columns - 1).setBackground(Color.red);
                break;
            case 3:
                panels.get(nodes.get(object)[8] + nodes.get(object)[9] * columns).setBackground(Color.red);
                break;
            case 0:
                panels.get(nodes.get(object)[8] + (nodes.get(object)[9] + 1) * columns - 1).setBackground(Color.red);
                break;
            case 1:
                panels.get(nodes.get(object)[8] + nodes.get(object)[9] * columns - 2).setBackground(Color.red);
                break;
            default:
                break;
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
