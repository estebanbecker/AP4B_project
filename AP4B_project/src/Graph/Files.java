package Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Files {
    private String filePath;
    private String flag1 = "***";
    private String flag2 = "*";
    private ArrayList<Integer> id = new ArrayList<>();
    private ArrayList<Float> x = new ArrayList<>();
    private ArrayList<Float> y = new ArrayList<>();
    private ArrayList<Integer> id_node_from = new ArrayList<>();
    private ArrayList<Integer> id_node_to = new ArrayList<>();
    private ArrayList<String> label = new ArrayList<>();




    public Files(String filePath) {
        this.filePath = filePath;
    }

    public Graph readFile() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            line = reader.readLine();
            if (line.equals(flag1)) { //Premier flag d'entrée

                line = reader.readLine();
                do {
                    if (!line.equals(flag2)) {
                        id.add(Integer.parseInt(line));
                        line = reader.readLine();
                    }
                    if (!line.equals(flag2)) {
                        x.add(Float.parseFloat(line));
                        line = reader.readLine();
                    }

                    if (!line.equals(flag2)) {
                        y.add(Float.parseFloat(line));
                        line = reader.readLine();

                    } else {
                        line = reader.readLine();
                    }

                } while (!line.equals(flag1));

                line = reader.readLine();
                do {
                    if (!line.equals(flag2)) {
                        id_node_from.add(Integer.parseInt(line));
                        line = reader.readLine();

                        if (!line.equals(flag2)) {
                            id_node_to.add(Integer.parseInt(line));
                            line = reader.readLine();
                        }

                        if (!line.equals(flag2)) {
                            label.add(line);
                            line = reader.readLine();
                        }
                    } else {
                        line = reader.readLine();
                    }
                } while (!line.equals(flag1));
            }
            reader.close();

        } catch (IOException e){
            e.printStackTrace();
        }

        Integer[] id_array = id.toArray(new Integer[id.size()]);
        Float[] x_array = x.toArray(new Float[x.size()]);
        Float[] y_array = y.toArray(new Float[y.size()]);

        Integer[] id_node_from_array = id_node_from.toArray(new Integer[id_node_from.size()]);
        Integer[] id_node_to_array = id_node_to.toArray(new Integer[id_node_to.size()]);
        String[] label_array = label.toArray(new String[label.size()]);

        Graph read_graph = new Graph(id_array,x_array,y_array);
        read_graph.createEdges(id_node_from_array,id_node_to_array,label_array);

        return read_graph;
    }

    public void writeFile(Graph graph) {
        NodeData node = graph.getNodesData();
        EdgeData edge = graph.getEdgesData();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

            writer.write(flag1);
            writer.newLine();

            for (int i = 0; i < node.getlength(); i++) {
                writer.write(String.valueOf(node.ids[i]));
                writer.newLine();
                writer.write(String.valueOf(node.xValues[i]));
                writer.newLine();
                writer.write(String.valueOf(node.yValues[i]));
                writer.newLine();
                writer.write(flag2);
                writer.newLine();
            }

            writer.write(flag1);
            writer.newLine();

            for (int i = 0; i < edge.node_from_ids.length; i++) {
                writer.write(String.valueOf(edge.node_from_ids[i]));
                writer.newLine();
                writer.write(String.valueOf(edge.node_to_ids[i]));
                writer.newLine();
                writer.write(edge.labels[i]);
                writer.newLine();
                writer.write(flag2);
                writer.newLine();
            }

            writer.write(flag1);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
