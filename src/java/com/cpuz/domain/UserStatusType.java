package com.cpuz.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Enumerado para definición de Status (Ambitos) de los Users de la aplicación registrados
 */
public enum UserStatusType {
	RECEIVED(0, "received"), // Recibido
	WAITING(1, "waiting"), // En espera
	AUTHORIZED(2, "authorized"); // Autorizado
	private final int id;
	private final String key;

	UserStatusType(int id, String key) {
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
	 * Devuelve la lista de posibles estados (status) de los Bugs
	 * 
	 * @return Lista de tipos de Ambito de News
	 */
	public static List<UserStatusType> list() {
		return new ArrayList<>(Arrays.asList(UserStatusType.values()));
	}

	/** Parsea el entero y devuelve el valor del enumerado */
	public static UserStatusType parse(int i) {
		switch (i) {
			case 0:
				return UserStatusType.RECEIVED;
			case 1:
				return UserStatusType.WAITING;
			case 2:
				return UserStatusType.AUTHORIZED;
			default:
				throw new EnumConstantNotPresentException(UserStatusType.class, i
						+ ": Identificador para Status de User no permitido");
		}
	}
	/** Parsea la clave y devuelve el valor del enumerado */
	public static UserStatusType parse(String key) {
		switch (key) {
			case "received":
				return UserStatusType.RECEIVED;
			case "waiting":
				return UserStatusType.WAITING;
			case "authorized":
				return UserStatusType.AUTHORIZED;
			default:
				throw new EnumConstantNotPresentException(UserStatusType.class, key
						+ ": Identificador para Status de User no permitido");
		}
	}
}
