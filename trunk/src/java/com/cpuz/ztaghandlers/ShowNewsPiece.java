/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.ztaghandlers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedMap;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class ShowNewsPiece implements Tag {

    private static final String[] attributesList = {"colspan", "height", "bars", "charsIntroduction"};
    private PageContext pageContext;
    private Tag parent;
    private String dirApplication;
    private String dirHomeResources;
    private Integer columns;
    private Integer lengthIntroduction;
    private String mode;
    private int ncol;
    private Integer onWidthImage;

    public ShowNewsPiece() {
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

    @Override
    public int doStartTag() throws JspException {
        return Tag.SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        ShowNews itemsTag = (ShowNews) getParent();
        Object item = itemsTag.getItem();
        ncol = itemsTag.getNcol();
        columns = itemsTag.getColumns();
        lengthIntroduction = itemsTag.getLengthintroduction();
        mode = itemsTag.getMode();
        dirApplication = (String) this.pageContext.getAttribute("dirApplication", PageContext.APPLICATION_SCOPE);
        dirHomeResources = (String) this.pageContext.getAttribute("dirHomeResources", PageContext.APPLICATION_SCOPE);
        onWidthImage = itemsTag.getOnWidthImage();

        JspWriter out = pageContext.getOut();
        try {
            listItem(item, out);
            itemsTag.setNcol(ncol);
        } catch (IOException e) {
            throw new JspException(e);
        }
        return Tag.EVAL_PAGE;


    }

    @Override
    public void release() {
        return;
    }

    private void listItem(Object item, JspWriter out) throws IOException {

        Integer colspan = 1;
        Integer widthCell = 0;
        Integer rId = (Integer) ((SortedMap) item).get("npi_id");
        String rSection = (String) ((SortedMap) item).get("npi_section_id");
        String rDescription = (String) ((SortedMap) item).get("npi_description");
        Date rFecha = (Date) ((SortedMap) item).get("npi_date");
        String rFechatexto = new SimpleDateFormat("dd/MM/yyyy").format(rFecha);

        if (mode == null || !mode.equals("alone")) {
            widthCell = 100 / columns;
        } else {
            widthCell = 100;
        }
        colspan = 1;

        /* Guarda el identificador del registro en el �mbito de la página para su
         * posterior gestáón fuera de la Tag
         * */
        pageContext.setAttribute("ShowItemId", rId);


// Se calcula el porcentaje de ancho de celda
        out.println("<td colspan=" + colspan + " width=" + (widthCell * colspan) + "% valign=\"top\">");
// se muestáa la fecha de la noticia
        out.println("<p class='noticia_fecha'> &nbsp;");
        out.println(rFechatexto);
        out.println("</p>");

// fin de la noticia
        ncol += colspan;
    }
}
