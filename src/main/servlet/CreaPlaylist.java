package servlet;

import dao.DAOFactory;
import dao.interfaces.BranoPlaylistDAO;
import dao.interfaces.GenereMusicaleDAO;
import dao.interfaces.PlaylistDAO;
import model.Brano;
import model.GenereMusicale;
import model.Playlist;
import model.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;
import static utils.Constants.*;

@WebServlet("/Playlist")
public class CreaPlaylist extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("errore", null);
        Utente utente = (Utente) session.getAttribute("utente");

        if (isNull(utente)) {
            session.setAttribute("errore", "Missing utente in session!");
            session.getServletContext().getRequestDispatcher(INDEX.getValue()).forward(req, resp);
            return;
        }

        BranoPlaylistDAO branoPlaylistDAO = DAOFactory.getDAOFactory().getBranoPlaylistDAO();

        Integer idPlaylist = (Integer) req.getAttribute("id");

        if (idPlaylist < 0) {
            session.setAttribute("errore", "Invalid value...");
            session.getServletContext().getRequestDispatcher(INDEX.getValue()).forward(req, resp);
            return;
        }

        List<Brano> brani = branoPlaylistDAO.getAllBraniForPlaylist(idPlaylist);

        session.setAttribute("brani", brani);
        session.setAttribute("utente", utente);
        session.getServletContext().getRequestDispatcher(PLAYLIST.getValue()).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("errore", null);
        Utente utente = (Utente) session.getAttribute("utente");

        if (isNull(utente)) {
            session.setAttribute("errore", "Missing utente in session!");
            session.getServletContext().getRequestDispatcher(INDEX.getValue()).forward(req, resp);
            return;
        }

        GenereMusicaleDAO genereMusicaleDAO = DAOFactory.getDAOFactory().getGenereMusicaleDAO();
        PlaylistDAO playlistDAO = DAOFactory.getDAOFactory().getPlaylistDAO();
        String nome = req.getParameter("nome");


        if (isNull(nome) || nome.isEmpty()) {
            session.setAttribute("errore", "Invalid data");
            session.getServletContext().getRequestDispatcher(HOME.getValue()).forward(req, resp);
            return;
        }

        Playlist playlist = new Playlist(nome, Date.from(Instant.now()), utente.getId());

        playlistDAO.creaPlaylist(playlist);
        List<Playlist> playlists = playlistDAO.getAllFromUtente(utente);
        List<GenereMusicale> generi = genereMusicaleDAO.getAllGenereMusicale();

        session.setAttribute("generi", generi);
        session.setAttribute("playlists", playlists);
        session.setAttribute("utente", utente);
        session.getServletContext().getRequestDispatcher(HOME.getValue()).forward(req, resp);
    }

}
