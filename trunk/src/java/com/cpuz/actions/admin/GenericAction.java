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
package com.cpuz.actions.admin;

import com.cpuz.exceptions.UserException;
import com.cpuz.st2.beans.ControlParams;
import com.opensymphony.xwork2.ActionSupport;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.RequestAware;

/**
 * Esta clase abstract gestiona las operaciones CRUD de los objetos de dominio de la aplicación
 */
public abstract class GenericAction<T> extends ActionSupport implements RequestAware, Serializable {

	ControlParams control = new ControlParams();
	List<T> dataList = new ArrayList<>();
	T dataEdit;
	Map<Integer, String> mapStatus = new HashMap<>();
	Map<String, Object> requestAttributes = new HashMap<>();
	String selec1;

	public GenericAction() {
		super();
	}

	@Override
	public String execute() {
		return "error";
	}

	public abstract String objectNew() throws SQLException ;

	public abstract String objectEdit() throws SQLException, UserException;

	public abstract String objectSaveNew() throws SQLException, UserException;

	public abstract String objectSaveEdit() throws SQLException, UserException;

	public abstract String objectDelete() throws SQLException;

	public abstract String objectList() throws SQLException;

	public String objectNavigation() throws SQLException {
		control.doNavigation();
		return objectList();
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

	public T getDataEdit() {
		return this.dataEdit;
	}

	public void setDataEdit(T dataEdit) {
		this.dataEdit = dataEdit;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public abstract Object getDataService();

	public abstract void setDataService(Object dataService);

	public String getSelec1() {
		return selec1;
	}

	public void setSelec1(String selec1) {
		this.selec1 = selec1;
	}

	public Map<Integer, String> getMapStatus() {
		return mapStatus;
	}

	public void setMapStatus(Map<Integer, String> mapStatus) {
		this.mapStatus = mapStatus;
	}

	public void setRequest(Map map) {
		this.requestAttributes = map;
	}

	public Map<String, Object> getRequestAttributes() {
		return requestAttributes;
	}
}
