package Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class File {
    private String filePath;
    private String flag1;
    private String flag2;
    private int id;
    private int x;
    private int y;
    private String label;



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

                line = reader.readLine();
                if (!line.equals(flag2)) id = Integer.parseInt(line);

                line = reader.readLine();
                if (!line.equals(flag2)) x = Integer.parseInt(line);

                line = reader.readLine();
                if (!line.equals(flag2)) y = Integer.parseInt(line);


            }



            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getLabel() {
        return label;
    }
}
