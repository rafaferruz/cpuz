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
<%@attribute name="imageReference" type="java.lang.String" required="true" description="nombre del fichero en el repositorio"%>
<%@attribute name="imagePosition" type="java.lang.String" required="true" description="Alineación de la imagen"%>
<%@attribute name="classCss" type="java.lang.String" required="true" description="estilo css"%>


<img src='${applicationScope.dirHomeResources}/../CPUZ/images/${imageReference}'
	 alt='${applicationScope.dirHomeResources}/../CPUZ/images/${imageReference}'
	 align='${imagePosition}' height='90' width='120' <cpuz:classCss style='${classCss}' /> />
