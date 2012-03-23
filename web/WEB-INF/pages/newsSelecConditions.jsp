<%--
    Document   : NewsSelecConditions.jsp
    Created on : 02-feb-2010
    Author     : RAFAEL FERRUZ
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%--
<%@page errorPage="error.jsp" %>
--%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<fmt:setBundle basename="com.cpuz.multilang.cpuz" var="bundle" scope="page"/>

<script>
    function orden_ejecutar(accion) {
        window.document.formFechas.runaction.value = accion;
    <%--        validarFormularioPáginasSeleccionar();--%>
            window.document.formFechas.submit();
        }

</script>


<form id="formFechas" name="formFechas" method="post" action="NewsSearchedList.do">
    <input name="runaction" type="hidden" id="runaction" value="${nscFormBean.runaction}"/>
    <h3 align="center"><fmt:message key="${nscFormBean.titleKey}" bundle="${bundle}" /></h3>
    <table width="90%" height="21" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td><p><fmt:message key="${nscFormBean.helpKey}" bundle="${bundle}" /></p>
                <p>&nbsp;</p></td>
        </tr>
        <tr>
            <td>
                <table align="center"  border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td>
                            <strong><fmt:message key="datefrom" bundle="${bundle}" />:</strong>
                        <td>
                            <html:select value="1"  name="fromDay" property="fromDay" styleClass="div_selectDia">
                                <c:forEach var="dayNumber" begin="1" end="31">
                                    <html:option value="${dayNumber}">${dayNumber}</html:option>
                                </c:forEach>

                            </html:select>
                        </td>
                        <td>
                            <html:select  value="1" name="fromMonth" styleClass="div_selectMes"  property="fromMonth">
                                <c:forEach var="monthNumber" begin="1" end="12">
                                    <html:option value="${monthNumber}"><fmt:message key="Month_abbr_${monthNumber}" bundle="${bundle}" /></html:option>
                                </c:forEach>
                            </html:select>
                        </td>
                        <td>
                            <html:select  value="${nscFormBean.firstYear}" property="fromYear" name="fromYear" styleClass="div_selectAnio">
                                <c:forEach var="yearNumber" begin="${nscFormBean.firstYear}" end="${nscFormBean.lastYear}">
                                    <html:option value="${yearNumber}">${yearNumber}</html:option>
                                </c:forEach>
                            </html:select>
                        </td>
                        <%--
                        <td>
                            <html:img src="http://www.ecosysw.com/ZORONGO/WEB-INF/imagenes/calendar2.gif" alt="c" onmousedown="mostrarCalendario(event)" onmouseover="JavaScript: calendario='desde'"/>
                        </td>
                        --%>
                        <td>
                            <strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong>
                        </td>
                        <td><strong><fmt:message key="dateto" bundle="${bundle}" />:</strong></td>
                        <td>
                            <html:select value="31" property="toDay" name="toDay" styleClass="div_selectDia">
                                <c:forEach var="dayNumber" begin="1" end="31">
                                    <html:option value="${dayNumber}">${dayNumber}</html:option>
                                </c:forEach>
                            </html:select>
                        </td>
                        <td><html:select value="12" property="toMonth" name="toMonth" styleClass="div_selectMes">
                                <c:forEach var="monthNumber" begin="1" end="12">
                                    <html:option value="${monthNumber}"><fmt:message key="Month_abbr_${monthNumber}" bundle="${bundle}" /></html:option>
                                </c:forEach>
                            </html:select>
                        </td>
                        <td>
                            <html:select value="${nscFormBean.lastYear}" property="toYear" name="toYear" styleClass="div_selectAnio">
                                <c:forEach var="yearNumber" begin="${nscFormBean.firstYear}" end="${nscFormBean.lastYear}">
                                    <html:option value="${yearNumber}">${yearNumber}</html:option>
                                </c:forEach>
                            </html:select>
                        </td>
                        <%--
                        <td><img src="http://www.ecosysw.com/ZORONGO/WEB-INF/imagenes/calendar2.gif" alt="c" onmousedown="mostrarCalendario(event)" onmouseover="JavaScript: calendario='hasta'"/></td>
                        --%>
                    </tr>
                </table>
                <p>
                    <label><fmt:message key="lookingforwords" bundle="${bundle}" />:
                        <input name="words" type="text" id="words" size="30" maxlength="60" class="noticias_form"/>
                    </label>
                    <%--                        <label><fmt:message key="maxnumbernews" bundle="${bundle}" />:
                                                <input name="maxreg" type="text" id="maxreg" value="30" size="4" maxlength="3"  class="noticias_form"/>
                                            </label>
                    --%>
                    <br><br>
                </p>
                <table width="100%">
                    <tr>

                        <td  width="35%" align="right">
                            <input type="button" name="search" id="search" value="<fmt:message key="searchselect" bundle="${bundle}" />" onclick="orden_ejecutar('list_selec')" />
                        </td>
                    </tr>
                </table>

                <p>
                    <input type="hidden" name="fromDate" id="fromDate" />
                    <input type="hidden" name="toDate" id="toDate" />
                </p>
            </td>
        </tr>
    </table>
</form>
<p>&nbsp;</p>

<div id='divCalendario' align='center' class="div_Calendario">
    <table id="tblCalendar" width="126" border="0" cellspacing="0" cellpadding="0" height="10">
        <tr>
            <td height="1" bgcolor="#CCCCCC"><div align="center"><font face="Verdana, Arial, Helvetica, sans-serif" size="1" color="#FFFFFF"><a href="JavaScript:PreviousMonth()"><img src="http://www.ecosysw.com/ZORONGO/WEB-INF/imagenes/anterior.gif" width="7" height="7" border="0" /></a></font></div></td>
            <td height="1" colspan="5" bgcolor="#CCCCCC"><font face="Verdana, Arial, Helvetica, sans-serif" size="1" color="#FFFFFF"></font><font face="Verdana, Arial, Helvetica, sans-serif" size="1" color="#FFFFFF"></font><font face="Verdana, Arial, Helvetica, sans-serif" size="1" color="#FFFFFF"></font><font face="Verdana, Arial, Helvetica, sans-serif" size="1" color="#FFFFFF"></font><font face="Verdana, Arial, Helvetica, sans-serif" size="1" color="#FFFFFF"></font></td>
            <td height="1" bgcolor="#CCCCCC"><div align="center"><font face="Verdana, Arial, Helvetica, sans-serif" size="1" color="#FFFFFF"><a href="JavaScript:NextMonth()"><img src="http://www.ecosysw.com/ZORONGO/WEB-INF/imagenes/siguiente.gif" width="7" height="7" border="0" /></a></font></div></td>
        </tr>
        <tr>
            <td width="18" height="1"><div align="center"><font face="Verdana, Arial, Helvetica, sans-serif" size="1">L</font></div></td>
            <td width="18" height="1"><div align="center"><font face="Verdana, Arial, Helvetica, sans-serif" size="1">M</font></div></td>
            <td width="18" height="1"><div align="center"><font face="Verdana, Arial, Helvetica, sans-serif" size="1">X</font></div></td>
            <td width="18" height="1"><div align="center"><font face="Verdana, Arial, Helvetica, sans-serif" size="1">J</font></div></td>
            <td width="18" height="1"><div align="center"><font face="Verdana, Arial, Helvetica, sans-serif" size="1">V</font></div></td>
            <td width="18" height="1"><div align="center"><font face="Verdana, Arial, Helvetica, sans-serif" size="1">S</font></div></td>
            <td width="18" height="1"><div align="center"><font face="Verdana, Arial, Helvetica, sans-serif" size="1">D</font></div></td>
        </tr>
        <tr>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
        </tr>
        <tr>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
        </tr>
        <tr>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1">&nbsp;</td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
        </tr>
        <tr>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
        </tr>
        <tr>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
        </tr>
        <tr>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
            <td height="1"><font face="Verdana, Arial, Helvetica, sans-serif" size="1"></font></td>
        </tr>
    </table>
</div>
