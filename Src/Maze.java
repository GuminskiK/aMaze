public class Maze {

    private int columns;
    private int rows;
    private char[][] maze;

    private Integer StartX;
    private Integer StartY;
    private Integer EndX;
    private Integer EndY;

    Maze(int columns, int rows, char[][] maze){

        this.columns = columns;
        this.rows = rows;
        this.maze = maze;
        this.StartX = null;
        this.StartY = null;
        this.EndX = null;
        this.EndY = null;

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
    public void setStart (Integer StartX, Integer StartY){
        this.StartX = StartX;
        this.StartY = StartY;
    }

    public void setEnd (Integer EndX, Integer EndY){
        this.EndX = EndX;
        this.EndY = EndY;
    }

    //gettery
    public Integer[] getStart (){
        return new Integer[]{StartX, StartY};
    }

    public Integer[] getEnd (){
        return new Integer[]{EndX, EndY};
    }
}
