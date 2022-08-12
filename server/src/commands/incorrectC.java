package commands;

import common.Commands;
import main.CollectionHolder;

/**
 * команда вызывается при невозможности использования иной команды
 */
public class incorrectC extends Command {


    public incorrectC(Commands type, String param) {
        super(type, param);
    }

    @Override
    public void execute(CollectionHolder cHolder) {

    }

}
