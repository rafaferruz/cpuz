/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.st2.actions;

import com.cpuz.domain.Bug;
import com.cpuz.model.BugsModel;
import com.cpuz.st2.beans.ListControlParams;
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
public class BugAction extends ActionSupport implements RequestAware, SessionAware, Serializable {

    private ListControlParams control = new ListControlParams();
    private List<Bug> dataList = new ArrayList<Bug>();
    private Bug dataEdit = new Bug();
    private BugsModel dataModel;
    private Map<Integer, String> mapStatus = new HashMap<Integer, String>();
    private List<String> listTypes = new ArrayList<String>();
    private Map<Integer, String> mapScopes = new HashMap<Integer, String>();
    private Map<String, Object> requestáttributes;
    private Map<String, Object> sessionAttributes;
    private String selec1;

    public BugAction() {
        super();
    }

    @Override
    public String execute() throws Exception {
        return "error";
    }

    public String Bug_new() throws Exception {

        initDataEdit();
        initMapStatus();
        initListTypes();
        control.setRecCount(1);
        control.setRunAction("new");
        requestáttributes.put("page", "/WEB-INF/views/bugEdit.jsp");
        return "NEW";
    }

    public String Bug_edit() throws Exception {
        dataEdit = dataModel.getRecords("SELECT * FROM bugs WHERE bug_id = "
                + control.getId(), "", "").get(0);
        initMapStatus();
        initListTypes();
        control.setRunAction("edit");
        requestáttributes.put("page", "/WEB-INF/views/bugEdit.jsp");
        return "EDIT";
    }

    public String Bug_saveNew() throws Exception {
        dataEdit.setUser((String) sessionAttributes.get("user"));

        if (dataModel.setNewRecord(dataEdit) == 1) {
            this.addActionMessage(getText("BugEditSaveOkMsg"));
            dataList = dataModel.getRecords("SELECT * FROM bugs "
                    + " ORDER BY bug_priority, bug_date DESC "
                    + " LIMIT " + control.getRecChunk().toString()
                    + " OFFSET " + control.getRecStart().toString(), "", "");
            control.setRecCount(dataList.size());
            control.setRunAction("list");
            requestáttributes.put("page", "/WEB-INF/views/bugList.jsp");
            return "LIST";
        }
        return "EDIT";
    }

    public String Bug_saveEdit() throws Exception {

        if (dataModel.keyIdExists(dataEdit.getId())) {
            try {
                dataModel.setUpdateRecord(dataEdit);
                this.addActionMessage(getText("BugEditSaveOkMsg"));
            } catch (Exception ex) {
                this.addActionError(getText("BugEditErrorMsg"));
                return "EDIT";
            }
            dataList = dataModel.getRecords("SELECT * FROM bugs "
                    + " LIMIT " + control.getRecChunk().toString()
                    + " OFFSET " + control.getRecStart().toString(), "", "");
            control.setRecCount(dataList.size());
            control.setRunAction("list");
            requestáttributes.put("page", "/WEB-INF/views/bugList.jsp");
            return "LIST";
        }
        return "NEW";
    }

    public String Bug_delete() throws Exception {
        if (selec1 != null) {
            String[] deletes = selec1.split(",");
            for (int i = 0; i < deletes.length; i++) {
                dataEdit.setId(Integer.parseInt(deletes[i].trim()));
                if (dataModel.deleteNews(dataEdit) == 1) {
                    addActionMessage(deletes[i] + " " + getText("SuccessDeletedBug"));
                } else {
                    addActionError(deletes[i] + " " + getText("NoneDeletedBug"));
                }
            }
            return Bug_list();
        }
        addActionError(getText("NoneSelectedBug"));
        return Bug_list();
    }

    public String Bug_list() throws Exception {
        if (control.getRecCount() == 0) {
            dataList = dataModel.getRecords("SELECT * FROM bugs "
                    + ((Integer)sessionAttributes.get("userCategory") == 2 ? "" : " WHERE bug_user = '" + sessionAttributes.get("user") + "' "), "", "");
            control.setRecCount(dataList.size());
        }
        dataList = dataModel.getRecords("SELECT * FROM bugs "
                + ((Integer) sessionAttributes.get("userCategory") == 2 ? "" : " WHERE bug_user = '" + sessionAttributes.get("user") + "' ")
                + " ORDER BY bug_priority, bug_date DESC "
                + " LIMIT " + control.getRecChunk().toString()
                + " OFFSET " + control.getRecStart().toString(), "", "");
        control.setRunAction("list");
        requestáttributes.put("page", "/WEB-INF/views/bugList.jsp");
        return "LIST";
    }

    public String Bug_Navigation() throws Exception {
        control.doNavigation();
        return Bug_list();
    }

    @Override
    public void validate() {
        super.validate();
    }

    public ListControlParams getControl() {
        return control;
    }

    public void setControl(ListControlParams control) {
        this.control = control;
    }

    public Map<Integer, String> getMapStatus() {
        return mapStatus;
    }

    public void setMapStatus(HashMap<Integer, String> mapStatus) {
        this.mapStatus = mapStatus;
    }

    public Bug getDataEdit() {
        return dataEdit;
    }

    public void setDataEdit(Bug dataEdit) {
        this.dataEdit = dataEdit;
    }

    public List<Bug> getDataList() {
        return dataList;
    }

    public void setDataList(List<Bug> dataList) {
        this.dataList = dataList;
    }

    public void setDataModel(BugsModel dataModel) {
        this.dataModel = dataModel;
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
        mapStatus.put(0, this.getText("created"));
        mapStatus.put(1, this.getText("incourse"));
        mapStatus.put(2, this.getText("ended"));
    }

    public void initListTypes() {
        //Prepara tipos de status para radio element
        listTypes.add(this.getText("undefined"));
        listTypes.add(this.getText("enhancement"));
        listTypes.add(this.getText("error"));
        listTypes.add(this.getText("development"));
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
        dataEdit.setPriority(5);
        dataEdit.setType(this.getText("undefined"));
        dataEdit.setApplication(this.getText(""));
        dataEdit.setHeader("");
        dataEdit.setBody("");
        //dataEdit.setUser();
    }

}
