import Graph.Graph;
import PathFinder.*;

import javax.swing.*;

import static UI.GraphEditor.createAndShowGUI;


public class App {
    public static void main(String[] args) {
        Graph graph = new Graph();

        Float[][] positions = new Float[][] {
            { 0.0f, 0.0f },
            { 100.0f, 100.0f },
            { 200.0f, 100.0f },
            { 80.0f, 150.0f },
            { 400.0f, 400.0f },
            { 500.0f, 200.0f },
            { 600.0f, 300.0f }
        };

        graph.createNodes(positions);

        graph.connectBidirectionalNodes(0, 1, "Rue JO");
        graph.connectBidirectionalNodes(1, 2, "Rue JO");
        graph.connectBidirectionalNodes(2, 3, "very silly");
        graph.connectUnidirectionalNodes(1, 3, "Rue JO");
        graph.connectBidirectionalNodes(4, 5, ":333 ");
        graph.connectUnidirectionalNodes(3, 4, "3-4");
        graph.connectUnidirectionalNodes(4, 6, "big");
        graph.connectUnidirectionalNodes(6, 5, "big");
        graph.connectUnidirectionalNodes(6, 6, "big");

        graph.updateEdgeName(6, 5, "big2");

        graph.deleteNode(2, true);
        graph.updatePosition(0, 50f, 500f);

        Dijkstra solver = new Dijkstra();

        IntFloatList result = solver.findShortestPath(graph, 3, 5);

        System.out.println("Path: ");
        for (int i = 0; i < result.getIntList().length; i++) {
            System.out.println(result.getIntList()[i]);
        }
        System.out.println("Distance: " + result.getFloatValue());

        System.out.println(graph.getEdgeWeight(3, 4));
        System.out.println(graph.getEdgeWeight(4, 5));

        SwingUtilities.invokeLater(() -> {
            createAndShowGUI(graph);
        });
    }
}