package dao.jdbc;

import dao.interfaces.UtenteDAO;
import model.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static java.util.Objects.isNull;

//JDBC DAO che contiene tutti i metodi di interazione con il DB per la tabella utente
public class JDBCUtenteDAO implements UtenteDAO {

    //Nomi della tabella e dei campi sul DB
    private static final String TABELLA_UTENTE = "utente";
    private static final String UTENTE_ID = "id";
    private static final String UTENTE_USERNAME = "username";
    private static final String UTENTE_PASSWORD = "password";

    private static JDBCUtenteDAO instance;

    private JDBCUtenteDAO() {
    }

    public static UtenteDAO getInstance() {
        if (isNull(instance))
            instance = new JDBCUtenteDAO();
        return instance;
    }

    @Override
    //la funzione ritorna un utente, se esistente, per un username o una password
    //null in ogni altro caso
    public Utente getUtenteByUserAndPass(String username, String password) {
        Utente u = null;

        //creo la connessione come oggetto che si chiude al termine del try catch
        try (Connection connection = JDBCDaoFactory.getConnection()) {
            //scrivo la query che mi serve
            String query = "SELECT * FROM ".concat(TABELLA_UTENTE)
                    .concat(" WHERE ").concat(UTENTE_USERNAME).concat("=? AND ")
                    .concat(UTENTE_PASSWORD).concat("=?;");

            //creo lo statement con la query per formattarla e rimpiazzare elementi dove serve
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            //ottengo il risultato dell'esecuzione della query
            ResultSet rs = statement.executeQuery();

            //ciclo per ogni elemento del set e reperisco tutti i dati
            while (rs.next()) {
                u = new Utente(Integer.parseInt(rs.getString(rs.findColumn(UTENTE_ID))), rs.getString(UTENTE_USERNAME), rs.getString(UTENTE_PASSWORD));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return u;
    }

    @Override
    //la funzione ritorna un utente, se esistente, per un username
    //null in ogni altro caso
    public Utente getUtenteByUser(String username) {
        Utente u = null;

        //creo la connessione come oggetto che si chiude al termine del try catch
        try (Connection connection = JDBCDaoFactory.getConnection()) {
            //scrivo la query che mi serve
            String query = "SELECT * FROM ".concat(TABELLA_UTENTE)
                    .concat(" WHERE ").concat(UTENTE_USERNAME).concat("=?;");

            //creo lo statement con la query per formattarla e rimpiazzare elementi dove serve
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            //ottengo il risultato dell'esecuzione della query
            ResultSet rs = statement.executeQuery();

            //ciclo per ogni elemento del set e reperisco tutti i dati
            while (rs.next()) {
                u = new Utente(rs.getInt(rs.findColumn(UTENTE_ID)), rs.getString(rs.findColumn(UTENTE_USERNAME)), rs.getString(rs.findColumn(UTENTE_PASSWORD)));

                if (isNull(u.getUsername())) {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return u;
    }
}

