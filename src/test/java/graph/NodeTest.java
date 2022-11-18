package graph;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NodeTest {

    @Test
    void getValue() {
        Point p1 = new Point(0,0);
        Node test = new Node(0, p1);
        assertEquals(test.getValue(), 0);
    }

    @Test
    void removeEdge() {
        Point p1 = new Point(0,0);
        Point p2 = new Point(0,1);
        Point p3 = new Point(0,2);
        Node node1 = new Node(1, p1);
        Node node2 = new Node(2, p2);
        Node node3 = new Node(3, p3);
        node1.addEdge(node2);
        node2.addEdge(node1);
        node2.addEdge(node3);
        node3.addEdge(node2);
        node2.removeEdge(node3);

        HashMap<Point, Node> node1Expected = new HashMap<>();
        node1Expected.put(p2, node2);
        HashMap<Point, Node> node2Expected = new HashMap<>();
        node2Expected.put(p1, node1);

        HashMap<Point, Node> node3Expected = new HashMap<>();
        node3Expected.put(p2, node2);

        assertEquals(node1Expected, node1.getEdges());
        assertEquals(node2Expected, node2.getEdges());
        assertEquals(node3Expected, node3.getEdges());
    }

    @Test
    void testToString() {
        Point location = new Point(1,0);
        Node test = new Node(0, location);
        assertEquals( "(1,0)",test.toString());
    }

    @Test
    void getLocation() {
        Point p1 = new Point(1,0);
        Node test = new Node(0, p1);
        assertEquals(test.getLocation(), p1);
    }

    @Test
    void getEdges() {
        Point p1 = new Point(0,0);
        Point p2 = new Point(0,1);

        Node node1 = new Node(1, p1);
        Node node2 = new Node(2, p2);
        node1.addEdge(node2);
        node2.addEdge(node1);

        HashMap<Point, Node> node1Expected = new HashMap<>();
        node1Expected.put(p2, node2);
        HashMap<Point, Node> node2Expected = new HashMap<>();
        node2Expected.put(p1, node1);

        assertEquals(node1Expected, node1.getEdges());
        assertEquals(node2Expected, node2.getEdges());
    }

    @Test
    void addEdge() {
        Point p1 = new Point(0,0);
        Point p2 = new Point(0,1);

        Node node1 = new Node(1, p1);
        Node node2 = new Node(2, p2);
        node1.addEdge(node2);
        node2.addEdge(node1);

        HashMap<Point, Node> node1Expected = new HashMap<>();
        node1Expected.put(p2, node2);
        HashMap<Point, Node> node2Expected = new HashMap<>();
        node2Expected.put(p1, node1);

        assertEquals(node1Expected, node1.getEdges());
        assertEquals(node2Expected, node2.getEdges());
    }

    @Test
    void getDegree() {
        Point p1 = new Point(1,0);
        Point p2 = new Point(2,0);
        Point p3 = new Point(3,0);
        Node testNode1 = new Node(0, p1);
        Node testNode2 = new Node(1, p2);
        Node testNode3 = new Node(0, p3);

        testNode1.addEdge(testNode2);
        testNode1.addEdge(testNode3);
        testNode3.addEdge(testNode1);

        assertEquals(testNode1.getDegree(), 2);
        assertEquals(testNode2.getDegree(), 0);
        assertEquals(testNode3.getDegree(), 1);
    }

}