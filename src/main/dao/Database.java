package dao;

//classe singletone per il DB
public class Database {

    private static Database instance;

    private Database() {
    }

    public static Database getInstance() {
        if (instance == null) instance = new Database();

        return instance;
    }
}
