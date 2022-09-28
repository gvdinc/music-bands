package commands.input;

import commands.Command;
import common.CTransitPack;
import common.Commands;
import common.ReplyPack;
import main.CollectionHolder;
import main.Tools;

/**
 * обновить значение элемента коллекции, id которого равен заданному
 */
public class CUpdate extends Command {

    public CUpdate(Commands type, String param) {
        super(type, param);
    }

    public CUpdate(CTransitPack transitPack) {
        super(transitPack);
    }

    @Override
    public ReplyPack execute(CollectionHolder cHolder) {
        System.out.println(getParam());
        if (Tools.regSearch(getParam(), "\\D"))
            System.out.println("!!!wrong id!!!");
        if (getReceivedBand().isCorrect()) {
            int id;
            try {
                id = new Integer(getParam());
            } catch (NumberFormatException e) {
                System.out.println("!!!wrong id!!!");
                return new ReplyPack(Commands.UPDATE, false);
            }
            getReceivedBand().setId(id);
            cHolder.updateGroup(getReceivedBand());
            System.out.println("finished");
            return new ReplyPack(Commands.UPDATE, true);
        }
        System.out.println("Impossible to update");
        return new ReplyPack(Commands.UPDATE, false);
    }


}
