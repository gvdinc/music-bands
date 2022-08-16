package commands.others;

import common.Command;
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

    @Override
    public void execute(CollectionHolder cHolder) {
        if (Tools.regSearch(this.getParam(), "\\D")) {
            System.out.println("!!!wrong id!!!");
            return;
        }
        cHolder.deleteElement(this.getParam());
    }

}
