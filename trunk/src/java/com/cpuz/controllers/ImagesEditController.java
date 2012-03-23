/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.controllers;

import com.cpuz.domain.Image;
import com.cpuz.exceptions.ImageException;
import com.cpuz.model.ImagesModel;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import cz.dhl.ftp.*;
import cz.dhl.io.CoFile;
import cz.dhl.io.CoLoad;
import cz.dhl.io.LocalFile;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class ImagesEditController extends GenericEditController {

    private static final long serialVersionUID = 301L;
    protected ImagesModel nvm = new ImagesModel();

    public ImagesEditController() {
        super.jspFileList = "views/imagesList.jsp";
        super.jspFileEdit = "views/imagesEdit.jsp";
        super.jspFileSelec = "views/imagesSelec.jsp";
        super.attrNameListRecs = "imagesListNews";
    }

    /** Procesa las solicitudes procedentes de la vista images.jsp
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected synchronized void getParametersForm(HttpServletRequest request, HttpServletResponse response) {

        Enumeration parameters = request.getParameterNames();
        while (parameters.hasMoreElements()) {
            String parameterName = (String) parameters.nextElement();
            if (request.getParameterValues(parameterName).length == 1) {
                request.setAttribute(parameterName, request.getParameter(parameterName));
            } else {
                request.setAttribute(parameterName, request.getParameterValues(parameterName));
            }
        }
        // SE GRABA EL REGISTRO EN LA BASE DE DATOS

        ImagesModel nvm = new ImagesModel();

        try {
            //Obtenemos la lista de filas listadas para a�adir las nuevas o modificar las editadas
            List recs = (List) request.getSession().getAttribute(attrNameListRecs);

            if (request.getAttribute("runaction") != null
                    && request.getAttribute("runaction").equals("save_new")) {
// Se crea un NUEVO registro de Image
// Prepara el bean de Image previo a la grabación del registro
                Image not = new Image();
//                request.setAttribute("identificador", "");

                setPropertiesRecord(request, not);
                if (nvm.setNewRecord(not) < 0) {
                    request.setAttribute("ImagesEditErrorMsg", "ImagesEditErrorMsg");
                } else {
                    recs.add(0, not);
                    request.getSession().setAttribute(attrNameListRecs, recs);
                    request.setAttribute("recList", recs);

                    request.setAttribute("ImagesEditSaveOkMsg", "ImagesEditSaveOkMsg");
                }
                request.setAttribute("runaction", "new");
            } else if (request.getAttribute("runaction") != null
                    && request.getAttribute("runaction").equals("save_edit")) {
// Se ACTUALIZA el registro de Image que se acaba de editar
// Prepara el bean de Image previo a la grabación del registro
                Image not = new Image();

                setPropertiesRecord(request, not);
                not.setId(request.getAttribute("id_disabled") == null
                        || ((String) request.getAttribute("id_disabled")).equals("")
                        ? 0 : Integer.parseInt((String) request.getAttribute("id_disabled")));
                if (nvm.setUpdateRecord(not) < 0) {
                    request.setAttribute("runaction", "edit");
                    request.setAttribute("ImagesEditErrorMsg", "ImagesEditErrorMsg");
                } else {
                    for (Object sec : recs) {
                        if (((Image) sec).getId().equals(request.getAttribute("id_disabled"))) {
                            recs.set(recs.indexOf(sec), not);
                        }
                    }
                    request.getSession().setAttribute(attrNameListRecs, recs);
                    request.setAttribute("recList", recs);

                    request.setAttribute("runaction", "list");
                    request.setAttribute("ImagesEditSaveOkMsg", "ImagesEditSaveOkMsg");
                }
            }

            // imagesEdit.jsp necesita saber si la vista a mostrar es para crear
            // una noticia nueva (new) o para editar una noticia ya existente (edit)
        } catch (ParseException ex) {
            request.setAttribute("runaction", "edit");
            request.setAttribute("ImagesEditErrorMsg", "ImagesEditErrorParseException");
        }
    }

    protected void setPropertiesRecord(HttpServletRequest request, Object rec) throws ParseException {

        String pattern = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat();
        // apply the pattern to the SimpleDateFormat class
        sdf.applyPattern(pattern);

        Image not = (Image) rec;

        not.setId(request.getAttribute("identificador") == null || ((String) request.getAttribute("identificador")).equals("") ? 0 : Integer.parseInt((String) request.getAttribute("identificador")));
        if (request.getAttribute("runaction").equals("save_new")) {
            not.setDatetime(new Date());
            not.setUser((String) request.getSession().getAttribute("user"));
        }
        //sdf.parse((String) request.getAttribute("date"))
        not.setUserReference((String) request.getAttribute("userReference"));
        not.setFilename((String) request.getAttribute("filename"));
        not.setRepositoryReference((String) request.getAttribute("repositoryReference"));
        not.setScope(request.getAttribute("scope") == null || ((String) request.getAttribute("scope")).equals("") ? 0 : Integer.parseInt((String) request.getAttribute("scope")));

    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param requestáservlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param requestáservlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    protected void doSaveMultipart(HttpServletRequest request, HttpServletResponse response) {
        String temporaryFileName;

        try {
            temporaryFileName = "";

            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload sfu = new ServletFileUpload(factory);
            List items = sfu.parseRequest(request); /* FileItem */
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
// Pone como atributo de requestácualquier dato recibido para que están disponibles
// para rellenar los campos de datos de posteriores vistas
                    request.setAttribute(item.getFieldName(), (item.getString() == null ? "" : item.getString()));
                } else {
                    String fieldName = item.getFieldName();
                    String fileName = item.getName();

                    if (fileName.length() > 0) {
                        fileName = fileName.lastIndexOf("/") >= 0 ? fileName.substring(fileName.lastIndexOf("/") + 1) : fileName;
                        fileName = fileName.lastIndexOf("\\") >= 0 ? fileName.substring(fileName.lastIndexOf("\\") + 1) : fileName;

                        temporaryFileName = String.format("%1$015d", (new Date()).getTime()) + "_" + fileName;

                        String contentType = item.getContentType();
                        boolean isInMemory = item.isInMemory();
                        long sizeInBytes = item.getSize();
                        // CODIGO PROCESAMIENTO FICHERO IMAGEN
                        // Process a file upload
                        File uploadedFile = new File(getServletContext().getRealPath("/WEB-INF/temp/" + temporaryFileName));
                        try {
                            item.write(uploadedFile);
// Elimina el fichero que haya en el repositorio si el cliente env�a uno nuevo
                            if (request.getAttribute("repositoryReference") != null) {
                                try {
                                    deleteFileFtp((String) request.getAttribute("repositoryReference"));
                                } catch (ImageException ex) {
                                    Logger.getLogger(ImagesEditController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        } catch (Exception ex) {
                            request.setAttribute("runaction", "new");
                            request.setAttribute("ImagesEditErrorMsg", "ImagesEditErrorUploadFile");
                        }
                        request.setAttribute("repositoryReference", temporaryFileName);
                        request.setAttribute("filename", fileName);
                    }
                }
            }
            // SE GRABA EL REGISTRO EN LA BASE DE DATOS

            nvm = new ImagesModel();

            if (request.getAttribute("runaction") != null
                    && request.getAttribute("runaction").equals("save_new")) {
// Se crea un NUEVO registro de Image
// Prepara el bean de Image previo a la grabación del registro
                Image not = new Image();
                request.setAttribute("identificador", "0");
                SimpleDateFormat sdf = new SimpleDateFormat();
                // apply the pattern to the SimpleDateFormat class
                String pattern = "dd/MM/yyyy HH:mm:ss";
                sdf.applyPattern(pattern);
                // Format and print the date in the desired format.
                request.setAttribute("date", sdf.format(new Date()));
                request.setAttribute("user", request.getSession(false).getAttribute("user"));

                setPropertiesRecord(request, not);

                try {
                    // Sube el fichero al servidor de recursos estáticos
                    putFileFtp(getServletContext().getRealPath("/WEB-INF/temp"), temporaryFileName);

// Graba el registro en la BD
                    if (nvm.setNewRecord(not) < 0) {
                        request.setAttribute("ImagesEditErrorMsg", "ImagesEditErrorMsg");
                    } else {
                        request.setAttribute("ImagesEditSaveOkMsg", "ImagesEditSaveOkMsg");
                    }
                    request.setAttribute("runaction", "new");
                } catch (ImageException ex) {
                    request.setAttribute("runaction", "edit");
                    request.setAttribute("ImagesEditErrorMsg", "ImagesEditErrorMsg");
                } catch (IOException ex) {
                    request.setAttribute("runaction", "edit");
                    request.setAttribute("ImagesEditErrorMsg", "ImagesEditErrorMsg");
                } finally {
// Elimina el fichero temporal
                    if ((!temporaryFileName.equals("")) && (not.getFilename() != null ? not.getFilename() : "").equals(temporaryFileName)) {
                        File fileImage = new File(getServletContext().getRealPath("/WEB-INF/temp/" + temporaryFileName));
                        if (fileImage.exists()) {
                            fileImage.delete();
                        }
                    }
                }

            } else if (request.getAttribute("runaction") != null
                    && request.getAttribute("runaction").equals("save_edit")) {

                // Prepara el bean de Image previo a la grabación del registro
                Image not = new Image();

                setPropertiesRecord(request, not);

                not.setId(request.getAttribute("id_disabled") == null
                        || ((String) request.getAttribute("id_disabled")).equals("")
                        ? 0 : Integer.parseInt((String) request.getAttribute("id_disabled")));

                try {
                    // Sube el fichero al servidor de recursos estáticos
                    putFileFtp(getServletContext().getRealPath("/WEB-INF/temp"), temporaryFileName);

// Se ACTUALIZA el registro de Image que se acaba de editar


                    if (nvm.setUpdateRecord(not) < 0) {
                        request.setAttribute("runaction", "edit");
                        request.setAttribute("ImagesEditErrorMsg", "ImagesEditErrorMsg");
                    } else {
                        request.setAttribute("runaction", "list");
                        request.setAttribute("ImagesEditSaveOkMsg", "ImagesEditSaveOkMsg");
                    }
                } catch (ImageException ex) {
                    request.setAttribute("runaction", "edit");
                    request.setAttribute("ImagesEditErrorMsg", "ImagesEditErrorMsg");
                } catch (IOException ex) {
                    request.setAttribute("runaction", "edit");
                    request.setAttribute("ImagesEditErrorMsg", "ImagesEditErrorMsg");
                } finally {
// Elimina el fichero temporal
                    if ((!temporaryFileName.equals("")) && (not.getFilename() != null ? not.getFilename() : "").equals(temporaryFileName)) {
                        File fileImage = new File(getServletContext().getRealPath("/WEB-INF/temp/" + temporaryFileName));
                        if (fileImage.exists()) {
                            fileImage.delete();
                        }
                    }
                }
            }

            // imagesEdit.jsp necesita saber si la vista a mostrar es para crear
            // una noticia nueva (new) o para editar una noticia ya existente (edit)
        } catch (FileUploadException ex) {
            request.setAttribute("runaction", "edit");
            request.setAttribute("ImagesEditErrorMsg", "ImagesEditErrorUploadFile");
        } catch (ParseException ex) {
            request.setAttribute("runaction", "edit");
            request.setAttribute("ImagesEditErrorMsg", "ImagesEditErrorParseException");
        }
    }

    private void putFileFtp(String localDir, String localFileName) throws ImageException, IOException {

        String[] argsFtp = {"ftp://ftp.laboraldetarragona.com", "-user", "ecosysw@laboraldetarragona.com"};

        FtpConnect ftpConx = FtpConnect.newConnect(argsFtp);
        ftpConx.setPassWord("cragogru");

        Ftp ftpObj = new Ftp();

        /*       ftpConx.setHostName("ftp.laboraldetarragona.com");
        ftpConx.setUserName("ecosysw@laboraldetarragona.com");
        ftpConx.setPassWord("dr85d4x3");
         *
         */
        try {  /* connect & login to host */
            ftpObj.connect(ftpConx);
        } catch (IOException e) {
            System.out.println(e);
            throw new ImageException("Conection FTP Error: " + e);
        }
        // El m�todo pwd devuelve el directorio actual
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

        }
        /* this must be always run */
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
        }
        /* this must be always run */
        ftpObj.disconnect();

    }
}
