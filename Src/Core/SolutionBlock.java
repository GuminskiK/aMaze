package Core;

public class SolutionBlock implements Cloneable {

    private int steps;
    private int direction;

    SolutionBlock( int steps, int direction){

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
