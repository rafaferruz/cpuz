/*
 * Copyright 2012 Rafael Ferruz
 * 
 * This file is part of CPUZ.
 * 
 * CPUZ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * CPUZ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with CPUZ.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.cpuz.DAO;

import com.cpuz.DAO.impl.InjectableDAO;
import com.cpuz.domain.Document;
import com.cpuz.st2.beans.ControlParams;
import com.cpuz.util.SqlUtil;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * Clase para la ejecución de operaciones CRUD sobre la tabla 'documents'.
 */
public class DocumentDAO implements InjectableDAO{

	transient private final Logger log = Logger.getLogger(this.getClass());
	private Connection conn;

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	/**
	 * Añade el Document a la tabla 
	 *
	 * @param rec		Objeto Document que se quiere insertar en la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha insertado con éxito.
	 * @throws SQLException 
	 */
	public synchronized int create(Document rec) throws SQLException {

		String sql = "INSERT INTO documents "
				+ "(doc_id, "
				+ "doc_date, "
				+ "doc_user, "
				+ "doc_user_reference, "
				+ "doc_filename, "
				+ "doc_repository_reference, "
				+ "doc_scope) "
				+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, rec.getId());
		ps.setDate(2, new java.sql.Date(rec.getDatetime().getTime()));
		ps.setString(3, rec.getUser());
		ps.setString(4, rec.getUserReference());
		ps.setString(5, rec.getFilename());
		ps.setString(6, rec.getRepositoryReference());
		ps.setInt(7, rec.getScope());
		log.debug("DocumentDAO create(): " + ps.toString());
		return ps.executeUpdate();
	}

	/**
	 * Recupera un Document de la tabla 
	 *
	 * @param documentId		Id del objeto Document que se quiere recuperar de la tabla
	 * @return			Un objeto Document  con la información recuperada de la 
	 *					base de datos. Si no encuentra el id buscado, devuelve null.
	 * @throws SQLException 
	 */
	public Document read(int documentId) throws SQLException {
		Document document = null;
		String sql = "SELECT * FROM documents WHERE doc_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, documentId);
		log.debug("DocumentDAO read(): " + ps.toString());
		ResultSet rs = ps.executeQuery(sql);
		if (rs.next()) {
			document = getCompleteDocument(rs);
		}
		return document;
	}

	/**
	 * Actualiza un Document en la tabla 
	 *
	 * @param document		Objeto Document que se quiere actualizar en la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha insertado con éxito.
	 * @throws SQLException 
	 */
	public int update(Document document) throws SQLException {
		int rowCount = 0;
		String sql = "UPDATE documents SET "
				+ "doc_user_reference =  = ?,"
				+ "doc_filename = ?,"
				+ "doc_repository_reference = ?,"
				+ "doc_scope = ? "
				+ " WHERE doc_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, document.getUserReference());
		ps.setString(2, document.getFilename());
		ps.setString(3, document.getRepositoryReference());
		ps.setInt(4, document.getScope());
		log.debug("DocumentDAO update(): " + ps.toString());
		rowCount = ps.executeUpdate();
		return rowCount;
	}

	/**
	 * Elimina un Document de la tabla 
	 *
	 * @param secId		Id del objeto Document que se quiere eliminar de la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha eliminado con éxito.
	 * @throws SQLException 
	 */
	public int delete(Integer documentId) throws SQLException {
		int rowCount = 0;
		String sql = "DELETE FROM documents WHERE doc_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, documentId);
		log.debug("DocumentDAO delete(): " + ps.toString());
		rowCount = ps.executeUpdate();
		return rowCount;
	}

	public List<Document> getDocumentList(ControlParams control) throws SQLException {
		List<Document> documents = new ArrayList<>();
		String sql = "SELECT * FROM documents ORDER BY doc_id ";
		String limit = control.getRecChunk() > 0 ? " LIMIT ? OFFSET ?" : "";
		sql = sql + limit;
		PreparedStatement ps = conn.prepareStatement(sql);
		if (!limit.isEmpty()) {
			ps.setInt(1, control.getRecChunk());
			ps.setInt(2, control.getRecStart());
		}
		log.debug("DocumentDAO getDocumentList(): " + ps.toString());
		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				documents.add(getCompleteDocument(rs));
			}
		}
		return documents;
	}

	public int deleteIds(List<Integer> ids) throws SQLException {
		String sql = "DELETE FROM documents WHERE doc_id IN "
				+ SqlUtil.getPreparedStatementInClause(ids);
		PreparedStatement ps = conn.prepareStatement(sql);
		SqlUtil.setList(ps, ids);
		log.debug("DocumentDAO deleteIds(): " + ps.toString());
		return ps.executeUpdate();
	}

	/**
	 * Devuelve el número de registros totales de la tabla
	 * 
	 * @return variable int con el número de registros en la tabla
	 */
	public int getCountRows() throws SQLException {
		String sql = "SELECT COUNT(*) FROM documents";
		PreparedStatement ps = conn.prepareStatement(sql);
		log.debug("DocumentDAO getCountRows(): " + ps.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			return rs.getInt(1);
		}
		return 0;
	}

	public Document getCompleteDocument(ResultSet rs) throws SQLException {
		Document document = new Document();
		document.setId(rs.getInt("doc_id"));
		document.setDatetime(rs.getTimestamp("doc_date"));
		document.setUser(rs.getString("doc_user"));
		document.setUserReference(rs.getString("doc_user_reference"));
		document.setFilename(rs.getString("doc_filename"));
		document.setRepositoryReference(rs.getString("doc_repository_reference"));
		document.setScope(rs.getInt("doc_scope"));
		return document;
	}
}

