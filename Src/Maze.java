public class Maze {

    private int columns;
    private int rows;
    private char[][] maze;

    private Integer StartX;
    private Integer StartY;
    private Integer EndX;
    private Integer EndY;

    private Integer customStart[];
    private Integer customEnd[];

    private int whoPickedCustom;

    Maze(){

        this.StartX = null;
        this.StartY = null;
        this.EndX = null;
        this.EndY = null;

        this.customStart = new Integer[]{null,null};
        this.customEnd = new Integer[]{null,null};

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

    ///////
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

    public void reset(){
        this.columns = 0;
        this.rows = 0;
        this.maze = null;
    
        this.StartX = null;
        this.StartY = null;
        this.EndX = null;
        this.EndY = null;
    
        this.customStart = new Integer[]{null,null};
        this.customEnd = new Integer[]{null,null};
    
        whoPickedCustom = 0;
    }
}
