package com.cpuz.exceptions;

import javax.servlet.ServletException;

/**
 * Excepción que representa un error en los datos introducidos por el usuario. Es un error recuperable, así que la
 * página de error mostrada no debe ser muy agresiva ni obligar a salir de la aplicación.
 * 
 * Nota: el campo message debe contener una clave de internacionalización.
 */
public class UserCodexException extends ServletException {

	private String i18nParam;

	/**
	 * @param message
	 *            Clave de internacionalización del mensaje de error.
	 */
	public UserCodexException(String message) {
		super(message);
	}

	/**
	 * @param message
	 *            Clave de internacionalización del mensaje de error.
	 * @param i18nParam
	 *            Primer parámetro de internacionalización
	 */
	public UserCodexException(String message, String i18nParam) {
		super(message);
		this.i18nParam = i18nParam;
	}

	public UserCodexException(Throwable rootCause) {
		super(rootCause);
	}

	/**
	 * @param message
	 *            Clave de internacionalización del mensaje de error.
	 */
	public UserCodexException(String message, Throwable rootCause) {
		super(message, rootCause);
	}

	public String getI18nParam() {
		return i18nParam;
	}

}
