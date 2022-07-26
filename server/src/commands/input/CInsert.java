package commands.input;


import collections.MusicBand;
import commands.Comand;
import main.CollectionCreator;
import main.CollectionHolder;
import main.CommandExecutor;
import main.Tools;

/**
 * добавить новый элемент с заданным ключом
 */
public class CInsert extends Comand {
    private final CollectionHolder cHolder;

    public CInsert(CollectionHolder holder) {
        super(holder);
        this.cHolder = holder;
    }

    @Override
    public void execute(String input) {

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
                id = this.cHolder.getMapLength() + 1;
            }

            if (!(id > this.cHolder.getMapLength())) {
                id = this.cHolder.getMapLength() + 1;
            }
            newBand.setId(id);
            cHolder.addNewGroup(newBand);
            System.out.println("finished");
            return;
        }
        System.out.println("Impossible to add");

    }

}
