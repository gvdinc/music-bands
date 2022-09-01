package commands.output;

import commands.Command;
import common.CTransitPack;
import common.Commands;
import common.ReplyPack;
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
    public ReplyPack execute(CollectionHolder cHolder) {
        return new ReplyPack(Commands.EXIT, true);
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }
}
