package graph;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    @Test
    void parseFile() throws Exception{
        String fileName = "src/test/java/TestMaps/testMap.csv";
        Graph testGraph  = new Graph(fileName,true);
        int[][] expected = {
                {0,0,0,0,0,0,0},
                {0,1,1,0,1,1,0},
                {0,1,1,0,1,1,0},
                {0,0,0,0,0,0,0},
                {0,1,1,0,1,1,0},
                {0,1,1,0,1,1,0},
                {0,0,0,0,0,0,0}
        };
        int[][] got = testGraph.parseFile(fileName);
        for  (int i=0; i < expected.length;  i++){
            for (int  j=0;  j < expected[i].length;  j++){
                  assertEquals(expected[i][j], got[i][j]);
            }
        }
    }
    @Test
    void createUndirected()   throws Exception {
        String fileName = "src/test/java/TestMaps/testMap.csv";
        Graph  testGraph  = new Graph(fileName,false);
        int[][] expected  = {{2,3,3,3,3,3,2},
                {2,1,1,2,1,1,2},
                {2,1,1,2,1,1,2},
                {3,4,4,4,4,4,3},
                {2,1,1,2,1,1,2},
                {2,1,1,2,1,1,2},
                {2,3,3,3,3,3,2}};
        for(int i =0; i< expected.length; i++) {
            for (int j = 0; j < expected[i].length; j++) {
                Point  location  = new Point(j,i);
                assertEquals(testGraph.getVertices().get(location).getDegree(),expected[i][j]);
            }
        }
    }

    @Test
    void getVertices() throws Exception {
        String fileName = "src/test/java/TestMaps/testMap.csv";
        Graph  testGraph  = new Graph(fileName,false);
        int[][] map = testGraph.getMap();
        int  expectedSize = map.length*map[0].length;
        HashMap<Point, Node> vertices = testGraph.getVertices();
        assertEquals(vertices.size(),expectedSize);
        for  (int i =0; i < map.length;i++){
            for(int j =0; j < map[i].length;j++){
                Point location = new Point(j,i);
                assertNotNull(vertices.get(location));
            }
        }
    }

    @Test
    void addNode() throws Exception {
        String fileName = "src/test/java/TestMaps/testMap.csv";
        Graph  testGraph  = new Graph(fileName,false);
        int size = testGraph.getVertices().size();
        Point p1 = new Point(0,0);
        Node node = testGraph.getNode(p1);
        testGraph.addNode(node,-1,-1,4);
        assertEquals(testGraph.getVertices().size(),size+1);
        assertNotNull(testGraph.getNode(new Point(-1,-1)));
    }

    @Test
    void dijkstra() throws Exception {
        String fileName = "src/test/java/TestMaps/testMap.csv";
        Graph  testGraph  = new Graph(fileName,false);
        Point p1 = new Point(0,0);
        Point p2 = new Point(5,4);
        Node node1 = testGraph.getNode(p1);
        Node node2 =  testGraph.getNode(p2);
        Path  got  = testGraph.dijkstra(node1,node2);
        ArrayList<Point> ex  =  new ArrayList<>();
        ex.add(new   Point(0,0));
        ex.add(new   Point(1,0));
        ex.add(new   Point(2,0));
        ex.add(new   Point(3,0));
        ex.add(new   Point(3,1));
        ex.add(new   Point(3,2));
        ex.add(new   Point(3,3));
        ex.add(new   Point(4,3));
        ex.add(new   Point(5,3));
        ex.add(new   Point(5,4));
        Path expected = new Path(ex);
        assertEquals(expected,got);
        //node not in graph
        Node node3 = testGraph.getNode(new Point(-2,-3));
        got = testGraph.dijkstra(node1,node3);
        assertNull(got);
    }

    @Test
    void  getPathMultipleNodes() throws Exception {
        String fileName = "src/test/java/TestMaps/testMap.csv";
        Graph  testGraph  = new Graph(fileName,false);
        Node start= testGraph.getNode(new Point(0,0));
        Node end = testGraph.getNode(new Point(0,5));
        ArrayList<Point> test = new ArrayList<>();
        test.add(new Point(0,2));
        test.add(new Point(0,4));
        test.add(new Point(0,6));
        ArrayList<Path> got  = testGraph.getPathMultipleNodes(start,end,test);
        ArrayList<Path> expected = new ArrayList<>();
        ArrayList<Point> temp1 = new  ArrayList<>();
        ArrayList<Point> temp2 = new  ArrayList<>();
        ArrayList<Point> temp3 = new  ArrayList<>();
        ArrayList<Point> temp4 = new  ArrayList<>();
        temp1.add(new Point(0,0));
        temp1.add(new Point(0,1));
        temp1.add(new Point(0,2));
        temp2.add(new Point(0,2));
        temp2.add(new Point(0,3));
        temp2.add(new Point(0,4));
        temp3.add(new Point(0,4));
        temp3.add(new Point(0,5));
        temp3.add(new Point(0,6));
        temp4.add(new Point(0,6));
        temp4.add(new Point(0,5));
        expected.add(new Path(temp1));
        expected.add(new Path(temp2));
        expected.add(new Path(temp3));
        expected.add(new Path(temp4));
        assertEquals(expected,got);
    }
    @Test
    void shortestOrderPath() throws Exception {
        String fileName = "src/test/java/TestMaps/testMapLarge.csv";
        Graph  testGraph  = new Graph(fileName,false);
        //TODO  finish  test
        Node start = testGraph.getNode(testGraph.getStartPoint());
        Node end = testGraph.getNode(testGraph.getEndPoint());
        List<Point>  shelves  = testGraph.getShelves();
        ArrayList<Point> items = new ArrayList<>();
        items.add(shelves.get(5));
        items.add(shelves.get(10));
        items.add(shelves.get(6));
        items.add(shelves.get(15));
        items.add(shelves.get(8));

        ArrayList<Path> got= testGraph.shortestOrderPath(start,end,items);
        System.out.println(got);
        assertNotNull(got);
    }
}