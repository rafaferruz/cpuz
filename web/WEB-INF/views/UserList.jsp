<%@ taglib prefix="s" uri="/struts-tags" %>

<h3 align="center"><s:text name="UsersList"/></h3>

<s:actionmessage/>
<s:actionerror/>


<s:form id="userlist_form" name="userlist_form" method="post" cssClass="data_table">
    <s:hidden name="control.runAction" id="runAction"/>
    <s:hidden name="control.idKey" id="idKey" />
    <s:hidden name="control.recStart" id="recStart"/>
    <s:hidden name="control.recChunk" id="recChunk"/>
    <s:hidden name="control.recCount" id="recCount"/>


    <%-- Recibe una lista de Users y las presenta en pantalla --%>
    <tr>
        <s:if test="#session.userCategory == 2">
            <th><s:text name="ID" /></th>
        </s:if>
        <th><s:text name="Status"/></th>
        <th><s:text name="Category"/></th>
        <th><s:text name="User"/></th>
        <th><s:text name="Name"/></th>
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
                    <s:property value="#row.estado"/>
                </td>
                <td>
                    <s:property value="#row.category"/>
                </td>
                <td>
                    <s:property value="#row.user"/>
                </td>
                <td>
                    <s:property value="#row.nombre"/>
                </td>
                <s:if test="#session.userCategory == 2">
                    <td align="center"><s:submit theme="simple" type="button" name="edit" id="edit" value="%{getText('Edit')}"   onclick="news_edit('%{#row.id}')"/></td>
                    <td align="center"><s:checkbox theme="simple" name="selec1" fieldValue="%{#row.id+';'+#row.user}"/></td>
                </s:if>
            </tr>
        </s:iterator>
    </s:if>

    <%@include  file="/WEB-INF/views/ListTableFootButtons.jspf" %>

</s:form>

<script type="text/javascript">
    function orden_ejecutar(accion) {
        window.document.userlist_form.runAction.value = accion;
        window.document.userlist_form.action = "User_"+accion+".action";
        window.document.userlist_form.submit();
        return 0;
    }
    function DoNavigation(nav_rule) {
        window.document.userlist_form.runAction.value = nav_rule;
        window.document.userlist_form.action = "User_Navigation.action";
        window.document.userlist_form.submit();
        return 0;
    }
    function news_edit(id) {
        window.document.userlist_form.idKey.value = id;
        orden_ejecutar('edit');
        return 0;
    }
</script>



