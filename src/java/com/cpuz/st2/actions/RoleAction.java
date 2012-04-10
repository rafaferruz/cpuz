/*
 * Copyright 2012 Rafael Ferruz
 * 
 * This file is part of CPUZ.
 * 
 * Foobar is free software: you can redistribute it and/or modify
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

import com.cpuz.domain.Role;
import com.cpuz.domain.UserType;
import com.cpuz.model.RolesModel;
import com.cpuz.st2.beans.ControlParams;
import com.opensymphony.xwork2.ActionSupport;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.RequestAware;

/**
 * Esta clase gestiona las operaciones CRUD de los objetos Role de la aplicación
 */
public class RoleAction extends ActionSupport implements RequestAware, Serializable {

	private ControlParams control = new ControlParams();
	private List<Role> dataList = new ArrayList<>();
	private Role dataEdit = new Role();
	private RolesModel dataModel;
	private Map<Integer, String> mapStatus = new HashMap<>();
	private Map<String, Object> requestAttributes = new HashMap<>();
	private String selec1;

	public RoleAction() {
		super();
	}

	@Override
	public String execute() {
		return "error";
	}

	public String roleNew() {

		control.setRecCount(1);
		control.setRunAction("new");
		requestAttributes.put("page", "/WEB-INF/views/roleEdit.jsp");
		return "New";
	}

	public String roleEdit() throws SQLException {
		dataEdit = dataModel.getById(control.getId());
		control.setRunAction("edit");
		requestAttributes.put("page", "/WEB-INF/views/roleEdit.jsp");
		return "Edit";
	}

	public String roleSaveNew() throws SQLException{
		if (dataModel.insertRole(dataEdit) == 1) {
			this.addActionMessage(getText("RoleEditSaveOkMsg"));
			return roleList();
		}
		return "Edit";
	}

	public String roleSaveEdit() throws SQLException {

		if (dataModel.keyIdExists(dataEdit.getId())) {
			try {
				dataModel.updateRole(dataEdit);
				this.addActionMessage(getText("RoleEditSaveOkMsg"));
			} catch (SQLException ex) {
				this.addActionError(getText("RoleEditErrorMsg"));
				return "Edit";
			}
			return roleList();
		}
		return "New";
	}

	public String roleDeleteIds() throws SQLException {
		if (selec1 != null) {
			String[] deletes = selec1.split(",");
			if (dataModel.deleteRoleIds(Arrays.asList(deletes)) > 0) {
				addActionMessage(getText("SuccessDeletedRoles"));
			} else {
				addActionError(getText("NoneDeletedRole"));
			}
			return roleList();
		}
		addActionError(getText("NoneSelectedRole"));
		return roleList();
	}

	public String roleList() throws SQLException {
		if (control.getRecCount() == 0) {
			control.setRecCount(dataModel.getCountRows());
		}
		control.setUserType(UserType.ADMIN);
		dataList = dataModel.getRoleList(control);
		control.setRunAction("List");
		requestAttributes.put("page", "/WEB-INF/views/roleList.jsp");
		return "List";
	}

	public String roleNavigation() throws SQLException {
		control.doNavigation();
		return roleList();
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

	public void setMapStatus(Map<Integer, String> mapStatus) {
		this.mapStatus = mapStatus;
	}

	public Role getDataEdit() {
		return dataEdit;
	}

	public void setDataEdit(Role dataEdit) {
		this.dataEdit = dataEdit;
	}

	public List<Role> getDataList() {
		return dataList;
	}

	public void setDataList(List<Role> dataList) {
		this.dataList = dataList;
	}

	public void setDataModel(RolesModel dataModel) {
		this.dataModel = dataModel;
	}

	public String getSelec1() {
		return selec1;
	}

	public void setSelec1(String selec1) {
		this.selec1 = selec1;
	}

	public void initMapStatus() {
		//Prepara tipos de status para radio element
		mapStatus.put(0, this.getText("received"));
		mapStatus.put(1, this.getText("waiting"));
		mapStatus.put(2, this.getText("authorized"));
	}

	public void setRequest(Map map) {
		this.requestAttributes = map;
	}
}
