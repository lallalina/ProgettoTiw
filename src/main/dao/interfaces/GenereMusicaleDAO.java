package dao.interfaces;

import model.GenereMusicale;

import java.util.List;

public interface GenereMusicaleDAO {
    List<GenereMusicale> getAllGenereMusicale();

    GenereMusicale getGenereFromNome(String nome);
}
