<%@ page isErrorPage="true" %>
<%@ page import="java.io.*" %>

<html>
    <head>
        <title>Aplicaci�n CPUZ</title>
    </head>
    <body>
        <h1>Soporte T�cnico</h1>

        <p>Se ha producido un error durante el procesamiento de la solicitud realizada.</p>

        <p>Tome nota del error: <%= exception.getMessage()%></p>

        <p>Para ver los detalles del mensaje de error, vea el c�digo fuente de est� p�gina.</p>
    <!--<% exception.printStackTrace(new PrintWriter(out));%>--!>
      </body>
    </html>

