<%-- Tag para componener un enlace html
	- Uso:
	-	<cpuz:followLink linkedElement="xxxxx" id="99" />
--%>

<%@tag description="Crea un string para asignar una clase a un elemento html" 
	   pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="cpuz"%>
<%@attribute name="link" type="java.lang.String" required="true" description=""%>
<%@attribute name="order" type="java.lang.String" required="false" description=""%>

<c:set var="htmlLink" value=""/>
<c:if test="${link != '' }">
	<c:set var="id" value="0"/>
	<c:catch var="exception">
		<c:set var="dummy" value="${link / 1}"/>
	</c:catch>
	<c:choose>
		<c:when test="${exception !=null}" >
			<a <cpuz:classCss style="composition" /> href='${link}'>
			</c:when>
			<c:otherwise>
				<c:set var="orderParam" value=""/>
				<c:if test="${order != null}">
					<c:set var="orderParam" value="&amp;getOrder=${order}"/>
				</c:if>
				<a <cpuz:classCss style="composition" /> href="${applicationScope.dirApplication}/pages/NewsDisplay.jsp?getid=${link}${orderParam}">
				</c:otherwise>

			</c:choose>

		</c:if>