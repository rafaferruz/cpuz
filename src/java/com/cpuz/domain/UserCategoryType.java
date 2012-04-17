package com.cpuz.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Enumerado para definición de Status (Ambitos) de los Users de la aplicación registrados
 */
public enum UserCategoryType {
	BASIC(0, "basic"), // normal
	MANAGER(1, "manager"), // manager de sección
	ADMIN(2, "admin"); // administrador de aplicación
	private final int id;
	private final String key;

	UserCategoryType(int id, String key) {
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
	 * @return Lista de tipos de Categorías de Usuarios
	 */
	public static List<UserCategoryType> list() {
		return new ArrayList<>(Arrays.asList(UserCategoryType.values()));
	}

	/** Parsea el entero y devuelve el valor del enumerado */
	public static UserCategoryType parse(int i) {
		switch (i) {
			case 0:
				return UserCategoryType.BASIC;
			case 1:
				return UserCategoryType.MANAGER;
			case 2:
				return UserCategoryType.ADMIN;
			default:
				throw new EnumConstantNotPresentException(UserCategoryType.class, i
						+ ": Identificador para Category de User no permitido");
		}
	}
	/** Parsea la clave y devuelve el valor del enumerado */
	public static UserCategoryType parse(String key) {
		switch (key) {
			case "basic":
				return UserCategoryType.BASIC;
			case "manager":
				return UserCategoryType.MANAGER;
			case "admin":
				return UserCategoryType.ADMIN;
			default:
				throw new EnumConstantNotPresentException(UserCategoryType.class, key
						+ ": Identificador para Category de User no permitido");
		}
	}
}
