package com.cpuz.DAO.impl;

import com.cpuz.domain.Document;
import com.cpuz.exceptions.DocumentException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;



/** DocumentDAO es una plantilla de interface que puede servir de base para la
 * la creación de interfaces más específicos destánados a las operaciones CRUD
 * sobre tablas de una base de datos.
 * 
 * @author RAFAEL FERRUZ
 */

public interface DocumentDAO {
    int createDocument(Document rec) throws DocumentException  ,SQLException;
    int deleteDocument(Document rec) throws DocumentException ,SQLException;
    int deleteDocument(String sqlWhereClause) throws DocumentException ,SQLException;
    int updateDocument(Document rec) throws DocumentException ,SQLException;
    Document readDocument(String keyId) throws DocumentException ,SQLException, NamingException;
    List<Document> readDocuments() throws DocumentException ,SQLException, NamingException;
    List<Document> readDocuments(String sqlWhereClause) throws DocumentException ,SQLException, NamingException;
    void setPropertiesDocument(Document rec, ResultSet rs) throws DocumentException;
    public boolean keyIdExists(int keyId) throws SQLException, NamingException;
}
