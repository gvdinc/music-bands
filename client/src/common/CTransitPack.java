package common;

import java.io.Serializable;

public class CTransitPack extends Command implements Serializable {

    public CTransitPack(Commands type, String param) {
        super(type, param);
    }
}
