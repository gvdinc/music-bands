package commands.others;

import commands.Command;
import common.CTransitPack;
import common.Commands;
import common.ReplyPack;
import main.CollectionHolder;
import main.CommandExecutor;
import main.Tools;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * Считать и исполнить скрипт из указанного файла.
 * В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
 */
@Deprecated
public class CScript extends Command {


    public CScript(Commands type, String param) {
        super(type, param);
    }

    public CScript(CTransitPack transitPack) {
        super(transitPack);
    }

    @Override
    public ReplyPack execute(CollectionHolder cHolder) {
        CommandExecutor commandExecutor = new CommandExecutor(cHolder);
        if (!this.getParam().isEmpty()) {
            String commandsLine = "";
            BufferedInputStream buffer;
            try {
                buffer = Tools.getInputStream(this.getParam());
            } catch (IOException e) {
                e.printStackTrace();
                return new ReplyPack(Commands.EXECUTE, false);
            }
            if (buffer != null) {
                while (true) {
                    try {
                        if (!(buffer.available() > 0)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    char currentSymbol = 0;
                    try {
                        currentSymbol = (char) buffer.read();
                    } catch (IOException e) {
                        System.out.println("unexpected error");
                        e.printStackTrace();
                        return new ReplyPack(Commands.EXECUTE, false);
                    }
                    commandsLine += currentSymbol;
                }
            }
            String[] commands = commandsLine.split("\n");
            for (int i = 0; i != commands.length; i++) {
                commands[i] = commands[i].trim();
                System.out.println("ScriptExecutor: running command " + commands[i]);
                commandExecutor.runCommand(commands[i]);
            }
            System.out.println("ScriptExecutor: execution completed");
            return new ReplyPack(Commands.EXECUTE, true);
        } else {
            System.out.println("you forgot to give the pass-link");
            return new ReplyPack(Commands.EXECUTE, false);
        }
    }

}
