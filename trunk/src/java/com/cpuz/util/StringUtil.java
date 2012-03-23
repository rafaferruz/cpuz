package com.cpuz.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public final class StringUtil {

	private static final Logger log = Logger.getLogger(StringUtil.class);

	/**
	 * Comprueba si la cadena está vacía o es nula
	 * 
	 * @param str
	 *            la cadena
	 * @return true: si está vacío o es nulo false: si hay algo
	 */
	public static boolean isEmptyOrNull(String str) {
		if (str != null) {
			if (str.trim().length() > 0) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	/**
	 * Devuelve una cadena vacía si es nulo. Si no, devuelve la cadena.
	 * 
	 * @param str
	 *            cadena de texto
	 * @return String vacío o el valor de la cadena
	 */
	public static String nullToEmpty(String str) {
		if (str == null) {
			return "";
		} else {
			return str;
		}
	}

	public static String getCorrectedFileName(String nameFile) {
		String nameFileCorrected = "";

		nameFile = nameFile.replace("á", "a").replace("é", "e").replace("í", "i");
		nameFile = nameFile.replace("ó", "o").replace("ú", "u").replace("ñ", "n");

		for (int i = 0; i < nameFile.length(); i++) {
			char ch = nameFile.charAt(i);
			int valAscii = ch;

			if ((valAscii >= 48 && valAscii <= 57) || // 0 - 9
					(valAscii >= 65 && valAscii <= 90) || // A - Z
					(valAscii >= 97 && valAscii <= 122) || // a - z
					(valAscii == 46)) { // .
				nameFileCorrected += nameFile.charAt(i);
			}
		}
		return nameFileCorrected;
	}

	/**
	 * En una cadena de texto, cambia letras con acento por letras sin acento.
	 * 
	 * @param text
	 *            texto a transformar
	 * @return texto transformado
	 */
	public static String plainWrittenAccents(String text) {
		if (text == null) {
			return null;
		}
		return text.replaceAll("[áàäâã]", "a").replaceAll("[ÁÀÄÂÃ]", "A").replaceAll("[éèëê]", "e").replaceAll("[ÉÈËÊ]", "E").replaceAll("[íìïî]", "i").replaceAll("[ÍÌÏÎ]", "I").replaceAll("[óòöôõ]", "o").replaceAll("[ÓÒÖÔÕ]", "O").replaceAll("[úùüû]", "u").replaceAll("[ÚÙÜÛ]", "U");
	}

	public static String removeNonAscii(String text) {
		text = plainWrittenAccents(text);
		// Además sustituye ñ por n
		text = text.replaceAll("ñ", "n").replaceAll("Ñ", "N");
		String asciiText = "";
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			int valAscii = ch;
			if ((valAscii >= 32 && valAscii < 127) || valAscii == 10 || valAscii == 13) {
				asciiText += ch;
			}
		}
		return asciiText;
	}

	/**
	 * Escapa comillas simples y dobles de una cadena para su uso en JavaScript
	 * 
	 * @param str
	 * @return cadena escapada
	 */
	public static String escapeQuotesForJs(String str) {
		return str.replaceAll("\'", "\\\\\'").replaceAll("\"", "\\\\\"");
	}

	/**
	 * Se le pasa una lista de strings, y devuelve una cadena adecuada para hacer un IN ('a',...) en sql
	 * 
	 * @param string
	 * @return cadena
	 */
	public static String listStringToInSQL(List<String> string) {
		String stringWidthComma = "";
		if (!string.isEmpty()) {
			for (String cad : string) {
				stringWidthComma += "'" + cad + "', ";
			}
		}
		return stringWidthComma.substring(0, stringWidthComma.length() - 2);
	}

	/**
	 * Se le pasa una lista de strings, y devuelve una cadena adecuada para hacer un IN ('a',...) en sql
	 * 
	 * @param listInteger
	 * @return cadena
	 */
	public static String listIntegerToInSQL(List<Integer> listInteger) {
		String stringWidthComma = "";
		if (!listInteger.isEmpty()) {
			for (Integer cad : listInteger) {
				stringWidthComma += "" + cad + ", ";
			}
			stringWidthComma = stringWidthComma.substring(0, stringWidthComma.length() - 2);
		}
		return stringWidthComma;
	}

	/**
	 * Dice si el string que se pasa por parámetro es alguno de los que se ponen a continuación.
	 * 
	 * @param compareStr
	 *            cadena que vamos a checkear
	 * @param matchStr
	 *            lista de cadenas con las que comprobar si existe la cadena compareStr
	 * @return true si la lista de cadenas contiene la cadena buscada
	 */
	public static boolean isOneOf(String compareStr, String... matchStr) {
		for (String match : matchStr) {
			if (match.equals(compareStr)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Devuelve una cadena con el número de caracteres que le indiquemos, completando hasta el total con el carácter
	 * indicado por delante.
	 * 
	 * @param stringToFormat
	 *            cadena a formatear
	 * @param numCharacters
	 *            número de caracteres de la cadena
	 * @param character
	 *            carácter con el que se rellenará la cadena
	 * @return cadena formateada
	 */
	public static String stringFormat(String stringToFormat, int numCharacters, char character) {
		String stringFormatted = "";

		if (stringToFormat != null) {
			stringFormatted = stringToFormat.trim();
		}

		if (stringFormatted.length() < numCharacters) {
			for (int i = stringFormatted.length(); i < numCharacters; i++) {
				stringFormatted = character + stringFormatted;
			}
		} else {
			stringFormatted = stringFormatted.substring(0, numCharacters);
		}
		return stringFormatted;
	}

	public static String getString(HttpServletRequest request, String parameter, int maxLength) {
		if (!Misc.isEmptyOrNull(request, parameter)) {
			String newString = request.getParameter(parameter);
			if (newString.length() > maxLength) {
				newString = newString.substring(0, maxLength);
			}
			return newString;
		} else {
			return null;
		}
	}

	/**
	 * Dada una cadena, elimina del inicio los caracteres consecutivos que encuentra iguales al carácter dado
	 * 
	 * @param stringToUnFormat
	 * @param character
	 * @return cadena sin los caracteres iguales al parámatro al comienzo de la misma
	 */
	public static String stringUnFormat(String stringToUnFormat, char character) {
		Pattern pattern = Pattern.compile("^[" + character + "]+");
		String[] parts = pattern.split(stringToUnFormat);
		if (parts.length == 1) {
			return parts[0];
		} else {
			return parts[1];
		}
	}

	/**
	 * Devuelve una cadena con la primera letra de cada palabra mayúscula y las demás minúsculas.
	 * 
	 * @param str
	 *            cadena a transformar a mayúsculas
	 * @return cadena en mayúsculas
	 */
	public static String toCamelCase(String str) {
		StringBuilder sb = new StringBuilder();
		for (String word : str.split(" ")) {
			if (word.length() > 0) {
				sb.append(word.substring(0, 1).toUpperCase());
			}
			if (word.length() > 1) {
				sb.append(word.substring(1).toLowerCase());
			}
			sb.append(" ");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public static boolean startsWithVowel(String str) {
		char c = str.toLowerCase().charAt(0);
		return (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u');
	}

	/**
	 * Devuelve una cadena aleatoria de la longitud requerida.
	 * 
	 * @param strLength
	 *            longitud de la cadena
	 * @return cadena generada aleatoriamente
	 */
	public static String getRandomString(int strLength) {
		String alphabet = "abcdefghijklmnopqrsuvwxyz";
		int alphabetLength = "abcdefghijklmnopqrsuvwxyz".length();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strLength; i++) {
			sb.append(alphabet.charAt((int) Math.floor(Math.random() * alphabetLength)));
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param stringToSeparate
	 * @param character
	 * @return 
	 */
	public static List<String> separateStringInPiecesAccordingACharacter(String stringToSeparate, String character) {
		List<String> stringsSeparate = new ArrayList<>();
		int index = 0;
		while (index != -1) {
			int indexFinal = stringToSeparate.indexOf("_", index);
			if (indexFinal == -1) {
				stringsSeparate.add(stringToSeparate.substring(index));
				index = indexFinal;
			} else {
				stringsSeparate.add(stringToSeparate.substring(index, indexFinal));
				index = indexFinal + 1;
			}
		}
		return stringsSeparate;
	}

	/**
	 * Devuelve el valor del parámetro dado en el requestá
	 * Si es parámetro está vacío, lanza RuntimeException.
	 * 
	 * @param requestárequestáque contiene el parámetro
	 * @param param		nombre del parámetro que se recibe
	 * @throws RuntimeException
	 *			 si el parámetro está vacío
	 * @return valor asociado al parámetro dado
	 */
	public static String getString(HttpServletRequest request, String param) {
		if (StringUtil.isEmptyOrNull(request.getParameter(param))) { // Si es null...
			throw new RuntimeException("El parámetro: -" + param + "- es nulo o vacío");
		}
		return request.getParameter(param);
	}
}
