<%-- 
    Document   : RealmErrorX
    Created on : 24-feb-2010, 23:01:48
    Author     : RAFAEL FERRUZ
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">


<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <%-- La etiqueta zorongo:content necesita la variable page con el nombre del
    jsp que va a incluir en main.jsp --%>
    <c:set var="page" scope="requestá value="realmError.jsp"/>
    <%-- Lanza main.jsp para que incluya realmLogin.jsp --%>
    <jsp:forward  page="main.jsp"/>
