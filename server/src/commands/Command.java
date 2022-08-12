package commands;

import collections.MusicBand;
import common.Commands;
import main.CollectionCreator;
import main.CollectionHolder;
import main.CommandExecutor;

import java.io.IOException;

/**
 * abstract class for all command objects
 */
public abstract class Command {
    private static final long serialVersionUID = 1L;
    private final Commands type;
    private String param;
    private MusicBand receivedBand;
    private boolean elementTaking;

    public Command(Commands type, String param) {
        this.type = type;
        this.elementTaking = this.type.isElementTaking();
        this.param = param;     // can be null
        if (elementTaking) initElement();
    }

    /**
     * procedure of command execution (when {@link #elementTaking} is false
     *
     * @param cHolder - database to operate with
     */
    public abstract void execute(CollectionHolder cHolder);

    /**
     * input element while you create Cmd object
     */
    private void initElement() {
        this.receivedBand = CollectionCreator.getClientBand();
        System.out.println("Command: Element initialised");
    }


    public void sendToHolder(String paramID, CollectionHolder cHolder) {
        if (this.elementTaking) {
            if (receivedBand.isCorrect()) {
                int id;
                try {
                    id = new Integer(paramID);
                } catch (NumberFormatException e) {
                    id = cHolder.getMapLength() + 1;
                }

                if (!(id > cHolder.getMapLength())) {
                    id = cHolder.getMapLength() + 1;
                }
                receivedBand.setId(id);
                cHolder.addNewGroup(receivedBand);
                System.out.println("successfully added to " + cHolder.getClass().getName());
                return;
            }
        }
        System.out.println("Impossible to add");
    }

    @Deprecated
    public void cascadeRun(CommandExecutor commandExecutor, String inputs) throws IOException {
    }

    /**
     * setter for {@link #elementTaking} ({@param elementTaking})
     */
    public void setElementTaking(boolean elementTaking) {
        this.elementTaking = elementTaking;
    }

    public boolean getExitStatus() {
        return false;
    }

    public Commands getType() {
        return type;
    }

    public String getParam() {
        return this.param != null ? this.param : "null";
    }

    public MusicBand getReceivedBand() {
        return this.receivedBand;
    }
}
