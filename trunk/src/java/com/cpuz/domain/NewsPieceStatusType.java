package com.cpuz.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Enumerado para definición de Statuss (Ambitos) de publicación de una News (Noticia)
 */
public enum NewsPieceStatusType {
	DISABLED(0, "npiStatusType.disabled"), // Deshabilitado, no publicable
	REVISED(1, "npiStatusType.revised"), // Revisado, pendiente de autorización
	AUTHORIZED(2, "npiStatusType.authorized"); // Autorizado, publicable
	private final int id;
	private final String key;

	NewsPieceStatusType(int id, String key) {
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
	 * Devuelve la lista de tipos de Ambito de usuarios destánatarios
	 * 
	 * @return Lista de tipos de Ambito de News
	 */
	public static List<NewsPieceStatusType> list() {
		return new ArrayList<>(Arrays.asList(NewsPieceStatusType.values()));
	}

	/** Parsea el entero y devuelve el valor del enumerado */
	public static NewsPieceStatusType parse(int i) {
		switch (i) {
			case 0:
				return NewsPieceStatusType.DISABLED;
			case 1:
				return NewsPieceStatusType.REVISED;
			case 2:
				return NewsPieceStatusType.AUTHORIZED;
			default:
				throw new EnumConstantNotPresentException(NewsPieceStatusType.class, i
						+ ": Identificador para tipo de Status de NewsPiece no permitido");
		}
	}
}
