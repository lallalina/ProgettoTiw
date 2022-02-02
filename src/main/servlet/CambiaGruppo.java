package servlet;

import dao.DAOFactory;
import dao.interfaces.GenereMusicaleDAO;
import dao.interfaces.PlaylistDAO;
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
import java.util.List;

import static java.util.Objects.isNull;
import static utils.Constants.HOME;
import static utils.Constants.INDEX;

@WebServlet("/CambiaGruppo")
public class CambiaGruppo extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
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
        List<Playlist> playlists = playlistDAO.getAllFromUtente(utente);
        List<GenereMusicale> generi = genereMusicaleDAO.getAllGenereMusicale();

        Integer newValue = (Integer) session.getAttribute("initialState");
        String movement = req.getParameter("movement");

        if (movement.equals("precedente")) {
            newValue = newValue <= 0
                    ? 0
                    : newValue - 1;
        } else {
            newValue = newValue >= playlists.size() / 5
                    ? newValue
                    : newValue + 1;
        }

        session.setAttribute("initialState", newValue);
        session.setAttribute("generi", generi);
        session.setAttribute("playlists", playlists);
        session.setAttribute("utente", utente);
        session.getServletContext().getRequestDispatcher(HOME.getValue()).forward(req, resp);
    }
}
