package model;

public class GenereMusicale {

    private Integer id;
    private String genere;

    public GenereMusicale() {
    }

    public GenereMusicale(String genere) {
        this.genere = genere;
    }

    public GenereMusicale(Integer id, String genere) {
        this.id = id;
        this.genere = genere;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }
}
