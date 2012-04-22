/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.st2.actions;

import com.cpuz.domain.Role;
import com.cpuz.domain.Section;
import com.cpuz.domain.UserRole;
import com.cpuz.service.RolesService;
import com.cpuz.service.SectionsService;
import com.cpuz.service.UserRolesService;
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
    private SectionsService dataService;
    private Map<Integer, String> mapStatus = new HashMap<Integer, String>();
    private Map<String, Object> requestáttributes = new HashMap<String, Object>();
    private List<Role> availableRolesList;
    private List<String> authRolesList;
    private RolesService rolesService;
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
        availableRolesList = rolesService.getNewsRecords();

        control.setRecCount(1);
        control.setRunAction("new");
        requestáttributes.put("page", "/WEB-INF/views/SectionEdit.jsp");
        return "NEW";
    }

    public String Section_edit() throws Exception {
        dataEdit = dataService.getRecords("SELECT * FROM sections WHERE sec_id = '"
                + control.getId() + "'", "", "").get(0);
        // Se lee lista de Roles
        availableRolesList = rolesService.getNewsRecords();

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
        if (dataService.setNewRecord(dataEdit) == 1) {
            this.addActionMessage(getText("SectionEditSaveOkMsg"));
            return Section_list();
        }
        return "EDIT";
    }

    public String Section_saveEdit() throws Exception {

        dataEdit.setAuthorizedRoles(authRolesSel.replaceAll(" ", ""));
        if (dataService.keyIdExists(dataEdit.getId().toString())) {
            try {
                dataService.setUpdateRecord(dataEdit);
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
                if (dataService.deleteNews(dataEdit) == 1) {
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
            dataList = dataService.getRecords("SELECT * FROM sections ", "", "");
            control.setRecCount(dataList.size());
        }
        dataList = dataService.getRecords("SELECT * FROM sections "
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

    public void setDataService(SectionsService dataService) {
        this.dataService = dataService;
    }

    public void setAuthRolesList(List<String> authRolesList) {
        this.authRolesList = authRolesList;
    }

    public void setAvailableRolesList(List<Role> availableRolesList) {
        this.availableRolesList = availableRolesList;
    }

    public void setRolesService(RolesService rolesService) {
        this.rolesService = rolesService;
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
