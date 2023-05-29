package UI;

import Graph.Graph;
import Graph.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class GraphEditor {
    private static void addNodeOnClick(JPanel panel, Graph graph) {
        // Change cursor to crosshair
        panel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {

                    super.mouseClicked(e);
                    int x = (int) ((e.getX() - ((GraphPanel) panel).getOffsetX()) / ((GraphPanel) panel).getScale());
                    int y = (int) ((e.getY() - ((GraphPanel) panel).getOffsetY()) / ((GraphPanel) panel).getScale());

                    Node node = new Node(x, y);
                    graph.addNode(node);

                    // Repaint the panel
                    panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    panel.removeMouseListener(this);
                    panel.repaint();
                }
            }
        });
    }
    public static void createAndShowGUI(Graph graph) {
        JFrame frame = new JFrame("Graph Nodes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);

        JPanel panel = new GraphPanel(graph.getNodes());
        panel.setLayout(null);
        frame.getContentPane().add(panel);

        JButton fab = new JButton("+ add node");
        fab.setHorizontalTextPosition(SwingConstants.CENTER);
        fab.setFocusPainted(false);
        fab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == fab) {
                    addNodeOnClick(panel, graph);
                }
            }
        });

        // Add a ComponentListener to reset FAB position on window resize
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int fabWidth = 140;
                int fabHeight = 50;
                int padding = 10;
                int frameWidth = frame.getWidth();
                int frameHeight = frame.getHeight();
                int fabX = frameWidth - fabWidth - padding;
                int fabY = frameHeight - fabHeight - padding - 25;

                fab.setBounds(fabX, fabY, fabWidth, fabHeight);
            }
        });
        fab.setBounds(10, 10, 100, 20);
        fab.setForeground(new Color(144, 31, 199));
        fab.setFocusPainted(false);
        fab.setFont(new Font("Arial", Font.BOLD, 20 ));
        // Set the initial position of the FAB
        int fabWidth = 140;
        int fabHeight = 50;
        int padding = 10;
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        int fabX = frameWidth - fabWidth - padding;
        int fabY = frameHeight - fabHeight - padding;

        fab.setBounds(fabX, fabY, fabWidth, fabHeight);

        panel.add(fab);
        frame.pack();
        frame.setVisible(true);
    }

    private static class GraphPanel extends JPanel {
        private HashMap<Integer, Node> nodes;

        private int offsetX = 100; // X offset for dragging


        public int getOffsetX() {
            return offsetX;
        }

        public int getOffsetY() {
            return offsetY;
        }

        public double getScale() {
            return scale;
        }

        private int offsetY = 100; // Y offset for dragging
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
                private final double MIN_SCALE = 0.5;
                private final double MAX_SCALE = 2.0;
                public void mouseWheelMoved(MouseWheelEvent e) {
                    int notches = e.getWheelRotation();
                    int mouseX = e.getX();
                    int mouseY = e.getY();

                    double scaleFactor = Math.pow(1.00005, notches);

                    // Apply the quadratic curve to the scale factor
                    scaleFactor = Math.pow(scaleFactor, 2.0);

                    //scale *= scaleFactor;

                    double newScale = scale * scaleFactor;

                    // Clamp the scale within the specified range
                    scale = Math.max(MIN_SCALE, Math.min(MAX_SCALE, scale));

                    // Limit the scale to a reasonable range
                    if (scale < MIN_SCALE || newScale < MIN_SCALE) {
                        scale = MIN_SCALE;
                        newScale=MIN_SCALE;
                    } else if (scale > MAX_SCALE || newScale > MAX_SCALE) {
                        scale = MAX_SCALE;
                        newScale=MAX_SCALE;
                    }
                    // Calculate the offset adjustment based on the mouse position
                    int offsetXAdjustment = (int) (mouseX - mouseX * (newScale / scale));
                    int offsetYAdjustment = (int) (mouseY - mouseY * (newScale / scale));

                    // Update the scale and offset
                    scale = newScale;
                    offsetX += offsetXAdjustment;
                    offsetY += offsetYAdjustment;

                    repaint();
                }
            });
        }

        public GraphPanel(HashMap<Integer, Node> nodes) {
            this.nodes = nodes;
        }

        @Override
        protected void paintComponent(Graphics g) {
            GraphEditor();
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Apply zoom and offset transformations
            g2d.translate(offsetX, offsetY);
            g2d.scale(scale, scale);

            //draw a grid in X+- and Y+- directions
            g2d.setColor(Color.LIGHT_GRAY);
            for (int i = -2000; i < 2000; i += 50) {
                g2d.drawLine(i, -2000, i, 2000);
                g2d.drawLine(-2000, i, 2000, i);
            }

            //draw a red circle on the 0,0 point
            g2d.setColor(Color.RED);
            g2d.fillOval(-5, -5, 10, 10);


            g2d.setStroke(new BasicStroke(2.0f));


            for (Node node : nodes.values()) {
                int x1 = node.getPosition()[0];
                int y1 = node.getPosition()[1];
                //System.out.println("node: " + node.getId() + " edges: " + node.getEdges());
                //get the color for a nice dark green
                g2d.setColor(new Color(0, 100, 0));
                g2d.fillOval(x1 - 5, y1 - 5, 10, 10);

                g2d.setColor(Color.BLACK);
                g2d.drawString(Integer.toString(node.getId()), x1 + 10, y1);

                for (Integer neighborId : node.getEdges().keySet()) {
                    Node neighborNode = nodes.get(neighborId);

                    if (neighborNode != null) {
                        int x2 = neighborNode.getPosition()[0];
                        int y2 = neighborNode.getPosition()[1];
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
                        // Draw arrow
                        int arrowSize = 10;
                        int arrowX1 = (int) (x2 - arrowSize * Math.cos(angle - Math.PI / 6));
                        int arrowY1 = (int) (y2 - arrowSize * Math.sin(angle - Math.PI / 6));
                        int arrowX2 = (int) (x2 - arrowSize * Math.cos(angle + Math.PI / 6));
                        int arrowY2 = (int) (y2 - arrowSize * Math.sin(angle + Math.PI / 6));

                        int[] arrowHeadX = {x2, arrowX1, arrowX2};
                        int[] arrowHeadY = {y2, arrowY1, arrowY2};
                        g2d.fillPolygon(arrowHeadX, arrowHeadY, 3);
                        g2d.drawString(node.getEdges().get(neighborId).getLabel(), (startX + endX + 10) / 2, (startY + endY - 10) / 2);
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
            return new Dimension(1200, 800);
        }
    }
}
