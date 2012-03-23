/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.controllers;

import com.cpuz.domain.InfoBlock;
import com.cpuz.domain.UserType;
import com.cpuz.model.InfoBlocksModel;
import com.cpuz.domain.UserType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class InfoBlocksListController extends GenericListController {

    private static final long serialVersionUID = 302L;
    protected InfoBlocksModel nvm = new InfoBlocksModel();
/*    protected String jspFileList = "views/infoBlocksList.jsp";
    protected String jspFileEdit = "views/infoBlocksEdit.jsp";
    protected String jspFileSelec = "views/infoBlocksSelec.jsp";
    protected String attrNameListRecs = "InfoBlocksListNews";
*/
    public InfoBlocksListController() {
        super.jspFileList = "views/infoBlocksList.jsp";
        super.jspFileEdit = "views/infoBlocksEdit.jsp";
        super.jspFileSelec = "views/selecConditions.jsp";
        super.attrNameListRecs = "InfoBlocksListNews";
//        super.nvm = new InfoBlocksModel();
    }

    protected synchronized void doListSelec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String conditions = " inb_date >= '" + request.getParameter("anioDesde") + "-" +
                request.getParameter("mesDesde") + "-" +
                request.getParameter("diaDesde") + "' AND " +
                " inb_date <= '" + request.getParameter("anioHasta") + "-" +
                request.getParameter("mesHasta") + "-" +
                request.getParameter("diaHasta") + "' ";
        if (request.getParameter("buscartexto") != null && !request.getParameter("buscartexto").equals("")) {
            String[] words = request.getParameter("buscartexto").split(" ");
            for (int i = 0; i < words.length;) {
                if (i == 0) {
                    conditions += " AND (inb_header LIKE '%" + words[i] + "%' OR " +
                            " inb_body LIKE '%" + words[i] + "%'  ";
                } else {
                    conditions += " OR inb_header LIKE '%" + words[i] + "%' OR " +
                            " inb_body LIKE '%" + words[i] + "%'  ";
                }
                if (++i == words.length) {
                    conditions += " )";
                }
            }
        }
        doList(request, response, conditions);
    }

        protected synchronized void doNew(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // InfoBlocksEdit.jsp necesita saber si la vista a mostrar es para crear
        // una noticia nueva (new) o para editar una noticia ya existente (edit)
        request.setAttribute("runaction", "new");

        /* Crea un Bean de InfoBlock con valores por defecto para pasarla, a trav�s de
         * un atributo de solicitud, al generador de la vista InfoBlocksEdit.jsp
         * */
        request.setAttribute("recsBean", new InfoBlock());
// Pasa el control al jsp que construye la vista
        doDispatcher(request, response, jspFileEdit);

    }


    protected void doSelecNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("selecTitle", "newsselec");
        request.setAttribute("selecHelpLines", "newsselechelp");
        request.setAttribute("selecAction", "InfoBlocksList");
        request.setAttribute("selecRunAction", "list_selec");

// Pasa el control al jsp que construye la vista
        doDispatcher(request, response, jspFileSelec);
    }

    @Override
    protected boolean checkIdNew(Object n, String idNew) {
        /* Eliminar marcas de inicio y fin de comentarios y adaptar el m�todo a la
         * clase extendida==Integer.parseInt(idNew) && nvm.deleteNews(  n)
         */
        if ((((InfoBlock) n).getId() == Integer.parseInt(idNew)) && nvm.deleteNews((InfoBlock) n) > 0) {
            return true;
        }
        return false;

    }

    @Override
    protected void doIssueNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

// Pasa el control al jsp que construye la vista
        doDispatcher(request, response, "docs/NewsDisplay.jsp?getid=" + request.getParameter("identificador"));
    }

    @Override
    protected List getRecord(HttpServletRequest request, HttpServletResponse response, String idNew) {
        List recs = new ArrayList();
        // Se hace la consulta a la base de datos de noticias.
// Requirement codes: E1-1, E5-1
        if (request.getSession().getAttribute("userCategory") != null && (Integer) request.getSession().getAttribute("userCategory") == 2) {
            recs = (List) nvm.getNewsRecords(UserType.ADMIN, " inb_id = " + idNew + " ");
        } else {
            recs = (List) nvm.getNewsRecords(UserType.ANONYMOUS, " inb_id = " + idNew + " ");
        }
        return recs;
    }

    @Override
    protected List getRecords(HttpServletRequest request, HttpServletResponse response, String selecConditions) {
        List recs = new ArrayList();
        // Se hace la consulta a la base de datos de noticias.
// Requirement codes: E1-1, E5-1
        if (request.getSession().getAttribute("userCategory") != null && (Integer) request.getSession().getAttribute("userCategory") == 2) {
            recs = (List) nvm.getNewsRecords(UserType.ADMIN, selecConditions);
        } else {
            recs = (List) nvm.getNewsRecords(UserType.ANONYMOUS, selecConditions);
        }
        return recs;
    }

    @Override
    protected List getDetails(HttpServletRequest request, HttpServletResponse response, String idNew) {
        List recs = new ArrayList();
        // Se hace la consulta a la base de datos de noticias
        recs = nvm.getNewsDetails(idNew);
        return recs;
    }
}
