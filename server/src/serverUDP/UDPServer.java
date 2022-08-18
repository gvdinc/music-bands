package serverUDP;

import commands.Command;
import common.Commands;
import main.CollectionHolder;
import main.CommandExecutor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class UDPServer implements Runnable {
    private final CollectionHolder cHolder;
    private final Connector connector;
    private ServerState serverState = ServerState.OFFLINE;
    private Command receivedCmd;

    public UDPServer(int port, CollectionHolder cHolder) throws SocketException {
        this.connector = new Connector(port);
        this.cHolder = cHolder;
        launchServer();
    }

    public void turnServerOff(){
        if (this.serverState == ServerState.OFFLINE) return;
        disconnectClient();
        this.connector.closeSocket();
        this.serverState = ServerState.OFFLINE;
        System.out.println("Server down");
    }

    public void launchServer(){
        this.connector.disconnect();
        this.serverState = ServerState.UNCONNECTED;
    }

    @Override
    public void run() {
        CommandExecutor executor = new CommandExecutor(this.cHolder);
        while (this.serverState != ServerState.OFFLINE) {
            switch (this.serverState) {

                case OPERATING: {
                    if (!this.connector.isConnected()) { disconnectClient(); continue;}
                    try {
                        this.receivedCmd = connector.getCommand();
                    } catch (IOException e) {
                        e.printStackTrace();
                        disconnectClient();
                        continue;
                    }
                    if ((this.receivedCmd == null) || receivedCmd.getType() == Commands.EXIT) {
                        disconnectClient(); continue;
                    }
                    switch (receivedCmd.getType()) {
                        case PING:
                        case CONNECT: {
                            connector.sendMessage(getServerState().toString() + " " + this.connector.getClient().getSenderAddress());
                            continue;
                        }
                        case SAVE: {
                            connector.sendMessage("cheater");
                            continue;
                        }
                        default:
                        {
                            try {
                                executor.runCommand(receivedCmd);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                connector.sendMessage("/FAILED");
                            }
                            connector.sendMessage(Preparator.readFromFile());
                        }
                    }
                    continue;
                }

                case UNCONNECTED: {
                    try {
                        this.connector.meetClient();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Server: shutting down");
                        turnServerOff();
                        break;
                    }
                    if (this.connector.isConnected()) {
                        this.serverState = ServerState.OPERATING;
                        connector.sendMessage(getServerState().toString() + " " + this.connector.getClient().getSenderAddress());
                    }
                    continue;
                }
            }
        }
    }

    public ServerState getServerState() {
        return serverState;
    }

    private void disconnectClient(){
        if (serverState == ServerState.OFFLINE) return;
        System.out.println("Disconnecting client " + (this.connector.getClient() != null ? this.connector.getClient().getSenderAddress() : "NO CLIENT"));
        this.connector.disconnect();
        this.serverState = ServerState.UNCONNECTED;
    }

    private static String encode(byte[] buffer) {
        return new String(buffer, StandardCharsets.UTF_8).trim();
    }
}

//    Command cmd = getCommand();
//            while (cmd == null) {
//                this.connector.sendMessage("wrong cmd - repeat input!!!");
//                cmd = getCommand(1024);
//            }
//            if (cmd.getType() == Commands.EXIT) break;
//            if (cmd.getType() == Commands.PING) connector.reconnect();
//            if (!cmd.getType().isElementTaking()) {
//                try {
//                    executor.runCommand(cmd, this.preparator);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                executor.runCascadeCommand(cmd, this.preparator, this.connector);
//            }
//            this.preparator.readFromFile();
//            connector.sendMessage(this.preparator.flush());
