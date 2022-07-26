package common;

import java.io.Serializable;

public class Command implements Serializable {
    private static final long serialVersionUID = 1L;
    private Commands type;
    private String args;

    public Command(Commands type, String args){
        this.type = type;
        this.args = args;
    }

    public Commands getType() {
        return type;
    }

    public String getArgs() {
        if (this.args != null){
            return args;
        }
        else{return "null";}
    }
}
