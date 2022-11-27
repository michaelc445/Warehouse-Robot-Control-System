package stock;

import graph.Point;

public record ItemOrder(String name, Point location, int quantity, int weight) {
}
