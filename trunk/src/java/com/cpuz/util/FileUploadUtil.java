package com.cpuz.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUploadUtil {

	private Map<String, FileItem> itemMap;
	private String headerEncoding;

	/** Procesa un requestáde tipo RFC 1867 (multipart/form-data). */
	public FileUploadUtil(HttpServletRequest request) throws ServletException {
		try {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			if (request.getCharacterEncoding() != null) {
				upload.setHeaderEncoding(request.getCharacterEncoding());
				this.headerEncoding = request.getCharacterEncoding();
			}
			this.itemMap = new HashMap<String, FileItem>();
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				this.itemMap.put(item.getFieldName(), item);
			}
		} catch (FileUploadException fuex) {
			throw new ServletException(fuex);
		}
	}

	/** Devuelve un archivo enviado en el requestá */
	public FileItem getFileItem(String fieldName) {
		if (this.itemMap != null) {
			FileItem fileItem = this.itemMap.get(fieldName);
			if (fileItem != null && !fileItem.isFormField() && fileItem.getSize() > 0) {
				return fileItem;
			}
		}
		return null;
	}

	/** Devuelve un parámetro de texto enviado en el requestá */
	public String getParameter(String fieldName) {
		if (this.itemMap != null) {
			FileItem fileItem = this.itemMap.get(fieldName);
			if (fileItem != null && fileItem.isFormField()) {
				try {
					if (this.headerEncoding != null) {
						return fileItem.getString(this.headerEncoding);
					} else {
						return fileItem.getString();
					}
				} catch (UnsupportedEncodingException ex) {
					throw new RuntimeException(ex);
				}
			}
		}
		return null;
	}

	public Map<String, FileItem> getItemMap() {
		return itemMap;
	}
}
