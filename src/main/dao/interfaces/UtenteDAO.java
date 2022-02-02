package dao.interfaces;

import model.Utente;

public interface UtenteDAO {
    Utente getUtenteByUserAndPass(String username, String password);

    Utente getUtenteByUser(String username);
}
