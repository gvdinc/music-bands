package commands.output;

import commands.Command;
import common.Commands;
import main.CollectionHolder;

/**
 * : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
 */
public class CShow extends Command {

    public CShow(Commands type, String param) {
        super(type, param);
    }

    public void execute(CollectionHolder cHolder) {
        cHolder.readMap();
    }

}
