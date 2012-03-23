package com.cpuz.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Enumerado para definición de Scopes (Ambitos) de publicación de una News (Noticia)
 */
public enum NewsPieceScopeType {

	GLOBAL(0, "npiScopeType.global"), // Global, de acceso público total
	VECINAL(1, "npiScopeType.vecinal"), // Vecinal, de acceso para usuarios registrados
	CONFIDENCIAL(2, "npiScopeType.confidencial"); // Confidencial, de acceso restringido para administradores
	private final int id;
	private final String key;

	NewsPieceScopeType(int id, String key) {
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
	 * Devuelve la lista de tipos de Ambito de usuarios destinatarios
	 * 
	 * @return Lista de tipos de Ambito de News
	 */
	public static List<NewsPieceScopeType> list() {
		return new ArrayList<>(Arrays.asList(NewsPieceScopeType.values()));
	}

	/** Parsea el entero y devuelve el valor del enumerado */
	public static NewsPieceScopeType parse(int i) {
		switch (i) {
			case 0:
				return NewsPieceScopeType.GLOBAL;
			case 1:
				return NewsPieceScopeType.VECINAL;
			case 2:
				return NewsPieceScopeType.CONFIDENCIAL;
			default:
				throw new EnumConstantNotPresentException(NewsPieceScopeType.class, i
						+ ": Identificador para tipo de Ambito de NewsPiece no permitido");
		}
	}
}
