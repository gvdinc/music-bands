package common;

import collections.CollectionCreator;
import collections.MusicBand;

import java.io.Serializable;

public class CTransitPack implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Commands type;
    private final String param;
    private MusicBand receivedBand;
    private User user;


    public CTransitPack(Commands type, String param, User user) {
        this.type = type;
        this.param = param;     // can be null
        this.user = user;
        if (this.type.isElementTaking()) initElement();
    }

    /**
     * input element while you create Cmd object
     */
    private void initElement() {
        this.receivedBand = CollectionCreator.getClientBand(user);
        System.out.println("Command: Element initialised");
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

