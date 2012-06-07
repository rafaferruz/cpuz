/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.actions.admin;

import com.cpuz.domain.NewsComposition;
import com.cpuz.domain.NewsPiece;
import com.cpuz.domain.Section;
import com.cpuz.service.NewsCompositionsService;
import com.cpuz.service.NewsPiecesService;
import com.cpuz.service.SectionsService;
import com.cpuz.st2.beans.ControlParams;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class NewsPresentationAction extends ActionSupport implements RequestAware, SessionAware, Serializable {

	private ControlParams control = new ControlParams();
	private List<NewsComposition> dataList = new ArrayList<>();
	private NewsComposition dataEdit = new NewsComposition();
	private NewsCompositionsService dataService;
	private Map<Integer, String> mapStatus = new HashMap<>();
	private Map<Integer, String> mapScopes = new HashMap<>();
	private Map<Integer, String> mapAccess = new HashMap<>();
	private List<String> listTypes = new ArrayList<>();
	private List<String> listImagePosition = new ArrayList<>();
	private Map<String, Object> requestAttributes;
	private Map<String, Object> sessionAttributes;
	private String[] selec1;
	private List<Section> sectionList;
	private String NewsPieceId = "";
	private String NewsCompId = "";

	public NewsPresentationAction() {
		super();
	}

	@Override
	public String execute() throws Exception {
		// Obtenemos las secciones
		SectionsService sectionsService = new SectionsService();
//		sectionList = sectionsService.getRecords();
		// Obtenemos las noticias de cada sección
		NewsPiecesService newsPiecesService = new NewsPiecesService();
//		List<NewsPiece> newsPieceList=newsPiecesService.getNewsRecords();

		return "SHOW";
	}

	public String NewsComposition_new() throws Exception {

		initDataEdit();
		initMapStatus();
		initMapAccess();
		initMapScopes();
		control.setRecCount(1);
		control.setRunAction("new");
		requestAttributes.put("page", "/WEB-INF/views/newsCompositionEdit.jsp");
		return "NEW";
	}

	public String NewsComposition_edit() throws Exception {
		ActionContext act = ActionContext.getContext();
		if (act.getParameters().get("NewsCompId") != null) {
			String[] parameter = (String[]) act.getParameters().get("NewsCompId");
			this.setNewsCompId((String) parameter[0]);
// SE QUITA PARA QUE COMPILE PERO HAY QUE REPONER ESTA LINEA            control.setId(getNewsCompId());
		}
		dataEdit = dataService.getRecords("SELECT * FROM newscomposition WHERE nco_composition_id = "
				+ control.getId(), "", "").get(0);
		initMapStatus();
		initMapAccess();
		initMapScopes();
		initListHeaderStyle();
		initListImagePosition();
		control.setRunAction("edit");
//        requestáttributes.put("page", "/WEB-INF/views/newsCompEdit.jsp");
		return "EDIT";
	}

	public String NewsComposition_saveNew() throws Exception {
		//    dataEdit.setUser((String) sessionAttributes.get("user"));
		//    dataEdit.setDatetime(new Date());
		//    dataEdit.setStatus(0);

		if (dataService.setNewRecord(dataEdit) == 1) {
			this.addActionMessage(getText("NewsCompositionEditSaveOkMsg"));
			return NewsComposition_list();
		}
		return "EDIT";
	}

	public String NewsComposition_saveEdit() throws Exception {

		if (dataService.keyIdExists(dataEdit.getId())) {
			try {
				dataService.setUpdateRecord(dataEdit);
				this.addActionMessage(getText("NewsCompositionEditSaveOkMsg"));
			} catch (Exception ex) {
				this.addActionError(getText("NewsCompositionEditErrorMsg"));
				return "EDIT";
			}
			return NewsComposition_list();
		}
		return "NEW";
	}

	public String NewsComposition_delete() throws Exception {
		if (selec1 != null) {
			for (int i = 0; i < selec1.length; i++) {
				dataEdit.setId(Integer.parseInt(selec1[i].trim()));
				if (dataService.deleteNews(dataEdit) == 1) {
					addActionMessage(selec1[i] + " " + getText("SuccessDeletedNewsComposition"));
				} else {
					addActionError(selec1[i] + " " + getText("NoneDeletedNewsComposition"));
				}
			}
			return NewsComposition_list();
		}
		addActionError(getText("NoneSelectedNewsComposition"));
		return NewsComposition_list();
	}

	public String NewsComposition_list() throws Exception {
		ActionContext act = ActionContext.getContext();
		if (act.getParameters().get("NewsPieceId") != null) {
			String[] parameter = (String[]) act.getParameters().get("NewsPieceId");
			this.setNewsPieceId((String) parameter[0]);
			dataList = dataService.getRecords("SELECT * FROM newscomposition "
					+ " WHERE nco_npi_id = "
					+ getNewsPieceId()
					+ " ORDER BY nco_order", "", "");
			control.setRecCount(dataList.size());

		}
		control.setRunAction("list");
//        requestáttributes.put("page", "/WEB-INF/views/newsCompositionList.jsp");
		return "LIST";
	}

	public String NewsComposition_Navigation() throws Exception {
		control.doNavigation();
		return NewsComposition_list();
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

	public NewsComposition getDataEdit() {
		return dataEdit;
	}

	public void setDataEdit(NewsComposition dataEdit) {
		this.dataEdit = dataEdit;
	}

	public List<NewsComposition> getDataList() {
		return dataList;
	}

	public void setDataList(List<NewsComposition> dataList) {
		this.dataList = dataList;
	}

	public void setDataService(NewsCompositionsService dataService) {
		this.dataService = dataService;
	}

	public String[] getSelec1() {
		return selec1;
	}

	public void setSelec1(String[] selec1) {
		this.selec1 = selec1;
	}

	public Map<Integer, String> getMapScopes() {
		return mapScopes;
	}

	public void setMapScopes(Map<Integer, String> mapScopes) {
		this.mapScopes = mapScopes;
	}

	public Map<Integer, String> getMapAccess() {
		return mapAccess;
	}

	public void setMapAccess(Map<Integer, String> mapAccess) {
		this.mapAccess = mapAccess;
	}

	public List<Section> getSectionList() {
		return sectionList;
	}

	public void setSectionList(List<Section> sectionList) {
		this.sectionList = sectionList;
	}

	public String getNewsPieceId() {
		return NewsPieceId;
	}

	public void setNewsPieceId(String NewsPieceId) {
		this.NewsPieceId = NewsPieceId;
	}

	public String getNewsCompId() {
		return NewsCompId;
	}

	public void setNewsCompId(String NewsCompId) {
		this.NewsCompId = NewsCompId;
	}

	public List<String> getHeaderStyle() {
		return listTypes;
	}

	public List<String> getListImagePosition() {
		return listImagePosition;
	}

	public void setListImagePosition(List<String> listImagePosition) {
		this.listImagePosition = listImagePosition;
	}

	public List<String> getListHeaderStyle() {
		return listTypes;
	}

	public void setListHeaderStyle(List<String> listTypes) {
		this.listTypes = listTypes;
	}

	public void initListHeaderStyle() {
		//Prepara tipos de status para radio element
		listTypes.add(this.getText("title"));
		listTypes.add(this.getText("subtitle"));
		listTypes.add(this.getText("remarked"));
	}

	public void initListImagePosition() {
		//Prepara tipos de status para radio element
		listImagePosition.add("left");
		listImagePosition.add("center");
		listImagePosition.add("right");
	}

	private void initMapStatus() {
		//Prepara tipos de status para radio element
		mapStatus.put(0, this.getText("received"));
		mapStatus.put(1, this.getText("waiting"));
		mapStatus.put(2, this.getText("authorized"));
	}

	private void initMapScopes() {
		//Prepara tipos de status para radio element
		mapScopes.put(0, this.getText("global"));
		mapScopes.put(1, this.getText("vecinity"));
		mapScopes.put(2, this.getText("restáicted"));
	}

	private void initMapAccess() {
		//Prepara tipos de status para radio element
		mapAccess.put(0, this.getText("Restáicted"));
		mapAccess.put(1, this.getText("ALL"));
	}

	@Override
	public void setRequest(Map map) {
		this.requestAttributes = map;
	}

	@Override
	public void setSession(Map map) {
		this.sessionAttributes = map;
	}

	private void initDataEdit() {
//        dataEdit.setDatetime(new Date());
//        dataEdit.setStatus(0);
//        dataEdit.setScope(1);
//        dataEdit.setAccess(0);
//        dataEdit.setUser("");
//        dataEdit.setSection("");
//        dataEdit.setDescription("");
		//dataEdit.setUser();
	}
}
