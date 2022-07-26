package serverUDP;

import java.net.InetAddress;

public class ClientData {
    private InetAddress senderAddress;
    private int senderPort;

    public ClientData(InetAddress senderAddress, int senderPort) {
        this.senderAddress = senderAddress;
        this.senderPort = senderPort;
    }

    public int getSenderPort() {
        return senderPort;
    }

    public void setSenderPort(int senderPort) {
        this.senderPort = senderPort;
    }

    public InetAddress getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(InetAddress senderAddress) {
        this.senderAddress = senderAddress;
    }

    @Override
    public String toString() {
        return "ClientData{" +
                "senderAddress=" + senderAddress +
                ", senderPort=" + senderPort +
                '}';
    }
}
