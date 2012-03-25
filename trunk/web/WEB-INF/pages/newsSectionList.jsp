<%-- 
    Document   : NewsSection
    Created on : 09-mar-2010, 11:46:23
    Author     : RAFAEL FERRUZ
--%>

<form id="NewsSectionList_form" name="NewsSectionList_form" method="post" action="NewsSectionList.do"   class="zorongo">
    <input name="runaction" type="hidden" id="runaction" value="${NewsSectionListFormBean.runaction}"/>
    <input name="section" type="hidden" id="section" value="${NewsSectionListFormBean.section}" />
    <input name="maxRows" type="hidden" id="maxRows" value="${NewsSectionListFormBean.maxRows}"/>
    <input name="startRow" type="hidden" id="startRow" value="${NewsSectionListFormBean.startRow}"/>
    <input name="rowCount" type="hidden" id="rowCount" value="${NewsSectionListFormBean.rowCount}"/>

            <c:set var="rowCount" value="${NewsSectionListFormBean.rowCount}"/>
    <c:set var="startRow" value="${NewsSectionListFormBean.startRow}"/>
    <c:if test="${NewsSectionListFormBean.startRow<0}">
        <sql:query var="newsCount"  dataSource="jdbc/CPUZ"
                   sql="SELECT npi_id FROM newspieces
                   WHERE npi_status = 2 AND npi_scope >= 0
                   AND npi_section = ?">
            <sql:param value="${NewsSectionListFormBean.section}"/>
        </sql:query>
        <c:forEach var="counter" varStatus="control" items="${newsCount.rows}"  >
            <c:set var="rowCount" value="${control.count}"/>
        </c:forEach>
        <c:set var="startRow" value="${rowCount % NewsSectionListFormBean.maxRows}"/>
        <c:set var="startRow" value="${rowCount-startRow}"/>
    </c:if>
    <sql:query var="sections" dataSource="jdbc/CPUZ"
               sql="SELECT * FROM sections
               WHERE sec_id = ? ">
        <sql:param value="${NewsSectionListFormBean.section}"/>
    </sql:query>
    <c:forEach var="section" items="${sections.rows}">
        <sql:query var="sectionNews"  dataSource="jdbc/CPUZ"
                   maxRows="${NewsSectionListFormBean.maxRows}"  startRow="${startRow}"
                   sql="SELECT * FROM newspieces
                   WHERE npi_status = 2 AND npi_scope >= 0
                   AND npi_section = ?
                   ORDER BY npi_date DESC">
            <sql:param value="${NewsSectionListFormBean.section}"/>
        </sql:query>
        <c:if test="${sectionNews.rowCount > 0 }">
            <c:set var="rowCount" value="${sectionNews.rowCount}"/>
            <c:set var="rowClass" value="tr_par"  scope="page"/>
            <br/>
            <table width="90%" class="data_table">
        <caption class="homepagenews">Listado general de noticias de&nbsp;${section.sec_name}<br/></caption>
                <tr>
                    <th>Fecha</th>
                    <th>Descripción</th>
                    <th>Titulares</th>
                </tr>
                <c:forEach var="news" items="${sectionNews.rows}">
                    <sql:query var="newsComposition"  dataSource="jdbc/CPUZ"
                               sql="SELECT * FROM newscomposition
                               WHERE nco_npi_id = ?
                               AND nco_component_type IN ('InfoBlock','Document','News')
                               ORDER BY nco_order">
                        <sql:param value="${news.npi_id}"/>
                    </sql:query>

                    <c:set var="newsHeaders" value=""/>
                    <c:forEach var="element" items="${newsComposition.rows}">
                        <c:if test="${newsHeaders != ''}">
                            <c:set var="newsHeaders" value="${newsHeaders}<br/>${element.nco_header_alternate}"/>
                        </c:if>
                        <c:if test="${newsHeaders == ''}">
                            <c:set var="newsHeaders" value="${element.nco_header_alternate}"/>
                        </c:if>
                    </c:forEach>

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
                            <fmt:formatDate pattern="dd/MM/yyyy" value="${news.npi_date}"/>
                        </td>
                        <td width="45%">
                            <a class="composition" href="/CPUZ/pages/NewsDisplay.jsp?getid=${news.npi_id}" ><c:out value="${news.npi_description}" /></a>
                        </td>
                        <td width="45%">
                            <c:out   escapeXml="false" value="${newsHeaders}" />
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

    </c:forEach>
    <table width="90%" border="0" align="center" cellpadding="0" cellspacing="5" bordercolor="#000000">
        <tr>
            <td align="center">
                <input type="submit" value="|<<" name="nav_first" onclick="orden_ejecutar('nav_first')"
                       <c:if test="${NewsSectionListFormBean.startRow==0}">
                           disabled
                       </c:if>
                       />
                <input type="submit" value=" < " name="nav_prev" onclick="orden_ejecutar('nav_prev')"
                       <c:if test="${NewsSectionListFormBean.startRow==0}">
                           disabled
                       </c:if>
                       />

                <input type="submit" value=" > " name="nav_next" onclick="orden_ejecutar('nav_next')"
                       <c:if test="${sectionNews.rowCount<NewsSectionListFormBean.maxRows}" >
                           disabled
                       </c:if>
                       />
                <input type="submit" value=">>|" name="nav_last" onclick="orden_ejecutar('nav_last')"
                       <c:if test="${sectionNews.rowCount<NewsSectionListFormBean.maxRows}">
                           disabled
                       </c:if>
                       />
            </td>
        </tr>
    </table>

</form>

<script type="text/javascript">

    function orden_ejecutar(accion) {
        window.document.NewsSectionList_form.startRow.value = ${startRow};
        window.document.NewsSectionList_form.rowCount.value = ${rowCount};
        window.document.NewsSectionList_form.runaction.value = accion;
        window.document.NewsSectionList_form.submit();
        return 0;
    }

</script>
