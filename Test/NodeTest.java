import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @Test
    void getValue() {
        Node testNode = new Node(0,"(0,0)");
        assertEquals(0,testNode.getValue());
    }

    @Test
    void getLocation() {
        Node testNode = new Node(0,"(0,0)");
        assertEquals("(0,0)",testNode.getLocation());
    }

    @Test
    void getEdges() {
        Node node1 = new Node(1,"(0,0)");
        Node node2 = new Node(2,"(0,1)");
        node1.addEdge(node2);
        node2.addEdge(node1);

        HashMap<String,Node> node1Expected = new HashMap<>();
        node1Expected.put("(0,1)",node2);
        HashMap<String,Node> node2Expected = new HashMap<>();
        node2Expected.put("(0,0)",node1);

        assertEquals(node1Expected,node1.getEdges());
        assertEquals(node2Expected,node2.getEdges());
    }

    @Test
    void addEdge() {
        Node node1 = new Node(1,"(0,0)");
        Node node2 = new Node(2,"(0,1)");
        node1.addEdge(node2);
        node2.addEdge(node1);

        HashMap<String,Node> node1Expected = new HashMap<>();
        node1Expected.put("(0,1)",node2);
        HashMap<String,Node> node2Expected = new HashMap<>();
        node2Expected.put("(0,0)",node1);

        assertEquals(node1Expected,node1.getEdges());
        assertEquals(node2Expected,node2.getEdges());
    }

    @Test
    void removeEdge() {
        Node node1 = new Node(1,"(0,0)");
        Node node2 = new Node(2,"(0,1)");
        Node node3 = new Node(3,"(0,2)");
        node1.addEdge(node2);
        node2.addEdge(node1);
        node2.addEdge(node3);
        node3.addEdge(node2);
        node2.removeEdge(node3);

        HashMap<String,Node> node1Expected = new HashMap<>();
        node1Expected.put("(0,1)",node2);
        HashMap<String,Node> node2Expected = new HashMap<>();
        node2Expected.put("(0,0)",node1);

        HashMap<String,Node> node3Expected = new HashMap<>();
        node3Expected.put("(0,1)",node2);

        assertEquals(node1Expected,node1.getEdges());
        assertEquals(node2Expected,node2.getEdges());
        assertEquals(node3Expected,node3.getEdges());
    }

    @Test
    void getDegree() {
        Node testNode1 =  new Node(0,"1,0");
        Node testNode2 =  new Node(1,"2,0");
        Node testNode3 =  new Node(0,"3,0");

        testNode1.addEdge(testNode2);
        testNode1.addEdge(testNode3);
        testNode3.addEdge(testNode1);

        assertEquals(testNode1.getDegree(),2);
        assertEquals(testNode2.getDegree(),0);
        assertEquals(testNode3.getDegree(),1);

    }
}