package serverUDP;


import common.Command;
import common.Commands;
import common.Serializer;
import main.CollectionHolder;
import main.CommandExecutor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class UDPServer implements Runnable {
    private final int port;
    private final CollectionHolder cHolder;
    private final CommandExecutor commandExecutor;
    private final Connector connector;
    private DatagramSocket dSocket;
    private boolean exitStatus = false;
    private final Preparator preparator;

    public UDPServer(int port, CollectionHolder cHolder, CommandExecutor commandExecutor) throws SocketException {
        this.port = port;
        this.connector = new Connector(port);
        this.cHolder = cHolder;
        this.commandExecutor = commandExecutor;
        this.preparator = new Preparator();
    }

    private static String encode(byte[] buffer) {
        return new String(buffer, StandardCharsets.UTF_8).trim();
    }

    @Override
    public void run() {
        this.dSocket = this.connector.getDSocket();

        while (!exitStatus) {

            Command cmd = null;
            cmd = getCommand(1024);
            while (cmd == null) {
                this.connector.sendMessage("wrong cmd - repeat input!!!");
                cmd = getCommand(1024);
            }
            if (cmd.getType() == Commands.EXIT) break;
            if (cmd.getType() == Commands.PING) connector.reconnect();
            if (!cmd.getType().isElementTaking()) {
                try {
                    this.commandExecutor.runCommand(cmd, this.preparator);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                this.commandExecutor.runCascadeCommand(cmd, this.preparator, this.connector);
            }
            this.preparator.readFromFile();
            connector.sendMessage(this.preparator.flush());

        }
    }

    private Command getCommand(int bufferLength) {
        byte[] receivingDataBuffer = new byte[bufferLength];
        DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
        System.out.println("waiting for client's command");
        try {
            this.dSocket.receive(inputPacket);
            while (inputPacket.getPort() != this.connector.getClient().getSenderPort()) {
                System.out.println("get out of here funked Zayats...");
                this.dSocket.receive(inputPacket);
            }
        } catch (RuntimeException | IOException runtimeException) {
            System.out.println("Client disconnected");
            this.exitStatus = true;
            return null;
        }
        Command cmd;
        cmd = (Command) Serializer.deserialize(inputPacket.getData());
        if (cmd.getType() != null) {
            System.out.println("Sent from the client: " + cmd.getType().getCommandName() + " " + cmd.getArgs());
            return cmd;
        } else {
            cmd = new Command(Commands.PING, "wrong command");
            return cmd;
        }
    }
}

