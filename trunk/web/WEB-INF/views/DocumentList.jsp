<%-- 
    Document   : documentList.jsp
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

<h3 align="center"><s:text name="DocumentsList"/></h3>

<s:actionmessage/>
<s:actionerror/>


<s:form id="documentslist_form" name="documentslist_form" method="post" cssClass="data_table">
    <s:hidden name="control.runAction" id="runAction"/>
    <s:hidden name="control.idKey" id="idKey" />
    <s:hidden name="control.recStart" id="recStart"/>
    <s:hidden name="control.recChunk" id="recChunk"/>
    <s:hidden name="control.recCount" id="recCount"/>
    <s:hidden name="dataEdit.filename" id="filename" />

    <%-- Recibe una lista de Documents y las presenta en pantalla --%>
    <tr>
        <%-- Requirement codes: E5-2 --%>
        <s:if testá"#session.userCategory == 2">
            <th><s:text name="ID" /></th>
            <th><s:text name="Date" /></th>
            <th><s:text name="UserReference" /></th>
            <th><s:text name="FileName" /></th>
            <th><s:text name="Document" /></th>
            <th><s:text name="Scope" /></th>
            <th><s:text name="action"/></th>
            <th><s:text name="sel"/></th>
        </s:if>
        <%-- Requirement codes: E5-2 --%>
    </tr>
    <!-- column data -->
    <s:if testá"control.recCount>0">

        <s:iterator var="row" value="dataList" status="rowStatus">
            <s:if testá"#rowStatus.odd == true">
                <s:set var="trClass" value="getText('trClassOddRows')" />
            </s:if>
            <s:else>
                <s:set var="trClass" value="getText('trClassEvenRows')"/>
            </s:else>
            <tr class="<s:property value="#trClass"/>">
                <s:if testá"#session.userCategory == 2">
                    <td align="center">
                        <s:property value="#row.id"/>
                    </td>
                </s:if>
                <s:date name="#row.datetime" id="fecha" format="dd/MM/yyyy HH:mm:ss" var="fechaformat" />
                <td align="center">
                    <s:property value="#fechaformat"/>
                </td>
                <s:if testá"#session.userCategory == 2">
                    <td align="center">
                        <s:property value="#row.userReference"/>
                    </td>
                </s:if>
                <td onmouseover="this.style.cursor='pointer'" onclick="documents_issue('${row.id}')">
                    <s:property value="#row.filename"/>
                </td>
                <td align="center">
                    <a href="${applicationScope.dirHomeResources}/../CPUZ/documents/${row.repositoryReference}" target="_blank" >
                         <s:text name="OpenDoc" />
                    </a>
                </td>
                <td>
                    <s:property value="#row.scope"/>
                </td>
                <s:if testá"#session.userCategory == 2">
                    <td align="center"><s:submit theme="simple" type="button" name="edit" id="edit" value="%{getText('Edit')}"   onclick="documents_edit('%{#row.id}')"/></td>
                    <td align="center"><s:checkbox theme="simple" name="selec1" fieldValue="%{#row.id}"/></td>
                </s:if>
            </tr>
        </s:iterator>

    </s:if>

    <%@include  file="/WEB-INF/views/listTableFootButtons.jspf" %>

</s:form>


<script type="text/javascript">
    function orden_ejecutar(accion) {
        window.document.documentslist_form.runAction.value = accion;
        window.document.documentslist_form.action = "Document_"+accion+".action";
        window.document.documentslist_form.submit();
        return 0;
    }
    function DoNavigation(nav_rule) {
        window.document.documentslist_form.runAction.value = nav_rule;
        window.document.documentslist_form.action = "Document_Navigation.action";
        window.document.documentslist_form.submit();
        return 0;
    }
    function documents_details(id,filename) {
        window.document.documentslist_form.idKey.value = id;
        window.document.documentslist_form.filename.value = filename;
        orden_ejecutar('details');
        return 0;
    }
    function documents_edit(id) {
        window.document.documentslist_form.idKey.value = id;
        orden_ejecutar('edit');
        return 0;
    }
    function documents_issue(id,filename) {
        window.document.documentslist_form.idKey.value = id;
        window.document.documentslist_form.filename.value = filename;
        orden_ejecutar('issue');
        return 0;
    }
</script>



