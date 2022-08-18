package commands.input;

import commands.Command;
import common.CTransitPack;
import common.Commands;
import main.CollectionHolder;

/**
 * очистить коллекцию
 */
public class CClear extends Command {

    public CClear(Commands type, String param) {
        super(type, param);
    }

    public CClear(CTransitPack transitPack) {
        super(transitPack);
    }

    @Override
    public boolean execute(CollectionHolder cHolder) {
        cHolder.clearMap();
        return true;
    }

}
