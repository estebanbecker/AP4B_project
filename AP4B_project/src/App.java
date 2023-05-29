import Graph.Graph;
import Graph.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

        private int offsetX = 0; // X offset for dragging
        private int offsetY = 0; // Y offset for dragging
        private double scale = 1.0; // Zoom scale

        private int startX; // Start X position for dragging
        private int startY; // Start Y position for dragging

        //Create a Graph Editor method
        public void GraphEditor() {
            // Add mouse listener for dragging
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    startX = e.getX();
                    startY = e.getY();
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    int deltaX = e.getX() - startX;
                    int deltaY = e.getY() - startY;

                    offsetX += deltaX;
                    offsetY += deltaY;

                    startX = e.getX();
                    startY = e.getY();

                    repaint();
                }
            });

            // Add mouse wheel listener for zooming
            addMouseWheelListener(new MouseWheelListener() {
                public void mouseWheelMoved(MouseWheelEvent e) {
                    int notches = e.getWheelRotation();
                    if (notches < 0) {
                        scale *= 1.1;
                    } else {
                        scale /= 1.1;
                    }

                    repaint();
                }
            });
        }

        public GraphPanel(HashMap<Integer, Node> nodes) {
            this.nodes = nodes;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Apply zoom and offset transformations
            g2d.translate(offsetX, offsetY);
            g2d.scale(scale, scale);

            g2d.setStroke(new BasicStroke(2.0f));


            for (Node node : nodes.values()) {
                int x1 = node.getPosition()[0] + 5;
                int y1 = node.getPosition()[1] + 5;
                System.out.println("node: " + node.getId() + " edges: " + node.getEdges());

                g2d.setColor(Color.GREEN);
                g2d.fillOval(x1 - 5, y1 - 5, 10, 10);

                g2d.setColor(Color.BLACK);
                g2d.drawString(Integer.toString(node.getId()), x1 + 10, y1);

                for (Integer neighborId : node.getEdges().keySet()) {
                    Node neighborNode = nodes.get(neighborId);

                    if (neighborNode != null) {
                        int x2 = neighborNode.getPosition()[0] + 5;
                        int y2 = neighborNode.getPosition()[1] + 5;
                        double angle = Math.atan2(y2 - y1, x2 - x1);

                        int nodeRadius = 5; // Adjust the radius of the node circle as needed
                        int padding = 2; // Adjust the padding value as needed

                        // Calculate the adjusted start and end points
                        int startX = x1 + (int) (Math.cos(angle) * nodeRadius);
                        int startY = y1 + (int) (Math.sin(angle) * nodeRadius);
                        int endX = x2 - (int) (Math.cos(angle) * nodeRadius);
                        int endY = y2 - (int) (Math.sin(angle) * nodeRadius);



                        // Draw line with padding between the start and end of node
                        g2d.setColor(Color.BLACK);
                        g2d.drawLine(startX, startY, endX, endY);
                        //Print the weight with a little padding (5 pixels)
                        g2d.drawString(node.getEdges().get(neighborId).getLabel(), (x1 + x2 + padding) / 2, (y1 + y2 + padding) / 2);

                        // Draw arrow
                        int arrowSize = 10;
                        int arrowX1 = (int) (x2 - arrowSize * Math.cos(angle - Math.PI / 6));
                        int arrowY1 = (int) (y2 - arrowSize * Math.sin(angle - Math.PI / 6));
                        int arrowX2 = (int) (x2 - arrowSize * Math.cos(angle + Math.PI / 6));
                        int arrowY2 = (int) (y2 - arrowSize * Math.sin(angle + Math.PI / 6));

                        int[] arrowHeadX = {x2, arrowX1, arrowX2};
                        int[] arrowHeadY = {y2, arrowY1, arrowY2};
                        g2d.fillPolygon(arrowHeadX, arrowHeadY, 3);
                    }
                }
            }

            // Reset transformations
            g2d.scale(1.0 / scale, 1.0 / scale);
            g2d.translate(-offsetX, -offsetY);
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