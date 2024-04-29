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

    public void WriteSolution(boolean[] solution, ArrayList<JLabel> panels, int Start, int End, ArrayList<Integer[]> nodes, int columns){

        this.Start = Start;
        this.End = End;
        int x = solve(nodes, solution, panels, columns);
        System.out.println(length_now);

    }

    private int solve(ArrayList<Integer[]> nodes, boolean[] solution, ArrayList<JLabel> panels, int columns){

        ID_next = Start;
        length_now = 2; // dodajemy krok wejscia do i wyjscia z labiryntu

        while (p != 1){
            
            ID_now = ID_next;
            panels.get(nodes.get(ID_now)[8] + nodes.get(ID_now)[9] * columns - 1 ).setBackground(Color.RED);
            if( ID_now == End){
                break;
            }
            solution[ID_now] = false;
            ID_next = whichNext(nodes, solution);

        }

        return 0;
        
    }

    private int whichNext(ArrayList<Integer[]> nodes, boolean[] solution){
        
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

        return ID_n;

    }
}
