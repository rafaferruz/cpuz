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

import com.cpuz.domain.Document;
import com.cpuz.domain.UserType;
import com.cpuz.exceptions.DocumentException;
import com.cpuz.service.DocumentsService;
import com.cpuz.st2.beans.ControlParams;
import com.opensymphony.xwork2.ActionSupport;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import cz.dhl.ftp.*;
import cz.dhl.io.CoFile;
import cz.dhl.io.CoLoad;
import cz.dhl.io.LocalFile;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class DocumentAction extends ActionSupport implements ServletRequestAware, RequestAware, SessionAware, ApplicationAware, Serializable {

	private ControlParams control = new ControlParams();
	private List<Document> dataList = new ArrayList<>();
	private Document dataEdit = new Document();
	private DocumentsService dataService;
	private Map<Integer, String> mapStatus = new HashMap<>();
	private List<String> listTypes = new ArrayList<>();
	private Map<Integer, String> mapScopes = new HashMap<>();
	private HttpServletRequest request;
	private Map<String, Object> requestAttributes;
	private Map<String, Object> sessionAttributes;
	private Map<String, Object> applicationAttributes;
	private String selec1;
	private String filenameStore = "";
	private String temporaryFileName = "";

	public DocumentAction() {
		super();
	}

	@Override
	public String execute() throws Exception {
		return "error";
	}

	public String documentNew() throws Exception {
		control.setRecCount(1);
		control.setRunAction("New");
		filenameStore = "";
		requestAttributes.put("page", "/WEB-INF/views/documentEdit.jsp");
		return "new";
	}

	public String documentEdit() throws Exception {
		dataEdit = dataService.getById(control.getId());
		control.setRunAction("Edit");
		requestAttributes.put("page", "/WEB-INF/views/documentEdit.jsp");
		setFilenameStore(dataEdit.getFilename());
		return "edit";
	}

	public String documentSaveNew() throws Exception {
		if (dataEdit.getFileFileName() != null && !dataEdit.getFileFileName().equals("")) {
			try {
				// Pasa la documentn en memoria a un fichero temporal
				temporaryFileName = String.format("%1$015d", (new Date()).getTime()) + "_" + dataEdit.getFileFileName();
				doSaveTemporaryDocumentFile(dataEdit.getFile(), dataEdit.getFileContentType(), dataEdit.getFileFileName());
				// Elimina el fichero que haya en el repositorio si el cliente envía uno nuevo
				if (dataEdit.getRepositoryReference() != null) {
					try {
						deleteFileFtp((String) dataEdit.getRepositoryReference());
					} catch (DocumentException ex) {
						Logger.getLogger(DocumentAction.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			} catch (Exception ex) {
				Logger.getLogger(DocumentAction.class.getName()).log(Level.SEVERE, null, ex);
				control.setRunAction("New");
				addActionError(getText("DocumentsEditErrorUploadFile"));
				return "new";
			}
			try {
				putFileFtp(applicationAttributes.get("dirRealApplicationPath") + ("WEB-INF/temp"), temporaryFileName);
				dataEdit.setRepositoryReference(temporaryFileName);
			} catch (DocumentException ex) {
				Logger.getLogger(DocumentAction.class.getName()).log(Level.SEVERE, null, ex);
				control.setRunAction("Edit");
				addActionError(getText("DocumentsEditErrorUploadFile"));
				return "edit";
			} catch (IOException ex) {
				Logger.getLogger(DocumentAction.class.getName()).log(Level.SEVERE, null, ex);
				control.setRunAction("Edit");
				addActionError(getText("DocumentsEditErrorUploadFile"));
				return "edit";
			}
// Elimina el fichero temporal
			if (!temporaryFileName.equals("")) {
				File fileDocument = new File(applicationAttributes.get("dirRealApplicationPath") + ("WEB-INF/temp/" + temporaryFileName));
				if (fileDocument.exists()) {
					// fileDocument.delete();
				}
			}
			dataEdit.setFilename(dataEdit.getFileFileName());
		} else {
			if (filenameStore.length() > 0) {
				dataEdit.setFilename(filenameStore);
			}
		}
		dataEdit.setUser((String) sessionAttributes.get("user"));
		try {
			if (dataService.insertDocument(dataEdit) == 1) {
				this.addActionMessage(getText("DocumentEditSaveOkMsg"));
			}
		} catch (Exception ex) {
			this.addActionError(getText("DocumentEditErrorMsg"));
			return "edit";
		}
		filenameStore = dataEdit.getFilename();
		return documentList();
	}

	public String documentSaveEdit() throws SQLException {
		if (dataEdit.getFileFileName() != null && !dataEdit.getFileFileName().equals("")) {
			try {
				// Pasa la documentn en memoria a un fichero temporal
				temporaryFileName = String.format("%1$015d", (new Date()).getTime()) + "_" + dataEdit.getFileFileName();
				doSaveTemporaryDocumentFile(
						dataEdit.getFile(), dataEdit.getFileContentType(), dataEdit.getFileFileName());
				// Elimina el fichero que haya en el repositorio si el cliente env�a uno nuevo
				if (dataEdit.getRepositoryReference() != null) {
					try {
						deleteFileFtp((String) dataEdit.getRepositoryReference());
					} catch (DocumentException ex) {
						Logger.getLogger(DocumentAction.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			} catch (Exception ex) {
				Logger.getLogger(DocumentAction.class.getName()).log(Level.SEVERE, null, ex);
				control.setRunAction(
						"Edit");
				addActionError(getText("DocumentsEditErrorUploadFile"));
				return "edit";
			}
			try {
				putFileFtp(applicationAttributes.get("dirRealApplicationPath") + ("WEB-INF/temp"), temporaryFileName);
				dataEdit.setRepositoryReference(temporaryFileName);
			} catch (DocumentException ex) {
				Logger.getLogger(DocumentAction.class.getName()).log(Level.SEVERE, null, ex);
				control.setRunAction("Edit");
				addActionError(getText("DocumentsEditErrorUploadFile"));
				return "edit";
			} catch (IOException ex) {
				Logger.getLogger(DocumentAction.class.getName()).log(Level.SEVERE, null, ex);
				control.setRunAction("Edit");
				addActionError(getText("DocumentsEditErrorUploadFile"));
				return "edit";
			}
// Elimina el fichero temporal
			if (!temporaryFileName.equals("")) {
				File fileDocument = new File(applicationAttributes.get("dirRealApplicationPath") + ("WEB-INF/temp/" + temporaryFileName));
				if (fileDocument.exists()) {
					// fileDocument.delete();
				}
			}
			dataEdit.setFilename(dataEdit.getFileFileName());
		} else {
			if (filenameStore.length() > 0) {
				dataEdit.setFilename(filenameStore);
			}
		}
		if (dataService.keyIdExists(dataEdit.getId())) {
			try {
				dataService.updateDocument(dataEdit);
				this.addActionMessage(getText("DocumentEditSaveOkMsg"));
			} catch (Exception ex) {
				this.addActionError(getText("DocumentEditErrorMsg"));
				return "edit";
			}
			filenameStore = dataEdit.getFilename();
			return documentList();
		}
		filenameStore = dataEdit.getFilename();
		return "edit";
	}

	public String documentDelete() throws Exception {
		if (selec1 != null) {
			String[] deletes = selec1.split(",");
			List<Integer> ids = new ArrayList<>();
			for (int i = 0; i < deletes.length; i++) {
				ids.add(Integer.parseInt(deletes[i]));
			}
			if (dataService.deleteDocumentIds(ids) > 0) {
				if (dataEdit.getRepositoryReference() != null) {
					try {
						deleteFileFtp((String) dataEdit.getRepositoryReference());
					} catch (DocumentException ex) {
						Logger.getLogger(DocumentAction.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
				addActionMessage(getText("SuccessDeletedDocuments"));
			} else {
				addActionError(getText("NoneDeletedDocuments"));
			}

			return documentList();
		}
		addActionError(getText("NoneSelectedDocument"));
		return documentList();
	}

	public String documentList() throws SQLException  {
		if (control.getRecCount() == 0) {
			control.setRecCount(dataService.getCountRows());
		}
		control.setUserType(UserType.ADMIN);
		dataList = dataService.getDocumentList(control);
		control.setRunAction("List");
		requestAttributes.put("page", "/WEB-INF/views/documentList.jsp");
		return "list";
	}

	public String documentNavigation() throws Exception {
		control.doNavigation();
		return documentList();
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

	public Document getDataEdit() {
		return dataEdit;
	}

	public void setDataEdit(Document dataEdit) {
		this.dataEdit = dataEdit;
	}

	public List<Document> getDataList() {
		return dataList;
	}

	public void setDataList(List<Document> dataList) {
		this.dataList = dataList;
	}

	public void setDataService(DocumentsService dataService) {
		this.dataService = dataService;
	}

	public String getSelec1() {
		return selec1;
	}

	public void setSelec1(String selec1) {
		this.selec1 = selec1;
	}

	public String getFilenameStore() {
		return filenameStore;
	}

	public void setFilenameStore(String filenameStore) {
		this.filenameStore = filenameStore;
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

	@Override
	public void setRequest(Map map) {
		this.requestAttributes = map;
	}

	@Override
	public void setSession(Map map) {
		this.sessionAttributes = map;
	}

	@Override
	public void setApplication(Map map) {
		this.applicationAttributes = map;
	}

	@Override
	public void setServletRequest(HttpServletRequest hsr) {
		this.request = hsr;
	}

	protected void doSaveTemporaryDocumentFile(File file, String fileContentType, String fileFileName) throws IOException {
		File temporaryFile = new File(applicationAttributes.get("dirRealApplicationPath") + ("WEB-INF/temp/" + temporaryFileName));
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		FileOutputStream fos = new FileOutputStream(temporaryFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		byte[] bf = new byte[255];
		int nchars = 0;
		nchars = fis.read(bf);
		while (nchars != -1) {
			fos.write(bf, 0, nchars);
			nchars = fis.read(bf);
		}
		fos.flush();
		fis.close();
		fos.close();
	}

	private void putFileFtp(String localDir, String localFileName) throws DocumentException, IOException {
		String[] argsFtp = {"ftp://ftp.laboraldetarragona.com", "-user", "ecosysw@laboraldetarragona.com"};
		FtpConnect ftpConx = FtpConnect.newConnect(argsFtp);
		ftpConx.setPassWord("cragogru");
		Ftp ftpObj = new Ftp();        /*       ftpConx.setHostName("ftp.laboraldetarragona.com");
		ftpConx.setUserName("ecosysw@laboraldetarragona.com");
		ftpConx.setPassWord("dr85d4x3");
		 *
		 */
		try {  /* connect & login to host */
			ftpObj.connect(ftpConx);
		} catch (IOException e) {
			System.out.println(e);
			throw new DocumentException("Conection FTP Error: " + e);
		} // El m�todo pwd devuelve el directorio actual
		CoFile dir = new FtpFile(ftpObj.pwd(), ftpObj);
		if (ftpObj.cd("CPUZ/documents")) {
			CoFile file = new FtpFile(localFileName, ftpObj);
			CoFile localFile = new LocalFile(localDir, localFileName);
			if (file.exists()) {
				if (file.isFile()) {
					if (!file.delete()) {
						throw new DocumentException("El fichero ya existe y no puede ser reemplazado: ");
					}
				}
			}
			if (!CoLoad.copy(file, localFile)) {
				throw new DocumentException("No ha sido posible copiar el fichero en el servidor: ");
			}
		} else {
			throw new DocumentException("No se encuentra el repositorio de im�genes");
		} /* this must be always run */
		ftpObj.disconnect();
	}

	private void deleteFileFtp(String fileToDelete) throws DocumentException {
		String[] argsFtp = {"ftp://ftp.laboraldetarragona.com", "-user", "ecosysw@laboraldetarragona.com"};
		FtpConnect ftpConx = FtpConnect.newConnect(argsFtp);
		ftpConx.setPassWord("cragogru");
		Ftp ftpObj = new Ftp();
		try {  /* connect & login to host */
			ftpObj.connect(ftpConx);
		} catch (IOException e) {
			System.out.println(e);
			throw new DocumentException("Conection FTP Error: " + e);
		}
		if (ftpObj.cd("CPUZ/documents")) {
			CoFile file = new FtpFile(fileToDelete, ftpObj);
			if (file.exists()) {
				if (file.isFile()) {
					if (!file.delete()) {
						throw new DocumentException("El fichero ya existe y no puede ser reemplazado: ");
					}
				}
			}
		} else {
			throw new DocumentException("No se encuentra el repositorio de imágenes");
		} /* this must be always run */
		ftpObj.disconnect();
	}
}
