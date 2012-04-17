/*
 * Copyright 2012 Rafael Ferruz
 * 
 * This file is part of CPUZ.
 * 
 * CPUZ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * CPUZ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with CPUZ.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.cpuz.st2.actions;

import com.cpuz.domain.Role;
import com.cpuz.domain.User;
import com.cpuz.domain.UserRole;
import com.cpuz.model.RolesModel;
import com.cpuz.model.UserRolesModel;
import com.cpuz.model.UserModel;
import com.cpuz.st2.beans.ControlParams;
import com.opensymphony.xwork2.ActionSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.RequestAware;

/**
 * Esta clase gestiona las operaciones CRUD de los objetos User de la aplicación
 */
public class UserAction extends ActionSupport implements RequestAware, Serializable {

    private ControlParams control = new ControlParams();
    private List<User> dataList = new ArrayList<>();
    private User dataEdit = new User();
    private UserModel dataModel;
    private Map<String, Object> requestAttributes = new HashMap<>();
    private List<Role> rolesList;
    private List<UserRole> userRolesList;
    private RolesModel rolesModel;
    private UserRolesModel userRolesModel;
    private String[] authRolesSel;
    private String[] availableRolesSel;
    private String selec1;
    private String passwordAgain;

    public UserAction() {
        super();
    }

    @Override
    public String execute(){
        return "error";
    }

    public String UserNew(){
        control.setRecCount(1);
        control.setRunAction("new");
        dataEdit.setDate(new Date());
        requestAttributes.put("page", "/WEB-INF/views/UserEdit.jsp");
        return "New";
    }

    public String UserEdit() throws Exception {
        dataEdit = dataModel.getById(control.getId());
        // Se lee lista de Users
        passwordAgain = dataEdit.getPassword();
        rolesList = rolesModel.getNewsRecords();
        userRolesList = userRolesModel.getNewsRecords("SELECT * FROM  userroles "
                + " WHERE usu_user = '" + dataEdit.getUser() + "'");
		List<Role>removeRoles=new ArrayList<>();
        for (UserRole ur : userRolesList) {
            if (!ur.getRole().equals("")) {
                for (Role r : rolesList) {
                    if (ur.getRole().equals(r.getRole())) {
                        removeRoles.add(new Role(r.getRole()));
                        break;
                    }
                }
            }
        }
		rolesList.removeAll(removeRoles);
        control.setRunAction("edit");
        requestAttributes.put("page", "/WEB-INF/views/UserEdit.jsp");
        return "Edit";
    }

    public String UserSaveNew() throws Exception {

        try {
            if (dataModel.setNewRecord(dataEdit) == 1) {
                try {
                    // Elimina todos Roles del Usuario y crea los Roles de Usuario que
                    // se encuentren en asignados
                    updateUserRoles();
                } catch (Exception ex) {
                    this.addActionError(getText("UserEditErrorMsg"));
                    return "New";
                }
            } else {
                this.addActionError(getText("UserEditErrorMsg"));
                return "New";
            }
        } catch (Exception ex) {
            this.addActionError(getText("UserEditErrorMsg"));
            return "New";
        }
        this.addActionMessage(getText("UserEditSaveOkMsg"));
        return UserList();
    }

    public String UserSaveEdit() throws Exception {

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
            return UserList();

        }
        return "NEW";
    }

    public String UserDelete() throws Exception {
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
            return UserList();
        }
        addActionError(getText("NoneSelectedUser"));
        return UserList();
    }

    public String UserList() throws Exception {
        if (control.getRecCount() == 0) {
            dataList = dataModel.getRecords("SELECT * FROM users ", "", "");
            control.setRecCount(dataList.size());
        }
        dataList = dataModel.getRecords("SELECT * FROM users "
                + " LIMIT " + control.getRecChunk().toString()
                + " OFFSET " + control.getRecStart().toString(), "", "");
        control.setRunAction("list");
        requestAttributes.put("page", "/WEB-INF/views/UserList.jsp");
        return "LIST";
    }

    public String User_Navigation() throws Exception {
        control.doNavigation();
        return UserList();
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

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String password_again) {
        this.passwordAgain = password_again;
    }

    @Override
    public void setRequest(Map map) {
        this.requestAttributes = map;
    }

    private void updateUserRoles() {
        userRolesModel.deleteRecords(" WHERE usu_user = '" + dataEdit.getUser() + "'");
        if (authRolesSel.length > 0) {
            for (int i = 0; i < authRolesSel.length; i++) {
                UserRole ur = new UserRole();
                ur.setUser(dataEdit.getUser());
                ur.setRole(authRolesSel[i]);
                ur.setStatus(0);
                ur.setDescription("");
                userRolesModel.setNewRecord(ur);
            }
        }
    }
}
