import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MazeAnalyzer {

    public int currentID;
    public int Start;
    public int StartPos;
    public int End;
    public int EndPos;
    public ArrayList<Integer[]> nodes;

    int currentRows;

    public int analyzeMaze (File file, int columns, int rows){
       
        char[][] x = new char[3][columns]; 
        this.nodes = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(file);
            readTXT(file, columns, rows, x, scanner);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /* 
        //System.out.println(this.currentID);
        for (int i = 0; i < this.currentID; i++){
            System.out.print(i + "  ");
            for(int y = 0; y < 8; y++){
                System.out.print((int)nodes.get(i)[y] + " ");
            }
            System.out.println("");
        }
        //System.out.println(Start + " " + End);
        //System.out.println("ROWS-1:" + currentRows);
        */
        return 0;
    }

    private void readTXT (File file, int columns, int rows, char[][] x, Scanner scaneer){

        int[] ID = new int[columns];
        int[] Numbers = new int [columns];
        this.currentID = 0;
        this.currentRows = 0;

        for(int i = 0; i < columns; i++){

            ID[i] = -1;
            Numbers[i] = -1;
        }

        try {

            Scanner scanner = new Scanner(file);
            for (int i = 0; i < 3; i++){

                String line = scanner.nextLine();
                System.arraycopy(line.toCharArray(), 0, x[i], 0, line.length());
    
            }
            currentRows = 2;
            ifNode(x, columns, rows, ID, Numbers, currentRows);

            for (int i = 0; i < rows - 3; i++){

                currentRows++;
                //Przepisanie na wyższe wiersze w x
                System.arraycopy(x[1], 0, x[0], 0, columns);
                System.arraycopy(x[2], 0, x[1], 0, columns);

                String line = scanner.nextLine();
                System.arraycopy(line.toCharArray(), 0, x[2], 0, line.length());
                ifNode(x, columns, rows, ID, Numbers, currentRows);

            }

            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void ifNode(char[][] x, int columns, int rows, int[] ID, int[] Numbers, int currentRows){

        for(int i = 1; i < columns - 1 ; i++ ){
            
            if ( x[1][i] == ' '){
                
                int G = 0;
                int P = 0;
                int D = 0;
			    int L = 0;
			    int h = 0;

                //góra
                if( path(x[0][i])){

                    if (x[0][i] == 'P'){
                        h++;
                        this.Start = currentID;
                        this.StartPos = columns * (currentRows - 2) + i;
                    } else if (x[0][i] == 'K'){
                        h++;
                        this.End = currentID;
                        this.EndPos = columns * (currentRows - 2)+ i;
                    } else {
                        G++;
                        h++;
                    }

                }
                //lewo
                if( path(x[1][i-1])){

                    if (x[1][i-1] == 'P'){
                        h++;
                        this.Start = currentID;
                        this.StartPos = columns * (currentRows -1) + i -1;
                    } else if (x[1][i-1] == 'K'){
                        h++;
                        this.End = currentID;
                        this.EndPos = columns * (currentRows - 1) + i -1;
                    } else {
                        L++;
                        h++;
                    }

                }
                //dół
                if( path(x[2][i])){
                    
                    if (x[2][i] == 'P'){
                        h++;
                        D++;
                        this.Start = currentID;
                        this.StartPos = columns * currentRows + i;
                    } else if (x[2][i] == 'K'){
                        h++;
                        D++;
                        this.End = currentID;
                        this.EndPos = columns * currentRows + i ;
                    } else {
                        D++;
                        h++;
                    }
                }
                //prawo
                if( path(x[1][i+1])){

                    if (x[1][i+1] == 'P'){
                        h++;
                        P++;
                        this.Start = currentID;
                        this.StartPos = columns * (currentRows - 1) + i + 1;
                    } else if (x[1][i+1] == 'K'){
                        h++;
                        P++;
                        this.End = currentID;
                        this.EndPos = columns * (currentRows - 1) + i + 1;
                    } else {
                        P++;
                        h++;
                    }
                }

                

                //System.out.printf("Char: %c, H:%d, G:%d, P:%d, D:%d, L:%d \n", x[1][i], h, G, P, D, L);
                //System.out.printf("G:%c, P:%c, D:%c, L:%c \n", x[0][i], x[1][i-1], x[2][i], x[1][i+1]);
                //System.out.println("");

                if (Start == currentID || End == currentID){
                    x[1][i] = 'Z';
                    Link(Numbers, ID, i, G, L);
                }
                //jeśli node (skręt, zaułek, rozdroże w labiryncie) 3 razy to samo popraw!!!
                else if ( h == 1){ //zaulek
                    x[1][i] = 'Z';
                    
                    Link( Numbers, ID, i, G, L);
        
                } else if (h == 2 && ((G == 1 || D == 1) && (L == 1 || P == 1)) ){ // skret
                    x[1][i] = 'S';

                    Link( Numbers, ID, i, G, L );

                } else if (h >= 3){ //rozdroze
                    x[1][i] = 'R';
                    
                    Link( Numbers, ID, i, G, L );

                } else{ //połączenia

                    if (L == 1 && P == 1){
                        ID[i] = ID[i-1];
                        Numbers[i] = Numbers[i-1] + 1;

                    }else if ( G == 1 && D == 1){
                        Numbers[i] = Numbers[i] + 1;

                    }
                }

            } else {
                ID[i] = -1;
                Numbers[i] = -1;
            }
        }

        /*
        for (int i = 1; i < columns - 1; i++){
            System.out.print(ID[i] + " ");
            System.out.print(Numbers[i] + " ");
            System.out.println("");

        }
        
        System.out.println("------------------------------------");
        */
        
    }

    private boolean path( char x){

        if(x == ' ' || x == 'Z' || x == 'S' || x == 'R'|| x == 'P' ||  x == 'K'){
            return true;
        }else {
            return false;
        }
    }

    private Integer[] directions(){
        Integer[] x = {-1,-1,-1,-1,-1,-1,-1,-1, 0, 0};
        return x;
    }

    private void Link( int[] Numbers, int[]ID, int i, int G, int L ){

        nodes.add(directions());
        
        if (G == 1){

            nodes.get(this.currentID)[1] = Numbers[i] + 1;
            nodes.get(this.currentID)[0] = ID[i];

            nodes.get(ID[i])[5] = Numbers[i]+1;
            nodes.get(ID[i])[4] = this.currentID;

            ID[i] = this.currentID;
            Numbers[i] = 0; 

        }
        
        if( L == 1 ){

            ID[i] = this.currentID;
            Numbers[i] = 0; 

            nodes.get(this.currentID)[7] = Numbers[i-1] + 1;
            nodes.get(this.currentID)[6] = ID[i-1];

            nodes.get(ID[i-1])[3] = Numbers[i-1] + 1;
            nodes.get(ID[i-1])[2] = this.currentID;
            
        }
        
        if ( G != 1 && L != 1 ){
            ID[i] = this.currentID;
            Numbers[i] = 0;
        } 

        nodes.get(currentID)[8] = i + 1;//x
        nodes.get(currentID)[9] = currentRows - 1;//y
        this.currentID++;

    }
}
