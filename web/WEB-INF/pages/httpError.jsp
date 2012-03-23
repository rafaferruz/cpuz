<%-- 
    Document   : HttpError
    Created on : 24-feb-2010, 14:08:43
    Author     : RAFAEL FERRUZ
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="com.cpuz.multilang.cpuz" var="bundle" scope="page"/>


<h1 align="center"><fmt:message key="TechnicalSupport" bundle="${bundle}" /></h1>

<p><fmt:message key="errorThrown" bundle="${bundle}" /></p>

<br/>
<br/>
<p><fmt:message key="noteError" bundle="${bundle}" />:</p>

<br/>
<br/>

<h3 align="center"><fmt:message key="errorNoPageNoPermis" bundle="${bundle}" /></h3>

<br/>
<br/>
