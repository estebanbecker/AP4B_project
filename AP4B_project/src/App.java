import Graph.Graph;
import Graph.Node;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class App {
    public static void main(String[] args) {
        Graph graph = new Graph();

        Integer[][] positions = {
                { 0, 0 },
                { 100, 100 },
                { 200, 200 },
                { 300, 300 },
                { 400, 400 }
        };

        graph.createNodes(positions);

        graph.connectBidirectionalNodes(1, 2, "Rue JO");
        graph.connectBidirectionalNodes(1, 3, "1-3");
        graph.connectBidirectionalNodes(2, 4, "2-4");
        graph.connectUnidirectionalNodes(3, 4, "3-4");

        SwingUtilities.invokeLater(() -> {
            createAndShowGUI(graph);
        });
    }

    private static void createAndShowGUI(Graph graph) {
        JFrame frame = new JFrame("Graph Nodes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new GraphPanel(graph.getNodes());
        frame.getContentPane().add(panel);

        frame.pack();
        frame.setVisible(true);
    }

    private static class GraphPanel extends JPanel {
        private HashMap<Integer, Node> nodes;

        public GraphPanel(HashMap<Integer, Node> nodes) {
            this.nodes = nodes;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setStroke(new BasicStroke(2.0f));


            for (Node node : nodes.values()) {
                int x1 = node.getPosition()[0] + 5;
                int y1 = node.getPosition()[1] + 5;

                g2d.setColor(Color.GREEN);
                g2d.fillOval(x1 - 5, y1 - 5, 10, 10);

                g2d.setColor(Color.BLACK);
                g2d.drawString(Integer.toString(node.getId()), x1 + 10, y1);

                g2d.setColor(Color.BLACK);
                System.out.println("node: " + node.getId() + " edges: " + node.getEdges());

            }
        }

        /*
         * private void drawArrowLine(Graphics2D g2d, int x1, int y1, int x2, int y2,
         * int arrowSize, int lineThickness) {
         * // Set the line thickness
         * g2d.setStroke(new BasicStroke(lineThickness));
         *
         * // Draw the line
         * g2d.drawLine(x1, y1, x2, y2);
         *
         * // Calculate the angle of the line
         * double angle = Math.atan2(y2 - y1, x2 - x1);
         *
         * // Calculate coordinates for arrowhead
         * int x3 = (int) (x2 - arrowSize * Math.cos(angle - Math.PI / 6));
         * int y3 = (int) (y2 - arrowSize * Math.sin(angle - Math.PI / 6));
         * int x4 = (int) (x2 - arrowSize * Math.cos(angle + Math.PI / 6));
         * int y4 = (int) (y2 - arrowSize * Math.sin(angle + Math.PI / 6));
         *
         * // Draw the arrowhead
         * g2d.fillPolygon(new int[]{x2, x3, x4}, new int[]{y2, y3, y4}, 3);
         * }
         */
        public Dimension getPreferredSize() {
            return new Dimension(500, 500);
        }
    }
}