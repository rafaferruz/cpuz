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
	/** property declaration for tag attribute: menuSeparator.
	 *
	 */
	private java.lang.String menuSeparator;
	private java.lang.String menuItemSeparator;
	private java.lang.String displayMode;
	private java.lang.String menuChain;
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
	 * Generate the links based on the menuSeparator attribute passed
	 * in to the tag.
	 */
	public void otherDoStartTagOperations() {

		if (displayMode.equals("null")) {
			return;
		}

		HttpServletRequest request = (HttpServletRequest) (pageContext.getRequest());
		HttpServletResponse response = (HttpServletResponse) (pageContext.getResponse());
		HttpSession session = request.getSession(false);

		StringBuffer links = new StringBuffer("");
		StringBuffer menuBar = new StringBuffer("");
		StringBuffer menuPanels = new StringBuffer("");

		int menuPanelNumber = 0;
		String varJavascriptMenuNavigatorLayers = "";

		// Generamos una lista con cada línea de definición de menú
		List<String> menuDefs = Arrays.asList(menuChain.split("\\" + menuSeparator));
		Integer needCategory = 0;
		if (session.getAttribute("userCategory") == null) {
			session.setAttribute("userCategory", 0);
		}

		for (String menuDef : menuDefs) {
			List<String> slideDefs = Arrays.asList(menuDef.split("\\" + menuItemSeparator));
			// Recuperamos el texto de la línea de menú
			String text = slideDefs.get(0).replaceAll("\n", "").trim();
			String linkTo = "";
			// Recuperamos la dirección de linkTo
			if (slideDefs.size() > 1) {
				linkTo = slideDefs.get(1).trim();
			}
			// Recuperamos el nivel de categoría del usuario necesario para mostrar la línea
			needCategory = 0;
			if (slideDefs.size() > 2) {
				needCategory = Integer.parseInt(slideDefs.get(2).trim());
			}

			if (!text.startsWith("//")) { 	// Si se trata de una línea de comentario 
				// no se trata y se toma la siguiente línea
				// de definición de menú

				if ((Integer) session.getAttribute("userCategory") >= needCategory) {
					switch (displayMode) {
						case "column":
							links.append("<tr><td><table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr>");
							if (!linkTo.equals("")) {
								links.append(getTd20());
								links.append(getTd80Start());
								links.append(getDivLeftStart());
								menuPanels.append(getLinkElement(request, response, linkTo, "class=\"menu_2\"", text));
							} else {
								links.append(getTdMenusStart());
								links.append(getDivLeftStart());
								links.append("&nbsp;&nbsp;" + text);
							}
							links.append("</div></td></tr></table></td></tr>");
							break;
						case "foot":
							menuPanels.append(getLinkElement(request, response, linkTo, "class=\"menu_2\"", text));
							if (menuDefs.indexOf(menuDef) < menuDefs.size() - 1) {
								links.append(" | ");
							}
							break;
						case "navigator":
							if (!linkTo.equals("")) {
								menuPanels.append(getTrLinkMenu(request, response, linkTo, text));
							} else {
								// Se crea la barra principal de navegación
								if (menuPanels.length() != 0) {
									menuPanels.append("</table>\n</div>\n");
								} else {
									// Es la primera línea del menú
									links.append("\n<table class=\"" + class_navigationbar + "\" align=\"" + halign + "\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
									links.append("\n<tr>");
								}
								if (!varJavascriptMenuNavigatorLayers.equals("")) {
									varJavascriptMenuNavigatorLayers = varJavascriptMenuNavigatorLayers + ",";
								}
								menuBar.append(getTextBar(menuPanelNumber, text));
								menuPanelNumber++;
								menuPanels.append(getTextPanel(text));
								varJavascriptMenuNavigatorLayers = varJavascriptMenuNavigatorLayers
										+ "\"" + text + "\"";
							}
							break;
					}
				}
			}
		}
		if (displayMode.equals("navigator")) {
// Se envía la barra de navegación
			links.append(menuBar);
			links.append("\n</tr>");
			links.append("\n<tr>\n<td>");

// Se envían los paneles de menús hijos
			if (menuPanels.length() != 0) {
				menuPanels.append("\n</table>\n</div>\n");
				links.append(menuPanels);
				links.append("\n</td>\n</tr>\n");
			}
// Se cierra la tabla que forma el menú de navegación
			links.append("\n</table>");

// insertamos código HTML generado

			// insertamos código JavaScript
			links = getJavaScriptCode(links, menuPanelNumber, varJavascriptMenuNavigatorLayers);

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

	private String getLinkElement(HttpServletRequest request, HttpServletResponse response, String linkTo, String cssClass, String text) {
		if (!request.getSession().isNew()) {
			linkTo = response.encodeURL(linkTo);
		}
		if (linkTo.startsWith("http") || linkTo.startsWith("#")) {
			return " <a href=\"" + linkTo + "\" " + cssClass + ">" + text + "</a>";
		} else {
			return " <a href=\"" + request.getContextPath() + "/" + linkTo + "\" " + cssClass + ">" + text + "</a>";
		}
	}

	private String getDivLeftStart() {
		return "<div align=\"left\">";
	}

	private String getTrLinkMenu(HttpServletRequest request, HttpServletResponse response, String linkTo, String text) {
		return "<tr>\n<td class=\"" + class_child + "\">\n "
				+ getLinkElement(request, response, linkTo, " class=\"" + class_parent + "\"", "&nbsp;&nbsp;" + text + "&nbsp;&nbsp;&nbsp;&nbsp;")
				+ "\n</td>\n</tr>\n";

	}

	private String getTextBar(int menuPanelNumber, String text) {
		return "\n<td>\n<div id=\"anchor" + menuPanelNumber + "\""
				+ " class=\"" + class_boxparent + "\" >\n<a href=\"#\" "
				+ " onmouseover=\"MenuPanelShowPositioning('" + text + "')\" "
				+ " onmouseout=\"MenuPanelHideDelaying('" + text + "')\">&nbsp;&nbsp;&nbsp;&nbsp;"
				+ text + "&nbsp;&nbsp;&nbsp;&nbsp;\n</a>\n</div>\n</td>\n";
	}

	private String getTextPanel(String text) {
		return "\n<div id=\""
				+ text + "\" class=\"" + class_boxchild + "\"  "
				+ " onmouseover=\"MenuPanelShowPositioning('" + text + "')\" "
				+ " onmouseout=\"MenuPanelHideDelaying('" + text + "')\" "
				+ " >\n"
				+ "\n<table class=\"" + class_childtable + "\">"
				+ "\n";
	}

	private StringBuffer getJavaScriptCode(StringBuffer links, int menuPanelNumber, String varJavascriptMenuNavigatorLayers) {
		links.append("\n\n<script type='text/javascript'>\n");
		links.append("var menuNavigatorStart = false\n");
		links.append("var menuNavigatorAnchors = new Array (");
		for (int i = 1; i <= menuPanelNumber; i++) {
			links.append("\"anchor" + i + "\"");
			if (i < menuPanelNumber) {
				links.append(",");
			}
		}
		links.append(")\n");
		links.append("\n");
		links.append("var menuNavigatorLayers = new Array(").append(varJavascriptMenuNavigatorLayers).append(")\n");
		links.append("var menuNavigatorDelay\n");
		links.append("var menuNavigatorHide\n");

		links.append("function MenuPanelShow(layer){\n");
			links.append("xShow(layer);\n");
		links.append("}\n");

		links.append("function MenuPanelHide(layer){\n");
			links.append("xHide(layer);\n");
		links.append("}\n");

		links.append("function MenuPanelPositioning(){\n");

			links.append("for (i=0;i<menuNavigatorLayers.length;i++){\n");
				links.append("posx= xOffsetLeft(menuNavigatorAnchors[i])\n");
				links.append("if (posx>1050) posx=1050;\n");
				links.append("posy= xOffsetTop(menuNavigatorAnchors[i])\n");
				links.append("xMoveTo(menuNavigatorLayers[i],posx,posy+30)\n");
			links.append("} \n");
		links.append("}\n");

		links.append("MenuPanelPositioning()\n");
		links.append("menuNavigatorStart = true\n");
		links.append("window.onresize = function() {\n");
			links.append("MenuPanelPositioning()\n");
		links.append("}\n");

		links.append("function MenuPanelShowPositioning(layer){\n");
			links.append("if (menuNavigatorStart){\n");
				links.append("for (i=0;i<menuNavigatorLayers.length;i++){\n");
					links.append("if (menuNavigatorLayers[i] != layer) xHide(menuNavigatorLayers[i])\n");
				links.append("}\n");
				links.append("clearTimeout(menuNavigatorDelay)\n");
				links.append("xShow(layer)\n");
			links.append("}\n");
		links.append("}\n");

		links.append("function MenuPanelHideDelaying(layer){\n");
			links.append("if (menuNavigatorStart){\n");
				links.append("menuNavigatorHide =layer\n");
				links.append("clearTimeout(menuNavigatorDelay)\n");
				links.append("menuNavigatorDelay = setTimeout(\"xHide('\" + menuNavigatorHide + \"')\",1000)\n");
			links.append("}\n");
		links.append("}\n");

		links.append("function MenuPanelShowDelaying(ind){\n");
			links.append("if (menuNavigatorStart){\n");
				links.append("clearTimeout(menuNavigatorDelay)\n");
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

	public String getDisplayMode() {
		return displayMode;
	}

	public void setDisplayMode(String displayMode) {
		this.displayMode = displayMode;
	}

	public String getMenuChain() {
		return menuChain;
	}

	public void setMenuChain(String menuChain) {
		this.menuChain = menuChain;
	}

	public String getMenuItemSeparator() {
		return menuItemSeparator;
	}

	public void setMenuItemSeparator(String menuItemSeparator) {
		this.menuItemSeparator = menuItemSeparator;
	}

	public String getMenuSeparator() {
		return menuSeparator;
	}

	public void setMenuSeparator(String menuSeparator) {
		this.menuSeparator = menuSeparator;
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
