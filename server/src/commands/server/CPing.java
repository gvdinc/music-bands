package commands.server;

import commands.Command;
import common.CTransitPack;
import common.Commands;
import main.CollectionHolder;

public class CPing extends Command {
    public CPing(Commands type, String param) {
        super(type, param);
    }

    public CPing(CTransitPack transitPack) {
        super(transitPack);
    }

    @Override
    public boolean execute(CollectionHolder cHolder) {
        return true;
    }
}
