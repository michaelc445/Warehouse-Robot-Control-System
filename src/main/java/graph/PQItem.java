package graph;

public record PQItem(int cost, Node next, Node from) implements Comparable {
    @Override
    public int compareTo( Object o) {
        if (this == o) {
            return 0;
        }
        if (getClass() != o.getClass()) {
            return -1;
        }
        PQItem other = (PQItem) o;
        if (this.cost > other.cost()) {
            return 1;
        } else if (this.cost < other.cost()) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return String.format("%d,%s", this.cost(), this.next.getLocation());
    }
}
