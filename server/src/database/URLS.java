package database;

public enum URLS {
    BANDS("\"musicBands\".bands"),
    USERS("\"musicBands\".users");

    final private String pass;

    URLS(String URLPass){
        this.pass = URLPass;
    }
    public String getPass() {
        return this.pass;
    }
}
