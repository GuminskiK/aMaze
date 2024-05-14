public class Node {

    private int id;
    public int directionFrom; //skąd przyszliśmy
    public int directionToMin; //do jakiego poszliśmy, ale się cofneliśmy
    public boolean visited;

    Node(int id){
        this.id = id;
        this.directionToMin = 0;
        this.visited = false;
        this.directionFrom = -1;
    }

}
