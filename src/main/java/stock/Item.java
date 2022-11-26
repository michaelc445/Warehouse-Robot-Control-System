package stock;

import graph.Point;

/**
 * Item class is a data class, containing the item name and item location
 * @param name - name of the item
 * @param location - location of the item, represented as a Point
 */
public record Item(String name, Point location,int weight) {}
