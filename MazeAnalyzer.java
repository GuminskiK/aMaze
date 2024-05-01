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
    public int maxID;


    int currentRows;

    public int analyzeMaze (File file, int columns, int rows){
       
        char[][] x = new char[3][columns]; 
        this.nodes = new ArrayList<>();
        this.Start = -2;
        this.End = -2;

        try {
            Scanner scanner = new Scanner(file);
            readTXT(file, columns, rows, x, scanner);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.maxID = currentID; 
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
                        G++;
                        this.Start = currentID;
                        this.StartPos = columns * (currentRows - 2) + i;
                        nodes.add(directions());
                        nodes.get(currentID)[4] = currentID + 1; //do hakai?
                        nodes.get(currentID)[5] = 1;
                        Numbers[i] = 0;
                        ID[i] = currentID; 

                        nodes.get(currentID)[8] = i + 1;//x
                        nodes.get(currentID)[9] = currentRows - 2;//y

                        currentID++;
                        nodes.add(directions());

                        nodes.get(currentID)[0] = currentID - 1;
                        nodes.get(currentID)[1] = 1;

                    } else if (x[0][i] == 'K'){
                        h++;
                        G++;
                        this.End = currentID;
                        this.EndPos = columns * (currentRows - 2)+ i;
                        nodes.add(directions());
                        nodes.get(currentID)[4] = currentID + 1;
                        nodes.get(currentID)[5] = 1;
                        Numbers[i] = 0;
                        ID[i] = currentID; 

                        nodes.get(currentID)[8] = i +1;//x
                        nodes.get(currentID)[9] = currentRows - 2;//y

                        currentID++;
                        nodes.add(directions());

                        nodes.get(currentID)[0] = currentID - 1;
                        nodes.get(currentID)[1] = 1;
                    } else {
                        G++;
                        h++;
                    }

                }
                //lewo
                if( path(x[1][i-1])){

                    if (x[1][i-1] == 'P'){
                        h++;
                        L++;
                        this.Start = currentID;
                        this.StartPos = columns * (currentRows -1) + i -1;
                        nodes.add(directions());
                        nodes.get(currentID)[2] = currentID + 1;
                        nodes.get(currentID)[3] = 1;
                        Numbers[i-1] = 0;
                        ID[i-1] = currentID; 

                        nodes.get(currentID)[8] = i;//x
                        nodes.get(currentID)[9] = currentRows - 1;//y

                        currentID++;
                        nodes.add(directions());

                        nodes.get(currentID)[6] = currentID - 1;
                        nodes.get(currentID)[7] = 1;
                    } else if (x[1][i-1] == 'K'){
                        h++;
                        L++;
                        this.End = currentID;
                        this.EndPos = columns * (currentRows - 1) + i -1;
                        nodes.add(directions());
                        nodes.get(currentID)[2] = currentID + 1;
                        nodes.get(currentID)[3] = 1;
                        Numbers[i-1] = 0;
                        ID[i-1] = currentID;

                        nodes.get(currentID)[8] = i;//x
                        nodes.get(currentID)[9] = currentRows - 1;//y

                        currentID++;
                        nodes.add(directions());

                        nodes.get(currentID)[6] = currentID - 1;
                        nodes.get(currentID)[7] = 1;
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
                        nodes.add(directions());
                        nodes.get(currentID)[0] = currentID + 1;
                        nodes.get(currentID)[1] = 1;

                        nodes.get(currentID)[8] = i +1;//x
                        nodes.get(currentID)[9] = currentRows;//y

                        currentID++;
                        nodes.add(directions());

                        nodes.get(currentID)[4] = currentID - 1;
                        nodes.get(currentID)[5] = 1;
                    } else if (x[2][i] == 'K'){
                        h++;
                        D++;
                        this.End = currentID;
                        this.EndPos = columns * currentRows + i ;
                        nodes.add(directions());
                        nodes.get(currentID)[0] = currentID + 1;
                        nodes.get(currentID)[1] = 1;

                        nodes.get(currentID)[8] = i +1;//x
                        nodes.get(currentID)[9] = currentRows;//y

                        currentID++;
                        nodes.add(directions());

                        nodes.get(currentID)[4] = currentID - 1;
                        nodes.get(currentID)[5] = 1;
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
                        nodes.add(directions());
                        nodes.get(currentID)[6] = currentID + 1;
                        nodes.get(currentID)[7] = 1;
                        Numbers[i+1] = 0;
                        ID[i+1] = currentID;

                        nodes.get(currentID)[8] = i + 2;//x
                        nodes.get(currentID)[9] = currentRows -1;//y

                        currentID++;
                        nodes.add(directions());

                        nodes.get(currentID)[2] = currentID - 1;
                        nodes.get(currentID)[3] = 1;
                    } else if (x[1][i+1] == 'K'){
                        h++;
                        P++;
                        this.End = currentID;
                        this.EndPos = columns * (currentRows - 1) + i + 1;
                        nodes.add(directions());
                        nodes.get(currentID)[6] = currentID + 1;
                        nodes.get(currentID)[7] = 1;
                        Numbers[i+1] = 0;
                        ID[i+1] = currentID;
                        nodes.get(currentID)[8] = i + 2;//x
                        nodes.get(currentID)[9] = currentRows -1;//y

                        currentID++;
                        nodes.add(directions());

                        nodes.get(currentID)[2] = currentID - 1;
                        nodes.get(currentID)[3] = 1;
                    } else {
                        P++;
                        h++;
                    }
                }

                

                //System.out.printf("Char: %c, H:%d, G:%d, P:%d, D:%d, L:%d \n", x[1][i], h, G, P, D, L);
                //System.out.printf("G:%c, P:%c, D:%c, L:%c \n", x[0][i], x[1][i-1], x[2][i], x[1][i+1]);
                //System.out.println("");

                //jeśli node (skręt, zaułek, rozdroże w labiryncie) 3 razy to samo popraw!!!
                if ( h == 1){ //zaulek
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
        
        if (Start != currentID - 1 && End != currentID - 1){
            nodes.add(directions());
        }
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

    public void customAnalyzer (int[] pathi, int[] custom, int columns, char c){


        int[] ID1,ID2;   
        int G = 0;
        int P = 0;
        int D = 0;
        int L = 0;
        int h = 0;
        int ID = custom[0] + custom[1] * columns;
        
        //góra
        if( pathi[ID - columns] == 1){

            h++;
            G++;

        }
        //lewo
        if( pathi[ID - 1] == 1){

            h++;
            L++;
        }
        //dół
        if( pathi[ID + columns] == 1){
                
            h++;
            D++;
        }
            //prawo
        if( pathi[ID + 1] == 1){

            P++;
            h++;
        }
            
        if ( h == 1 || (h == 2 && ((G == 1 || D == 1) && (L == 1 || P == 1))) || h >= 3){ //zaulek
            //jesteśmy od początku na nodzie hip hip hurra
        } else {
            nodes.add(new Integer[]{0,0,0,0,0,0,0,0,0,0});

            if ( h == 2 && P == 1){
                if (c == 'S'){
                    ID1 = searchCustom(pathi,columns,ID, 2);
                    nodes.get(Start)[6] = ID1[0];
                    nodes.get(Start)[7] = ID1[1];
                    ID2 = searchCustom(pathi,columns,ID, 3);
                    nodes.get(Start)[2] = ID2[0];
                    nodes.get(Start)[3] = ID2[1];
                } else {
                    ID1 = searchCustom(pathi,columns,ID, 2);
                    nodes.get(End)[6] = ID1[0];
                    nodes.get(End)[7] = ID1[1];
                    ID2 = searchCustom(pathi,columns,ID, 3);
                    nodes.get(End)[2] = ID2[0];
                    nodes.get(End)[3] = ID2[1];
                }
            }else {
                if( c == 'S'){
                    ID1 = searchCustom(pathi,columns,ID, 0);
                    nodes.get(Start)[0] = ID1[0];
                    nodes.get(Start)[1] = ID1[1];
                    ID2 = searchCustom(pathi,columns,ID, 1);
                    nodes.get(Start)[4] = ID2[0];
                    nodes.get(Start)[5] = ID2[1];
                } else{
                    ID1 = searchCustom(pathi,columns,ID, 0);
                    nodes.get(End)[0] = ID1[0];
                    nodes.get(End)[1] = ID1[1];
                    ID2 = searchCustom(pathi,columns,ID, 1);
                    nodes.get(End)[4] = ID2[0];
                    nodes.get(End)[5] = ID2[1];
                }
            }
        }
        
    }

    private int[] searchCustom(int [] pathi, int columns, int ID, int mod){

        int x = 0;
        int distance = 0;
        int G,L,P,D,h;
        int z[] = new int[2];

        while (x != 1){

            G = 0;
            L = 0;
            P = 0;
            D = 0;
            h = 0;

            if( pathi[ID - columns] == 1){

                h++;
                G++;
    
            }
            //lewo
            if( pathi[ID - 1] == 1){
    
                h++;
                L++;
            }
            //dół
            if( pathi[ID + columns] == 1){
                    
                h++;
                D++;
            }
                //prawo
            if( pathi[ID + 1] == 1){
    
                P++;
                h++;
            }
                
            if ( h == 1 || (h == 2 && ((G == 1 || D == 1) && (L == 1 || P == 1))) || h >= 3){ //zaulek
                //jesteśmy na nodzie hip hip hurra
                x = 1;
                z[0] = ID;
                z[1] = distance;
                return z;
            }

            switch (mod) {
                case 0:
                    ID -= columns;
                    break;
                case 1:
                    ID += columns;
                    break;
                case 2:
                    ID -= 1;
                    break;
                case 3:
                    ID += 1;
                    break;
                default:
                    break;
            }

            distance++;
        }
        //zmien na id noda
        return z;
    }
}
