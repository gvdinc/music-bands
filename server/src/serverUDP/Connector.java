package serverUDP;

import commands.Command;
import common.CTransitPack;
import common.Commands;
import common.Serializer;
import main.CommandExecutor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Date;

// 57
public class Connector {
    private DatagramSocket datagramSocket;
    private ClientData client;
    private int serverPort;
    private boolean isConnected;
    private Date lastConnectionTime;
    private Date lastReceivedTime;

    private static final int awaitingTime = 200000;

    public Connector(int serverPort) throws SocketException {
        this.serverPort = serverPort;
        this.isConnected = false;
        this.datagramSocket = new DatagramSocket(this.serverPort);
        System.out.println("DatagramSocket initialised on port " + this.serverPort);
    }

    public void disconnect(){
        this.client = null;
        this.isConnected = false;
        this.datagramSocket.disconnect();
    }

    public boolean meetClient() throws IOException {
        DatagramPacket packet = receivePacket();
        if (packet == null){System.out.println("no connection request"); return false;}
        Command cmdConnect = getCommand(packet);
        System.out.println("received cmd: " + cmdConnect.getType() + cmdConnect);
        if (cmdConnect.getType() != Commands.CONNECT){System.out.println("incorrect connection request"); return false;}
        resetClientInfo(packet.getAddress(), packet.getPort());
        this.datagramSocket.connect(client.getSenderAddress(), client.getSenderPort());
        return true;
    }

    // various command getters (with receiving and without)
    public Command getCommand() throws IOException {
        Command cmd;
        DatagramPacket inputPacket = this.receivePacket(); // this one throws exception
        // check if Packet is empty
        if (inputPacket == null) return null;
        cmd = getCommand(inputPacket);
        if (cmd == null || cmd.getType() == null) return null;
        else {
            System.out.println("Sent from the client: " + cmd.getType().getCommandName() + " " + cmd.getParam());
            return cmd;
        }
    }

    public Command getCommand(DatagramPacket inputPacket){
        Command cmd;
        // check if Packet is empty
        if (inputPacket == null) return null;
        CTransitPack transitPack = (CTransitPack) Serializer.deserialize(inputPacket.getData());
        if (transitPack == null || transitPack.getType() == null) return null;
        else {
            cmd = CommandExecutor.unpackTransitPack(transitPack);
            if (cmd != null) System.out.println("Sent from the client: " + cmd.getType().getCommandName() + " " + cmd.getParam());
            return cmd;
        }
    }

    public void sendMessage(String message) {
        byte[] sendingDataBuffer = message.getBytes();
        // Создайте новый UDP-пакет с данными, чтобы отправить их клиенту
        DatagramPacket outputPacket = new DatagramPacket(
                sendingDataBuffer, sendingDataBuffer.length,
                this.client.getSenderAddress(), this.client.getSenderPort()
        );

        // Отправьте пакет клиенту
        try {
            datagramSocket.send(outputPacket);
            System.out.println("message " + new String(outputPacket.getData()) + " is sent to client");
            //Thread.sleep(300);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //private operations
    /** @exception throws exception if the limit of waiting time reached */
    private DatagramPacket receivePacket() throws IOException {
        byte[] receivingDataBuffer = new byte[1024];
        DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
        if (client != null) {
            System.out.println("Connector: waiting for client's packet");
            datagramSocket.setSoTimeout(awaitingTime);
        }
        else{
            System.out.println("Connector: waiting for request");
            datagramSocket.setSoTimeout(awaitingTime * 10);
        }

        this.datagramSocket.receive(inputPacket);
        System.out.println(Arrays.toString(inputPacket.getData()));
        if (inputPacket.getData().length == 0) return null;
        this.lastReceivedTime = new Date();
        return inputPacket;
    }


    private void resetClientInfo(InetAddress address, int port){
        this.client = new ClientData(address, port);
        this.isConnected = true;
        lastConnectionTime = new Date();
    }

    public void closeSocket() {
        disconnect();
        this.datagramSocket.close();
        this.datagramSocket = null;
        System.out.println("DatagramSocket closed!");
    }

    // getters, setters and stuff

    public DatagramSocket getDSocket() {
        return this.datagramSocket;
    }
    public ClientData getClient() {
        return client != null ? client : null;
    }
    public boolean isConnected() {
        return datagramSocket.isConnected();
    }
    public Date getLastConnectionTime() {
        return lastConnectionTime;
    }
    public Date getLastReceivedTime() {
        return lastReceivedTime;
    }
}
