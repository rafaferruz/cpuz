/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.controllers;

import com.cpuz.domain.InfoBlock;
import com.cpuz.service.InfoBlocksModel;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class NewsCompositionsEditController extends GenericEditController {

    private static final long serialVersionUID = 301L;
    protected InfoBlocksModel nvm = new InfoBlocksModel();

    public NewsCompositionsEditController() {
        super.jspFileList = "views/infoBlocksList.jsp";
        super.jspFileEdit = "views/infoBlocksEdit.jsp";
        super.jspFileSelec = "views/infoBlocksSelec.jsp";
        super.attrNameListRecs = "InfoBlocksListNews";
    }

    /** Procesa las solicitudes procedentes de la vista infoBlocks.jsp
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected synchronized void getParametersForm(HttpServletRequest request, HttpServletResponse response) {

        Enumeration parameters = request.getParameterNames();
        while (parameters.hasMoreElements()) {
            String parameterName = (String) parameters.nextElement();
            if (request.getParameterValues(parameterName).length == 1) {
                request.setAttribute(parameterName, request.getParameter(parameterName));
            } else {
                request.setAttribute(parameterName, request.getParameterValues(parameterName));
            }
        }
        // SE GRABA EL REGISTRO EN LA BASE DE DATOS

        InfoBlocksModel nvm = new InfoBlocksModel();

        try {
            //Obtenemos la lista de filas listadas para a�adir las nuevas o modificar las editadas
            List recs = (List) request.getSession().getAttribute(attrNameListRecs);

            if (request.getAttribute("runaction") != null
                    && request.getAttribute("runaction").equals("save_new")) {
// Se crea un NUEVO registro de InfoBlock
// Prepara el bean de InfoBlock previo a la grabación del registro
                InfoBlock not = new InfoBlock();
//                request.setAttribute("identificador", "");

                setPropertiesRecord(request, not);
                if (nvm.setNewRecord(not) < 0) {
                    request.setAttribute("InfoBlocksEditErrorMsg", "InfoBlocksEditErrorMsg");
                } else {
                    recs.add(0, not);
                    request.getSession().setAttribute(attrNameListRecs, recs);
                    request.setAttribute("recList", recs);

                    request.setAttribute("InfoBlocksEditSaveOkMsg", "InfoBlocksEditSaveOkMsg");
                }
                request.setAttribute("runaction", "new");
            } else if (request.getAttribute("runaction") != null
                    && request.getAttribute("runaction").equals("save_edit")) {
// Se ACTUALIZA el registro de InfoBlock que se acaba de editar
// Prepara el bean de InfoBlock previo a la grabación del registro
                InfoBlock not = new InfoBlock();

                setPropertiesRecord(request, not);
                not.setId(request.getAttribute("id_disabled") == null
                        || ((String) request.getAttribute("id_disabled")).equals("")
                        ? 0 : Integer.parseInt((String) request.getAttribute("id_disabled")));
                if (nvm.setUpdateRecord(not) < 0) {
                    request.setAttribute("runaction", "edit");
                    request.setAttribute("InfoBlocksEditErrorMsg", "InfoBlocksEditErrorMsg");
                } else {
                    for (Object sec : recs) {
                        if (((InfoBlock) sec).getId().equals(request.getAttribute("id_disabled"))) {
                            recs.set(recs.indexOf(sec), not);
                        }
                    }
                    request.getSession().setAttribute(attrNameListRecs, recs);
                    request.setAttribute("recList", recs);

                    request.setAttribute("runaction", "list");
                    request.setAttribute("InfoBlocksEditSaveOkMsg", "InfoBlocksEditSaveOkMsg");
                }
            }

            // InfoBlocksEdit.jsp necesita saber si la vista a mostrar es para crear
            // una noticia nueva (new) o para editar una noticia ya existente (edit)
        } catch (ParseException ex) {
            request.setAttribute("runaction", "edit");
            request.setAttribute("InfoBlocksEditErrorMsg", "InfoBlocksEditErrorParseException");
        }
    }

    protected void setPropertiesRecord(HttpServletRequest request, Object rec) throws ParseException {

        String pattern = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat();
        // apply the pattern to the SimpleDateFormat class
        sdf.applyPattern(pattern);

        InfoBlock not = (InfoBlock) rec;

        not.setId(request.getAttribute("identificador") == null || ((String) request.getAttribute("identificador")).equals("") ? 0 : Integer.parseInt((String) request.getAttribute("identificador")));
        if (request.getAttribute("runaction").equals("save_new")) {
            not.setDatetime(new Date());
        not.setUser((String) request.getSession().getAttribute("user"));
        }
        //sdf.parse((String) request.getAttribute("date"))
        not.setStatus(request.getAttribute("status") == null || ((String) request.getAttribute("status")).equals("") ? 0 : Integer.parseInt((String) request.getAttribute("status")));
        not.setType((String) request.getAttribute("type"));
        not.setHeader((String) request.getAttribute("header"));
        not.setBody((String) request.getAttribute("body"));
        not.setScope(request.getAttribute("scope") == null || ((String) request.getAttribute("scope")).equals("") ? 0 : Integer.parseInt((String) request.getAttribute("scope")));

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
            throws ServletException, IOException {
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
            throws ServletException, IOException {
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

    @Override
    protected void doSaveMultipart(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
