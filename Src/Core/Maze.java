package Core;
import Cells.*;

public class Maze {

    private int columns;
    private int rows;
    private char[][] mazeInChar2DArray;
    
    private MazeCell[][] mazeCells;

    private Integer startX;
    private Integer startY;
    private Integer endX;
    private Integer endY;

    private Integer newStartPosition[];
    private Integer newEndPosition[];

    private int whoPickedCustom;

    private int maxWidthInPixels;

    private boolean startLocated;
    private boolean startChanged;
    private boolean endLocated;
    private boolean endChanged;

    Maze(){

        this.startX = null;
        this.startY = null;
        this.endX = null;
        this.endY = null;

        this.startLocated = false;
        this.startChanged = false;
        this.endLocated = false;
        this.endLocated = false;

        this.newStartPosition = new Integer[]{null,null};
        this.newEndPosition = new Integer[]{null,null};

    }

    public void mazeToMazeCells(){

        mazeCells = new MazeCell[rows][columns];
        int xPixels = 5;
        int yPixels = 5;
        this.maxWidthInPixels = columns * 10;

        for (int i = 0; i < rows; i++){
            
            for(int y = 0; y < columns; y++){

                switch(getCharFromMazeInChar2DArray(y, i)){
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

    public void reset(){
        this.columns = 0;
        this.rows = 0;
        this.mazeInChar2DArray = null;
    
        this.startX = null;
        this.startY = null;
        this.endX = null;
        this.endY = null;

        this.startLocated = false;
        this.startChanged = false;
        this.endLocated = false;
        this.endChanged = false;
    
        this.newStartPosition = new Integer[]{null,null};
        this.newEndPosition = new Integer[]{null,null};
    
        whoPickedCustom = 0;
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

    public void changeMazeCellToWall(int row, int column){
        mazeCells[row][column] = new WallCell(5 + ((column * 10) % maxWidthInPixels), 5 + row * 10  );
    }

    public void changeMazeCellToPath(int row, int column){
        mazeCells[row][column] = new PathCell(5 + ((column * 10) % maxWidthInPixels), 5 + row * 10  );
    }
    
    // type czy pick?
    public void pickCustom(int who){
        this.whoPickedCustom = who;
    }

    public int whoPickedCustom(){
        return this.whoPickedCustom;
    }

    //gettery i settery
    public char[][] getMaze(){
        return mazeInChar2DArray;
    }

    public char getCharFromMazeInChar2DArray(int columns, int rows){
        return mazeInChar2DArray[rows][columns];
    }

    public MazeCell[][] getMazeCells(){
        return this.mazeCells;
    }

    public Integer[] getNewStartPosition (){
        return newStartPosition;
    }

    public Integer[] getNewEndPosition (){
        return newEndPosition;
    }

    public int getColumns(){
        return columns;
    }
    
    public int getRows(){
        return rows;
    }

    public Integer[] getStart (){
        return new Integer[]{startX, startY};
    }

    public Integer[] getEnd (){
        return new Integer[]{endX, endY};
    }

    public boolean getStartLocated(){
        return this.startLocated;
    }

    public boolean getStartChanged(){
        return this.startChanged;
    }

    public boolean getEndLocated(){
        return this.endLocated;
    }

    public boolean getEndChanged(){
        return this.endChanged;
    }




    public void setMaze( char[][] maze){
        this.mazeInChar2DArray = maze;
    }

    public void setCharFromMazeInChar2DArray(int columns, int rows, char x){
        this.mazeInChar2DArray[rows][columns] = x;

    }

    public void setNewStartPosition ( Integer newStartPosition0, Integer newStartPosition1){
        this.newStartPosition[0] = newStartPosition0;
        this.newStartPosition[1] = newStartPosition1;
    }


    public void setNewEndPosition ( Integer newEndPosition0, Integer newEndPosition1){
        this.newEndPosition[0] = newEndPosition0;
        this.newEndPosition[1] = newEndPosition1;
    }

    
    public void setColumns(int columns){
        this.columns = columns;
    }


    public void setRows(int rows){
        this.rows = rows;
    }

    public void setStart (Integer startX, Integer startY){
        this.startX = startX;
        this.startY = startY;
    }

    public void setEnd (Integer endX, Integer endY){
        this.endX = endX;
        this.endY = endY;
    }

    public void setStartLocated( boolean x){
        this.startLocated = x;
    }

    public void setStartChanged( boolean x){
        this.startChanged= x;
    }

    public void setEndLocated( boolean x){
        this.endLocated = x;
    }

    public void setEndChanged( boolean x){
        this.endChanged = x;
    }

}
