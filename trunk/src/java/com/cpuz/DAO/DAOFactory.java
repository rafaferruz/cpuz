package com.cpuz.DAO;

import com.cpuz.DAO.RoleDAO;
import com.cpuz.DAO.impl.InjectableDAO;
import com.cpuz.DAO.impl.NewsPieceDAOImpl;
import com.cpuz.DAO.impl.UserDAO;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 * Factoría para manejar los DAO de manera transaccional.
 * 
 * Ejemplo de uso no transaccional (sin reutilizar la conexión entre varias DAO):
 *
 *     new DAOFactory().getFiscalPeriodDAO().update(fiscalPeriod, userId);
 * 
 * Ejemplo de uso transaccional (reutilizando la misma conexión entre las DAO):
 * 
 *     DAOFactory df = new DAOFactory();
 *     df.startTransaction();
 *     try {
 *         df.getFiscalPeriodDAO().update(fiscalPeriod, userId);
 *         df.getUserDAO().save(user, userId);
 *         df.commit();
 *     } catch (Throwable ex) {
 *         df.rollback(ex);
 *     }
 */
public class DAOFactory {

	private Logger log = Logger.getLogger(this.getClass());
	Connection conn;

	/** Inicia una transacción. */
	public void startTransaction() {
		this.conn = DB.getConnection();
	}

	/** Termina una transacción haciendo commit. */
	public void commit() {
		try {
			conn.commit();
		} catch (SQLException ex) {
			try {
				if (conn != null) {
					log.error("Ejecutando rollback.");
					conn.rollback();
				}
			} catch (SQLException sex) {
				log.error("Error SQL: Imposible deshacer. " + sex.toString());
			}
			log.error("Error de acceso a datos en TransactionalDAOFactory");
			throw new RuntimeException(ex.getCause() != null ? ex.getCause() : ex);
		} finally {
			DB.putConnection(conn);
		}
	}

	/** Termina una transacción haciendo rollback. */
	public void rollback(Throwable ex) {
		try {
			if (conn != null) {
				log.error("Ejecutando rollback.");
				conn.rollback();
			}
		} catch (SQLException sex) {
			log.error("Error SQL: Imposible deshacer. " + sex.toString());
		} finally {
			DB.putConnection(conn);
		}
		throw new RuntimeException(ex.getCause() != null ? ex.getCause() : ex);
	}

	private Object getDAO(Class clazz) {
		InjectableDAO dao;
		try {
			Constructor constructor = clazz.getConstructor();
			dao = (InjectableDAO) constructor.newInstance();
		} catch (InstantiationException ex) {
			throw new RuntimeException(ex); // Esto no debería ocurrir nunca
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex); // Esto no debería ocurrir nunca
		} catch (IllegalArgumentException ex) {
			throw new RuntimeException(ex); // Esto no debería ocurrir nunca
		} catch (InvocationTargetException ex) {
			throw new RuntimeException(ex); // Esto no debería ocurrir nunca
		} catch (NoSuchMethodException ex) {
			throw new RuntimeException(ex); // Esto no debería ocurrir nunca
		} catch (SecurityException ex) {
			throw new RuntimeException(ex); // Esto no debería ocurrir nunca
		}
		if (conn != null) {
			// Si estamos en modo transaccional (reutilizamos la conexión entre todas las DAO)
			dao.setConnection(conn);
			return dao;
		} else {
			// Si estamos en modo no transaccional
			return DAOProxy.newInstance(dao);
		}
	}

	public UserDAO getUserDAO() {
		return (UserDAO) this.getDAO(UserDAO.class);
	}

	public RoleDAO getRoleDAO() {
		return (RoleDAO) this.getDAO(RoleDAO.class);
	}
	public NewsPieceDAOImpl getNewsPieceDAOImpl() {
		return (NewsPieceDAOImpl) this.getDAO(NewsPieceDAOImpl.class);
	}


	
}
