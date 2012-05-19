package com.cpuz.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Enumerado para definición de Statuss (Ambitos) de publicación de una News (Noticia)
 */
public enum ComponentType {
	INFOBLOCK(0, "InfoBlock"), // Bloque de Información
	IMAGE(1, "Image"), // Imagen
	DOCUMENT(2, "Document"), // Fichero de documento
	NEWS(3, "News"); // Noticia
	private final int id;
	private final String key;

	ComponentType(int id, String key) {
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
	public static List<ComponentType> list() {
		return new ArrayList<>(Arrays.asList(ComponentType.values()));
	}

	/** Parsea el entero y devuelve el valor del enumerado */
	public static ComponentType parse(int i) {
		switch (i) {
			case 0:
				return ComponentType.INFOBLOCK;
			case 1:
				return ComponentType.IMAGE;
			case 2:
				return ComponentType.DOCUMENT;
			case 3:
				return ComponentType.NEWS;
			default:
				throw new EnumConstantNotPresentException(ComponentType.class, i
						+ ": Identificador para tipo de Componente no permitido");
		}
	}
	/** Parsea la clave y devuelve el valor del enumerado */
	public static ComponentType parse(String key) {
		switch (key) {
			case "InfoBlock":
				return ComponentType.INFOBLOCK;
			case "Image":
				return ComponentType.IMAGE;
			case "Document":
				return ComponentType.DOCUMENT;
			case "News":
				return ComponentType.NEWS;
			default:
				throw new EnumConstantNotPresentException(ComponentType.class, key
						+ ": Identificador para tipo de Componente no permitido");
		}
	}
}
