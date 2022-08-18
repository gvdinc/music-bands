package commands.output;

import commands.Command;
import common.CTransitPack;
import common.Commands;
import main.CollectionHolder;

/**
 * завершить программу (без сохранения в файл)
 */
public class CExit extends Command {


    public CExit(Commands type, String param) {
        super(type, param);
}

    public CExit(CTransitPack transitPack) {
        super(transitPack);
    }

    @Override
    public boolean execute(CollectionHolder cHolder) {
        return true;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }
}
