<%-- 
    Document   : ComentariosContentList
    Created on : 06-feb-2010, 19:39:03
    Author     : RAFAEL FERRUZ
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%--
<%@page errorPage="error.jsp" %>
--%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="zorongo" uri="http://www.zorongocookies.com/zorongo"%>

<fmt:setBundle basename="com.cpuz.multilang.cpuz" var="bundle" scope="page"/>

<td>

    <h3 align="center"><fmt:message key="commentlist" bundle="${bundle}" /></h3>

    <c:if test="${resquestScope.ComentariosEditSaveOkMsg!=null}">
        <label style="color: black">
            <fmt:message key="${ComentariosEditSaveOkMsg}"  bundle="${bundle}"/>
            <br/>
        </label>
    </c:if>

    <form id="comentarioslist_form" name="comentarioslist_form" method="post" action="ComentariosContentList"   class="zorongo">
        <input name="runaction" type="hidden" id="runaction" value="list">
        <input name="identificador" type="hidden" id="identificador" >
        <input name="titulo" type="hidden" id="titulo" >
        <input name="recStart" type="hidden" id="recStart" value="${resquestScope.recStart}">
        <input name="recChunk" type="hidden" id="recChunk" value="${resquestScope.recChunk}">
        <input name="recCount" type="hidden" id="recCount" value="${resquestScope.recCount}">

        <%-- Recibe una lista de Comentarios y las presenta en pantalla --%>


        <table  width="100%" border="0"  title="<fmt:message key="commentlist" bundle="${bundle}" />" cellspacing="0" cellpadding="0" bordercolor="#000000">

            <tr>
                <td>
                    <table width="100%" cellspacing="1" cellpadding="0">
                        <tr>
                            <th width="15%"><fmt:message key="ID" bundle="${bundle}" /><br/>
                            <fmt:message key="author" bundle="${bundle}" /></th>
                            <th width="85%"><fmt:message key="title" bundle="${bundle}" /> -
                            <fmt:message key="body" bundle="${bundle}" /></th>
                        </tr>
                    </table>
                </td>
            </tr>
            <!-- column data -->
            <c:set var="orderrow" value="1"  scope="page"/>
            <c:set var="recEnd" value="${resquestScope.recStart+resquestScope.recChunk-2}" />
            <c:if test="${resquestScope.recStart+resquestScope.recChunk>resquestScope.recCount}">
                <c:set var="recEnd" value="${resquestScope.recCount-1}" />
            </c:if>
            <tr>
                <td align="center">
                    <div id="listarcomentarios" class="listarcomentarios_div" >
                        <table width="100%" cellspacing="1" cellpadding="0">
                            <c:forEach var="row"  end="${recEnd}"  begin="${resquestScope.recStart-1}" items="${resquestScope.recList}">
                                <c:choose>
                                    <c:when test="${orderrow == 1}">
                                        <tr bgcolor="#FFFFFF">
                                            <c:set var="orderrow" value="0"  scope="page"/>
                                        </c:when>
                                        <c:when test="${orderrow == 0}">
                                        <tr bgcolor="#CCFFCC">
                                            <c:set var="orderrow" value="1"  scope="page"/>
                                        </c:when>
                                    </c:choose>
                                    <td width="15%" valign="top">
                                        <a name="${row.id}"></a>
                                        <c:out value="${row.id}"/>
                                        <br/>
                                        <%-- La fecha se imprime en todos los casos --%>
                                        <fmt:formatDate  pattern="dd/MM/yyyy" value="${row.fecha}"/>
                                        <c:if test="${row.publicar == 'publicarsi'}" >
                                            <br/>
                                            <c:out value="${row.nombre}"/>
                                            <br/>
                                            <c:out value="${row.parcela}"/>
                                        </c:if>
                                    </td>
                                    <%-- El T�tulo se imprime en todos los casos --%>
                                    <td width="85%" valign="top">

                                        <b><c:out value="${row.titulo}"/></b>
                                        <br/>
                                        <c:if test="${row.ficheroImagen > '' }"  >

                                            <img src="http://www.laboraldetarragona.com/ecosysw.com/ZORONGO/WEB-INF/imagenes/comentarios/<c:out value="${row.ficheroImagen}"/>" alt="${row.ficheroImagen}" width="200" height="150"  hspace="10" vspace="10" align="right" />
                                        </c:if>
                                        <zorongo:changenewline value="${row.comentario}" />
                                        <br/><br/>
                                    </td>
                                    <%-- Requirement codes: E5-2 --%>
                                </tr>
                            </c:forEach>
                        </table>

                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="5" bordercolor="#000000">
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
                            <td align="center">
                                <input type="button" name="selec" id="selec" value="<fmt:message key="searchselect" bundle="${bundle}" />"  onclick="orden_ejecutar('selec')"/>
                                <input type="button" name="titles" id="titles" value="<fmt:message key="toListTitles" bundle="${bundle}" />"  onclick="orden_ejecutar('list')"/>
                            </td>
                        </tr>
                    </table>
                </td>
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



