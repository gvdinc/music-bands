package commands.output;

import commands.Comand;
import main.CollectionHolder;

/**
 * вывести в стандартный поток вывода информацию о коллекции
 * (тип, дата инициализации, количество элементов и т.д.)
 */
public class CInfo extends Comand {
    private final CollectionHolder holder;

    public CInfo(CollectionHolder holder) {
        super(holder);
        this.holder = holder;
    }

    @Override
    public void execute(String input) {
        holder.mapInfo();
    }

}
