package com.cpuz.tools;


import java.sql.*;
import javax.sql.*;
import javax.naming.*;

public class JDBCHelper {

  //jndiName is the JNDI name of the data source to use to get a connection.
  private String jndiName = "CPUZ";

  /* numberConnections es una variable estática para mantener un contador de
   * de conexiones activas
   * */
  static private int numberConnections;

  // Creates new JDBCHelper
  public JDBCHelper(String jndiName) {
    this.jndiName = jndiName;
  }

  // @param jndiName JNDI name of the data source to use to 
  //  get a connection
  public synchronized Connection getConnection() throws NamingException, SQLException {
    Context initCtx = null;
    try {
      // Obtain the initial JNDI context
      initCtx = new InitialContext();

      // Perform JNDI lookup to obtain resource manager connection factory
      DataSource ds = (javax.sql.DataSource)
        initCtx.lookup("java:comp/env/jdbc/" + jndiName);

      // Se incrementa el número de conexiones
      numberConnections++;
        System.out.println("From JDBCHelper: Active DataSource connections: "+numberConnections);
      // Invoke factory to obtain a connection.
      return ds.getConnection();
    }
    finally {
      // Don't forget to close the naming context
      if (initCtx != null) {
        initCtx.close();
      }
    }
  }

  // Always cleans up, even if it encounters a SQL exception
  public synchronized void cleanup(Connection databaseConnection,
               Statement statement1,
               Statement statement2) throws SQLException {
    try {
      // Close the database connection and statement
      if (statement1 != null) {
        statement1.close();
      }
      if (statement2 != null) {
        statement2.close();
      }
    }
    finally {
      // Make sure we always try to close the connection, even
      // if something went wrong trying to close a statement
      if (databaseConnection != null) {
        databaseConnection.close();
      // Se incrementa el número de conexiones
      numberConnections--;
        System.out.println("From JDBCHelper: Active DataSource connections: "+numberConnections);
      }
    }
  }
}
