<%-- 
Plantilla que define la estáuctura de las páginas de vistas de la aplicación.
La plantilla ofrece una estructura de página html con un elemento <head> y un elemento <body>
- El elemento <head> realiza un include del fichero 'jspf/head01.jspf' que deberá contener
	los enlaces a ficheros de estilos CSS y Javascript que deseen usarse en las vistas.
- El elemento <body> define la estáuctura de la vista, estábleciendo una cabecera que deberá
	definirse en el fichero 'jspf/cabecera.jspf', y un cuerpo con una zona central, definido en
	el fichero 'jspf/bodycenter.jspf', y una columna lateral derecha, definida en el fichero 
	'jspf/bodyright.jspf'.
--%>
<c:set var="titleHead">
	<fmt:message bundle="${bundle}" key="titleHead" />
</c:set>




<%-- Start of html page --%>
<html>
	<%@ include file="jspf/htmlHeaders.jspf" %>
	
    <body class="yui-skin-sam">
		<div class="page_margins">
			<div class="page">
				<div id="header">
					<%-- Se incluye una cabecera con tres filas de tabla.
						La primera contiene el banner de cabeceracon el logotipo.
						La segunda está pensada para una barra de noticias.
						La tercera está pensada para uns barra de men?.
					--%>
					<%@ include file="jspf/bodyTop.jspf" %>
					<div id="topnav">
					</div>
					<h1 id="title"><a href="${pageContext.request.contextPath}/common/welcome.do"><fmt:message bundle="${bundle}" key="page.headerTitle" /></a></h1>
					<span id="subtitle"><em><fmt:message bundle="${bundle}" key="page.headerSubtitle" /></em></span>
				</div>
				<!-- begin: main navigation #nav -->
				<c:if test="${not empty section}">
					<div id="nav">
						<div class="hlist">
							<h2 class="hideme"><fmt:message bundle="${bundle}" key="page.mainMenu" /></h2>
							<c:set var="menu">
								<%--@include file="menu.jspf" --%>
							</c:set>
							<c:if test="${not empty menu}"><ul>${menu}</ul></c:if>
						</div>
					</div>
				</c:if>
				<!-- end: main navigation -->
				<!-- begin: main content area #main -->
				<div id="main">
					<%@ include file="jspf/bodyCenter.jspf" %>
				</div>
				<!-- end: #main -->
				<!-- begin: #footer -->
				<div id="footer">
                    <%--
   <zorongo:links cadenamenu="Página inicial?index.jsp|
                                      //Mapa de la Web?mapaweb.jsp|
                                      Cerrar sesión?docs/cerrarsesión.jsp|
                                      Publicidad?docs/publicidad.jsp"
                                      separadoritems="?" separadormenu="|" mododisplay="pie" />
                    --%>
					<fmt:message bundle="${bundle}" key="page.credits" /> |
					<a href="http://www.w3.org/TR/xhtml1/" title="<fmt:message bundle="${bundle}" key="page.xhtmlCompliant" />">XHTML 1.0</a> |
					<a href="http://www.w3.org/TR/CSS21/" title="<fmt:message bundle="${bundle}" key="page.cssCompliant" />">CSS 2.1</a> |
					<%--
					<a href="http://www.w3.org/WAI/" title="<codex:message key="page.waiCompliant" />">WAI-AA</a> |
					<a href="http://www.yaml.de/" title="<fmt:message bundle="${bundle}" key="page.basedOnYaml" />">YAML</a>
					--%>
				</div>
				<!-- end: #footer -->
			</div>
		</div>
        <script type="text/javascript">
            function funciones_iniciales() {
                regenerate2();
                if (div_inicial != null && div_inicial.length>0)
                    mostrar(div_inicial);
                PosicionaPanelMenu();
                empezarMenuNavegador = true;
            }
            window.onload=funciones_iniciales;
        </script>
	</body>

</html>
