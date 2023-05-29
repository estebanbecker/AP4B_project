import Graph.Graph;

import javax.swing.*;

import static UI.GraphEditor.createAndShowGUI;


public class App {
    public static void main(String[] args) {
        Graph graph = new Graph();

        Integer[][] positions = {
                { 0, 0 },
                { 100, 100 },
                { 200, 200 },
                { 300, 300 },
                { 400, 400 },
                { 500, 1200}
        };

        graph.createNodes(positions);

        graph.connectBidirectionalNodes(1, 2, "Rue JO");
        graph.connectBidirectionalNodes(2, 3, "very silly");
        graph.connectBidirectionalNodes(4, 5, ":333 ");
        graph.connectUnidirectionalNodes(3, 4, "3-4");
        graph.connectUnidirectionalNodes(4, 6, "big");

        SwingUtilities.invokeLater(() -> {
            createAndShowGUI(graph);
        });
    }
}