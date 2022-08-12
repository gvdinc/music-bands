package commands.output;

import commands.Command;
import common.Commands;
import main.CollectionHolder;

/**
 * вывести справку по доступным командам
 */
public class CHelp extends Command {


    public CHelp(Commands type, String param) {
        super(type, param);
    }

    public void execute(CollectionHolder cHolder) {
        System.out.print("help : display help on available server.commands\n" +
                "info : print information about the collection to standard output (type, initialization date, number of elements, etc.)\n" +
                "show : print to standard output all elements of the collection in string representation\n" +
                "insert null {element} : add a new element with the given key\n" +
                "update id {element} : update the value of the collection element whose id is equal to the given one\n" +
                "remove_key null : remove an element from the collection by its key\n" +
                "clear : clear the collection\n" +
                "save : save the collection to a file\n" +
                "execute_script file_name : read and execute a script from the specified file. The script contains server.commands in the same form in which they are entered by the user in interactive mode.\n" +
                "exit : exit the program (without saving to a file)\n" +
                "remove_lower {element} : remove all elements from the collection that are smaller than the given one\n" +
                "replace_if_lowe null {element} : replace value by key if new value is less than old\n" +
                "remove_lower_key null : remove from the collection all elements whose key is less than the given one\n" +
                "min_by_id : display any object from the collection whose id field value is the minimum\n" +
                "filter_by_number_of_participants numberOfParticipants : display elements whose numberOfParticipants field value is equal to the given one\n" +
                "filter_less_than_number_of_participants numberOfParticipants : display elements whose numberOfParticipants field value is less than the given value\n\n");
    }

}
