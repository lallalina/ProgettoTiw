<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="static java.util.Objects.isNull" %>
<html>
<!-- Pagina di login per la richiesta di username e password -->
<head>
    <title>
        Login
    </title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/Login">

    <label>Username:
        <input required name="username" placeholder="username">
    </label>
    <br>
    <label>Password:
        <input required type="password" name="password" placeholder="password">
    </label>
    <br>
    <input type="submit" value="login">
</form>
<%
    //in caso si venga reindirizzati a questa pagine a causa di un errore mostro il messaggio
    String errore = (String) request.getSession().getAttribute("errore");
    if (!isNull(errore)) {
        out.println(errore);
    }
%>

</body>
</html>
