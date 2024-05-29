package Core;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class SolutionExporter {

    private ArrayList<SolutionBlock> solution;
    private Maze maze;
    private String filename;

    private boolean wholeMazeExported;

    private char currentChar;
    private int currentNumberOfChars;

    private int currentXPosition;
    private int currentYPosition;

    private int columns;
    private int rows;

    private char[][] mazeInChar2DArray;
    private int lengthMin;

    private char c;

    private int numberOfCodeWords;

    private Watched watched;

    SolutionExporter(ArrayList<SolutionBlock> solution, Maze maze, String filename, int lengthMin, Watched watched) {

        this.solution = solution;
        this.maze = maze;
        this.filename = filename;
        this.wholeMazeExported = false;

        this.mazeInChar2DArray = maze.getMaze();
        this.columns = maze.getColumns();
        this.rows = maze.getRows();
        this.currentChar = 'X';

        this.lengthMin = lengthMin;

        this.numberOfCodeWords = 0;

        this.watched = watched;

    }

    public void exportSolution() {

        if (lengthMin - 1 > 255) {
            System.out.println("The solution is too long, cannot export.");
        } else {

            try (FileOutputStream fos = new FileOutputStream(filename)) {

                // nagłówek
                writeIntLE(fos, 1381122627);
                writeByteLE(fos, 27);
                writeShortLE(fos, maze.getColumns());
                writeShortLE(fos, maze.getRows());
                writeShortLE(fos, maze.getStart()[0] + 1);
                writeShortLE(fos, maze.getStart()[1] + 1);
                writeShortLE(fos, maze.getEnd()[0] + 1);
                writeShortLE(fos, maze.getEnd()[1] + 1);
                for (int i = 0; i < 3; i++) {
                    writeByteLE(fos, 255);
                    writeByteLE(fos, 0);
                    writeByteLE(fos, 0);
                    writeByteLE(fos, 0);
                }
                writeIntLE(fos, 0);
                writeIntLE(fos, 0);

                writeByteLE(fos, 35);
                writeByteLE(fos, 88);
                writeByteLE(fos, 32);

                // labirynt
                while (!wholeMazeExported) {
                    currentNumberOfChars = -1;
                    writeByteLE(fos, 35);
                    writeByteLE(fos, currentChar);
                    cellsCounter();
                    writeByteLE(fos, currentNumberOfChars);
                    numberOfCodeWords++;
                }

                // rozwiazanie-nagłówek
                writeIntLE(fos, 0);
                writeByteLE(fos, lengthMin - 1);

                // rozwiązanie
                for (SolutionBlock solutionBlock : solution) {
                    directionIntToChar(solutionBlock.getDirection());
                    writeByteLE(fos, c);
                    writeByteLE(fos, solutionBlock.getSteps() - 1);
                }

                fos.close();

            } catch (IOException e) {
                System.err.println("IOException: " + e.getMessage());
            }

            try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {

                raf.seek(29);
                writeIntLE(raf, numberOfCodeWords);
                raf.seek(33);
                writeIntLE(raf, numberOfCodeWords * 3);

                raf.close();

            } catch (IOException e) {
                System.err.println("IOException: " + e.getMessage());
            }
        }

        watched.setMessage("exported");
    }

    private void cellsCounter() {

        while (mazeInChar2DArray[currentYPosition][currentXPosition] == currentChar) {

            currentNumberOfChars++;
            currentXPosition++;
            if (currentXPosition >= columns) {
                currentXPosition = 0;
                currentYPosition++;
                if (currentYPosition == rows) {
                    break;
                }
            }
        }

        if (currentYPosition != rows) {
            currentChar = mazeInChar2DArray[currentYPosition][currentXPosition];
        } else {
            wholeMazeExported = true;
        }

    }

    private void directionIntToChar(int direction) {
        switch (direction) {
            case 0:
                c = 'N';
                break;
            case 1:
                c = 'E';
                break;
            case 2:
                c = 'S';
                break;
            case 3:
                c = 'W';
                break;
            default:
                break;

        }
    }

    // zamiany do little-endian
    private static void writeIntLE(RandomAccessFile raf, int value) throws IOException {
        byte[] bytes = ByteBuffer.allocate(4)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt(value)
                .array();
        raf.write(bytes);
    }

    private static void writeIntLE(FileOutputStream fos, int value) throws IOException {
        byte[] bytes = ByteBuffer.allocate(4)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt(value)
                .array();
        fos.write(bytes);
    }

    private static void writeShortLE(FileOutputStream fos, int value) throws IOException {
        byte[] bytes = ByteBuffer.allocate(2)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putShort((short) value)
                .array();
        fos.write(bytes);
    }

    private static void writeByteLE(FileOutputStream fos, int value) throws IOException {
        fos.write((byte) value);
    }
}
