package com.cpuz.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tipos de Usuario
 */
public enum UserType {

	ANONYMOUS(0, "userAnonimous"),		// Usuario anónimo
	REGISTERED(1, "userRegistered"),	// Usuario registrado
	ADMIN(2, "userAdmin");				// Usuario administrador

	private final int id;
	private final String key;

	UserType(int id, String key) {
		this.id = id;
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public int getId() {
		return id;
	}

	public static List<UserType> list() {
		return new ArrayList<>(Arrays.asList(UserType.values()));
	}

	public static UserType parse(int id) {
		switch (id) {
			case 0:
				return UserType.ANONYMOUS;
			case 1:
				return UserType.REGISTERED;
			case 2:
				return UserType.ADMIN;
			default:
				throw new EnumConstantNotPresentException(UserType.class, id
					+ " :Identificador de tipo de usuario no permitido");
		}
	}
}
