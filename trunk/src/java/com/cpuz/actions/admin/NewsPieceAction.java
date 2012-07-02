/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.actions.admin;

import com.cpuz.domain.ComponentType;
import com.cpuz.domain.Document;
import com.cpuz.domain.Image;
import com.cpuz.domain.InfoBlock;
import com.cpuz.domain.NewsComposition;
import com.cpuz.domain.NewsPiece;
import com.cpuz.domain.Section;
import com.cpuz.domain.UserRole;
import com.cpuz.domain.UserType;
import com.cpuz.exceptions.UserException;
import com.cpuz.service.DocumentsService;
import com.cpuz.service.ImagesService;
import com.cpuz.service.InfoBlocksService;
import com.cpuz.service.NewsCompositionsService;
import com.cpuz.service.NewsPiecesService;
import com.cpuz.service.SectionsService;
import com.cpuz.service.UserRolesService;
import com.cpuz.st2.beans.ControlParams;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class NewsPieceAction extends ActionSupport implements RequestAware, SessionAware, Serializable {

	private ControlParams control = new ControlParams();
	private List<NewsPiece> dataList = new ArrayList<>();
	private NewsPiece dataEdit = new NewsPiece();
	private NewsPiecesService dataService = new NewsPiecesService();
	private Map<Integer, String> mapStatus = new HashMap<>();
	private Map<Integer, String> mapScopes = new HashMap<>();
	private Map<Integer, String> mapAccess = new HashMap<>();
	private List<String> listTypes = new ArrayList<>();
	private List<String> listImagePositions = new ArrayList<>();
	private Map<String, Object> requestAttributes;
	private Map<String, Object> sessionAttributes;
	private ControlParams controlCompList = new ControlParams();
	private List<NewsComposition> dataCompList = new ArrayList<>();
	private NewsComposition dataCompEdit = new NewsComposition();
	private NewsCompositionsService dataCompService = new NewsCompositionsService();
	private String selec1;
	private List<Section> sectionList;
	private SectionsService sectionsService  = new SectionsService();
	private String addComponentType;
	private Map<String, String> mapComponentTypes = new HashMap<>();
	private ControlParams controlComponentType = new ControlParams();
	private List dataObjectsIncludeList = new ArrayList();

	public NewsPieceAction() {
		super();
	}

	@Override
	public String execute() throws Exception {
		return "error";
	}

	public String newsPieceNew() throws Exception {
		initSectionList();
		control.setRecCount(1);
		control.setRunAction("New");
		requestAttributes.put("page", "/WEB-INF/views/newsPieceEdit.jsp");
		return "new";
	}

	public String newsPieceEdit() throws Exception {
		initSectionList();
		if (control.getRunAction().equals("") == false) {
			dataEdit = dataService.getById(control.getId());
			// Prepara lista de Componentes de la Noticia
			dataCompList = dataCompService.getRecords("SELECT * FROM newscomposition "
					+ " WHERE nco_npi_id = "
					+ control.getId()
					+ " ORDER BY nco_order", "", "");
		}
		controlCompList.setRecCount(dataCompList.size());
		sessionAttributes.put("dataCompList", dataCompList);
		control.setRunAction("Edit");
		controlCompList.setRunAction("List");
		requestAttributes.put("page", "/WEB-INF/views/newsPieceEdit.jsp");
		return "edit";
	}

	public String newsPieceEditComposition() throws Exception {
		dataCompList = (List<NewsComposition>) sessionAttributes.get("dataCompList");

		for (int i = 0; i < dataCompList.size(); i++) {
			if (dataCompList.get(i).getId() == controlCompList.getId()) {
				dataCompEdit = dataCompList.get(i);
				i = dataCompList.size();
				break;
			}
		}
		initSectionList();
		initListImagePositions();
		control.setRunAction("Edit");
		controlCompList.setRunAction("Edit");
		requestAttributes.put("page", "/WEB-INF/views/newsPieceEdit.jsp");
		return "edit";
	}

	public String newsPieceSaveNew() throws Exception {
		dataEdit.setUser((String) sessionAttributes.get("user"));
		dataEdit.setDatetime(new Date());
		dataEdit.setStatus(0);

		if (dataService.insertNewsPiece(dataEdit) == 1) {
			this.addActionMessage(getText("NewsPieceEditSaveOkMsg"));
			return newsPieceList();
		}
		return "edit";
	}

	public String NewsPiece_saveEdit() throws Exception {

		if (dataService.keyIdExists(dataEdit.getId())) {
			try {
				// Se ACTUALIZA el registro de NewsPiece que se acaba de editar
// Actualiza el registtro de NewsPiece con el contenido obtenido al leer los parámetros de request
				if (dataService.updateNewsPiece(dataEdit) > 0) {
					dataCompList = (List<NewsComposition>) sessionAttributes.get("dataCompList");

// Regenera borra los Composition de la BD
					dataCompService.deleteNews(" WHERE nco_npi_id = " + dataEdit.getId() + " ");
					int i = 0;
					for (NewsComposition nc : dataCompList) {
// Crea el nuevo valor de Orden de la línea Composition
						nc.setId(0);
						nc.setOrder(++i);
						nc.setIdNpi(dataEdit.getId());
// Graba la nueva Composition en la BD
						dataCompService.setNewRecord(nc);
					}
					this.addActionMessage(getText("NewsPieceEditSaveOkMsg"));
				}
			} catch (Exception ex) {
				this.addActionError(getText("NewsPieceEditErrorMsg"));
				return "edit";
			}
			return newsPieceList();
		}
		return "new";


	}

	public String newsPieceSaveEditComposition() throws Exception {
		dataCompList = (List<NewsComposition>) sessionAttributes.get("dataCompList");

		for (int i = 0; i < dataCompList.size(); i++) {
			if (String.valueOf(dataCompList.get(i).getId()).equals(String.valueOf(dataCompEdit.getId()))) {
				dataCompList.set(i, dataCompEdit);
				i = dataCompList.size();
				break;
			}
		}
		control.setRunAction("");

		return newsPieceEdit();
	}

	public String newsPieceDelete() throws Exception {
		if (selec1 != null) {
			String[] deletes = selec1.split(",");
			List<Integer> ids = new ArrayList<>();
			for (int i = 0; i < deletes.length; i++) {
				ids.add(Integer.parseInt(deletes[i]));
			}
			if (dataService.deleteNewsPieceIds(ids) > 0) {
				addActionMessage(getText("SuccessDeletedNewsPiece"));
			} else {
				addActionError(getText("NoneDeletedNewsPiece"));
			}
			return newsPieceList();
		}

		addActionError(getText("NoneSelectedNewsPiece"));
		return newsPieceList();
	}

	public String newsPieceList() throws Exception {
		if (control.getRecCount() == 0) {
			control.setRecCount(dataService.getCountRows());
		}
				control.setUserType(UserType.ADMIN);
		dataList = dataService.getNewsPieceList(control);
		control.setRunAction("List");
		requestAttributes.put("page", "/WEB-INF/views/newsPieceList.jsp");
		return "list";
	}
	public String newsPieceAddComponent() throws Exception {
		initSectionList();
		if (addComponentType.equals(ComponentType.INFOBLOCK.getKey())) {
			InfoBlocksService componentTypeService = new InfoBlocksService();
			dataObjectsIncludeList = componentTypeService.getInfoBlockList(control);
		}
		if (addComponentType.equals(ComponentType.IMAGE.getKey())) {
			ImagesService componentTypeService = new ImagesService();
			dataObjectsIncludeList = componentTypeService.getImageList(control);
		}
		if (addComponentType.equals(ComponentType.DOCUMENT.getKey())) {
			DocumentsService componentTypeService = new DocumentsService();
			dataObjectsIncludeList = componentTypeService.getDocumentList(control);
		}
		control.setRunAction("Edit");

		dataCompList = (List<NewsComposition>) sessionAttributes.get("dataCompList");
		controlCompList.setRecCount(dataCompList.size());
		controlCompList.setRunAction("List");

		sessionAttributes.put("dataObjectsIncludeList", dataObjectsIncludeList);
		controlComponentType.setRecCount(dataObjectsIncludeList.size());
		controlComponentType.setRunAction("List");
		requestAttributes.put("page", "/WEB-INF/views/newsPieceEdit.jsp");
		return "edit";
	}

	public String newsPieceAssignComponents() throws Exception {
		ActionContext act = ActionContext.getContext();
		if (act.getParameters().get("selec1_INB") != null) {
			String[] parameter = (String[]) act.getParameters().get("selec1_INB");

			setDataCompList((List<NewsComposition>) sessionAttributes.get("dataCompList"));
			setDataObjectsIncludeList((List) sessionAttributes.get("dataObjectsIncludeList"));
			for (int j = 0; j < parameter.length; j++) {
				for (int i = 0; i < dataObjectsIncludeList.size(); i++) {
					if (((InfoBlock) dataObjectsIncludeList.get(i)).getId() == Integer.parseInt(parameter[j])) {
						dataCompEdit = new NewsComposition();
						dataCompEdit.setComponentType("InfoBlock");
						dataCompEdit.setIdNpi(dataEdit.getId());
						dataCompEdit.setIdComponent(((InfoBlock) dataObjectsIncludeList.get(i)).getId());
						dataCompEdit.setHeaderAlt(((InfoBlock) dataObjectsIncludeList.get(i)).getHeader());
						dataCompEdit.setHeaderStyle(((InfoBlock) dataObjectsIncludeList.get(i)).getType());
						dataCompEdit.setBodyAbstract(((InfoBlock) dataObjectsIncludeList.get(i)).getBody().substring(0, Math.min(((InfoBlock) dataObjectsIncludeList.get(i)).getBody().length() - 1, 255)));
						dataCompEdit.setImageHigh(0);
						dataCompEdit.setImageWidth(0);
						dataCompEdit.setLinkedElement("");
						dataCompEdit.setOrder(99999);
						dataCompList.add(dataCompEdit);
						i = 999999;
					}
				}
			}
		}
		if (act.getParameters().get("selec1_IMG") != null) {
			String[] parameter = (String[]) act.getParameters().get("selec1_IMG");

			setDataCompList((List<NewsComposition>) sessionAttributes.get("dataCompList"));
			setDataObjectsIncludeList((List) sessionAttributes.get("dataObjectsIncludeList"));
			for (int j = 0; j < parameter.length; j++) {
				for (int i = 0; i < dataObjectsIncludeList.size(); i++) {
					if (((Image) dataObjectsIncludeList.get(i)).getId() == Integer.parseInt(parameter[j])) {
						dataCompEdit = new NewsComposition();
						dataCompEdit.setComponentType("Image");
						dataCompEdit.setIdNpi(dataEdit.getId());
						dataCompEdit.setIdComponent(((Image) dataObjectsIncludeList.get(i)).getId());
						dataCompEdit.setHeaderAlt(((Image) dataObjectsIncludeList.get(i)).getUserReference());
						dataCompEdit.setBodyAbstract(((Image) dataObjectsIncludeList.get(i)).getRepositoryReference());
						dataCompEdit.setImageHigh(0);
						dataCompEdit.setImageWidth(0);
						dataCompEdit.setLinkedElement("");
						dataCompEdit.setOrder(99999);
						dataCompList.add(dataCompEdit);
						i = 999999;
					}
				}
			}
		}
		if (act.getParameters().get("selec1_DOC") != null) {
			String[] parameter = (String[]) act.getParameters().get("selec1_DOC");

			setDataCompList((List<NewsComposition>) sessionAttributes.get("dataCompList"));
			setDataObjectsIncludeList((List) sessionAttributes.get("dataObjectsIncludeList"));
			for (int j = 0; j < parameter.length; j++) {
				for (int i = 0; i < dataObjectsIncludeList.size(); i++) {
					if (((Document) dataObjectsIncludeList.get(i)).getId() == Integer.parseInt(parameter[j])) {
						dataCompEdit = new NewsComposition();
						dataCompEdit.setComponentType("Document");
						dataCompEdit.setIdNpi(dataEdit.getId());
						dataCompEdit.setIdComponent(((Document) dataObjectsIncludeList.get(i)).getId());
						dataCompEdit.setHeaderAlt(((Document) dataObjectsIncludeList.get(i)).getUserReference());
						dataCompEdit.setHeaderStyle("remarked");
						dataCompEdit.setBodyAbstract(((Document) dataObjectsIncludeList.get(i)).getRepositoryReference());
						dataCompEdit.setImageHigh(0);
						dataCompEdit.setImageWidth(0);
						dataCompEdit.setLinkedElement("");
						dataCompEdit.setOrder(99999);
						dataCompList.add(dataCompEdit);
						i = 999999;
					}
				}
			}
		}
		control.setRunAction("");
		return newsPieceEdit();
	}

	public String newsPieceNavigation() throws Exception {
		control.doNavigation();
		return newsPieceList();
	}

	public String newsPieceCompNavTop() throws Exception {
		runactionNavTopComposition();
		control.setRunAction("");
		return newsPieceEdit();
	}

	public String newsPieceCompNavUp() throws Exception {
		runactionNavUpComposition();
		control.setRunAction("");
		return newsPieceEdit();
	}

	public String newsPieceCompNavDown() throws Exception {
		runactionNavDownComposition();
		control.setRunAction("");
		return newsPieceEdit();
	}

	public String newsPieceCompNavBottom() throws Exception {
		runactionNavBottomComposition();
		control.setRunAction("");
		return newsPieceEdit();
	}

	public String newsPieceCompDelete() throws Exception {
		runactionDeleteComposition();
		control.setRunAction("");
		return newsPieceEdit();
	}

	public String newsPieceNavigationComponentType() throws Exception {
		controlComponentType.doNavigation();
		return newsPieceAddComponent();
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

	public NewsPiece getDataEdit() {
		return dataEdit;
	}

	public void setDataEdit(NewsPiece dataEdit) {
		this.dataEdit = dataEdit;
	}

	public List<NewsPiece> getDataList() {
		return dataList;
	}

	public void setDataList(List<NewsPiece> dataList) {
		this.dataList = dataList;
	}

	public void setDataService(NewsPiecesService dataService) {
		this.dataService = dataService;
	}

	public String getSelec1() {
		return selec1;
	}

	public void setSelec1(String selec1) {
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

	public String getAddComponentType() {
		return addComponentType;
	}

	public void setAddComponentType(String addComponentType) {
		this.addComponentType = addComponentType;
	}

	public Map<String, String> getMapComponentTypes() {
		return mapComponentTypes;
	}

	public void setMapComponentTypes(Map<String, String> mapComponentTypes) {
		this.mapComponentTypes = mapComponentTypes;
	}

	public ControlParams getControlCompList() {
		return controlCompList;
	}

	public void setControlCompList(ControlParams controlCompList) {
		this.controlCompList = controlCompList;
	}

	public List<NewsComposition> getDataCompList() {
		return dataCompList;
	}

	public void setDataCompList(List<NewsComposition> dataCompList) {
		this.dataCompList = dataCompList;
	}

	public NewsComposition getDataCompEdit() {
		return dataCompEdit;
	}

	public void setDataCompEdit(NewsComposition dataCompEdit) {
		this.dataCompEdit = dataCompEdit;
	}

	public List<String> getListTypes() {
		return listTypes;
	}

	public void setListTypes(List<String> listTypes) {
		this.listTypes = listTypes;
	}

	public List<String> getListImagePositions() {
		return listImagePositions;
	}

	public void setListImagePositions(List<String> listImagePositions) {
		this.listImagePositions = listImagePositions;
	}

	public ControlParams getControlComponentType() {
		return controlComponentType;
	}

	public void setControlComponentType(ControlParams controlComponentType) {
		this.controlComponentType = controlComponentType;
	}

	public List getDataObjectsIncludeList() {
		return dataObjectsIncludeList;
	}

	public void setDataObjectsIncludeList(List dataObjectsIncludeList) {
		this.dataObjectsIncludeList = dataObjectsIncludeList;
	}

	private void initMapAccess() {
		//Prepara tipos de status para radio element
		mapAccess.put(0, this.getText("Restricted"));
		mapAccess.put(1, this.getText("ALL"));
	}

	public void initListImagePositions() {
		//Prepara tipos de status para radio element
		listImagePositions.add(this.getText("left"));
		listImagePositions.add(this.getText("center"));
		listImagePositions.add(this.getText("right"));
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
		dataEdit.setDatetime(new Date());
		dataEdit.setStatus(0);
		dataEdit.setScope(1);
		dataEdit.setAccess(0);
		dataEdit.setUser("");
		dataEdit.setSectionId(0);
		dataEdit.setDescription("");
		//dataEdit.setUser();
	}

	private void initSectionList() throws SQLException, UserException {
		String stringRoles = "";
		String user = (String) sessionAttributes.get("user");
		UserRolesService userRolesService = new UserRolesService();
		List<UserRole> userRolesList = userRolesService.getUserRoleList(user);
		for (UserRole userRole : userRolesList) {
			stringRoles = stringRoles.concat(userRole.getRole() + ",");
		}
		sectionList = sectionsService.getSectionList(control);
		if (!stringRoles.contains("all") && !stringRoles.contains("adminRole")) {
			List<Section> unusedSections = new ArrayList<>();
			for (Section section : sectionList) {
				/*
				if (!stringRoles.contains(section.getId())) {
					unusedSections.add(section);
				}*/
			}
			sectionList.removeAll(unusedSections);
		}

	}

	private void runactionNavUpComposition() {
		ActionContext act = ActionContext.getContext();
		if (act.getParameters().get("selec1Comp") != null) {
			String[] parameter = (String[]) act.getParameters().get("selec1Comp");

			NewsComposition ncTemp = new NewsComposition();
			setDataCompList((List<NewsComposition>) sessionAttributes.get("dataCompList"));
			for (int j = 0; j < parameter.length; j++) {
				int selec = Integer.parseInt(parameter[j]);
				for (int i = 0; i < dataCompList.size(); i++) {
					if (selec == ((NewsComposition) dataCompList.get(i)).getId()) {
						if (i > 0) {
							ncTemp = (NewsComposition) dataCompList.get(i);
							dataCompList.set(i, (NewsComposition) dataCompList.get(i - 1));
							dataCompList.set(i - 1, ncTemp);
						}
						break;
					}
				}
			}
		}
	}

	private void runactionNavDownComposition() {
		ActionContext act = ActionContext.getContext();
		if (act.getParameters().get("selec1Comp") != null) {
			String[] parameter = (String[]) act.getParameters().get("selec1Comp");

			NewsComposition ncTemp = new NewsComposition();
			setDataCompList((List<NewsComposition>) sessionAttributes.get("dataCompList"));
			for (int j = parameter.length - 1; j >= 0; j--) {
				int selec = Integer.parseInt(parameter[j]);
				for (int i = dataCompList.size() - 1; i >= 0; i--) {
					if (selec == ((NewsComposition) dataCompList.get(i)).getId()) {
						if (i < dataCompList.size() - 1) {
							ncTemp = (NewsComposition) dataCompList.get(i + 1);
							dataCompList.set(i + 1, (NewsComposition) dataCompList.get(i));
							dataCompList.set(i, ncTemp);
						}
						break;
					}
				}
			}
		}
	}

	private void runactionNavTopComposition() {
		ActionContext act = ActionContext.getContext();
		if (act.getParameters().get("selec1Comp") != null) {
			String[] parameter = (String[]) act.getParameters().get("selec1Comp");

			NewsComposition ncTemp = new NewsComposition();
			setDataCompList((List<NewsComposition>) sessionAttributes.get("dataCompList"));
			for (int j = parameter.length - 1; j >= 0; j--) {
				int selec = Integer.parseInt(parameter[j]);
				for (int i = dataCompList.size() - 1; i >= 0; i--) {
					if (selec == ((NewsComposition) dataCompList.get(i)).getId()) {
						ncTemp = (NewsComposition) dataCompList.get(i);
						dataCompList.remove(i);
						dataCompList.add(0, ncTemp);
						break;
					}
				}
			}
		}
	}

	private void runactionNavBottomComposition() {
		ActionContext act = ActionContext.getContext();
		if (act.getParameters().get("selec1Comp") != null) {
			String[] parameter = (String[]) act.getParameters().get("selec1Comp");

			NewsComposition ncTemp = new NewsComposition();
			setDataCompList((List<NewsComposition>) sessionAttributes.get("dataCompList"));
			for (int j = 0; j < parameter.length; j++) {
				int selec = Integer.parseInt(parameter[j]);
				for (int i = 0; i < dataCompList.size() - 1; i++) {
					if (selec == ((NewsComposition) dataCompList.get(i)).getId()) {
						ncTemp = (NewsComposition) dataCompList.get(i);
						dataCompList.remove(i);
						dataCompList.add(ncTemp);
						break;
					}
				}
			}
		}
	}

	private void runactionDeleteComposition() {
		ActionContext act = ActionContext.getContext();
		if (act.getParameters().get("selec1Comp") != null) {
			String[] parameter = (String[]) act.getParameters().get("selec1Comp");

			setDataCompList((List<NewsComposition>) sessionAttributes.get("dataCompList"));
			for (int i = 0; i < parameter.length; i++) {
				Iterator it = dataCompList.iterator();
				while (it.hasNext()) {
					NewsComposition ib = (NewsComposition) it.next();
					if (ib.getId() == Integer.parseInt(parameter[i])) {
						dataCompList.remove(ib);
//                            ncvm.deleteNews(ib);
						break;
					}
				}
			}
		}
	}
}
