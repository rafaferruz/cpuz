/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.actions.admin;

import com.cpuz.domain.Document;
import com.cpuz.domain.Image;
import com.cpuz.domain.InfoBlock;
import com.cpuz.domain.NewsComposition;
import com.cpuz.domain.NewsPiece;
import com.cpuz.domain.Section;
import com.cpuz.domain.UserRole;
import com.cpuz.service.DocumentsService;
import com.cpuz.service.ImagesService;
import com.cpuz.service.InfoBlocksService;
import com.cpuz.service.NewsCompositionsService;
import com.cpuz.service.NewsPiecesService;
import com.cpuz.service.SectionsService;
import com.cpuz.service.UserRolesService;
import com.cpuz.st2.beans.ControlParams;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class NewsPieceAction extends ActionSupport implements RequestAware, SessionAware, Serializable {

    private ControlParams control = new ControlParams();
    private List<NewsPiece> dataList = new ArrayList<NewsPiece>();
    private NewsPiece dataEdit = new NewsPiece();
    private NewsPiecesService dataService = new NewsPiecesService();
    private Map<Integer, String> mapStatus = new HashMap<Integer, String>();
    private Map<Integer, String> mapScopes = new HashMap<Integer, String>();
    private Map<Integer, String> mapAccess = new HashMap<Integer, String>();
    private List<String> listTypes = new ArrayList<String>();
    private List<String> listImagePositions = new ArrayList<String>();
    private Map<String, Object> requestáttributes;
    private Map<String, Object> sessionAttributes;
    private ControlParams controlCompList = new ControlParams();
    private List<NewsComposition> dataCompList = new ArrayList<NewsComposition>();
    private NewsComposition dataCompEdit = new NewsComposition();
    private NewsCompositionsService dataCompService = new NewsCompositionsService();
    private String[] selec1;
    private List<Section> sectionList;
    private SectionsService sectionsService = new SectionsService();
    private String addComponentType;
    private Map<String, String> mapComponentTypes = new HashMap<String, String>();
    private ControlParams controlComponentType = new ControlParams();
    private List dataObjectsIncludeList = new ArrayList();

    public NewsPieceAction() {
        super();
    }

    @Override
    public String execute() throws Exception {
        return "error";
    }

    public String NewsPiece_new() throws Exception {

        initDataEdit();
        initMapStatus();
        initMapAccess();
        initMapScopes();
        initSectionList();
        control.setRecCount(1);
        control.setRunAction("new");
        requestáttributes.put("page", "/WEB-INF/views/newsPieceEdit.jsp");
        return "NEW";
    }

    public String NewsPiece_edit() throws Exception {
        initMapStatus();
        initMapAccess();
        initMapScopes();
        initSectionList();
        initMapComponentTypes();
        if (control.getRunAction().equals("") == false) {
            dataEdit = dataService.getRecords("SELECT * FROM newspieces WHERE npi_id = "
                    + control.getId(), "", "").get(0);
            // Prepara lista de Componentes de la Noticia
            dataCompList = dataCompService.getRecords("SELECT * FROM newscomposition "
                    + " WHERE nco_npi_id = "
                    + control.getId()
                    + " ORDER BY nco_order", "", "");
        }
        controlCompList.setRecCount(dataCompList.size());
        sessionAttributes.put("dataCompList", dataCompList);
        control.setRunAction("edit");
        controlCompList.setRunAction("list");
        requestáttributes.put("page", "/WEB-INF/views/NewsPieceEdit.jsp");
        return "EDIT";
    }

    public String NewsPiece_editComposition() throws Exception {
        dataCompList = (List<NewsComposition>) sessionAttributes.get("dataCompList");

        for (int i = 0; i < dataCompList.size(); i++) {
            if (dataCompList.get(i).getId() == controlCompList.getId()) {
                dataCompEdit = dataCompList.get(i);
                i = dataCompList.size();
                break;
            }
        }
        initMapStatus();
        initMapAccess();
        initMapScopes();
        initSectionList();
        initMapComponentTypes();
        initListTypes();
        initListImagePositions();
        control.setRunAction("edit");
        controlCompList.setRunAction("edit");
        requestáttributes.put("page", "/WEB-INF/views/NewsPieceEdit.jsp");
        return "EDIT";
    }

    public String NewsPiece_saveNew() throws Exception {
        dataEdit.setUser((String) sessionAttributes.get("user"));
        dataEdit.setDatetime(new Date());
        dataEdit.setStatus(0);

        if (dataService.setNewRecord(dataEdit) == 1) {
            this.addActionMessage(getText("NewsPieceEditSaveOkMsg"));
            return NewsPiece_list();
        }
        return "EDIT";
    }

    public String NewsPiece_saveEdit() throws Exception {

        if (dataService.keyIdExists(dataEdit.getId())) {
            try {
                // Se ACTUALIZA el registro de NewsPiece que se acaba de editar
// Actualiza el registtro de NewsPiece con el contenido obtenido al leer los parámetros de request
                if (dataService.setUpdateRecord(dataEdit) > 0) {
                    dataCompList = (List<NewsComposition>) sessionAttributes.get("dataCompList");

// Regenera borra los Composition de la BD
                    dataCompService.deleteNews(" WHERE nco_npi_id = " + dataEdit.getId() + " ");
                    int i = 0;
                    for (NewsComposition nc : dataCompList) {
// Crea el nuevo valor de Orden de la l�nea Composition
                        nc.setId(0);
                        nc.setOrder(++i);
                        nc.setIdNpi(dataEdit.getId());
// Graba la nueva Composition en la BD
                        dataCompService.setNewRecord(nc);
                    }
                    this.addActionMessage(getText("NewsPieceEditSaveOkMsg"));
                }
            } catch (Exception ex) {
                this.addActionError(getText("NewsPieceEditErrorMsg"));
                return "EDIT";
            }
            return NewsPiece_list();
        }
        return "NEW";


    }

    public String NewsPiece_saveEditComposition() throws Exception {
        dataCompList = (List<NewsComposition>) sessionAttributes.get("dataCompList");

        for (int i = 0; i < dataCompList.size(); i++) {
            if (String.valueOf(dataCompList.get(i).getId()).equals(String.valueOf(dataCompEdit.getId()))) {
                dataCompList.set(i, dataCompEdit);
                i = dataCompList.size();
                break;
            }
        }
        control.setRunAction("");

        return NewsPiece_edit();
    }

    public String NewsPiece_delete() throws Exception {
        if (selec1 != null) {
            for (int i = 0; i < selec1.length; i++) {
                dataEdit.setId(Integer.parseInt(selec1[i].trim()));
                if (dataService.deleteNews(dataEdit) == 1) {
                    addActionMessage(selec1[i] + " " + getText("SuccessDeletedNewsPiece"));
                } else {
                    addActionError(selec1[i] + " " + getText("NoneDeletedNewsPiece"));
                }
            }
            return NewsPiece_list();
        }
        addActionError(getText("NoneSelectedNewsPiece"));
        return NewsPiece_list();
    }

    public String NewsPiece_list() throws Exception {
        if (control.getRecCount() == 0) {
            dataList = dataService.getRecords("SELECT * FROM newspieces ", "", "");
            control.setRecCount(dataList.size());
        }
        dataList = dataService.getRecords("SELECT * FROM newspieces "
                + " ORDER BY npi_date DESC"
                + " LIMIT " + control.getRecChunk().toString()
                + " OFFSET " + control.getRecStart().toString(), "", "");
        control.setRunAction("list");
        requestáttributes.put("page", "/WEB-INF/views/NewsPieceList.jsp");
        return "LIST";
    }

    public String NewsPiece_addComponent() throws Exception {
        initMapStatus();
        initMapAccess();
        initMapScopes();
        initSectionList();
        initMapComponentTypes();
//        controlComponentType.setRecChunk(20);
        if (addComponentType.equals("InfoBlock")) {
            InfoBlocksService componentTypeService = new InfoBlocksService();
            dataObjectsIncludeList = componentTypeService.getRecords("SELECT * FROM infoblocks "
                    + " ORDER BY inb_date DESC"
                    + " LIMIT " + controlComponentType.getRecChunk().toString()
                    + " OFFSET " + controlComponentType.getRecStart().toString(), "", "");
        }
        if (addComponentType.equals("Image")) {
            ImagesService componentTypeService = new ImagesService();
            dataObjectsIncludeList = componentTypeService.getRecords("SELECT * FROM images "
                    + " ORDER BY img_date DESC"
                    + " LIMIT " + controlComponentType.getRecChunk().toString()
                    + " OFFSET " + controlComponentType.getRecStart().toString(), "", "");
        }
        if (addComponentType.equals("Document")) {
            DocumentsService componentTypeService = new DocumentsService();
            dataObjectsIncludeList = componentTypeService.getRecords("SELECT * FROM documents "
                    + " ORDER BY doc_date DESC"
                    + " LIMIT " + controlComponentType.getRecChunk().toString()
                    + " OFFSET " + controlComponentType.getRecStart().toString(), "", "");
        }
        control.setRunAction("edit");

        dataCompList = (List<NewsComposition>) sessionAttributes.get("dataCompList");
        controlCompList.setRecCount(dataCompList.size());
        controlCompList.setRunAction("list");

        sessionAttributes.put("dataObjectsIncludeList", dataObjectsIncludeList);
        controlComponentType.setRecCount(dataObjectsIncludeList.size());
        controlComponentType.setRunAction("list");
        requestáttributes.put("page", "/WEB-INF/views/NewsPieceEdit.jsp");
        return "EDIT";
    }

    public String NewsPiece_AssignComponents() throws Exception {
        ActionContext act = ActionContext.getContext();
        if (act.getParameters().get("selec1_INB") != null) {
            String[] parameter = (String[]) act.getParameters().get("selec1_INB");

            setDataCompList((List<NewsComposition>) sessionAttributes.get("dataCompList"));
            setDataObjectsIncludeList((List) sessionAttributes.get("dataObjectsIncludeList"));
            for (int j = 0; j < parameter.length; j++) {
                for (int i = 0; i < dataObjectsIncludeList.size(); i++) {
                    if (((InfoBlock) dataObjectsIncludeList.get(i)).getId() == Integer.parseInt(parameter[j])) {
                        dataCompEdit = new NewsComposition();
                        dataCompEdit.setComponentType("InfoBlock");
                        dataCompEdit.setIdNpi(dataEdit.getId());
                        dataCompEdit.setIdComponent(((InfoBlock) dataObjectsIncludeList.get(i)).getId());
                        dataCompEdit.setHeaderAlt(((InfoBlock) dataObjectsIncludeList.get(i)).getHeader());
                        dataCompEdit.setHeaderStyle(((InfoBlock) dataObjectsIncludeList.get(i)).getType());
                        dataCompEdit.setBodyAbstract(((InfoBlock) dataObjectsIncludeList.get(i)).getBody().substring(0, Math.min(((InfoBlock) dataObjectsIncludeList.get(i)).getBody().length() - 1, 255)));
                        dataCompEdit.setImageHigh(0);
                        dataCompEdit.setImageWidth(0);
                        dataCompEdit.setLinkedElement("");
                        dataCompEdit.setOrder(99999);
                        dataCompList.add(dataCompEdit);
                        i = 999999;
                    }
                }
            }
        }
        if (act.getParameters().get("selec1_IMG") != null) {
            String[] parameter = (String[]) act.getParameters().get("selec1_IMG");

            setDataCompList((List<NewsComposition>) sessionAttributes.get("dataCompList"));
            setDataObjectsIncludeList((List) sessionAttributes.get("dataObjectsIncludeList"));
            for (int j = 0; j < parameter.length; j++) {
                for (int i = 0; i < dataObjectsIncludeList.size(); i++) {
                    if (((Image) dataObjectsIncludeList.get(i)).getId() == Integer.parseInt(parameter[j])) {
                        dataCompEdit = new NewsComposition();
                        dataCompEdit.setComponentType("Image");
                        dataCompEdit.setIdNpi(dataEdit.getId());
                        dataCompEdit.setIdComponent(((Image) dataObjectsIncludeList.get(i)).getId());
                        dataCompEdit.setHeaderAlt(((Image) dataObjectsIncludeList.get(i)).getUserReference());
                        dataCompEdit.setBodyAbstract(((Image) dataObjectsIncludeList.get(i)).getRepositoryReference());
                        dataCompEdit.setImageHigh(0);
                        dataCompEdit.setImageWidth(0);
                        dataCompEdit.setLinkedElement("");
                        dataCompEdit.setOrder(99999);
                        dataCompList.add(dataCompEdit);
                        i = 999999;
                    }
                }
            }
        }
        if (act.getParameters().get("selec1_DOC") != null) {
            String[] parameter = (String[]) act.getParameters().get("selec1_DOC");

            setDataCompList((List<NewsComposition>) sessionAttributes.get("dataCompList"));
            setDataObjectsIncludeList((List) sessionAttributes.get("dataObjectsIncludeList"));
            for (int j = 0; j < parameter.length; j++) {
                for (int i = 0; i < dataObjectsIncludeList.size(); i++) {
                    if (((Document) dataObjectsIncludeList.get(i)).getId() == Integer.parseInt(parameter[j])) {
                        dataCompEdit = new NewsComposition();
                        dataCompEdit.setComponentType("Document");
                        dataCompEdit.setIdNpi(dataEdit.getId());
                        dataCompEdit.setIdComponent(((Document) dataObjectsIncludeList.get(i)).getId());
                        dataCompEdit.setHeaderAlt(((Document) dataObjectsIncludeList.get(i)).getUserReference());
                        dataCompEdit.setHeaderStyle("remarked");
                        dataCompEdit.setBodyAbstract(((Document) dataObjectsIncludeList.get(i)).getRepositoryReference());
                        dataCompEdit.setImageHigh(0);
                        dataCompEdit.setImageWidth(0);
                        dataCompEdit.setLinkedElement("");
                        dataCompEdit.setOrder(99999);
                        dataCompList.add(dataCompEdit);
                        i = 999999;
                    }
                }
            }
        }
        control.setRunAction("");
        return NewsPiece_edit();
    }

    public String NewsPiece_Navigation() throws Exception {
        control.doNavigation();
        return NewsPiece_list();
    }

    public String NewsPiece_CompNavTop() throws Exception {
        runaction_navTopComposition();
        control.setRunAction("");
        return NewsPiece_edit();
    }

    public String NewsPiece_CompNavUp() throws Exception {
        runaction_navUpComposition();
        control.setRunAction("");
        return NewsPiece_edit();
    }

    public String NewsPiece_CompNavDown() throws Exception {
        runaction_navDownComposition();
        control.setRunAction("");
        return NewsPiece_edit();
    }

    public String NewsPiece_CompNavBottom() throws Exception {
        runaction_navBottomComposition();
        control.setRunAction("");
        return NewsPiece_edit();
    }

    public String NewsPiece_CompDelete() throws Exception {
        runaction_deleteComposition();
        control.setRunAction("");
        return NewsPiece_edit();
    }

    public String NewsPiece_NavigationComponentType() throws Exception {
        controlComponentType.doNavigation();
        return NewsPiece_addComponent();
    }

    @Override
    public void validate() {
        super.validate();
    }

    public ControlParams getControl() {
        return control;
    }

    public void setControl(ControlParams control) {
        this.control = control;
    }

    public Map<Integer, String> getMapStatus() {
        return mapStatus;
    }

    public void setMapStatus(HashMap<Integer, String> mapStatus) {
        this.mapStatus = mapStatus;
    }

    public NewsPiece getDataEdit() {
        return dataEdit;
    }

    public void setDataEdit(NewsPiece dataEdit) {
        this.dataEdit = dataEdit;
    }

    public List<NewsPiece> getDataList() {
        return dataList;
    }

    public void setDataList(List<NewsPiece> dataList) {
        this.dataList = dataList;
    }

    public void setDataService(NewsPiecesService dataService) {
        this.dataService = dataService;
    }

    public String[] getSelec1() {
        return selec1;
    }

    public void setSelec1(String[] selec1) {
        this.selec1 = selec1;
    }

    public Map<Integer, String> getMapScopes() {
        return mapScopes;
    }

    public void setMapScopes(Map<Integer, String> mapScopes) {
        this.mapScopes = mapScopes;
    }

    public Map<Integer, String> getMapAccess() {
        return mapAccess;
    }

    public void setMapAccess(Map<Integer, String> mapAccess) {
        this.mapAccess = mapAccess;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    public String getAddComponentType() {
        return addComponentType;
    }

    public void setAddComponentType(String addComponentType) {
        this.addComponentType = addComponentType;
    }

    public Map<String, String> getMapComponentTypes() {
        return mapComponentTypes;
    }

    public void setMapComponentTypes(Map<String, String> mapComponentTypes) {
        this.mapComponentTypes = mapComponentTypes;
    }

    public ControlParams getControlCompList() {
        return controlCompList;
    }

    public void setControlCompList(ControlParams controlCompList) {
        this.controlCompList = controlCompList;
    }

    public List<NewsComposition> getDataCompList() {
        return dataCompList;
    }

    public void setDataCompList(List<NewsComposition> dataCompList) {
        this.dataCompList = dataCompList;
    }

    public NewsComposition getDataCompEdit() {
        return dataCompEdit;
    }

    public void setDataCompEdit(NewsComposition dataCompEdit) {
        this.dataCompEdit = dataCompEdit;
    }

    public List<String> getListTypes() {
        return listTypes;
    }

    public void setListTypes(List<String> listTypes) {
        this.listTypes = listTypes;
    }

    public List<String> getListImagePositions() {
        return listImagePositions;
    }

    public void setListImagePositions(List<String> listImagePositions) {
        this.listImagePositions = listImagePositions;
    }

    public ControlParams getControlComponentType() {
        return controlComponentType;
    }

    public void setControlComponentType(ControlParams controlComponentType) {
        this.controlComponentType = controlComponentType;
    }

    public List getDataObjectsIncludeList() {
        return dataObjectsIncludeList;
    }

    public void setDataObjectsIncludeList(List dataObjectsIncludeList) {
        this.dataObjectsIncludeList = dataObjectsIncludeList;
    }

    private void initMapStatus() {
        //Prepara tipos de status para radio element
        mapStatus.put(0, this.getText("received"));
        mapStatus.put(1, this.getText("waiting"));
        mapStatus.put(2, this.getText("authorized"));
    }

    private void initMapScopes() {
        //Prepara tipos de status para radio element
        mapScopes.put(0, this.getText("global"));
        mapScopes.put(1, this.getText("vecinity"));
        mapScopes.put(2, this.getText("restáicted"));
    }

    private void initMapAccess() {
        //Prepara tipos de status para radio element
        mapAccess.put(0, this.getText("Restáicted"));
        mapAccess.put(1, this.getText("ALL"));
    }

    private void initMapComponentTypes() {
        //Prepara tipos de status para radio element
        mapComponentTypes.put("InfoBlock", this.getText("InfoBlock"));
        mapComponentTypes.put("Image", this.getText("Image"));
        mapComponentTypes.put("Document", this.getText("Document"));
        mapComponentTypes.put("News", this.getText("News"));
    }

    public void initListTypes() {
        //Prepara tipos de status para radio element
        listTypes.add(this.getText("title"));
        listTypes.add(this.getText("subtitle"));
        listTypes.add(this.getText("remarked"));
    }

    public void initListImagePositions() {
        //Prepara tipos de status para radio element
        listImagePositions.add(this.getText("left"));
        listImagePositions.add(this.getText("center"));
        listImagePositions.add(this.getText("right"));
    }

    @Override
    public void setRequest(Map map) {
        this.requestáttributes = map;
    }

    @Override
    public void setSession(Map map) {
        this.sessionAttributes = map;
    }

    private void initDataEdit() {
        dataEdit.setDatetime(new Date());
        dataEdit.setStatus(0);
        dataEdit.setScope(1);
        dataEdit.setAccess(0);
        dataEdit.setUser("");
        dataEdit.setSection("");
        dataEdit.setDescription("");
        //dataEdit.setUser();
    }

    private void initSectionList() {
        String stringRoles="";
        String user = (String) sessionAttributes.get("user");
        UserRolesService userRolesService = new UserRolesService();
        List<UserRole> userRolesList = userRolesService.getNewsRecords("SELECT * FROM userroles WHERE usu_user = '" + user + "'");
        for (UserRole userRole:userRolesList){
            stringRoles=stringRoles.concat(userRole.getRole()+",");
        }
        sectionList = sectionsService.getRecords("SELECT * FROM sections ORDER BY sec_name", "", "");
        if (!stringRoles.contains("all") && !stringRoles.contains("adminRole")){
            List<Section> unusedSections=new ArrayList<Section>();
            for (Section section:sectionList){
                if (!stringRoles.contains(section.getId())){
                    unusedSections.add(section);
                }
            }
            sectionList.removeAll(unusedSections);
        }

    }

    private void runaction_navUpComposition() {
        ActionContext act = ActionContext.getContext();
        if (act.getParameters().get("selec1Comp") != null) {
            String[] parameter = (String[]) act.getParameters().get("selec1Comp");

            NewsComposition ncTemp = new NewsComposition();
            setDataCompList((List<NewsComposition>) sessionAttributes.get("dataCompList"));
            for (int j = 0; j < parameter.length; j++) {
                int selec = Integer.parseInt(parameter[j]);
                for (int i = 0; i < dataCompList.size(); i++) {
                    if (selec == ((NewsComposition) dataCompList.get(i)).getId()) {
                        if (i > 0) {
                            ncTemp = (NewsComposition) dataCompList.get(i);
                            dataCompList.set(i, (NewsComposition) dataCompList.get(i - 1));
                            dataCompList.set(i - 1, ncTemp);
                        }
                        break;
                    }
                }
            }
        }
    }

    private void runaction_navDownComposition() {
        ActionContext act = ActionContext.getContext();
        if (act.getParameters().get("selec1Comp") != null) {
            String[] parameter = (String[]) act.getParameters().get("selec1Comp");

            NewsComposition ncTemp = new NewsComposition();
            setDataCompList((List<NewsComposition>) sessionAttributes.get("dataCompList"));
            for (int j = parameter.length - 1; j >= 0; j--) {
                int selec = Integer.parseInt(parameter[j]);
                for (int i = dataCompList.size() - 1; i >= 0; i--) {
                    if (selec == ((NewsComposition) dataCompList.get(i)).getId()) {
                        if (i < dataCompList.size() - 1) {
                            ncTemp = (NewsComposition) dataCompList.get(i + 1);
                            dataCompList.set(i + 1, (NewsComposition) dataCompList.get(i));
                            dataCompList.set(i, ncTemp);
                        }
                        break;
                    }
                }
            }
        }
    }

    private void runaction_navTopComposition() {
        ActionContext act = ActionContext.getContext();
        if (act.getParameters().get("selec1Comp") != null) {
            String[] parameter = (String[]) act.getParameters().get("selec1Comp");

            NewsComposition ncTemp = new NewsComposition();
            setDataCompList((List<NewsComposition>) sessionAttributes.get("dataCompList"));
            for (int j = parameter.length - 1; j >= 0; j--) {
                int selec = Integer.parseInt(parameter[j]);
                for (int i = dataCompList.size() - 1; i >= 0; i--) {
                    if (selec == ((NewsComposition) dataCompList.get(i)).getId()) {
                        ncTemp = (NewsComposition) dataCompList.get(i);
                        dataCompList.remove(i);
                        dataCompList.add(0, ncTemp);
                        break;
                    }
                }
            }
        }
    }

    private void runaction_navBottomComposition() {
        ActionContext act = ActionContext.getContext();
        if (act.getParameters().get("selec1Comp") != null) {
            String[] parameter = (String[]) act.getParameters().get("selec1Comp");

            NewsComposition ncTemp = new NewsComposition();
            setDataCompList((List<NewsComposition>) sessionAttributes.get("dataCompList"));
            for (int j = 0; j < parameter.length; j++) {
                int selec = Integer.parseInt(parameter[j]);
                for (int i = 0; i < dataCompList.size() - 1; i++) {
                    if (selec == ((NewsComposition) dataCompList.get(i)).getId()) {
                        ncTemp = (NewsComposition) dataCompList.get(i);
                        dataCompList.remove(i);
                        dataCompList.add(ncTemp);
                        break;
                    }
                }
            }
        }
    }

    private void runaction_deleteComposition() {
        ActionContext act = ActionContext.getContext();
        if (act.getParameters().get("selec1Comp") != null) {
            String[] parameter = (String[]) act.getParameters().get("selec1Comp");

            setDataCompList((List<NewsComposition>) sessionAttributes.get("dataCompList"));
            for (int i = 0; i < parameter.length; i++) {
                Iterator it = dataCompList.iterator();
                while (it.hasNext()) {
                    NewsComposition ib = (NewsComposition) it.next();
                    if (ib.getId() == Integer.parseInt(parameter[i])) {
                        dataCompList.remove(ib);
//                            ncvm.deleteNews(ib);
                        break;
                    }
                }
            }
        }
    }
}
