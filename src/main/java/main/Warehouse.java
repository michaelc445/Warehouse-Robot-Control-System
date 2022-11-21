package main;

import graph.Point;
import graph.WarehouseGraph;
import stock.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warehouse {
    private final HashMap<String,Item> items;
    private final List<Point> shelveLocations;
    private final Point chargingArea;
    private final Point dispatchedArea;
    private final WarehouseGraph warehouseGraph;

    private static final int ORDER_LIMITATION = 5;

    public Warehouse(WarehouseGraph warehouseGraph) {
        this.items=  new HashMap<>();
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

    public void addItem(String name, Point location){
        items.put(name,new Item(name,location));
    }
    public void removeItemByName(String name){
        items.remove(name);
    }
    public Point getItemLocation(String name){
        if (!items.containsKey(name)){
            return null;
        }
        return items.get(name).location();
    }
    public List<String> getItemNames(){
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Item> entry:  items.entrySet()){
            result.add(entry.getKey());
        }
        return result;
    }

    public List<Item> getItems() {
        List<Item> result = new ArrayList<>();
        for (Map.Entry<String, Item> entry:  items.entrySet()){
            result.add(entry.getValue());
        }
        return result;
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
        List<Point> result = new ArrayList<>();
        for (Map.Entry<String, Item> entry:  items.entrySet()){
            result.add(entry.getValue().location());
        }
        return result;
    }

    public List<Point> getShelveLocations() {
        return shelveLocations;
    }

    public WarehouseGraph getWarehouseGraph() {
        return warehouseGraph;
    }

    public int getOrderLimitation() {
        return ORDER_LIMITATION;
    }
}
