package servlet;

import dao.DAOFactory;
import dao.interfaces.BranoDAO;
import dao.interfaces.GenereMusicaleDAO;
import dao.interfaces.PlaylistDAO;
import model.Brano;
import model.GenereMusicale;
import model.Playlist;
import model.Utente;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static utils.Constants.HOME;
import static utils.Constants.INDEX;

public class RichiediBrano extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().getServletContext().getRequestDispatcher(HOME.getValue()).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("errore", null);
        Utente utente = (Utente) session.getAttribute("utente");

        PlaylistDAO playlistDAO = DAOFactory.getDAOFactory().getPlaylistDAO();
        BranoDAO branoDAO = DAOFactory.getDAOFactory().getBranoDAO();
        GenereMusicaleDAO genereMusicaleDAO = DAOFactory.getDAOFactory().getGenereMusicaleDAO();

        String nome = req.getParameter("titolo");
        String immagine = req.getParameter("immagine");
        String nomeGenere = req.getParameter("genere");
        String titoloAlbum = req.getParameter("titoloAlbum");
        Date dataPubblicazione = Date.from(
                LocalDate.parse(req.getParameter("dataPubblicazione"))
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
        );

        GenereMusicale genere = genereMusicaleDAO.getGenereFromNome(nomeGenere);

        if (isNull(utente)) {
            session.setAttribute("errore", "Missing utente in session!");
            session.getServletContext().getRequestDispatcher(INDEX.getValue()).forward(req, resp);
            return;
        }
        if (Stream.of(nome, immagine, nomeGenere, titoloAlbum)
                .anyMatch(s -> isNull(s) || s.isEmpty())
                || dataPubblicazione.after(Date.from(Instant.now()))) {
            session.setAttribute("errore", "Missing or invalid data");
            session.getServletContext().getRequestDispatcher(HOME.getValue()).forward(req, resp);
            return;
        }

        Brano brano = new Brano(nome, immagine, titoloAlbum, genere.getId(), dataPubblicazione);

        branoDAO.createBrano(brano);
        List<Playlist> playlists = playlistDAO.getAllFromUtente(utente);
        List<GenereMusicale> generi = genereMusicaleDAO.getAllGenereMusicale();

        session.setAttribute("generi", generi);
        session.setAttribute("playlists", playlists);
        session.setAttribute("utente", utente);
        session.getServletContext().getRequestDispatcher(HOME.getValue()).forward(req, resp);
    }

}
