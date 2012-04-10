package com.cpuz.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Enumerado para definición de Statuss (Ambitos) de publicación de una News (Noticia)
 */
public enum BugType {
	UNDEFINED(0, "undefined"), // No definido
	ENHANCEMENT(1, "enhancement"), // mejora
	ERROR(2, "error"), // error
	DEVELOPMENT(3, "development"); // nuevo desarrollo
	private final int id;
	private final String key;

	BugType(int id, String key) {
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
	 * Devuelve la lista de tipos de Componentes de noticias
	 * 
	 * @return Lista de tipos de Ambito de News
	 */
	public static List<BugType> list() {
		return new ArrayList<>(Arrays.asList(BugType.values()));
	}

	/** Parsea el entero y devuelve el valor del enumerado */
	public static BugType parse(int i) {
		switch (i) {
			case 0:
				return BugType.UNDEFINED;
			case 1:
				return BugType.ENHANCEMENT;
			case 2:
				return BugType.ERROR;
			case 3:
				return BugType.DEVELOPMENT;
			default:
				throw new EnumConstantNotPresentException(BugType.class, i
						+ ": Identificador para tipo de Bug no permitido");
		}
	}
	/** Parsea la clave y devuelve el valor del enumerado */
	public static BugType parse(String key) {
		switch (key) {
			case "undefined":
				return BugType.UNDEFINED;
			case "enhancement":
				return BugType.ENHANCEMENT;
			case "error":
				return BugType.ERROR;
			case "development":
				return BugType.DEVELOPMENT;
			default:
				throw new EnumConstantNotPresentException(BugType.class, key
						+ ": Identificador para tipo de Bug no permitido");
		}
	}
}
