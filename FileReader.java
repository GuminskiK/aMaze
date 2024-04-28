import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {

    public int columns;
    public int rows;

    public void CountRowsColumns(File file){

        columns = 0;
        rows = 0;

        try{

            String dane = "";
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                dane = myReader.nextLine();
                //System.out.println(dane);
                rows ++;
            }
            columns = dane.length();
            myReader.close();

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        System.out.println(rows);
        System.out.println(columns);
        
    }

    public char[][] ReadFileTXT (File file){

        char[][] x = new char[rows][columns]; 

        try {
            Scanner scanner = new Scanner(file);
            for (int i = 0; i < this.rows; i++){

                String line = scanner.nextLine();
                System.arraycopy(line.toCharArray(), 0, x[i], 0, line.length());
    
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        for (int i = 0; i < this.rows; i++){

            System.out.println(x[i]);
        }
        return x;
    }


}
