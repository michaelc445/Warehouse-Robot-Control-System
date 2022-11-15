package main;

import java.util.HashMap;

public class Warehouse {
    private static final HashMap<String, graph.Point> locations = new HashMap<>();

    public Warehouse(){

    }

    public void addItem(String name, graph.Point location){
        locations.put(name,location);
    }
    public void removeItem(String name){
        locations.remove(name);
    }
    public graph.Point getItemLocation(String name){
        return locations.get(name);
    }
    public Object[] getItemNames(){
        return locations.keySet().toArray();
    }
}
