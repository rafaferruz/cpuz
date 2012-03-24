<%-- Tag para componener un enlace sobre un elemento párrafo html
	- Uso:
	-	<cpuz:paragrahLink var="name_class" />
--%>

<%@tag description="Crea un string para asignar una clase a un elemento html" 
	   pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="cpuz"%>
<%@attribute name="id" type="java.lang.String" required="true" description="Cadena inicial del enlace"%>
<%@attribute name="order" type="java.lang.String" required="true" description="Cadena inicial del enlace"%>
<%@attribute name="headerStyle" type="java.lang.String" required="true" description="Cadena con la clase a aplicar al texto que formará el enlace"%>
<%@attribute name="headerAlt" type="java.lang.String" required="true" description="Texto alternativo del enlace"%>

<c:set var="startLink">
	<cpuz:createLink link="id" order="order" />
</c:set>
<c:if test="${fn:contains(startLink,'NewsDisplay')}" >
	<c:set var="startLink">
		${fn:replace(startLink,"NewsDisplay","ComponentDisplay")}
	</c:set>
</c:if>
<cpuz:paragraphLink headerStyle="${headerStyle}" headerAlt="${headerAlternate}" startLink="${startLink}" />
