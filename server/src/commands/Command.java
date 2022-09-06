package commands;

import collections.CollectionCreator;
import collections.MusicBand;
import common.CTransitPack;
import common.Commands;
import common.ReplyPack;
import common.User;
import main.CollectionHolder;

import java.io.Serializable;

/**
 * abstract class for all command objects
 */
public abstract class Command implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Commands type;
    private final String param;
    private MusicBand receivedBand;

    public User getUser() {
        return user;
    }

    private final User user;


    public Command(Commands type, String param) {
        this.type = type;
        this.param = param;     // can be null
        if (this.type.isElementTaking()) initElement();
        this.user = new User("admin", "qwerty");
    }

    public Command(CTransitPack transitPack){
        this.type = transitPack.getType();
        this.param = transitPack.getParam();
        this.receivedBand = transitPack.getReceivedBand();
        this.user = transitPack.getUser();
    }

    /**
     * procedure of command execution when elementTaking is false
     *
     * @param cHolder - database to operate with
     */
    public abstract ReplyPack execute(CollectionHolder cHolder);

    /**
     * input element while you create Cmd object
     */
    private void initElement() {
        this.receivedBand = CollectionCreator.getClientBand(this.user);
        System.out.println("Command: Element initialised");
    }

    public boolean getExitStatus() {
        return false;
    }

    public Commands getType() {
        return type;
    }

    public String getParam() {
        return this.param != null ? this.param : "null";
    }

    public MusicBand getReceivedBand() {
        return this.receivedBand;
    }
}
