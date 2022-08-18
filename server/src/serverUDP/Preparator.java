package serverUDP;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Preparator {

    public static String addLine(String line) {
        return "\n" + line;
    }

    public static String readFromFile() {
        String message = "";
        try (FileReader reader = new FileReader("output.txt")) {
            // читаем посимвольно
            StringBuilder line = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                line.append((char) c);
            }
            message += addLine(line.toString());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("output.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (writer != null) {
            writer.print("");
            writer.close();
        }
        return message;
    }
}
