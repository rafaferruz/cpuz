/**
 *  Generated tag class.
 * Creada el 30 de julio de 2010 por Rafael Ferruz
 * Se trata de una versión modificada de la clase LinksHandlerV3 para pasar como
 * atributos opcionales las clases que controlan el style de los menus
 */
package com.cpuz.taghandlers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 *  Generated tag class.
 */
public class LinksHandlerV3 extends BodyTagSupport {

	private static final long serialVersionUID = 309L;
	/** property declaration for tag attribute: separadormenu.
	 *
	 */
	private java.lang.String separadormenu;
	private java.lang.String separadoritems;
	private java.lang.String mododisplay;
	private java.lang.String cadenamenu;
	private java.lang.String class_parent;
	private java.lang.String class_child;
	private java.lang.String class_boxchild;
	private java.lang.String class_navigationbar;
	private java.lang.String class_boxparent;
	private java.lang.String class_childtable;
	private java.lang.String halign;

	public LinksHandlerV3() {
		super();
	}
	////////////////////////////////////////////////////////////////
	///                                                          ///
	///   User methods.                                          ///
	///                                                          ///
	///   Modify these methods to customize your tag handler.    ///
	///                                                          ///
	////////////////////////////////////////////////////////////////

	/**
	 *
	 * Generate the links based on the separadormenu attribute passed
	 * in to the tag.
	 */
	public void otherDoStartTagOperations() {

		if (mododisplay.equals("null")) {
			return;
		}

		HttpServletRequest request = (HttpServletRequest) (pageContext.getRequest());
		ResourceBundle bundle = BundleHelper.getBundle(request.getLocales());

		HttpServletResponse response = (HttpServletResponse) (pageContext.getResponse());
		HttpSession session = request.getSession(false);

		StringBuffer links = new StringBuffer("");
		StringBuffer menuBarra = new StringBuffer("");
		StringBuffer menuPaneles = new StringBuffer("");

		int numeroPanelMenu = 0;
		String varcapasJavascriptMenuNavegador = "";

		// Generamos una lista con cada línea de definición de menú
		List<String> menuDefs = Arrays.asList(cadenamenu.split("\\" + separadormenu));
		Integer needCategory = 0;
		if (session.getAttribute("userCategory") == null) {
			session.setAttribute("userCategory", 0);
		}

		for (String menuDef : menuDefs) {
			List<String> slideDefs = Arrays.asList(menuDef.split("\\" + separadoritems));
			// Recuperamos el texto de la línea de menú
			String texto = slideDefs.get(0).replaceAll("\n", "").trim();
			String enlace = "";
			// Recuperamos la dirección de enlace
			if (slideDefs.size() > 1) {
				enlace = slideDefs.get(1).trim();
			}
			// Recuperamos el nivel de categoría del usuario necesario para mostrar la línea
			needCategory = 0;
			if (slideDefs.size() > 2) {
				needCategory = Integer.parseInt(slideDefs.get(2).trim());
			}

			if (!texto.startsWith("//")) { 	// Si se trata de una línea de comentario 
				// no se trata y se toma la siguiente línea
				// de definición de menú

				if ((Integer) session.getAttribute("userCategory") >= needCategory) {
					switch (mododisplay) {
						case "column":
							links.append("<tr><td><table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr>");
							if (!enlace.equals("")) {
								links.append(getTd20());
								links.append(getTd80Start());
								links.append(getDivLeftStart());
								menuPaneles.append(getLinkElement(request, response, enlace, "class=\"menu_2\"", texto));
							} else {
								links.append(getTdMenusStart());
								links.append(getDivLeftStart());
								links.append("&nbsp;&nbsp;" + texto);
							}
							links.append("</div></td></tr></table></td></tr>");
							break;
						case "foot":
							menuPaneles.append(getLinkElement(request, response, enlace, "class=\"menu_2\"", texto));
							if (menuDefs.indexOf(menuDef) < menuDefs.size() - 1) {
								links.append(" | ");
							}
							break;
						case "navigator":
							if (!enlace.equals("")) {
								menuPaneles.append(getTrLinkMenu(request, response, enlace, texto));
							} else {
								// Se crea la barra principal de navegación
								if (menuPaneles.length() != 0) {
									menuPaneles.append("</table>\n</div>\n");
								} else {
									// Es la primera línea del menú
									links.append("\n<table class=\"" + class_navigationbar + "\" align=\"" + halign + "\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
									links.append("\n<tr>");
								}
								if (!varcapasJavascriptMenuNavegador.equals("")) {
									varcapasJavascriptMenuNavegador = varcapasJavascriptMenuNavegador + ",";
								}
								menuBarra.append(getTextBar(numeroPanelMenu, texto));
								menuPaneles.append(getTextPanel(texto));
								varcapasJavascriptMenuNavegador = varcapasJavascriptMenuNavegador
										+ "\"" + texto + "\"";
							}
							break;
					}
				}
			}
		}
		if (mododisplay.equals("navigator")) {
// Se envía la barra de navegación
			links.append(menuBarra);
			links.append("\n</tr>");
			links.append("\n<tr>\n<td>");

// Se envían los paneles de menús hijos
			if (menuPaneles.length() != 0) {
				menuPaneles.append("\n</table>\n</div>\n");
				links.append(menuPaneles);
				links.append("\n</td>\n</tr>\n");
			}
// Se cierra la tabla que forma el menú de navegación
			links.append("\n</table>");

// insertamos código HTML generado

			// insertamos código JavaScript
			links = getJavaScriptCode(links, numeroPanelMenu, varcapasJavascriptMenuNavegador);

			try {
				JspWriter out = pageContext.getOut();
				out.print(links.toString());
			} catch (java.io.IOException ex) {
				// do nothing
			}
		}

	
	}
	

	private String getTd20() {
		return "<td width=\"20%\" bgcolor=\"#80FF80\" >&nbsp;</td>";
	}

	private String getTd80Start() {
		return "<td width=\"80%\" bgcolor=\"#80FF80\" >";
	}

	private String getTdMenusStart() {
		return "<td bgcolor=\"#10C410\" class=\"menus\">";
	}

	private String getLinkElement(HttpServletRequest request, HttpServletResponse response, String enlace, String cssClass, String texto) {
		if (!request.getSession().isNew()) {
			enlace = response.encodeURL(enlace);
		}
		if (enlace.startsWith("http") || enlace.startsWith("#")) {
			return " <a href=\"" + enlace + cssClass + ">" + texto + "</a>";
		} else {
			return " <a href=\"" + request.getContextPath() + "/" + enlace + cssClass + ">" + texto + "</a>";
		}
	}

	private String getDivLeftStart() {
		return "<div align=\"left\">";
	}

	private String getTrLinkMenu(HttpServletRequest request, HttpServletResponse response, String enlace, String texto) {
		return "<tr>\n<td class=\"" + class_child + "\">\n "
				+ getLinkElement(request, response, enlace, "class=\"" + class_parent + "\"", "&nbsp;&nbsp;" + texto + "&nbsp;&nbsp;&nbsp;&nbsp;")
				+ "\n</td>\n</tr>\n";

	}

	private String getTextBar(int numeroPanelMenu, String texto) {
		return "\n<td>\n<div id=ancla" + numeroPanelMenu++
				+ " class=" + class_boxparent + " >\n<a href=\"#\" "
				+ " onmouseover=\"MuestraPanelMenuColoca('" + texto + "')\" "
				+ " onmouseout=\"OcultaPanelMenuRetarda('" + texto + "')\">&nbsp;&nbsp;&nbsp;&nbsp;"
				+ texto + "&nbsp;&nbsp;&nbsp;&nbsp;\n</a>\n</div>\n</td>\n";
	}

	private String getTextPanel(String texto) {
		return "\n<div id=\""
				+ texto + "\" class=\"" + class_boxchild + "\"  "
				+ " onmouseover=\"MuestraPanelMenuRetarda('" + texto + "')\" "
				+ " onmouseout=\"OcultaPanelMenuRetarda('" + texto + "')\" "
				+ " >\n"
				+ "\n<table class=\"" + class_childtable + "\">"
				+ "\n";
	}

	private StringBuffer getJavaScriptCode(StringBuffer links, int numeroPanelMenu, String varcapasJavascriptMenuNavegador) {
		links.append("\n\n<script type='text/javascript'>\n");
		links.append("var empezarMenuNavegador = false\n");
		links.append("var anclasMenuNavegador = new Array (");
		for (int i = 1; i <= numeroPanelMenu; i++) {
			links.append("\"ancla" + i + "\"");
			if (i < numeroPanelMenu) {
				links.append(",");
			}
		}
		links.append(")\n");
		links.append("\n");
		links.append("var capasMenuNavegador = new Array(").append(varcapasJavascriptMenuNavegador).append(")\n");
		links.append("var retardoMenuNavegador\n");
		links.append("var ocultarMenuNavegador\n");
		links.append("function MuestraPanelMenu(capa){\n");
		links.append("xShow(capa);\n");
		links.append("}\n");
		links.append("function OcultaPanelMenu(capa){\n");
		links.append("xHide(capa);\n");
		links.append("}\n");
		links.append("function PosicionaPanelMenu(){\n");

		links.append("for (i=0;i<capasMenuNavegador.length;i++){\n");
		links.append("posx= xOffsetLeft(anclasMenuNavegador[i])\n");
		links.append("if (posx>1050) posx=1050;\n");
		links.append("posy= xOffsetTop(anclasMenuNavegador[i])\n");
		links.append("xMoveTo(capasMenuNavegador[i],posx,posy+30)\n");
		links.append("} \n");

		links.append("}\n");

		links.append("PosicionaPanelMenu()\n");
		links.append("empezarMenuNavegador = true\n");


		links.append("window.onresize = function() {\n");
		links.append("PosicionaPanelMenu()\n");
		links.append("}\n");

		links.append("function MuestraPanelMenuColoca(capa){\n");
		links.append("if (empezarMenuNavegador){\n");
		links.append("for (i=0;i<capasMenuNavegador.length;i++){\n");
		links.append("if (capasMenuNavegador[i] != capa) xHide(capasMenuNavegador[i])\n");
		links.append("}\n");
		links.append("clearTimeout(retardoMenuNavegador)\n");
		links.append("xShow(capa)\n");
		links.append("}\n");
		links.append("}\n");

		links.append("function OcultaPanelMenuRetarda(capa){\n");
		links.append("if (empezarMenuNavegador){\n");
		links.append("ocultarMenuNavegador =capa\n");
		links.append("clearTimeout(retardoMenuNavegador)\n");
		links.append("retardoMenuNavegador = setTimeout(\"xHide('\" + ocultarMenuNavegador + \"')\",1000)\n");
		links.append("}\n");
		links.append("}\n");

		links.append("function MuestraPanelMenuRetarda(ind){\n");
		links.append("if (empezarMenuNavegador){\n");
		links.append("clearTimeout(retardoMenuNavegador)\n");
		links.append("}\n");
		links.append("} \n");

		links.append("</script> \n");
		return links;
	}

	/**
	 *
	 * Fill in this method to determine if the tag body should be evaluated
	 * Called from doStartTag().
	 *
	 * Return false, since this tag is declared to have an empty body.
	 */
	public boolean theBodyShouldBeEvaluated() {
		return false;
	}

	/**
	 *
	 * doEndTag() does nothing
	 *
	 */
	public void otherDoEndTagOperations() {
		return;
	}

	/**
	 *
	 * The restáof the JSP page should be evaluated after this tag
	 * is finished..
	 *
	 */
	public boolean shouldEvaluateRestOfPageAfterEndTag() {
		return true;
	}
////////////////////////////////////////////////////////////////
///                                                          ///
///   Tag Handler interface methods.                         ///
///                                                          ///
///   Do not modify these methods; instead, modify the       ///
///   methods that they call.                                ///
///                                                          ///
////////////////////////////////////////////////////////////////

	/** .
	 *
	 * This method is called when the JSP engine encounters the start tag,
	 * after the attributes are processed.
	 * Scripting variables (if any) have their values set here.
	 * @return EVAL_BODY_INCLUDE if the JSP engine should evaluate the tag body, otherwise return SKIP_BODY.
	 * This method is automatically generated. Do not modify this method.
	 * Instead, modify the methods that this method calls.
	 *
	 *
	 */
	public int doStartTag() throws JspException, JspException {
		otherDoStartTagOperations();

		if (theBodyShouldBeEvaluated()) {
			return EVAL_BODY_BUFFERED;
		} else {
			return SKIP_BODY;
		}

	}

	/** .
	 *
	 *
	 * This method is called after the JSP engine finished processing the tag.
	 * @return EVAL_PAGE if the JSP engine should continue evaluating the JSP page, otherwise return SKIP_PAGE.
	 * This method is automatically generated. Do not modify this method.
	 * Instead, modify the methods that this method calls.
	 *
	 *
	 */
	public int doEndTag() throws JspException, JspException {
		otherDoEndTagOperations();

		if (shouldEvaluateRestOfPageAfterEndTag()) {
			return EVAL_PAGE;
		} else {
			return SKIP_PAGE;
		}

	}

	public java.lang.String getCadenamenu() {
		return cadenamenu;
	}

	public void setCadenamenu(java.lang.String value) {
		cadenamenu = value;
	}

	public java.lang.String getSeparadormenu() {
		return separadormenu;
	}

	public void setSeparadormenu(java.lang.String value) {
		separadormenu = value;
	}

	public java.lang.String getSeparadoritems() {
		return separadoritems;
	}

	public void setSeparadoritems(java.lang.String value) {
		separadoritems = value;
	}

	public java.lang.String getMododisplay() {
		return mododisplay;
	}

	public void setMododisplay(java.lang.String value) {
		mododisplay = value;
	}

	public String getClass_child() {
		return class_child;
	}

	public void setClass_child(String class_child) {
		this.class_child = class_child;
	}

	public String getClass_parent() {
		return class_parent;
	}

	public void setClass_parent(String class_parent) {
		this.class_parent = class_parent;
	}

	public String getClass_boxchild() {
		return class_boxchild;
	}

	public void setClass_boxchild(String class_boxchild) {
		this.class_boxchild = class_boxchild;
	}

	public String getClass_navigationbar() {
		return class_navigationbar;
	}

	public void setClass_navigationbar(String class_navigationbar) {
		this.class_navigationbar = class_navigationbar;
	}

	public String getClass_boxparent() {
		return class_boxparent;
	}

	public void setClass_boxparent(String class_boxparent) {
		this.class_boxparent = class_boxparent;
	}

	public String getClass_childtable() {
		return class_childtable;
	}

	public void setClass_childtable(String class_childtable) {
		this.class_childtable = class_childtable;
	}

	public String getHalign() {
		return halign;
	}

	public void setHalign(String halign) {
		this.halign = halign;
	}

	/** .
	 * Fill in this method to process the body content of the tag.
	 * You only need to do this if the tag's BodyContent property
	 * is set to "JSP" or "tagdependent."
	 * If the tag's bodyContent is set to "empty," then this method
	 * will not be called.
	 *
	 *
	 */
	public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException {
		//
		// TODO: insert code to write html before writing the body content.
		//       e.g.  out.println("<B>" + getAttribute1() + "</B>");
		//             out.println("   <BLOCKQUOTE>");

		//
		// write the body content (after processing by the JSP engine) on the output Writer
		//
		bodyContent.writeOut(out);

		//
		// Or else get the body content as a string and process it, e.g.:
		//     String bodyStr = bodyContent.getString();
		//     String result = yourProcessingMethod(bodyStr);
		//     out.println(result);
		//

		// TODO: insert code to write html after writing the body content.
		//       e.g.  out.println("   <BLOCKQUOTE>");

		// clear the body content for the next time through.
		bodyContent.clearBody();
	}

	/** .
	 *
	 * Handles exception from processing the body content.
	 *
	 *
	 */
	public void handleBodyContentException(Exception ex) throws JspException {
		// Since the doAfterBody method is guarded, place exception handing code here.
		throw new JspException("error in LinksHandler: " + ex);
	}

	/** .
	 *
	 *
	 * This method is called after the JSP engine processes the body content of the tag.
	 * @return EVAL_BODY_AGAIN if the JSP engine should evaluate the tag body again, otherwise return SKIP_BODY.
	 * This method is automatically generated. Do not modify this method.
	 * Instead, modify the methods that this method calls.
	 *
	 *
	 */
	public int doAfterBody() throws JspException {
		try {
			//
			// This code is generated for tags whose bodyContent is "tagdependent"
			//

			JspWriter out = getPreviousOut();
			BodyContent bodyContent = getBodyContent();

			writeTagBodyContent(out, bodyContent);
		} catch (Exception ex) {
			handleBodyContentException(ex);
		}

		if (theBodyShouldBeEvaluatedAgain()) {
			return EVAL_BODY_AGAIN;
		} else {
			return SKIP_BODY;
		}

	}

	/**
	 * Fill in this method to determine if the tag body should be evaluated
	 * again after evaluating the body.
	 * Use this method to create an iterating tag.
	 * Called from doAfterBody().
	 *
	 *
	 */
	public boolean theBodyShouldBeEvaluatedAgain() {
		//
		// TODO: code that determines whether the tag body should be
		//       evaluated again after processing the tag
		//       should be placed here.
		//       You can use this method to create iterating tags.
		//       Called from the doAfterBody() method.
		//
		return false;
	}
}
