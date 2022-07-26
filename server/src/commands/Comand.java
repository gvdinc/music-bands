package commands;

import main.CollectionHolder;
import main.CommandExecutor;
import serverUDP.Preparator;

import java.io.IOException;

/**
 * abstract class for all command objects
 */
public abstract class Comand {
    private final CollectionHolder cHolder;
    private Preparator preparator;
    /**
     * use {@link #cascadeRun(CommandExecutor, String)} instead of {@link #execute(String)} when is true
     */
    private boolean cascadeInput = false;

    /**
     * Constructor
     */
    public Comand(CollectionHolder holder) {
        this.cHolder = holder;
    }

    /**
     * procedure of command execution (when {@link #cascadeInput} is false
     *
     * @param input - parameters
     */
    public abstract void execute(String input);

    /**
     * returns boolean exit status
     *
     * @return false if command do not stop the program and true if it does
     * app.commands requires true exitStatus must override this method
     */
    public boolean getExitStatus() {
        return false;
    }

    /**
     * procedure of command execution (when {@link #cascadeInput} is true
     * provides ability to use commandExecutor to get client input while executing
     */
    public void cascadeRun(CommandExecutor commandExecutor, String params) throws IOException {
    }

    /**
     * getter for {@link #cascadeInput}
     */
    public boolean isCascadeInput() {
        return cascadeInput;
    }

    /**
     * setter for {@link #cascadeInput} ({@param cascadeInput})
     */
    public void setCascadeInput(boolean cascadeInput) {
        this.cascadeInput = cascadeInput;
    }
}
