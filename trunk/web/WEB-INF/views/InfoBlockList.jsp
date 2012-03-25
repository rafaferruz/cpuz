<%-- 
    Document   : infoBlocksList.jsp
    Created on : 23-dic-2009, 13:30:17
    Author     : RAFAEL FERRUZ
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%--
<%@page errorPage="error.jsp" %>
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h3 align="center"><s:text name="InfoBlocksList"/></h3>

<s:actionmessage/>
<s:actionerror/>


<s:form id="infoblockslist_form" name="infoblockslist_form" method="post" cssClass="data_table">
    <s:hidden name="control.runAction" id="runAction"/>
    <s:hidden name="control.idKey" id="idKey" />
    <s:hidden name="control.recStart" id="recStart"/>
    <s:hidden name="control.recChunk" id="recChunk"/>
    <s:hidden name="control.recCount" id="recCount"/>

    <%-- Recibe una lista de InfoBlocks y las presenta en pantalla --%>
    <tr>
        <%-- Requirement codes: E5-2 --%>
        <s:if test="#session.userCategory >= 1">
            <th><s:text name="ID" /></th>
        </s:if>
        <th><s:text name="Date" /></th>
        <th><s:text name="Status" /></th>
        <s:if test="#session.userCategory == 2">
            <th><s:text name="User" /></th>
        </s:if>
        <th><s:text name="Type" /></th>
        <th><s:text name="Header" /></th>
        <th><s:text name="Scope" /></th>
        <th><s:text name="action"/></th>
        <th><s:text name="sel"/></th>
        <%-- Requirement codes: E5-2 --%>
    </tr>
    <!-- column data -->
    <s:if test="control.recCount>0">

        <s:iterator var="row" value="dataList" status="rowStatus">
            <s:if test="#rowStatus.odd == true">
                <s:set var="trClass" value="getText('trClassOddRows')" />
            </s:if>
            <s:else>
                <s:set var="trClass" value="getText('trClassEvenRows')"/>
            </s:else>
            <tr class="<s:property value="#trClass"/>">
                <s:if test="#session.userCategory >= 1">
                    <td align="center">
                        <s:property value="#row.id"/>
                    </td>
                </s:if>
                <s:date name="#row.datetime" id="fecha" format="dd/MM/yyyy" var="fechaformat" />
                <td align="center">
                    <s:property value="#fechaformat"/>
                </td>
                <td>
                    <s:property value="#row.status"/>
                </td>
                <s:if test="#session.userCategory == 2">
                    <td align="center">
                        <s:property value="#row.user"/>
                    </td>
                </s:if>
                <td>
                    <s:property value="#row.type"/>
                </td>
                <td onmouseover="this.style.cursor='pointer'" onclick="infoblocks_issue('%{#row.id}')">
                    <b><s:property value="#row.header" /></b>
                </td >
                <td>
                    <s:property value="#row.scope"/>
                </td>
                <td align="center"><s:submit theme="simple" type="button" name="edit" id="edit" value="%{getText('Edit')}"   onclick="infoblocks_edit('%{#row.id}')"/></td>
                <td align="center"><s:checkbox theme="simple" name="selec1" fieldValue="%{#row.id}"/></td>
            </tr>
        </s:iterator>

    </s:if>

    <%@include  file="/WEB-INF/views/listTableFootButtons.jspf" %>

</s:form>

<script type="text/javascript">
    function orden_ejecutar(accion) {
        window.document.infoblockslist_form.runAction.value = accion;
        window.document.infoblockslist_form.action = "InfoBlock_"+accion+".action";
        window.document.infoblockslist_form.submit();
        return 0;
    }
    function DoNavigation(nav_rule) {
        window.document.infoblockslist_form.runAction.value = nav_rule;
        window.document.infoblockslist_form.action = "InfoBlock_Navigation.action";
        window.document.infoblockslist_form.submit();
        return 0;
    }
    function infoblocks_details(id,header) {
        window.document.infoblockslist_form.idKey.value = id;
        window.document.infoblockslist_form.header.value = header;
        orden_ejecutar('details');
        return 0;
    }
    function infoblocks_edit(id) {
        window.document.infoblockslist_form.idKey.value = id;
        orden_ejecutar('edit');
        return 0;
    }
    function infoblocks_issue(id,header) {
        window.document.infoblockslist_form.idKey.value = id;
        window.document.infoblockslist_form.header.value = header;
        orden_ejecutar('issue');
        return 0;
    }
</script>