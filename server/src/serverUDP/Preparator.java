package serverUDP;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Preparator {
    private String message;

    public Preparator() {
        this.message = "";
    }

    public void addLine(String line) {
        this.message += "\n" + line;
    }

    public String flush() {
        String mess = this.message;
        this.message = "";
        return mess;
    }

    public void readFromFile() {
        try (FileReader reader = new FileReader("output.txt")) {
            // читаем посимвольно
            StringBuilder line = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                line.append((char) c);
            }
            addLine(line.toString());
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("output.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.print("");
// other operations
        writer.close();
    }
}
