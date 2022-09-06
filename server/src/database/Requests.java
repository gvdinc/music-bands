package database;


public enum Requests {

    DELETE_USER_BANDS{
        @Override
        public String get(String[] args){
            return "DELETE FROM "+ URLS.BANDS.getPass() +" WHERE username = '" + args[0] + "'";
        }
    },

    LOAD_DATA {
        @Override
        public String get(String[] args) {
            return "select * from " + URLS.BANDS.getPass();
        }
    },

    /** username needed in the end of the line */
    USER_HASH {
        @Override
        public String get(String[] args){return "select \"passwordHash\" from "+ URLS.USERS.getPass() + " where username = '" + args[0] + "'";}
    },

    ADD_USER {
        @Override
        public String get(String[] args){return (args != null && args.length == 2) ? "insert into "+ URLS.USERS.getPass() +" (username, \"passwordHash\") values ('"+args[0]+"', '"+args[1]+"')" : "";}
    };

    public abstract String get(String [] args);

}
