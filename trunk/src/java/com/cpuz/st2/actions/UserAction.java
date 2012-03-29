package com.cpuz.st2.actions;

import com.cpuz.domain.Role;
import com.cpuz.domain.User;
import com.cpuz.domain.UserRole;
import com.cpuz.model.RolesModel;
import com.cpuz.model.UserRolesModel;
import com.cpuz.model.UserModel;
import com.cpuz.st2.beans.ListControlParams;
import com.opensymphony.xwork2.ActionSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.RequestAware;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class UserAction extends ActionSupport implements RequestAware, Serializable {

    private ListControlParams control = new ListControlParams();
    private List<User> dataList = new ArrayList<>();
    private User dataEdit = new User();
    private UserModel dataModel;
    private Map<Integer, String> mapStatus = new HashMap<>();
    private Map<Integer, String> mapCategories = new HashMap<>();
    private Map<String, Object> requestáttributes = new HashMap<>();
    private List<Role> rolesList;
    private List<UserRole> userRolesList;
    private RolesModel rolesModel;
    private UserRolesModel userRolesModel;
    private String[] authRolesSel;
    private String[] availableRolesSel;
    private String selec1;
    private String password_again;

    public UserAction() {
        super();
    }

    @Override
    public String execute() throws Exception {
        return "error";
    }

    public String User_new() throws Exception {
        initMapStatus();
        initMapCategories();
        rolesList = rolesModel.getNewsRecords();
        userRolesList.clear();
        control.setRecCount(1);
        control.setRunAction("new");
        dataEdit.setDate(new Date());
        requestáttributes.put("page", "/WEB-INF/views/UserEdit.jsp");
        return "NEW";
    }

    public String User_edit() throws Exception {
        dataEdit = dataModel.getRecords("SELECT * FROM users WHERE usu_id = "
                + control.getId(), "", "").get(0);
        // Se lee lista de Users
        password_again = dataEdit.getPassword();

        rolesList = rolesModel.getNewsRecords();
        userRolesList = userRolesModel.getNewsRecords("SELECT * FROM  userroles "
                + " WHERE usu_user = '" + dataEdit.getUser() + "'");
        for (UserRole ur : userRolesList) {
            if (!ur.getRole().equals("")) {
                for (Role r : rolesList) {
                    if (ur.getRole().equals(r.getRole())) {
                        rolesList.remove(r);
                        break;
                    }
                }
            }
        }
        initMapStatus();
        initMapCategories();
        control.setRunAction("edit");
        requestáttributes.put("page", "/WEB-INF/views/UserEdit.jsp");
        return "EDIT";
    }

    public String User_saveNew() throws Exception {

        try {
            if (dataModel.setNewRecord(dataEdit) == 1) {
                try {
                    // Elimina todos Roles del Usuario y crea los Roles de Usuario que
                    // se encuentren en asignados
                    updateUserRoles();
                } catch (Exception ex) {
                    this.addActionError(getText("UserEditErrorMsg"));
                    return "NEW";
                }
            } else {
                this.addActionError(getText("UserEditErrorMsg"));
                return "NEW";
            }
        } catch (Exception ex) {
            this.addActionError(getText("UserEditErrorMsg"));
            return "NEW";
        }
        this.addActionMessage(getText("UserEditSaveOkMsg"));
        return User_list();
    }

    public String User_saveEdit() throws Exception {

        if (dataModel.keyIdExists(dataEdit.getId())) {
            try {
                // Elimina todos Roles del Usuario y crea los Roles de Usuario que
                // se encuentren en asignados
                if (dataEdit.getPassword().equals("")) {
                    dataEdit.setPassword(dataModel.getRecords("SELECT * FROM users "
                            + " WHERE usu_id = " + dataEdit.getId(), "", "").get(0).getPassword());
                }
                updateUserRoles();
            } catch (Exception ex) {
                this.addActionError(getText("UserEditErrorMsg"));
                return "EDIT";
            }
            try {
                dataModel.setUpdateRecord(dataEdit);
                this.addActionMessage(getText("UserEditSaveOkMsg"));
            } catch (Exception ex) {
                this.addActionError(getText("UserEditErrorMsg"));
                return "EDIT";
            }
            return User_list();

        }
        return "NEW";
    }

    public String User_delete() throws Exception {
        if (selec1 != null) {
            String[] deletes = selec1.split(",");
            for (int i = 0; i < deletes.length; i++) {
                try {
                    dataEdit.setUser(deletes[i].trim().split(";")[1]);
                    dataEdit.setId(Integer.parseInt(deletes[i].trim().split(";")[0]));
                    userRolesModel.deleteRecords(" WHERE usu_user = " + dataEdit.getUser());
                    if (dataModel.deleteNews(dataEdit) == 1) {
                        addActionMessage(deletes[i] + " " + getText("SuccessDeletedUser"));
                    } else {
                        addActionError(deletes[i] + " " + getText("NoneDeletedUser"));
                    }
                } catch (Exception ex) {
                    addActionError(deletes[i] + " " + getText("NoneDeletedUser"));
                }
            }
            return User_list();
        }
        addActionError(getText("NoneSelectedUser"));
        return User_list();
    }

    public String User_list() throws Exception {
        if (control.getRecCount() == 0) {
            dataList = dataModel.getRecords("SELECT * FROM users ", "", "");
            control.setRecCount(dataList.size());
        }
        dataList = dataModel.getRecords("SELECT * FROM users "
                + " LIMIT " + control.getRecChunk().toString()
                + " OFFSET " + control.getRecStart().toString(), "", "");
        control.setRunAction("list");
        requestáttributes.put("page", "/WEB-INF/views/UserList.jsp");
        return "LIST";
    }

    public String User_Navigation() throws Exception {
        control.doNavigation();
        return User_list();
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

    public Map<Integer, String> getMapCategories() {
        return mapCategories;
    }

    public void setMapCategories(Map<Integer, String> mapCategories) {
        this.mapCategories = mapCategories;
    }

    public User getDataEdit() {
        return dataEdit;
    }

    public void setDataEdit(User dataEdit) {
        this.dataEdit = dataEdit;
    }

    public List<User> getDataList() {
        return dataList;
    }

    public void setDataList(List<User> dataList) {
        this.dataList = dataList;
    }

    public void setDataModel(UserModel dataModel) {
        this.dataModel = dataModel;
    }

    public String[] getAuthRolesSel() {
        return authRolesSel;
    }

    public void setAuthRolesSel(String[] authRolesSel) {
        this.authRolesSel = authRolesSel;
    }

    public String[] getAvailableRolesSel() {
        return availableRolesSel;
    }

    public void setAvailableRolesSel(String[] availableRolesSel) {
        this.availableRolesSel = availableRolesSel;
    }

    public String getSelec1() {
        return selec1;
    }

    public void setSelec1(String selec1) {
        this.selec1 = selec1;
    }

    public void setRolesModel(RolesModel rolesModel) {
        this.rolesModel = rolesModel;
    }

    public void setUserRolesModel(UserRolesModel userRolesModel) {
        this.userRolesModel = userRolesModel;
    }

    public void setRolesList(List<Role> rolesList) {
        this.rolesList = rolesList;
    }

    public void setUserRolesList(List<UserRole> userRolesList) {
        this.userRolesList = userRolesList;
    }

    public List<Role> getRolesList() {
        return rolesList;
    }

    public List<UserRole> getUserRolesList() {
        return userRolesList;
    }

    public String getPassword_again() {
        return password_again;
    }

    public void setPassword_again(String password_again) {
        this.password_again = password_again;
    }

    public void initMapStatus() {
        //Prepara tipos de status para radio element
        mapStatus.put(0, this.getText("received"));
        mapStatus.put(1, this.getText("waiting"));
        mapStatus.put(2, this.getText("authorized"));
    }

    public void initMapCategories() {
        //Prepara tipos de status para radio element
        mapCategories.put(0, this.getText("basic"));
        mapCategories.put(1, this.getText("manager"));
        mapCategories.put(2, this.getText("admin"));
    }

    @Override
    public void setRequest(Map map) {
        this.requestáttributes = map;
    }

    private void updateUserRoles() {
        userRolesModel.deleteRecords(" WHERE usu_user = '" + dataEdit.getUser() + "'");
        if (authRolesSel.length > 0) {
            for (int i = 0; i < authRolesSel.length; i++) {
                UserRole ur = new UserRole();
                ur.setUser(dataEdit.getUser());
                ur.setRole(authRolesSel[i]);
                ur.setEstado(0);
                ur.setDescription("");
                userRolesModel.setNewRecord(ur);
            }
        }
    }
}
