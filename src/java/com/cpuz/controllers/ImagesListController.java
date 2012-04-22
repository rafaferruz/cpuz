package com.cpuz.controllers;

import com.cpuz.domain.Image;
import com.cpuz.domain.UserType;
import com.cpuz.service.ImagesService;
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
public class ImagesListController extends GenericListController {

    private static final long serialVersionUID = 302L;
    protected ImagesService nvm = new ImagesService();
/*    protected String jspFileList = "views/imagesList.jsp";
    protected String jspFileEdit = "views/imagesEdit.jsp";
    protected String jspFileSelec = "views/imagesSelec.jsp";
    protected String attrNameListRecs = "ImagesListNews";
*/
    public ImagesListController() {
        super.jspFileList = "views/imagesList.jsp";
        super.jspFileEdit = "views/imagesEdit.jsp";
        super.jspFileSelec = "views/selecConditions.jsp";
        super.attrNameListRecs = "ImagesListNews";
//        super.nvm = new ImagesService();
    }

    protected synchronized void doListSelec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String conditions = " img_date >= '" + request.getParameter("anioDesde") + "-" +
                request.getParameter("mesDesde") + "-" +
                request.getParameter("diaDesde") + "' AND " +
                " img_date <= '" + request.getParameter("anioHasta") + "-" +
                request.getParameter("mesHasta") + "-" +
                request.getParameter("diaHasta") + "' ";
        if (request.getParameter("buscartexto") != null && !request.getParameter("buscartexto").equals("")) {
            String[] words = request.getParameter("buscartexto").split(" ");
            for (int i = 0; i < words.length;) {
                if (i == 0) {
                    conditions += " AND (img_user_reference LIKE '%" + words[i] + "%' OR " +
                            " img_filename LIKE '%" + words[i] + "%'  ";
                } else {
                    conditions += " OR img_user_reference LIKE '%" + words[i] + "%' OR " +
                            " img_filename LIKE '%" + words[i] + "%'  ";
                }
                if (++i == words.length) {
                    conditions += " )";
                }
            }
        }
        doList(request, response, conditions);
    }

        protected synchronized void doNew(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // ImagesEdit.jsp necesita saber si la vista a mostrar es para crear
        // una noticia nueva (new) o para editar una noticia ya existente (edit)
        request.setAttribute("runaction", "new");

        /* Crea un Bean de Image con valores por defecto para pasarla, a trav�s de
         * un atributo de solicitud, al generador de la vista ImagesEdit.jsp
         * */
        request.setAttribute("recsBean", new Image());
// Pasa el control al jsp que construye la vista
        doDispatcher(request, response, jspFileEdit);

    }


    protected void doSelecNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("selecTitle", "newsselec");
        request.setAttribute("selecHelpLines", "newsselechelp");
        request.setAttribute("selecAction", "ImagesList");
        request.setAttribute("selecRunAction", "list_selec");

// Pasa el control al jsp que construye la vista
        doDispatcher(request, response, jspFileSelec);
    }

    @Override
    protected boolean checkIdNew(Object n, String idNew) {
        /* Eliminar marcas de inicio y fin de comentarios y adaptar el m�todo a la
         * clase extendida==Integer.parseInt(idNew) && nvm.deleteNews(  n)
         */
        if ((((Image) n).getId() == Integer.parseInt(idNew)) && nvm.deleteNews((Image) n) > 0) {
            return true;
        }
        return false;

    }

    @Override
    protected void doIssueNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

// Pasa el control al jsp que construye la vista
        doDispatcher(request, response, "docs/newsDisplay.jsp?getid=" + request.getParameter("identificador"));
    }

    @Override
    protected List getRecord(HttpServletRequest request, HttpServletResponse response, String idNew) {
        List recs = new ArrayList();
        // Se hace la consulta a la base de datos de noticias.
// Requirement codes: E1-1, E5-1
        if (request.getSession().getAttribute("userCategory") != null && (Integer) request.getSession().getAttribute("userCategory") == 2) {
            recs = (List) nvm.getNewsRecords(UserType.ADMIN, " img_id = " + idNew + " ");
        } else {
            recs = (List) nvm.getNewsRecords(UserType.ANONYMOUS, " img_id = " + idNew + " ");
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
