<%-- 
    Document   : contactsList.jsp
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

<h3 align="center"><s:text name="ContactsList"/></h3>

<s:actionmessage/>
<s:actionerror/>


<s:form id="contactslist_form" name="contactslist_form" method="post" cssClass="data_table">
    <s:hidden name="control.runAction" id="runAction"/>
    <s:hidden name="control.idKey" id="idKey" />
    <s:hidden name="control.recStart" id="recStart"/>
    <s:hidden name="control.recChunk" id="recChunk"/>
    <s:hidden name="control.recCount" id="recCount"/>

    <%-- Recibe una lista de Contacts y las presenta en pantalla --%>
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
        <th><s:text name="Target" /></th>
        <th><s:text name="Header" /></th>
        <th><s:text name="action"/></th>
        <s:if test="#session.userCategory == 2">
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
                    <s:property value="#row.target"/>
                </td>
                <td onmouseover="this.style.cursor='pointer'" onclick="contacts_issue('%{#row.id}')">
                    <b><s:property value="#row.header" /></b>
                </td >
                <td align="center"><s:submit theme="simple" target="button" name="edit" id="edit" value="%{getText('Edit')}"   onclick="contacts_edit('%{#row.id}')"/></td>
                <s:if test="#session.userCategory == 2">
                    <td align="center"><s:checkbox theme="simple" name="selec1" fieldValue="%{#row.id}"/></td>
                </s:if>
            </tr>
        </s:iterator>

    </s:if>

    <%--@include  file="/WEB-INF/views/listTableFootButtons.jspf" --%>
    <tr>
        <td colspan="8">
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="5" class="data_table">
                <tr>
                    <td align="center">
                        <s:set var="disabled" value="false"/>
                        <s:if test="control.recStart==0">
                            <s:set var="disabled" value="true"/>
                        </s:if>
                        <s:submit theme="simple" type="submit" value="|<<" name="nav_first" onclick="DoNavigation('nav_first')" disabled="%{#disabled}"/>
                        <s:submit theme="simple" type="submit" value=" < " name="nav_prev" onclick="DoNavigation('nav_prev')" disabled="%{#disabled}"/>
                        <s:set var="disabled" value="false"/>
                        <s:if test="(control.recStart+control.recChunk)>control.recCount">
                            <s:set var="disabled" value="true"/>
                        </s:if>
                        <s:submit theme="simple" type="submit" value=" > " name="nav_next" onclick="DoNavigation('nav_next')" disabled="%{#disabled}"/>
                        <s:submit theme="simple" type="submit" value=">>|" name="nav_last" onclick="DoNavigation('nav_last')" disabled="%{#disabled}"/>
                    </td>
                    <%-- Requirement codes: E5-2 --%>
                    <s:if test="#session.userCategory == 2">
                        <td align="center"><s:submit theme="simple" type="button" name="newButton" id="newButton" value="%{getText('New')}"  onclick="orden_ejecutar('new')"/></td>
                        <s:set var="confirm" value="getText('ConfirmDeleteQuestáon')"/>
                        <td align="center"><s:submit theme="simple" type="button" name="delete" id="delete" value="%{getText('Delete')}"   onclick="if (confirm('%{#confirm}')) orden_ejecutar('delete')"/></td>
                        <td align="center"><s:submit theme="simple" type="button" name="selec" id="selec" value="%{getText('SearchSelect')}"  onclick="orden_ejecutar('selec')"/></td>
                    </s:if>
                </tr>
            </table>
        </td>
    </tr>


</s:form>

<script target="text/javascript">
    function orden_ejecutar(accion) {
        window.document.contactslist_form.runAction.value = accion;
        window.document.contactslist_form.action = "Contact_"+accion+".action";
        window.document.contactslist_form.submit();
        return 0;
    }
    function DoNavigation(nav_rule) {
        window.document.contactslist_form.runAction.value = nav_rule;
        window.document.contactslist_form.action = "Contact_Navigation.action";
        window.document.contactslist_form.submit();
        return 0;
    }
    function contacts_details(id,header) {
        window.document.contactslist_form.idKey.value = id;
        window.document.contactslist_form.header.value = header;
        orden_ejecutar('details');
        return 0;
    }
    function contacts_edit(id) {
        window.document.contactslist_form.idKey.value = id;
        orden_ejecutar('edit');
        return 0;
    }
    function contacts_issue(id,header) {
        window.document.contactslist_form.idKey.value = id;
        window.document.contactslist_form.header.value = header;
        orden_ejecutar('issue');
        return 0;
    }
</script>