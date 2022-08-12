package commands.output;

import commands.Command;
import common.Commands;
import main.CollectionHolder;

/**
 * вывести в стандартный поток вывода информацию о коллекции
 * (тип, дата инициализации, количество элементов и т.д.)
 */
public class CInfo extends Command {

    public CInfo(Commands type, String param) {
        super(type, param);
    }

    @Override
    public void execute(CollectionHolder cHolder) {
        cHolder.mapInfo();
    }

}
