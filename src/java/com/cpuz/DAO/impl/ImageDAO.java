package com.cpuz.DAO.impl;

import com.cpuz.domain.Image;
import com.cpuz.exceptions.ImageException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;



/** ImageDAO es una plantilla de interface que puede servir de base para la
 * la creación de interfaces más específicos destánados a las operaciones CRUD
 * sobre tablas de una base de datos.
 * 
 * @author RAFAEL FERRUZ
 */

public interface ImageDAO {
    int createImage(Image rec) throws ImageException  ,SQLException;
    int deleteImage(Image rec) throws ImageException ,SQLException;
    int deleteImage(String sqlWhereClause) throws ImageException ,SQLException;
    int updateImage(Image rec) throws ImageException ,SQLException;
    Image readImage(String keyId) throws ImageException ,SQLException, NamingException;
    List<Image> readImages() throws ImageException ,SQLException, NamingException;
    List<Image> readImages(String sqlWhereClause) throws ImageException ,SQLException, NamingException;
    void setPropertiesImage(Image rec, ResultSet rs) throws ImageException;
    public boolean keyIdExists(int keyId) throws SQLException, NamingException;
}
