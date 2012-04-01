/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.st2.actions;

import com.cpuz.domain.Role;
import com.cpuz.domain.Section;
import com.cpuz.domain.UserRole;
import com.cpuz.model.RolesModel;
import com.cpuz.model.SectionsModel;
import com.cpuz.model.UserRolesModel;
import com.cpuz.st2.beans.ControlParams;
import com.opensymphony.xwork2.ActionSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.RequestAware;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class SectionAction extends ActionSupport implements RequestAware, Serializable {

    private ControlParams control = new ControlParams();
    private List<Section> dataList = new ArrayList<Section>();
    private Section dataEdit = new Section();
    private SectionsModel dataModel;
    private Map<Integer, String> mapStatus = new HashMap<Integer, String>();
    private Map<String, Object> requestáttributes = new HashMap<String, Object>();
    private List<Role> availableRolesList;
    private List<String> authRolesList;
    private RolesModel rolesModel;
    private String authRolesSel;
    private String[] availableRolesSel;
    private String selec1;

    public SectionAction() {
        super();
    }

    @Override
    public String execute() throws Exception {
        return "error";
    }

    public String Section_new() throws Exception {
        availableRolesList = rolesModel.getNewsRecords();

        control.setRecCount(1);
        control.setRunAction("new");
        requestáttributes.put("page", "/WEB-INF/views/SectionEdit.jsp");
        return "NEW";
    }

    public String Section_edit() throws Exception {
        dataEdit = dataModel.getRecords("SELECT * FROM sections WHERE sec_id = '"
                + control.getId() + "'", "", "").get(0);
        // Se lee lista de Roles
        availableRolesList = rolesModel.getNewsRecords();

        authRolesList.clear();
        if (dataEdit.getAuthorizedRoles() != null
                && !dataEdit.getAuthorizedRoles().isEmpty()) {
            String[] roles = dataEdit.getAuthorizedRoles().split(",");
            for (int i = 0; i < roles.length; i++) {
                if (!roles[i].equals("")) {
                    authRolesList.add(roles[i]);
                    for (int j = 0; j < availableRolesList.size(); j++) {
                        if (availableRolesList.get(j).getRole().equals(roles[i])) {
                            availableRolesList.remove(j);
                            j = availableRolesList.size();
                        }
                    }
                }
            }
        }
//        initMapStatus();
        control.setRunAction("edit");
        requestáttributes.put("page", "/WEB-INF/views/SectionEdit.jsp");
        return "EDIT";
    }

    public String Section_saveNew() throws Exception {
        dataEdit.setAuthorizedRoles(authRolesSel.replaceAll(" ", ""));
        if (dataModel.setNewRecord(dataEdit) == 1) {
            this.addActionMessage(getText("SectionEditSaveOkMsg"));
            return Section_list();
        }
        return "EDIT";
    }

    public String Section_saveEdit() throws Exception {

        dataEdit.setAuthorizedRoles(authRolesSel.replaceAll(" ", ""));
        if (dataModel.keyIdExists(dataEdit.getId().toString())) {
            try {
                dataModel.setUpdateRecord(dataEdit);
                this.addActionMessage(getText("SectionEditSaveOkMsg"));
            } catch (Exception ex) {
                this.addActionError(getText("SectionEditErrorMsg"));
                return "EDIT";
            }
            return Section_list();
        }
        return "NEW";
    }

    public String Section_delete() throws Exception {
        if (selec1 != null) {
            String[] deletes = selec1.split(",");
            for (int i = 0; i < deletes.length; i++) {
                dataEdit.setId(deletes[i].trim());
                if (dataModel.deleteNews(dataEdit) == 1) {
                    addActionMessage(deletes[i] + " " + getText("SuccessDeletedSection"));
                } else {
                    addActionError(deletes[i] + " " + getText("NoneDeletedSection"));
                }
            }
            return Section_list();
        }
        addActionError(getText("NoneSelectedSection"));
        return Section_list();
    }

    public String Section_list() throws Exception {
        if (control.getRecCount() == 0) {
            dataList = dataModel.getRecords("SELECT * FROM sections ", "", "");
            control.setRecCount(dataList.size());
        }
        dataList = dataModel.getRecords("SELECT * FROM sections "
                + " LIMIT " + control.getRecChunk().toString()
                + " OFFSET " + control.getRecStart().toString(), "", "");
        control.setRunAction("list");
        requestáttributes.put("page", "/WEB-INF/views/SectionList.jsp");
        return "LIST";
    }

    public String Section_Navigation() throws Exception {
        control.doNavigation();
        return Section_list();
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

    public Section getDataEdit() {
        return dataEdit;
    }

    public void setDataEdit(Section dataEdit) {
        this.dataEdit = dataEdit;
    }

    public List<Section> getDataList() {
        return dataList;
    }

    public void setDataList(List<Section> dataList) {
        this.dataList = dataList;
    }

    public void setDataModel(SectionsModel dataModel) {
        this.dataModel = dataModel;
    }

    public void setAuthRolesList(List<String> authRolesList) {
        this.authRolesList = authRolesList;
    }

    public void setAvailableRolesList(List<Role> availableRolesList) {
        this.availableRolesList = availableRolesList;
    }

    public void setRolesModel(RolesModel rolesModel) {
        this.rolesModel = rolesModel;
    }

    public String getAuthRolesSel() {
        return authRolesSel;
    }

    public void setAuthRolesSel(String authRolesSel) {
        this.authRolesSel = authRolesSel;
    }

    public String[] getAvailableRolesSel() {
        return availableRolesSel;
    }

    public void setAvailableRolesSel(String[] availableRolesSel) {
        this.availableRolesSel = availableRolesSel;
    }

    public List<String> getAuthRolesList() {
        return authRolesList;
    }

    public List<Role> getAvailableRolesList() {
        return availableRolesList;
    }

    public String getSelec1() {
        return selec1;
    }

    public void setSelec1(String selec1) {
        this.selec1 = selec1;
    }

    public void initMapStatus() {
        //Prepara tipos de status para radio element
        mapStatus.put(0, this.getText("received"));
        mapStatus.put(1, this.getText("waiting"));
        mapStatus.put(2, this.getText("authorized"));
    }

    public void setRequest(Map map) {
        this.requestáttributes = map;
    }
}
