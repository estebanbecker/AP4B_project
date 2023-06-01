package UI;

import Graph.Graph;
import Graph.Node;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphEditor {


    private static final int CONTEXT_MENU_COOLDOWN = 1000; // Cooldown period in milliseconds
    private static long lastContextMenuTime = 0; // Last time the context menu was invoked


    private static void addNodeOnClick(JPanel panel, Graph graph, boolean selected) {
        // Change cursor to crosshair
        panel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {

                    super.mouseClicked(e);
                    int x = (int) ((e.getX() - ((GraphPanel) panel).getOffsetX()) / ((GraphPanel) panel).getScale());
                    int y = (int) ((e.getY() - ((GraphPanel) panel).getOffsetY()) / ((GraphPanel) panel).getScale());

                    if(selected) {
                        x = (x + 25) / 50 * 50;
                        y = (y + 25) / 50 * 50;
                    }
                    graph.createANode((float) x, (float) y);

                    // Repaint the panel
                    panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    panel.removeMouseListener(this);
                    panel.repaint();
                }
            }
        });
    }

    public static void createAndShowGUI(Graph graph) {
        //setup flatlaf
        try {
            FlatMacLightLaf.setup();
        } catch (Exception e) {
            System.err.println("Failed to initialize LaF");
        }
        JFrame frame = new JFrame("Graph Nodes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);

        JPanel panel = new GraphPanel(graph.getNodes());
        ((GraphPanel) panel).setGraph(graph);
        panel.setLayout(null);
        frame.getContentPane().add(panel);


        JCheckBox snap = new JCheckBox("Snap to grid");
        snap.setBounds(5, 10, 500, 20);
        snap.setSelected(true);
        snap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == snap) {
                    ((GraphPanel) panel).setSnap(snap.isSelected());
                    panel.repaint();
                }
            }
        });
        panel.add(snap);

        JButton fab = new JButton("+ add node");
        fab.setHorizontalTextPosition(SwingConstants.CENTER);
        fab.setFocusPainted(false);
        fab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == fab) {
                    addNodeOnClick(panel, graph, snap.isSelected());
                }
            }
        });

        //add a checkbox for snapping

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

        private Graph graph;

        private void createContextMenu(int mouseX, int mouseY, int X, int Y) {
            // Check if the context menu was invoked recently
            if (System.currentTimeMillis() - lastContextMenuTime < CONTEXT_MENU_COOLDOWN) {
                return;
            }
            lastContextMenuTime = System.currentTimeMillis();
            Node clickedNode = findClickedNode(mouseX, mouseY);
            JPopupMenu contextMenu = new JPopupMenu();

            JMenuItem deleteItem = new JMenuItem("Delete Node");
            contextMenu.add(deleteItem);

            if (clickedNode != null) {
                contextMenu.show(this, X, Y);
                deleteItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        graph.deleteNode(clickedNode.getId(), true);
                        repaint();
                    }
                });
            }

        }


        public void setGraph(Graph graph) {
            this.graph = graph;
        }

        private HashMap<Integer, Node> nodes;

        private Node findClickedNode(int mouseX, int mouseY) {
            for (Node node : nodes.values()) {
                float x = node.getPosition()[0];
                float y = node.getPosition()[1];

                // Check if the mouse click is inside the node bounds
                if (Math.abs(mouseX - x) <= 10 && Math.abs(mouseY - y) <= 10) {
                    return node;
                }
            }
            return null;
        }

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

        private int mouseX;
        private int mouseY;

        public float getMouseX() {
            return mouseX;
        }

        public float getMouseY() {
            return mouseY;
        }

        private Node selectedNode;

        //put the hovered nodes in a list
        private ArrayList<Node> hoveredNodes = new ArrayList<Node>();

        private ArrayList<Node> clickednodes = new ArrayList<Node>();
        //Create a Graph Editor method
        public void GraphEditor(){

            //create a add edge function that uses the graph.connectUnidirectionalNodes() method as argument
            //create a hoveredNodes node list, and store it publically
            // Add mouse listener for hovering over nodes make them bigger and show their name

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

                //add a mouse released

                public void mouseMoved(MouseEvent e) {

                    mouseX = (int) ((e.getX() - offsetX) / scale);
                    mouseY = (int) ((e.getY() - offsetY) / scale);

                    Node hoveredNode = findClickedNode(mouseX, mouseY);
                    //if hovered node, make it bigger and show name
                    //if not, remove the bigger node and name
                    if (hoveredNode != null) {
                        hoveredNodes.add(hoveredNode);
                        //System.out.println("hovered" + hoveredNode);
                        // Set the cursor to the hand cursor
                        if (getCursor().getType() != Cursor.CROSSHAIR_CURSOR) {
                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                        }
                    } else {
                        //if cursor is cross hair, set it to default
                        if (getCursor().getType() != Cursor.CROSSHAIR_CURSOR) {
                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }
                        // Set the cursor to the move cursor
                    }
                    repaint();
                }
            });
            addMouseListener(new MouseAdapter() {



                public void mousePressed(MouseEvent e) {
                    startX = e.getX();
                    startY = e.getY();
                    mouseX = (int) ((startX - offsetX) / scale);
                    mouseY = (int) ((startY - offsetY) / scale);

                    if(SwingUtilities.isRightMouseButton(e)){
                        createContextMenu(mouseX, mouseY, startX, startY);
                    }

                    if (SwingUtilities.isLeftMouseButton(e)) {
                        Node clickedNode = findClickedNode(mouseX, mouseY);

                        if (clickedNode != null) {
                            if (selectedNode == null) {
                                // First node is selected
                                selectedNode = clickedNode;
                                clickednodes.add(selectedNode);
                            } else {
                                // Second node is selected
                                if (selectedNode != clickedNode) {
                                    // Create an edge between the two nodes
                                    clickednodes.add(clickedNode);
                                    String edgeName = JOptionPane.showInputDialog(this, "Enter edge name:");
                                    if (edgeName != null && !edgeName.isEmpty()) {
                                        graph.connectUnidirectionalNodes(selectedNode.getId(), clickedNode.getId(), edgeName);
                                        repaint();
                                    }
                                    //clear the clicked nodes
                                    clickednodes.clear();
                                    // Reset the selected nodes
                                    selectedNode = null;
                                    // Repaint the graph
                                    repaint();
                                } else {
                                    // If the same node is selected, reset the selection
                                    clickednodes.remove(selectedNode);
                                    selectedNode = null;
                                }
                            }
                        } else {
                            // No node is clicked, reset the selection
                            clickednodes.remove(selectedNode);
                            selectedNode = null;
                        }
                    }
                }
            });



            // Add mouse wheel listener for zooming
            addMouseWheelListener(new MouseWheelListener() {
                private final double MIN_SCALE = 0.6;
                private final double MAX_SCALE = 3.0;
                public void mouseWheelMoved(MouseWheelEvent e) {
                    int notches = e.getWheelRotation();
                    int mouseX = e.getX();
                    int mouseY = e.getY();

                    double scaleFactor = Math.pow(1.000023, notches);

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
            int x1 = Math.round(node.getPosition()[0]);
            int y1 = Math.round(node.getPosition()[1]);
                //System.out.println("node: " + node.getId() + " edges: " + node.getEdges());
                //get the color for a nice dark green
                g2d.setColor(new Color(0, 100, 0));
                g2d.fillOval(x1 - 5, y1 - 5, 10, 10);

                g2d.setColor(Color.BLACK);
                g2d.drawString(Integer.toString(node.getId()), x1 + 10, y1);

                for (Integer neighborId : node.getEdges().keySet()) {
                    Node neighborNode = nodes.get(neighborId);

                    if (neighborNode != null) {
                        int x2 = Math.round(neighborNode.getPosition()[0]);
                        int y2 = Math.round(neighborNode.getPosition()[1]);
                        double angle = Math.atan2(y2 - y1, x2 - x1);

                        int nodeRadius = 5; // Adjust the radius of the node circle as needed

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
            //go through the hovered node list and draw a circle around the node
            for (Node hoveredNode : hoveredNodes) {
                Node node = nodes.get(hoveredNode.getId());
                int x1 = Math.round(node.getPosition()[0]);
                int y1 = Math.round(node.getPosition()[1]);
                //dark forest green
                g2d.setColor(new Color(34, 139, 34));
                g2d.drawOval(x1 - 10, y1 - 10, 20, 20);
            }
            for (Node clicknode : clickednodes) {
                Node node = nodes.get(clicknode.getId());
                int x1 = Math.round(node.getPosition()[0]);
                int y1 = Math.round(node.getPosition()[1]);
                //dark forest green
                g2d.setColor(new Color(34, 139, 34));
                g2d.drawOval(x1 - 10, y1 - 10, 20, 20);
            }
            hoveredNodes.clear();
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

        public void setSnap(boolean selected) {
        }
    }
}
