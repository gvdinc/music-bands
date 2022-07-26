package commands;

import collections.MusicBand;
import main.CollectionHolder;
import main.Tools;

import java.util.Stack;
import java.util.function.Predicate;

/**
 * удалить из коллекции все элементы, ключ которых меньше, чем заданный
 */
public class CRemoveLowerKey extends Comand {
    private final CollectionHolder holder;

    public CRemoveLowerKey(CollectionHolder holder) {
        super(holder);
        this.holder = holder;
    }

    @Override
    public void execute(String input) {
        if (Tools.regSearch(input, "\\D")) {
            System.out.println("!!!wrong id!!!");
            return;
        }

        Stack<Integer> queueToDelete = new Stack<>();
        Predicate<MusicBand> numberFilter = musicBand -> musicBand.getId() > 0 && musicBand.getId() < new Integer(input);
        this.holder.getMapStream().filter(numberFilter).forEach(mB -> {
            queueToDelete.push(mB.getId());
        });
        queueToDelete.forEach(a -> {
            this.holder.deleteElement(a);
        });
    }


}
