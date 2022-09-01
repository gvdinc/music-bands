package common;

import collections.MusicBand;

import java.io.Serializable;
import java.util.Map;

public class ReplyPack implements Serializable {
    private static final long serialVersionUID = 2L;
    private Map<Integer, MusicBand> map = null;
    private String message;
    private boolean operationSucceeded = false;
    private Commands commandType;

    public ReplyPack(Commands commandType, boolean operationSucceeded, Map<Integer, MusicBand> map){
        this.commandType = commandType;
        this.map = map;
        this.operationSucceeded = operationSucceeded;
    }

    public ReplyPack(Commands commandType, boolean operationSucceeded){
        this.commandType = commandType;
        this.operationSucceeded = operationSucceeded;
    }

    public ReplyPack(Commands commandType, boolean operationSucceeded, String message){
        this.commandType = commandType;
        this.operationSucceeded = operationSucceeded;
        this.message = message;
    }

    public Map<Integer, MusicBand> getMap() {
        return map;
    }

    public void setMap(Map<Integer, MusicBand> map) {
        this.map = map;
    }

    public boolean isOperationSucceeded() {
        return operationSucceeded;
    }

    public void setOperationSucceeded(boolean operationSucceeded) {
        this.operationSucceeded = operationSucceeded;
    }

    public Commands getCommandType() {
        return commandType;
    }

    public void setCommandType(Commands commandType) {
        this.commandType = commandType;
    }

    public String getMessage() {
        return message;
    }
}
