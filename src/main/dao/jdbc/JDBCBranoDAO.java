package dao.jdbc;

import dao.interfaces.BranoDAO;
import model.Brano;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import static java.util.Objects.isNull;

public class JDBCBranoDAO implements BranoDAO {

    public static final String TABELLA_BRANO = "brano";
    public static final String BRANO_ID = "id";
    public static final String BRANO_TITOLO = "titolo";
    public static final String BRANO_IMMAGINE = "immagine";
    public static final String BRANO_TITOLO_ALBUM = "titolo_album";
    public static final String BRANO_DATA_PUBBLICAZIONE = "data_pubblicazione";
    public static final String BRANO_ID_GENERE = "id_genere";


    private static JDBCBranoDAO instance;

    private JDBCBranoDAO() {
    }

    public static BranoDAO getInstance() {
        if (isNull(instance))
            instance = new JDBCBranoDAO();
        return instance;
    }

    @Override
    public Boolean createBrano(Brano brano) {

        //creo la connessione come oggetto che si chiude al termine del try catch
        try (Connection connection = JDBCDaoFactory.getConnection()) {
            //scrivo la query che mi serve
            String query = "INSERT INTO ".concat(TABELLA_BRANO).concat("(")
                    .concat(BRANO_ID).concat(", ")
                    .concat(BRANO_TITOLO).concat(", ")
                    .concat(BRANO_IMMAGINE).concat(", ")
                    .concat(BRANO_TITOLO_ALBUM).concat(", ")
                    .concat(BRANO_DATA_PUBBLICAZIONE).concat(", ")
                    .concat(BRANO_ID_GENERE)
                    .concat(") VALUES(?,?,?,?,?,?);");
            //creo lo statement con la query per formattarla e rimpiazzare elementi dove serve
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, brano.getId());
            statement.setString(2, brano.getTitolo());
            statement.setString(3, brano.getImmagine());
            statement.setString(4, brano.getTitoloAlbum());
            statement.setDate(5, (Date) brano.getDataPubblicazione());
            statement.setInt(6, brano.getIdGenere());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
