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
public class ShowItem implements Tag {

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

    public ShowItem() {
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

        Integer startAttrNews = -1;
        Integer endAttrNews = -1;
        Integer colspan = 1;
        Integer widthCell = 0;
        Integer heightCell = 0;
        boolean showBars = false;
        Integer charsIntroduction = 0;
        String attributesNews = "";
        String valueAttrNews = "";
        Integer rId = (Integer) ((SortedMap) item).get("NOT_id");
        String rTipotitular = (String) ((SortedMap) item).get("NOT_TIPO_TITULAR");
        String rTitular = (String) ((SortedMap) item).get("NOT_TITULAR");
        String rContenido = (String) ((SortedMap) item).get("NOT_CONTENIDO");
        rContenido=rContenido.replaceAll("\n", "<br>");
        Date rFecha = (Date) ((SortedMap) item).get("NOT_FECHA");
        String rFechatexto = new SimpleDateFormat("dd/MM/yyyy").format(rFecha);
        String rFicheroimagen = (String) ((SortedMap) item).get("NOT_FICHERO_IMAGEN");
        Integer rAnchoimagen = (Integer) ((SortedMap) item).get("NOT_ANCHO_IMAGEN");
        Integer rAltoimagen = (Integer) ((SortedMap) item).get("NOT_ALTO_IMAGEN");
        String rPosicionimagen = (String) ((SortedMap) item).get("NOT_POSICION_IMAGEN");
        String payLoad = rContenido;

        if (mode == null || !mode.equals("alone")) {
            widthCell = 100 / columns;
        } else {
            widthCell = 100;
        }
        colspan = 1;
        heightCell = 0;
        showBars = false;
        charsIntroduction = 0;

        /* Guarda el identificador del registro en el �mbito de la página para su
         * posterior gestáón fuera de la Tag
         * */
        pageContext.setAttribute("ShowItemId", rId);

        // Intenta extraer attibutos de presentación de la noticia
        startAttrNews = rContenido.indexOf("<attr-news>");
        if (startAttrNews >= 0) {
            endAttrNews = rContenido.indexOf("</attr-news>");
            if (endAttrNews > startAttrNews + 10) {
                attributesNews = rContenido.substring(startAttrNews + 11, endAttrNews).trim();
                payLoad = rContenido.substring(endAttrNews + 12);
                if (mode == null || !mode.equals("alone")) {
                    for (String attr : attributesList) {
                        if (attributesNews.indexOf("<" + attr) >= 0) {
                            startAttrNews = attributesNews.indexOf("<" + attr);
                            endAttrNews = attributesNews.indexOf("/>", startAttrNews);
                            if (endAttrNews >= 0) {
                                startAttrNews = attributesNews.indexOf("=", startAttrNews);
                                valueAttrNews = attributesNews.substring(startAttrNews + 1, endAttrNews).trim();
                                if (attr.equals("colspan")) {
                                    if ((ncol + Integer.parseInt(valueAttrNews) - 1) > columns) {
                                        for (; ncol <= columns; ncol++) {
                                            out.println("<td colspan=" + colspan + " width=" + widthCell + "% ></td>");
                                        }
                                        out.println("</tr>");
                                        ncol = 1;
                                        out.println("<tr>");
                                    }
                                    colspan = Integer.parseInt(valueAttrNews);
                                }
                                if (attr.equals("height")) {
                                    heightCell = Integer.parseInt(valueAttrNews);
                                }
                                if (attr.equals("bars")) {
                                    showBars = true;
                                }
                                if (attr.equals("charsIntroduction")) {
                                    charsIntroduction = Integer.parseInt(valueAttrNews);
                                }
                            }
                        }

                    }
                }
            }
        }

// Se calcula el porcentaje de ancho de celda
        out.println("<td colspan=" + colspan + " width=" + (widthCell * colspan) + "% valign=\"top\">");
// se muestáa la fecha de la noticia
        out.println("<p class='noticia_fecha'> &nbsp;");
        out.println(rFechatexto);
        out.println("</p>");
// se muestáa el titular
        if (rTitular != null && !rTitular.equals("")) {
            if (rTipotitular.equals("Titular")) {
                out.println("<p class='noticia_titular' >");
            } else if (rTipotitular.equals("Subtítulo")) {
                out.println("<p class='noticia_subtitulo' >");
            } else if (rTipotitular.equals("Destácado")) {
                out.println("<p class='noticia_destácado' >");
            } else {
                out.println("<p>");
            }
            if (payLoad.length() > 0) {
                out.println("<a  class=\"noticia_titular\" href=\"" + dirApplication + "/pages/NewsDisplay.jsp?getid=" + rId.toString() + "\">");
                out.println(rTitular);
                out.println("</a>");
            } else {
                out.println(rTitular);
            }
            out.println("</p>");
        }
// se muestáan los primeros n caracteres de la noticia
        int ncar = Math.min(payLoad.length(), (charsIntroduction == 0 ? lengthIntroduction : charsIntroduction));
        if (ncar > 0) {
            if ((ncar == (charsIntroduction == 0 ? lengthIntroduction : charsIntroduction)) && payLoad.indexOf(" ", ncar) >= ncar) {
                ncar = payLoad.indexOf(" ", ncar);
            } else {
                ncar = payLoad.length();
            }
            // Muestáa el tramo inicial de la noticia en la ventana principal
            if (heightCell > 0) {
                out.println("<div style='height: " + heightCell + "px;" + (showBars ? "overflow: auto;" : "") + "'>");
            }
// muestáa la imagen si se ha incluido
            out.print("<p>" + payLoad.substring(0, 1));
            if (rFicheroimagen != null && !rFicheroimagen.equals("")) {
                String fileImageName = "";
                if (rFicheroimagen.startsWith(String.format("%1$05d", rId))) {
                    fileImageName = rFicheroimagen;
                } else {
                    fileImageName = rFechatexto.substring(6, 10) +
                            rFechatexto.substring(3, 5) +
                            rFechatexto.substring(0, 2) +
                            rFicheroimagen;
                }
                out.print("<img src='" + dirHomeResources + "/imagenes/noticias/" +
                        fileImageName + "' " +
                        ((rAnchoimagen != null && rAnchoimagen != 0) ? (" width=" + rAnchoimagen.toString()) : "") +
                        ((rAltoimagen != null && rAltoimagen != 0) ? (" height=" + rAltoimagen.toString()) : "") +
                        ((rPosicionimagen != null && !rPosicionimagen.equals("")) ? (rAnchoimagen < onWidthImage ? " align=" + rPosicionimagen : (" align=absmiddle")) : (" align=center")) +
                        ">");
            }
            out.println(payLoad.substring(1, ncar) +
                    (ncar < payLoad.length() ? "<a href=\"" + dirApplication +
                    "/pages/NewsDisplay.jsp?getid=" + rId.toString() +
                    "\"> ... (contin�a)</a>" : ""));
            out.println("</p>");
            if (heightCell > 0) {
                out.println("</div>");
            }
        }
// se buscan subtitulares y destácados
        //   out.println(subNoticias(rId));

// fin de la noticia
        ncol += colspan;
        /*     if ((ncol += colspan) > columns) {
        ncol = 1;
        }
         * */
    }
}
