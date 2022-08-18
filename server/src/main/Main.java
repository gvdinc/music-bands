package main;


import serverUDP.UDPServer;

import java.net.SocketException;


/**
 * Main class - Entrance point
 *
 * @version 1.8.0.301
 * @autor gvd2808
 */
public class Main {

    public static void administrate(CommandExecutor commandExecutor) {
        while (!commandExecutor.getExitStatus()) {
            String input = KeyboardReader.input("\n(Enter command)");
            commandExecutor.runCommand(input);
        }
    }

    public static void main(String[] args) {

        //
        CollectionHolder cHolder = new CollectionHolder("D:\\LabLib\\ProgLab6\\result.xml");

        CommandExecutor commandExecutor = new CommandExecutor(cHolder);
        while (!cHolder.checkInputStream()) {
            System.out.println("Main: the xml file pass is incorrect, please (repeat link input)!");
            try {
                String newPass = (KeyboardReader.input());
                cHolder = new CollectionHolder(newPass);
                commandExecutor = new CommandExecutor(cHolder);
                if (cHolder.checkInputStream()) {
                    System.out.println("File link correct\n");
                    break;
                }
            } catch (Exception ignored) {
            }
        } // trying to reach the data-file


    MAIN:  while (true) {
            String input = /*KeyboardReader.input("\n\n\nturn off - 0\nadmin mode-1\nserver mode - 2")*/ "2";
            switch (input.trim()) {
                case "0":
                    break MAIN;
                case "1":
                    administrate(commandExecutor);
                    continue;
                case "2": {
                    Integer port = null;
                    try {
                        String portInput = /*KeyboardReader.input("Set the server port: ")*/ "50000";
                        if (portInput == null) continue;
                        port = new Integer(portInput);
                    } catch (RuntimeException e) {
                        System.out.println("wrong port");
                    }
                    UDPServer server = null;
                    try {
                        server = new UDPServer(port, cHolder);
                    } catch (SocketException e) {
                        System.out.println("port "+ port + " is occupied");
                        continue;
                    }
                    server.launchServer();
                    server.run();
                    server.turnServerOff();
                    System.out.println("it's ok");
                    //commandExecutor.runCommand("save");
                }
            }

        }


    }


}

