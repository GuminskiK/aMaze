package Core;

import java.util.ArrayList;

public class SolutionWriter {

    private Maze maze;
    int currentColumn;
    int currentRow;

    public void WriteSolution(ArrayList<SolutionBlock> solution, Maze maze){

        this.maze = maze;
        this.currentColumn = maze.getStart()[0];
        this.currentRow = maze.getStart()[1];

        for ( SolutionBlock solutionBlock: solution){
            drawSolutionBlock(solutionBlock.getDirection(), solutionBlock.getSteps());
        }

        maze.changeMazeCellToStart(maze.getStart()[1], maze.getStart()[0]);
        maze.changeMazeCellToEnd(maze.getEnd()[1], maze.getEnd()[0]);

    }

    private void drawSolutionBlock(int direction, int length) {

        switch (direction) {
            case 0:
                for (int i = 0; i < length; i++) {
                   currentRow--;
                   maze.changeMazeCellToSolution(currentRow, currentColumn);
                }
                break;
            case 1:
                for (int i = 0; i < length; i++) {
                    currentColumn++;
                    maze.changeMazeCellToSolution(currentRow, currentColumn);
                }
                break;
            case 2:
                for (int i = 0; i < length; i++) {
                    currentRow++;
                    maze.changeMazeCellToSolution(currentRow, currentColumn);
                }
                break;
            case 3:
                for (int i = 0; i < length; i++) {
                    currentColumn--;
                    maze.changeMazeCellToSolution(currentRow, currentColumn);
                }
                break;
            default:
                break;
            
        }
    }

}
