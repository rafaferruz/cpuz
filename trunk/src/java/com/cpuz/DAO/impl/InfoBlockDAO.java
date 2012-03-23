package com.cpuz.DAO.impl;

import com.cpuz.domain.InfoBlock;
import com.cpuz.exceptions.InfoBlockException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;



/** InfoBlockDAO es una plantilla de interface que puede servir de base para la
 * la creación de interfaces más específicos destánados a las operaciones CRUD
 * sobre tablas de una base de datos.
 * 
 * @author RAFAEL FERRUZ
 */

public interface InfoBlockDAO {
    int createInfoBlock(InfoBlock rec) throws InfoBlockException  ,SQLException;
    int deleteInfoBlock(InfoBlock rec) throws InfoBlockException ,SQLException;
    int deleteInfoBlock(String sqlWhereClause) throws InfoBlockException ,SQLException;
    int updateInfoBlock(InfoBlock rec) throws InfoBlockException ,SQLException;
    InfoBlock readInfoBlock(String keyId) throws InfoBlockException ,SQLException, NamingException;
    List<InfoBlock> readInfoBlocks() throws InfoBlockException ,SQLException, NamingException;
    List<InfoBlock> readInfoBlocks(String sqlClause) throws InfoBlockException ,SQLException, NamingException;
    void setPropertiesInfoBlock(InfoBlock rec, ResultSet rs) throws InfoBlockException;
    public boolean keyIdExists(int keyId) throws SQLException, NamingException;
}
