package com.cpuz.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Enumerado para definición de Scope (Ambitos) de los Images
 */
public enum ScopeType {
	GLOBAL(0, "global"), // Global
	VECINITY(1, "vecinity"), // Vecinal
	RESTRICTED(2, "restricted"); // Restringido
	private final int id;
	private final String key;

	ScopeType(int id, String key) {
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
	 * Devuelve la lista de posibles ámbitos (Scopes) de los Images
	 * 
	 * @return Lista de tipos de Ambito de Images
	 */
	public static List<ScopeType> list() {
		return new ArrayList<>(Arrays.asList(ScopeType.values()));
	}

	/** Parsea el entero y devuelve el valor del enumerado */
	public static ScopeType parse(int i) {
		switch (i) {
			case 0:
				return ScopeType.GLOBAL;
			case 1:
				return ScopeType.VECINITY;
			case 2:
				return ScopeType.RESTRICTED;
			default:
				throw new EnumConstantNotPresentException(ScopeType.class, i
						+ ": Identificador para Scope de Image no permitido");
		}
	}
	/** Parsea la clave y devuelve el valor del enumerado */
	public static ScopeType parse(String key) {
		switch (key) {
			case "global":
				return ScopeType.GLOBAL;
			case "vecinity":
				return ScopeType.VECINITY;
			case "restricted":
				return ScopeType.RESTRICTED;
			default:
				throw new EnumConstantNotPresentException(ScopeType.class, key
						+ ": Identificador para Scope de Image no permitido");
		}
	}
}
