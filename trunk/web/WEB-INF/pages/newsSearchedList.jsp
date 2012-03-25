<%-- 
    Document   : NewsSection
    Created on : 09-mar-2010, 11:46:23
    Author     : RAFAEL FERRUZ
--%>

<form id="NewsSectionListform" name="NewsSectionListform" method="post" action="NewsSearchedList.do"   class="zorongo">
    <input name="runaction" type="hidden" id="runaction" value="${NewsSelecConditionsFormBean.runaction}"/>
    <input name="maxRows" type="hidden" id="maxRows" value="${NewsSelecConditionsFormBean.maxRows}"/>
    <input name="startRow" type="hidden" id="startRow" value="${NewsSelecConditionsFormBean.startRow}"/>
    <input name="rowCount" type="hidden" id="rowCount" value="${NewsSelecConditionsFormBean.rowCount}"/>

    <c:if test="${NewsSelecConditionsFormBean.rowCount > 0 }">
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


                <c:if test="${newsId ne '' && newsId ne news.npi_id}">

                    <c:choose>
                        <c:when test="${rowClass == 'tr_par'}">
                            <c:set var="rowClass" value="tr_impar"  scope="page"/>
                        </c:when>
                        <c:when test="${rowClass == 'tr_impar'}">
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

                <c:if test="${newsHeaders != ''}">
                    <c:set var="newsHeaders" value="${newsHeaders}<br/>${news.nco_headerAlt}"/>
                </c:if>
                <c:if test="${newsHeaders == ''}">
                    <c:set var="newsHeaders" value="${news.nco_headerAlt}"/>
                </c:if>

            </c:forEach>
            <c:if test="${newsId ne ''}">

                <c:choose>
                    <c:when test="${rowClass == 'tr_par'}">
                        <c:set var="rowClass" value="tr_impar"  scope="page"/>
                    </c:when>
                    <c:when test="${rowClass == 'tr_impar'}">
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
                       <c:if test="${NewsSelecConditionsFormBean.startRow==0}">
                           disabled
                       </c:if>
                       />
                <input type="submit" value=" < " name="nav_prev" onclick="orden_ejecutar('nav_prev')"
                       <c:if test="${NewsSelecConditionsFormBean.startRow==0}">
                           disabled
                       </c:if>
                       />

                <input type="submit" value=" > " name="nav_next" onclick="orden_ejecutar('nav_next')"
                       <c:if test="${NewsSelecConditionsFormBean.startRow>=(NewsSelecConditionsFormBean.rowCount-NewsSelecConditionsFormBean.maxRows)}" >
                           disabled
                       </c:if>
                       />
                <input type="submit" value=">>|" name="nav_last" onclick="orden_ejecutar('nav_last')"
                       <c:if test="${NewsSelecConditionsFormBean.startRow>=(NewsSelecConditionsFormBean.rowCount-NewsSelecConditionsFormBean.maxRows)}" >
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
