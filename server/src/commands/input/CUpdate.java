package commands.input;

import common.Command;
import common.Commands;
import main.CollectionHolder;
import main.CommandExecutor;
import main.Tools;

/**
 * обновить значение элемента коллекции, id которого равен заданному
 */
public class CUpdate extends Command {

    public CUpdate(Commands type, String param) {
        super(type, param);
    }


    @Override
    public void execute(CollectionHolder cHolder) {
        System.out.println(this.getParam());
        if (Tools.regSearch(this.getParam(), "\\D")) {
            System.out.println("!!!wrong id!!!");
            return;
        }

        if (this.getReceivedBand().isCorrect()) {
            int id;
            try {
                id = new Integer(this.getParam());
            } catch (NumberFormatException e) {
                System.out.println("!!!wrong id!!!");
                return;
            }

            if (!(id > cHolder.getMapLength())) {
                System.out.println("!!!wrong id!!!");
                return;
            }
            this.getReceivedBand().setId(id);
            cHolder.addNewGroup(this.getReceivedBand());
            System.out.println("finished");
            return;
        }
        System.out.println("Impossible to update");
    }

    @Deprecated
    @Override
    public void cascadeRun(CommandExecutor commandExecutor, String params) {


    }



}
