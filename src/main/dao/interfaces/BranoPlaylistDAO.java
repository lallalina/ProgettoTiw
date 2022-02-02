package dao.interfaces;

import model.Brano;

import java.util.List;

public interface BranoPlaylistDAO {
    List<Brano> getAllBraniForPlaylist(Integer idPlaylist);
}
