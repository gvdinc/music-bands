package main;

import java.util.Scanner;


/**
 * The KeyboardReader class provides ability to get Input from {@link #scanner}
 *
 * @author Grebenkin Vadim
 */
public class KeyboardReader {
    private final Scanner scanner;


    /**
     * Constructor (admin initialisation)
     */
    public KeyboardReader() {
        this.scanner = new Scanner(System.in);
        System.out.println("System: admin initialized");
    }

    /**
     * {@link #getLine()} with special client request-message
     *
     * @return String inputted line from console
     */
    public String getInput() {
        System.out.print("System: admin input requested: ");
        String input = this.getLine();
        return input;
    }

    /**
     * Gets inputted line from console
     *
     * @return client input
     */
    private String getLine() {
        String input = this.scanner.nextLine();
        return input;
    }
}
