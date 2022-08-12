package commands.output;

import commands.Command;
import common.Commands;
import main.CollectionHolder;

/**
 * вывести любой объект из коллекции, значение поля id которого является минимальным
 */
public class CMin extends Command {

    public CMin(Commands type, String param) {
        super(type, param);
    }

    @Override
    public void execute(CollectionHolder cHolder) {
        String res = (cHolder.getMinGroup().toString());
        System.out.println(res != null ? "group with minimal id: " + res : "empty collection");
    }

}
