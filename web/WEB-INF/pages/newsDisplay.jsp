<%--
    Document   : NewsDisplay
    Created on : 10-mar-2010, 11:46:23
    Author     : RAFAEL FERRUZ
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page buffer="none"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@taglib prefix="ztags" uri="/WEB-INF/tlds/ztags.tld"%>

<fmt:setBundle basename="com.cpuz.multilang.cpuz" var="bundle" scope="page"/>

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="page_index">
    <tr>
        <td valign="top">
            <sql:query var="news" dataSource="jdbc/CPUZ">
                SELECT * FROM newspieces
                WHERE npi_id = ${param.getid}
            </sql:query>
            <ztags:showNews mode="alone" lengthintroduction="99999" columns="1" listNews="${news}"  >
                <sql:query var="composition" dataSource="jdbc/CPUZ"
                           sql="SELECT * FROM newscomposition AS NCO
                           WHERE NCO.nco_npi_id = ?
                           ORDER BY nco_order" >
                    <sql:param value="${ShowItemId}"/>
                </sql:query>
                <c:forEach items="${composition.rows}" var="component" varStatus="control">
                    <c:if testá"${component.nco_component_type == 'InfoBlock'}">
                        <sql:query var="infoblock" dataSource="jdbc/CPUZ"
                                   sql="SELECT * FROM infoblocks AS INB
                                   WHERE INB.inb_id = ?
                                   " >
                            <sql:param value="${component.nco_component_id}"/>
                        </sql:query>
                        <ztags:showCopyComponentToComposition
                            component="${infoblock}"
                            composition="${composition}"
                            index="${control.index}"/>
                    </c:if>
                </c:forEach>
                <ztags:showComposition listComp="${composition}"/>
            </ztags:showNews>
        </td>
    </tr>
</table>


