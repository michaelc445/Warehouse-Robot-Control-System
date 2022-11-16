package main;

import graph.Point;
import graph.WarehouseGraph;
import stock.Item;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private final List<Item> items = new ArrayList<>();
    private final List<Point> shelveLocations;
    private final Point chargingArea;
    private final Point dispatchedArea;
    private final WarehouseGraph warehouseGraph; // TODO: think about getting rid of the field


    public Warehouse(WarehouseGraph warehouseGraph) {
        this.warehouseGraph = warehouseGraph;
        this.shelveLocations = createShelveList(warehouseGraph.getWarehouseMapLayout());
        this.chargingArea = warehouseGraph.getStartNode().getLocation();
        this.dispatchedArea = warehouseGraph.getEndNode().getLocation();
    }

    private ArrayList<Point> createShelveList(int[][] map){
        ArrayList<Point> shelveList = new ArrayList<>();
        for (int i =0; i  < map.length;i++){
            for(int j=0;  j  < map[i].length;j++){
                if (map[i][j]==1){
                    shelveList.add(new  Point(j,i));
                }
            }
        }
        return shelveList;
    }

    public void addItem(Item item){
        items.add(item);
    }
    public void removeItemByName(String name){
        items.removeIf(item -> item.name().equals(name));
    }
    public graph.Point getItemLocation(String name){
        return items.stream()
                .filter(item -> item.name().equals(name))
                .findFirst()
                .orElseThrow()
                .location();
    }
    public List<String> getItemNames(){
        return items.stream().map(Item::name).toList();
    }

    public List<Item> getItems() {
        return items;
    }


    public Point getChargingArea() {
        return chargingArea;
    }

    public Point getDispatchedArea() {
        return dispatchedArea;
    }

    public int[][] getMapLayout() {
        return warehouseGraph.getWarehouseMapLayout();
    }

    public List<Point> getItemLocations() {
        return items.stream().map(Item::location).toList();
    }

    public List<Point> getShelveLocations() {
        return shelveLocations;
    }

    public WarehouseGraph getWarehouseGraph() {
        return warehouseGraph;
    }
}
