package client;

import common.Command;
import common.Commands;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Timer;
import java.util.TimerTask;


public class UDPClient implements Runnable {

    private final int serverPort;
    private final ClientCommander commander = new ClientCommander();


    public UDPClient(int serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public void run(){
        DatagramChannel client = null; // will be done later
    }

    @Deprecated
    public void oldRun() {
//        this.address = new InetSocketAddress("localhost", serverPort);
//            sendCommand(client, address, new Command(Commands.PING, "hello"));
//            while (true) {
//                String receivedMessage = null; // 3 попытки получения - нет, значит всё
//                try {
//
//                    int timer = 3;
//                    while (timer > 0) {
//                        receivedMessage = getMessage(client);
//                        if (receivedMessage.isEmpty()){
//                            Thread.sleep(3000);
//                            receivedMessage = getMessage(client);
//                            if (receivedMessage.isEmpty()){
//                                sendCommand(client, address, new Command(Commands.PING, "hello"));
//                                System.out.println("No incoming message: attempt "+ timer);
//                                timer -= 1;
//                            }
//                            else {
//                                break;
//                            }
//                        }
//                        else break;
//                    }
//
//                    if (receivedMessage.isEmpty()) {System.out.println("server not avalible"); break;}
//                    else{ System.out.println("got s message"); }
//                } catch (IOException | InterruptedException e) {
//                    System.out.println("unable to send a message");
//                    e.printStackTrace();
//                }
//                System.out.println("Got from server: " + receivedMessage);
//                Command cmd = this.commander.getCommand();
//                while (cmd.getType() == null) {
//                    cmd = this.commander.getCommand();
//                }
//                sendCommand(client, address, cmd);
//                if (cmd.getType() == Commands.EXIT) {
//                    closeConnection(client);
//                    break;
//                }
//            }
    }

}


class TimeOutTask extends TimerTask {
    private Thread t;
    private Timer timer;

    TimeOutTask(Thread t, Timer timer){
        this.t = t;
        this.timer = timer;
    }

    public void run() {
        if (t != null && t.isAlive()) {
            t.interrupt();
            timer.cancel();
        }
    }

    public void stop(){
        this.timer.cancel();
    }
}