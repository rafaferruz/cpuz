package com.cpuz.taghandlers;

import java.io.IOException;
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
public class LinksHandler2 extends BodyTagSupport {

    private static final long serialVersionUID = 309L;
    /** property declaration for tag attribute: separadormenu.
     *
     */
    private java.lang.String separadormenu;
    private java.lang.String separadoritems;
    private java.lang.String mododisplay;
    private java.lang.String cadenamenu;

    public LinksHandler2() {
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

        HttpServletRequest request= (HttpServletRequest) (pageContext.getRequest());
        ResourceBundle bundle = BundleHelper.getBundle(request.getLocales());

        HttpServletResponse response = (HttpServletResponse) (pageContext.getResponse());
        HttpSession sesión = request.getSession(false);

        String contextPath = request.getContextPath();
        StringBuffer links = new StringBuffer();
        StringBuffer menuBarra = new StringBuffer();
        StringBuffer menuPaneles = new StringBuffer();
        menuBarra.append("");
        menuPaneles.append("");

        String enlacePanelMenu = "";
        int numeroPanelMenu = 0;
        String textoJavascriptMenuNavegador = "";
        String varcapasJavascriptMenuNavegador = "";

        String[] subCadenas=cadenamenu.split("\\"+separadormenu);

        for (int i = 1; i <= subCadenas.length; i++) {
            String texto = subCadenas[i-1].split(separadoritems)[0].trim();
            String enlace = "";
            try{
                enlace=subCadenas[i-1].split(separadoritems)[1].trim();
            } catch (Exception e) {
                enlace="";
            }


            if (!texto.startsWith("//")) {
                if (mododisplay.equals("columna")) {

                    links.append("<tr><td><table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr>");
                    if (!enlace.equals("")) {
                        links.append("<td width=\"20%\" bgcolor=\"#80FF80\" >&nbsp;</td>");
                        links.append("<td width=\"80%\" bgcolor=\"#80FF80\" >");
                        links.append("<div align=\"left\">");
                        if (!sesión.isNew()) {
                            enlace = response.encodeURL(enlace);
                        }
                        if (enlace.startsWith("http") || enlace.startsWith("#")) {
                            links.append(" <a href=\"" + enlace);
                        } else {
                            links.append(" <a href=\"" + contextPath + "/" + enlace);
                        }

//                        links.append(" <a href=\"" + enlace);
                        links.append("\" class=\"menus_2\">");
                        links.append(texto);
                        links.append("</a>");
                    } else {
                        links.append("<td bgcolor=\"#10C410\" class=\"menus\">");
                        links.append("<div align=\"left\">");
//                    links.append(" class=\"menus_2\">");
                        links.append("&nbsp;&nbsp;" + texto);
                    }
                    links.append("</div></td></tr></table></td></tr>");

                } else if (mododisplay.equals("pie")) {
                    if (!sesión.isNew()) {
                        enlace = response.encodeURL(enlace);
                    }

                    if (enlace.startsWith("http") || enlace.startsWith("#")) {
                        links.append(" <a href=\"" + enlace);
                    } else {
                        links.append(" <a href=\"" + contextPath + "/" + enlace);
                    }

                    links.append("\">");
                    links.append(texto);
                    links.append("</a>\n");
                    if (i < subCadenas.length) {
                        links.append(" | ");
                    }

                /*
                links.append("<a href=\"");
                links.append(contextPath);
                links.append("/docs/parameters/input.jsp\">");
                links.append(bundle.getString("parameters"));
                links.append("</a>\n");
                links.append(separadormenu);
                 */
                } else if (mododisplay.equals("navegador")) {
                    if (!enlace.equals("")) {

                        if (!enlacePanelMenu.equals("")) {
                            menuPaneles.append("\n");
                        }
                        enlacePanelMenu = enlace;
//                        menuPaneles.append("<a href=\"");

                        if (!sesión.isNew()) {
                            enlace = response.encodeURL(enlace);
                        }
                        if (enlace.startsWith("http") || enlace.startsWith("#")) {
                            menuPaneles.append("<tr><td class=\"enlacecelda\"> <a href=\"" + enlace);
                        } else {
                            menuPaneles.append("<tr><td class=\"enlacecelda\"> <a href=\"" + contextPath + "/" + enlace);
                        }

                        menuPaneles.append("\" " + "class=enlacesbarra>");

                        menuPaneles.append("&nbsp;&nbsp;" + texto + "&nbsp;&nbsp;&nbsp;&nbsp;");
                        menuPaneles.append("</a></td></tr>\n");
                    } else {
                        // Se crea la barra principal de navegación

                        if (menuPaneles.length() != 0) {
                            menuPaneles.append("</table></div>\n");
                        }
                        if (!varcapasJavascriptMenuNavegador.equals("")) {
                            varcapasJavascriptMenuNavegador = varcapasJavascriptMenuNavegador + ",";
                        }
                        numeroPanelMenu++;
                        menuBarra.append("<td><div id=ancla" + Integer.toString(numeroPanelMenu) +
                                " class=ancla ><a href=\"#\" " +
                                " onmouseover=\"MuestáaPanelMenuColoca('" + texto + "')\" " +
                                " onmouseout=\"OcultaPanelMenuRetarda('" + texto + "')\">&nbsp;&nbsp;&nbsp;&nbsp;" +
                                texto + "&nbsp;&nbsp;&nbsp;&nbsp;</a></div></td>\n");
                        menuPaneles.append("<div id=\"" +
                                texto + "\" class=\"recuadros\"  " +
                                " onmouseover=\"MuestáaPanelMenuRetarda('" + texto + "')\" " +
                                " onmouseout=\"OcultaPanelMenuRetarda('" + texto + "')\" " +
                                " >\n" +
                                "<table class=\"recuadrotabla\">" +
                                "\n");
                        enlacePanelMenu = "";
                        // Prepara la funcion Javascript PosicionPanelMenu que se monta un poco más abajo
                        textoJavascriptMenuNavegador = textoJavascriptMenuNavegador +
                                " posx= xOffsetLeft(\"ancla" + Integer.toString(numeroPanelMenu) + "\"\n" +
                                " posy= xOffsetTop (\"ancla" + Integer.toString(numeroPanelMenu) + "\"\n" +
                                " xMoveTo('" + texto + "',posx,posy+20)";
                        varcapasJavascriptMenuNavegador = varcapasJavascriptMenuNavegador +
                                "\"" + texto + "\"";


                    }
                }
            }
        }
        if (mododisplay.equals("navegador")) {
            if (!enlacePanelMenu.equals("")) {
                menuPaneles.append("\n");
            }
            if (menuPaneles.length() != 0) {
                menuPaneles.append("</table></div>\n");
            }
// insertamos c�digo HTML generado
//            links.append("<table border=\"0\" cellspacing=\"3\" cellpadding=\"5\" class=\"menus\" align=\"center\" bgcolor=#FFFF00 >\n ");
//            links.append("<tr>\n ");
            links.append(menuBarra);
//            links.append("</tr>\n ");
//            links.append("</table>\n ");
            links.append("<tr><td>\n ");
            links.append(menuPaneles);
            links.append("</td></tr>\n ");

            // insertamos c�digo JavaScript
            links.append("<script type='text/javascript'>\n");
            links.append("var empezarMenuNavegador = false\n");
            links.append("var anclasMenuNavegador = new Array (");
            for (int i = 1; i <= numeroPanelMenu; i++) {
                links.append("\"ancla" + Integer.toString(i) + "\"");
                if (i < numeroPanelMenu) {
                    links.append(",");
                }
            }
            links.append(")\n");
            links.append("\n");
            links.append("var capasMenuNavegador = new Array(" + varcapasJavascriptMenuNavegador + ")\n");
            links.append("var retardoMenuNavegador\n");
            links.append("var ocultarMenuNavegador\n");
            links.append("function MuestáaPanelMenu(capa){\n");
            links.append("xShow(capa);\n");
            links.append("}\n");
            links.append("function OcultaPanelMenu(capa){\n");
            links.append("xHide(capa);\n");
            links.append("}\n");
            links.append("function PosicionaPanelMenu(){\n");

//            links.append(textoJavascriptMenuNavegador);
            links.append("for (i=0;i<capasMenuNavegador.length;i++){\n");
//            links.append("document.form_login.userlogin.value=anclasMenuNavegador[i]\n");
            links.append("posx= xOffsetLeft(anclasMenuNavegador[i])\n");
            links.append("if (posx>1000) posx=1000;\n");
            links.append("posy= xOffsetTop(anclasMenuNavegador[i])\n");
            links.append("xMoveTo(capasMenuNavegador[i],posx,posy+20)\n");
            links.append("} \n");

            links.append("}\n");
//            links.append("window.onload = function() {\n");
//            links.append("PosicionaPanelMenu()\n");
//            links.append("empezarMenuNavegador = true\n");
//            links.append("}\n");

            links.append("window.onresize = function() {\n");
            links.append("PosicionaPanelMenu()\n");
            links.append("}\n");

            links.append("function MuestáaPanelMenuColoca(capa){\n");
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

            links.append("function MuestáaPanelMenuRetarda(ind){\n");
            links.append("if (empezarMenuNavegador){\n");
            links.append("clearTimeout(retardoMenuNavegador)\n");
            links.append("}\n");
            links.append("} \n");

            links.append("</script> \n");
        }

        try {
            JspWriter out = pageContext.getOut();
            out.print(links.toString());
        } catch (java.io.IOException ex) {
            // do nothing
        }
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
    public boolean shouldEvaluateRestáfPageAfterEndTag() {
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

        if (shouldEvaluateRestáfPageAfterEndTag()) {
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
