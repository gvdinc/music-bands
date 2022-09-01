package client;

import UI.Display;
import common.CTransitPack;
import common.Commands;
import common.ReplyPack;
import common.User;

import java.net.InetSocketAddress;


public class UDPClient implements Runnable {

    private final ClientCommander commander = new ClientCommander();
    private User user;
    private boolean notRegistered = true;
    private final Connector connector;

    public UDPClient(int serverPort) {
        this.connector = new Connector(new InetSocketAddress("localhost", serverPort));
    }

    @Override
    public void run(){

        RUN:while (connector.getClientState() != ClientState.OFFLINE) {
            connector.flushMessages(); // cleaning the cast

            switch (connector.getClientState()){

                case UNAUTHORIZED: {
                    authorise();
                    CTransitPack authorisationTransitPack = new CTransitPack(notRegistered ? Commands.REGISTER : Commands.AUTHORIZE, null, user);
                    connector.sendCommand(authorisationTransitPack);
                    ReplyPack replyPack = connector.getReplyAttempt();
                    Display.displayPackData(replyPack);
                    if ((replyPack.getCommandType() == Commands.AUTHORIZE || replyPack.getCommandType() == Commands.REGISTER) && replyPack.isOperationSucceeded()){
                        this.connector.setClientState(ClientState.AUTHORISED);
                    }
                    continue;
                }

                case AUTHORISED: {
                    CTransitPack transitPack = commander.getCommand(user);
                    if (transitPack == null) continue;
                    transitPack.setUser(user);
                    connector.sendCommand(transitPack);
                    if (transitPack.getType() == Commands.EXIT) {connector.disconnect(); break RUN;}
                    ReplyPack replyPack = connector.getReplyAttempt();
                    if (replyPack != null) Display.displayPackData(replyPack);
                    else {connector.disconnect(); connector.flushMessages();
                    }
                    continue;
                }
            }
        }
    }

    private void authorise(){
        boolean notRegistered = KeyboardReader.input("needs registration? Y/N").trim().equalsIgnoreCase("Y");
        user = notRegistered ? register() : login();
        this.notRegistered = notRegistered;
    }

    private static User login(){
        String name = KeyboardReader.inputNotNull("login: ");
        String password = KeyboardReader.inputNotNull("password: ");
        return new User(name, password);
    }

    private static User register(){
        String newName = KeyboardReader.inputNotNull("set your login: ");
        String newPassword = KeyboardReader.inputNotNull("set the password: ");
        return new User(newName, newPassword);
    }
}
