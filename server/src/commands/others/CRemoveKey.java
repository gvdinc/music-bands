package commands.others;

import commands.Command;
import common.CTransitPack;
import common.Commands;
import common.ReplyPack;
import main.CollectionHolder;
import main.Tools;

/**
 * удалить из коллекции все элементы, ключ которых меньше, чем заданный
 */
public class CRemoveKey extends Command {


    public CRemoveKey(Commands type, String param) {
        super(type, param);
    }

    public CRemoveKey(CTransitPack transitPack) {
        super(transitPack);
    }

    @Override
    public ReplyPack execute(CollectionHolder cHolder) {
        if (Tools.regSearch(this.getParam(), "\\D")) {
            System.out.println("!!!wrong id!!!");
            return new ReplyPack(Commands.REMOVE_KEY, false);
        }
        cHolder.deleteElement(this.getParam());
        return new ReplyPack(Commands.REMOVE_KEY, true);
    }

}
