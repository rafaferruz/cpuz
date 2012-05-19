package com.cpuz.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Enumerado para definición de Status (Ambitos) de los Elements
 */
public enum ElementType {
	TITLE(0, "title"), // Titular
	SUBTITLE(1, "subtitle"), // Subtitulo
	REMARKED(2, "remarked"); // Destacado
	private final int id;
	private final String key;

	ElementType(int id, String key) {
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
	public static List<ElementType> list() {
		return new ArrayList<>(Arrays.asList(ElementType.values()));
	}

	/** Parsea el entero y devuelve el valor del enumerado */
	public static ElementType parse(int i) {
		switch (i) {
			case 0:
				return ElementType.TITLE;
			case 1:
				return ElementType.SUBTITLE;
			case 2:
				return ElementType.REMARKED;
			default:
				throw new EnumConstantNotPresentException(ElementType.class, i
						+ ": Identificador para Tipo de InfoBlock no permitido");
		}
	}
	/** Parsea la clave y devuelve el valor del enumerado */
	public static ElementType parse(String key) {
		switch (key) {
			case "title":
				return ElementType.TITLE;
			case "subtitle":
				return ElementType.SUBTITLE;
			case "remarked":
				return ElementType.REMARKED;
			default:
				throw new EnumConstantNotPresentException(ElementType.class, key
						+ ": Identificador para Tipo de InfoBlock no permitido");
		}
	}
}
