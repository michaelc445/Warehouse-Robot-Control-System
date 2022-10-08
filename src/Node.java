import java.util.HashMap;
public class Node {
    private int value;
    private String location;
    private HashMap<String,Node> edges;

    public Node(int value,String location){
        if (location==null)return;
        this.value = value;
        this.location=location;
        this.edges = new HashMap<>();
    }
    public int getValue(){
        return this.value;
    }
    public  String  getLocation(){
        return this.location;
    }
    public HashMap<String,Node> getEdges(){
        return this.edges;
    }
    public void addEdge(Node neighbour){
        if (neighbour==null || neighbour ==this)return;
        this.edges.put(neighbour.getLocation(),neighbour);
    }
    public void removeEdge(Node neighbour){
        this.edges.remove(neighbour.getLocation());
    }
}
