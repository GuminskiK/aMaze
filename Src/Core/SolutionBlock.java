package Core;

public class SolutionBlock implements Cloneable {

    private int fromWhichNode;
    private int toWhichNode;

    private int steps;
    private int direction;

    SolutionBlock(int fromWhichNode, int toWhichNode, int steps, int direction){

        this.fromWhichNode = fromWhichNode;
        this.toWhichNode = toWhichNode;
        this.steps = steps;
        this.direction = direction;
    }

    public int getSteps(){
        return this.steps;
    }

    public int getDirection(){
        return this.direction;
    }


    @Override 
    protected Object clone() throws CloneNotSupportedException{
        return super.clone();
    } 
}
