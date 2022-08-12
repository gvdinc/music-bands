package commands.output;

import commands.Command;
import common.Commands;
import main.CollectionHolder;

/**
 * завершить программу (без сохранения в файл)
 */
public class CExit extends Command {


    public CExit(Commands type, String param) {
        super(type, param);
}

    @Override
    public void execute(CollectionHolder cHolder) {

    }

    @Override
    public boolean getExitStatus() {
        return true;
    }
}
