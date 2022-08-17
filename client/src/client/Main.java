package client;

public class Main {
    public static void main(String[] args) {
        UDPClient client = null;

        int serverPort = new Integer(KeyboardReader.input("Print the server port: "));
        client = new UDPClient(serverPort);
        client.run();

        //namespace
        //common package; serialize command
        //IOException
        //close channel
        //host port client; port server
        //client-server modules
    }
}
