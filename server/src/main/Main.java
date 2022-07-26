package main;


import serverUDP.UDPServer;


/**
 * Main class - Entrance point
 *
 * @version 1.8.0.301
 * @autor gvd2808
 */
public class Main {

    public static void main(String[] args) throws Exception {
        CollectionHolder cHolder = new CollectionHolder("D:\\LabLib\\ProgLab6\\result.xml");

        CommandExecutor commandExecutor = new CommandExecutor(cHolder);
        while (!cHolder.checkInputStream()) {
            System.out.println("Main: the xml file pass is incorrect, please (repeat link input)!");
            try {
                String newPass = (commandExecutor.getLine());
                cHolder = new CollectionHolder(newPass);
                commandExecutor = new CommandExecutor(cHolder);
                if (cHolder.checkInputStream()) {
                    System.out.println("File link correct\n");
                    break;
                }
            } catch (Exception ignored) {
            }
        } // trying to reach the data-file

//        while (!commandExecutor.getExitStatus()) {
//            System.out.println("\n(Enter command)");
//            commandExecutor.runCommand();
//        } // main cycle of getting and executing app.commands


        Integer port = new Integer(commandExecutor.getLine("Set the server port: "));
        UDPServer server = new UDPServer(port, cHolder, commandExecutor);
        while (true) {
            server.run();
            commandExecutor.runCommand("save");
        }

    }


}

