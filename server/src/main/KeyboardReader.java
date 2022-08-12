package main;
import java.util.Scanner;


/**
 * The KeyboardReader class provides ability to get Input from user
 * @author Grebenkin Vadim
 */
public class KeyboardReader {
    /**
     *
     * @return String inputted line from console
     */
    public static String input() {
        System.out.print("Reader: input requested: ");
        String input = ReadLine().trim();
        return input.isEmpty() ? null : input;
    }

    public static String input(String message) {
        System.out.println(message);
        return input();
    }

    /**
     * Gets inputted line from console
     *
     * @return client input
     */
    private static String ReadLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
