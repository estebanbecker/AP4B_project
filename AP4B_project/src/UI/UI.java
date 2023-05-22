package UI;

import Graph.Edge;
import Graph.Graph;
import Graph.Node;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.util.HashMap;

public class UI extends Application {
    private Graph graph;

    public UI(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();

        HashMap<Integer, Node> nodes = graph.getNodes();

        for (Node node : nodes.values()) {
            Integer[] position = node.getPosition();
            Label label = new Label(node.getId().toString());
            label.setLayoutX(position[0] * 50);
            label.setLayoutY(position[1] * 50);
            pane.getChildren().add(label);

            HashMap<Integer, Edge> edges = node.getEdges();

            for (Edge edge : edges.values()) {
                Integer nodeToId = edge.getNodeToId();
                Node nodeTo = nodes.get(nodeToId);
                Integer[] positionTo = nodeTo.getPosition();

                Line line = new Line();
                line.setStartX(position[0] * 50);
                line.setStartY(position[1] * 50);
                line.setEndX(positionTo[0] * 50);
                line.setEndY(positionTo[1] * 50);

                Polygon arrowHead = new Polygon();
                arrowHead.getPoints().addAll(0.0, 0.0, -10.0, 5.0, -10.0, -5.0);
                arrowHead.setLayoutX(positionTo[0] * 50);
                arrowHead.setLayoutY(positionTo[1] * 50);
                arrowHead.setRotate(Math.toDegrees(Math.atan2(line.getEndY() - line.getStartY(),
                        line.getEndX() - line.getStartX())));

                pane.getChildren().addAll(line, arrowHead);
            }
        }

        Scene scene = new Scene(pane, 800, 600);

        primaryStage.setTitle("Graph Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
