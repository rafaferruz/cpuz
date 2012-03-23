<%-- 
    Document   : imagesList.jsp
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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h3 align="center"><s:text name="ImagesList"/></h3>

<s:hidden name="controlComponentType.idKey" id="controlComponentType_idKey" value="%{controlComponentType.idKey}"/>

<%-- Recibe una lista de Images y las presenta en pantalla --%>

<table width="100%" align="center" class="data_table">
    <%-- Requirement codes: E5-2 --%>
    <s:if testá"#session.userCategory == 2">
        <tr>
            <th><s:text name="ID" /></th>
            <th><s:text name="Date" /></th>
            <th><s:text name="userReference" /></th>
            <th><s:text name="filename" /></th>
            <th><s:text name="document" /></th>
            <th><s:text name="scope" /></th>
            <th><s:text name="sel"/></th>
        </tr>
    </s:if>
    <!-- column data -->
    <s:if testá"controlComponentType.recCount>0">

        <s:iterator var="IMGrow" value="dataObjectsIncludeList" status="IMGrowStatus">
            <s:if testá"#IMGrowStatus.odd == true">
                <s:set var="trClass" value="getText('trClassOddRows')" />
            </s:if>
            <s:else>
                <s:set var="trClass" value="getText('trClassEvenRows')"/>
            </s:else>
            <tr class="<s:property value="#trClass"/>">
                <s:if testá"#session.userCategory == 2">
                    <td align="center">
                        <s:property value="#IMGrow.id"/>
                    </td>
                </s:if>
                <s:date name="#IMGrow.datetime" id="fecha" format="dd/MM/yyyy" var="IMGfechaformat" />
                <td align="center">
                    <s:property value="#IMGfechaformat"/>
                </td>
                <td>
                    <s:property value="#IMGrow.userReference" />
                </td
                <td>
                </td
                <td align="center">
                    <img src="${applicationScope.dirHomeResources}/../CPUZ/images/${IMGrow.repositoryReference}"
                         alt="${applicationScope.dirHomeResources}/../CPUZ/images/${IMGrow.repositoryReference}"
                         height="90" width="120"/>
                </td>
                <td>
                    <s:property value="#IMGrow.scope"/>
                </td>
                <s:if testá"#session.userCategory == 2">
                    <td align="center"><s:checkbox theme="simple" name="selec1_IMG" fieldValue="%{#IMGrow.id}"/></td>
                </s:if>
            </tr>
        </s:iterator>
        <tr>
            <td colspan="7">
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="5" class="data_table">
                    <tr>
                        <td align="center">
                            <s:set var="disabled" value="false"/>
                            <s:if testá"controlComponentType.recStart==0">
                                <s:set var="disabled" value="true"/>
                            </s:if>
                            <s:submit theme="simple" type="submit" value="|<<" name="nav_first" onclick="DoNavigationComponentType('nav_first')" disabled="%{#disabled}"/>
                            <s:submit theme="simple" type="submit" value=" < " name="nav_prev" onclick="DoNavigationComponentType('nav_prev')" disabled="%{#disabled}"/>
                            <s:set var="disabled" value="false"/>
                            <s:if testá"(controlComponentType.recStart+controlComponentType.recChunk)>controlComponentType.recCount">
                                <s:set var="disabled" value="true"/>
                            </s:if>
                            <s:submit theme="simple" type="submit" value=" > " name="nav_next" onclick="DoNavigationComponentType('nav_next')" disabled="%{#disabled}"/>
                            <s:submit theme="simple" type="submit" value=">>|" name="nav_last" onclick="DoNavigationComponentType('nav_last')" disabled="%{#disabled}"/>
                        </td>
                        <%-- Requirement codes: E5-2 --%>
                        <td align="center"><s:submit theme="simple" type="button" name="assignComponents" id="assignComponents" value="%{getText('AssignComponents')}"  onclick="orden_ejecutar('AssignComponents')"/></td>
                    </tr>
                </table>
            </td>
        </tr>
    </s:if>
</table>

