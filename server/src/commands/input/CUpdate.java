package commands.input;

import commands.Command;
import common.CTransitPack;
import common.Commands;
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
    public boolean execute(CollectionHolder cHolder) {
        System.out.println(this.getParam());
        if (Tools.regSearch(this.getParam(), "\\D")) {
            System.out.println("!!!wrong id!!!");
            return false;
        }

        if (this.getReceivedBand().isCorrect()) {
            int id;
            try {
                id = new Integer(this.getParam());
            } catch (NumberFormatException e) {
                System.out.println("!!!wrong id!!!");
                return false;
            }

            if (!(id > cHolder.getMapLength())) {
                System.out.println("!!!wrong id!!!");
                return false;
            }
            this.getReceivedBand().setId(id);
            cHolder.addNewGroup(this.getReceivedBand());
            System.out.println("finished");
            return true;
        }
        System.out.println("Impossible to update");
        return false;
    }


}
