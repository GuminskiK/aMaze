package Core;
import java.util.ArrayList;

public class Graph {

    private ArrayList<Integer[]> nodesConnections;
    private Integer currentNodesConnectionsNumber;

    Graph(){
        
        this.nodesConnections = new ArrayList<>();
        this.currentNodesConnectionsNumber = 0;
    }

    public ArrayList<Integer[]> getNodesConnections(){
        return nodesConnections;
    }

    public void setNodeConnectionsValue( int ID, int position, Integer value) {
        nodesConnections.get(ID)[position] = value;
    }

    public Integer getNodeConnectionsValue( int ID, int position) {
        return nodesConnections.get(ID)[position];
    }

    public void addNodeConnection(){
        nodesConnections.add(nullNodeConnection());
    }

    public Integer getCurrentNodesConnectionsNumber(){
        return currentNodesConnectionsNumber;
    }

    public void setCurrentConnectionsNodes(int currentNodesConnectionsNumber){
        this.currentNodesConnectionsNumber = currentNodesConnectionsNumber;
    }

    public void increaseCurrentNodesConnectionsNumber(){
        currentNodesConnectionsNumber++;
    }

    private Integer[] nullNodeConnection() {
        Integer[] x = { null, null, null, null, null, null, null, null, null, null};
        return x;
    }





}
