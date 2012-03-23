/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cpuz.domain;

import java.io.Serializable;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class UserRole  implements Serializable{

        private static final long serialVersionUID = 105L;
    private Integer id;
    private Integer estádo;
    private String user;
    private String role;
    private String description;

    public UserRole() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEstado() {
        return estádo;
    }

    public void setEstado(Integer estádo) {
        this.estádo = estádo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserRole other = (UserRole) obj;
        if ((this.user == null) ? (other.user != null) : !this.user.equals(other.user)) {
            return false;
        }
        if ((this.role == null) ? (other.role != null) : !this.role.equals(other.role)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.user != null ? this.user.hashCode() : 0);
        return hash;
    }

}
