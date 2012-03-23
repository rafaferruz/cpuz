<%--
    Document   : newsCompositionList.jsp
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

<h3 align="center"><s:text name="NewsCompositionList"/></h3>

<s:actionmessage/>
<s:actionerror/>

<s:form id="newscomplist_form" name="newscomplist_form" method="post" cssClass="data_table">
    <s:hidden name="control.runAction" id="runAction"/>
    <s:hidden name="control.idKey" id="idKey" />
    <s:hidden name="control.recStart" id="recStart"/>
    <s:hidden name="control.recChunk" id="recChunk"/>
    <s:hidden name="control.recCount" id="recCount"/>
    <s:hidden name="NewsPieceId" id="NewsPieceId" value="%{NewsPieceId}"/>
    <s:hidden name="NewsCompId" id="NewsCompId" value=""/>



    <tr>
        <%-- Requirement codes: E5-2 --%>
        <s:if testá"#session.userCategory == 2">
            <th><s:text name="ID" /></th>
            <th><s:text name="ComponentType" /></th>
            <th><s:text name="HeaderAlt" /></th>
            <th><s:text name="HeaderStyle" /></th>
            <th><s:text name="BodyAbstract" /></th>
            <th><s:text name="Link" /></th>
            <th><s:text name="action"/></th>
            <th><s:text name="sel"/></th>
        </s:if>
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
                <%-- Requirement codes: E5-2 --%>
                <s:if testá"#session.userCategory == 2">
                    <td align="center"><s:property value="#row.id"/></td>
                    <td align="center"><s:property value="#row.componentType"/></td>
                    <td align="left"><s:property value="#row.headerAlt"/></td>
                    <td align="center"><s:property value="#row.headerStyle"/></td>
                    <s:if testá"#row.componentType =='InfoBlock'" >
                        <td align="left"><s:property value="#row.bodyAbstract"/></td>
                    </s:if>
                    <s:if testá"#row.componentType =='Image'" >
                        <td align="#row.headerStyle">
                            <img src="<s:property value="%{#application.dirHomeResources}"/>/../CPUZ/images/<s:property value="%{#row.bodyAbstract}"/>"
                                 alt="<s:property value="%{#application.dirHomeResources}"/>/../CPUZ/images/<s:property value="%{#row.bodyAbstract}"/>"
                                 height="90" width="120"/>
                        </td>
                    </s:if>
                    <td align="left"><s:property value="#row.linkedElement"/></td>
                </s:if>
                <%-- Requirement codes: E5-2 --%>
                <s:if testá"#session.userCategory == 2">
                    <td align="center"><s:submit theme="simple" type="button" name="edit" id="edit" value="%{getText('Edit')}"   onclick="newsComposition_edit('%{#row.id}')"/></td>
                    <td align="center"><s:checkbox theme="simple" name="selec1" fieldValue="%{#row.id}"/></td>
                </s:if>
            </tr>
        </s:iterator>

    </s:if>

    <%@include  file="/WEB-INF/views/orderTableFootButtons.jspf" %>
</s:form>


<script type="text/javascript">
    function newsComposition_orden_ejecutar(accion) {
        window.document.newscomplist_form.runAction.value = accion;
        window.document.newscomplist_form.action = "NewsPiece_"+accion+".action";
        window.document.newscomplist_form.submit();
        return 0;
    }
    function newsComposition_details(id) {
        window.document.newsCompositionlist_form.idKey.value = id;
        orden_ejecutar('details');
        return 0;
    }
    function newsComposition_edit(id) {

        window.document.newscomplist_form.NewsCompId.value = id;
        newsComposition_orden_ejecutar('editComposition');
        return 0;
    }
    function newsComposition_issue(id,description) {
        window.document.newsCompositionlist_form.idKey.value = id;
        orden_ejecutar('issue');
        return 0;
    }
</script>



