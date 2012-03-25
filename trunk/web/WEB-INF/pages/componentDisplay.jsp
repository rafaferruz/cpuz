<%--
    Document   : ComponentDisplay
    Created on : 10-mar-2010, 11:46:23
    Author     : RAFAEL FERRUZ
--%>

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
                           AND NCO.nco_order = ${param.getOrder}" >
                    <sql:param value="${ShowItemId}"/>
                </sql:query>
                <c:forEach items="${composition.rows}" var="component" varStatus="control">
                    <c:if test="${component.nco_component_type == 'InfoBlock'}">
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


