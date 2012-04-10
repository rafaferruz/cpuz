
<h3 align="center"><s:text name="RolesList"/></h3>

<s:actionmessage/>
<s:actionerror/>


<s:form id="roleslist_form" name="roleslist_form" method="post" cssClass="data_table">
    <s:hidden name="control.runAction" id="runAction"/>
    <s:hidden name="control.idKey" id="idKey" />
    <s:hidden name="control.recStart" id="recStart"/>
    <s:hidden name="control.recChunk" id="recChunk"/>
    <s:hidden name="control.recCount" id="recCount"/>

    <tr>
        <%-- Requirement codes: E5-2 --%>
        <s:if test="#session.userCategory == 2">
            <th><s:text name="ID" /></th>
            <th><s:text name="Role" /></th>
            <th><s:text name="Description" /></th>
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
                <td>
                    <s:property value="#row.role"/>
                </td>
                <td>
                    <s:property value="#row.description"/>
                </td>
                <s:if test="#session.userCategory == 2">
                    <td align="center"><s:submit theme="simple" type="button" name="edit" id="edit" value="%{getText('Edit')}"   onclick="news_edit('%{#row.id}','%{#row.role}')"/></td>
                    <td align="center"><s:checkbox theme="simple" name="selec1" fieldValue="%{#row.id}"/></td>
                </s:if>
            </tr>
        </s:iterator>

    </s:if>

    <%@include  file="/WEB-INF/views/listTableFootButtons.jspf" %>

</s:form>

<script type="text/javascript">
    function orden_ejecutar(accion) {
        window.document.roleslist_form.runAction.value = accion;
        window.document.roleslist_form.action = "Role_"+accion+".action";
        window.document.roleslist_form.submit();
        return 0;
    }
    function DoNavigation(nav_rule) {
        window.document.roleslist_form.runAction.value = nav_rule;
        window.document.roleslist_form.action = "Role_Navigation.action";
        window.document.roleslist_form.submit();
        return 0;
    }
    function news_edit(id,titular) {
        window.document.roleslist_form.identificador.value = id;
        window.document.roleslist_form.role.value = titular;
        orden_ejecutar('edit');
        return 0;
    }
</script>



