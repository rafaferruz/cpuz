package com.cpuz.util;

import com.cpuz.exceptions.UserException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;

public class Misc {

	private static final Logger log = Logger.getLogger(Misc.class);


	/**
	 * Comprueba el parámetro del requestáes nulo o vacío
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param str
	 *            Identificador del parámetro
	 * @return true: si está vacío o es nulo false: si hay algo
	 */
	public static boolean isEmptyOrNull(HttpServletRequest request, String str) {
		if (request.getParameter(str) != null) {
			if (request.getParameter(str).trim().length() > 0) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	/**
	 * Comprueba el parámetro del requestáes nulo o vacío
	 * 
	 * @param fup
	 *            fileUploadUtil
	 * @param str
	 *            Identificador del parámetro
	 * @return true: si está vacío o es nulo false: si hay algo
	 */
	public static boolean isEmptyOrNull(FileUploadUtil fup, String str) {
		if (fup.getParameter(str) != null) {
			if (fup.getParameter(str).trim().length() > 0) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	/**
	 * Lee un archivo por líneas y devuelve una lista con cada una de las líneas leídas
	 * 
	 * @param file
	 *            flujo del archivo
	 * @return lista de líneas leídas
	 * @throws UserException
	 */
	public static List<String> getFileLines(InputStream file) throws UserException {
		InputStreamReader fr = null;
		BufferedReader br = null;
		List<String> lines = new ArrayList<String>();
		try {
			fr = new InputStreamReader(file);
			br = new BufferedReader(fr);

			String linea;
			while ((linea = br.readLine()) != null) {
				lines.add(linea);
			}

		} catch (FileNotFoundException fe) {
			throw new UserException("No se ha encontrado el archivo: " + fe);
		} catch (IOException ioex) {
			throw new UserException("Error de lectura del archivo: " + ioex);
		} finally {
			try {
				fr.close();
			} catch (IOException ex) {
				throw new UserException("Error al recoger la información del archivo " + ex);
			}
		}
		return lines;
	}

	/**
	 * Devuelve el archivo subido como una lista de líneas
	 * 
	 * @param request
	 *            request
	 * @param parameter
	 *            parámetro archivo
	 * @return lista de líneas leídas del archivo
	 * @throws UserException
	 */
	public static List<String> getFileLines(HttpServletRequest request, String parameter) throws UserException {
		List<String> lines = null;
		BufferedInputStream file;
		try {
			FileUploader f = new FileUploader(request);
			if (!f.isThereAnyFile()) {
				throw new UserException("UserException.paymentFileNotContainsPayments");
			}
			file = new BufferedInputStream(f.readFile(parameter));
			if (file != null) {
				lines = Misc.getFileLines(file);
			}
		} catch (FileUploadException ex) {
			log.error("Error al subir el archivo", ex);
			throw new UserException("Error al subir archivo. " + ex);
		}
		return lines;
	}

	/**
	 * Devuelve la url del servidor contenida en el request
	 * 
	 * @param request
	 * @return url del servidor contenida en el request
	 */
	public static String getServerUrl(HttpServletRequest request) {
		StringBuffer serverUrl = request.getRequestURL();
		serverUrl.delete(serverUrl.indexOf(request.getContextPath()), serverUrl.length());
		serverUrl.append(request.getContextPath());
		return serverUrl.toString();
	}


	/** Devuelve un entero entre 0 (incluido) y maxValue (no incluido). */
	private static int getRandonInteger(int maxValue) {
		return (int) Math.floor(Math.random() * maxValue);
	}

	public static String generatePassword() {
		String vowels = "aeiou";
		String consonants = "bcdfghjklmnpqrstvwxyz";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			if (i % 2 == 0) {
				sb.append(consonants.charAt(getRandonInteger(20)));
			} else {
				sb.append(vowels.charAt(getRandonInteger(5)));
			}
		}
		return sb.toString();
	}

	/**
	 * Comprueba si un objeto es null y lanza una excepción personalizada.
	 * 
	 * @param object
	 *            Objeto que se comprueba
	 * @param i18nMessage
	 *            mensaje internacionalizado a mostrar
	 * @throws UserException
	 */
	public static void checkNull(Object object, String i18nMessage) throws UserException {
		if (object == null) {
			throw new UserException(i18nMessage);
		}
	}

	/**
	 * Comprueba si un objeto es null y lanza una excepción personalizada.
	 * 
	 * @param object
	 *            Objeto que se comprueba
	 * @param i18nMessage
	 *            mensaje internacionalizado
	 * @param i18nParam
	 *            parámetro del mensaje
	 * @throws UserException
	 */
	public static void checkNull(Object object, String i18nMessage, Object i18nParam) throws UserException {
		if (object == null) {
			if (i18nParam != null) {
				String i18nString = String.valueOf(i18nParam);
				if (!i18nString.isEmpty()) {
					throw new UserException(i18nMessage);
				} else {
//	FIXME: La he comentado para que no dé error				throw new UserException(i18nMessage, i18nString);
				}
			}
		}
	}
}
