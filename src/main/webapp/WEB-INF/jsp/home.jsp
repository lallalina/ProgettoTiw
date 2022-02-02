<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.GenereMusicale" %>
<%@ page import="static java.util.Objects.isNull" %>
<%@ page import="model.Playlist" %>
<%@ page import="model.Utente" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.*" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<%
    Integer initialState = (Integer) request.getSession().getAttribute("initialState");
    Utente u = (Utente) request.getSession().getAttribute("utente");
    if (isNull(u)) {
        out.println("NO SESSIONE: " + request.getSession().getId());
    }
    List<Playlist> playlists = (ArrayList<Playlist>) request.getSession().getAttribute("playlists");
    if (Objects.isNull(playlists)) {
        out.println("Error retrieving playlists...");
    }
    List<GenereMusicale> generi = (ArrayList<GenereMusicale>) request.getSession().getAttribute("generi");
    if (Objects.isNull(generi)) {
        out.println("Error retrieving generi...");
    }

    Map<Integer, List<Playlist>> playlistPages = new HashMap<>();
    for (int i = 0; i < playlistPages.size() / 5; i++) {
        playlistPages.put(i, new ArrayList<Playlist>());
    }

    if (!(Objects.isNull(playlists) || playlists.isEmpty())) {
        for (int i = 0; i < playlists.size(); i++) {
            playlistPages.get(i / 5).add(playlists.get(i));
        }
    }

%>

<h1>Home page</h1>
<div>
    <div>
        <h3>Carica un nuovo brano</h3>

        <form action="${pageContext.request.contextPath}/RichiediBrano" method="POST">
            <input type="hidden" name="utente" value=<%=((Utente)request.getSession().getAttribute("utente"))%>>
            Titolo: <input type="text" name="titolo">
            <br/>
            Immagine: <input type="file" name="immagine"/>
            <br/>
            Titolo dell'album: <input type="file" name="titoloAlbum"/>
            <br/>
            <select name="genere">
                <%
                    for (GenereMusicale genere : generi) {
                %>
                <option>genere.getGenere()</option>
                <%
                    }
                %>
            </select>
            <br/>
            <input type="submit" value="Submit"/>
        </form>

    </div>

    <div>
        <form method="POST" action="${pageContext.request.contextPath}/CreaPlaylist">
            <input type="hidden" name="utente" value=<%=((Utente)request.getSession().getAttribute("utente"))%>>
            <input type="text" name="nome">
            <input type="submit" value="crea">
        </form>
    </div>

    <%-- mostro la playlist --%>
    <div>
        <h3>Le tue playlist</h3>
        <%
            if (playlists.isEmpty()) {
                out.println("Non ci sono playlist disponibili!");
            } else {
        %>
        <form method="GET" action="${pageContext.request.contextPath}/CambiaGruppo">
            <%
                if (initialState > 0) {
            %>
            <input type="submit" name="movement" value="precedente">
            <%
                }
            %>
            <%
                if (initialState < playlistPages.size()) {
            %>
            <input type="submit" name="movement" value="successivo">
            <%
                }
            %>
            <table>
                <tr>
                    <td>Id</td>
                    <td>Nome</td>
                </tr>
                <%
                        List<Playlist> playlistGroup = playlistPages.get(initialState);
                        for (Playlist p : playlistGroup) {
                            try {
                                out.println("<tr>" +
                                        "<input type=\"hidden\" name=\"id\" value=" + p.getId() + ">" +
                                        "<td>" + p.getId() + "</td>" +
                                        "<td>" + p.getNome() + "</td>" +
                                        "</tr>");
                            } catch (IOException ignored) {
                            }
                        }
                    }
                %>
            </table>
        </form>
        <%
            }
        %>
    </div>
</div>


<p>
    <%
        //in caso si venga reindirizzati a questa pagine a causa di un errore mostro il messaggio
        String
        errore
        =
        (
        String
        )
        request
        .
        getSession
        (
        )
        .
        getAttribute
        (
        "errore"
        )
        ;
        if
        (
        !
        isNull
        (
        errore
        )
        )
        out
        .
        println
        (
        errore
        )
        ;
    %>
</p>

<form method="post" action="${pageContext.request.contextPath}/Logout">
    <input type="submit" value="logout">
</form>
</body>
</html>
