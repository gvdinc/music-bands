package commands.input;


import common.Command;
import common.Commands;
import main.CollectionHolder;
import main.CommandExecutor;
import main.Tools;

/**
 * добавить новый элемент с заданным ключом
 */
public class CInsert extends Command {

    public CInsert(Commands type, String param) {
        super(type, param);
    }


    @Override
    public void execute(CollectionHolder cHolder) {
        System.out.println(this.getParam());
        if (Tools.regSearch(this.getParam(), "\\D")) {
            System.out.println("!wrong id!");
        }

        if (this.getReceivedBand().isCorrect()) {
            int id;
            try {
                id = new Integer(this.getParam());
            } catch (NumberFormatException e) {
                id = cHolder.getMapLength() + 1;
            }

            if (!(id > cHolder.getMapLength())) {
                id = cHolder.getMapLength() + 1;
            }
            this.getReceivedBand().setId(id);
            cHolder.addNewGroup(this.getReceivedBand());
            System.out.println("finished");
            return;
        }
        System.out.println("Impossible to add");
    }

    @Override
    public void cascadeRun(CommandExecutor commandExecutor, String params) {
        // unusable since 11.08.22
    }

}
