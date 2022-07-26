package commands;

import main.CollectionHolder;
import main.Tools;

/**
 * удалить из коллекции все элементы, ключ которых меньше, чем заданный
 */
public class CRemoveKey extends Comand {
    private final CollectionHolder cHolder;

    public CRemoveKey(CollectionHolder holder) {
        super(holder);
        this.cHolder = holder;
    }

    @Override
    public void execute(String input) {
        if (Tools.regSearch(input, "\\D")) {
            System.out.println("!!!wrong id!!!");
            return;
        }
        this.cHolder.deleteElement(input);
    }
}
