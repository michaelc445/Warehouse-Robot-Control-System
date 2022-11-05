package graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PQItemTest {

    @Test
    void getCost() {
        Node to = new Node(0,"0,1");
        Node from = new Node(1,"0,2");
        PQItem test = new PQItem(0,to,from);
        assertEquals(test.getCost(),0);
    }

    @Test
    void getNext() {
        Node to = new Node(0,"0,1");
        Node from = new Node(1,"0,2");
        PQItem test = new PQItem(0,to,from);
        assertEquals(test.getNext(),to);
    }

    @Test
    void getFrom() {
        Node to = new Node(0,"0,1");
        Node from = new Node(1,"0,2");
        PQItem test = new PQItem(0,to,from);
        assertEquals(test.getFrom(),from);
    }


    @Test
    void testCompareTo() {
        Node  node1 = new Node(0,"0,0");
        Node  node2 = new Node(1,"0,1");
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
        Node node1 = new Node(0,"0,0");
        Node node2 = new  Node(1,"0,1");
        PQItem testItem = new PQItem(0,node1,node2);

        assertEquals(testItem.toString(),"0,(0,0)");
    }
}