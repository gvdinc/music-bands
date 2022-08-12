package commands.input;

import commands.Command;
import common.Commands;
import main.CollectionHolder;

/**
 * очистить коллекцию
 */
public class CClear extends Command {

    public CClear(Commands type, String param) {
        super(type, param);
    }

    @Override
    public void execute(CollectionHolder cHolder) {
        cHolder.clearMap();
    }

}
