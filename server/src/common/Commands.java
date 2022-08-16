package common;

public enum Commands {

    /**
     * Перечислены команды, которые имеют реализацию на сервере.
     */

    INFO(   "info", false),
    HELP(   "help", false),
    SHOW(   "show", false),
    INSERT( "insert", true),
    UPDATE( "update", true),
    REMOVE_KEY("remove_key", true),
    CLEAR("clear", false),
    EXECUTE("   execute_script", false),
    EXIT("exit", false),
    REMOVE_LOWER("remove_lower", true),
    REPLACE_IF_LOWER("replace_if_lower", true),
    REMOVE_LOWER_KEY("remove_lower_key", false),
    MIN_BY_ID("min_by_id", false),
    FILTER_NUM("filter_by_number_of_participants", false),
    FILTER_LESS("filter_less_than_number_of_participans", false),
    PING("ping", false),
    SAVE("save", false),
    CONNECT("connect", false); // special command to connect the server

    /**
     * commandName - название команды в системе
     * isHasArgs - логическая переменная, которая характеризует команду на наличие аругментов
     */
    final private String commandName;
    final private boolean elementTaking;

    Commands(String commandName, boolean elementTanking) {
        this.commandName = commandName;
        this.elementTaking = elementTanking;
    }

    public String getCommandName() {
        return commandName;
    }

    public boolean isElementTaking() {
        return elementTaking;
    }
}
