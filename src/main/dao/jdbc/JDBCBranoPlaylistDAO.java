package dao.jdbc;

import dao.interfaces.BranoPlaylistDAO;
import model.Brano;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static dao.jdbc.JDBCBranoDAO.*;
import static java.util.Objects.isNull;

public class JDBCBranoPlaylistDAO implements BranoPlaylistDAO {

    public static final String TABELLA_BRANO_PLAYLIST = "brano_playlist";
    public static final String BRANO_PLAYLIST_ID = "id";
    public static final String BRANO_PLAYLIST_TITOLO = "id_brano";
    public static final String BRANO_PLAYLIST_IMMAGINE = "id_playlist";


    private static JDBCBranoPlaylistDAO instance;

    private JDBCBranoPlaylistDAO() {
    }

    public static JDBCBranoPlaylistDAO getInstance() {
        if (isNull(instance))
            instance = new JDBCBranoPlaylistDAO();
        return instance;
    }

    @Override
    public List<Brano> getAllBraniForPlaylist(Integer idPlaylist) {
        List<Brano> brani = new ArrayList<>();

        try (Connection connection = JDBCDaoFactory.getConnection()) {
            String query = "SELECT * FROM ".concat(TABELLA_BRANO)
                    .concat(" WHERE ").concat(BRANO_ID).concat(" IN (")
                    .concat("SELECT ").concat(BRANO_ID).concat(" FROM ")
                    .concat(TABELLA_BRANO_PLAYLIST).concat(" WHERE ")
                    .concat(BRANO_PLAYLIST_IMMAGINE).concat("=?")
                    .concat(");");
            //creo lo statement con la query per formattarla e rimpiazzare elementi dove serve
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idPlaylist);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Brano temp = new Brano(
                        rs.getString(rs.findColumn(BRANO_TITOLO)),
                        rs.getString(rs.findColumn(BRANO_IMMAGINE)),
                        rs.getString(rs.findColumn(BRANO_TITOLO_ALBUM)),
                        Integer.parseInt(rs.getString(rs.findColumn(BRANO_ID_GENERE))),
                        rs.getDate(rs.findColumn(BRANO_DATA_PUBBLICAZIONE))
                );
                brani.add(temp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return brani;
    }
}
