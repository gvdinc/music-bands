package commands.input;

import commands.Command;
import common.CTransitPack;
import common.Commands;
import main.CollectionHolder;

/**
 * сохранить коллекцию в файл
 */
public class CSave extends Command {

    public CSave(Commands type, String param) {
        super(type, param);
    }

    public CSave(CTransitPack transitPack) {
        super(transitPack);
    }

    @Override
    public boolean execute(CollectionHolder cHolder) {
        cHolder.exportXML();
        return true;
    }

}
