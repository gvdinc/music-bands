package serverUDP;

import commands.Command;
import common.CTransitPack;
import common.ReplyPack;
import common.Serializer;
import main.CommandExecutor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

/** Module of getting connections and returns commands */
public class Connector{
    public static final int bufferSize = 2048;
    private static final int awaitingTime = 200000;

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
                System.out.println("ReplyPack " + pack + " is sent to client " + client);
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

        datagramSocket.receive(inputPacket);
        System.out.println(Arrays.toString(inputPacket.getData()));
        if (inputPacket.getData().length == 0) return null;
        return inputPacket;
    }
}
