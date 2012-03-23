package com.cpuz.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Enumerado para definición de Statuss (Ambitos) de publicación de una News (Noticia)
 */
public enum HeaderType {
	HEADLINE(0, "Titular"), // Titular de noticia
	SUBTITLE(1, "Subtítulo"), // Subtítulo
	REMARKED(2, "Destacado"); // Frase destacada
	private final int id;
	private final String key;

	HeaderType(int id, String key) {
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
	public static List<HeaderType> list() {
		return new ArrayList<>(Arrays.asList(HeaderType.values()));
	}

	/** Parsea el entero y devuelve el valor del enumerado */
	public static HeaderType parse(int i) {
		switch (i) {
			case 0:
				return HeaderType.HEADLINE;
			case 1:
				return HeaderType.SUBTITLE;
			case 2:
				return HeaderType.REMARKED;
			default:
				throw new EnumConstantNotPresentException(HeaderType.class, i
						+ ": Identificador para tipo de Cabecera no permitido");
		}
	}
	/** Parsea la clave y devuelve el valor del enumerado */
	public static HeaderType parse(String key) {
		switch (key) {
			case "Titular":
				return HeaderType.HEADLINE;
			case "Subtítulo":
				return HeaderType.SUBTITLE;
			case "Destacado":
				return HeaderType.REMARKED;
			default:
				throw new EnumConstantNotPresentException(HeaderType.class, key
						+ ": Identificador para tipo de Cabecera no permitido");
		}
	}
}
