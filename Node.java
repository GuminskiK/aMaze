public class Node {

    int ID;
    public int directionFrom; //skąd przyszliśmy
    public int directionToMin; //do jakiego poszliśmy, ale się cofneliśmy
    public boolean visited;

    Node(int ID){
        this.ID = ID;
        this.directionToMin = 0;
        this.visited = false;
        this.directionFrom = -1;
    }

}
