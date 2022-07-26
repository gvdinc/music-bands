package commands.input;

import commands.Comand;
import main.CollectionHolder;

/**
 * сохранить коллекцию в файл
 */
public class CSave extends Comand {
    private final CollectionHolder cHolder;

    public CSave(CollectionHolder holder) {
        super(holder);
        this.cHolder = holder;
    }

    @Override
    public void execute(String input) {
        this.cHolder.exportXML();
    }

}
