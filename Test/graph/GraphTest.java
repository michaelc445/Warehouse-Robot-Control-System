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
                String name = String.format("%d,%d",i,j);
                assertEquals(testGraph.getVertices().get(name).getDegree(),expected[i][j]);
            }
        }
    }

    @Test
    void getVertices() throws Exception {
        String fileName = "Test/TestMaps/testMap.csv";
        Graph  testGraph  = new Graph(fileName,false);
        int[][] map = testGraph.getMap();
        int  expectedSize = map.length*map[0].length;
        HashMap<String,Node> vertices = testGraph.getVertices();
        assertEquals(vertices.size(),expectedSize);
        for  (int i =0; i < map.length;i++){
            for(int j =0; j < map[i].length;j++){
                String name = String.format("%d,%d",i,j);
                assertNotNull(vertices.get(name));
            }
        }
    }

    @Test
    void addNode() throws Exception {
        String fileName = "Test/TestMaps/testMap.csv";
        Graph  testGraph  = new Graph(fileName,false);
        int size = testGraph.getVertices().size();
        Node node = testGraph.getNode("0,0");
        testGraph.addNode(node,-1,-1,4);
        assertEquals(testGraph.getVertices().size(),size+1);
        assertNotNull(testGraph.getNode("-1,-1"));
    }

    @Test
    void dijkstra() throws Exception {
        String fileName = "Test/TestMaps/testMap.csv";
        Graph  testGraph  = new Graph(fileName,false);
        Node node1 = testGraph.getNode("0,0");
        Node node2 =  testGraph.getNode("5,4");
        ArrayList<String>  got  = testGraph.dijkstra(node1,node2);

        ArrayList<String> expected   =  new ArrayList<>();
        expected.add("0,0");
        expected.add("1,0");
        expected.add("2,0");
        expected.add("3,0");
        expected.add("4,0");
        expected.add("5,0");
        expected.add("6,0");
        expected.add("6,1");
        expected.add("6,2");
        expected.add("6,3");
        expected.add("6,4");
        expected.add("5,4");
        assertEquals(got,expected);
        //node not in graph
        Node node3 = testGraph.getNode("-1,4");
        got = testGraph.dijkstra(node1,node3);
        assertNull(got);
    }
}