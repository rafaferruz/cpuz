
<h3 align="center"><s:text name="BugsList"/></h3>

<s:actionmessage/>
<s:actionerror/>


<s:form id="bugslist_form" name="bugslist_form" method="post" cssClass="data_table" >
    <s:hidden name="control.runAction" id="runAction"/>
    <s:hidden name="control.idKey" id="idKey" />
    <s:hidden name="control.recStart" id="recStart"/>
    <s:hidden name="control.recChunk" id="recChunk"/>
    <s:hidden name="control.recCount" id="recCount"/>

    <%-- Recibe una lista de Bugs y las presenta en pantalla --%>
    <tr>
        <%-- Requirement codes: E5-2 --%>
        <th><s:text name="ID" /></th>
        <th><s:text name="Date" /></th>
        <th><s:text name="Status" /></th>
        <th><s:text name="User" /></th>
        <th><s:text name="Priority" /></th>
        <th><s:text name="Type" /></th>
        <th><s:text name="Application" /></th>
        <th><s:text name="Header" /></th>
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
                <td align="center">
                    <s:property value="#row.id"/>
                </td>
                <s:date name="#row.datetime" id="fecha" format="dd/MM/yyyy" var="fechaformat" />
                <td align="center">
                    <s:property value="#fechaformat"/>
                </td>
                <td>
                    <s:property value="#row.status"/>
                </td>
                <td align="center">
                    <s:property value="#row.user"/>
                </td>
                <td>
                    <s:property value="#row.priority"/>
                </td>
                <td>
                    <s:property value="#row.type"/>
                </td>
                <td>
                    <s:property value="#row.application"/>
                </td>
                <td onmouseover="this.style.cursor='pointer'" onclick="bugs_issue('%{#row.id}')">
                    <b><s:property value="#row.header" /></b>
                </td >
                <td align="center"><s:submit theme="simple" type="button" name="edit" id="edit" value="%{getText('Edit')}"   onclick="bugs_edit('%{#row.id}')"/></td>
                <td align="center"><s:checkbox theme="simple" name="selec1" fieldValue="%{#row.id}"/></td>
            </tr>
        </s:iterator>

    </s:if>

    <%@include  file="/WEB-INF/views/listTableFootButtons.jspf" %>

</s:form>

<script type="text/javascript">
    function orden_ejecutar(accion) {
        window.document.bugslist_form.runAction.value = accion;
        window.document.bugslist_form.action = "Bug"+accion+".action";
        window.document.bugslist_form.submit();
        return 0;
    }
    function DoNavigation(nav_rule) {
        window.document.bugslist_form.runAction.value = nav_rule;
        window.document.bugslist_form.action = "BugNavigation.action";
        window.document.bugslist_form.submit();
        return 0;
    }
    function bugs_details(id,header) {
        window.document.bugslist_form.idKey.value = id;
        window.document.bugslist_form.header.value = header;
        orden_ejecutar('details');
        return 0;
    }
    function bugs_edit(id) {
        window.document.bugslist_form.idKey.value = id;
        orden_ejecutar('edit');
        return 0;
    }
    function bugs_issue(id,header) {
        window.document.bugslist_form.idKey.value = id;
        window.document.bugslist_form.header.value = header;
        orden_ejecutar('issue');
        return 0;
    }
</script>