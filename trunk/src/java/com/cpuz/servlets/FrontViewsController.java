/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.servlets;

import com.cpuz.tools.CheckTool;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class FrontViewsController extends HttpServlet {

    private static final long serialVersionUID = 303L;
    String emailAdmin = "";

    public void init() throws ServletException {
//        emailAdmin = getServletConfig().getInitParameter("emailadmin");
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
        // in the content area of the template page /WEB-INF/pages/main.jsp.
        // It is passed to the template in a requestáattribute.
        String page;
        // If no requestáis received or if the requestáreceived is
        // for the root, serve /WEB-INF/pages/index.jsp
        if (id == null || id.trim().equals("") || id.equals("/")) {
            getServletContext().log(request.getRemoteAddr() + " solicit� " + id);
            page = "/WEB-INF/pages/index.jsp";
            id = "/pages/index.jsp";
        }


        // If a requestáreceived is for a file that does not end in
        // .jsp or .html, there was an error. Include the
        // error page (nonesuch.jsp) and set the nonesuch attribute with
        // the path requestád.
        // && !id.endsWith(".html") && !id.endsWith(".htm")
        if (!id.endsWith(".jsp") && !id.endsWith(".htm") && !id.endsWith(".html") && !id.startsWith("/view/")) {
            getServletContext().log(request.getRemoteAddr() + " solicit� " + id);
            page = "/WEB-INF/pages/nonesuch.jsp";

            request.setAttribute("nonesuch", id);
        } else {
            getServletContext().log(request.getRemoteAddr() + " solicit� " + id);
            page = "/WEB-INF".concat(id);
            try {
// Comprueba sesión y usuario. Incrementa contador de visitas y comprueba usuario.
                CheckTool.checkSession(request, response);
            } catch (NamingException ex) {
                Logger.getLogger(FrontViewsController.class.getName()).log(Level.SEVERE, null, ex);
            }
// fin de comprobación de sesión y usuario  

            /* Se comprueba la autorización para el uso de la página y 
            si pertenece al grupo de páginas 'index'
             */
            page = CheckTool.checkPageAndPermission(request, response, id, page);
// Fin de la comprobación de autorización de uso de la página

        }


        request.setAttribute("page", page);
        try {
            if (page.lastIndexOf("/") >= 0) {
                getServletContext().getNamedDispatcher(page.substring(page.lastIndexOf("/") + 1) + "Controller").forward(request, response);
            }

        } catch (Exception t) {
            System.out.println(t.getMessage());
            getServletContext().log(t.getMessage());
            try {
                request.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(request, response);
            } catch (Throwable e) {
                getServletContext().log(e.getMessage());
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param requestáservlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FrontViewsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param requestáservlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FrontViewsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
