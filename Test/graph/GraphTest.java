package graph;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    @Test
    void parseFile() throws Exception{
        //ArrayList<String[]>  expected = new ArrayList<>();
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
        int[][] got = testGraph.parseFile(fileName);
        for(int i =0; i< got.length; i++) {
            for (int j = 0; j < got[i].length; j++) {
                String name = String.format("%d,%d",i,j);
                System.out.print(testGraph.getVertices().get(name).getDegree()+",");
            }
            System.out.print("\n");
        }
    }

    @Test
    void getVertices() {
    }

    @Test
    void addNode() {
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