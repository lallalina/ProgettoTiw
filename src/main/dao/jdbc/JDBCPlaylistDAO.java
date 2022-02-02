package dao.jdbc;

import dao.interfaces.PlaylistDAO;
import model.Playlist;
import model.Utente;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class JDBCPlaylistDAO implements PlaylistDAO {

    public static final String TABELLA_PLAYLIST = "playlist";
    public static final String PLAYLIST_ID = "id";
    public static final String PLAYLIST_NOME = "nome";
    public static final String PLAYLIST_DATA_CREAZIONE = "data_creazione";
    public static final String PLAYLIST_ID_UTENTE = "id_utente";

    private static JDBCPlaylistDAO instance;

    private JDBCPlaylistDAO() {
    }

    public static PlaylistDAO getInstance() {
        if (isNull(instance))
            instance = new JDBCPlaylistDAO();
        return instance;
    }

    @Override
    public List<Playlist> getAllFromUtente(Utente utente) {
        List<Playlist> playlists = new ArrayList<>();

        //creo la connessione come oggetto che si chiude al termine del try catch
        try (Connection connection = JDBCDaoFactory.getConnection()) {
            //scrivo la query che mi serve
            String query = "SELECT * FROM ".concat(TABELLA_PLAYLIST)
                    .concat(" WHERE ").concat(PLAYLIST_ID_UTENTE)
                    .concat("=?;");

            //creo lo statement con la query per formattarla e rimpiazzare elementi dove serve
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, utente.getId());

            //ottengo il risultato dell'esecuzione della query
            ResultSet rs = statement.executeQuery();

            //ciclo per ogni elemento del set e reperisco tutti i dati
            while (rs.next()) {
                Playlist temp = new Playlist(
                        Integer.parseInt(rs.getString(rs.findColumn(PLAYLIST_ID))),
                        rs.getString(rs.findColumn(PLAYLIST_NOME)),
                        rs.getDate(rs.findColumn(PLAYLIST_DATA_CREAZIONE)),
                        Integer.parseInt(rs.getString(rs.findColumn(PLAYLIST_ID_UTENTE)))
                );
                playlists.add(temp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return playlists;
    }

    @Override
    public Boolean creaPlaylist(Playlist playlist) {
        //creo la connessione come oggetto che si chiude al termine del try catch
        try (Connection connection = JDBCDaoFactory.getConnection()) {
            //scrivo la query che mi serve
            String query = "INSERT INTO ".concat(TABELLA_PLAYLIST).concat("(")
                    .concat(PLAYLIST_ID).concat(", ")
                    .concat(PLAYLIST_NOME).concat(", ")
                    .concat(PLAYLIST_DATA_CREAZIONE).concat(", ")
                    .concat(PLAYLIST_ID_UTENTE)
                    .concat(") VALUES(?,?,?,?);");
            //creo lo statement con la query per formattarla e rimpiazzare elementi dove serve
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, playlist.getId());
            statement.setString(2, playlist.getNome());
            statement.setDate(3, Date.valueOf(LocalDate.now()));
            statement.setInt(4, playlist.getIdUtente());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
