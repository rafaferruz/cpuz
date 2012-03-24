<%-- Tag para componener un enlace html
	- Uso:
	-	<cpuz:modeNullOrAloneLink startLink="startLink" elementLink="elementLink" />
--%>

<%@tag description="Crea un string para asignar una clase a un elemento html" 
	   pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="cpuz"%>
<%@attribute name="linkedElement" type="java.lang.String" required="true" description=""%>
<%@attribute name="id" type="java.lang.String" required="true" description=""%>
<%@attribute name="mode" type="java.lang.String" required="false" description=""%>

<c:if test="${mode == null || mode !='alone'}">
	<cpuz:followLink linkedElement="${linkedElement}" id="${id}" />
</c:if>