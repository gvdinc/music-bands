package commands.input;

import commands.Comand;
import main.CollectionHolder;

/**
 * очистить коллекцию
 */
public class CClear extends Comand {
    private final CollectionHolder cHolder;

    public CClear(CollectionHolder holder) {
        super(holder);
        this.cHolder = holder;
    }

    @Override
    public void execute(String input) {
        cHolder.clearMap();
    }

}
