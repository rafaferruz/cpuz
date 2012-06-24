/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.actions.admin;

import com.cpuz.service.UserService;
import com.cpuz.dummy.DAOFactoryDummy;
import com.cpuz.domain.User;
import com.cpuz.dummy.UserActionDummy;
import com.cpuz.exceptions.UserException;
import java.sql.SQLException;
import com.cpuz.domain.UserType;
import com.cpuz.service.RolesService;
import com.cpuz.service.UserService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class UserActionTest {

	private UserActionDummy instance;

	public UserActionTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
		instance = createUserAction();
	}

	private UserActionDummy createUserAction() {
		UserActionDummy userAction = new UserActionDummy();
		userAction.setDataService(new UserService());
		userAction.getDataService().setDAOFactory(new DAOFactoryDummy());
		userAction.setRolesService(new RolesService());
		userAction.getRolesService().setDAOFactory(userAction.getDataService().getDAOFactory());
		return userAction;
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of userNew method, of class UserAction.
	 */
	@Test
	public void testObjectNew() throws SQLException {
		System.out.println("userNew");
		assertEquals("userNew() siempre devuelve 'new'", "new", instance.objectNew());
		assertEquals("userNew() control.recCount debe ser 1", 1, instance.getControl().getRecCount());
		assertEquals("userNew() control.runAction debe ser 'New'", "New", instance.getControl().getRunAction());
		assertEquals("userNew() requestAttributes[page] debe ser '/WEB-INF/views/userEdit.jsp'", "/WEB-INF/views/userEdit.jsp", instance.getRequestAttributes().get("page"));
	}

	/**
	 * Test of userEdit method, of class UserAction.
	 */
	@Test(expected = SQLException.class)
	public void testObjectEdit_WhenSQLException() throws Exception {
		System.out.println("userEdit");
		instance.getControl().setId(-1); // Provocamos SQLException
		instance.objectEdit();
	}

	/**
	 * Test of userEdit method, of class UserAction.
	 */
	@Test
	public void testObjectEdit_WhenNotFound() throws Exception {
		System.out.println("userEdit");
		instance.getControl().setId(0); // Provocamos Not Found Record
		assertEquals("Debería ser 'list' cuando no encuentra registro", "list", instance.objectEdit());
	}

	/**
	 * Test of userEdit method, of class UserAction.
	 */
	@Test
	public void testObjectEdit_WhenOk() throws Exception {
		System.out.println("userEdit");
		instance.getControl().setId(1); // Provocamos Not Found Record
		assertEquals("Debería ser 'edit' cuando no encuentra registro", "edit", instance.objectEdit());
		assertEquals("userEdit() control.runAction debe ser 'Edit'", "Edit", instance.getControl().getRunAction());
		assertEquals("userEdit() requestAttributes[page] debe ser '/WEB-INF/views/userEdit.jsp'", "/WEB-INF/views/userEdit.jsp", instance.getRequestAttributes().get("page"));
	}

	@Test(expected = SQLException.class)
	public void testObjectSaveNew_WhenSQLException() throws SQLException, UserException {
		System.out.println("userSaveNew");
		instance.getDataEdit().setUser("X"); // Evitamos UserException
		instance.getDataEdit().setPassword("X"); // Evitamos UserException
		instance.getDataEdit().setId(-1); // Provocamos SQLException
		instance.objectSaveNew();
	}

	@Test(expected = UserException.class)
	public void testObjectSaveNew_WhenUserException() throws SQLException, UserException {
		System.out.println("userSaveNew");
		instance.getDataEdit().setUser("X"); // Evitamos UserException
		instance.getDataEdit().setPassword(""); // Provocamos UserException
		instance.objectSaveNew();
	}

	@Test
	public void testObjectSaveNew_WhenOk() throws SQLException, UserException {
		System.out.println("userSaveNew");
		instance.getDataEdit().setId(1);
		instance.getDataEdit().setUser("X"); // Evitamos UserException
		instance.getDataEdit().setPassword("X"); // Provocamos UserException
		assertEquals("Debería ser igual a 'list'", "list", instance.objectSaveNew());
	}

	@Test(expected = SQLException.class)
	public void testObjectSaveEdit_WhenSQLException() throws SQLException, UserException {
		System.out.println("userSaveEdit");
		instance.getDataEdit().setUser("X"); // Evitamos UserException
		instance.getDataEdit().setPassword("X"); // Evitamos UserException
		instance.getDataEdit().setId(-1); // Provocamos SQLException
		instance.objectSaveEdit();
	}

	@Test(expected = UserException.class)
	public void testObjectSaveEdit_WhenUserException() throws SQLException, UserException {
		System.out.println("userSaveEdit");
		instance.getDataEdit().setUser("X"); // Evitamos UserException
		instance.getDataEdit().setPassword(""); // Provocamos UserException
		instance.objectSaveEdit();
	}

	@Test
	public void testObjectSaveEdit_WhenOk() throws SQLException, UserException {
		System.out.println("userSaveEdit");
		instance.getDataEdit().setId(1);
		instance.getDataEdit().setUser("X"); // Evitamos UserException
		instance.getDataEdit().setPassword("X"); // Provocamos UserException
		assertEquals("Debería ser igual a 'list'", "list", instance.objectSaveEdit());
	}

	/**
	 * Test of userDelete method, of class UserAction.
	 */
	@Test
	public void testObjectDelete_WhenSelec1Null() throws SQLException {
		System.out.println("userDelete");
		instance.setSelec1(null);
		assertEquals("Debería ser igual a 'list'", "list", instance.objectDelete());
		assertTrue("Debería ser igual a 'NoneSelectedUser'", instance.getActionErrors().contains("NoneSelectedUser"));

	}

	/**
	 * Test of userDelete method, of class UserAction.
	 */
	@Test(expected = SQLException.class)
	public void testObjectDelete_WhenSelec1Empty() throws SQLException {
		System.out.println("userDelete");
		instance.setSelec1("");
		instance.objectDelete();
	}

	/**
	 * Test of userDelete method, of class UserAction.
	 */
	@Test
	public void testObjectDelete_WhenNoneDeleted() throws SQLException {
		System.out.println("userDelete");
		instance.setSelec1("none");
		assertEquals("Debería ser igual a 'list'", "list", instance.objectDelete());
		assertTrue("Debería ser igual a 'NoneDeletedUser'", instance.getActionErrors().contains("NoneDeletedUser"));
	}

	/**
	 * Test of userDelete method, of class UserAction.
	 */
	@Test
	public void testObjectDelete_WhenOkDeleted() throws SQLException {
		System.out.println("userDelete");
		instance.setSelec1("1,2,3");
		assertEquals("Debería ser igual a 'list'", "list", instance.objectDelete());
		assertTrue("Debería ser igual a 'SuccessDeletedUsers'", instance.getActionMessages().contains("SuccessDeletedUsers"));
	}

	/**
	 * Test of userDelete method, of class UserAction.
	 */
	@Test(expected = SQLException.class)
	public void testObjectList_WhenSQLException() throws SQLException {
		System.out.println("userList");
		instance.getControl().setRecChunk(-1);
		instance.objectList();
	}

	/**
	 * Test of userList method, of class UserAction.
	 */
	@Test
	public void testObjectList_WhenOk() throws SQLException {
		System.out.println("userList");
		instance.getControl().setRecCount(0);
		assertEquals("Debería ser igual a 'list'", "list", instance.objectList());
		assertNotNull("DataList debería ser distinto de null", instance.getDataList());
		assertEquals("Debería ser igual a 'List'", "List", instance.getControl().getRunAction());
		assertEquals("Debería ser igual a 'UserType.ADMIN'", UserType.ADMIN, instance.getControl().getUserType());
		assertEquals("userList() requestAttributes[page] debe ser '/WEB-INF/views/userList.jsp'", "/WEB-INF/views/userList.jsp", instance.getRequestAttributes().get("page"));
	}

	/**
	 * Test of userNavigation method, of class UserAction.
	 */
	@Test(expected = SQLException.class)
	public void testObjectNavigation_WhenSQLException() throws SQLException {
		System.out.println("userNavigation");
		instance.getControl().setRecChunk(-1);
		instance.objectNavigation();
	}

	/**
	 * Test of userNavigation method, of class UserAction.
	 */
	@Test
	public void testObjectNavigation_WhenOk() throws SQLException {
		System.out.println("userNavigation");
		instance.getControl().setRecCount(0);
		assertEquals("Debería ser igual a 'list'", "list", instance.objectList());
	}

		/**
	 * Test of getDataEdit method, of class User.
	 */
	@Test
	public void testGetDataEdit() {
		System.out.println("getDataEdit");
		User result = instance.getDataEdit();
		assertNotNull("dataEdit NO debería ser null", result);
	}



}
