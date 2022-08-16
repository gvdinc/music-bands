package commands.others;

import common.Command;
import common.Commands;
import main.CollectionHolder;

/**
 * заменить значение по ключу, если новое значение меньше старого
 */
public class CReplaceIfLower extends Command {

    public CReplaceIfLower(Commands type, String param) {
        super(type, param);
    }

    @Override
    public void execute(CollectionHolder cHolder) {
        int id;
        try {
            id = new Integer(this.getParam());
        } catch (NumberFormatException e) {
            System.out.println("!!!wrong id!!!");
            return;
        }

        this.getReceivedBand().setId(id);
        if (this.getReceivedBand().getNumberOfParticipants() < cHolder.getNumberOfParticipants(id)) {
            cHolder.replaceGroup(this.getReceivedBand());
            System.out.println("finished");
        } else {
            System.out.println("Element with this id has equals or bigger amount of participants");
        }
    }

}
