<%-- 
    Document   : NewsPresentation
    Created on : 09-mar-2010, 11:46:23
    Author     : RAFAEL FERRUZ
--%>

<jsp:useBean id="userService" class="com.cpuz.service.UserService"/>
<jsp:setProperty name="userService" property="userCode" value="${sessionScope['user']}"/>
<jsp:setProperty name="userService" property="roleName" value="regularRole"/>
<c:set var="regularRole">
    <jsp:getProperty name="userService" property="userInRole"/>
</c:set>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">
            <sql:query var="sections" dataSource="jdbc/CPUZ">
                SELECT * FROM sections
                WHERE sec_group IS NULL OR sec_group = ''
                ORDER BY sec_name
            </sql:query>
            <c:forEach var="section" items="${sections.rows}">
                <sql:query var="sectionNews"  dataSource="jdbc/CPUZ" maxRows="6">
                    SELECT * FROM newspieces AS NPI
                    WHERE NPI.npi_status = 2
                    AND (NPI.npi_scope = 0
                    <c:if test="${regularRole}">
                        OR NPI.npi_scope=1
                    </c:if>
                    <c:if test="${sessionScope.userCategory >= 1}">
                        OR  NPI.npi_scope=2
                    </c:if>
                    )
                    AND TO_DAYS(NPI.npi_date) > (TO_DAYS(CURDATE()) - 270)
                    AND NPI.npi_section_id = ?
                    ORDER BY NPI.npi_date DESC
                    <sql:param value="${section.sec_id}"/>
                </sql:query>
                <c:if test="${sectionNews.rowCount > 0 }">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <th class="homepagenews">${section.sec_name}</th>
                        </tr>
                        <tr>
                            <td>
                                <ztags:showNews lengthintroduction="150" columns="3" listNews="${sectionNews}"  >
                                    <sql:query var="composition" dataSource="jdbc/CPUZ"
                                               sql="SELECT * FROM newscomposition AS NCO
                                               WHERE NCO.nco_npi_id = ?
                                               ORDER BY nco_order" >
                                        <sql:param value="${ShowItemId}"/>
                                    </sql:query>
                                    <ztags:showComposition listComp="${composition}"/>
                                </ztags:showNews>
                            </td>
                        </tr>
                        <tr>
                            <td class="visitsection">
                                <a href="/CPUZ/pages/NewsSection.jsp?section=${section.sec_id}"  class="visitsection">Visita la Secci�n:&nbsp;${section.sec_name}&nbsp;>></a>
                            </td>
                        </tr>
                    </table>
                </c:if>

            </c:forEach>
        </td>
    </tr>
</table>

