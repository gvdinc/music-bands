package commands.others;

import collections.MusicBand;
import common.Command;
import common.Commands;
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

    @Override
    public void execute(CollectionHolder cHolder) {
        if (Tools.regSearch(this.getParam(), "\\D")) {
            System.out.println("!!!wrong id!!!");
            return;
        }

        Stack<Integer> queueToDelete = new Stack<>();
        Predicate<MusicBand> numberFilter = musicBand -> musicBand.getId() > 0 && musicBand.getId() < new Integer(this.getParam());
        cHolder.getMapStream().filter(numberFilter).forEach(mB -> {
            queueToDelete.push(mB.getId());
        });
        queueToDelete.forEach(cHolder::deleteElement);
    }

}
