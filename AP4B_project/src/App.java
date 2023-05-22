import Graph.Edge;
import Graph.Graph;
import Graph.Node;

public class App {
    public static void main(String[] args) throws Exception {
        Graph graph = new Graph();
        
        Integer[][] positions = {
            {0, 0},
            {1, 1},
            {2, 2},
            {3, 3},
            {4, 4}
        };

        graph.createNodes(positions);

        graph.connectBidirectionalNodes(1, 2, "Rue JO");

        graph.connectBidirectionalNodes(1, 3, "1-3");

        graph.connectBidirectionalNodes(2, 4, "2-4");

        graph.connectUnidirectionalNodes(3, 4, "3-4");

        graph.print();

        Integer[] neight= graph.getNeigbourgh(1);

        System.out.println("Neight of 1: ");
        for (Integer integer : neight) {
            System.out.println(integer);
        }

        String labels = graph.getEdgeLabel(1, 2);

        System.out.println("Label of 1-2: " + labels);

        Float weight = graph.getEdgeWeight(1, 2);

        System.out.println("Weight of 1-2: " + weight);


    }
}
