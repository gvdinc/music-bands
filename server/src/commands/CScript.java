package commands;

import main.CollectionHolder;
import main.CommandExecutor;
import main.Tools;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * Считать и исполнить скрипт из указанного файла.
 * В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
 */
public class CScript extends Comand {
    private final CollectionHolder cHolder;


    public CScript(CollectionHolder holder) {
        super(holder);
        this.cHolder = holder;
    }

    @Override
    public void cascadeRun(CommandExecutor commandExecutor, String params) throws IOException {
        if (!params.isEmpty()) {
            String commandsLine = "";
            BufferedInputStream buffer;
            try {
                buffer = Tools.getInputStream(params);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            if (buffer != null) {
                while (buffer.available() > 0) {
                    char currentSymbol = (char) buffer.read();
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
        } else {
            System.out.println("you forgot to give the pass-link");
        }
    }

    @Override
    public void execute(String input) {

    }


}
