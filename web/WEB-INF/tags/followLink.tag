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
<%@attribute name="linkedElement" type="java.lang.String" required="true" description=""%>
<%@attribute name="id" type="java.lang.String" required="true" description="Identificador de la news"%>

<c:set var="linkElement" >
	<cpuz:createLink link="${linkedElement}" />
</c:set>
<c:set var="linkId" >
	<cpuz:createLink link="${id}"  />
</c:set>
<c:choose>
	<c:when test="${linkedElement != ''}">
		<cpuz:showLink startLink="${linkElement}" elementLink="  (...continúa)"  />
	</c:when>
	<c:otherwise>
		<cpuz:showLink startLink="${linkId}" elementLink="  (...continúa)"  />
	</c:otherwise>
</c:choose>

