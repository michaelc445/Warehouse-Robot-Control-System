package graph;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    @Test
    void parseFile() throws Exception{
        String fileName = "Test/TestMaps/testMap.csv";
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
    void createDirected() {
    }

    @Test
    void createUndirected()   throws Exception {
        String fileName = "Test/TestMaps/testMap.csv";
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
                Point  location  = new  Point(j,i);
                assertEquals(testGraph.getVertices().get(location).getDegree(),expected[i][j]);
            }
        }
    }

    @Test
    void getVertices() throws Exception {
        String fileName = "Test/TestMaps/testMap.csv";
        Graph  testGraph  = new Graph(fileName,false);
        int[][] map = testGraph.getMap();
        int  expectedSize = map.length*map[0].length;
        HashMap<Point,Node> vertices = testGraph.getVertices();
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
        String fileName = "Test/TestMaps/testMap.csv";
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
        String fileName = "Test/TestMaps/testMap.csv";
        Graph  testGraph  = new Graph(fileName,false);
        Point p1 = new Point(0,0);
        Point p2 = new Point(5,4);
        Node node1 = testGraph.getNode(p1);
        Node node2 =  testGraph.getNode(p2);
        ArrayList<Point>  got  = testGraph.dijkstra(node1,node2);
        ArrayList<Point> expected   =  new ArrayList<>();
        expected.add(new   Point(0,0));
        expected.add(new   Point(1,0));
        expected.add(new   Point(2,0));
        expected.add(new   Point(3,0));
        expected.add(new   Point(3,1));
        expected.add(new   Point(3,2));
        expected.add(new   Point(3,3));
        expected.add(new   Point(4,3));
        expected.add(new   Point(5,3));
        expected.add(new   Point(5,4));
        assertEquals(expected,got);
        //node not in graph
        Node node3 = testGraph.getNode(new Point(-2,-3));
        got = testGraph.dijkstra(node1,node3);
        assertNull(got);
    }
}