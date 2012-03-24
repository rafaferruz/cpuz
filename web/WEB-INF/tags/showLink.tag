<%-- Tag para componener un enlace html
	- Uso:
	-	<cpuz:showLink startLink="startLink" elementLink="elementLink" />
--%>

<%@tag description="Crea un string para asignar una clase a un elemento html" 
	   pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="cpuz"%>
<%@attribute name="startLink" type="java.lang.String" required="true" description="Cadena inicial del enlace"%>
<%@attribute name="elementLink" type="java.lang.String" required="true" description="Cadena con el elemento sobre el que se crea el enlace"%>

${startLink} ${elementLink}
<c:if test="${startLink != ''}">
</a>
</c:if>
