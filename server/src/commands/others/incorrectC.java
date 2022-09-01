package commands.others;

import commands.Command;
import common.Commands;
import common.ReplyPack;
import main.CollectionHolder;

/**
 * команда вызывается при невозможности использования иной команды
 */
public class incorrectC extends Command {


    public incorrectC(Commands type, String param) {
        super(type, param);
    }

    @Override
    public ReplyPack execute(CollectionHolder cHolder) {
        return new ReplyPack(Commands.PING, false);
    }

}
