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
import com.cpuz.domain.Image;
import com.cpuz.st2.beans.ControlParams;
import com.cpuz.util.SqlUtil;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * Clase para la ejecución de operaciones CRUD sobre la tabla 'images'.
 */
public class ImageDAO implements InjectableDAO {

	transient private final Logger log = Logger.getLogger(this.getClass());
	private Connection conn;

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	/**
	 * Añade el Image a la tabla 
	 *
	 * @param rec		Objeto Image que se quiere insertar en la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha insertado con éxito.
	 * @throws SQLException 
	 */
	public synchronized int create(Image rec) throws SQLException {

		String sql = "INSERT INTO images "
				+ "(img_id, "
				+ "img_date, "
				+ "img_user, "
				+ "img_user_reference, "
				+ "img_filename, "
				+ "img_repository_reference, "
				+ "img_scope) "
				+ " VALUES (?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, rec.getId());
		ps.setDate(2, new java.sql.Date(rec.getDatetime().getTime()));
		ps.setString(3, rec.getUser());
		ps.setString(4, rec.getUserReference());
		ps.setString(5, rec.getFilename());
		ps.setString(6, rec.getRepositoryReference());
		ps.setInt(7, rec.getScope());
		log.debug("ImageDAO create(): " + ps.toString());
		return ps.executeUpdate();
	}

	/**
	 * Recupera un Image de la tabla 
	 *
	 * @param imageId		Id del objeto Image que se quiere recuperar de la tabla
	 * @return			Un objeto Image  con la información recuperada de la 
	 *					base de datos. Si no encuentra el id buscado, devuelve null.
	 * @throws SQLException 
	 */
	public Image read(int imageId) throws SQLException {
		Image image = null;
		String sql = "SELECT * FROM images WHERE img_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, imageId);
		log.debug("ImageDAO read(): " + ps.toString());
		ResultSet rs = ps.executeQuery(sql);
		if (rs.next()) {
			image = getCompleteImage(rs);
		}
		return image;
	}

	/**
	 * Actualiza un Image en la tabla 
	 *
	 * @param image		Objeto Image que se quiere actualizar en la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha insertado con éxito.
	 * @throws SQLException 
	 */
	public int update(Image image) throws SQLException {
		int rowCount = 0;
		String sql = "UPDATE images SET "
				+ "img_user_reference =  = ?,"
				+ "img_filename = ?,"
				+ "img_repository_reference = ?,"
				+ "img_scope = ? "
				+ " WHERE img_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, image.getUserReference());
		ps.setString(2, image.getFilename());
		ps.setString(3, image.getRepositoryReference());
		ps.setInt(4, image.getScope());
		log.debug("ImageDAO update(): " + ps.toString());
		rowCount = ps.executeUpdate();
		return rowCount;
	}

	/**
	 * Elimina un Image de la tabla 
	 *
	 * @param secId		Id del objeto Image que se quiere eliminar de la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha eliminado con éxito.
	 * @throws SQLException 
	 */
	public int delete(Integer imageId) throws SQLException {
		int rowCount = 0;
		String sql = "DELETE FROM images WHERE img_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, imageId);
		log.debug("ImageDAO delete(): " + ps.toString());
		rowCount = ps.executeUpdate();
		return rowCount;
	}

	public List<Image> getImageList(ControlParams control) throws SQLException {
		List<Image> images = new ArrayList<>();
		String sql = "SELECT * FROM images ORDER BY img_id ";
		String limit = control.getRecChunk() > 0 ? " LIMIT ? OFFSET ?" : "";
		sql = sql + limit;
		PreparedStatement ps = conn.prepareStatement(sql);
		if (!limit.isEmpty()) {
			ps.setInt(1, control.getRecChunk());
			ps.setInt(2, control.getRecStart());
		}
		log.debug("ImageDAO getImageList(): " + ps.toString());
		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				images.add(getCompleteImage(rs));
			}
		}
		return images;
	}

	public int deleteIds(List<Integer> ids) throws SQLException {
		String sql = "DELETE FROM images WHERE img_id IN "
				+ SqlUtil.getPreparedStatementInClause(ids);
		PreparedStatement ps = conn.prepareStatement(sql);
		SqlUtil.setList(ps, ids);
		log.debug("ImageDAO deleteIds(): " + ps.toString());
		return ps.executeUpdate();
	}

	/**
	 * Devuelve el número de registros totales de la tabla
	 * 
	 * @return variable int con el número de registros en la tabla
	 */
	public int getCountRows() throws SQLException {
		String sql = "SELECT COUNT(*) FROM images";
		PreparedStatement ps = conn.prepareStatement(sql);
		log.debug("ImageDAO getCountRows(): " + ps.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			return rs.getInt(1);
		}
		return 0;
	}

	public Image getCompleteImage(ResultSet rs) throws SQLException {
		Image image = new Image();
		image.setId(rs.getInt("img_id"));
		image.setDatetime(rs.getTimestamp("img_date"));
		image.setUser(rs.getString("img_user"));
		image.setUserReference(rs.getString("img_user_reference"));
		image.setFilename(rs.getString("img_filename"));
		image.setRepositoryReference(rs.getString("img_repository_reference"));
		image.setScope(rs.getInt("img_scope"));
		return image;
	}
}
