package commands.others;

import commands.Command;
import common.CTransitPack;
import common.Commands;
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
    public boolean execute(CollectionHolder cHolder) {
        if (Tools.regSearch(this.getParam(), "\\D")) {
            System.out.println("!!!wrong id!!!");
            return false;
        }
        cHolder.deleteElement(this.getParam());
        return true;
    }

}
