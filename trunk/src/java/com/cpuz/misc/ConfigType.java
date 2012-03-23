package com.cpuz.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Define un enumerador con los tipos de configuraciones posibles
 * 
 * @author RAFAEL FERRUZ
 */
public enum ConfigType {

	CONFIG(1, "ConfigType.config");
	private final int id;
	private final String key;

	ConfigType(int id, String key) {
		this.id = id;
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public int getId() {
		return id;
	}

	public static ConfigType parse(int id) {
		switch (id) {
			case 1:
				return CONFIG;
			default:
				throw new EnumConstantNotPresentException(ConfigType.class, "Identificador para Configuración: " + id);
		}

	}

	public static List<ConfigType> listConfigTypes() {
		return new ArrayList<ConfigType>(Arrays.asList(ConfigType.values()));
	}
}
