package com.cpuz.DAO.impl;

import com.cpuz.domain.Contact;
import com.cpuz.exceptions.UserException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;



/** ContactDAO es una plantilla de interface que puede servir de base para la
 * la creación de interfaces más específicos destánados a las operaciones CRUD
 * sobre tablas de una base de datos.
 * 
 * @author RAFAEL FERRUZ
 */

public interface ContactDAO {
    int createContact(Contact rec) throws UserException  ,SQLException;
    int deleteContact(Contact rec) throws UserException ,SQLException;
    int deleteContact(String sqlWhereClause) throws UserException ,SQLException;
    int updateContact(Contact rec) throws UserException ,SQLException;
    Contact readContact(String keyId) throws UserException ,SQLException, NamingException;
    List<Contact> readContacts() throws UserException ,SQLException, NamingException;
    List<Contact> readContacts(String sqlClause) throws UserException ,SQLException, NamingException;
    void setPropertiesContact(Contact rec, ResultSet rs) throws UserException;
    public boolean keyIdExists(int keyId) throws SQLException, NamingException;
}
