package servlet;

import dao.DAOFactory;
import dao.interfaces.GenereMusicaleDAO;
import dao.interfaces.PlaylistDAO;
import dao.interfaces.UtenteDAO;
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

//Servlet utilizzata per la Login
@WebServlet("/Login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    //le richieste GET reindirizzano all'index per il login
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().getServletContext().getRequestDispatcher(INDEX.getValue()).forward(request, response);
    }

    //le richieste POST processano la richiesta
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //come prima cosa invalidano la sessione per ripulire
        HttpSession session = request.getSession(false);
        if (!isNull(session)) {
            session.invalidate();
            session = request.getSession();
        }
        GenereMusicaleDAO genereMusicaleDAO = DAOFactory.getDAOFactory().getGenereMusicaleDAO();
        PlaylistDAO playlistDAO = DAOFactory.getDAOFactory().getPlaylistDAO();

        //creano un utente null e un percorso per il redirect
        Utente utente = null;
        String redirect = HOME.getValue();

        //ottengo username e password dalla richiesta
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //se sono null o vuoti reindirizzo alla pagina di login e scrivo che mancano parametri
        if (isNull(username) || username.isEmpty() ||
                isNull(password) || password.isEmpty()) {
            session.setAttribute("errore", "Missing parameters!");
            redirect = INDEX.getValue();
        } else {
            //altrimenti provo ad ottenere l'utente
            Boolean result = checkLogin(username, password);

            //se esiste prendo l'utente altrimenti scrivo che l'utente non esiste
            if (result) {
                utente = DAOFactory.getDAOFactory().getUtenteDAO().getUtenteByUserAndPass(username, password);

            } else {
                session.setAttribute("errore", "Credenziali errate o utente inesistente!");
                redirect = INDEX.getValue();
            }
        }

        List<Playlist> playlists = playlistDAO.getAllFromUtente(utente);
        List<GenereMusicale> generi = genereMusicaleDAO.getAllGenereMusicale();

        //metto l'utente in sessione e invio la richeista
        session.setAttribute("initialState", 0);
        session.setAttribute("generi", generi);
        session.setAttribute("playlist", playlists);
        session.setAttribute("utente", utente);
        session.getServletContext().getRequestDispatcher(redirect).forward(request, response);
    }

    //funzione per vedere se il login è corretto
    private Boolean checkLogin(String username, String password) {
        //prendo l'istanza del DAO
        UtenteDAO utenteDAO = DAOFactory.getDAOFactory().getUtenteDAO();

        //ottengo l'utente
        Utente u = utenteDAO.getUtenteByUserAndPass(username, password);
        return !isNull(u);
    }
}