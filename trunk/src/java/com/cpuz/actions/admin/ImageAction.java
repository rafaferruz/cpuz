/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.actions.admin;

import com.cpuz.domain.Image;
import com.cpuz.exceptions.ImageException;
import com.cpuz.service.ImagesService;
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
public class ImageAction extends ActionSupport implements ServletRequestAware, RequestAware, SessionAware, ApplicationAware, Serializable {

    private ControlParams control = new ControlParams();
    private List<Image> dataList = new ArrayList<Image>();
    private Image dataEdit = new Image();
    private ImagesService dataService;
    private Map<Integer, String> mapStatus = new HashMap<Integer, String>();
    private List<String> listTypes = new ArrayList<String>();
    private Map<Integer, String> mapScopes = new HashMap<Integer, String>();
    private HttpServletRequest request;
    private Map<String, Object> requestáttributes;
    private Map<String, Object> sessionAttributes;
    private Map<String, Object> applicationAttributes;
    private String selec1;
    private String filenameStore = "";
    private String temporaryFileName = "";

    public ImageAction() {
        super();
    }

    @Override
    public String execute() throws Exception {
        return "error";
    }

    public String Image_new() throws Exception {
        initDataEdit();
        initMapStatus();
        initListTypes();
        initMapScopes();
        control.setRecCount(1);
        control.setRunAction("new");
        filenameStore = "";
        requestáttributes.put("page", "/WEB-INF/views/imageEdit.jsp");
        return "NEW";
    }

    public String Image_edit() throws Exception {
        dataEdit = dataService.getRecords("SELECT * FROM images WHERE img_id = "
                + control.getId(), "", "").get(0);
        initMapStatus();
        initListTypes();
        initMapScopes();
        control.setRunAction("edit");
        requestáttributes.put("page", "/WEB-INF/views/imageEdit.jsp");
        setFilenameStore(dataEdit.getFilename());
        return "EDIT";
    }

    public String Image_saveNew() throws Exception {
        if (dataEdit.getFileFileName() != null && !dataEdit.getFileFileName().equals("")) {
            try {
                // Pasa la imagen en memoria a un fichero temporal
                temporaryFileName = String.format("%1$015d", (new Date()).getTime()) + "_" + dataEdit.getFileFileName();
                doSaveTemporaryImageFile(dataEdit.getFile(), dataEdit.getFileContentType(), dataEdit.getFileFileName());
                // Elimina el fichero que haya en el repositorio si el cliente env�a uno nuevo
                if (dataEdit.getRepositoryReference() != null) {
                    try {
                        deleteFileFtp((String) dataEdit.getRepositoryReference());
                    } catch (ImageException ex) {
                        Logger.getLogger(ImageAction.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(ImageAction.class.getName()).log(Level.SEVERE, null, ex);
                control.setRunAction("new");
                addActionError(getText("ImagesEditErrorUploadFile"));
                return "NEW";
            }
            try {
                putFileFtp(applicationAttributes.get("dirRealApplicationPath") + ("WEB-INF/temp"), temporaryFileName);
                dataEdit.setRepositoryReference(temporaryFileName);
            } catch (ImageException ex) {
                Logger.getLogger(ImageAction.class.getName()).log(Level.SEVERE, null, ex);
                control.setRunAction("edit");
                addActionError(getText("ImagesEditErrorUploadFile"));
                return "EDIT";
            } catch (IOException ex) {
                Logger.getLogger(ImageAction.class.getName()).log(Level.SEVERE, null, ex);
                control.setRunAction("edit");
                addActionError(getText("ImagesEditErrorUploadFile"));
                return "EDIT";
            }
// Elimina el fichero temporal
            if (!temporaryFileName.equals("")) {
                File fileImage = new File(applicationAttributes.get("dirRealApplicationPath") + ("WEB-INF/temp/" + temporaryFileName));
                if (fileImage.exists()) {
                    // fileImage.delete();
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
            if (dataService.setNewRecord(dataEdit) == 1) {
                this.addActionMessage(getText("ImageEditSaveOkMsg"));
            }
        } catch (Exception ex) {
            this.addActionError(getText("ImageEditErrorMsg"));
            return "EDIT";
        }
        filenameStore = dataEdit.getFilename();
        return Image_list();
    }

    public String Image_saveEdit() {
        if (dataEdit.getFileFileName() != null && !dataEdit.getFileFileName().equals("")) {
            try {
                // Pasa la imagen en memoria a un fichero temporal
                temporaryFileName = String.format("%1$015d", (new Date()).getTime()) + "_" + dataEdit.getFileFileName();
                doSaveTemporaryImageFile(
                        dataEdit.getFile(), dataEdit.getFileContentType(), dataEdit.getFileFileName());
                // Elimina el fichero que haya en el repositorio si el cliente env�a uno nuevo
                if (dataEdit.getRepositoryReference() != null) {
                    try {
                        deleteFileFtp((String) dataEdit.getRepositoryReference());
                    } catch (ImageException ex) {
                        Logger.getLogger(ImageAction.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(ImageAction.class.getName()).log(Level.SEVERE, null, ex);
                control.setRunAction(
                        "edit");
                addActionError(getText("ImagesEditErrorUploadFile"));
                return "EDIT";
            }
            try {
                putFileFtp(applicationAttributes.get("dirRealApplicationPath") + ("WEB-INF/temp"), temporaryFileName);
                dataEdit.setRepositoryReference(temporaryFileName);
            } catch (ImageException ex) {
                Logger.getLogger(ImageAction.class.getName()).log(Level.SEVERE, null, ex);
                control.setRunAction(
                        "edit");
                addActionError(getText("ImagesEditErrorUploadFile"));
                return "EDIT";
            } catch (IOException ex) {
                Logger.getLogger(ImageAction.class.getName()).log(Level.SEVERE, null, ex);
                control.setRunAction(
                        "edit");
                addActionError(getText("ImagesEditErrorUploadFile"));
                return "EDIT";
            }
// Elimina el fichero temporal
            if (!temporaryFileName.equals("")) {
                File fileImage = new File(applicationAttributes.get("dirRealApplicationPath") + ("WEB-INF/temp/" + temporaryFileName));
                if (fileImage.exists()) {
                    // fileImage.delete();
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
                dataService.setUpdateRecord(dataEdit);
                this.addActionMessage(getText("ImageEditSaveOkMsg"));
            } catch (Exception ex) {
                this.addActionError(getText("ImageEditErrorMsg"));
                return "EDIT";
            }
            filenameStore = dataEdit.getFilename();
            return Image_list();
        }
        filenameStore = dataEdit.getFilename();
        return "EDIT";
    }

    public String Image_delete() throws Exception {
        if (selec1 != null) {
            String[] deletes = selec1.split(",");
            for (int i = 0; i
                    < deletes.length; i++) {
                dataEdit.setId(Integer.parseInt(deletes[i].trim()));
                if (dataService.deleteNews(dataEdit) == 1) {
                    if (dataEdit.getRepositoryReference() != null) {
                        try {
                            deleteFileFtp((String) dataEdit.getRepositoryReference());
                        } catch (ImageException ex) {
                            Logger.getLogger(ImageAction.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    addActionMessage(deletes[i] + " " + getText("SuccessDeletedImage"));
                } else {
                    addActionError(deletes[i] + " " + getText("NoneDeletedImage"));
                }
            }
            return Image_list();
        }
        addActionError(getText("NoneSelectedImage"));
        return Image_list();
    }

    public String Image_list() {
        if (control.getRecCount() == 0) {
            dataList = dataService.getRecords("SELECT * FROM images "
                    + ((Integer) sessionAttributes.get("userCategory") == 2 ? "" : " WHERE img_user = '" + sessionAttributes.get("user") + "' "), "", "");
            control.setRecCount(dataList.size());
        }
        dataList = dataService.getRecords("SELECT * FROM images "
                + ((Integer) sessionAttributes.get("userCategory") == 2 ? "" : " WHERE img_user = '" + sessionAttributes.get("user") + "' ")
                + " LIMIT " + control.getRecChunk().toString()
                + " OFFSET " + control.getRecStart().toString(), "", "");
        control.setRunAction("list");
        requestáttributes.put("page", "/WEB-INF/views/imageList.jsp");
        return "LIST";
    }

    public String Image_Navigation() throws Exception {
        control.doNavigation();
        return Image_list();
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

    public Image getDataEdit() {
        return dataEdit;
    }

    public void setDataEdit(Image dataEdit) {
        this.dataEdit = dataEdit;
    }

    public List<Image> getDataList() {
        return dataList;
    }

    public void setDataList(List<Image> dataList) {
        this.dataList = dataList;
    }

    public void setDataService(ImagesService dataService) {
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

    public void initMapStatus() {
        //Prepara tipos de status para radio element
        mapStatus.put(0, this.getText("received"));
        mapStatus.put(1, this.getText("waiting"));
        mapStatus.put(2, this.getText("authorized"));
    }

    public void initListTypes() {
        //Prepara tipos de status para radio element
        listTypes.add(this.getText("title"));
        listTypes.add(this.getText("subtitle"));
        listTypes.add(this.getText("remarked"));
    }

    public void initMapScopes() {
        //Prepara tipos de status para radio element
        mapScopes.put(0, this.getText("global"));
        mapScopes.put(1, this.getText("vecinity"));
        mapScopes.put(2, this.getText("restáicted"));
    }

    @Override
    public void setRequest(Map map) {
        this.requestáttributes = map;
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
        this.request= hsr;
    }

    private void initDataEdit() {
        dataEdit.setDatetime(new Date());
        dataEdit.setFilename("");
        dataEdit.setRepositoryReference("");
        dataEdit.setUserReference("");
        dataEdit.setScope(1);
        //dataEdit.setUser();
    }

    protected void doSaveTemporaryImageFile(File file, String fileContentType, String fileFileName) throws IOException {
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

    private void putFileFtp(String localDir, String localFileName) throws ImageException, IOException {
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
            throw new ImageException("Conection FTP Error: " + e);
        } // El m�todo pwd devuelve el directorio actual
        CoFile dir = new FtpFile(ftpObj.pwd(), ftpObj);
        if (ftpObj.cd("CPUZ/images")) {
            CoFile file = new FtpFile(localFileName, ftpObj);
            CoFile localFile = new LocalFile(localDir, localFileName);
            if (file.exists()) {
                if (file.isFile()) {
                    if (!file.delete()) {
                        throw new ImageException("El fichero ya existe y no puede ser reemplazado: ");
                    }
                }
            }
            if (!CoLoad.copy(file, localFile)) {
                throw new ImageException("No ha sido posible copiar el fichero en el servidor: ");
            }
        } else {
            throw new ImageException("No se encuentra el repositorio de im�genes");
        } /* this must be always run */
        ftpObj.disconnect();
    }

    private void deleteFileFtp(String fileToDelete) throws ImageException {
        String[] argsFtp = {"ftp://ftp.laboraldetarragona.com", "-user", "ecosysw@laboraldetarragona.com"};
        FtpConnect ftpConx = FtpConnect.newConnect(argsFtp);
        ftpConx.setPassWord("cragogru");
        Ftp ftpObj = new Ftp();
        try {  /* connect & login to host */
            ftpObj.connect(ftpConx);
        } catch (IOException e) {
            System.out.println(e);
            throw new ImageException("Conection FTP Error: " + e);
        }
        if (ftpObj.cd("CPUZ/images")) {
            CoFile file = new FtpFile(fileToDelete, ftpObj);
            if (file.exists()) {
                if (file.isFile()) {
                    if (!file.delete()) {
                        throw new ImageException("El fichero ya existe y no puede ser reemplazado: ");
                    }
                }
            }
        } else {
            throw new ImageException("No se encuentra el repositorio de im�genes");
        } /* this must be always run */
        ftpObj.disconnect();
    }

}
