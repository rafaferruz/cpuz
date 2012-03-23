<%-- 
    Document   : Index
    Created on : 28-jul-2010
    Author     : RAFAEL FERRUZ
--%>

<%@page buffer="none"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setBundle basename="com.cpuz.multilang.cpuz" var="bundle" scope="page"/>


<table width="100%">
    <tr>
        <td >
            <%@ include file="jspf/whatsNew.jspf" %>
        </td>
    </tr>

    <tr>
        <td valign="top">

            <jsp:include page="newsPresentation.jsp" />

        </td>
    </tr>
</table>
