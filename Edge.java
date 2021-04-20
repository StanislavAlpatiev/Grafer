// PROG2 VT2021, Övning 4
// Grupp 033
// Viggo Asklöf vias2878
// Stanislav Alpatiev stanl5991

import java.io.Serializable;

public class Edge<N> implements Serializable {
    private final N destination;
    private final String name;
    private int weight;

    public Edge(N destination, String name, int weight){
        this.destination = destination;
        this.name = name;
        this.weight = weight;
    }

    public N getDestination(){
        return destination;
    }

    public String getName(){
        return name;
    }

    public int getWeight(){
        return weight;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public String toString(){
        return destination.toString();
    }
}

