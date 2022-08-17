package client;

import common.CTransitPack;
import common.Command;
import common.Commands;

import java.util.Locale;


public class ClientCommander {

    public Command getCommand(){
        String commandLine = KeyboardReader.input("input your command: ");
        String[] input = commandLine != null ? commandLine.split(" ",2) : null;
        if (input == null || input.length == 0) return null;
        Commands type = findType(input[0]);
        Command cmd = null;
        if (input.length > 1 && type != null) {
            cmd = new CTransitPack(type, input[1]);
        }
        else if (type != null){
            cmd = new CTransitPack(type, null);
        }
        return cmd;
    }

    private Commands findType(String s) {
        switch (s.toLowerCase(Locale.ROOT)){
            case "help": return Commands.HELP;
            case "info": return Commands.INFO;
            case "show": return Commands.SHOW;
            case "insert": return Commands.INSERT;
            case "update": return Commands.UPDATE;
            case "remove_key": return Commands.REMOVE_KEY;
            case "clear": return Commands.CLEAR;
            case "execute_script": return Commands.EXECUTE;
            case "exit": return Commands.EXIT;
            case "remove_lower": return Commands.REMOVE_LOWER;
            case "replace_if_lower": return Commands.REPLACE_IF_LOWER;
            case "remove_lower_key": return Commands.REMOVE_LOWER_KEY;
            case "min_by_id": return Commands.MIN_BY_ID;
            case "filter_by_number_of_participants": return Commands.FILTER_NUM;
            case "filter_less_than_number_of_participans": return Commands.FILTER_LESS;
            case "ping": return Commands.PING;
            case "connect": return Commands.CONNECT;
        }
        return null;
    }
}

