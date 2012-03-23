<%-- 
    Document   : RealmError
    Created on : 24-feb-2010, 8:49:25
    Author     : RAFAEL FERRUZ
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%--
<%@page errorPage="error.jsp" %>
--%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>


<fmt:setBundle basename="com.cpuz.multilang.cpuz" var="bundle" scope="page"/>

<table align="center" width="80%">
    <tr>
        <td>
            <h3 align="center"><fmt:message key="errorUserLogin" bundle="${bundle}" /></h3>

            <br/><br/><br/><br/>
            <fmt:message key="errorUserOrPassword" bundle="${bundle}" />
            <br/><br/><br/><br/>
        </td>
    </tr>
</table>


