package Core;
public class Node {

    private int directionFrom; //skąd przyszliśmy
    private int directionToMin; //do jakiego poszliśmy, ale się cofneliśmy
    private boolean visited;

    Node(){
        
        this.directionToMin = 0;
        this.visited = false;
        this.directionFrom = -1;
    }

    public int getDirectionFrom(){
        return this.directionFrom;
    }

    public void setDirectionFrom(int directionFrom){
        this.directionFrom = directionFrom;
    }

    public int getDirectionToMin (){
        return this.directionToMin;
    }

    public void setDirectionToMin( int directionToMin){
        this.directionToMin = directionToMin;
    }

    public boolean getVisited (){
        return this.visited;
    }

    public void setVisited( boolean visited){
        this.visited = visited;
    }
}
