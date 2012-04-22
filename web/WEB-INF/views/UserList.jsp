<%@ taglib prefix="s" uri="/struts-tags" %>

<h3 align="center"><s:text name="UsersList"/></h3>

<s:actionmessage/>
<s:actionerror/>


<s:form id="userListForm" name="userListForm" method="post" cssClass="data_table">
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
                    <td align="center"><s:submit theme="simple" type="button" name="edit" id="edit" value="%{getText('Edit')}"   onclick="newsEdit('%{#row.id}')"/></td>
                    <td align="center"><s:checkbox theme="simple" name="selec1" fieldValue="%{#row.id+';'+#row.user}"/></td>
                </s:if>
            </tr>
        </s:iterator>
    </s:if>

    <%@include  file="/WEB-INF/views/listTableFootButtons.jspf" %>

</s:form>

<script type="text/javascript">
    function actionExecute(action) {
        window.document.userListForm.runAction.value = action;
        window.document.userListForm.action = "User"+action+".action";
        window.document.userListForm.submit();
        return 0;
    }
    function doNavigation(navRule) {
        window.document.userListForm.runAction.value = navRule;
        window.document.userListForm.action = "UserNavigation.action";
        window.document.userListForm.submit();
        return 0;
    }
    function newsEdit(id) {
        window.document.userListForm.idKey.value = id;
        actionExecute('Edit');
        return 0;
    }
</script>



