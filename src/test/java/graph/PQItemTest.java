package graph;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PQItemTest {

    @Test
    void getCost() {
        Point p1 = new Point(0,1);
        Point p2 = new Point(0,2);
        Node to = new Node(0,p1);
        Node from = new Node(1,p2);
        PQItem test = new PQItem(0,to,from);
        assertEquals(test.cost(),0);
    }

    @Test
    void getNext() {
        Point p1 = new Point(0,1);
        Point p2 = new Point(0,2);
        Node to = new Node(0,p1);
        Node from = new Node(1,p2);
        PQItem test = new PQItem(0,to,from);
        assertEquals(test.next(),to);
    }

    @Test
    void getFrom() {
        Point p1 = new Point(0,1);
        Point p2 = new Point(0,2);
        Node to = new Node(0,p1);
        Node from = new Node(1,p2);
        PQItem test = new PQItem(0,to,from);
        assertEquals(test.from(),from);
    }


    @Test
    void testCompareTo() {
        Point p1 = new Point(0,1);
        Point p2 = new Point(0,2);
        Node  node1 = new Node(0,p1);
        Node  node2 = new Node(1,p2);
        PQItem  pq1 = new PQItem(1,node1,node2);
        PQItem  pq2 = new PQItem(2,node1,node2);
        assertTrue(pq1.compareTo(pq2)<0);
        assertTrue(pq2.compareTo(pq1)>0);
        assertFalse(pq1.compareTo(pq2)>0);
        assertFalse(pq2.compareTo(pq1)<0);
        assertEquals(pq1.compareTo(pq1),0);
    }


    @Test
    void testToString() {
        Point p1 = new Point(0,0);
        Point p2 = new Point(0,1);
        Node node1 = new Node(0,p1);
        Node node2 = new  Node(1,p2);
        PQItem testItem = new PQItem(0,node1,node2);

        assertEquals("0,(0,0)",testItem.toString());
    }
}