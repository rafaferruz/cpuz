/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.controllers;

import com.cpuz.domain.NewsComposition;
import com.cpuz.domain.NewsPiece;
import com.cpuz.domain.Section;
import com.cpuz.domain.UserType;
import com.cpuz.service.NewsCompositionsService;
import com.cpuz.service.NewsPiecesService;
import com.cpuz.service.SectionsService;
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
public class NewsPiecesListController extends GenericListController {

    private static final long serialVersionUID = 302L;
    protected NewsPiecesService nvm = new NewsPiecesService();
    NewsCompositionsService compvm = new NewsCompositionsService();

    /*    protected String jspFileList = "views/NewsPiecesList.jsp";
    protected String jspFileEdit = "views/NewsPiecesEdit.jsp";
    protected String jspFileSelec = "views/NewsPiecesSelec.jsp";
    protected String attrNameListRecs = "NewsPiecesListNews";
     */
    public NewsPiecesListController() {
        super.jspFileList = "views/NewsPiecesList.jsp";
        super.jspFileEdit = "views/NewsPiecesEdit.jsp";
        super.jspFileSelec = "views/SelecConditions.jsp";
        super.attrNameListRecs = "NewsPiecesListNews";
//        super.nvm = new NewsPiecesService();
    }

    protected synchronized void doListSelec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String conditions = " npi_date >= '" + request.getParameter("anioDesde") + "-"
                + request.getParameter("mesDesde") + "-"
                + request.getParameter("diaDesde") + "' AND "
                + " npi_date <= '" + request.getParameter("anioHasta") + "-"
                + request.getParameter("mesHasta") + "-"
                + request.getParameter("diaHasta") + "' ";
        if (request.getParameter("buscartexto") != null && !request.getParameter("buscartexto").equals("")) {
            String[] words = request.getParameter("buscartexto").split(" ");
            for (int i = 0; i < words.length;) {
                if (i == 0) {
                    conditions += " AND (npi_section LIKE '%" + words[i] + "%' OR "
                            + " npi_description LIKE '%" + words[i] + "%'  ";
                } else {
                    conditions += " OR npi_section LIKE '%" + words[i] + "%' OR "
                            + " npi_description LIKE '%" + words[i] + "%'  ";
                }
                if (++i == words.length) {
                    conditions += " )";
                }
            }
        }
        doList(request, response, conditions);
    }

    protected synchronized void doNew(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // NewsPiecesEdit.jsp necesita saber si la vista a mostrar es para crear
        // una noticia nueva (new) o para editar una noticia ya existente (edit)
        request.setAttribute("runaction", "new");

        /* Crea un Bean de NewsPiece con valores por defecto para pasarla, a trav�s de
         * un atributo de solicitud, al generador de la vista NewsPiecesEdit.jsp
         * */
        request.setAttribute("recsBean", new NewsPiece());

// Pasa una lista de Secciones
        request.getSession().setAttribute("recSections", getSections());

// Pasa el control al jsp que construye la vista
        doDispatcher(request, response, jspFileEdit);

    }

    protected synchronized void doEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // NoticiasEdit.jsp necesita saber si la vista a mostrar es para crear
        // una noticia nueva (new) o para editar una noticia ya existente (edit)
        request.setAttribute("runaction", "edit");

        if (request.getParameter("identificador") != null) {
            /* Busca un Bean de Noticia con el identificador recibido de la vista, lo pasa
            como attributo de solicitud al generador de la vista NoticiasEdit.jsp
             * */
            List records = getRecord(request, response, request.getParameter("identificador"));
            for (Object record : records) {
//                if (not.getId() == Integer.parseInt(request.getParameter("identificador"))) {
                request.setAttribute("recBean", record);
// Pasa una lista de Secciones
                request.getSession().setAttribute("recSections", getSections());

// Crea la lista de Composición de la News y la pasa a la vista
                NewsCompositionsService compvm = new NewsCompositionsService();
                List<NewsComposition> compositionList = compvm.getNewsRecords(UserType.ANONYMOUS, " nco_npi_id = " + request.getParameter("identificador") + " ");
                request.getSession().setAttribute("recComposition", compositionList);

// Pasa el control al jsp que construye la vista
                doDispatcher(request, response, jspFileEdit);
                break;
//                }
            }

        }
    }

    protected void doSelecNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("selecTitle", "newsselec");
        request.setAttribute("selecHelpLines", "newsselechelp");
        request.setAttribute("selecAction", "NewsPiecesList");
        request.setAttribute("selecRunAction", "list_selec");

// Pasa el control al jsp que construye la vista
        doDispatcher(request, response, jspFileSelec);
    }

    @Override
    protected synchronized void doDeleteNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("runaction", "list");

        List recs = (List) request.getSession().getAttribute(attrNameListRecs);

        if (request.getParameter("selec1") != null) {
            String[] toDelete = request.getParameterValues("selec1");
            for (String idNew : toDelete) {
                for (Object n : recs) {
                    if (checkIdNew(n, idNew)) {
                        recs.remove(n);
                        NewsCompositionsService compvm = new NewsCompositionsService();
                        compvm.deleteNews(" WHERE nco_npi_id = " + idNew);
                        break;
                    }
                }
            }
            request.getSession().setAttribute(attrNameListRecs, recs);
            request.setAttribute("recList", recs);
            request.setAttribute("recCount", recs.size());
            request.setAttribute("recStart", (Integer) Math.max(1, (Integer) Math.min((Integer) request.getAttribute("recStart"), recs.size())));
// Pasa el control al jsp que construye la vista
            doDispatcher(request, response, jspFileList);
        }
    }

    @Override
    protected void doDuplicateNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Pasa el control al jsp que construye la vista.
        Usado para popular registros en la base de datos en fase de pruebas
        por el desarrollador */
        request.setAttribute("runaction", "list");

        List recs = (List) request.getSession().getAttribute(attrNameListRecs);

        if (request.getParameter("selec1") != null) {
            String[] toDuplicate = request.getParameterValues("selec1");
            for (String idNew : toDuplicate) {
                List<NewsPiece> npi = nvm.getNewsRecords(UserType.ADMIN, " npi_id = " + idNew);
                if (npi != null && npi.size() == 1) {
                    npi.get(0).setId(0);
                    nvm.setNewRecord(npi.get(0));
                    Integer  newNpiId=npi.get(0).getId();
                    List<NewsPiece> newNpi=nvm.getNewsRecords(UserType.ADMIN, " npi_id = "+newNpiId);
                    recs.add(newNpi.get(0));
                    Integer newIdNew=newNpi.get(0).getId();
                    List<NewsComposition> ncomp = compvm.getNewsRecords(UserType.ADMIN, " nco_npi_id = " + idNew);
                    if (ncomp != null) {
                        for (NewsComposition nc : ncomp) {
                            nc.setIdNpi(newIdNew);
                            nc.setId(null);
                            compvm.setNewRecord(nc);
                        }
                    }
                }
            }
            request.getSession().setAttribute(attrNameListRecs, recs);
            request.setAttribute("recList", recs);
            request.setAttribute("recCount", recs.size());
            request.setAttribute("recStart", (Integer) Math.max(1, (Integer) Math.min((Integer) request.getAttribute("recStart"), recs.size())));
// Pasa el control al jsp que construye la vista
            doDispatcher(request, response, jspFileList);
        }
    }

    @Override
    protected boolean checkIdNew(Object n, String idNew) {
        /* Eliminar marcas de inicio y fin de comentarios y adaptar el m�todo a la
         * clase extendida==Integer.parseInt(idNew) && nvm.deleteNews(  n)
         */
        if ((((NewsPiece) n).getId() == Integer.parseInt(idNew)) && nvm.deleteNews((NewsPiece) n) > 0) {
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
            recs = (List) nvm.getNewsRecords(UserType.ADMIN, " npi_id = " + idNew + " ");
        } else {
            recs = (List) nvm.getNewsRecords(UserType.ANONYMOUS, " npi_id = " + idNew + " ");
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

    private List getSections() {

        SectionsService estám = new SectionsService();
        List<Section> sec = estám.getRecords();
        return sec;
    }
}
