package commands.output;

import commands.Comand;
import main.CollectionHolder;
import main.HolderSortTypes;

/**
 * вывести любой объект из коллекции, значение поля id которого является минимальным
 */
public class CMin extends Comand {
    private final CollectionHolder cHolder;

    public CMin(CollectionHolder holder) {
        super(holder);
        this.cHolder = holder;
    }

    @Override
    public void execute(String input) { // min by id
        String res = (this.cHolder.getMinGroup().toString());
        System.out.println(res != null ? "group with minimal id: " + res : "empty collection");
    }

    @Deprecated
    public void oldExecute(String input) { // min by id
        this.cHolder.sort(HolderSortTypes.DEFAULT);
        this.cHolder.ReadCollectionElement(1);
    }
}
