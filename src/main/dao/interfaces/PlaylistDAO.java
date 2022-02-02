package dao.interfaces;

import model.Playlist;
import model.Utente;

import java.util.List;

public interface PlaylistDAO {
    List<Playlist> getAllFromUtente(Utente utente);

    Boolean creaPlaylist(Playlist playlist);
}
