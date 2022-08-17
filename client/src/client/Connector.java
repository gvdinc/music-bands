package client;

import common.CTransitPack;
import common.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class Connector {
    private SocketAddress address;
    private DatagramChannel channel;
    private int port;
    private ClientState clientState = ClientState.OFFLINE;

    public Connector(){
        try {
            channel = initChannel();
        } catch (IOException e) {
            System.out.println("Unable to start the client");
            e.printStackTrace();
            return;
        }
        //this.address = new InetSocketAddress("localhost", serverPort);
    }

    private DatagramChannel initChannel() throws IOException {
        DatagramChannel channel = DatagramChannelBuilder.bindChannel(null);
        channel.configureBlocking(false);
        this.clientState = ClientState.UNCONNECTED; // without it - can be static
        return channel;
    }

    private void disconnect(){
        try {
            channel.disconnect();
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void closeChannel() {
        try {
            channel.disconnect();
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // client - server communicating
    public String getMessage(DatagramChannel client)throws IOException {
        ByteBuffer respBuffer = ByteBuffer.allocate(1024);
        System.out.println("waiting for message");
        try{
            client.receive(respBuffer);
            //System.out.println("got message");
            String res = readByteBuffer(respBuffer);
            return res;}
        catch (RuntimeException r){
            System.out.println("server is not available");
            //System.exit(1);
            return null;
        }
    }
    public boolean sendCommand(CTransitPack cmd){
        try {
            ByteArrayOutputStream serialized = Serializer.serialize(cmd);
            channel.send(ByteBuffer.wrap(serialized.toByteArray()), address);
            return true;
        }
        catch (IOException e) {
            System.out.println("unable to send");
            e.printStackTrace();
            return false;
        }

    }

    public static void sendMessage(DatagramChannel client, String msg, SocketAddress serverAddress) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        client.send(buffer, serverAddress);
    }


    // statics
    public static String readByteBuffer(ByteBuffer bf){
        String line = "";
        bf.position(0);
        while (bf.hasRemaining()) {
            byte[] b = new byte[1];
            b[0] = bf.get();
            line += new String(b, StandardCharsets.UTF_8);
        }
        return line.trim();
    }


}
