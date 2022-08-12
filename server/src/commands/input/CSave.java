package commands.input;

import commands.Command;
import common.Commands;
import main.CollectionHolder;

/**
 * сохранить коллекцию в файл
 */
public class CSave extends Command {

    public CSave(Commands type, String param) {
        super(type, param);
    }

    @Deprecated
    @Override
    public void execute(CollectionHolder cHolder) {
        cHolder.exportXML();
    }

}
