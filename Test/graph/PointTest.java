package graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void getX() {
        Point test = new Point(0,0);
        assertEquals(test.getX(),0);
    }

    @Test
    void getY() {
        Point test = new Point(0,0);
        assertEquals(test.getY(),0);
    }

    @Test
    void testEquals() {
        Point test1 = new Point(0,0);
        Point test2 = new Point(0,0);
        Point test3 = new Point(0,1);
        assertEquals(test1,test2);
        assertNotEquals(test1,test3);
    }

    @Test
    void testToString() {
        Point test1 = new Point(0,0);
        Point test2 = new Point(0,1);
        assertEquals(test1.toString(),"0,0");
        assertEquals(test2.toString(),"0,1");
    }

    @Test
    void testHashCode() {
        Point test1 = new Point(0,0);
        Point test2 = new Point(0,0);
        Point test3 = new Point(0,1);
        assertEquals(test1.hashCode(),test2.hashCode());
        assertNotEquals(test1.hashCode(),test3.hashCode());
    }
}