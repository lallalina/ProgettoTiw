package model;

import java.util.Date;

public class Playlist {

    private Integer id;
    private String nome;
    private Date dataCreazione;
    private Integer idUtente;

    public Playlist() {
    }

    public Playlist(String nome, Date dataCreazione) {
        this.nome = nome;
        this.dataCreazione = dataCreazione;
    }

    public Playlist(Integer id, String nome, Date dataCreazione, Integer idUtente) {
        this.id = id;
        this.nome = nome;
        this.dataCreazione = dataCreazione;
        this.idUtente = idUtente;
    }

    public Playlist(String nome, Date dataCreazione, Integer idUtente) {
        this.nome = nome;
        this.dataCreazione = dataCreazione;
        this.idUtente = idUtente;
    }

    public Integer getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }
}
