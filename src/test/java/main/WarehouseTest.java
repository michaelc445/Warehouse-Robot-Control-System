package main;
import graph.Point;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
public class WarehouseTest {

    @Test
    void addItem() throws Exception{
        Warehouse locs1 = new Warehouse();
        locs1.addItem("test1", new Point(1,1));
        assertEquals(locs1.getItemLocation("test1"), new Point(1,1));
    }

    @Test
    void getItemNames() throws Exception{
        Warehouse locs2 = new Warehouse();
        locs2.addItem("test1", new Point(1,1));
        locs2.addItem("test2", new Point(2,2));
        Object[] expected1 = {"test2", "test1"};
        assertArrayEquals(locs2.getItemNames(),expected1);
    }

    @Test
    void getItemLocation() throws Exception{
        Warehouse locs3 = new Warehouse();
        locs3.addItem("test1", new Point(1,1));
        assertEquals(locs3.getItemLocation("test1"), new Point(1,1));
    }

    @Test
    void removeItem() throws Exception{
        Warehouse locs4 = new Warehouse();
        locs4.addItem("test1", new Point(1,1));
        locs4.addItem("test2", new Point(2,2));
        locs4.removeItem("test2");
        Object[] expected2 = {"test1"};
        assertArrayEquals(locs4.getItemNames(), expected2);
    }
}
