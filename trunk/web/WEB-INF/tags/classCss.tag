<%-- Tag para generar el códifo Javascript necesario en la creación de los menús de la página
	- Uso:
	-	<cpuz:menuJavascriptCode var="name_class" />
--%>
<%@tag description="Crea un string para asignar una clase a un elemento html" 
	   pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="cpuz"%>
<%@attribute name="style" type="java.lang.String" required="true" description="Indica el tipo de estilo del texto a mostrar"%>
<%@attribute name="prefix" type="java.lang.String" required="false" description="Prefijo a colocar delante del name para obtener un nombre compuesto"%>
<%@attribute name="mode" type="java.lang.String" required="false" description=""%>
<c:choose>
	<c:when test="${style == 'Titular'}">class='${prefix}compositionTitular'</c:when>
	<c:when test="${style == 'Subtítulo'}">
		<c:set var="class" value="class='${prefix}compositionSubtitulo'" />
		<c:if test="${mode != null && mode.equals('alone')}">
			<c:set var="class" value="class='${prefix}compositionTitular'" />
		</c:if>
		${class}</c:when>
	<c:when test="${style == 'Destacado'}">
		<c:set var="class" value="class='${prefix}compositionSubtitulo'" />
		<c:if test="${mode != null && mode.equals('alone')}">
			<c:set var="class" value="class='${prefix}compositionDestacado'" />
		</c:if>${class}</c:when>
	<c:when test="${style == 'compositionAbstract'}">class='${prefix}compositionAbstract'</c:when>
	<c:when test="${style == 'compositionDate'}">class='${prefix}compositionDate'</c:when>
	<c:when test="${style == 'composition'}">class='${prefix}composition'</c:when>
</c:choose>
