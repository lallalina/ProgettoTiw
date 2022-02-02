package dao;

import dao.interfaces.*;
import dao.jdbc.JDBCDaoFactory;

//DAOFactory per il retrieve delle varia istanze dei DAO
public abstract class DAOFactory {
    public static DAOFactory getDAOFactory() {
        return new JDBCDaoFactory();
    }

    public abstract UtenteDAO getUtenteDAO();

    public abstract PlaylistDAO getPlaylistDAO();

    public abstract GenereMusicaleDAO getGenereMusicaleDAO();

    public abstract BranoDAO getBranoDAO();

    public abstract BranoPlaylistDAO getBranoPlaylistDAO();
}