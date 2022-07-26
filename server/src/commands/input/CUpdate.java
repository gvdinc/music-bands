package commands.input;

import collections.MusicBand;
import commands.Comand;
import main.CollectionCreator;
import main.CollectionHolder;
import main.CommandExecutor;
import main.Tools;

/**
 * обновить значение элемента коллекции, id которого равен заданному
 */
public class CUpdate extends Comand {
    private final CollectionHolder cHolder;

    public CUpdate(CollectionHolder holder) {
        super(holder);
        this.cHolder = holder;
    }

    @Override
    public void cascadeRun(CommandExecutor commandExecutor, String params) {
        System.out.println(params);
        if (Tools.regSearch(params, "\\D")) {
            System.out.println("!!!wrong id!!!");
            return;
        }
        MusicBand newBand = CollectionCreator.getClientBand(commandExecutor);


        if (newBand.isCorrect()) {
            int id;
            try {
                id = new Integer(params);
            } catch (NumberFormatException e) {
                System.out.println("!!!wrong id!!!");
                return;
            }

            if (!(id > this.cHolder.getMapLength())) {
                System.out.println("!!!wrong id!!!");
                return;
            }
            newBand.setId(id);
            cHolder.addNewGroup(newBand);
            System.out.println("finished");
            return;
        }
        System.out.println("Impossible to update");

    }

    @Override
    public void execute(String input) {
        //cHolder.updateID();
    }

}
