import java.util.ArrayList;

public class Graph {

    private ArrayList<Integer[]> nodes;
    private int currentNodesNumber;

    Graph(){
        
        this.nodes = new ArrayList<>();
        this.currentNodesNumber = 0;
    }

    public ArrayList<Integer[]> getNodes(){
        return nodes;
    }

    public void setNodeValue( int ID, int position, Integer value) {
        nodes.get(ID)[position] = value;
    }

    public Integer getNodeValue( int ID, int position) {
        return nodes.get(ID)[position];
    }

    public void addNode(){
        nodes.add(nullNode());
    }

    public int getCurrentNodes(){
        return currentNodesNumber;
    }

    public int setCurrentNodes(){
        return currentNodesNumber;
    }

    public void addCurrentNodes(){
        currentNodesNumber++;
    }

    private Integer[] nullNode() {
        Integer[] x = { null, null, null, null, null, null, null, null, null, null};
        return x;
    }





}
