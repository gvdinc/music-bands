package database;

public enum URLS {
    BANDS("\"s335053\".bands"),
    USERS("\"s335053\".users");

    final private String pass;

    URLS(String URLPass){
        this.pass = URLPass;
    }
    public String getPass() {
        return this.pass;
    }
}
