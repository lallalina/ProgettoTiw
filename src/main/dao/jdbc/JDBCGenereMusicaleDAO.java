package dao.jdbc;

import dao.interfaces.GenereMusicaleDAO;
import model.GenereMusicale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class JDBCGenereMusicaleDAO implements GenereMusicaleDAO {

    //Nomi della tabella e dei campi sul DB
    private static final String TABELLA_GENERE = "genere";
    private static final String GENERE_ID = "id";
    private static final String GENERE_GENERE = "genere";
    private static JDBCGenereMusicaleDAO instance;


    private JDBCGenereMusicaleDAO() {
    }

    public static GenereMusicaleDAO getInstance() {
        if (isNull(instance))
            instance = new JDBCGenereMusicaleDAO();
        return instance;
    }


    @Override
    public List<GenereMusicale> getAllGenereMusicale() {
        List<GenereMusicale> generi = new ArrayList<>();

        //creo la connessione come oggetto che si chiude al termine del try catch
        try (Connection connection = JDBCDaoFactory.getConnection()) {
            //scrivo la query che mi serve
            String query = "SELECT * FROM ".concat(TABELLA_GENERE);

            //creo lo statement con la query per formattarla e rimpiazzare elementi dove serve
            PreparedStatement statement = connection.prepareStatement(query);

            //ottengo il risultato dell'esecuzione della query
            ResultSet rs = statement.executeQuery();

            //ciclo per ogni elemento del set e reperisco tutti i dati
            while (rs.next()) {
                GenereMusicale temp = new GenereMusicale(
                        Integer.parseInt(rs.getString(rs.findColumn(GENERE_ID))),
                        rs.getString(rs.findColumn(GENERE_GENERE)));
                generi.add(temp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return generi;
    }

    @Override
    public GenereMusicale getGenereFromNome(String nome) {
        GenereMusicale genereMusicale = null;

        //creo la connessione come oggetto che si chiude al termine del try catch
        try (Connection connection = JDBCDaoFactory.getConnection()) {
            //scrivo la query che mi serve
            String query = "SELECT * FROM ".concat(TABELLA_GENERE)
                    .concat(" WHERE ").concat(GENERE_GENERE)
                    .concat("=?;");

            //creo lo statement con la query per formattarla e rimpiazzare elementi dove serve
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nome);

            //ottengo il risultato dell'esecuzione della query
            ResultSet rs = statement.executeQuery();

            //ciclo per ogni elemento del set e reperisco tutti i dati
            while (rs.next()) {
                genereMusicale = new GenereMusicale(
                        Integer.parseInt(rs.getString(rs.findColumn(GENERE_ID))),
                        rs.getString(rs.findColumn(GENERE_GENERE)));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return genereMusicale;
    }
}
