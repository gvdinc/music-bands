package commands.input;

import commands.Command;
import common.CTransitPack;
import common.Commands;
import common.ReplyPack;
import main.CollectionHolder;

/**
 * сохранить коллекцию в файл
 */
public class CSave extends Command {

    public CSave(Commands type, String param) {
        super(type, param);
    }

    public CSave(CTransitPack transitPack) {
        super(transitPack);
    }

    @Override
    public ReplyPack execute(CollectionHolder cHolder) {
        return new ReplyPack(Commands.SAVE, false);
    }

}
