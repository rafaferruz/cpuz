/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.controllers;

import com.cpuz.domain.Image;
import com.cpuz.domain.InfoBlock;
import com.cpuz.domain.NewsComposition;
import com.cpuz.domain.NewsPiece;
import com.cpuz.domain.UserType;
import com.cpuz.service.ImagesModel;
import com.cpuz.service.InfoBlocksModel;
import com.cpuz.service.NewsCompositionsModel;
import com.cpuz.service.NewsPiecesModel;
import com.cpuz.domain.UserType;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class NewsPiecesEditController extends GenericEditController {

    private static final long serialVersionUID = 301L;
    protected NewsPiecesModel npvm = new NewsPiecesModel();
    private NewsCompositionsModel ncvm = new NewsCompositionsModel();
    private InfoBlocksModel inbvm = new InfoBlocksModel();
    private ImagesModel imgvm = new ImagesModel();
    private NewsPiece not = new NewsPiece();
    private List<NewsPiece> recs = new ArrayList<NewsPiece>();
    private List<NewsComposition> compositionList = new ArrayList<NewsComposition>();
    private List<InfoBlock> infoblockList = new ArrayList<InfoBlock>();
    private List<Image> imageList = new ArrayList<Image>();

    public NewsPiecesEditController() {
        super.jspFileList = "views/NewsPiecesList.jsp";
        super.jspFileEdit = "views/NewsPiecesEdit.jsp";
        super.jspFileSelec = "views/NewsPiecesSelec.jsp";
        super.attrNameListRecs = "NewsPiecesListNews";
    }

    /** Procesa las solicitudes procedentes de la vista NewsPieces.jsp
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

// Se repone el valor por defecto de la vista de edición (hay opciones que la modifican)
        super.jspFileEdit = "views/NewsPiecesEdit.jsp";

        //Obtenemos la lista de filas listadas para a�adir las nuevas o modificar las editadas
        recs = (List) request.getSession().getAttribute(attrNameListRecs);
        compositionList = (List<NewsComposition>) request.getSession().getAttribute("recComposition");
        infoblockList = (List<InfoBlock>) request.getSession().getAttribute("recInfoBlock");

        try {
            /* Guarda los datos de la vista en un Bean y pone el Bean disponible
            para la siguiente vista como atributo de solicitud */

            setPropertiesRecord(request, not);
            request.setAttribute("recBean", not);

            if (request.getAttribute("runaction") != null) {
                if (request.getAttribute("runaction").equals("save_new")) {
                    runaction_save_new(request);
                } else if (request.getAttribute("runaction").equals("save_edit")) {
                    runaction_save_edit(request);
                } else if (request.getAttribute("runaction").equals("editComposition")) {
                    runaction_editComposition(request);
                } else if (request.getAttribute("runaction").equals("save_composition")) {
                    runaction_saveComposition(request);
                } else if (request.getAttribute("runaction").equals("addComponent")) {
                    runaction_addComponent(request);
                } else if (request.getAttribute("runaction").equals("assignComponents")) {
                    runaction_assignComponents(request);
                } else if (request.getAttribute("runaction").equals("deleteComp")) {
                    runaction_deleteComp(request);
                } else if (request.getAttribute("runaction").equals("ncil_nav_up")) {
                    runaction_navUpComposition(request);
                } else if (request.getAttribute("runaction").equals("ncil_nav_top")) {
                    runaction_navTopComposition(request);
                } else if (request.getAttribute("runaction").equals("ncil_nav_down")) {
                    runaction_navDownComposition(request);
                } else if (request.getAttribute("runaction").equals("ncil_nav_bottom")) {
                    runaction_navBottomComposition(request);
                }
            }


            // NewsPiecesEdit.jsp necesita saber si la vista a mostrar es para crear
            // una noticia nueva (new) o para editar una noticia ya existente (edit)
        } catch (ParseException ex) {
            request.setAttribute("runaction", "edit");
            request.setAttribute("NewsPiecesEditErrorMsg", "NewsPiecesEditErrorParseException");
        }
    }

    protected void setPropertiesRecord(HttpServletRequest request, Object rec) throws ParseException {

        NewsPiece notLocal = (NewsPiece) rec;

        notLocal.setId(request.getAttribute("id_disabled") == null || ((String) request.getAttribute("id_disabled")).equals("") ? 0 : Integer.parseInt((String) request.getAttribute("id_disabled")));
        if (request.getAttribute("runaction").equals("save_new")) {
            notLocal.setDatetime(new Date());
            notLocal.setUser((String) request.getSession().getAttribute("user"));

        } else {
            List<NewsPiece> notList = npvm.getNewsRecords(UserType.ADMIN, " npi_id = " + notLocal.getId() + " ");
            if (notList != null) {
                notLocal.setDatetime(notList.get(0).getDatetime());
                notLocal.setUser(notList.get(0).getUser());
            }
        }
        //sdf.parse((String) request.getAttribute("date"))
        notLocal.setStatus(request.getAttribute("status") == null || ((String) request.getAttribute("status")).equals("") ? 0 : Integer.parseInt((String) request.getAttribute("status")));
        notLocal.setSection((String) request.getAttribute("section"));
        notLocal.setDescription((String) request.getAttribute("description"));
        notLocal.setScope(request.getAttribute("scope") == null || ((String) request.getAttribute("scope")).equals("") ? 0 : Integer.parseInt((String) request.getAttribute("scope")));
        notLocal.setAccess(request.getAttribute("access") == null || ((String) request.getAttribute("access")).equals("") ? 0 : Integer.parseInt((String) request.getAttribute("access")));

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

    private void runaction_save_new(HttpServletRequest request) {
        // Se crea un NUEVO registro de NewsPiece
// Prepara el bean de NewsPiece previo a la grabación del registro
        if (npvm.setNewRecord(not) < 0) {
            request.setAttribute("NewsPiecesEditErrorMsg", "NewsPiecesEditErrorMsg");
        } else {
            recs.add(0, not);
            request.getSession().setAttribute(attrNameListRecs, recs);
            request.setAttribute("recList", recs);

            request.setAttribute("NewsPiecesEditSaveOkMsg", "NewsPiecesEditSaveOkMsg");
        }
        request.setAttribute("runaction", "edit");

    }

    private void runaction_save_edit(HttpServletRequest request) {
        // Se ACTUALIZA el registro de NewsPiece que se acaba de editar
// Actualiza el registtro de NewsPiece con el contenido obtenido al leer los parámetros de request
        if (npvm.setUpdateRecord(not) < 0) {
            request.setAttribute("runaction", "edit");
            request.setAttribute("NewsPiecesEditErrorMsg", "NewsPiecesEditErrorMsg");
        } else {
// Actualiza el objeto NewsPiece de la lista de NewsPieces
            for (Object sec : recs) {
                if (((NewsPiece) sec).getId().equals(request.getAttribute("id_disabled"))) {
                    recs.set(recs.indexOf(sec), not);
                }
            }
            request.getSession().setAttribute(attrNameListRecs, recs);
            request.setAttribute("recList", recs);
// Regenera borra los Composition de la BD
            ncvm.deleteNews(" WHERE nco_npi_id = " + not.getId() + " ");
            int i = 0;
            for (NewsComposition nc : compositionList) {
// Crea el nuevo valor de Orden de la l�nea Composition
                nc.setOrder(++i);
// Graba la nueva Composition en la BD
                ncvm.setNewRecord(nc);
            }

            request.setAttribute("runaction", "list");
            request.setAttribute("NewsPiecesEditSaveOkMsg", "NewsPiecesEditSaveOkMsg");
        }

    }

    private void runaction_addComponent(HttpServletRequest request) {
// Se presenta una lista con los componentes disponibles del tipo seleccionado
        if (((String) request.getAttribute("addComponentType")).equals("InfoBlock")) {

// Crea la lista de Infoblocks disponibles y la pasa a la vista
            infoblockList = inbvm.getNewsRecords(UserType.ANONYMOUS, " inb_user = '"
                    + request.getSession(false).getAttribute("user")
                    + "' ");
            request.getSession(false).setAttribute("recInfoBlock", infoblockList);
            /* Se pasa a la vista un atributo de solicitud para indicar que
            debe presentar una lista de InfoBlocks */
            request.setAttribute("listComponents", "InfoBlock");
            request.setAttribute("returnFormAction", "NewsPiecesEdit");
        } else if (((String) request.getAttribute("addComponentType")).equals("Image")) {

// Crea la lista de Infoblocks disponibles y la pasa a la vista
            imageList = imgvm.getNewsRecords(UserType.ANONYMOUS, " img_user = '"
                    + request.getSession(false).getAttribute("user")
                    + "' ");
            request.getSession(false).setAttribute("recImage", imageList);
            /* Se pasa a la vista un atributo de solicitud para indicar que
            debe presentar una lista de InfoBlocks */
            request.setAttribute("listComponents", "Image");
            request.setAttribute("returnFormAction", "NewsPiecesEdit");
        }



        request.setAttribute("runaction", "edit");
    }

    private void runaction_assignComponents(HttpServletRequest request) {

        // Se presenta una lista con los componentes disponibles del tipo seleccionado
        if (((String) request.getAttribute("assignComponentType")).equals("InfoBlock")) {
// Graba como filas de NewsComposition los InfoBlocks seleccionados en la vista
            if (request.getAttribute("selec1_INB") != null) {
                if (request.getAttribute("selec1_INB") instanceof String) {
                    Iterator it = infoblockList.iterator();
                    while (it.hasNext()) {
                        InfoBlock ib = (InfoBlock) it.next();
                        if (ib.getId() == Integer.parseInt((String) request.getAttribute("selec1_INB"))) {
                            NewsComposition compositionRecord = new NewsComposition();
                            compositionRecord = setBeanCompositionInfoBlock(not, compositionRecord, ib);
                            compositionList.add(compositionRecord);
//                            ncvm.setNewRecord(compositionRecord);
                            break;
                        }
                    }
                } else if (request.getAttribute("selec1_INB") instanceof String[]) {
                    String[] selecs = (String[]) request.getAttribute("selec1_INB");
                    for (int i = 0; i < selecs.length ; i++) {
                        Iterator it = infoblockList.iterator();
                        while (it.hasNext()) {
                            InfoBlock ib = (InfoBlock) it.next();
                            if (ib.getId() == Integer.parseInt(selecs[i])) {
                                NewsComposition compositionRecord = new NewsComposition();
                                compositionRecord = setBeanCompositionInfoBlock(not, compositionRecord, ib);
                                compositionList.add(compositionRecord);
//                                ncvm.setNewRecord(compositionRecord);
                                break;
                            }
                        }
                    }
                }
                request.getSession().setAttribute("recInfoBlock", null);
                /* Se pasa a la vista un atributo de solicitud para indicar que
                debe presentar una lista de InfoBlocks */
                request.setAttribute("listComponents", null);
            }
        } else if (((String) request.getAttribute("assignComponentType")).equals("Image")) {


            // Graba como filas de NewsComposition las Images seleccionadas en la vista
            if (request.getAttribute("selec1_IMG") != null) {
                if (request.getAttribute("selec1_IMG") instanceof String) {
                    Iterator it = imageList.iterator();
                    while (it.hasNext()) {
                        Image ib = (Image) it.next();
                        if (ib.getId() == Integer.parseInt((String) request.getAttribute("selec1_IMG"))) {
                            NewsComposition compositionRecord = new NewsComposition();
                            compositionRecord = setBeanCompositionImage(not, compositionRecord, ib);
                            compositionList.add(compositionRecord);
//                            ncvm.setNewRecord(compositionRecord);
                            break;
                        }
                    }
                } else if (request.getAttribute("selec1_IMG") instanceof String[]) {
                    String[] selecs = (String[]) request.getAttribute("selec1_IMG");
                    for (int i = 0; i < selecs.length ; i++) {
                        Iterator it = imageList.iterator();
                        while (it.hasNext()) {
                            Image ib = (Image) it.next();
                            if (ib.getId() == Integer.parseInt(selecs[i])) {
                                NewsComposition compositionRecord = new NewsComposition();
                                compositionRecord = setBeanCompositionImage(not, compositionRecord, ib);
                                compositionList.add(compositionRecord);
//                                ncvm.setNewRecord(compositionRecord);
                                break;
                            }
                        }
                    }
                }
                request.getSession().setAttribute("recImage", null);
                /* Se pasa a la vista un atributo de solicitud para indicar que
                debe presentar una lista de InfoBlocks */
                request.setAttribute("listComponents", null);
            }
        }

        request.setAttribute("runaction", "edit");
    }

    private void runaction_deleteComp(HttpServletRequest request) {
        if (request.getAttribute("selec1Comp") != null) {
            if (request.getAttribute("selec1Comp") instanceof String) {
                Iterator it = compositionList.iterator();
                while (it.hasNext()) {
                    NewsComposition ib = (NewsComposition) it.next();
                    if (ib.getId() == Integer.parseInt((String) request.getAttribute("selec1Comp"))) {
                        compositionList.remove(ib);
//                        ncvm.deleteNews(ib);
                        break;
                    }
                }
            } else if (request.getAttribute("selec1Comp") instanceof String[]) {
                String[] selecs = (String[]) request.getAttribute("selec1Comp");
                for (int i = 0; i < selecs.length; i++) {
                    Iterator it = compositionList.iterator();
                    while (it.hasNext()) {
                        NewsComposition ib = (NewsComposition) it.next();
                        if (ib.getId() == Integer.parseInt(selecs[i])) {
                            compositionList.remove(ib);
//                            ncvm.deleteNews(ib);
                            break;
                        }
                    }
                }
            }
            /* / Crea la lista de Composición de la News y la pasa a la vista
            compositionList = ncvm.getNewsRecords(0, " nco_npi_id = " + request.getAttribute("id_disabled") + " ");
            request.getSession().setAttribute("recComposition", compositionList);
             */
            request.getSession().setAttribute("recInfoBlock", null);
            request.getSession().setAttribute("recImage", null);
            /* Se pasa a la vista un atributo de solicitud para indicar que
            debe presentar una lista de InfoBlocks */
            request.setAttribute("listComponents", null);
        }
        request.setAttribute("runaction", "edit");

    }

    private NewsComposition setBeanCompositionInfoBlock(NewsPiece np, NewsComposition compositionRecord, InfoBlock ib) {

        compositionRecord.setId(0);
        compositionRecord.setIdComponent(ib.getId());
        compositionRecord.setIdNpi(np.getId());
        compositionRecord.setComponentType("InfoBlock");
        compositionRecord.setHeaderAlt(ib.getHeader());
        compositionRecord.setHeaderStyle(ib.getType());
        if (ib.getBody() == null || ib.getBody().length() == 0) {
            compositionRecord.setBodyAbstract("");
        } else {
            compositionRecord.setBodyAbstract(ib.getBody().substring(0, Math.min(255, ib.getBody().length())));
        }
        compositionRecord.setImageHigh(0);
        compositionRecord.setImageWidth(0);
        compositionRecord.setLinkedElement("");
        return compositionRecord;
    }

    private NewsComposition setBeanCompositionImage(NewsPiece np, NewsComposition compositionRecord, Image ib) {

        compositionRecord.setId(0);
        compositionRecord.setIdComponent(ib.getId());
        compositionRecord.setIdNpi(np.getId());
        compositionRecord.setComponentType("Image");
        compositionRecord.setHeaderAlt(ib.getUserReference());
        compositionRecord.setHeaderStyle("left");
        compositionRecord.setBodyAbstract(ib.getRepositoryReference());
        compositionRecord.setImageHigh(0);
        compositionRecord.setImageWidth(0);
        compositionRecord.setLinkedElement("");
        return compositionRecord;
    }

    private void runaction_navUpComposition(HttpServletRequest request) {
        NewsComposition ncTemp = new NewsComposition();
        if (request.getAttribute("selec1Comp") != null) {
            compositionList = (List<NewsComposition>) request.getSession().getAttribute("recComposition");
            if (request.getAttribute("selec1Comp") instanceof String) {
                int selec = Integer.parseInt((String) request.getAttribute("selec1Comp"));
                for (int i = 0; i < compositionList.size(); i++) {
                    if (selec == ((NewsComposition) compositionList.get(i)).getId()) {
                        if (i > 0) {
                            ncTemp = (NewsComposition) compositionList.get(i);
                            compositionList.set(i, (NewsComposition) compositionList.get(i - 1));
                            compositionList.set(i - 1, ncTemp);
                        }
                        break;
                    }
                }
            } else if (request.getAttribute("selec1Comp") instanceof String[]) {
                String[] selecs = (String[]) request.getAttribute("selec1Comp");
                for (int j = 0; j < selecs.length; j++) {
                    int selec = Integer.parseInt(selecs[j]);
                    for (int i = 0; i < compositionList.size(); i++) {
                        if (selec == ((NewsComposition) compositionList.get(i)).getId()) {
                            if (i > 0) {
                                ncTemp = (NewsComposition) compositionList.get(i);
                                compositionList.set(i, (NewsComposition) compositionList.get(i - 1));
                                compositionList.set(i - 1, ncTemp);
                            }
                            break;
                        }
                    }
                }
            }
        }
        request.getSession().setAttribute("recComposition", compositionList);
        request.setAttribute("runaction", "edit");
    }

    private void runaction_navDownComposition(HttpServletRequest request) {
        NewsComposition ncTemp = new NewsComposition();
        if (request.getAttribute("selec1Comp") != null) {
            compositionList = (List<NewsComposition>) request.getSession().getAttribute("recComposition");
            if (request.getAttribute("selec1Comp") instanceof String) {
                int selec = Integer.parseInt((String) request.getAttribute("selec1Comp"));
                for (int i = compositionList.size() - 1; i >= 0; i--) {
                    if (selec == ((NewsComposition) compositionList.get(i)).getId()) {
                        if (i < compositionList.size() - 1) {
                            ncTemp = (NewsComposition) compositionList.get(i + 1);
                            compositionList.set(i + 1, (NewsComposition) compositionList.get(i));
                            compositionList.set(i, ncTemp);
                        }
                        break;
                    }
                }
            } else if (request.getAttribute("selec1Comp") instanceof String[]) {
                String[] selecs = (String[]) request.getAttribute("selec1Comp");
                for (int j = selecs.length - 1; j >= 0; j--) {
                    int selec = Integer.parseInt(selecs[j]);
                    for (int i = compositionList.size() - 1; i >= 0; i--) {
                        if (selec == ((NewsComposition) compositionList.get(i)).getId()) {
                            if (i < compositionList.size() - 1) {
                                ncTemp = (NewsComposition) compositionList.get(i + 1);
                                compositionList.set(i + 1, (NewsComposition) compositionList.get(i));
                                compositionList.set(i, ncTemp);
                            }
                            break;
                        }
                    }
                }
            }
        }
        request.getSession().setAttribute("recComposition", compositionList);
        request.setAttribute("runaction", "edit");
    }

    private void runaction_navTopComposition(HttpServletRequest request) {
        NewsComposition ncTemp = new NewsComposition();
        if (request.getAttribute("selec1Comp") != null) {
            compositionList = (List<NewsComposition>) request.getSession().getAttribute("recComposition");
            if (request.getAttribute("selec1Comp") instanceof String) {
                int selec = Integer.parseInt((String) request.getAttribute("selec1Comp"));
                for (int i = compositionList.size() - 1; i >= 0; i--) {
                    if (selec == ((NewsComposition) compositionList.get(i)).getId()) {
                        ncTemp = (NewsComposition) compositionList.get(i);
                        compositionList.remove(i);
                        compositionList.add(0, ncTemp);
                        break;
                    }
                }
            } else if (request.getAttribute("selec1Comp") instanceof String[]) {
                String[] selecs = (String[]) request.getAttribute("selec1Comp");
                for (int j = selecs.length - 1; j >= 0; j--) {
                    int selec = Integer.parseInt(selecs[j]);
                    for (int i = compositionList.size() - 1; i >= 0; i--) {
                        if (selec == ((NewsComposition) compositionList.get(i)).getId()) {
                            ncTemp = (NewsComposition) compositionList.get(i);
                            compositionList.remove(i);
                            compositionList.add(0, ncTemp);
                            break;
                        }
                    }
                }
            }
        }
        request.getSession().setAttribute("recComposition", compositionList);
        request.setAttribute("runaction", "edit");
    }

    private void runaction_navBottomComposition(HttpServletRequest request) {
        NewsComposition ncTemp = new NewsComposition();
        if (request.getAttribute("selec1Comp") != null) {
            compositionList = (List<NewsComposition>) request.getSession().getAttribute("recComposition");
            if (request.getAttribute("selec1Comp") instanceof String) {
                int selec = Integer.parseInt((String) request.getAttribute("selec1Comp"));
                for (int i = 0; i < compositionList.size() - 1; i++) {
                    if (selec == ((NewsComposition) compositionList.get(i)).getId()) {
                        ncTemp = (NewsComposition) compositionList.get(i);
                        compositionList.remove(i);
                        compositionList.add(ncTemp);
                        break;
                    }
                }
            } else if (request.getAttribute("selec1Comp") instanceof String[]) {
                String[] selecs = (String[]) request.getAttribute("selec1Comp");
                for (int j = 0; j < selecs.length; j++) {
                    int selec = Integer.parseInt(selecs[j]);
                    for (int i = 0; i < compositionList.size() - 1; i++) {
                        if (selec == ((NewsComposition) compositionList.get(i)).getId()) {
                            ncTemp = (NewsComposition) compositionList.get(i);
                            compositionList.remove(i);
                            compositionList.add(ncTemp);
                            break;
                        }
                    }
                }
            }
        }
        request.getSession().setAttribute("recComposition", compositionList);
        request.setAttribute("runaction", "edit");
    }

    private void runaction_editComposition(HttpServletRequest request) {
        for (NewsComposition comp : compositionList) {
            if (comp.getId() == Integer.parseInt((String) request.getAttribute("idCompositionEdit"))) {
                super.jspFileEdit = "views/NewsCompositionEdit.jsp";
                request.getSession(false).setAttribute("newsCompositionBean", comp);
                request.setAttribute("runaction", "edit");


// Tambi�n se guarda el Bean de NewsPiece en la session para recuperarlo a la vuelta
                request.getSession(false).setAttribute("recBean", not);
                break;
            }
        }
    }

    private void runaction_saveComposition(HttpServletRequest request) {
        for (NewsComposition comp : compositionList) {
            if (comp.getId() == Integer.parseInt((String) request.getAttribute("id_disabled_composition"))) {
                comp.setHeaderAlt((String) request.getAttribute("headerAlt"));
                comp.setHeaderStyle((String) request.getAttribute("headerStyle"));
                comp.setBodyAbstract((String) request.getAttribute("bodyAbstract"));
                comp.setImageHigh(Integer.parseInt((String) request.getAttribute("imageHigh")));
                comp.setImageWidth(Integer.parseInt((String) request.getAttribute("imageWidth")));
                comp.setLinkedElement((String) request.getAttribute("linkedElement"));
            }
        }
        request.setAttribute("runaction", "edit");

        // SAe recupera el Bean de NewsPiece de la session y se elimina del Scope
        request.setAttribute("recBean", request.getSession(false).getAttribute("recBean"));
        request.getSession(false).removeAttribute("recBean");

    }
}

