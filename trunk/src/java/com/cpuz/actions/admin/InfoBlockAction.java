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

import com.cpuz.domain.InfoBlock;
import com.cpuz.domain.NewsComposition;
import com.cpuz.domain.NewsPiece;
import com.cpuz.domain.UserType;
import com.cpuz.service.InfoBlocksService;
import com.cpuz.service.NewsCompositionsService;
import com.cpuz.service.NewsPiecesService;
import com.cpuz.st2.beans.ControlParams;
import com.opensymphony.xwork2.ActionSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class InfoBlockAction extends ActionSupport implements RequestAware, SessionAware, Serializable {

	private ControlParams control = new ControlParams();
	private List<InfoBlock> dataList = new ArrayList<>();
	private InfoBlock dataEdit = new InfoBlock();
	private InfoBlocksService dataService;
	private Map<Integer, String> mapStatus = new HashMap<>();
	private List<String> listTypes = new ArrayList<>();
	private Map<Integer, String> mapScopes = new HashMap<>();
	private Map<String, Object> requestAttributes;
	private Map<String, Object> sessionAttributes;
	private String selec1;

	public InfoBlockAction() {
		super();
	}

	@Override
	public String execute() throws Exception {
		return "error";
	}

	public String infoBlockNew() throws Exception {
		dataEdit.setType(this.getText("title"));
		control.setRecCount(1);
		control.setRunAction("new");
		requestAttributes.put("page", "/WEB-INF/views/infoBlockEdit.jsp");
		return "new";
	}

	public String infoBlockEdit() throws Exception {
		dataEdit = dataService.getById(control.getId());
		control.setRunAction("edit");
		requestAttributes.put("page", "/WEB-INF/views/infoBlockEdit.jsp");
		return "edit";
	}

	public String infoBlockSaveNew() throws Exception {
		dataEdit.setUser((String) sessionAttributes.get("user"));
		if (dataService.insertInfoBlock(dataEdit) == 1) {
			this.addActionMessage(getText("InfoBlockEditSaveOkMsg"));
			infoBlockList();
		}
		return "edit";
	}

	public String infoBlockSaveEdit() {
		try {
			if (dataService.keyIdExists(dataEdit.getId())) {
				dataService.updateInfoBlock(dataEdit);
				this.addActionMessage(getText("InfoBlockEditSaveOkMsg"));
				return infoBlockList();
			} else {
				this.addActionError(getText("InfoBlockEditErrorMsg"));
			}
		} catch (Exception ex) {
			this.addActionError(getText("InfoBlockEditErrorMsg"));
		}
		return "edit";
	}

	public String infoBlockDelete() throws Exception {
		if (selec1 != null) {
			String[] deletes = selec1.split(",");
			List<Integer> ids = new ArrayList<>();
			for (int i = 0; i < deletes.length; i++) {
				ids.add(Integer.parseInt(deletes[i]));
			}
			if (dataService.deleteInfoBlockIds(ids) > 0) {
				addActionMessage(getText("SuccessDeletedInfoBlocks"));
			} else {
				addActionError(getText("NoneDeletedInfoBlock"));
			}
			return infoBlockList();
		}
		addActionError(getText("NoneSelectedRole"));
		return infoBlockList();
	}

	public String infoBlockList() throws Exception {
		if (control.getRecCount() == 0) {
			control.setRecCount(dataService.getCountRows());
		}
		control.setUserType(UserType.ADMIN);
		dataList = dataService.getInfoBlockList(control);
		control.setRunAction("list");
		requestAttributes.put("page", "/WEB-INF/views/infoBlockList.jsp");
		return "list";
	}

	public String infoBlockNavigation() throws Exception {
		control.doNavigation();
		return infoBlockList();
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

	public InfoBlock getDataEdit() {
		return dataEdit;
	}

	public void setDataEdit(InfoBlock dataEdit) {
		this.dataEdit = dataEdit;
	}

	public List<InfoBlock> getDataList() {
		return dataList;
	}

	public void setDataList(List<InfoBlock> dataList) {
		this.dataList = dataList;
	}

	public void setDataService(InfoBlocksService dataService) {
		this.dataService = dataService;
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

	public void setRequest(Map map) {
		this.requestAttributes = map;
	}

	public void setSession(Map map) {
		this.sessionAttributes = map;
	}

	public String NewsPieceSaveNew() throws Exception {
		NewsPiece dataNewsPiece = new NewsPiece();
		dataNewsPiece.setUser((String) sessionAttributes.get("user"));
		dataNewsPiece.setDatetime(new Date());
		dataNewsPiece.setStatus(0);
		dataNewsPiece.setDescription(dataEdit.getHeader());
		dataNewsPiece.setScope(1);

		NewsPiecesService newsPieceService = new NewsPiecesService();
		if (newsPieceService.insertNewsPiece(dataNewsPiece) == 1) {
			control.setId(dataNewsPiece.getId());
			NewsComposition dataNewsComposition = new NewsComposition();
			dataNewsComposition.setOrder(1);
			dataNewsComposition.setIdNpi(dataNewsPiece.getId());
			dataNewsComposition.setComponentType("InfoBlock");
			dataNewsComposition.setIdComponent(dataEdit.getId());
			dataNewsComposition.setHeaderAlt(dataEdit.getHeader());
			dataNewsComposition.setHeaderStyle(dataEdit.getType());
			dataNewsComposition.setBodyAbstract(dataEdit.getBody().substring(0, Math.min(dataEdit.getBody().length() - 1, 255)));
			dataNewsComposition.setImageHigh(0);
			dataNewsComposition.setImageWidth(0);
			dataNewsComposition.setLinkedElement("");
// Graba la nueva Composition en la BD
			NewsCompositionsService newsCompositionsService = new NewsCompositionsService();
			newsCompositionsService.setNewRecord(dataNewsComposition);
		}
		return "EDIT_NEWSPIECE";
	}

	public String InfoBlockCreateNewsPiece() throws Exception {

		String option = control.getRunAction();
		if (option.equals("edit")) {
			infoBlockSaveEdit();
		} else if (option.equals("new")) {
			infoBlockSaveNew();
		} else {
			return "list";
		}
		return NewsPieceSaveNew();
	}
}
