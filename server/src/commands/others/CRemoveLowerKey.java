package commands.others;

import collections.MusicBand;
import commands.Command;
import common.CTransitPack;
import common.Commands;
import common.ReplyPack;
import main.CollectionHolder;
import main.Tools;

import java.util.Stack;
import java.util.function.Predicate;

/**
 * удалить из коллекции все элементы, ключ которых меньше, чем заданный
 */
public class CRemoveLowerKey extends Command {


    public CRemoveLowerKey(Commands type, String param) {
        super(type, param);
    }

    public CRemoveLowerKey(CTransitPack transitPack) {
        super(transitPack);
    }

    @Override
    public ReplyPack execute(CollectionHolder cHolder) {
        if (Tools.regSearch(this.getParam(), "\\D")) {
            System.out.println("!!!wrong id!!!");
            return new ReplyPack(Commands.REMOVE_LOWER_KEY, false);
        }

        Stack<Integer> queueToDelete = new Stack<>();
        Predicate<MusicBand> numberFilter = musicBand -> musicBand.getId() > 0 && musicBand.getId() < new Integer(this.getParam());
        cHolder.getMapStream().filter(numberFilter).forEach(mB -> {
            queueToDelete.push(mB.getId());
        });
        for (Integer i: queueToDelete
        ) {
            cHolder.deleteElement(i, this.getUser().getUsername());
        }
        return new ReplyPack(Commands.REMOVE_LOWER_KEY, true);
    }

}
