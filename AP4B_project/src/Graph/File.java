package Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class File {
    private String filePath;
    private String flag1;
    private String flag2;
    private Integer[] id;
    private Float[] x;
    private Float[] y;
    private Integer[] id_node_from;
    private Integer[] id_node_to;
    private String[] label;
    private Float[] weight;



    public File(String filePath, String flag1, String flag2) {
        this.filePath = filePath;
        this.flag1 = flag1;
        this.flag2 = flag2;
    }

    public void readFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            line = reader.readLine();
            if (line.equals(flag1)) { //Premier flag d'entrée
                int i = 0;
                line = reader.readLine();
                do {
                    if (!line.equals(flag2)) {
                        id[i] = Integer.parseInt(line);
                        line = reader.readLine();

                        if (!line.equals(flag2)) {
                            x[i] = Float.parseFloat(line);
                            line = reader.readLine();
                        }

                        if (!line.equals(flag2)) {
                            y[i] = Float.parseFloat(line);
                            line = reader.readLine();
                        }

                        i++;
                    } else {
                        line = reader.readLine();
                    }
                } while (!line.equals(flag1));

                line = reader.readLine();
                i = 0;
                do {
                    if (!line.equals(flag2)) {
                        id_node_from[i] = Integer.parseInt(line);
                        line = reader.readLine();

                        if (!line.equals(flag2)) {
                            id_node_to[i] = Integer.parseInt(line);
                            line = reader.readLine();
                        }

                        if (!line.equals(flag2)) {
                            label[i] = line;
                            line = reader.readLine();
                        }

                        if (!line.equals(flag2)) {
                            weight[i] = Float.parseFloat(line);
                            line = reader.readLine();
                        }

                        i++;
                    } else {
                        line = reader.readLine();
                    }
                } while (!line.equals(flag1));
            }
            reader.close();

        } catch (IOException e){
            e.printStackTrace();
        }

        Graph read_graph = new Graph(id,x,y);
        read_graph.createEdges(id_node_from,id_node_to,label,weight);


    }


}
