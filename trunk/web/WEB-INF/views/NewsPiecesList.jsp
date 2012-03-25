<%-- 
    Document   : NewsPiecesList.jsp
    Created on : 23-dic-2009, 13:30:17
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

<fmt:setBundle basename="com.cpuz.multilang.cpuz" var="bundle" scope="page"/>



    <h3 align="center"><fmt:message key="newspieceslist" bundle="${bundle}" /></h3>

    <c:if test="${resquestScope.NewsPiecesEditSaveOkMsg!=null}">
        <label style="color: black">
            <fmt:message key="${NewsPiecesEditSaveOkMsg}"  bundle="${bundle}"/>
            <br/>
        </label>
    </c:if>

    <form id="newspieceslist_form" name="newspieceslist_form" method="post" action="NewsPiecesList" >
        <input name="runaction" type="hidden" id="runaction" value="list">
        <input name="identificador" type="hidden" id="identificador" >
        <input name="description" type="hidden" id="description" >
        <input name="recStart" type="hidden" id="recStart" value="${resquestScope.recStart}">
        <input name="recChunk" type="hidden" id="recChunk" value="${resquestScope.recChunk}">
        <input name="recCount" type="hidden" id="recCount" value="${resquestScope.recCount}">

        <%-- Recibe una lista de NewsPieces y las presenta en pantalla --%>


        <table width="90%" align="center" class="data_table">

            <tr>
                <%-- Requirement codes: E5-2 --%>
                <c:if test="${sessionScope.userCategory == 2}">
                    <th><fmt:message key="ID" bundle="${bundle}" /></th>
                    <th><fmt:message key="date" bundle="${bundle}" /></th>
                    <th><fmt:message key="status" bundle="${bundle}" /></th>
                    <th><fmt:message key="section" bundle="${bundle}" /></th>
                    <th><fmt:message key="description" bundle="${bundle}" /></th>
                    <th><fmt:message key="scope" bundle="${bundle}" /></th>
                    <th><fmt:message key="access" bundle="${bundle}" /></th>
                    <th><fmt:message key="action" bundle="${bundle}" /></th>
                    <th><fmt:message key="sel" bundle="${bundle}" /></th>
                </c:if>
            </tr>
            <!-- column data -->
            <c:set var="orderrow" value="1"  scope="page"/>
            <c:set var="recEnd" value="${resquestScope.recStart+resquestScope.recChunk-2}" />
            <c:if test="${resquestScope.recStart+resquestScope.recChunk>resquestScope.recCount}">
                <c:set var="recEnd" value="${resquestScope.recCount-1}" />
            </c:if>
            <c:if test="${resquestScope.recCount>0}">
                <c:forEach var="row"  end="${recEnd}"  begin="${resquestScope.recStart-1}" items="${resquestScope.recList}">
                    <c:choose>
                        <c:when test="${orderrow == 1}">
                            <tr class="tr_impar">
                                <c:set var="orderrow" value="0"  scope="page"/>
                            </c:when>
                            <c:when test="${orderrow == 0}">
                            <tr class="tr_par">
                                <c:set var="orderrow" value="1"  scope="page"/>
                            </c:when>
                        </c:choose>
                        <%-- Requirement codes: E5-2 --%>
                        <c:if test="${sessionScope.userCategory == 2}">
                            <td align="center"><c:out value="${row.id}"/></td>
                        </c:if>
                        <td align="center" width="12%"><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${row.datetime}"/></td>
                        <%-- Requirement codes: E5-2 --%>
                        <c:if test="${sessionScope.userCategory == 2}">
                            <td align="center"><c:out value="${row.status}"/></td>
                            <td><c:out value="${row.section}"/></td>
                        </c:if>
                        <td  onmouseover="this.style.cursor='pointer'" onclick="newspieces_issue('${row.id}')">
                                  <b><c:out value="${row.description}"/></b>
                        </td>
                            <td align="center"><c:out value="${row.scope}"/></td>
                            <td align="center"><c:out value="${row.access}"/></td>
                        <%-- Requirement codes: E5-2 --%>
                        <c:if test="${sessionScope.userCategory == 2}">
                            <td align="center"><input type="button" name="edit" id="edit" value="<fmt:message key="edit" bundle="${bundle}" />"  onclick="newspieces_edit('${row.id}')"/></td>
                            <td align="center"><input type="checkbox" name="selec1" value="${row.id}" /></td>
                            </c:if>
                    </tr>
                </c:forEach>
            </c:if>
        </table>

        <table width="90%" border="0" align="center" cellpadding="0" cellspacing="5" bordercolor="#000000">
            <tr>
                <td align="center">
                    <table>
                        <tr>
                            <td align="center">
                                <input type="submit" value="|<<" name="nav_first" onclick="orden_ejecutar('nav_first')"
                                       <c:if test="${resquestScope.recStart=='1'}">
                                           disabled
                                       </c:if>
                                       />
                                <input type="submit" value=" < " name="nav_prev" onclick="orden_ejecutar('nav_prev')"
                                       <c:if test="${resquestScope.recStart=='1'}">
                                           disabled
                                       </c:if>
                                       />

                                <input type="submit" value=" > " name="nav_next" onclick="orden_ejecutar('nav_next')"
                                       <c:if test="${resquestScope.recStart+resquestScope.recChunk>resquestScope.recCount}" >
                                           disabled
                                       </c:if>
                                       />
                                <input type="submit" value=">>|" name="nav_last" onclick="orden_ejecutar('nav_last')"
                                       <c:if test="${resquestScope.recStart+resquestScope.recChunk>resquestScope.recCount}">
                                           disabled
                                       </c:if>
                                       />
                            </td>
                        </tr>
                    </table>
                </td>
                <%-- Requirement codes: E5-2 --%>
                <c:if test="${sessionScope.userCategory == 2}">
                    <td align="center"><input type="button" name="new" id="new" value="<fmt:message key="new" bundle="${bundle}" />"  onclick="orden_ejecutar('new')"/></td>
                    <td align="center"><input type="button" name="delete" id="delete" value="<fmt:message key="delete" bundle="${bundle}" />"  onclick="if (confirm('<fmt:message key="confirmdeletequestáon" bundle="${bundle}" />')) orden_ejecutar('delete')"/></td>
                    <td align="center"><input type="button" name="duplicate" id="duplicate" value="<fmt:message key="duplicate" bundle="${bundle}" />"  onclick="if (confirm('<fmt:message key="confirmduplicatequestáon" bundle="${bundle}" />')) orden_ejecutar('duplicate')"/></td>
                    </c:if>
                <td align="center"><input type="button" name="selec" id="selec" value="<fmt:message key="searchselect" bundle="${bundle}" />"  onclick="orden_ejecutar('selec')"/></td>
            </tr>
        </table>

    </form>

    <script type="text/javascript">
        function orden_ejecutar(accion) {
            window.document.newspieceslist_form.runaction.value = accion;
            window.document.newspieceslist_form.submit();
            return 0;
        }
        function newspieces_details(id,description) {
            window.document.newspieceslist_form.identificador.value = id;
            window.document.newspieceslist_form.description.value = description;
            orden_ejecutar('details');
            return 0;
        }
        function newspieces_edit(id,description) {
            window.document.newspieceslist_form.identificador.value = id;
            window.document.newspieceslist_form.description.value = description;
            orden_ejecutar('edit');
            return 0;
        }
        function newspieces_issue(id,description) {
            window.document.newspieceslist_form.identificador.value = id;
            window.document.newspieceslist_form.description.value = description;
            orden_ejecutar('issue');
            return 0;
        }
    </script>



