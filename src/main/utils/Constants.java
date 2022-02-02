package utils;

public enum Constants {
    INDEX("/WEB-INF/jsp/index.jsp"),
    HOME("/WEB-INF/jsp/home.jsp"),
    PLAYLIST("/WEB-INF/jsp/playlist.jsp"),

    ;

    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
