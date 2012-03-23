package com.cpuz.DAO.impl;

import com.cpuz.domain.Contact;
import com.cpuz.exceptions.ContactException;
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
    int createContact(Contact rec) throws ContactException  ,SQLException;
    int deleteContact(Contact rec) throws ContactException ,SQLException;
    int deleteContact(String sqlWhereClause) throws ContactException ,SQLException;
    int updateContact(Contact rec) throws ContactException ,SQLException;
    Contact readContact(String keyId) throws ContactException ,SQLException, NamingException;
    List<Contact> readContacts() throws ContactException ,SQLException, NamingException;
    List<Contact> readContacts(String sqlClause) throws ContactException ,SQLException, NamingException;
    void setPropertiesContact(Contact rec, ResultSet rs) throws ContactException;
    public boolean keyIdExists(int keyId) throws SQLException, NamingException;
}
