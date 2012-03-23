/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author RAFAEL FERRUZ
 */
abstract public class GenericEditController extends HttpServlet {

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

        if (ServletFileUpload.isMultipartContent(request)) {
            /* La solicitud recibida es de tipo de contenido multipart,
            por lo que lleva un fichero(s) de imagen en el cuerpo de solicitud */
            doSaveMultipart(request, response);
        } else {
            getParametersForm(request, response);
        }
// Pasa el control al jsp que construye la vista
        if (request.getAttribute("runaction").equals("new")) {
            doDispatcher(request, response, jspFileEdit);
        } else if (request.getAttribute("runaction").equals("edit")) {
            doDispatcher(request, response, jspFileEdit);
        } else if (request.getAttribute("runaction").equals("list")) {
            List recs = (List) request.getSession().getAttribute(attrNameListRecs);
            request.setAttribute("recList", recs);
            doDispatcher(request, response, jspFileList);

        }
    }

    abstract protected void doSaveMultipart(HttpServletRequest request, HttpServletResponse response);

    abstract protected void getParametersForm(HttpServletRequest request, HttpServletResponse response);

    abstract protected void setPropertiesRecord(HttpServletRequest request, Object not) throws ParseException;

    protected void doDispatcher(HttpServletRequest request, HttpServletResponse response, String fileTo) throws ServletException, IOException {

        request.setAttribute("page", "/WEB-INF/" + fileTo);
        getServletContext().getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(request, response);
    }

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
