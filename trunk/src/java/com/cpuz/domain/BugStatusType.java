package com.cpuz.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Enumerado para definición de Status (Ambitos) de los Bugs de la aplicación registrados
 */
public enum BugStatusType {
	CREATED(0, "created"), // Creado
	INCOURSE(1, "incourse"), // En curso de solución
	ENDED(2, "ended"); // Finalizado
	private final int id;
	private final String key;

	BugStatusType(int id, String key) {
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
	public static List<BugStatusType> list() {
		return new ArrayList<>(Arrays.asList(BugStatusType.values()));
	}

	/** Parsea el entero y devuelve el valor del enumerado */
	public static BugStatusType parse(int i) {
		switch (i) {
			case 0:
				return BugStatusType.CREATED;
			case 1:
				return BugStatusType.INCOURSE;
			case 2:
				return BugStatusType.ENDED;
			default:
				throw new EnumConstantNotPresentException(BugStatusType.class, i
						+ ": Identificador para Status de Bug no permitido");
		}
	}
	/** Parsea la clave y devuelve el valor del enumerado */
	public static BugStatusType parse(String key) {
		switch (key) {
			case "created":
				return BugStatusType.CREATED;
			case "incourse":
				return BugStatusType.INCOURSE;
			case "ended":
				return BugStatusType.ENDED;
			default:
				throw new EnumConstantNotPresentException(BugStatusType.class, key
						+ ": Identificador para Status de Bug no permitido");
		}
	}
}
