<%-- 
    Document   : RealmLoginX
    Created on : 24-feb-2010, 10:04:48
    Author     : RAFAEL FERRUZ
--%>

    <%-- La etiqueta zorongo:content necesita la variable page con el nombre del
    jsp que va a incluir en main.jsp --%>
    <c:set var="page" scope="request" value="realmLogin.jsp"/>
    <%-- Lanza main.jsp para que incluya realmLogin.jsp --%>
    <jsp:forward  page="main.jsp"/>

