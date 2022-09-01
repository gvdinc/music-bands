package client;

public class Main {
    public static void main(String[] args) {


        UDPClient client = null;
        int serverPort;
        while(true) {
            try {
                serverPort = new Integer(KeyboardReader.input("Input the server port: "));
                break;
            } catch (NumberFormatException e) {
                System.out.println("wrong port - try again");
            }
        }
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
