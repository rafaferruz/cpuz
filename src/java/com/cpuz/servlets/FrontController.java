package com.cpuz.servlets;

import com.cpuz.tools.CheckTool;
import java.io.IOException;

import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Realiza las tareas de:
 *	- Asigna a atributos de ServletContext los parámetros de inicialización de la aplicación
 *	- Modificar las rutas de acceso interiores a clases y generadores de vistas en función
 *		de las rutas de llamada recibidas
 *	- Comprueba la autorización para el uso de la página y
        si pertenece al grupo de páginas 'index'
 *	- Pasa el control de generación de vistas a 'main.jsp'
 * 
 */
public class FrontController extends HttpServlet {

    private static final long serialVersionUID = 304L;
    String emailAdmin = "";

    @Override
    public void init() throws ServletException {

        this.getServletContext().setAttribute("emailAdmin", getServletContext().getInitParameter("emailadmin"));
        this.getServletContext().setAttribute("dirApplication", getServletContext().getContextPath());
        this.getServletContext().setAttribute("dirRealApplicationPath", getServletContext().getRealPath("/"));
        this.getServletContext().setAttribute("dirPages", getServletContext().getInitParameter("dirPages"));
        this.getServletContext().setAttribute("dirHomeResources", getServletContext().getInitParameter("dirHomeResources"));
        this.getServletContext().setAttribute("dirCommonResources", getServletContext().getInitParameter("dirCommonResources"));
    }

    /** 
     * Processes requestá for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param requestáservlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        String id = request.getRequestURI();
        id = id.substring(request.getContextPath().length());

        // The page variable holds the path to the page that will be included
        // in the content area of the template page /WEB-INF/ult/main.jsp.
        // It is passed to the template in a requestáattribute.
        String page = getServletContext().getAttribute("dirPages") + "/index.jsp";

        // If no requestáis received or if the requestáreceived is
        // for the root, serve /WEB-INF/ult/index.jsp
        if (id == null || id.trim().equals("") || id.equals("/") || id.equals("/index") || id.equals("/index.htm") || id.equals("/index.html")) {
            getServletContext().log(request.getRemoteAddr() + " solicit� " + id);
            page = getServletContext().getAttribute("dirPages") + "/index.jsp";
            id = "/pages/index.jsp";
        } else if (id.startsWith("/pages/")) {
            getServletContext().log(request.getRemoteAddr() + " solicit� " + id);
            page = getServletContext().getAttribute("dirPages") + id.substring(id.lastIndexOf("/"));
            id = "/pages/" + id.substring(id.lastIndexOf("/"));
        } else if (id.startsWith("/views/")) {
            getServletContext().log(request.getRemoteAddr() + " solicit� " + id);
            page = getServletContext().getAttribute("dirPages") + "/../views" + id.substring(id.lastIndexOf("/"));
            id = "/views/" + id.substring(id.lastIndexOf("/"));
        } else if (id.equals("/admin")) {
            CheckTool.checkUserCredentials(request);
            getServletContext().log(request.getRemoteAddr() + " solicit� " + id);
            page = getServletContext().getAttribute("dirPages") + "/index.jsp";
            id = "/pages/index.jsp";
        }

        /* Se comprueba la autorización para el uso de la página y
        si pertenece al grupo de páginas 'index'
         */
        page = CheckTool.checkPageAndPermission(request, response, id, page);
// Fin de la comprobación de autorización de uso de la página



        request.setAttribute("page", page);
        try {
            request.getRequestDispatcher(getServletContext().getAttribute("dirPages") + "/main.jsp").forward(request, response);

        } catch (Throwable t) {
            getServletContext().log(t.getMessage());
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param requestáservlet request
     * @param response servlet response
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param requestáservlet request
     * @param response servlet response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Returns a short description of the servlet.
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
