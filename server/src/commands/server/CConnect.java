package commands.server;
import commands.Command;
import common.CTransitPack;
import common.Commands;
import main.CollectionHolder;

public class CConnect extends Command {

    public CConnect(Commands type, String param) {
        super(type, param);
    }

    public CConnect(CTransitPack transitPack) {
        super(transitPack);
    }

    @Override
    public boolean execute(CollectionHolder cHolder) {
        return true;
    }
}
