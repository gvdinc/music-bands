package commands.output;

import commands.Command;
import common.CTransitPack;
import common.Commands;
import common.ReplyPack;
import main.CollectionHolder;

/**
 * : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
 */
public class CShow extends Command {

    public CShow(Commands type, String param) {
        super(type, param);
    }

    public CShow(CTransitPack transitPack) {
        super(transitPack);
    }

    public ReplyPack execute(CollectionHolder cHolder) {
        return new ReplyPack(Commands.SHOW, true, cHolder.getMap());
    }

}
