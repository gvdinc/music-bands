package serverUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Connector {
    private DatagramSocket datagramSocket;
    private ClientData client;
    private boolean isConnected;

    public Connector(int serverPort) throws SocketException {
        this.isConnected = false;
        this.initialize(serverPort);
        meetClient(1024);
        //closeSocket();
    }

    private void initialize(int serverPort) throws SocketException {
        this.datagramSocket = new DatagramSocket(serverPort);
        System.out.println("DatagramSocket initialised on port " + serverPort);
    }

    private void meetClient(int bufferLength) {
        try {

      /* Буферы для хранения отправляемых и получаемых данных.
Они временно хранят данные в случае задержек связи */
            byte[] receivingDataBuffer = new byte[bufferLength];
            byte[] sendingDataBuffer = new byte[bufferLength];

            /* Создайте экземпляр UDP-пакета для хранения клиентских данных с использованием буфера для полученных данных */
            DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
            System.out.println("Waiting for a client to connect...");

            // Получите данные от клиента и сохраните их в inputPacket
            this.datagramSocket.receive(inputPacket);

            // Выведите на экран отправленные клиентом данные
            String receivedData = new String(inputPacket.getData());
            System.out.println("Sent from the client: " + receivedData.trim());

            /*
             * Преобразуйте отправленные клиентом данные в верхний регистр,
             * Преобразуйте их в байты
             * и сохраните в соответствующий буфер. */
            sendingDataBuffer = receivedData.toUpperCase().getBytes();

            // Получите IP-адрес и порт клиента
            InetAddress senderAddress = inputPacket.getAddress();
            int senderPort = inputPacket.getPort();
            this.client = new ClientData(senderAddress, senderPort);

            // Создайте новый UDP-пакет с данными, чтобы отправить их клиенту
            DatagramPacket outputPacket = new DatagramPacket(
                    sendingDataBuffer, sendingDataBuffer.length,
                    senderAddress, senderPort
            );

            // Отправьте пакет клиенту
            datagramSocket.send(outputPacket);
            System.out.println("client connected: " + this.client.toString());
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void reconnect() {
        this.isConnected = false;
        meetClient(1024);
    }

    private void closeSocket() {
        this.datagramSocket.close();
        this.datagramSocket = null;
        System.out.println("DatagramSocket closed!");
    }

    public DatagramSocket getDSocket() {
        return this.datagramSocket;
    }

    public ClientData getClient() {
        return client;
    }

    public boolean isConnected() {
        return isConnected;
    }
}
