package com.cpuz.DAO.impl;

import com.cpuz.domain.Bug;
import com.cpuz.exceptions.BugException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;



/** BugDAO es una plantilla de interface que puede servir de base para la
 * la creación de interfaces más específicos destánados a las operaciones CRUD
 * sobre tablas de una base de datos.
 * 
 * @author RAFAEL FERRUZ
 */

public interface BugDAO {
    int createBug(Bug rec) throws BugException  ,SQLException;
    int deleteBug(Bug rec) throws BugException ,SQLException;
    int deleteBug(String sqlWhereClause) throws BugException ,SQLException;
    int updateBug(Bug rec) throws BugException ,SQLException;
    Bug readBug(String keyId) throws BugException ,SQLException, NamingException;
    List<Bug> readBugs() throws BugException ,SQLException, NamingException;
    List<Bug> readBugs(String sqlClause) throws BugException ,SQLException, NamingException;
    void setPropertiesBug(Bug rec, ResultSet rs) throws BugException;
    public boolean keyIdExists(int keyId) throws SQLException, NamingException;
}
