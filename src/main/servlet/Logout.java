package servlet;

import utils.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {

    private static final long serialVersionUID = 1L;

    //le richieste GET reindirizzano all'index per il login
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().getServletContext().getRequestDispatcher(Constants.INDEX.getValue()).forward(request, response);
    }

    //la funzione invalida la sessione e invia alla pagina iniziale
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        request.getSession().getServletContext().getRequestDispatcher(Constants.INDEX.getValue()).forward(request, response);
    }

}