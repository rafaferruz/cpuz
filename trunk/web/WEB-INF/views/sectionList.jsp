<%--
    Document   : SectionsList
    Created on : 05-feb-2010, 18:33:22
    Author     : RAFAEL FERRUZ
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%--
<%@page errorPage="error.jsp" %>
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h1 align="center"><s:text name="SectionsList"/></h1>

<s:actionmessage/>
<s:actionerror/>


<s:form id="sectionslist_form" name="sectionslist_form" method="post" cssClass="data_table">
    <s:hidden name="control.runAction" id="runAction"/>
    <s:hidden name="control.idKey" id="idKey" />
    <s:hidden name="control.recStart" id="recStart"/>
    <s:hidden name="control.recChunk" id="recChunk"/>
    <s:hidden name="control.recCount" id="recCount"/>

    <%-- Recibe una lista de Sections y las presenta en pantalla --%>
    <tr>
        <%-- Requirement codes: E5-2 --%>
        <s:if test="#session.userCategory == 2">
            <th><s:text name="Section" /></th>
        </s:if>
        <th><s:text name="Name"/></th>
        <th><s:text name="authorizedRoles"/></th>
        <th><s:text name="group"/></th>
        <th><s:text name="action"/></th>
        <th><s:text name="sel"/></th>
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
                <td>
                    <s:property value="#row.name"/>
                </td>
                <td>
                    <s:property value="#row.authorizedRoles"/>
                </td>
                <td>
                    <s:property value="#row.group"/>
                </td>
                <s:if test="#session.userCategory == 2">
                    <td align="center"><s:submit theme="simple" type="button" name="edit" id="edit" value="%{getText('Edit')}"   onclick="newsEdit('%{#row.id}')"/></td>
                    <td align="center"><s:checkbox theme="simple" name="selec1" fieldValue="%{#row.id}"/></td>
                </s:if>
            </tr>
        </s:iterator>

    </s:if>


    <%@include  file="/WEB-INF/views/listTableFootButtons.jspf" %>

</s:form>

<script type="text/javascript">
    function actionExecute(action) {
        window.document.sectionslist_form.runAction.value = action;
        window.document.sectionslist_form.action = "Section"+action+".action";
        window.document.sectionslist_form.submit();
        return 0;
    }
    function DoNavigation(nav_rule) {
        window.document.sectionslist_form.runAction.value = nav_rule;
        window.document.sectionslist_form.action = "SectionNavigation.action";
        window.document.sectionslist_form.submit();
        return 0;
    }
    function newsEdit(id) {
        window.document.sectionslist_form.idKey.value = id;
        actionExecute('Edit');
        return 0;
    }
</script>



