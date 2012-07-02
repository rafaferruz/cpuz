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
package com.cpuz.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author RAFAEL FERRUZ
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
public class NewsPiece implements Serializable{

    private static final long serialVersionUID = 101L;

    private int id;
    private java.util.Date datetime;
    private int status;       // Puede ser: 0=Deshabilitado, 1=Revisado pendiente, 2=Autorizado
    private String user;
    private int sectionId;
    private String description;
    private String showParameters; // definiciones clave=valor separadas por el caracter ';' para pasar parámetros de configuración de presentación
    // colspan=x; para indicar el número de columnas de la tabla que ocupara la noticia
    // charsintroduction=xxx; para indicar los caracteres de introducción que se presentarón en las páginas generales de noticias
    private int scope;        // Puede ser : Global, Vecinal, Confidencial
    private int access;       // Puede ser: Restáicted Solamente accede el propietario, o
                                  //            ALL
    private boolean registered;
	private List<NewsComposition> newsCompositionList=new ArrayList<>();

    public NewsPiece() {
    }

    public NewsPiece(Integer id) {
        this.id = id;
    }

    public Integer getAccess() {
        return access;
    }

    public void setAccess(Integer access) {
        this.access = access;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getShowParameters() {
        return showParameters;
    }

    public void setShowParameters(String showParameters) {
        this.showParameters = showParameters;
    }

	public List<NewsComposition> getNewsCompositionList() {
		return newsCompositionList;
	}

	public void setNewsCompositionList(List<NewsComposition> newsCompositionList) {
		this.newsCompositionList = newsCompositionList;
	}

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NewsPiece other = (NewsPiece) obj;
        if (this.id != other.id && (this.id!=other.id)) {
            return false;
        }
        return true;
    }

    @Override

	public int hashCode() {
		int hash = 7;
		hash = 29 * hash + this.id;
		return hash;
	}

}