package commands.input;


import commands.Command;
import common.CTransitPack;
import common.Commands;
import common.ReplyPack;
import main.CollectionHolder;
import main.Tools;

/**
 * добавить новый элемент с заданным ключом
 */
public class CInsert extends Command {

    public CInsert(Commands type, String param) {
        super(type, param);
    }

    public CInsert(CTransitPack transitPack) {
        super(transitPack);
    }


    @Override
    public ReplyPack execute(CollectionHolder cHolder) {
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
            return new ReplyPack(Commands.INSERT, true);
        }
        System.out.println("Impossible to add");
        return new ReplyPack(Commands.INSERT, false);
    }

}
