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
<s:i18n name="com.cpuz.st2.actions.NewsCompositionAction">
<table style="width: 100%; ">
    <tr>
        <td>
            <h3 align="center"><s:text name="NewsCompositionList"/></h3>
        </td>
    </tr>
</table>
        <table>
    <%--
    <s:actionmessage/>
    <s:actionerror/>

<s:form id="newspiece_form" name="newspiece_form" method="post" cssClass="data_table">
    --%>
    <s:hidden name="controlCompList.runAction" id="runAction"/>
    <s:hidden name="controlCompList.idKey" id="idKey" />
    <s:hidden name="controlCompList.recStart" id="recStart"/>
    <s:hidden name="controlCompList.recChunk" id="recChunk"/>
    <s:hidden name="controlCompList.recCount" id="recCount"/>

    <%-- Requirement codes: E5-2 --%>
    <s:if test="#session.userCategory == 2">
        <tr>
            <th><s:text name="ID" /></th>
            <th><s:text name="ComponentType" /></th>
            <th><s:text name="HeaderAlt" /></th>
            <th><s:text name="HeaderStyle" /></th>
            <th><s:text name="BodyAbstract" /></th>
            <th><s:text name="Link" /></th>
            <th><s:text name="action"/></th>
            <th><s:text name="sel"/></th>
        </tr>
    </s:if>
    <!-- column data -->
    <s:if test="controlCompList.recCount>0">

        <s:iterator var="row" value="dataCompList" status="rowStatus">
            <s:if test="#rowStatus.odd == true">
                <s:set var="trClass" value="getText('trClassOddRows')" />
            </s:if>
            <s:else>
                <s:set var="trClass" value="getText('trClassEvenRows')"/>
            </s:else>
            <tr class="<s:property value="#trClass"/>">
                <%-- Requirement codes: E5-2 --%>
                <s:if test="#session.userCategory == 2">
                    <td align="center"><s:property value="#row.id"/></td>
                    <td align="center"><s:property value="#row.componentType"/></td>
                    <td align="left"><s:property value="#row.headerAlt"/></td>
                    <td align="center"><s:property value="#row.headerStyle"/></td>
                    <s:if test="#row.componentType =='InfoBlock'" >
                        <td align="left"><s:property value="#row.bodyAbstract"/></td>
                    </s:if>
                    <s:if test="#row.componentType =='Image'" >
                        <td align="#row.headerStyle">
                            <img src="<s:property value="%{#application.dirHomeResources}"/>/../CPUZ/images/<s:property value="%{#row.bodyAbstract}"/>"
                                 alt="<s:property value="%{#application.dirHomeResources}"/>/../CPUZ/images/<s:property value="%{#row.bodyAbstract}"/>"
                                 height="90" width="120"/>
                        </td>
                    </s:if>
                    <td align="left"><s:property value="#row.linkedElement"/></td>
                </s:if>
                <%-- Requirement codes: E5-2 --%>
                <s:if test="#session.userCategory == 2">
                    <td align="center"><s:submit theme="simple" type="button" name="edit" id="edit" value="%{getText('Edit')}"   onclick="newsComposition_edit('%{#row.id}')"/></td>
                    <td align="center"><s:checkbox theme="simple" name="selec1" fieldValue="%{#row.id}"/></td>
                </s:if>
            </tr>
        </s:iterator>

    </s:if>
</table>
<table>
    <tr><td>
            <table><tr><td>
                        <%@include  file="/WEB-INF/views/orderTableFootButtons.jspf" %>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</s:i18n>
<%--
</s:form>
--%>


<script type="text/javascript">
    function newsComposition_orden_ejecutar(accion) {
        window.document.newspiece_form.runAction.value = accion;
        window.document.newspiece_form.action = "NewsPiece_"+accion+".action";
        window.document.newspiece_form.submit();
        return 0;
    }

    function newsComposition_edit(id) {
        window.document.newspiece_form.controlCompList.idKey.value = id;
        newsComposition_orden_ejecutar('editComposition');
        return 0;
    }
</script>



