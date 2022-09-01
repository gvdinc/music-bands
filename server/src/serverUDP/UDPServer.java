package serverUDP;

import commands.Command;
import common.CTransitPack;
import common.Commands;
import common.ReplyPack;
import common.User;
import database.Operator;
import main.CollectionHolder;
import main.CommandExecutor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

import static main.CommandExecutor.unpackTransitPack;

public class UDPServer implements Runnable{
    private final DatagramSocket serverSocket;
    private final CollectionHolder cHolder;
    private ServerState serverState;
    public final Operator operator;
    private CommandExecutor commandExecutor;
    private final ExecutorService getService = Executors.newCachedThreadPool();
    private final ExecutorService runService = Executors.newFixedThreadPool(3);


    public UDPServer(int port, CollectionHolder cHolder, Operator operator) throws SocketException {
        this.operator = operator;
        this.serverSocket = Connector.getSocket(port);
        serverState = ServerState.OPERATING;
        this.commandExecutor = new CommandExecutor(cHolder);
        this.cHolder = cHolder;
    }

    @Override
    public void run(){
RUN:    while (serverState != ServerState.OFFLINE) {
            DatagramPacket packet;
            try {
                packet = Connector.receivePacket(serverSocket);
                if (packet == null) continue;
            } catch (IOException e) { // SO timeout exception
                e.printStackTrace();
                turnServerOff();
                break RUN;
            }
            Responder responder = new Responder(packet);
            getService.submit(responder);
            //new Thread(new Responder(packet)).start();
        }
    }

    /** Administrating functions */
    public void turnServerOff(){
        if (this.serverState == ServerState.OFFLINE) return;
        serverSocket.close();
        this.serverState = ServerState.OFFLINE;
        System.out.println("Server down");
    }
    public ServerState getServerState() {
        return serverState;
    }
    private static String encode(byte[] buffer) {
        return new String(buffer, StandardCharsets.UTF_8).trim();
    }


    // Threading classes
    private class Responder implements Runnable{
        private final DatagramPacket datagram;
        private ClientData client;
        private CTransitPack transitPack;

        public Responder(DatagramPacket pack) {
            this.datagram = pack;
        }

        @Override
        public void run() {
            this.client = new ClientData(datagram.getAddress(), datagram.getPort());
            this.transitPack = Connector.getTransit(datagram);
            if (transitPack == null || transitPack.getType() == Commands.EXIT) return;
            boolean authorised = operator.authorize(this.transitPack.getUser());

            if (authorised) {
                if (transitPack.getType() == Commands.AUTHORIZE) { // 1 - t
                    Connector.sendMessage(serverSocket, new ReplyPack(Commands.AUTHORIZE, true), this.client);
                }
                else {
                    Callable<ReplyPack> runCmd = () -> {
                        Command cmd = unpackTransitPack(transitPack);
                        if (cmd == null) return new ReplyPack(transitPack.getType(), false);
                        return cmd.execute(cHolder);
                    };
                    Future<ReplyPack> future = runService.submit(runCmd);
                    ReplyPack replyPack = new ReplyPack(transitPack.getType(), false);
                    try {
                        replyPack = future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    Connector.sendMessage(serverSocket, replyPack , client);
                }
            }
            else {
                if (transitPack.getType() == Commands.AUTHORIZE) { // 1 - f
                    Connector.sendMessage(serverSocket, new ReplyPack(Commands.AUTHORIZE, false), this.client);
                }
                else if (transitPack.getType() == Commands.REGISTER){ // 2 - t/f
                    Boolean isRegistered = operator.register(this.transitPack.getUser());
                    Connector.sendMessage(serverSocket, new ReplyPack(Commands.REGISTER, isRegistered), this.client);
                }
                else {Connector.sendMessage(serverSocket, new ReplyPack(Commands.AUTHORIZE, false), client);}
            } // unauthorized reacting

        }
    }
}