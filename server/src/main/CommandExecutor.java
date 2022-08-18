package main;

import commands.Command;
import commands.input.CClear;
import commands.input.CInsert;
import commands.input.CSave;
import commands.input.CUpdate;
import commands.others.*;
import commands.output.*;
import commands.server.CConnect;
import commands.server.CPing;
import common.CTransitPack;
import common.Commands;
import serverUDP.DualStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Locale;


/**
 * api is responsible for cooperating of user and database({@link #cHolder})
 * it also provides functional of Command initialisation and execution
 *
 * @author Grebenkin Vadim
 */
public class CommandExecutor {
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
     * Getter for current exitStatus
     *
     * @return {@link #exitStatus}
     */
    public boolean getExitStatus() {
        return this.exitStatus;
    }


//    public void runCommand(commands.Command cmmd) {
//        String input = cmmd.getType().getCommandName() + " " + cmmd.getArgs();
//        String[] inputs = input.trim().split(" ", 2);
//        Command cmd = this.initializeCommand(inputs[0]);
//        try {
//            if (!cmd.getType().isElementTaking()) {
//                this.exitStatus = cmd.getExitStatus();
//                if (this.exitStatus) return;
//                if (inputs.length >= 2) {
//                    cmd.execute(inputs[1]);
//                } else {
//                    cmd.execute(" ");
//                }
//            } else {
//                if (inputs.length > 1) {
//                    cmd.cascadeRun(this, inputs[1]);
//                } else {
//                    try {
//                        cmd.cascadeRun(this, "");
//                    } catch (IOException e) {
//                        System.out.println("got incorrect command (" + inputs[0] + "). \nno additional parameters ");
//                        //e.printStackTrace();
//                    }
//
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("I have failed you, my lord.");
//        }
//    }

    /**
     * Executes command (without -input request)
     *
     * @param input - name of command with metadata
     */
    public void runCommand(String input) {
        Command cmd = this.initializeCommand(input);
        this.exitStatus = cmd.getExitStatus();
        if (this.exitStatus) return;


        try {
            cmd.execute(this.cHolder);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("I have failed you, my lord.");
        }
    }

    /**
     * Executes command {@link #runCommand(String)} (but with input request)
     *
     * @param cmd
     */
    public void runCommand(Command cmd) throws FileNotFoundException {
        PrintStream st = new PrintStream(new FileOutputStream("output.txt"));
        setDualStream(st);
        boolean res = cmd.execute(this.cHolder);
        if (!res) { System.out.println("/FAILED"); }
        endStream(st);
    }

    /**
     * Analyse the current command and creates its object
     *
     * @param input -  input (from {@link java.io.BufferedInputStream})
     * @return A new object of requested command (ready to execute)
     */
    private Command initializeCommand(String input) {
        String[] inputs = input.trim().split(" ", 2);
        String type = inputs[0].toLowerCase(Locale.ROOT).trim();
        String param = (inputs.length > 1) ? inputs[1].toLowerCase(Locale.ROOT).trim() : " ";
        Command cmd;
        switch (type) {
            case "help":
                cmd = new CHelp(Commands.HELP, param);
                break;
            case "info":
                cmd = new CInfo(Commands.INFO, param);
                break;
            case "show":
                cmd = new CShow(Commands.SHOW, param);
                break;
            case "insert":
                cmd = new CInsert(Commands.INSERT, param);
                //cmd.setElementTaking(true);
                break;
            case "update":
                cmd = new CUpdate(Commands.UPDATE, param);
                //cmd.setElementTaking(true);
                break;
            case "remove_key":
                cmd = new CRemoveKey(Commands.REMOVE_KEY, param);
                break;
            case "clear":
                cmd = new CClear(Commands.CLEAR, param);
                break;
            case "save":
                cmd = new CSave(Commands.SAVE, param);
                break;
            case "execute_script":
                cmd = new CScript(Commands.EXECUTE, param);
                //cmd.setElementTaking(true);
                break;
            case "exit":
                cmd = new CExit(Commands.EXIT, param);
                break;
            case "remove_lower":
                cmd = new CRemoveLower(Commands.REMOVE_LOWER, param);
                //cmd.setElementTaking(true);
                break;
            case "replace_if_lowe":
                cmd = new CReplaceIfLower(Commands.REPLACE_IF_LOWER, param);
                //cmd.setElementTaking(true);
                break;
            case "remove_lower_key":
                cmd = new CRemoveLowerKey(Commands.REMOVE_LOWER_KEY, param);
                break;
            case "min_by_id":
                cmd = new CMin(Commands.MIN_BY_ID, param);
                break;
            case "filter_by_number_of_participants":
                cmd = new CFilterByNum(Commands.FILTER_NUM, param);
                break;
            case "filter_less_than_number_of_participants":
                cmd = new CFilterLess(Commands.FILTER_LESS, param);
                break;
            default:
                System.out.println("got incorrect command (" + input + "). \nUse \"help\" to get the list of app.commands ");
                cmd = new incorrectC(Commands.PING, param);
        }
        return cmd;
    }

    public static Command unpackTransitPack(CTransitPack transitPack){
        Command cmd;
        switch (transitPack.getType()) {
            case HELP:
                cmd = new CHelp(transitPack);
                break;
            case INFO:
                cmd = new CInfo(transitPack);
                break;
            case SHOW:
                cmd = new CShow(transitPack);
                break;
            case INSERT:
                cmd = new CInsert(transitPack);
                //cmd.setElementTaking(true);
                break;
            case UPDATE:
                cmd = new CUpdate(transitPack);
                //cmd.setElementTaking(true);
                break;
            case REMOVE_KEY:
                cmd = new CRemoveKey(transitPack);
                break;
            case CLEAR:
                cmd = new CClear(transitPack);
                break;
            case PING:
                cmd = new CPing(transitPack);
                break;
            case SAVE:
                cmd = new CSave(transitPack);
                break;
            case EXECUTE:
                cmd = new CScript(transitPack);
                //cmd.setElementTaking(true);
                break;
            case EXIT:
                cmd = new CExit(transitPack);
                break;
            case REMOVE_LOWER:
                cmd = new CRemoveLower(transitPack);
                //cmd.setElementTaking(true);
                break;
            case REPLACE_IF_LOWER:
                cmd = new CReplaceIfLower(transitPack);
                //cmd.setElementTaking(true);
                break;
            case REMOVE_LOWER_KEY:
                cmd = new CRemoveLowerKey(transitPack);
                break;
            case MIN_BY_ID:
                cmd = new CMin(transitPack);
                break;
            case FILTER_NUM:
                cmd = new CFilterByNum(transitPack);
                break;
            case FILTER_LESS:
                cmd = new CFilterLess(transitPack);
                break;
            case CONNECT:
                cmd = new CConnect(transitPack);
                break;
            default:
                System.out.println("got incorrect Transit Pack ");
                return null;
        }
        return cmd;
    }

    public static void setDualStream(PrintStream st){
        PrintStream dual = new DualStream(System.out, st);
        System.setErr(dual);
        System.setOut(dual);
    }

    public static void endStream(PrintStream st){
        st.flush();
        st.close();
        System.setErr(System.err);
        System.setOut(System.out);
    }
}
