package client;

import common.CTransitPack;
import common.ReplyPack;
import common.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class Connector {
    private final SocketAddress address;
    private DatagramChannel channel;
    private ClientState clientState = ClientState.OFFLINE;
    private static final int bufferSize = 2024;
    public static final int connectionDelay = 3000;


    public Connector(SocketAddress address){
        try {
            channel = initChannel();
        } catch (IOException e) {
            System.out.println("Unable to start the client");
            e.printStackTrace();
        }
        this.address = address;
    }

    private DatagramChannel initChannel() throws IOException {
        DatagramChannel channel = DatagramChannelBuilder.bindChannel(null);
        channel.configureBlocking(false);
        this.clientState = ClientState.UNAUTHORIZED; // without it - can be static
        return channel;
    }
    public void disconnect(){
        try {
            channel.disconnect();
            clientState = ClientState.OFFLINE;
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

    private ReplyPack receivePack()throws IOException {
        ByteBuffer respBuffer = ByteBuffer.allocate(bufferSize);
        ReplyPack replyPack;
        try{
            channel.receive(respBuffer);
            byte[] arr = new byte[2024];
            respBuffer.rewind();
            try {
                respBuffer.get(arr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            replyPack = (ReplyPack) Serializer.deserialize(arr);
            return replyPack;
        }
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
        ReplyPack res = new ReplyPack(null, false);
        while (res != null) {
            try {
                res = receivePack();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ReplyPack getReplyAttempt(int delay, int amountOfRequests){
        for (int i = 0; i < amountOfRequests; i++){
            await(100);
            ReplyPack replyPack = null;
            try {
                replyPack = receivePack();
            } catch (IOException e) {
                e.printStackTrace(); return null;
            } // receiving

            if (replyPack != null){
                return replyPack;
            }
            else {
                System.out.println("attempt " + (i+1) + " of " + amountOfRequests + " Failed") ;
                if (i < amountOfRequests - 1) await(delay);
            }
        }
        return null;
    }
    public ReplyPack getReplyAttempt(int amountOfRequests){
        return getReplyAttempt(connectionDelay, amountOfRequests);
    }
    public ReplyPack getReplyAttempt(){
        return getReplyAttempt(connectionDelay, 3);
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

    public void setClientState(ClientState clientState) {
        this.clientState = clientState;
    }

}
