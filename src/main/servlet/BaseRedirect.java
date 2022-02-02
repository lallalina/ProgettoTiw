package servlet;

import utils.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Servlet per il redirect di base sull'index
@WebServlet("/")
public class BaseRedirect extends HttpServlet {
    private static final long serialVersionUID = 1L;

    //Reindirizzamento all'index dopo la richiesta GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getServletContext().getRequestDispatcher(Constants.INDEX.getValue()).forward(request, response);
    }

    //Reindirizzamento all'index dopo la richiesta POST
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getServletContext().getRequestDispatcher(Constants.INDEX.getValue()).forward(request, response);
    }

}