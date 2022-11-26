package graph;

import org.paukov.combinatorics3.Generator;
import stock.ItemOrder;
import java.util.*;
import java.util.stream.Stream;

public class PathFinder {

    public List<Path> findShortestPath(WarehouseGraph warehouseGraph, List<ItemOrder> orderedItems) {
        checkArguments(warehouseGraph, orderedItems);
        if(warehouseGraph.getStartNode()==null ){
            throw new IllegalArgumentException("startPoint is null");
        }
        if(warehouseGraph.getEndNode()==null ){
            throw new IllegalArgumentException("endPoint is null");
        }
        List<Point> orderedItemLocations = new ArrayList<>();
        for (ItemOrder item : orderedItems){
            orderedItemLocations.add(item.location());
        }
        List<Point> locations = new ArrayList<>(List.copyOf(orderedItemLocations));
        Node start = warehouseGraph.getStartNode();
        Node end = warehouseGraph.getEndNode();
        locations.add(start.getLocation());
        locations.add(end.getLocation());
        HashMap<Point, HashMap<Point, Path>> distancePairMap = createItemPairDistanceMap(warehouseGraph, locations);
        List<Point> optimalOrder = findOptimalOrder(distancePairMap,orderedItemLocations,warehouseGraph);
        List<Point> orderExtendedForWeight = accountForWeight(optimalOrder,orderedItems,10,end.getLocation());
        return extractPaths(warehouseGraph, distancePairMap, orderExtendedForWeight);

    }

    /**
     * @param optimalOrder optimal order to collect items in
     * @param orders list of ItemOrder
     * @param maxWeight robots max carry Weight
     * @return new optimal path with trips back to drop off point
     */
    private List<Point> accountForWeight(List<Point>optimalOrder,List<ItemOrder> orders,int maxWeight,Point endPoint){
        HashMap<Point,ItemOrder> locationMap= new HashMap<>();
        for (ItemOrder item : orders){
            locationMap.put(item.location(),item);
        }
        int currentWeight = 0;
        int numItems = 0;
        List<Point> newPath = new ArrayList<>();
        for(int j=0; j < optimalOrder.size();j++){
            Point item = optimalOrder.get(j);
            ItemOrder  order = locationMap.get(item);
            newPath.add(item);
            for (int i =0; i < order.quantity();i++){
                if (currentWeight + order.weight()>maxWeight){
                    newPath.add(endPoint);
                    newPath.add(item);
//                    String out = String.format("drop off: %s numItems: %d",item,numItems);
//                    System.out.println(out);
                    numItems=0;
                    currentWeight = 0;
                }
                currentWeight += order.weight();
                numItems+=1;
                if (currentWeight==maxWeight && j!=optimalOrder.size()-1){
                    currentWeight=0;
//                    String out = String.format("drop off: %s numItems1: %d",item,numItems);
//                    System.out.println(out);
                    numItems =0;
                    newPath.add(endPoint);
                    if (i+1!=order.quantity()){
                        newPath.add(item);
                    }
                }
            }
        }
        if (newPath.get(newPath.size()-1).equals(endPoint)){
            newPath.remove(newPath.size()-1);
        }
        System.out.println(newPath);
        return newPath;
    }
    private List<Point> findOptimalOrder(HashMap<Point, HashMap<Point, Path>> distancePairMap, List<Point> orderedItemLocations,WarehouseGraph graph){
        Node start = graph.getStartNode();
        Node end = graph.getEndNode();
        Stream<List<Point>> permutationStream = Generator.permutation(orderedItemLocations).simple().stream();
        return permutationStream.min((path, pathToCompare) -> {
            int cost = pathCost(start, end, path, distancePairMap);
            int costToCompare = pathCost(start, end, pathToCompare, distancePairMap);
            return Integer.compare(cost, costToCompare);
        }).orElse(new ArrayList<>());
    }

    private List<Path> extractPaths(WarehouseGraph warehouseGraph, HashMap<Point, HashMap<Point, Path>> distancePairMap, List<Point> path) {
        Point start = warehouseGraph.getStartNode().getLocation();
        Point end = warehouseGraph.getEndNode().getLocation();

        if (!distancePairMap.containsKey(start))
            throw new IllegalArgumentException("No starting location in Distance pair map");

        ArrayList<Path> result = new ArrayList<>();

        Path firstPath = distancePairMap.get(start).get(path.get(0));
        result.add(firstPath);
        for (int i = 0; i < path.size() - 1; i++) {
            Point p1 = path.get(i);
            Point p2 = path.get(i + 1);
            if (!distancePairMap.containsKey(p1) || !distancePairMap.get(p1).containsKey(p2)) return new ArrayList<>();
            Path nextPath = distancePairMap.get(path.get(i)).get(path.get(i + 1));
            result.add(nextPath);
        }
        Path lastPath = distancePairMap.get(path.get(path.size() - 1)).get(end);
        result.add(lastPath);
        return result;
    }

    /**
     * This creates a distance  pair map between the locations  of items  in the warehouse.
     * it creates a nested HashMap using Point's  as keys
     * map.get(item1.point).get(item2.point) = path between item1 and item2
     * similarly
     * map.get(item2.point).get(item1.point) = path between item2 and item1
     * map will contain a key for all items in the order aswell as start/end point
     * @param warehouseGraph graph representation of the  warehouse
     * @param items list of items to be collected
     * @return HashMap<Point, HashMap<Point, Path>>
     */

    private HashMap<Point, HashMap<Point, Path>> createItemPairDistanceMap(WarehouseGraph warehouseGraph, List<Point> items) {
        HashMap<Point, HashMap<Point, Path>> result = new HashMap<>();
        for (int i = 0; i < items.size(); i++) {
            for (int j = 0; j < items.size(); j++) {
                if (i == j) continue;

                Point p1 = items.get(i);
                Point p2 = items.get(j);
                if (result.containsKey(p1) && result.get(p1).containsKey(p2)) continue;
                Path distance = this.dijkstra(warehouseGraph.getNodeByLocation(p1), warehouseGraph.getNodeByLocation(p2));
                if (!result.containsKey(p1)) {
                    result.put(p1, new HashMap<>());
                }
                result.get(p1).put(p2, distance);
                if (!result.containsKey(p2)) {
                    result.put(p2, new HashMap<>());
                }
                result.get(p2).put(p1, distance.reversed());
            }
        }
        return result;
    }

    private int pathCost(Node start, Node end, List<Point> items, HashMap<Point, HashMap<Point, Path>> distances) {
        if (items == null || items.size() == 0) {
            return 0;
        }
        Point s = start.getLocation();
        HashMap<Point, Path> startToFirst = distances.get(s);

        int cost = startToFirst.get(items.get(0)).getCost();
        for (int i = 0; i < items.size() - 1; i++) {
            startToFirst = distances.get(items.get(i));
            Point get = items.get(i + 1);
            cost += startToFirst.get(get).getCost();
        }
        HashMap<Point, Path> lastItemToDropOff = distances.get(items.get(items.size() - 1));
        cost += lastItemToDropOff.get(end.getLocation()).getCost();
        return cost;

    }

    public Path dijkstra(Node start, Node end) {
        if (start == null || end == null) {
            return null;
        }
        Point find = end.getLocation();
        PriorityQueue<PQItem> pq = new PriorityQueue<>();
        HashMap<Node, Node> path = new HashMap<>();
        HashMap<Node, Boolean> explored = new HashMap<>();
        exploreNode(start, pq, path, explored, 0);
        while (pq.size() > 0) {
            PQItem check = pq.remove();
            exploreNode(check.next(), pq, path, explored, check.cost());
            if (check.next().getLocation().equals(find)) {
                return getPath(start, end, path);
            }
        }
        return null;
    }

    private void exploreNode(Node start, PriorityQueue<PQItem> pq, HashMap<Node, Node> path, HashMap<Node, Boolean> explored, int cost) {
        for (Map.Entry<Point, Node> pair : start.getEdges().entrySet()) {
            if (explored.containsKey(pair.getValue())) {
                continue;
            }
            explored.put(pair.getValue(), true);
            PQItem newItem = new PQItem(cost + 1, pair.getValue(), start);
            pq.add(newItem);
            path.put(pair.getValue(), start);
        }
    }

    private Path getPath(Node start, Node end, HashMap<Node, Node> path) {
        ArrayList<Point> resultPath = new ArrayList<>();
        if (start.getLocation().equals(end.getLocation())) {
            return new Path(resultPath);
        }
        if (!path.containsKey(end)) {
            return null;
        }
        resultPath.add(end.getLocation());
        Node check = path.get(end);
        resultPath.add(check.getLocation());
        while (!check.getLocation().equals(start.getLocation())) {
            check = path.get(check);
            resultPath.add(check.getLocation());
        }
        Collections.reverse(resultPath);
        return new Path(resultPath);

    }

    private void checkArguments(WarehouseGraph warehouseGraph, List<ItemOrder> itemLocations) {
        if (warehouseGraph == null)
            throw new IllegalArgumentException("Warehouse graph is null");

        if (warehouseGraph.getVertices().isEmpty())
            throw new IllegalArgumentException("No vertices in the Warehouse graph");

        if (itemLocations == null)
            throw new IllegalArgumentException("Item locations either are null");

        if (itemLocations.isEmpty())
            throw new IllegalArgumentException("Item locations list is empty");
    }

}
