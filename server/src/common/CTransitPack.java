package common;

import collections.CollectionCreator;
import collections.MusicBand;

import java.io.Serializable;

public class CTransitPack implements Serializable {


    private static final long serialVersionUID = 1L;
    private final Commands type;
    private final String param;
    private MusicBand receivedBand;

    public CTransitPack(Commands type, String param) {
        this.type = type;
        this.param = param;     // can be null
        if (this.type.isElementTaking()) initElement();
    }

    /**
     * input element while you create Cmd object
     */
    private void initElement() {
        this.receivedBand = CollectionCreator.getClientBand();
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
