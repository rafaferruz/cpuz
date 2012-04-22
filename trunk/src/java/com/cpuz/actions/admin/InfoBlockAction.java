/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.actions.admin;

import com.cpuz.domain.InfoBlock;
import com.cpuz.domain.NewsComposition;
import com.cpuz.domain.NewsPiece;
import com.cpuz.service.InfoBlocksService;
import com.cpuz.service.NewsCompositionsService;
import com.cpuz.service.NewsPiecesService;
import com.cpuz.st2.beans.ControlParams;
import com.opensymphony.xwork2.ActionSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class InfoBlockAction extends ActionSupport implements RequestAware, SessionAware, Serializable {

    private ControlParams control = new ControlParams();
    private List<InfoBlock> dataList = new ArrayList<InfoBlock>();
    private InfoBlock dataEdit = new InfoBlock();
    private InfoBlocksService dataService;
    private Map<Integer, String> mapStatus = new HashMap<Integer, String>();
    private List<String> listTypes = new ArrayList<String>();
    private Map<Integer, String> mapScopes = new HashMap<Integer, String>();
    private Map<String, Object> requestáttributes;
    private Map<String, Object> sessionAttributes;
    private String selec1;

    public InfoBlockAction() {
        super();
    }

    @Override
    public String execute() throws Exception {
        return "error";
    }

    public String InfoBlock_new() throws Exception {

        initDataEdit();
        initMapStatus();
        initListTypes();
        initMapScopes();
        control.setRecCount(1);
        control.setRunAction("new");
        requestáttributes.put("page", "/WEB-INF/views/infoBlockEdit.jsp");
        return "NEW";
    }

    public String InfoBlock_edit() throws Exception {
        dataEdit = dataService.getRecords("SELECT * FROM infoblocks WHERE inb_id = "
                + control.getId(), "", "").get(0);
        initMapStatus();
        initListTypes();
        initMapScopes();
        control.setRunAction("edit");
        requestáttributes.put("page", "/WEB-INF/views/infoBlockEdit.jsp");
        return "EDIT";
    }

    public String InfoBlock_saveNew() throws Exception {
        dataEdit.setUser((String) sessionAttributes.get("user"));

        if (dataService.setNewRecord(dataEdit) == 1) {
            this.addActionMessage(getText("InfoBlockEditSaveOkMsg"));
            dataList = dataService.getRecords("SELECT * FROM infoblocks "
                    + " ORDER BY inb_date DESC "
                    + " LIMIT " + control.getRecChunk().toString()
                    + " OFFSET " + control.getRecStart().toString(), "", "");
            control.setRecCount(dataList.size());
            control.setRunAction("list");
            requestáttributes.put("page", "/WEB-INF/views/infoBlockList.jsp");
            return "LIST";
        }
        return "EDIT";
    }

    public String InfoBlock_saveEdit() throws Exception {

        if (dataService.keyIdExists(dataEdit.getId())) {
            try {
                dataService.setUpdateRecord(dataEdit);
                this.addActionMessage(getText("InfoBlockEditSaveOkMsg"));
            } catch (Exception ex) {
                this.addActionError(getText("InfoBlockEditErrorMsg"));
                return "EDIT";
            }
            dataList = dataService.getRecords("SELECT * FROM infoblocks "
                    + " LIMIT " + control.getRecChunk().toString()
                    + " OFFSET " + control.getRecStart().toString(), "", "");
            control.setRecCount(dataList.size());
            control.setRunAction("list");
            requestáttributes.put("page", "/WEB-INF/views/infoBlockList.jsp");
            return "LIST";
        }
        return "NEW";
    }

    public String InfoBlock_delete() throws Exception {
        if (selec1 != null) {
            String[] deletes = selec1.split(",");
            for (int i = 0; i < deletes.length; i++) {
                dataEdit.setId(Integer.parseInt(deletes[i].trim()));
                if (dataService.deleteNews(dataEdit) == 1) {
                    addActionMessage(deletes[i] + " " + getText("SuccessDeletedInfoBlock"));
                } else {
                    addActionError(deletes[i] + " " + getText("NoneDeletedInfoBlock"));
                }
            }
            return InfoBlock_list();
        }
        addActionError(getText("NoneSelectedInfoBlock"));
        return InfoBlock_list();
    }

    public String InfoBlock_list() throws Exception {
        if (control.getRecCount() == 0) {
            dataList = dataService.getRecords("SELECT * FROM infoblocks "
                    + ((Integer) sessionAttributes.get("userCategory") == 2 ? "" : " WHERE inb_user = '" + sessionAttributes.get("user") + "' "), "", "");
            control.setRecCount(dataList.size());
        }
        dataList = dataService.getRecords("SELECT * FROM infoblocks "
                + ((Integer) sessionAttributes.get("userCategory") == 2 ? "" : " WHERE inb_user = '" + sessionAttributes.get("user") + "' ")
                + " ORDER BY inb_date DESC "
                + " LIMIT " + control.getRecChunk().toString()
                + " OFFSET " + control.getRecStart().toString(), "", "");
        control.setRunAction("list");
        requestáttributes.put("page", "/WEB-INF/views/infoBlockList.jsp");
        return "LIST";
    }

    public String InfoBlock_Navigation() throws Exception {
        control.doNavigation();
        return InfoBlock_list();
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

    public InfoBlock getDataEdit() {
        return dataEdit;
    }

    public void setDataEdit(InfoBlock dataEdit) {
        this.dataEdit = dataEdit;
    }

    public List<InfoBlock> getDataList() {
        return dataList;
    }

    public void setDataList(List<InfoBlock> dataList) {
        this.dataList = dataList;
    }

    public void setDataService(InfoBlocksService dataService) {
        this.dataService = dataService;
    }

    public String getSelec1() {
        return selec1;
    }

    public void setSelec1(String selec1) {
        this.selec1 = selec1;
    }

    public List<String> getListTypes() {
        return listTypes;
    }

    public void setListTypes(List<String> listTypes) {
        this.listTypes = listTypes;
    }

    public Map<Integer, String> getMapScopes() {
        return mapScopes;
    }

    public void setMapScopes(Map<Integer, String> mapScopes) {
        this.mapScopes = mapScopes;
    }

    public void initMapStatus() {
        //Prepara tipos de status para radio element
        mapStatus.put(0, this.getText("received"));
        mapStatus.put(1, this.getText("waiting"));
        mapStatus.put(2, this.getText("authorized"));
    }

    public void initListTypes() {
        //Prepara tipos de status para radio element
        listTypes.add(this.getText("title"));
        listTypes.add(this.getText("subtitle"));
        listTypes.add(this.getText("remarked"));
    }

    public void initMapScopes() {
        //Prepara tipos de status para radio element
        mapScopes.put(0, this.getText("global"));
        mapScopes.put(1, this.getText("vecinity"));
        mapScopes.put(2, this.getText("restáicted"));
    }

    public void setRequest(Map map) {
        this.requestáttributes = map;
    }

    public void setSession(Map map) {
        this.sessionAttributes = map;
    }

    private void initDataEdit() {
        dataEdit.setDatetime(new Date());
        dataEdit.setStatus(0);
        dataEdit.setScope(1);
        dataEdit.setType(this.getText("title"));
        dataEdit.setHeader("");
        dataEdit.setBody("");
        //dataEdit.setUser();
    }

    public String NewsPiece_saveNew() throws Exception {
        NewsPiece dataNewsPiece = new NewsPiece();
        dataNewsPiece.setUser((String) sessionAttributes.get("user"));
        dataNewsPiece.setDatetime(new Date());
        dataNewsPiece.setStatus(0);
        dataNewsPiece.setDescription(dataEdit.getHeader());
        dataNewsPiece.setScope(1);

        NewsPiecesService newsPieceService = new NewsPiecesService();
        if (newsPieceService.setNewRecord(dataNewsPiece) == 1) {
            control.setId(dataNewsPiece.getId());
            NewsComposition dataNewsComposition = new NewsComposition();
            dataNewsComposition.setOrder(1);
            dataNewsComposition.setIdNpi(dataNewsPiece.getId());
            dataNewsComposition.setComponentType("InfoBlock");
            dataNewsComposition.setIdComponent(dataEdit.getId());
            dataNewsComposition.setHeaderAlt(dataEdit.getHeader());
            dataNewsComposition.setHeaderStyle(dataEdit.getType());
            dataNewsComposition.setBodyAbstract(dataEdit.getBody().substring(0, Math.min(dataEdit.getBody().length() - 1, 255)));
            dataNewsComposition.setImageHigh(0);
            dataNewsComposition.setImageWidth(0);
            dataNewsComposition.setLinkedElement("");
// Graba la nueva Composition en la BD
            NewsCompositionsService newsCompositionsService = new NewsCompositionsService();
            newsCompositionsService.setNewRecord(dataNewsComposition);
        }
        return "EDIT_NEWSPIECE";
    }

    public String InfoBlock_createNewsPiece() throws Exception {

        String option = control.getRunAction();
        if (option.equals("edit")) {
            InfoBlock_saveEdit();
        } else if (option.equals("new")) {
            InfoBlock_saveNew();
        } else {
            return "LIST";
        }
        return NewsPiece_saveNew();
    }
}
