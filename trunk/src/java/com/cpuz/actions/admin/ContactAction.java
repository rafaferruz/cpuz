/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.actions.admin;

import com.cpuz.domain.Contact;
import com.cpuz.service.ContactsService;
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
public class ContactAction extends ActionSupport implements RequestAware, SessionAware, Serializable {

    private ControlParams control = new ControlParams();
    private List<Contact> dataList = new ArrayList<>();
    private Contact dataEdit = new Contact();
    private ContactsService dataService;
    private Map<Integer, String> mapStatus = new HashMap<>();
    private List<String> listTargets = new ArrayList<>();
    private Map<Integer, String> mapScopes = new HashMap<>();
    private Map<String, Object> requestáttributes;
    private Map<String, Object> sessionAttributes;
    private String selec1;

    public ContactAction() {
        super();
    }

    @Override
    public String execute() throws Exception {
        return "error";
    }

    public String Contact_new() throws Exception {

        initDataEdit();
        initMapStatus();
        initListTargets();
        initMapScopes();
        control.setRecCount(1);
        control.setRunAction("new");
        requestáttributes.put("page", "/WEB-INF/views/contactEdit.jsp");
        return "NEW";
    }

    public String Contact_edit() throws Exception {
        dataEdit = dataService.getRecords("SELECT * FROM contacts WHERE con_id = "
                + control.getId(), "", "").get(0);
        initMapStatus();
        initListTargets();
        initMapScopes();
        control.setRunAction("edit");
        requestáttributes.put("page", "/WEB-INF/views/contactEdit.jsp");
        return "EDIT";
    }

    public String Contact_saveNew() throws Exception {
        dataEdit.setUser((String) sessionAttributes.get("user"));

        if (dataService.setNewRecord(dataEdit) == 1) {
            this.addActionMessage(getText("ContactEditSaveOkMsg"));
            dataList = dataService.getRecords("SELECT * FROM contacts "
                    + " ORDER BY con_date DESC "
                    + " LIMIT " + control.getRecChunk().toString()
                    + " OFFSET " + control.getRecStart().toString(), "", "");
            control.setRecCount(dataList.size());
            control.setRunAction("list");
            requestáttributes.put("page", "/WEB-INF/pages/thanksForContact.jsp");
            return "THANKS";
        }
        return "EDIT";
    }

    public String Contact_saveEdit() throws Exception {

        if (dataService.keyIdExists(dataEdit.getId())) {
            try {
                dataService.setUpdateRecord(dataEdit);
                this.addActionMessage(getText("ContactEditSaveOkMsg"));
            } catch (Exception ex) {
                this.addActionError(getText("ContactEditErrorMsg"));
                return "EDIT";
            }
            dataList = dataService.getRecords("SELECT * FROM contacts "
                    + " LIMIT " + control.getRecChunk().toString()
                    + " OFFSET " + control.getRecStart().toString(), "", "");
            control.setRecCount(dataList.size());
            control.setRunAction("list");
            requestáttributes.put("page", "/WEB-INF/views/contactList.jsp");
            return "LIST";
        }
        return "NEW";
    }

    public String Contact_delete() throws Exception {
        if (selec1 != null) {
            String[] deletes = selec1.split(",");
            for (int i = 0; i < deletes.length; i++) {
                dataEdit.setId(Integer.parseInt(deletes[i].trim()));
                if (dataService.deleteNews(dataEdit) == 1) {
                    addActionMessage(deletes[i] + " " + getText("SuccessDeletedContact"));
                } else {
                    addActionError(deletes[i] + " " + getText("NoneDeletedContact"));
                }
            }
            return Contact_list();
        }
        addActionError(getText("NoneSelectedContact"));
        return Contact_list();
    }

    public String Contact_list() throws Exception {
        if (control.getRecCount() == 0) {
            dataList = dataService.getRecords("SELECT * FROM contacts ", "", "");
            control.setRecCount(dataList.size());
        }
 
        dataList = dataService.getRecords("SELECT * FROM contacts "
                + " ORDER BY con_date DESC "
                + " LIMIT " + control.getRecChunk().toString()
                + " OFFSET " + control.getRecStart().toString(), "", "");
        control.setRunAction("list");
        requestáttributes.put("page", "/WEB-INF/views/contactList.jsp");
        return "LIST";
    }

    public String Contact_Navigation() throws Exception {
        control.doNavigation();
        return Contact_list();
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

    public Contact getDataEdit() {
        return dataEdit;
    }

    public void setDataEdit(Contact dataEdit) {
        this.dataEdit = dataEdit;
    }

    public List<Contact> getDataList() {
        return dataList;
    }

    public void setDataList(List<Contact> dataList) {
        this.dataList = dataList;
    }

    public void setDataService(ContactsService dataService) {
        this.dataService = dataService;
    }

    public String getSelec1() {
        return selec1;
    }

    public void setSelec1(String selec1) {
        this.selec1 = selec1;
    }

    public List<String> getListTargets() {
        return listTargets;
    }

    public void setListTargets(List<String> listTargets) {
        this.listTargets = listTargets;
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

    public void initListTargets() {
        //Prepara tipos de status para radio element
        listTargets.add(this.getText("Administración"));
        listTargets.add(this.getText("Mantenimiento"));
        listTargets.add(this.getText("Junta Comunidad"));
        listTargets.add(this.getText("Admin. Web"));
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
        dataEdit.setEmail("");
        dataEdit.setTarget(this.getText("Admin. Web"));
        dataEdit.setHeader("");
        dataEdit.setBody("");
        //dataEdit.setUser();
    }
}
