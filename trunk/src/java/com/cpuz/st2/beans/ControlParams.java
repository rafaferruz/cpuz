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
package com.cpuz.st2.beans;

import com.cpuz.domain.UserType;
import com.cpuz.tools.Const;
import java.io.Serializable;

/**
 * Esta clase se utiliza para pasar parámetros comunes a las clases de servicio,
 * a las clases DAO y a las vistas JSP
 */
public class ControlParams implements Serializable {

	private String runAction = "";
	private int id;
	private int recStart;
	private int recChunk = Const.LIMIT_SQL_RECORDS_SELECTED_MANAGEMENT;
	private int recCount;
	private UserType userType = UserType.ANONYMOUS;

	public ControlParams() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRecChunk() {
		return recChunk;
	}

	public void setRecChunk(int recChunk) {
		this.recChunk = recChunk;
	}

	public int getRecCount() {
		return recCount;
	}

	public void setRecCount(int recCount) {
		this.recCount = recCount;
	}

	public int getRecStart() {
		return recStart;
	}

	public void setRecStart(int recStart) {
		this.recStart = recStart;
	}

	public String getRunAction() {
		return runAction;
	}

	public void setRunAction(String runAction) {
		this.runAction = runAction;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public void doNavigation() {
		switch (getRunAction()) {
			case "nav_first":
				nav_first();
				break;
			case "nav_prev":
				nav_prev();
				break;
			case "nav_next":
				nav_next();
				break;
			case "nav_last":
				nav_last();
				break;
		}
	}

	private void nav_first() {
		this.setRecStart(0);
	}

	private void nav_prev() {
		this.setRecStart(Math.max(0, (this.getRecStart() - this.getRecChunk())));
	}

	private void nav_next() {
		this.setRecStart(this.getRecStart() + this.getRecChunk());
	}

	private void nav_last() {
		this.setRecStart(Math.max(0, (this.getRecCount() - this.getRecChunk())));
	}
}
