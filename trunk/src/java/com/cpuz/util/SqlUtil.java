package com.cpuz.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public final class SqlUtil {

	public static void addCondition(StringBuilder queryConditions, int condition, String field) {
		if (condition != -1) {
			queryConditions.append((queryConditions.length() > 0) ? " AND " : "").append(field + "=? ");
		}
	}

	public static void addCondition(StringBuilder queryConditions, List<Integer> condition, String field) {
		if (condition != null && !condition.isEmpty()) {
			queryConditions.append((queryConditions.length() > 0) ? " AND " : "").append(field).append(" IN (").append(StringUtil.listIntegerToInSQL(condition)).append(") ");
		}
	}

	public static void addNotNullCondition(StringBuilder queryConditions, String field) {
		queryConditions.append((queryConditions.length() > 0) ? " AND " : "").append(field + " IS NOT NULL ");
	}

	public static void addNullCondition(StringBuilder queryConditions, String field) {
		queryConditions.append((queryConditions.length() > 0) ? " AND " : "").append(field + " IS NULL ");
	}

	public static void addCondition(StringBuilder queryConditions, String condition, String field) {
		if (!StringUtil.isEmptyOrNull(condition)) {
			queryConditions.append((queryConditions.length() > 0) ? " AND " : "").append(
					"upper( " + field + ") LIKE ? ");
		}
	}

	public static void addCondition(StringBuilder queryConditions, Boolean condition, String field) {
		if (condition != null) {
			queryConditions.append((queryConditions.length() > 0) ? " AND " : "").append(field + "=? ");
		}
	}

	public static void addCondition(StringBuilder queryConditions, Object condition, String field) {
		if (condition != null) {
			queryConditions.append((queryConditions.length() > 0) ? " AND " : "").append(field + "=? ");
		}
	}

	public static void addCondition(StringBuilder queryConditions, Date fromDate, Date toDate, String field) {
		if (fromDate != null && toDate != null) {
			queryConditions.append((queryConditions.length() > 0) ? " AND " : "").append(
					"(" + field + " >= ? AND " + field + " <= ?) ");
		} else if (fromDate != null && toDate == null) {
			queryConditions.append((queryConditions.length() > 0) ? " AND " : "").append(field + " >= ? ");
		} else if (fromDate == null && toDate != null) {
			queryConditions.append((queryConditions.length() > 0) ? " AND " : "").append(field + " <= ? ");
		}
	}

	public static void addCondition(StringBuilder queryConditions, int minValue, int maxValue, String field) {
		if (minValue != -1 && maxValue != -1) {
			queryConditions.append((queryConditions.length() > 0) ? " AND " : "").append(
					"(ABS(" + field + ")" + " >= ? AND ABS(" + field + ")" + " <= ?) ");
		} else if (minValue != -1 && maxValue == -1) {
			queryConditions.append((queryConditions.length() > 0) ? " AND " : "").append(
					"ABS(" + field + ")" + " >= ? ");
		} else if (minValue == -1 && maxValue != -1) {
			queryConditions.append((queryConditions.length() > 0) ? " AND " : "").append(
					"ABS(" + field + ")" + " <= ? ");
		}
	}

	public static int addConditionValue(PreparedStatement ps, int index, int conditionValue) throws SQLException {
		int nextIndex = index;
		if (conditionValue != -1) {
			ps.setInt(nextIndex, conditionValue);
			nextIndex++;
		}
		return nextIndex;
	}

	public static int addConditionValue(PreparedStatement ps, int index, Integer conditionValue) throws SQLException {
		int nextIndex = index;
		if (conditionValue != null) {
			ps.setInt(nextIndex, conditionValue);
			nextIndex++;
		}
		return nextIndex;
	}

	public static int addConditionValue(PreparedStatement ps, int index, String conditionValue) throws SQLException {
		int nextIndex = index;
		if (!StringUtil.isEmptyOrNull(conditionValue)) {
			ps.setString(nextIndex, "%" + conditionValue.trim().toUpperCase() + "%");
			nextIndex++;
		}
		return nextIndex;
	}

	public static int addConditionValue(PreparedStatement ps, int index, Boolean conditionValue) throws SQLException {
		int nextIndex = index;
		if (conditionValue != null) {
			ps.setBoolean(nextIndex, conditionValue);
			nextIndex++;
		}
		return nextIndex;
	}

	public static int addConditionValue(PreparedStatement ps, int index, Date fromDate, Date toDate)
			throws SQLException {
		int nextIndex = index;
		if (fromDate != null) {
			ps.setDate(nextIndex, new java.sql.Date(fromDate.getTime()));
			nextIndex++;
		}
		if (toDate != null) {
			ps.setDate(nextIndex, new java.sql.Date(toDate.getTime()));
			nextIndex++;
		}
		return nextIndex;
	}

	public static int addConditionValue(PreparedStatement ps, int index, int minValue, int maxValue)
			throws SQLException {
		int nextIndex = index;
		if (minValue != -1) {
			ps.setInt(nextIndex, minValue);
			nextIndex++;
		}
		if (maxValue != -1) {
			ps.setInt(nextIndex, maxValue);
			nextIndex++;
		}
		return nextIndex;
	}

	public static String linkSelectWhereOrderBy(String select, String where, String orderBy) {
		String sql = select;
		if (!StringUtil.isEmptyOrNull(where)) {
			if (select.indexOf("WHERE") != -1) { // Si el select tiene un WHERE....
				sql += " AND " + where;
			} else if (where.indexOf("WHERE") != -1) { // Si el where tiene un WHERE...
				sql += " " + where;
			} else { // Si ninguno tiene el WHERE
				sql += " WHERE " + where;
			}
		}

		if (!StringUtil.isEmptyOrNull(orderBy)) {
			if (sql.indexOf("ORDER BY") != -1) { // Si el select tiene un ORDER BY....
				sql += ", " + orderBy;
			} else if (orderBy.indexOf("ORDER BY") != -1) { // Si el orderBy tiene un ORDER BY...
				sql += ", " + orderBy;
			} else { // Si ninguno tiene el ORDER BY
				sql += " ORDER BY " + orderBy;
			}
		}

		return sql;
	}

	/**
	 * Devuelve una lista de ? para incluir en el SQL de un PreparedStatement como SELECT * FROM users WHERE id IN (?,
	 * ?, ?)
	 */
	public static String getPreparedStatementInClause(Collection values) {
		if (values == null || values.isEmpty()) {
			throw new IllegalArgumentException("Recibida lista vacía");
		}
		StringBuilder sb = new StringBuilder("(");
		for (Object value : values) {
			if (value != null) {
				sb.append("?,");
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		return sb.toString();
	}

	/**
	 * Devuelve una lista de enteros, encerrada entre paréntesis y separados por el comas
	 * para incluir en la claúsula IN de una sentencia SQL
	 */
	public static String getIntegersStatementInClause(Collection<Integer> values) {
		if (values == null || values.isEmpty()) {
			throw new IllegalArgumentException("Recibida lista vacía");
		}
		StringBuilder sb = new StringBuilder("(");
		for (Object value : values) {
			if (value != null) {
				sb.append(value).append(",");
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		return sb.toString();
	}

	/**
	 * Asigna a un PreparedStatement una lista de String
	 * 
	 * @param firstIndex
	 *            El primer índice que se va a setear
	 * @return El indice del proximo valor a setear en el PreparedStatement.
	 */
	public static int setList(PreparedStatement ps, int firstIndex, Collection values) throws SQLException {
		int index = firstIndex;
		for (Object value : values) {
			if (value != null) {
				if (value instanceof String) {
					ps.setString(index, (String) value);
				} else if (value instanceof Integer) {
					ps.setInt(index, (Integer) value);
				} else {
					// TODO: implementar el restá de tipos
					ps.setObject(index, value);
				}
				index++;
			}
		}
		return index;
	}

	/**
	 * Asigna a un PreparedStatement una lista de String
	 * 
	 * @return El indice del proximo valor a setear en el PreparedStatement.
	 */
	public static int setList(PreparedStatement ps, Collection values) throws SQLException {
		return setList(ps, 1, values);
	}
}
