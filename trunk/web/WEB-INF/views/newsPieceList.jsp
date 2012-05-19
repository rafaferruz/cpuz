<%-- 
    Document   : NewsPiecesList.jsp
    Created on : 23-dic-2009, 13:30:17
    Author     : RAFAEL FERRUZ
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%--
<%@page errorPage="error.jsp" %>
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h1 align="center"><s:text name="NewsPiecesList"/></h1>

<s:actionmessage/>
<s:actionerror/>


<s:form id="newspieceslist_form" name="newspieceslist_form" method="post" cssClass="data_table">
    <s:hidden name="control.runAction" id="runAction"/>
    <s:hidden name="control.idKey" id="idKey" />
    <s:hidden name="control.recStart" id="recStart"/>
    <s:hidden name="control.recChunk" id="recChunk"/>
    <s:hidden name="control.recCount" id="recCount"/>

    <%-- Recibe una lista de NewsPieces y las presenta en pantalla --%>
    <tr>
        <%-- Requirement codes: E5-2 --%>
        <s:if test="#session.userCategory == 2">
            <th><s:text name="ID" /></th>
        </s:if>
        <th><s:text name="Date" /></th>
        <th><s:text name="Status" /></th>
        <th><s:text name="Section" /></th>
        <th><s:text name="Description" /></th>
        <th><s:text name="Scope" /></th>
        <th><s:text name="Access" /></th>
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
                <s:if test="#session.userCategory == 2">
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
                <td>
                    <s:property value="#row.section"/>
                </td>
                <td  onmouseover="this.style.cursor='pointer'" onclick="newsPiecesIssue('%{#row.id}')">
                    <b><s:property value="#row.description" /></b>
                </td>
                <td>
                    <s:property value="#row.scope"/>
                </td>
                <td>
                    <s:property value="#row.access"/>
                </td>
                <td align="center"><s:submit theme="simple" type="button" name="edit" id="edit" value="%{getText('Edit')}"   onclick="newsPiecesEdit('%{#row.id}')"/></td>
                <td align="center"><s:checkbox theme="simple" name="selec1" fieldValue="%{#row.id}"/></td>
            </tr>
        </s:iterator>

    </s:if>

    <%@include  file="/WEB-INF/views/listTableFootButtons.jspf" %>

</s:form>

<script type="text/javascript">
    function actionExecute(action) {
        window.document.newspieceslist_form.runAction.value = action;
        window.document.newspieceslist_form.action = "newsPiece"+action+".action";
        window.document.newspieceslist_form.submit();
        return 0;
    }
    function DoNavigation(navRule) {
        window.document.newspieceslist_form.runAction.value = navRule;
        window.document.newspieceslist_form.action = "newsPieceNavigation.action";
        window.document.newspieceslist_form.submit();
        return 0;
    }
    function newsPiecesDetails(id,description) {
        window.document.newspieceslist_form.idKey.value = id;
        window.document.newspieceslist_form.description.value = description;
        actionExecute('Details');
        return 0;
    }
    function newsPiecesEdit(id) {
        window.document.newspieceslist_form.idKey.value = id;
        actionExecute('Edit');
        return 0;
    }
    function newsPiecesIssue(id,description) {
        window.document.newspieceslist_form.idKey.value = id;
        window.document.newspieceslist_form.description.value = description;
        actionExecute('Issue');
        return 0;
    }
</script>



