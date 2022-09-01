package commands.output;

import commands.Command;
import common.CTransitPack;
import common.Commands;
import common.ReplyPack;
import main.CollectionHolder;

/**
 * вывести в стандартный поток вывода информацию о коллекции
 * (тип, дата инициализации, количество элементов и т.д.)
 */
public class CInfo extends Command {

    public CInfo(Commands type, String param) {
        super(type, param);
    }

    public CInfo(CTransitPack transitPack) {
        super(transitPack);
    }

    @Override
    public ReplyPack execute(CollectionHolder cHolder) {
        return new ReplyPack(Commands.INFO, true, cHolder.mapInfo());
    }

}
