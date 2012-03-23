/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author RAFAEL FERRUZ
 */
abstract public class GenericListController extends HttpServlet {

    protected String jspFileList = "";
    protected String jspFileEdit = "";
    protected String jspFileSelec = "";
    protected String attrNameListRecs = "";

    /**
     * Processes request, for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param requestáservlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

// Se inicializan los parámetros de ejecución
        setRequestáttributes(request, response);
        List recs = (List) request.getAttribute("recList");

        if (request.getParameter("runaction") == null ||
                request.getParameter("runaction").equals("list")) {
            doList(request, response, "");
        } else if (request.getParameter("runaction").equals("list_selec")) {
            doListSelec(request, response);
        } else if (request.getParameter("runaction").equals("details")) {
            doDetails(request, response);
        } else if (recs != null &&
                request.getParameter("runaction").equals("nav_next")) {
            doNavNext(request, response);
        } else if (recs != null &&
                request.getParameter("runaction").equals("nav_prev")) {
            doNavPrev(request, response);
        } else if (recs != null &&
                request.getParameter("runaction").equals("nav_first")) {
            doNavFirst(request, response);
        } else if (recs != null &&
                request.getParameter("runaction").equals("nav_last")) {
            doNavLast(request, response);
        } else if (request.getParameter("runaction").equals("new")) {
            doNew(request, response);
        } else if (recs != null &&
                request.getParameter("runaction").equals("edit")) {
            doEdit(request, response);
        } else if (recs != null &&
                request.getParameter("runaction").equals("delete")) {
            doDeleteNews(request, response);
        } else if (recs != null &&
                request.getParameter("runaction").equals("duplicate")) {
            doDuplicateNews(request, response);
        } else if (recs != null &&
                request.getParameter("runaction").equals("selec")) {
            doSelecNews(request, response);
        } else if (request.getParameter("runaction").equals("issue")) {
            doIssueNews(request, response);
        }
    }

    protected synchronized void doList(HttpServletRequest request, HttpServletResponse response, String selecConditions) throws ServletException, IOException {

        List recs = getRecords(request, response, selecConditions);
        // Se hace la consulta a la base de datos de noticias.
        request.getSession().setAttribute(attrNameListRecs, recs);
        request.setAttribute("recList", recs);
        request.setAttribute("recCount", recs.size());
        request.setAttribute("recStart", 1);

// Pasa el control al jsp que construye la vista
        doDispatcher(request, response, jspFileList);
    }

    protected synchronized void doListSelec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String conditions = "";
        // Llenar la variable conditions con el contenido deseado en el m�todo
        // sobreescrito de la clase final
        doList(request, response, conditions);
    }

    protected synchronized void doDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Se hace la consulta a la base de datos de noticias
        List recs = getDetails(request, response, request.getParameter("identificador"));
        request.getSession().setAttribute(attrNameListRecs, recs);
        request.setAttribute("recList", recs);
        request.setAttribute("recCount", recs.size());
        request.setAttribute("recStart", 1);
// Pasa el control al jsp que construye la vista
        doDispatcher(request, response, jspFileList);

    }

    protected synchronized void doNavNext(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("recStart",
                (Integer) request.getAttribute("recStart") + (Integer) request.getAttribute("recChunk") > (Integer) request.getAttribute("recCount")
                ? (Integer) request.getAttribute("recStart")
                : (Integer) request.getAttribute("recStart") + (Integer) request.getAttribute("recChunk"));
// Pasa el control al jsp que construye la vista
        doDispatcher(request, response, jspFileList);

    }

    protected synchronized void doNavLast(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("recStart", (Integer) Math.max((Integer) request.getAttribute("recCount") - (Integer) request.getAttribute("recChunk") + 1, 1));
// Pasa el control al jsp que construye la vista
        doDispatcher(request, response, jspFileList);

    }

    protected synchronized void doNavPrev(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("recStart",
                (Integer) request.getAttribute("recStart") - (Integer) request.getAttribute("recChunk") < 1
                ? 1
                : (Integer) request.getAttribute("recStart") - (Integer) request.getAttribute("recChunk"));
// Pasa el control al jsp que construye la vista
        doDispatcher(request, response, jspFileList);

    }

    protected synchronized void doNavFirst(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("recStart", 1);
        setRequestáttributes(request, response);
// Pasa el control al jsp que construye la vista
        doDispatcher(request, response, jspFileList);

    }

    protected synchronized void doNew(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // NoticiasEdit.jsp necesita saber si la vista a mostrar es para crear
        // una noticia nueva (new) o para editar una noticia ya existente (edit)
        request.setAttribute("runaction", "new");

        /* Crea un Bean de Noticia con valores por defecto para pasarla, a trav�s de
         * un atributo de solicitud, al generador de la vista NoticiasEdit.jsp
         * */
        request.setAttribute("recsBean", new Object());
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
// Pasa el control al jsp que construye la vista
                doDispatcher(request, response, jspFileEdit);
                break;
//                }
            }

        }
    }

    protected synchronized void doDeleteNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("runaction", "list");

        List recs = (List) request.getSession().getAttribute(attrNameListRecs);

        if (request.getParameter("selec1") != null) {
            String[] toDelete = request.getParameterValues("selec1");
            for (String idNew : toDelete) {
                for (Object n : recs) {
                    if (checkIdNew(n, idNew)) {
                        recs.remove(n);
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

    protected void doIssueNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// Pasa el control al jsp que construye la vista
    }

    protected void doDuplicateNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/* Pasa el control al jsp que construye la vista.
 Usado para popular registros en la base de datos en fase de pruebas
 por el desarrollador */
    }

    protected void setRequestáttributes(HttpServletRequest request, HttpServletResponse response) {

        // Se inicializan los parámetros de ejecución
        if (request.getParameter("recStart") == null) {
            // No proviene de una llamada anterior de la página de listar o editar
            request.setAttribute("recStart", 1);
        } else {
            try {
                request.setAttribute("recStart", Integer.valueOf(request.getParameter("recStart")));
            } catch (NumberFormatException e) {
                request.setAttribute("recStart", 1);
            }
        }

        if (request.getParameter("recChunk") == null) {
            // No proviene de una llamada anterior de la página de listar o editar
            request.setAttribute("recChunk", 20);
        } else {
            try {
                request.setAttribute("recChunk", Integer.valueOf(request.getParameter("recChunk")));
            } catch (NumberFormatException e) {
                request.setAttribute("recChunk", 20);
            }
        }

        /* El parámetro recCount no se necesita recuperar ya que debe contener el
         * número de registros en la lista y eso se obtiene en el c�digo que figura a
         * continuación con el m�todo size(). Se pasa como atributo para mostrarlo en
         * caso necesario por el jsp o si lo precisara alg�n  script de la página
         *
        if (request.getParameter("recCount") == null) {
        // No proviene de una llamada anterior de la página de listar o editar
        request.setAttribute("recCount", 0);
        } else {
        try {
        request.setAttribute("recCount", Integer.valueOf(request.getParameter("recCount")));
        } catch (NumberFormatException e) {
        request.setAttribute("recCount", 0);
        }
        }
         */
        List recs = (List) request.getSession().getAttribute(attrNameListRecs);
        request.setAttribute("recList", recs);
        if (recs == null) {
            request.setAttribute("recCount", 0);
        } else {
            request.setAttribute("recCount", recs.size());
        }

    }

    protected void doDispatcher(HttpServletRequest request, HttpServletResponse response, String fileTo) throws ServletException, IOException {

        request.setAttribute("page", "/WEB-INF/" + fileTo);
        getServletContext().getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(request, response);
    }

    protected void doStartVariables() {
    }

    abstract protected void doSelecNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    abstract protected boolean checkIdNew(Object n, String idNew);

    abstract protected List getRecord(HttpServletRequest request, HttpServletResponse response, String idNew);

    abstract protected List getRecords(HttpServletRequest request, HttpServletResponse response, String selecConditions);

    abstract protected List getDetails(HttpServletRequest request, HttpServletResponse response, String idNew);

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param requestáservlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,
            IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param requestáservlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,
            IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
