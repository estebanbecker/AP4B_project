import Graph.Graph;
import PathFinder.*;
import Graph.File;

import javax.swing.*;

import static UI.GraphEditor.createAndShowGUI;


public class App {
    public static void main(String[] args) {
        Graph graph = new Graph();

        Integer[][] positions = {
                { 0, 0 },
                { 100, 100 },
                { 200, 100 },
                { 80, 150 },
                { 400, 400 },
                { 500, 200},
                { 600, 300}
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

        graph.deleteNode(2, true);
        graph.updatePosition(0, 50, 500);

        SwingUtilities.invokeLater(() -> {
            createAndShowGUI(graph);
        });
    }
}