package graph;


import org.jetbrains.annotations.NotNull;

public class PQItem implements Comparable{
    private final int cost;
    private final Node next;
    private final Node from;

    public PQItem(int cost,Node next, Node from){
        this.cost  =  cost;
        this.next=next;
        this.from=from;
    }
    public  int getCost(){
        return this.cost;
    }
    public Node getNext(){
        return this.next;
    }
    public Node getFrom(){
        return this.from;
    }
    @Override
    public int compareTo(@NotNull Object o) {
        if (this==o){
            return 0;
        }
        if (getClass()  !=  o.getClass()){
            return -1;
        }
        PQItem other  =(PQItem)  o;
        if (this.cost  > other.getCost()){
            return 1;
        }else  if (this.cost < other.getCost()){
            return -1;
        }
        return 0;
    }
    public  String  toString(){
        return String.format("%d,(%s)",this.getCost(),this.next.getLocation());
    }
}
