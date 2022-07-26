package commands;

import main.CollectionHolder;

/**
 * команда вызывается при невозможности использования иной команды
 */
public class incorrectC extends Comand {
    public incorrectC(CollectionHolder holder) {
        super(holder);
    }

    @Override
    public void execute(String input) {

    }

}
