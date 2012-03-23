package com.cpuz.DAO.impl;

import com.cpuz.domain.User;
import com.cpuz.exceptions.UserException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;



/** UserDAO es una plantilla de interface que puede servir de base para la
 * la creación de interfaces más específicos destánados a las operaciones CRUD
 * sobre tablas de una base de datos.
 * 
 * @author RAFAEL FERRUZ
 */

public interface UserDAO {
    int createUser(User rec) throws UserException  ,SQLException;
    int deleteUser(User rec) throws UserException ,SQLException;
    int deleteUser(String sqlWhereClause) throws UserException ,SQLException;
    int updateUser(User rec) throws UserException ,SQLException;
    User readUser(String keyId) throws UserException ,SQLException, NamingException;
    List<User> readAllUsers() throws UserException ,SQLException, NamingException;
    List<User> readUsers(String sqlClause) throws UserException ,SQLException, NamingException;
    void setPropertiesUser(User rec, ResultSet rs) throws UserException;
    public boolean keyIdExists(int keyId) throws SQLException, NamingException;
}
