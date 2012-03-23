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
public class Section  implements Serializable{

        private static final long serialVersionUID = 106L;
    private String id;
    private String name;
    private String authorizedRoles;
    private String group;

    public Section() {
    }

    public String getAuthorizedRoles() {
        return authorizedRoles;
    }

    public void setAuthorizedRoles(String authorizedRoles) {
        this.authorizedRoles = authorizedRoles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Section other = (Section) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

}