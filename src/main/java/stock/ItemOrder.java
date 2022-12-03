package stock;

import graph.Point;

public record ItemOrder(String name, Point location, int quantity, int weight) {
    @Override
    public String toString() {
        return  "{name='" + name.split(" ")[0] + '\'' +
                ", quantity=" + quantity + '}';
    }
}
