/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class User  implements Serializable{

    private static final long serialVersionUID = 104L;
    private Integer id;
    private java.util.Date date;
    private Integer status = 0;
    private Integer category = 0;
    private String user;
    private String name;
    private String password;
    private String email;

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
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
        final User other = (User) obj;
        if ((this.user == null) ? (other.user != null) : !this.user.equals(other.user)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + (this.user != null ? this.user.hashCode() : 0);
        return hash;
    }
}
