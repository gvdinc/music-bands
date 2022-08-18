package client;

import common.CTransitPack;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import static client.Connector.await;


public class UDPClient implements Runnable {

    private final int serverPort;
    private final ClientCommander commander = new ClientCommander();
    private Connector connector = new Connector();

    public UDPClient(int serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public void run(){
        RUN:while (connector.getClientState() != ClientState.OFFLINE) { // TODO: printed double server message (show)
            switch (connector.getClientState()){

                case UNCONNECTED:{
                    boolean res = false;
                    try {
                        res = connector.connectToServer(new InetSocketAddress("localhost", serverPort));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (!res) await(10000);
                    connector.flushMessages();
                    continue RUN;
                }

                case CONNECTED: {
                    CTransitPack transitPack = commander.getCommand();
                    if (transitPack == null) continue ;
                    connector.flushMessages(); // cleaning the cast
                    connector.sendCommand(transitPack);
                    if (transitPack.getExitStatus()) {connector.disconnect(); break RUN;}
                    String message = connector.getMessageAttempt();
                    if (message != null) commander.interactMessage(message);
                    else {connector.disconnect(); connector.flushMessages(); continue; }
                }
            }
            //connector.connect(new InetSocketAddress("localhost", serverPort));
        }
    }

}
