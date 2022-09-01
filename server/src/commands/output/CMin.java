package commands.output;

import commands.Command;
import common.CTransitPack;
import common.Commands;
import common.ReplyPack;
import main.CollectionHolder;

/**
 * вывести любой объект из коллекции, значение поля id которого является минимальным
 */
public class CMin extends Command {

    public CMin(Commands type, String param) {
        super(type, param);
    }

    public CMin(CTransitPack transitPack) {
        super(transitPack);
    }

    @Override
    public ReplyPack execute(CollectionHolder cHolder) {
        String res = (cHolder.getMinGroup().toString());
        System.out.println(res != null ? "group with minimal id: " + res : "empty collection");
        return new ReplyPack(Commands.MIN_BY_ID, true, res);
    }

}
