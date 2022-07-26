package commands.output;

import commands.Comand;
import main.CollectionHolder;

/**
 * : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
 */
public class CShow extends Comand {
    private final CollectionHolder holder;

    public CShow(CollectionHolder holder) {
        super(holder);
        this.holder = holder;
    }

    @Override
    public void execute(String input) {
        this.holder.readMap();
    }
}
