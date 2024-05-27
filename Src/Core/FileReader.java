package Core;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class FileReader {

    private int columns;
    private int rows;
    private File file;
    private Maze maze;
    
    FileReader( File file, Maze maze){
        this.file = file;
        this.maze = maze;
    }

    public void countRowsColumns() {

        columns = 0;
        rows = 0;

        try {

            String dane = "";
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                dane = myReader.nextLine();
                rows++;
            }
            columns = dane.length();
            myReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public char[][] readFileTXT() {

        char[][] mazeInChar2DArray = new char[rows][columns];

        try {
            Scanner scanner = new Scanner(file);
            for (int i = 0; i < this.rows; i++) {

                String line = scanner.nextLine();
                System.arraycopy(line.toCharArray(), 0, mazeInChar2DArray[i], 0, line.length());

            }
            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return mazeInChar2DArray;
    }

    public void readNumberOfRowsColumns() {

        try (FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {

            byte[] buff4 = new byte[4];
            byte[] buff2 = new byte[2];
            byte[] buff1 = new byte[1];

            readBytes(bufferedInputStream.read(buff4), buff4);
            readBytes(bufferedInputStream.read(buff1), buff1);

            this.columns = readBytes(bufferedInputStream.read(buff2), buff2);
            this.rows = readBytes(bufferedInputStream.read(buff2), buff2);

            maze.setStart(readBytes(bufferedInputStream.read(buff2), buff2) -1, readBytes(bufferedInputStream.read(buff2), buff2) -1);;
            maze.setEnd(readBytes(bufferedInputStream.read(buff2), buff2) -1, readBytes(bufferedInputStream.read(buff2), buff2) -1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public char[][] ReadFileBIN() {

        char[][] x = new char[rows][columns];
        int currentColumns = 0;
        int currentRows = 0;

        try {

            FileInputStream fileInputStream = new FileInputStream(file);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);

            for (int i = 0; i < 10; i++) {
                dataInputStream.readInt();
            }

            while (dataInputStream.available() >= 1 && currentRows != 513) {
                dataInputStream.readByte(); // separator
                int value = (int) dataInputStream.readUnsignedByte(); // Wall/path
                int count = (int) dataInputStream.readUnsignedByte();

                if (value == 88) {
                    for (int y = 0; y < count + 1; y++) {
                        x[currentRows][currentColumns] = 'X';
                        currentColumns++;
                        if (currentColumns == columns) {
                            currentColumns = 0;
                            currentRows++;
                        }
                    }
                } else if (value == 32) {
                    for (int y = 0; y < count + 1; y++) {
                        x[currentRows][currentColumns] = ' ';
                        currentColumns++;
                        if (currentColumns == columns) {
                            currentColumns = 0;
                            currentRows++;
                        }
                    }
                }

            }

            dataInputStream.close();

            x[maze.getStart()[1]][maze.getStart()[0]] = 'P';
            x[maze.getEnd()[1]][maze.getEnd()[0]] = 'K';

        } catch (IOException e) {
            e.printStackTrace();
        }

        return x;

    }

    private int readBytes(int numberOfBytes, byte[] buff){
        
        int value = 0;

        for (int i = 0; i < numberOfBytes; i++) {
            value |= (buff[i] & 0xFF) << (8 * i);
        }
        return value;
    }

    public int getColumns(){
        return this.columns;
    }

    public int getRows(){
        return this.rows;
    }


}
