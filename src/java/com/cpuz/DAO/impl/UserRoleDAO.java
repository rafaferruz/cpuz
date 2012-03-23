/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cpuz.DAO.impl;

import com.cpuz.domain.UserRole;
import com.cpuz.exceptions.UserRoleException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author RAFAEL FERRUZ
 */
public interface UserRoleDAO {
    int createUserRole(UserRole rec) throws UserRoleException  ,SQLException;
    int deleteUserRole(UserRole rec) throws UserRoleException ,SQLException;
    int deleteUserRole(String sqlWhereClause) throws UserRoleException ,SQLException;
    int updateUserRole(UserRole rec) throws UserRoleException ,SQLException;
    UserRole readUserRole(String keyId) throws UserRoleException ,SQLException, NamingException;
    List<UserRole> readAllUserRoles() throws UserRoleException ,SQLException, NamingException;
    List<UserRole> readUserRoles(String sqlClause) throws UserRoleException ,SQLException, NamingException;
    void setPropertiesUserRole(UserRole rec, ResultSet rs) throws UserRoleException;
    public boolean keyIdExists(int keyId) throws SQLException, NamingException;
}
