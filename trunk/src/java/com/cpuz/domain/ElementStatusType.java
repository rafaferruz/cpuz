package com.cpuz.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Enumerado para definición de Status (Ambitos) de los Elements
 */
public enum ElementStatusType {
	RECEIVED(0, "received"), // Recibido
	WAITING(1, "waiting"), // En espera
	AUTHORIZED(2, "authorized"); // Autorizado
	private final int id;
	private final String key;

	ElementStatusType(int id, String key) {
		this.id = id;
		this.key = key;
	}

	public int getId() {
		return id;
	}

	public String getKey() {
		return key;
	}

	/**
	 * Devuelve la lista de posibles estados (status) de los Elements
	 * 
	 * @return Lista de tipos de Ambito de News
	 */
	public static List<ElementStatusType> list() {
		return new ArrayList<>(Arrays.asList(ElementStatusType.values()));
	}

	/** Parsea el entero y devuelve el valor del enumerado */
	public static ElementStatusType parse(int i) {
		switch (i) {
			case 0:
				return ElementStatusType.RECEIVED;
			case 1:
				return ElementStatusType.WAITING;
			case 2:
				return ElementStatusType.AUTHORIZED;
			default:
				throw new EnumConstantNotPresentException(ElementStatusType.class, i
						+ ": Identificador para Status de InfoBlock no permitido");
		}
	}
	/** Parsea la clave y devuelve el valor del enumerado */
	public static ElementStatusType parse(String key) {
		switch (key) {
			case "received":
				return ElementStatusType.RECEIVED;
			case "waiting":
				return ElementStatusType.WAITING;
			case "authorized":
				return ElementStatusType.AUTHORIZED;
			default:
				throw new EnumConstantNotPresentException(ElementStatusType.class, key
						+ ": Identificador para Status de InfoBlock no permitido");
		}
	}
}
