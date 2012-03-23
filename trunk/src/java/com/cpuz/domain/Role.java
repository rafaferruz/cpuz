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
public class Role  implements Serializable{

    private static final long serialVersionUID = 103L;

    private Integer id;
    private String role;
    private String description;

    public Role(){

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Role other = (Role) obj;
        if ((this.role == null) ? (other.role != null) : !this.role.equals(other.role)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (this.role != null ? this.role.hashCode() : 0);
        return hash;
    }

}
