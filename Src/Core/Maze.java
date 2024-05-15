package Core;
import Cells.*;

public class Maze {

    private int columns;
    private int rows;
    private char[][] maze;
    
    private MazeCell[][] mazeCells;

    private Integer startX;
    private Integer startY;
    private Integer endX;
    private Integer endY;

    private Integer customStart[];
    private Integer customEnd[];

    private int whoPickedCustom;

    private int maxWidthInPixels;

    Maze(){

        this.startX = null;
        this.startY = null;
        this.endX = null;
        this.endY = null;

        this.customStart = new Integer[]{null,null};
        this.customEnd = new Integer[]{null,null};

    }

    public void MazeToMazeCells(){

        mazeCells = new MazeCell[rows][columns];
        int xPixels = 5;
        int yPixels = 5;
        this.maxWidthInPixels = columns * 10;

        for (int i = 0; i < rows; i++){
            
            for(int y = 0; y < columns; y++){

                switch(getCharFromMaze(y, i)){
                    case 'X':
                        mazeCells[i][y] = new WallCell(xPixels, yPixels);
                        break;
                    case ' ':
                        mazeCells[i][y] = new PathCell(xPixels, yPixels);
                        break;
                    case 'P':
                        mazeCells[i][y] = new StartCell(xPixels, yPixels);
                        break;
                    case 'K':
                        mazeCells[i][y] = new EndCell(xPixels, yPixels);
                        break;
                    default:
                        break;
                }

                xPixels = (xPixels + 10) % maxWidthInPixels;

            }
            yPixels += 10;
        }
    }

    public void changeMazeCellToStart(int row, int column){
        mazeCells[row][column] = new StartCell(5 + ((column * 10) % maxWidthInPixels), 5 + row * 10);
    }

    public void changeMazeCellToEnd(int row, int column){
        mazeCells[row][column] = new EndCell(5 + ((column * 10) % maxWidthInPixels), 5 + row * 10  );
    }

    public void changeMazeCellToSolution(int row, int column){
        mazeCells[row][column] = new SolutionCell(5 + ((column * 10) % maxWidthInPixels), 5 + row * 10  );
    }
    
    public MazeCell[][] getMazeCells(){
        return this.mazeCells;
    }

    public void pickCustom(int who){
        this.whoPickedCustom = who;
    }

    public int whoPickedCustom(){
        return this.whoPickedCustom;
    }
    public void setCustomStart ( Integer customStart0, Integer customStart1){
        this.customStart[0] = customStart0;
        this.customStart[1] = customStart1;
    }

    public Integer[] getCustomStart (){
        return customStart;
    }

    public void setCustomEnd ( Integer customEnd0, Integer customEnd1){
        this.customEnd[0] = customEnd0;
        this.customEnd[1] = customEnd1;
    }

    public Integer[] getCustomEnd (){
        return customEnd;
    }

    public int getColumns(){
        return columns;
    }

    public void setColumns(int columns){
        this.columns = columns;
    }

    public int getRows(){
        return rows;
    }

    public void setRows(int rows){
        this.rows = rows;
    }

    public char[][] getMaze(){
        return maze;
    }

    public void setMaze( char[][] maze){
        this.maze = maze;
    }

    public char getCharFromMaze(int columns, int rows){
        return maze[rows][columns];
    }

    public void setCharFromMaze(int columns, int rows, char x){
        this.maze[rows][columns] = x;

    }

    // settery
    public void setStart (Integer startX, Integer startY){
        this.startX = startX;
        this.startY = startY;
    }

    public void setEnd (Integer endX, Integer endY){
        this.endX = endX;
        this.endY = endY;
    }

    //gettery
    public Integer[] getStart (){
        return new Integer[]{startX, startY};
    }

    public Integer[] getEnd (){
        return new Integer[]{endX, endY};
    }

    public void reset(){
        this.columns = 0;
        this.rows = 0;
        this.maze = null;
    
        this.startX = null;
        this.startY = null;
        this.endX = null;
        this.endY = null;
    
        this.customStart = new Integer[]{null,null};
        this.customEnd = new Integer[]{null,null};
    
        whoPickedCustom = 0;
    }
}
