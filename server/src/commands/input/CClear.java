package commands.input;

import commands.Command;
import common.CTransitPack;
import common.Commands;
import common.ReplyPack;
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
    public ReplyPack execute(CollectionHolder cHolder) {
        cHolder.clearMap(this.getUser().getUsername());
        return new ReplyPack(Commands.CLEAR, true);
    }

}
