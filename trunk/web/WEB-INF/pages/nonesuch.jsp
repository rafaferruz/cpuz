<fmt:message key="nonesuch" bundle="${bundle}"/> 

<table align="center" border="0" width="60%">
    <thead>
        <tr>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>
                <br><br><br><br>
                <c:choose>
                    <c:when test="${requestScope['nonesuch'] != null}">
                        <c:out value="${requestScope.nonesuch}"/>
                    </c:when>
                    <c:when test="${requestScope['javax.servlet.error.requestUri'] != null}">
                        <c:out value="${requestScope['javax.servlet.error.requestUri']}"/>
                    </c:when>
                    <c:otherwise>???</c:otherwise>
                </c:choose>
                <br><br>
                El recurso solicitado no se ha encontrado o se carece de permisos de usuario.
                <br><br><br><br>
            </td>
        </tr>
    </tbody>
</table>

