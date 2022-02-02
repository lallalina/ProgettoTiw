package dao.jdbc;


import dao.DAOFactory;
import dao.interfaces.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

//contiene la factory dei DAO da cui reperiamo le istanze dei nostri DAO
public class JDBCDaoFactory extends DAOFactory {

    //connessione al DB
    public static Connection getConnection() throws SQLException {
        try {
            DataSource source = InitialContext.doLookup("java:comp/env/jdbc/playlist");
            return source.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public UtenteDAO getUtenteDAO() {
        return JDBCUtenteDAO.getInstance();
    }

    @Override
    public BranoDAO getBranoDAO() {
        return JDBCBranoDAO.getInstance();
    }

    @Override
    public PlaylistDAO getPlaylistDAO() {
        return JDBCPlaylistDAO.getInstance();
    }

    @Override
    public GenereMusicaleDAO getGenereMusicaleDAO() {
        return JDBCGenereMusicaleDAO.getInstance();
    }

    @Override
    public BranoPlaylistDAO getBranoPlaylistDAO() {
        return JDBCBranoPlaylistDAO.getInstance();
    }

}

