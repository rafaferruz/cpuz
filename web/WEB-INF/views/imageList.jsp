<%-- 
    Document   : imagesList.jsp
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

<h1 align="center"><s:text name="ImagesList"/></h1>

<s:actionmessage/>
<s:actionerror/>


<s:form id="imageslist_form" name="imageslist_form" method="post" cssClass="data_table">
    <s:hidden name="control.runAction" id="runAction"/>
    <s:hidden name="control.idKey" id="idKey" />
    <s:hidden name="control.recStart" id="recStart"/>
    <s:hidden name="control.recChunk" id="recChunk"/>
    <s:hidden name="control.recCount" id="recCount"/>
    <s:hidden name="dataEdit.filename" id="filename" />

    <%-- Recibe una lista de Images y las presenta en pantalla --%>
    <tr>
        <%-- Requirement codes: E5-2 --%>
        <s:if test="#session.userCategory == 2">
            <th><s:text name="ID" /></th>
            <th><s:text name="Date" /></th>
            <th><s:text name="UserReference" /></th>
            <th><s:text name="FileName" /></th>
            <th><s:text name="Image" /></th>
            <th><s:text name="Scope" /></th>
            <th><s:text name="action"/></th>
            <th><s:text name="sel"/></th>
        </s:if>
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
                <s:date name="#row.datetime" id="fecha" format="dd/MM/yyyy HH:mm:ss" var="fechaformat" />
                <td align="center">
                    <s:property value="#fechaformat"/>
                </td>
                <s:if test="#session.userCategory == 2">
                    <td align="center">
                        <s:property value="#row.userReference"/>
                    </td>
                </s:if>
                <td onmouseover="this.style.cursor='pointer'" onclick="imagesIssue('${row.id}')">
                    <s:property value="#row.filename"/>
                </td>
                <td align="center">
                    <img src="${applicationScope.dirHomeResources}/../CPUZ/images/${row.repositoryReference}"
                         alt="${applicationScope.dirHomeResources}/../CPUZ/images/${row.repositoryReference}"
                         height="90" width="120"/>
                </td>
                <td>
                    <s:property value="#row.scope"/>
                </td>
                <s:if test="#session.userCategory == 2">
                    <td align="center"><s:submit theme="simple" type="button" name="edit" id="edit" value="%{getText('Edit')}"   onclick="imagesEdit('%{#row.id}')"/></td>
                    <td align="center"><s:checkbox theme="simple" name="selec1" fieldValue="%{#row.id}"/></td>
                </s:if>
            </tr>
        </s:iterator>

    </s:if>

    <%@include  file="/WEB-INF/views/listTableFootButtons.jspf" %>

</s:form>


<script type="text/javascript">
    function actionExecute(action) {
        window.document.imageslist_form.runAction.value = action;
        window.document.imageslist_form.action = "Image"+action+".action";
        window.document.imageslist_form.submit();
        return 0;
    }
    function DoNavigation(navRule) {
        window.document.imageslist_form.runAction.value = navRule;
        window.document.imageslist_form.action = "ImageNavigation.action";
        window.document.imageslist_form.submit();
        return 0;
    }
    function imagesDetails(id,filename) {
        window.document.imageslist_form.idKey.value = id;
        window.document.imageslist_form.filename.value = filename;
        actionExecute('Details');
        return 0;
    }
    function imagesEdit(id) {
        window.document.imageslist_form.idKey.value = id;
        actionExecute('Edit');
        return 0;
    }
    function imagesIssue(id,filename) {
        window.document.imageslist_form.idKey.value = id;
        window.document.imageslist_form.filename.value = filename;
        actionExecute('Issue');
        return 0;
    }
</script>



