<%-- 
    Document   : newsCompositionList.jsp
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
<h3 align="center"><fmt:message key="newsCompositionlist" bundle="${bundle}" /></h3>

<c:if testá"${requestácope.NewsCompositionEditSaveOkMsg!=null}">
    <label style="color: black">
        <fmt:message key="${NewsCompositionEditSaveOkMsg}"  bundle="${bundle}"/>
        <br/>
    </label>
</c:if>

<%--        <input name="runaction" type="hidden" id="runaction" value="list">
        <input name="identificador" type="hidden" id="identificador" >
        <input name="description" type="hidden" id="description" >
        <input name="recStart" type="hidden" id="recStart" value="${requestácope.recStart}">
        <input name="recChunk" type="hidden" id="recChunk" value="${requestácope.recChunk}">
        <input name="recCount" type="hidden" id="recCount" value="${requestácope.recCount}">
--%>
<%-- Recibe una lista de NewsComposition y las presenta en pantalla --%>


<table width="90%" align="center" class="data_table">

    <tr>
        <%-- Requirement codes: E5-2 --%>
        <c:if testá"${sessionScope.userCategory == 2}">
            <th><fmt:message key="ID" bundle="${bundle}" /></th>
            <th><fmt:message key="ComponentType" bundle="${bundle}" /></th>
            <th><fmt:message key="HeaderAlt" bundle="${bundle}" /></th>
            <th><fmt:message key="HeaderStyle" bundle="${bundle}" /></th>
            <th><fmt:message key="BodyAbstract" bundle="${bundle}" /></th>
            <th><fmt:message key="Link" bundle="${bundle}" /></th>
            <th><fmt:message key="action" bundle="${bundle}" /></th>
            <th><fmt:message key="sel" bundle="${bundle}" /></th>
        </c:if>
    </tr>
    <!-- column data -->
    <c:set var="orderrow" value="1"  scope="page"/>
    <c:if testá"${requestácope.recCount>0}">
        <c:forEach var="rowComp" items="${sessionScope.recComposition}">
            <c:choose>
                <c:when testá"${orderrow == 1}">
                    <tr class="tr_impar">
                        <c:set var="orderrow" value="0"  scope="page"/>
                    </c:when>
                    <c:when testá"${orderrow == 0}">
                    <tr class="tr_par">
                        <c:set var="orderrow" value="1"  scope="page"/>
                    </c:when>
                </c:choose>
                <%-- Requirement codes: E5-2 --%>
                <c:if testá"${sessionScope.userCategory == 2}">
                    <td align="center"><c:out value="${rowComp.id}"/></td>
                    <td align="center"><c:out value="${rowComp.componentType}"/></td>
                    <td align="left"><c:out value="${rowComp.headerAlt}"/></td>
                    <td align="center"><c:out value="${rowComp.headerStyle}"/></td>
                    <c:if testá"${rowComp.componentType =='InfoBlock'}" >
                        <td align="left"><c:out value="${rowComp.bodyAbstract}"/></td>
                    </c:if>
                    <c:if testá"${rowComp.componentType =='Image'}" >
                        <td align="${rowComp.headerStyle}">
                            <img src="${applicationScope.dirHomeResources}/../CPUZ/images/${rowComp.bodyAbstract}"
                                 alt="${applicationScope.dirHomeResources}/../CPUZ/images/${rowComp.bodyAbstract}"
                                 height="90" width="120"/>
                        </td>
                    </c:if>
                    <td align="left"><c:out value="${rowComp.linkedElement}"/></td>
                </c:if>
                <%-- Requirement codes: E5-2 --%>
                <c:if testá"${sessionScope.userCategory == 2}">
                    <td align="center">
                        <input type="button" name="editComp" id="editComp" value="<fmt:message key="edit" bundle="${bundle}" />"  onclick="newsComposition_edit('${rowComp.id}')"/>
                    </td>
                    <td align="center"><input type="checkbox" name="selec1Comp" value="${rowComp.id}" /></td>
                    </c:if>
            </tr>
        </c:forEach>
    </c:if>
</table>
<input type="hidden" name="idCompositionEdit" id="idCompositionEdit" value="" />

<table width="90%" border="0" align="center" cellpadding="0" cellspacing="5" bordercolor="#000000">
    <tr>
        <td align="center">
            <table>
                <tr>
                    <td align="center">
                        <input type="submit" value="<fmt:message key="top" bundle="${bundle}" />" name="ncil_nav_top" onclick="orden_ejecutar('ncil_nav_top')"
                               />
                        <input type="submit" value="<fmt:message key="up" bundle="${bundle}" />" name="ncil_nav_up" onclick="orden_ejecutar('ncil_nav_up')"
                               />

                        <input type="submit" value="<fmt:message key="down" bundle="${bundle}" />" name="ncil_nav_down" onclick="orden_ejecutar('ncil_nav_down')"
                               />
                        <input type="submit" value="<fmt:message key="bottom" bundle="${bundle}" />" name="ncil_nav_bottom" onclick="orden_ejecutar('ncil_nav_bottom')"
                               />
                    </td>
                </tr>
            </table>
        </td>
        <%-- Requirement codes: E5-2 --%>
        <c:if testá"${sessionScope.userCategory == 2}">
            <td align="center"><input type="button" name="newComp" id="newComp" value="<fmt:message key="new" bundle="${bundle}" />"  onclick="orden_ejecutar('new')"/></td>
            <td align="center"><input type="button" name="selecComp" id="deleteComp" value="<fmt:message key="delete" bundle="${bundle}" />"  onclick="if (confirm('<fmt:message key="confirmdeletequestáon" bundle="${bundle}" />')) orden_ejecutar('deleteComp')"/></td>
            </c:if>
        <td align="center"><input type="button" name="selecComp" id="selecComp" value="<fmt:message key="searchselect" bundle="${bundle}" />"  onclick="newsComposition_orden_ejecutar('selec')"/></td>
    </tr>
</table>

<script type="text/javascript">
    function newsComposition_orden_ejecutar(accion) {
        window.document.newspiece_form.runaction.value = accion;
        window.document.newspiece_form.submit();
        return 0;
    }
    function newsComposition_details(id) {
        window.document.newsCompositionlist_form.identificador.value = id;
        orden_ejecutar('details');
        return 0;
    }
    function newsComposition_edit(id) {

        window.document.newspiece_form.idCompositionEdit.value = id;
        newsComposition_orden_ejecutar('editComposition');
        return 0;
    }
    function newsComposition_issue(id,description) {
        window.document.newsCompositionlist_form.identificador.value = id;
        orden_ejecutar('issue');
        return 0;
    }
</script>



