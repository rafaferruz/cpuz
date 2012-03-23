/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cpuz.DAO.impl;

import com.cpuz.domain.Section;
import com.cpuz.exceptions.SectionException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author RAFAEL FERRUZ
 */
public interface SectionDAO {
    int createSection(Section rec) throws SectionException  ,SQLException;
    int deleteSection(Section rec) throws SectionException ,SQLException;
    int deleteSection(String sqlWhereClause) throws SectionException ,SQLException;
    int updateSection(Section rec) throws SectionException ,SQLException;
    Section readSection(String keyId) throws SectionException ,SQLException, NamingException;
    List<Section> readSections() throws SectionException ,SQLException, NamingException;
    List<Section> readSections(String sqlClause) throws SectionException ,SQLException, NamingException;
    void setPropertiesSection(Section rec, ResultSet rs) throws SectionException;
    public boolean keyIdExists(String keyId) throws SQLException, NamingException;
}
