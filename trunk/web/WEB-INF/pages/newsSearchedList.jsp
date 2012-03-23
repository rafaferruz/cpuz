<%-- 
    Document   : NewsSection
    Created on : 09-mar-2010, 11:46:23
    Author     : RAFAEL FERRUZ
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page buffer="none"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="ztags" uri="/WEB-INF/tlds/ztags.tld"%>

<fmt:setBundle basename="com.cpuz.multilang.cpuz" var="bundle" scope="page"/>

<form id="NewsSectionListform" name="NewsSectionListform" method="post" action="NewsSearchedList.do"   class="zorongo">
    <input name="runaction" type="hidden" id="runaction" value="${NewsSelecConditionsFormBean.runaction}"/>
    <input name="maxRows" type="hidden" id="maxRows" value="${NewsSelecConditionsFormBean.maxRows}"/>
    <input name="startRow" type="hidden" id="startRow" value="${NewsSelecConditionsFormBean.startRow}"/>
    <input name="rowCount" type="hidden" id="rowCount" value="${NewsSelecConditionsFormBean.rowCount}"/>

    <c:if testá"${NewsSelecConditionsFormBean.rowCount > 0 }">
        <c:set var="rowCount" value="${NewsSelecConditionsFormBean.rowCount}"/>
        <c:set var="rowClass" value="tr_par"  scope="page"/>
        <br/>
        <table width="90%" class="data_table">
            <caption class="homepagenews">Selección de Noticias<br/></caption>
            <tr>
                <th>Fecha</th>
                <th>Descripción</th>
                <th>Titulares</th>
            </tr>
            <c:set var="newsHeaders" value=""/>
            <c:set var="newsId" value=""/>
            <c:set var="newsDescription" value=""/>
            <c:forEach var="news" items="${NewsSelecConditionsFormBean.reportData}"
                       begin="${NewsSelecConditionsFormBean.startRow}"
                       end="${NewsSelecConditionsFormBean.startRow + NewsSelecConditionsFormBean.maxRows}"  >


                <c:if testá"${newsId ne '' && newsId ne news.npi_id}">

                    <c:choose>
                        <c:when testá"${rowClass == 'tr_par'}">
                            <c:set var="rowClass" value="tr_impar"  scope="page"/>
                        </c:when>
                        <c:when testá"${rowClass == 'tr_impar'}">
                            <c:set var="rowClass" value="tr_par"  scope="page"/>
                        </c:when>
                    </c:choose>

                    <tr class="${rowClass}">
                        <td width="10%" align="center" >
                            <fmt:formatDate pattern="dd/MM/yyyy" value="${newsDate}"/>
                        </td>
                        <td width="45%">
                            <a class="composition" href="/CPUZ/pages/NewsDisplay.jsp?getid=${news.npi_id}" ><c:out value="${newsDescription}" /></a>
                        </td>
                        <td width="45%">
                            <c:out   escapeXml="false" value="${newsHeaders}" />
                        </td>
                    </tr>
                                <c:set var="newsHeaders" value=""/>

                </c:if>

                <c:set var="newsId" value="${news.npi_id}"/>
                <c:set var="newsDescription" value="${news.npi_description}"/>
                <c:set var="newsDate" value="${news.npi_date}"/>

                <c:if testá"${newsHeaders != ''}">
                    <c:set var="newsHeaders" value="${newsHeaders}<br/>${news.nco_headerAlt}"/>
                </c:if>
                <c:if testá"${newsHeaders == ''}">
                    <c:set var="newsHeaders" value="${news.nco_headerAlt}"/>
                </c:if>

            </c:forEach>
            <c:if testá"${newsId ne ''}">

                <c:choose>
                    <c:when testá"${rowClass == 'tr_par'}">
                        <c:set var="rowClass" value="tr_impar"  scope="page"/>
                    </c:when>
                    <c:when testá"${rowClass == 'tr_impar'}">
                        <c:set var="rowClass" value="tr_par"  scope="page"/>
                    </c:when>
                </c:choose>

                <tr class="${rowClass}">
                    <td width="10%" align="center" >
                        <fmt:formatDate pattern="dd/MM/yyyy" value="${newsDate}"/>
                    </td>
                    <td width="45%">
                        <a class="composition" href="/CPUZ/pages/NewsDisplay.jsp?getid=${news.npi_id}" ><c:out value="${newsDescription}" /></a>
                    </td>
                    <td width="45%">
                        <c:out   escapeXml="false" value="${newsHeaders}" />
                    </td>
                </tr>
            </c:if>
        </table>
    </c:if>

    <table width="90%" border="0" align="center" cellpadding="0" cellspacing="5" bordercolor="#000000">
        <tr>
            <td align="center">
                <input type="submit" value="|<<" name="nav_first" onclick="orden_ejecutar('nav_first')"
                       <c:if testá"${NewsSelecConditionsFormBean.startRow==0}">
                           disabled
                       </c:if>
                       />
                <input type="submit" value=" < " name="nav_prev" onclick="orden_ejecutar('nav_prev')"
                       <c:if testá"${NewsSelecConditionsFormBean.startRow==0}">
                           disabled
                       </c:if>
                       />

                <input type="submit" value=" > " name="nav_next" onclick="orden_ejecutar('nav_next')"
                       <c:if testá"${NewsSelecConditionsFormBean.startRow>=(NewsSelecConditionsFormBean.rowCount-NewsSelecConditionsFormBean.maxRows)}" >
                           disabled
                       </c:if>
                       />
                <input type="submit" value=">>|" name="nav_last" onclick="orden_ejecutar('nav_last')"
                       <c:if testá"${NewsSelecConditionsFormBean.startRow>=(NewsSelecConditionsFormBean.rowCount-NewsSelecConditionsFormBean.maxRows)}" >
                           disabled
                       </c:if>
                       />
            </td>
        </tr>
    </table>

</form>

<script type="text/javascript">

    function orden_ejecutar(accion) {
        window.document.NewsSectionListform.startRow.value = ${NewsSelecConditionsFormBean.startRow};
        window.document.NewsSectionListform.rowCount.value = ${NewsSelecConditionsFormBean.rowCount};
        window.document.NewsSectionListform.runaction.value = accion;
        window.document.NewsSectionListform.submit();
        return 0;
    }

</script>
