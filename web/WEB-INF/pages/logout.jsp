<%-- 
    Document   : Logout
    Created on : 11-feb-2009, 22:39:10
    Author     : RAFAEL FERRUZ
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page buffer="none"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="com.cpuz.multilang.cpuz" var="bundle" scope="page"/>


<h3 align="center"><fmt:message key="userLogin" bundle="${bundle}" /></h3>

<h4>
    <fmt:message key="closedSessionByUser" bundle="${bundle}" />
</h4>
<br />
<br />
<h4>
    <fmt:message key="useOption" bundle="${bundle}" />
</h4>


