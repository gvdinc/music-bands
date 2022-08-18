package client;

import common.CTransitPack;
import common.Commands;
import common.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class Connector {
    private SocketAddress address;
    private DatagramChannel channel;
    private ClientState clientState = ClientState.OFFLINE;
    private static final int bufferSize = 2024;
    public static final int connectionDelay = 3000;
    private static String clientIP = "127.0.0.1";

    /*
    static {
        try {
            clientIP = Inet4Address.getLocalHost().getHostAddress().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }*/

    public Connector(){
        try {
            channel = initChannel();
        } catch (IOException e) {
            System.out.println("Unable to start the client");
            e.printStackTrace();
            return;
        }

    }

    private DatagramChannel initChannel() throws IOException {
        DatagramChannel channel = DatagramChannelBuilder.bindChannel(null);
        channel.configureBlocking(false);
        this.clientState = ClientState.UNCONNECTED; // without it - can be static
        return channel;
    }
    public void disconnect(){
        try {
            channel.disconnect();
            clientState = ClientState.UNCONNECTED;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void closeChannel() {
        try {
            channel.disconnect();
            clientState = ClientState.OFFLINE;
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // client - server communicating
    public boolean connectToServer(SocketAddress address) throws IOException { // TODO: fix the exception
        if (clientState != ClientState.UNCONNECTED) return false;
        this.address = address;
        //this.channel.connect(address);
        flushMessages();
        for (int i = 0; i < 3; i++){
            sendCommand(new CTransitPack(Commands.CONNECT, null));
            await(60);
            String message = getMessage();
            if (message != null && message.equals("OPERATING /"+ clientIP)){
                System.out.println("successfully connected");
                clientState = ClientState.CONNECTED;
                return true;
            }
            else {
                System.out.println("attempt " + (i+1) + " of 3 Failed") ;
                if (i < 2) await(connectionDelay);
            }
        }
        System.out.println("server offline");
        //this.closeChannel();
        return false;
    }

    private String getMessage()throws IOException {
        ByteBuffer respBuffer = ByteBuffer.allocate(bufferSize);
        try{
            channel.receive(respBuffer);
            //System.out.println("got message");
            String res = readByteBuffer(respBuffer);
            System.out.println("received message: " + (res.length() <= 0 ? "NONE" : res));
            return res.length() > 0 ? res.trim() : null;}
        catch (RuntimeException r){
            return null;
        }
    }

    public void sendCommand(CTransitPack cmd) {
        //if (!this.channel.isConnected()) {System.out.println("can not send because not connected");return;}
        ByteArrayOutputStream serialized = Serializer.serialize(cmd);
        try {
            channel.send(ByteBuffer.wrap(serialized.toByteArray()), address);
        } catch (IOException e) {
            e.printStackTrace();
            disconnect();
        }
    }

    public void flushMessages(){
        String res = "easter egg";
        while (res != null) {
            System.out.print("FLUSHED: ");
            try {
                res = getMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getMessageAttempt(int delay, int amountOfRequests){
        for (int i = 0; i < 3; i++){
            await(10);
            String message = null;
            try {
                message = getMessage();
            } catch (IOException e) {
                e.printStackTrace(); return null;
            } // receiving

            if (message != null && message.length() > 0){
                return message;
            }
            else {
                System.out.println("attempt " + (i+1) + " of " + amountOfRequests + " Failed") ;
                if (i < amountOfRequests - 1) await(connectionDelay);
                await(delay);
            }
        }
        return null;
    }
    public String getMessageAttempt(int amountOfRequests){
        return getMessageAttempt(connectionDelay, amountOfRequests);
    }
    public String getMessageAttempt(){
        return getMessageAttempt(connectionDelay, 3);
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

    public static void await(int milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ignored) {
        }
    }

    // setters, getters
    public ClientState getClientState() {
        return clientState;
    }
}
