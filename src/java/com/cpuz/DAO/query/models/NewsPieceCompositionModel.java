/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.DAO.query.models;

import com.cpuz.DAO.query.entities.NewsPieceComposition;
import com.cpuz.tools.JDBCHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class NewsPieceCompositionModel {
    // JNDI name of the data source this class requires

    private static final String DATA_SOURCE_NAME = "CPUZ";
    private JDBCHelper jdbcHelper;
    private PreparedStatement selectStatement = null;
    private PreparedStatement insertStatement = null;

    /** Creates a new instance of NewsPieceCompositionModel
     *
     */
    public NewsPieceCompositionModel() {
        jdbcHelper = new JDBCHelper(DATA_SOURCE_NAME);
    }

    public List<NewsPieceComposition> getQuerySearchingWordsInJoinedTables(String words) {

        Connection connection = null;
        List<NewsPieceComposition> listNpc = new ArrayList<NewsPieceComposition>();
        listNpc.add(new NewsPieceComposition());
        listNpc.remove(0);

        String sqlSelect = "select * from newspieces left join newscomposition on nco_npi_id = npi_id"
                + " where nco_component_type IN ('InfoBlock','Document')";
        if (words != null && words.length() > 0) {
            String[] word = words.split(" ");
            if (word != null && word.length > 0) {
                sqlSelect = sqlSelect + " AND (";
                for (int i = 0; i < word.length; i++) {
                    sqlSelect = sqlSelect + " nco_header_alternate LIKE '%" + word[i] + "%'"
                            + " OR nco_body_abstract LIKE '%" + word[i] + "%' ";
                    if (i < word.length - 1) {
                        sqlSelect = sqlSelect + " OR ";
                    } else {
                        sqlSelect = sqlSelect + " ) ";

                    }
                }
            }
        }
        sqlSelect = sqlSelect + " ORDER BY npi_date DESC, npi_id DESC, nco_order";

        try {
            connection = jdbcHelper.getConnection();
            selectStatement = connection.prepareStatement(sqlSelect);
            ResultSet result = selectStatement.executeQuery();
            while (result.next()) {
                NewsPieceComposition npc = new NewsPieceComposition();
                npc.setNpi_id(result.getInt("npi_id"));
                npc.setNpi_date(result.getDate("npi_date"));
                npc.setNpi_description(result.getString("npi_description"));
                npc.setNco_bodyAbstract(result.getString("nco_body_abstract"));
                npc.setNco_headerAlt(result.getString("nco_header_alternate"));
                listNpc.add(npc);
            }
        } catch (NamingException ex) {
            System.err.println(ex.getMessage());
            return listNpc;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            try {
                jdbcHelper.cleanup(connection, selectStatement, null);
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }

        return listNpc;
    }
}

