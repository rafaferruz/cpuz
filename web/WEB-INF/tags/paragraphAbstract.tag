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
<%@attribute name="bodyAbstract" type="java.lang.String" required="true" description=""%>
<%@attribute name="headerStyle" type="java.lang.String" required="true" description="Cadena con la clase a aplicar al texto"%>

<p <cpuz:classCss style="${headerStyle}" /> >${bodyAbstract}</p>
