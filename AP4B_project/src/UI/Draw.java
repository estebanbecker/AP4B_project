package UI;

import Graph.Node;
import PathFinder.IntFloatList;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Draw{
    private Graphics2D g2d;

    public Draw(Graphics2D new_g2d){
        this.g2d = new_g2d;
    }

    // draw a grid in X+- and Y+- directions
    public void grid(){
        g2d.setColor(Color.LIGHT_GRAY);
        for (int i = -2000; i < 2000; i += 50) {
            g2d.drawLine(i, -2000, i, 2000);
            g2d.drawLine(-2000, i, 2000, i);
        }
    }

    // draw a red circle on the 0,0 point
    public void origin(){
        g2d.setColor(Color.RED);
        g2d.fillOval(-5, -5, 10, 10);
    }

    

    // go through the hovered node list and draw a circle around the node
    public void node_center(HashMap<Integer,Node> nodes){
        for(HashMap.Entry<Integer, Node> entry : nodes.entrySet()){
            Node node = entry.getValue();
            if (node == null)
                continue;
            node_center(node, 5);
        }
    }

    public void node_center(Node node, int radius){
        int x1 = Math.round(node.getPosition()[0]);
        int y1 = Math.round(node.getPosition()[1]);
        g2d.setColor(new Color(0, 100, 0));
        g2d.fillOval(x1 - radius, y1 - radius, 10, 10);
        g2d.setColor(Color.BLACK);
        g2d.drawString(Integer.toString(node.getId()), x1 + 10, y1);
    }

    // go through the hovered node list and draw a circle around the node
    public void node_circle(ArrayList<Node> nodes){
        g2d.setColor(new Color(34, 139, 34));
        for (Node node : nodes) {
            if (node == null)
                continue;
            node_circle(node, 10);
        }
    }

    public void node_circle(Node node, int radius){
        int x1 = Math.round(node.getPosition()[0]);
        int y1 = Math.round(node.getPosition()[1]);
        g2d.drawOval(x1 - radius, y1 - 10, 20, 20);
    }

    


    public void path(IntFloatList result, HashMap<Integer, Node> nodes){
        int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
        for (int i = 0; i < result.getIntList().length - 1; i++) {
            // draw edges in red
            g2d.setColor(new Color(152, 70, 255, 255));
            x1 = Math.round(nodes.get(result.getIntList()[i]).getPosition()[0]);
            y1 = Math.round(nodes.get(result.getIntList()[i]).getPosition()[1]);
            x2 = Math.round(nodes.get(result.getIntList()[i + 1]).getPosition()[0]);
            y2 = Math.round(nodes.get(result.getIntList()[i + 1]).getPosition()[1]);
            g2d.drawLine(x1, y1, x2, y2);
            
        }
        texte_rectangle(x1, y1, x2, y2, "distance: " + result.getFloatValue().toString() + " units", 2, new Color(152, 70, 255, 194));
    }

    // position 2 is halfway, 1 at base and 3 at a third
    public void texte_rectangle(int x1, int y1, int x2, int y2, String text, int position, Color color){
        // draw string with distance
        g2d.setColor(new Color(0, 0, 0, 255));
        g2d.setFont(new Font("Helvetica", Font.BOLD, 14));
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(text);

        // Calculate the dimensions and position of the rectangle
        int rectWidth = textWidth + 20; // Add some padding
        int rectHeight = 35;
        int rectX = x1 + (x2 - x1 - rectWidth) / position;
        int rectY = y1 + (y2 - y1 - rectHeight) / position;

        // Draw the rounded rectangle
        g2d.setColor(color);
        g2d.fillRoundRect(rectX, rectY, rectWidth, rectHeight, 20, 20);
        // add a white border
        g2d.setColor(new Color(81, 45, 110, 255));
        g2d.drawRoundRect(rectX, rectY, rectWidth, rectHeight, 20, 20);
        // Draw the text centered within the rectangle
        g2d.setColor(Color.BLACK);
        int textX = rectX + (rectWidth - textWidth) / 2;
        int textY = rectY + (rectHeight - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();
        g2d.drawString(text, textX, textY);
    }

    public void all_arrows(HashMap<Integer, Node> nodes){
        for (Node node : nodes.values()) {
            int x1 = Math.round(node.getPosition()[0]);
            int y1 = Math.round(node.getPosition()[1]);

            for (Integer neighborId : node.getEdges().keySet()) {
                Node neighborNode = nodes.get(neighborId);
                if (neighborNode != null) {
                    int x2 = Math.round(neighborNode.getPosition()[0]);
                    int y2 = Math.round(neighborNode.getPosition()[1]);
                    arrow(x1, x2, y1, y2);
                }
            }
        }
        for (Node node : nodes.values()) {
            int x1 = Math.round(node.getPosition()[0]);
            int y1 = Math.round(node.getPosition()[1]);
            for (Integer neighborId : node.getEdges().keySet()) {
                Node neighborNode = nodes.get(neighborId);
                if (neighborNode != null) {
                    int x2 = Math.round(neighborNode.getPosition()[0]);
                    int y2 = Math.round(neighborNode.getPosition()[1]);
                    texte_rectangle(x1, y1, x2, y2, node.getEdges().get(neighborId).getLabel(), 3, new Color(152, 70, 255, 194));
                }
            }
        }
    }

    public void arrow(int x1, int x2, int y1, int y2){
        
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
        // Print the weight with a little padding (5 pixels)
        // Draw arrow
        int arrowSize = 10;
        int arrowX1 = (int) (x2 - arrowSize * Math.cos(angle - Math.PI / 6));
        int arrowY1 = (int) (y2 - arrowSize * Math.sin(angle - Math.PI / 6));
        int arrowX2 = (int) (x2 - arrowSize * Math.cos(angle + Math.PI / 6));
        int arrowY2 = (int) (y2 - arrowSize * Math.sin(angle + Math.PI / 6));

        int[] arrowHeadX = { endX, arrowX1, arrowX2 };
        int[] arrowHeadY = { endY, arrowY1, arrowY2 };
        g2d.fillPolygon(arrowHeadX, arrowHeadY, 3);
    }

    public void nodes(HashMap<Integer, Node> nodes, int radius){
        for (Node node : nodes.values()) {
            int x1 = Math.round(node.getPosition()[0]);
            int y1 = Math.round(node.getPosition()[1]);
            // get the color for a nice dark green
            g2d.setColor(new Color(0, 100, 0));
            g2d.fillOval(x1 - 5, y1 - 5, 10, 10);
            g2d.setColor(Color.BLACK);
            g2d.drawString(Integer.toString(node.getId()), x1 + 10, y1);
        }
    }
}