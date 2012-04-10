<%@ page isErrorPage="true" %>
<%@ page import="java.io.*" %>

<html>
    <head>
        <title>Aplicación CPUZ</title>
    </head>
    <body>
        <h1>Soporte Técnico</h1>

        <p>Se ha producido un error durante el procesamiento de la solicitud realizada.</p>

        <p>Tome nota del error: <%= exception.getMessage()%></p>

        <p>Para ver los detalles del mensaje de error, vea el código fuente de esta página.</p>
    <!--<% exception.printStackTrace(new PrintWriter(out)); %> --!>
      </body>
    </html>

