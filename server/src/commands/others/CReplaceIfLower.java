package commands.others;

import commands.Command;
import common.CTransitPack;
import common.Commands;
import common.ReplyPack;
import main.CollectionHolder;

/**
 * заменить значение по ключу, если новое значение меньше старого
 */
public class CReplaceIfLower extends Command {

    public CReplaceIfLower(Commands type, String param) {
        super(type, param);
    }

    public CReplaceIfLower(CTransitPack transitPack) {
        super(transitPack);
    }

    @Override
    public ReplyPack execute(CollectionHolder cHolder) {
        int id;
        try {
            id = new Integer(this.getParam());
        } catch (NumberFormatException e) {
            System.out.println("!!!wrong id!!!");
            return new ReplyPack(Commands.REPLACE_IF_LOWER, false);
        }

        this.getReceivedBand().setId(id);
        if (this.getReceivedBand().getNumberOfParticipants() < cHolder.getNumberOfParticipants(id)) {
            cHolder.replaceGroup(this.getReceivedBand());
            System.out.println("finished");
        } else {
            System.out.println("Element with this id has equals or bigger amount of participants");
        }
        return new ReplyPack(Commands.REPLACE_IF_LOWER, true);
    }

}
