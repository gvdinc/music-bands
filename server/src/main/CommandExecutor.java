package main;

import commands.*;
import commands.input.CClear;
import commands.input.CInsert;
import commands.input.CSave;
import commands.input.CUpdate;
import commands.output.*;
import common.Command;
import serverUDP.Connector;
import serverUDP.DualStream;
import serverUDP.Preparator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;


/**
 * api is responsible for cooperating of {@link #keyboardReader} and database({@link #cHolder})
 * it also provides functional of Comand initialisation and execution
 *
 * @author Grebenkin Vadim
 */
public class CommandExecutor {
    private final KeyboardReader keyboardReader = new KeyboardReader();
    /**
     * Stops the cycle of keyboardReader-database cooperating when true
     */
    private boolean exitStatus = false;

    /**
     * an object of database to operate with
     */
    private final CollectionHolder cHolder;


    /**
     * Constructor
     *
     * @param cHolder - the {@link CollectionHolder} database to operate with
     */
    public CommandExecutor(CollectionHolder cHolder) {
        this.cHolder = cHolder;
        // ! will it be a copy?
    }


    /**
     * Getting {@link #keyboardReader} input
     *
     * @return The String line keyboardReader inputted in console
     * if input is empty returns String "null"
     */
    public String getLine() {
        String line = this.keyboardReader.getInput();
        if (line.isEmpty()) {
            return "null";
        }
        return line;
    }

    /**
     * Getting keyboardReader input with message
     *
     * @param message - the message printed in console before {@link #keyboardReader} input
     * @return The String line {@link #keyboardReader} inputted in console
     * if input is empty returns String "null"
     */
    public String getLine(String message) {
        System.out.println(message);
        String line = this.keyboardReader.getInput();
        if (line.isEmpty()) {
            return "null";
        }
        return line;
    }

    /**
     * Getter for current exitStatus
     *
     * @return {@link #exitStatus}
     */
    public boolean getExitStatus() {
        return this.exitStatus;
    }


    public void runCommand(Command cmmd) {
        String input = cmmd.getType().getCommandName() + " " + cmmd.getArgs();
        String[] inputs = input.trim().split(" ", 2);
        Comand cmd = this.initializeCommand(inputs[0]);
        try {
            if (!cmd.isCascadeInput()) {
                this.exitStatus = cmd.getExitStatus();
                if (this.exitStatus) return;
                if (inputs.length >= 2) {
                    cmd.execute(inputs[1]);
                } else {
                    cmd.execute(" ");
                }
            } else {
                if (inputs.length > 1) {
                    cmd.cascadeRun(this, inputs[1]);
                } else {
                    try {
                        cmd.cascadeRun(this, "");
                    } catch (IOException e) {
                        System.out.println("got incorrect command (" + inputs[0] + "). \nno additional parameters ");
                        //e.printStackTrace();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("I have failed you, my lord.");
        }
    }

    /**
     * Executes command (without {@link #keyboardReader}-input request)
     *
     * @param input - name of command with metadata
     */
    public void runCommand(String input) {
        String[] inputs = input.trim().split(" ", 2);
        Comand cmd = this.initializeCommand(inputs[0]);
        try {
            if (!cmd.isCascadeInput()) {
                this.exitStatus = cmd.getExitStatus();
                if (this.exitStatus) return;
                if (inputs.length >= 2) {
                    cmd.execute(inputs[1]);
                } else {
                    cmd.execute(" ");
                }
            } else {
                if (inputs.length > 1) {
                    cmd.cascadeRun(this, inputs[1]);
                } else {
                    try {
                        cmd.cascadeRun(this, "");
                    } catch (IOException e) {
                        System.out.println("got incorrect command (" + inputs[0] + "). \nno additional parameters ");
                        //e.printStackTrace();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("I have failed you, my lord.");
        }
    }

    /**
     * Executes command {@link #runCommand(String)} (but with pre-{@link #keyboardReader}-input request)
     *
     * @param cmd
     * @param preparator
     */
    public void runCommand(Command cmd, Preparator preparator) throws FileNotFoundException {
        PrintStream st = new PrintStream(new FileOutputStream("output.txt"));
        PrintStream dual = new DualStream(System.out, st);
        System.setErr(dual);
        System.setOut(dual);

        String input = cmd.getType().getCommandName() + " " + cmd.getArgs();
        this.runCommand(input);
        st.flush();
        st.close();
    }

    /**
     * Analyse the current command and creates its object
     *
     * @param gInput - {@link #keyboardReader} input (from {@link java.io.BufferedInputStream})
     * @return A new object of requested command (ready to execute)
     */
    private Comand initializeCommand(String gInput) {
        Comand cmd;
        gInput = gInput.toLowerCase(Locale.ROOT);
        switch (gInput) {
            case "help":
                cmd = new CHelp(this.cHolder);
                break;
            case "info":
                cmd = new CInfo(this.cHolder);
                break;
            case "show":
                cmd = new CShow(this.cHolder);
                break;
            case "insert":
                cmd = new CInsert(this.cHolder);
                cmd.setCascadeInput(true);
                break;
            case "update":
                cmd = new CUpdate(this.cHolder);
                cmd.setCascadeInput(true);
                break;
            case "remove_key":
                cmd = new CRemoveKey(this.cHolder);
                break;
            case "clear":
                cmd = new CClear(this.cHolder);
                break;
            case "save":
                cmd = new CSave(this.cHolder);
                break;
            case "execute_script":
                cmd = new CScript(this.cHolder);
                cmd.setCascadeInput(true);
                break;
            case "exit":
                cmd = new CExit(this.cHolder);
                break;
            case "remove_lower":
                cmd = new CRemoveLower(this.cHolder);
                cmd.setCascadeInput(true);
                break;
            case "replace_if_lowe":
                cmd = new CReplaceIfLower(this.cHolder);
                cmd.setCascadeInput(true);
                break;
            case "remove_lower_key":
                cmd = new CRemoveLowerKey(this.cHolder);
                break;
            case "min_by_id":
                cmd = new CMin(this.cHolder);
                break;
            case "filter_by_number_of_participants":
                cmd = new CFilterByNum(this.cHolder);
                break;
            case "filter_less_than_number_of_participants":
                cmd = new CFilterLess(this.cHolder);
                break;
            default:
                System.out.println("got incorrect command (" + gInput + "). \nUse \"help\" to get the list of app.commands ");
                cmd = new incorrectC(this.cHolder);
        }
        return cmd;
    }

    public void runCascadeCommand(Command cmd, Preparator preparator, Connector connector) {
        connector.sendMessage("success");
    }
}
