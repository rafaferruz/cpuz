package com.cpuz.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Vector;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

/**
 * Interfaz un poco más cómodo para las clases FileUploader del paquete org.apache.commons.fileupload.
 * 
 * Permite requestáen codificacion UTF-8
 * 
 * Tambien permite requestánormales
 * 
 * <i>usage</i> <code>
 *     FileUploader fu = new FileUploader( requestá);
 *     File uploadDir = new File( "/tmp/myDirectory" );
 *     fu.writeFile( uploadDir );
 *     String fileNmae = fu.getFileName();
 *  </code>
 * 
 */
public class FileUploader {

	/** Indica si el requestáes de tipo multipart/form-data o no. */
	private boolean uploadRequest;
	private HttpServletRequest theRequest;
	private ServletFileUpload upload;
	private List<FileItem> items;
	private String fileName;
	private File uploadedFile;
	private String prefix;
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());

	/**
	 * Constructor.
	 * 
	 * @param request
	 *            Objeto requestáque contiene un envío de datos de formulario codificado con el tipo mime
	 *            multipart/form-data o multipart/mixed
	 */
	public FileUploader(HttpServletRequest request) throws FileUploadException {
		this.theRequest= request;
		log.debug("ContentType del requestá " + theRequest.getContentType());
		uploadRequest= theRequest.getContentType().indexOf("multipart") >= 0;
		if (uploadRequest) {
			this.upload = new ServletFileUpload(new DiskFileItemFactory());
			this.upload.setHeaderEncoding(request.getCharacterEncoding());
			this.items = new Vector<FileItem>();
			for (Object fi : upload.parseRequest(request)) {
				this.items.add((FileItem) fi);
			}
			log.debug("Codificacion del requestá " + this.upload.getHeaderEncoding());
		}
	}

	/**
	 * Constructor.
	 * 
	 * @param request
	 *            Objeto requestáque contiene un envío de datos de formulario codificado con el tipo mime
	 *            multipart/form-data o multipart/mixed.
	 * @param prefix
	 *            texto del prefijo usado en los nombres de parámetros del formulario.
	 */
	public FileUploader(HttpServletRequest request, String prefix) throws FileUploadException {
		this.theRequest= request;
		this.prefix = prefix;
		log.debug("ContentType del requestá " + theRequest.getContentType());
		uploadRequest= theRequest.getContentType().indexOf("multipart") >= 0;
		if (uploadRequest) {
			this.upload = new ServletFileUpload(new DiskFileItemFactory());
			this.upload.setHeaderEncoding(request.getCharacterEncoding());
			this.items = new Vector<FileItem>();
			for (Object fi : upload.parseRequest(request)) {
				this.items.add((FileItem) fi);
			}
			log.debug("Codificacion del requestá " + this.upload.getHeaderEncoding());
		}
	}

	/**
	 * Devuelve el valor de un parámetro del formulario.
	 * 
	 * @param name
	 *            Nombre del parámetro
	 * @return El valor del parámetro, si existe, o null en caso contrario Devuelve un UNICO valor para cada parámetro
	 */
	public String getParameter(String name) {
		if (uploadRequest) {
			for (int i = 0; i < this.items.size(); i++) {
				FileItem fileItem = this.items.get(i);
				if (fileItem.isFormField()) {
					if ((prefix != null && fileItem.getFieldName().equals(prefix + "_" + name))
							|| fileItem.getFieldName().equals(name)) {
						try {
							return fileItem.getString(this.upload.getHeaderEncoding());
						} catch (UnsupportedEncodingException ex) {
							throw new RuntimeException(ex);
						}
					}
				}
			}
			return null;
		} else {
			return theRequest.getParameter(name);
		}
	}

	/**
	 * Devuelve los valores de un parámetro del formulario.
	 * 
	 * @param name
	 *            Nombre del parámetro
	 */
	public String[] getParameterValues(String name) {
		if (uploadRequest) {
			java.util.Vector<String> vv = new java.util.Vector<String>();
			for (int i = 0; i < this.items.size(); i++) {
				FileItem fileItem = this.items.get(i);
				if (fileItem.isFormField() && fileItem.getFieldName().equals(name)) {
					vv.add(fileItem.getString());
				}
			}
			return (vv.isEmpty()) ? null : (String[]) vv.toArray(new String[0]);
		} else {
			return theRequest.getParameterValues(name);
		}
	}

	/**
	 * Nos dice si en el formulario se ha enviado un archivo o no.
	 */
	public boolean isThereAnyFile() {
		if (uploadRequest) {
			for (int i = 0; i < this.items.size(); i++) {
				FileItem fileItem = this.items.get(i);
				if (!fileItem.isFormField() && (fileItem.getSize() > 0)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Nos dice cuantos archivos se han enviado en el formulario.
	 */
	public int countFiles() {
		int count = 0;
		if (uploadRequest) {
			for (int i = 0; i < this.items.size(); i++) {
				FileItem fileItem = this.items.get(i);
				if (!fileItem.isFormField() && (fileItem.getSize() > 0)) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * Nos dice el tamaño en bytes de los ficheros enviados en el formulario.
	 */
	public long sizeFiles() {
		long size = 0;
		if (uploadRequest) {
			for (int i = 0; i < this.items.size(); i++) {
				FileItem fileItem = this.items.get(i);
				if (!fileItem.isFormField() && (fileItem.getSize() > 0)) {
					size += fileItem.getSize();
				}
			}
		}
		return size;
	}

	/**
	 * Escribe el UNICO archivo subido en el lugar indicado.
	 * 
	 * @param uploadDir
	 *            Descriptor del directorio en el que se escribir el archivo Intenta crear está directorio si no existe
	 * @return el nombre del archivo subido.
	 */
	public String writeFile(File uploadDir) throws FileUploadException {
		return writeFile(uploadDir, null);
	}

	/**
	 * Escribe el UNICO archivo subido en el lugar indicado.
	 * 
	 * @param uploadDir
	 *            Descriptor del directorio en el que se escribir el archivo. Intenta crear está directorio si no
	 *            existe.
	 * @param sFieldName
	 *            El nombre del campo del requestáque contiene el fichero. Si el parametro es nulo, guarda el primer
	 *            fichero que encuentra.
	 * @return el nombre del archivo subido; Nulo si el usuario no ha enviado ning�n fichero en está campo.
	 */
	public String writeFile(File uploadDir, String sFieldName) throws FileUploadException {
		try {
			mkdirIfNotExists(uploadDir);
			FileItem fileItem = this.getFileItem(sFieldName);
			if (fileItem == null) {
				return null;
			}
			String sFN = fileItem.getName();
			if ((sFN == null) || (sFN.length() == 0)) {
				return null;
			}
			File file = getCorrectedFile(sFN, uploadDir);
			fileItem.write(file);
			this.fileName = file.getName();
			this.uploadedFile = file;
			return this.fileName;
		} catch (Exception ex) { // FileItem lanza Exception !!!
			throw new FileUploadException(ex.getMessage());
		}
	}

	/**
	 * Lee el archivo subido.
	 * 
	 * @param sFieldName
	 *            El nombre del campo del requestáque contiene el fichero. Si el parametro es nulo, guarda el primer
	 *            fichero que encuentra.
	 * @return un stream para leer el archivo subido
	 */
	public InputStream readFile(String sFieldName) throws FileUploadException {
		try {
			FileItem fi = this.getFileItem(sFieldName);
			if (fi == null) {
				return null;
			}
			return fi.getInputStream();
		} catch (java.io.IOException ex) { // FileItem lanza Exception !!!
			throw new FileUploadException(ex.getMessage());
		}
	}

	/**
	 * Devuelve el nombre original del fichero subido por el usuario.
	 */
	public String getOriginalFileName(String sFieldName) {
		FileItem fi = getFileItem(sFieldName);
		if (fi == null) {
			return null;
		}
		return fi.getName();
	}

	private FileItem getFileItem(String sFieldName) {
		for (int i = 0; i < this.items.size(); i++) {
			FileItem fileItem = this.items.get(i);
			if (!fileItem.isFormField() && ((sFieldName == null) || sFieldName.equals(fileItem.getFieldName()))) {
				return fileItem;
			}
		}
		return null;
	}

	/**
	 * Devuelve el nombre del archivo subido. No se puede llamar a está método antes de escribir el archivo
	 * 
	 * @throws FileUploadException
	 *             si a�n no se ha escrito el archivo con writeFile()
	 */
	public String getFileName() throws FileUploadException {
		if (this.fileName != null) {
			return this.fileName;
		} else {
			throw new FileUploadException("No se ha almacenado ningún archivo todavía");
		}
	}

	/**
	 * Devuelve el archivo subido. No se puede llamar a está método antes de escribir el archivo
	 * 
	 * @throws FileUploadException
	 *             si aún no se ha escrito el archivo con writeFile()
	 */
	public File getFile() throws FileUploadException {
		if (this.uploadedFile != null) {
			return this.uploadedFile;
		} else {
			throw new FileUploadException("No se ha almacenado ningún archivo todavía");
		}
	}

	/**
	 * Busca un nombre v�lido para un archivo subido. - Le quita los paths de los directorios C:\Mis documentos\hola.txt
	 * ---> hola.txt - Le a�ade un número si ya existe hola.txt ---> hola1.txt
	 * 
	 * @param fileName
	 *            nombre propuestá para el archivo (el que viene con el POST)
	 * @param uploadDir
	 *            directorio donde se almacenar�
	 * 
	 * @return el archivo con el nombre correcto
	 */
	public static File getCorrectedFile(String fileName, File uploadDir) {
		String name = fileName;

		// Le quitamos los directorios, si los hubiera
		// Windows
		if (name.lastIndexOf('\\') > -1) {
			name = name.substring(name.lastIndexOf('\\') + 1);
		}

		// Unix
		if (name.lastIndexOf('/') > -1) {
			name = name.substring(name.lastIndexOf('/') + 1);
		}
		name = StringUtil.getCorrectedFileName(name);
		File file = new File(uploadDir, name);

		// Si el archivo ya existe, le cambiamos el nombre
		if (file.exists()) {
			int nDotPos = name.lastIndexOf('.');
			boolean bExt = ((nDotPos > 0) && (nDotPos < name.length()));
			String onlyName = (bExt) ? name.substring(0, nDotPos) : name;
			String extension = (bExt) ? name.substring(nDotPos) : "";
			int count = 2;
			while (file.exists()) {
				file = new File(uploadDir, onlyName + (count++) + extension);
			}
		}
		return file;
	}

	/**
	 * Crea el directorio que se pasa como parámetro, si no existe.
	 */
	public static File mkdirIfNotExists(String dirName) throws FileUploadException {
		return mkdirIfNotExists(new File(dirName));
	}

	/**
	 * Crea el directorio que se pasa como parámetro, si no existe.
	 */
	public static File mkdirIfNotExists(File dir) throws FileUploadException {
		if ((dir == null) || !dir.isDirectory()) {
			boolean success = dir.mkdirs();
			if (!success) {
				throw new FileUploadException("No se puede escribir en el directorio de upload ("
						+ dir.getAbsolutePath() + ")");
			}
		}
		return dir;
	}

	/**
	 * Devuelve un mapa con las parejas, nombre-parametro, valor-parametro como strings Si hay ficheros el valor del
	 * parametro devuelto es el nombre del fichero sin la ruta.
	 * 
	 * FIXME: Generalizar para cualquier número de archivos subidos.
	 */
	public HashMap<String, String> getParameters() throws FileUploadException {
		HashMap<String, String> hm = new HashMap<String, String>();

		for (int i = 0; i < items.size(); i++) {
			FileItem fileItem = this.items.get(i);
			if (fileItem.isFormField()) {
				hm.put(fileItem.getFieldName(), fileItem.getString());
			} else {
				if (isThereAnyFile()) {
					hm.put(fileItem.getFieldName(), FileUploader.getCorrectedFilename(fileItem.getName()));
				} else {
					hm.put(fileItem.getFieldName(), null);
				}
			}
		}
		return hm;
	}

	/**
	 * Muy similar a getCorrectedFile pero sin renombrar. Por está motivo no necesita el parámetro uploadDir.
	 * 
	 * FIXME: Ver si se puede integrar con la versión con uploadDir
	 */
	public static String getCorrectedFilename(String fileName) {
		String name = fileName;

		// Le quitamos los directorios, si los hubiera
		// Windows
		if (name.lastIndexOf('\\') > -1) {
			name = name.substring(name.lastIndexOf('\\') + 1);
		}

		// Unix
		if (name.lastIndexOf('/') > -1) {
			name = name.substring(name.lastIndexOf('/') + 1);
		}
		return name;
	}
}
