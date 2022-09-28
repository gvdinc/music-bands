package serverUDP;

import common.CTransitPack;
import common.ReplyPack;
import common.Serializer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Module of getting connections and returns commands */
public class Connector{
    public static final int bufferSize = 2048;
    private static final int awaitingTime = 3600000;
    private static final int MIN_PORT_NUMBER = 30000;
    private static final int MAX_PORT_NUMBER = 31999;
    private static final SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");


    public static DatagramSocket getSocket(int serverPort) throws SocketException {
        DatagramSocket datagramSocket = new DatagramSocket(serverPort);
        System.out.println("DatagramSocket initialised on port " + serverPort);
        return datagramSocket;
    }

    // various command getters (with receiving and without)
    @Deprecated
    public static CTransitPack getTransit(DatagramSocket datagramSocket) throws IOException {
        DatagramPacket inputPacket = receivePacket(datagramSocket); // this one throws exception
        // check if Packet is empty
        if (inputPacket == null) return null;
        return getTransit(inputPacket);
    }

    /** Модуль чтения запроса Fixed Thread Pool needed*/
    public static CTransitPack getTransit(DatagramPacket datagramPacket){
        CTransitPack transitPack = (CTransitPack) Serializer.deserialize(datagramPacket.getData());
        if (transitPack == null || transitPack.getType() == null) return null;
        else {
            System.out.println("Sent from the client ( + " + transitPack.getUser().getUsername() +
                    ": " + transitPack.getType() + " " + transitPack.getParam());
            return transitPack;
        }
    }

    public static void sendMessage(DatagramSocket socket, ReplyPack pack, ClientData client) {
        Runnable sending = () -> {
            byte[] sendingDataBuffer = Serializer.serialize(pack).toByteArray();
            // Создайте новый UDP-пакет с данными, чтобы отправить их клиенту
            DatagramPacket outputPacket = new DatagramPacket(
                    sendingDataBuffer, sendingDataBuffer.length,
                    client.getSenderAddress(), client.getSenderPort()
            );

            // Отправьте пакет клиенту
            try {
                socket.send(outputPacket);
                System.out.println("ReplyPack " + pack + " is sent to client " + client + '\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Thread sender = new Thread(sending);
        sender.start();
    }


    /** Модуль приёма подключений
     *  Cached Thread Pool needed
    * @exception IOException exception if the limit of waiting time reached */
    public static DatagramPacket receivePacket(DatagramSocket datagramSocket) throws IOException {
        byte[] receivingDataBuffer = new byte[bufferSize];
        DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
        datagramSocket.setSoTimeout(awaitingTime);
        System.out.println("Connector: waiting for request");
        try {
            datagramSocket.receive(inputPacket);
        } catch (SocketException ignored) {
            return null;
        }

        //System.out.println(Arrays.toString(inputPacket.getData()));
        if (inputPacket.getData().length == 0) return null;
        Date date = new Date(System.currentTimeMillis());

        System.out.println(formatter.format(date)+": got pack from "+inputPacket.getAddress().toString());
        return inputPacket;
    }

    /**
     * Checks to see if a specific port is available.
     *
     * @param port the port to check for availability
     */
    public static boolean available(int port){
        if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
            return false;
        }

        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        } catch (IOException e) {
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    /* should not be thrown */
                }
            }
        }

        return false;
    }
}

