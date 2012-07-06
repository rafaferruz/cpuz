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

/**
 * Clase que define los objetos Section de la aplicación
 */

public class Section  implements Serializable{

    private int id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Section other = (Section) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	public int hashCode() {
		int hash = 7;
		hash = 89 * hash + this.id;
		return hash;
	}


}