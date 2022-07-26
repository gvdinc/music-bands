package commands.output;

import commands.Comand;
import main.CollectionHolder;

/**
 * завершить программу (без сохранения в файл)
 */
public class CExit extends Comand {
    public CExit(CollectionHolder holder) {
        super(holder);
    }

    @Override
    public void execute(String input) {

    }


    @Override
    public boolean getExitStatus() {
        return true;
    }
}
