package model;

import java.util.Date;

public class Brano {

    private Integer id;
    private String titolo;
    private String immagine;
    private String titoloAlbum;
    private Integer idGenere;
    private Date dataPubblicazione;
    private String linkCanzone;

    public Brano() {
    }

    public Brano(String titolo, String immagine, String titoloAlbum, Integer idGenere, Date dataPubblicazione) {
        this.titolo = titolo;
        this.immagine = immagine;
        this.titoloAlbum = titoloAlbum;
        this.idGenere = idGenere;
        this.dataPubblicazione = dataPubblicazione;
    }

    public Brano(Integer id, String titolo, String immagine, String titoloAlbum, Integer idGenere, Date dataPubblicazione) {
        this.id = id;
        this.titolo = titolo;
        this.immagine = immagine;
        this.titoloAlbum = titoloAlbum;
        this.idGenere = idGenere;
        this.dataPubblicazione = dataPubblicazione;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public String getTitoloAlbum() {
        return titoloAlbum;
    }

    public void setTitoloAlbum(String titoloAlbum) {
        this.titoloAlbum = titoloAlbum;
    }

    public Integer getIdGenere() {
        return idGenere;
    }

    public void setIdGenere(Integer idGenere) {
        this.idGenere = idGenere;
    }

    public Date getDataPubblicazione() {
        return dataPubblicazione;
    }

    public void setDataPubblicazione(Date dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }
    
    public String getLinkCanzone(){
        return linkCanzone;
    }
    
    public void setLinkCanzone(String linkCanzone){
        this.linkCanzone = linkCanzone;
    }
    
}
