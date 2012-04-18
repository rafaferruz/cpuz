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

import com.cpuz.domain.Bug;
import com.cpuz.domain.BugStatusType;
import com.cpuz.domain.BugType;
import com.cpuz.model.BugsModel;
import com.cpuz.st2.beans.ControlParams;
import com.opensymphony.xwork2.ActionSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

/**
 * Esta clase gestiona las operaciones CRUD de los objetos Bug de la aplicación
 */
public class BugAction extends ActionSupport implements RequestAware, SessionAware, Serializable {

	private ControlParams control = new ControlParams();
	private List<Bug> dataList = new ArrayList<>();
	private Bug dataEdit = new Bug();
	private BugsModel dataModel;
	private Map<Integer, String> mapStatus = new HashMap<>();
	private List<String> listTypes = new ArrayList<>();
	private Map<Integer, String> mapScopes = new HashMap<>();
	private Map<String, Object> requestAttributes;
	private Map<String, Object> sessionAttributes;
	private String selec1;

	public BugAction() {
		super();
	}

	@Override
	public String execute() throws Exception {
		return "error";
	}

	public String bugNew() throws Exception {

		initMapStatus();
		initListTypes();
		control.setRecCount(1);
		control.setRunAction("new");
		requestAttributes.put("page", "/WEB-INF/views/bugEdit.jsp");
		return "New";
	}

	public String bugEdit() throws Exception {
		dataEdit = dataModel.getById(control.getId());
		initMapStatus();
		initListTypes();
		control.setRunAction("edit");
		requestAttributes.put("page", "/WEB-INF/views/bugEdit.jsp");
		return "Edit";
	}

	public String bugSaveNew() throws Exception {
		dataEdit.setUser((String) sessionAttributes.get("user"));

		if (dataModel.insertBug(dataEdit) == 1) {
			this.addActionMessage(getText("BugEditSaveOkMsg"));
			return bugList();
		}
		return "Edit";
	}

	public String bugSaveEdit() throws Exception {

		if (dataModel.keyIdExists(dataEdit.getId())) {
			try {
				dataModel.updateBug(dataEdit);
				this.addActionMessage(getText("BugEditSaveOkMsg"));
			} catch (Exception ex) {
				this.addActionError(getText("BugEditErrorMsg"));
				return "Edit";
			}
			return bugList();
		}
		return "New";
	}

	public String bugDelete() throws Exception {
		if (selec1 != null) {
			String[] deletes = selec1.split(",");
			if (dataModel.deleteBugIds(Arrays.asList(deletes)) > 0) {
				addActionMessage(getText("SuccessDeletedBugs"));
			} else {
				addActionError(getText("NoneDeletedBug"));
			}
			return bugList();
		}
		addActionError(getText("NoneSelectedRole"));
		return bugList();
	}

	public String bugList() throws Exception {
		if (control.getRecCount() == 0) {
			control.setRecCount(dataModel.getCountRows());
		}
		dataList = dataModel.getBugList(control);
		control.setRunAction("list");
		requestAttributes.put("page", "/WEB-INF/views/bugList.jsp");
		return "List";
	}

	public String BugNavigation() throws Exception {
		control.doNavigation();
		return bugList();
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

	public Map<Integer, String> getMapStatus() {
		return mapStatus;
	}

	public void setMapStatus(HashMap<Integer, String> mapStatus) {
		this.mapStatus = mapStatus;
	}

	public Bug getDataEdit() {
		return dataEdit;
	}

	public void setDataEdit(Bug dataEdit) {
		this.dataEdit = dataEdit;
	}

	public List<Bug> getDataList() {
		return dataList;
	}

	public void setDataList(List<Bug> dataList) {
		this.dataList = dataList;
	}

	public void setDataModel(BugsModel dataModel) {
		this.dataModel = dataModel;
	}

	public String getSelec1() {
		return selec1;
	}

	public void setSelec1(String selec1) {
		this.selec1 = selec1;
	}

	public List<String> getListTypes() {
		return listTypes;
	}

	public void setListTypes(List<String> listTypes) {
		this.listTypes = listTypes;
	}

	public Map<Integer, String> getMapScopes() {
		return mapScopes;
	}

	public void setMapScopes(Map<Integer, String> mapScopes) {
		this.mapScopes = mapScopes;
	}

	public void initMapStatus() {
		//Prepara tipos de status para radio element
		mapStatus.put(BugStatusType.CREATED.getId(), this.getText(BugStatusType.CREATED.getKey()));
		mapStatus.put(BugStatusType.INCOURSE.getId(), this.getText(BugStatusType.INCOURSE.getKey()));
		mapStatus.put(BugStatusType.ENDED.getId(), this.getText(BugStatusType.ENDED.getKey()));
	}

	public void initListTypes() {
		//Prepara tipos de status para radio element
		Iterator it = Arrays.asList(BugType.values()).iterator();
		while (it.hasNext()) {
			listTypes.add(((BugType) it.next()).getKey());
		}
	}

	public void setRequest(Map map) {
		this.requestAttributes = map;
	}

	public void setSession(Map map) {
		this.sessionAttributes = map;
	}

}