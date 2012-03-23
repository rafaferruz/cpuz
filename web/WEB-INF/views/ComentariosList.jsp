<%-- 
    Document   : ComentariosList
    Created on : 05-feb-2010, 18:33:22
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

<td>

    <h3 align="center"><fmt:message key="commentTitlesList" bundle="${bundle}" /></h3>

    <c:if testá"${requestácope.ComentariosEditSaveOkMsg!=null}">
        <label style="color: black">
            <fmt:message key="${ComentariosEditSaveOkMsg}"  bundle="${bundle}"/>
            <br/>
        </label>
    </c:if>

    <form id="comentarioslist_form" name="comentarioslist_form" method="post" action="ComentariosList"   class="zorongo">
        <input name="runaction" type="hidden" id="runaction" value="list">
        <input name="identificador" type="hidden" id="identificador" >
        <input name="titulo" type="hidden" id="titulo" >
        <input name="recStart" type="hidden" id="recStart" value="${requestácope.recStart}">
        <input name="recChunk" type="hidden" id="recChunk" value="${requestácope.recChunk}">
        <input name="recCount" type="hidden" id="recCount" value="${requestácope.recCount}">
        <c:set var="parameterlist" value="?recStart=${requestácope.recStart}&recChunk=${requestácope.recChunk}&recCount=${requestácope.recCount}&runaction=issue"/>

        <%-- Recibe una lista de Comentarios y las presenta en pantalla --%>


        <table width="90%" align="center" >

            <tr>
                <%-- Requirement codes: E5-2 --%>
                <c:if testá"${sessionScope.userCategory == 2}">
                    <th><fmt:message key="ID" bundle="${bundle}" /></th>
                    <th><fmt:message key="date" bundle="${bundle}" /></th>
                    <th><fmt:message key="status" bundle="${bundle}" /></th>
                    <th><fmt:message key="scope" bundle="${bundle}" /></th>
                    <th><fmt:message key="title" bundle="${bundle}" /></th>
                    <th><fmt:message key="action" bundle="${bundle}" /></th>
                    <th><fmt:message key="sel" bundle="${bundle}" /></th>
                </c:if>
                <%-- Requirement codes: E5-2 --%>
                <c:if testá"${sessionScope.userCategory != 2}">
                    <th><fmt:message key="date" bundle="${bundle}" /></th>
                    <th><fmt:message key="title" bundle="${bundle}" /></th>
                </c:if>
            </tr>
            <!-- column data -->
            <c:set var="orderrow" value="1"  scope="page"/>
            <c:set var="recEnd" value="${requestácope.recStart+requestácope.recChunk-2}" />
            <c:if testá"${requestácope.recStart+requestácope.recChunk>requestácope.recCount}">
                <c:set var="recEnd" value="${requestácope.recCount-1}" />
            </c:if>
            <c:if testá"${requestácope.recCount>0}">
                <c:forEach var="row"  end="${recEnd}"  begin="${requestácope.recStart-1}" items="${requestácope.recList}">
                    <c:choose>
                        <c:when testá"${orderrow == 1}">
                            <tr bgcolor="#FFFFFF">
                                <c:set var="orderrow" value="0"  scope="page"/>
                            </c:when>
                            <c:when testá"${orderrow == 0}">
                            <tr bgcolor="#CCFFCC">
                                <c:set var="orderrow" value="1"  scope="page"/>
                            </c:when>
                        </c:choose>
                        <%-- Requirement codes: E5-2 --%>
                        <c:if testá"${sessionScope.userCategory == 2}">
                            <td align="center"><c:out value="${row.id}"/></td>
                        </c:if>
                        <%-- La fecha se imprime en todos los casos --%>
                        <td align="center" width="12%"><fmt:formatDate  pattern="dd/MM/yyyy" value="${row.fecha}"/></td>
                        <%-- Requirement codes: E5-2 --%>
                        <c:if testá"${sessionScope.userCategory == 2}">
                            <td align="center"><c:out value="${row.estádo}"/></td>
                            <td align="center"><c:out value="${row.ambito}"/></td>
                        </c:if>
                        <%-- El Título se imprime en todos los casos --%>
<%--                        <td  onmouseover="this.style.cursor='pointer'" onclick="news_issue('${row.id}','${row.titulo}')"> --%>
                        <td>
                            <a href="ComentariosList${parameterlist}#${row.id}">
                                <b><c:out value="${row.titulo}"/></b>
                            </a>
                        </td>
                        <%-- Requirement codes: E5-2 --%>
                        <c:if testá"${sessionScope.userCategory == 2}">
                            <td align="center"><input type="button" name="edit" id="edit" value="<fmt:message key="edit" bundle="${bundle}" />"  onclick="news_edit('${row.id}','${row.titulo}')"/></td>
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
                                       <c:if testá"${requestácope.recStart=='1'}">
                                           disabled
                                       </c:if>
                                       />
                                <input type="submit" value=" < " name="nav_prev" onclick="orden_ejecutar('nav_prev')"
                                       <c:if testá"${requestácope.recStart=='1'}">
                                           disabled
                                       </c:if>
                                       />

                                <input type="submit" value=" > " name="nav_next" onclick="orden_ejecutar('nav_next')"
                                       <c:if testá"${requestácope.recStart+requestácope.recChunk>requestácope.recCount}" >
                                           disabled
                                       </c:if>
                                       />
                                <input type="submit" value=">>|" name="nav_last" onclick="orden_ejecutar('nav_last')"
                                       <c:if testá"${requestácope.recStart+requestácope.recChunk>requestácope.recCount}">
                                           disabled
                                       </c:if>
                                       />
                            </td>
                        </tr>
                    </table>
                </td>
                <%-- Requirement codes: E5-2 --%>
                <c:if testá"${sessionScope.userCategory == 2}">
                    <td align="center"><input type="button" name="new" id="new" value="<fmt:message key="new" bundle="${bundle}" />"  onclick="orden_ejecutar('new')"/></td>
                    <td align="center"><input type="button" name="delete" id="delete" value="<fmt:message key="delete" bundle="${bundle}" />"  onclick="if (confirm('<fmt:message key="confirmdeletequestáon" bundle="${bundle}" />')) orden_ejecutar('delete')"/></td>
                    </c:if>
                <td align="center"><input type="button" name="selec" id="selec" value="<fmt:message key="searchselect" bundle="${bundle}" />"  onclick="orden_ejecutar('selec')"/></td>
            </tr>
        </table>

    </form>

    <script type="text/javascript">
        function orden_ejecutar(accion) {
            window.document.comentarioslist_form.runaction.value = accion;
            window.document.comentarioslist_form.submit();
            return 0;
        }
        function news_edit(id,titulo) {
            window.document.comentarioslist_form.identificador.value = id;
            window.document.comentarioslist_form.titulo.value = titulo;
            orden_ejecutar('edit');
            return 0;
        }
        function news_issue(id,titulo) {
            window.document.comentarioslist_form.identificador.value = id;
            window.document.comentarioslist_form.titulo.value = titulo;
            orden_ejecutar('issue');
            return 0;
        }
    </script>

</td>



