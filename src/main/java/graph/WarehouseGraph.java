package graph;

import java.util.HashMap;
import java.util.Map;

public class WarehouseGraph {

    private final HashMap<Point, Node> vertices;
    private final Node startNode;
    private final Node endNode;
    private final int[][] warehouseMapLayout;

    public WarehouseGraph(int[][] warehouseMapLayout, Point startPoint, Point endPoint) {
        this.vertices = new HashMap<>();
        initialiseVertices(warehouseMapLayout);

        this.warehouseMapLayout = warehouseMapLayout;
        this.startNode = getNodeByLocation(startPoint);
        this.endNode = getNodeByLocation(endPoint);
    }

    private void initialiseVertices(int[][] warehouseMap) {
        for (int i = 0; i < warehouseMap.length; i++) {
            for (int j = 0; j < warehouseMap[i].length; j++) {
                Point point = new Point(j, i);
                Node temp;
                int val = warehouseMap[i][j];
                if (!this.vertices.containsKey(point)) {
                    temp = new Node(val, point);
                    this.vertices.put(point, temp);
                } else {
                    temp = this.vertices.get(point);
                }
                if (val == 1) {
                    if (i - 1 >= 0 && warehouseMap[i - 1][j] == 0) {
                        this.addNode(temp, i - 1, j, warehouseMap[i - 1][j]);
                    }
                    if (i + 1 < warehouseMap.length && warehouseMap[i + 1][j] == 0) {
                        this.addNode(temp, i + 1, j, warehouseMap[i + 1][j]);
                    }
                    continue;
                }
                //up
                if (i - 1 >= 0) {
                    int upVal = warehouseMap[i - 1][j];
                    this.addNode(temp, i - 1, j, upVal);
                }
                //down
                if (i + 1 < warehouseMap.length) {
                    int downVal = warehouseMap[i + 1][j];
                    this.addNode(temp, i + 1, j, downVal);
                }
                //left
                if (j - 1 >= 0 && warehouseMap[i][j - 1] != 1) {
                    int leftVal = warehouseMap[i][j - 1];
                    this.addNode(temp, i, j - 1, leftVal);
                }
                //right
                if (j + 1 < warehouseMap[i].length && warehouseMap[i][j + 1] != 1) {
                    int rightVal = warehouseMap[i][j + 1];
                    this.addNode(temp, i, j + 1, rightVal);
                }
            }
        }
    }

    private void addNode(Node mainNode, int y, int x, int val) {
        Point location = new Point(x, y);
        if (this.vertices.containsKey(location)) {
            mainNode.addEdge(this.vertices.get(location));
        } else {
            Node otherNode = new Node(val, location);
            mainNode.addEdge(otherNode);
            this.vertices.put(location, otherNode);
        }
    }

    public Map<Point, Node> getVertices() {
        return vertices;
    }


    public Node getNodeByLocation(Point location){
        if  (this.vertices.containsKey(location)) {
            return this.vertices.get(location);
        }
        return null;
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public int[][] getWarehouseMapLayout() {
        return warehouseMapLayout;
    }
}
