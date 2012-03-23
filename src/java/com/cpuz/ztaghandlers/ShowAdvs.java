/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.ztaghandlers;

import java.io.IOException;
import java.util.SortedMap;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.tagext.Tag;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class ShowAdvs implements Tag {

    private PageContext pageContext;
    private Tag parent;
    private String dirApplication;
    private String dirHomeResources;
    private Object value;
    private SortedMap[] Items;

    public ShowAdvs() {
        super();
    }

    @Override
    public void setPageContext(PageContext pc) {
        this.pageContext = pc;
    }

    @Override
    public void setParent(Tag parent) {
        this.parent = parent;
    }

    @Override
    public Tag getParent() {
        return parent;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
        this.Items = ((Result) value).getRows();
    }

    @Override
    public int doStartTag() throws JspException {
        return Tag.SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        dirApplication = (String) this.pageContext.getAttribute("dirApplication", PageContext.APPLICATION_SCOPE);
        dirHomeResources = (String) this.pageContext.getAttribute("dirHomeResources", PageContext.APPLICATION_SCOPE);

        JspWriter out = pageContext.getOut();
        for (SortedMap item : Items) {
            try {
                out.println(listAdvs(item));
            } catch (IOException e) {
                throw new JspException(e);
            }
        }
        return Tag.EVAL_PAGE;


    }

    @Override
    public void release() {
        return;
    }

    private String listAdvs(SortedMap item) {

        String out2 = "";
        Integer rId = (Integer) ((SortedMap) item).get("NOT_id");
        String rTipotitular = (String) ((SortedMap) item).get("NOT_TIPO_TITULAR");
        String rTitular = (String) ((SortedMap) item).get("NOT_TITULAR");
        String rContenido = (String) ((SortedMap) item).get("NOT_CONTENIDO");
        String rEnlace = (String) ((SortedMap) item).get("NOT_ENLACE");

        // se muestáa el titular
        if (rTitular != null && !rTitular.equals("")) {
            if (rTipotitular.equals("Titular")) {
                out2 = out2 + "<p class='noticia_titular' >";
            } else if (rTipotitular.equals("Subtitulo")) {
                out2 = out2 + "<p class='noticia_subtitulo' >";
            } else if (rTipotitular.equals("Destácado")) {
                out2 = out2 + "<p class='noticia_destácado' >";
            } else {
                out2 = out2 + "<p>";
            }
// crea el enlace html si hay ENLACE en la subnoticia

            if (rEnlace != null && rEnlace.length() > 0) {
                out2 = out2 + ("<a href=\"" + rEnlace + "\">");
            } else {
// crea el enlace html si hay contenido en la subnoticia
                if (rContenido.length() > 0) {
                    out2 = out2 + ("<a href=\"" + dirApplication + "/pages/NewsDisplay.jsp?getid=" + rId + "\">");
                }

            }
            if (rTitular.contains(":")) {
                out2 = out2 + "<strong>";
                rTitular.substring(rTitular.indexOf(":"));
                out2 = out2 + "</strong>";
                rTitular.substring(rTitular.indexOf(":"), rTitular.length());
            } else {
                out2 = out2 + rTitular;
            }
// cierra el enlace html si hay contenido en la subnoticia

            if (rContenido.length() > 0) {
                out2 = out2 + ("</a>");
            }

            out2 = out2 + "</p>";
        }
        return out2;
    }
}
