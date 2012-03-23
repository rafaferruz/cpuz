/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.model;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class UserService {

    private String user;
    private String role;
    private boolean userInRole;

    public UserService() {
    }

    public UserService(String user) {
        this.user=user;
    }

    public UserService(String user,String role) {
        this.user=user;
        this.role=role;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isUserInRole() {
        return isUserInRole(this.getRole());
    }

    public void setUserInRole(boolean userInRole) {
        this.userInRole = userInRole;
    }

    public boolean isUserInRole(String role) {
        if (this.user != null && !this.user.equals("")) {
            UserRolesModel userRolesModel = new UserRolesModel();
            return !userRolesModel.getNewsRecords("SELECT * FROM userroles WHERE usu_user = '" + this.user + "' "
                    + " AND usr_role='" + this.role + "'").isEmpty();
        }
        return false;
    }
}
