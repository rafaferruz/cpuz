/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.actions.admin;

import com.cpuz.dummy.DAOFactoryDummy;
import com.cpuz.domain.Role;
import com.cpuz.dummy.RoleActionDummy;
import com.cpuz.exceptions.UserException;
import java.sql.SQLException;
import com.cpuz.dummy.RoleDAODummy;
import com.cpuz.domain.UserType;
import com.cpuz.service.RolesService;
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
public class RoleActionTest {

	private RoleActionDummy instance;

	public RoleActionTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
		instance = createRoleAction();
	}

	private RoleActionDummy createRoleAction() {
		RoleActionDummy roleAction = new RoleActionDummy();
		roleAction.setDataService(new RolesService());
		roleAction.getDataService().setDAOFactory(new DAOFactoryDummy());
		return roleAction;
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of roleNew method, of class RoleAction.
	 */
	@Test
	public void testObjectNew() {
		System.out.println("roleNew");
		assertEquals("roleNew() siempre devuelve 'new'", "new", instance.objectNew());
		assertEquals("roleNew() control.recCount debe ser 1", 1, instance.getControl().getRecCount());
		assertEquals("roleNew() control.runAction debe ser 'New'", "New", instance.getControl().getRunAction());
		assertEquals("roleNew() requestAttributes[page] debe ser '/WEB-INF/views/roleEdit.jsp'", "/WEB-INF/views/roleEdit.jsp", instance.getRequestAttributes().get("page"));
	}

	/**
	 * Test of roleEdit method, of class RoleAction.
	 */
	@Test(expected = SQLException.class)
	public void testObjectEdit_WhenSQLException() throws Exception {
		System.out.println("roleEdit");
		instance.getControl().setId(-1); // Provocamos SQLException
		instance.objectEdit();
	}

	/**
	 * Test of roleEdit method, of class RoleAction.
	 */
	@Test
	public void testObjectEdit_WhenNotFound() throws Exception {
		System.out.println("roleEdit");
		instance.getControl().setId(0); // Provocamos Not Found Record
		assertEquals("Debería ser 'list' cuando no encuentra registro", "list", instance.objectEdit());
	}

	/**
	 * Test of roleEdit method, of class RoleAction.
	 */
	@Test
	public void testObjectEdit_WhenOk() throws Exception {
		System.out.println("roleEdit");
		instance.getControl().setId(1); // Provocamos Not Found Record
		assertEquals("Debería ser 'edit' cuando no encuentra registro", "edit", instance.objectEdit());
		assertEquals("roleEdit() control.runAction debe ser 'Edit'", "Edit", instance.getControl().getRunAction());
		assertEquals("roleEdit() requestAttributes[page] debe ser '/WEB-INF/views/roleEdit.jsp'", "/WEB-INF/views/roleEdit.jsp", instance.getRequestAttributes().get("page"));
	}

	@Test(expected = SQLException.class)
	public void testObjectSaveNew_WhenSQLException() throws SQLException, UserException {
		System.out.println("roleSaveNew");
		instance.getDataEdit().setRole("X"); // Evitamos UserException
		instance.getDataEdit().setDescription("X"); // Evitamos UserException
		instance.getDataEdit().setId(-1); // Provocamos SQLException
		instance.objectSaveNew();
	}

	@Test(expected = UserException.class)
	public void testObjectSaveNew_WhenUserException() throws SQLException, UserException {
		System.out.println("roleSaveNew");
		instance.getDataEdit().setRole("X"); // Evitamos UserException
		instance.getDataEdit().setDescription(""); // Provocamos UserException
		instance.objectSaveNew();
	}

	@Test
	public void testObjectSaveNew_WhenOk() throws SQLException, UserException {
		System.out.println("roleSaveNew");
		instance.getDataEdit().setId(1);
		instance.getDataEdit().setRole("X"); // Evitamos UserException
		instance.getDataEdit().setDescription("X"); // Provocamos UserException
		assertEquals("Debería ser igual a 'list'", "list", instance.objectSaveNew());
	}

	@Test(expected = SQLException.class)
	public void testObjectSaveEdit_WhenSQLException() throws SQLException, UserException {
		System.out.println("roleSaveEdit");
		instance.getDataEdit().setRole("X"); // Evitamos UserException
		instance.getDataEdit().setDescription("X"); // Evitamos UserException
		instance.getDataEdit().setId(-1); // Provocamos SQLException
		instance.objectSaveEdit();
	}

	@Test(expected = UserException.class)
	public void testObjectSaveEdit_WhenUserException() throws SQLException, UserException {
		System.out.println("roleSaveEdit");
		instance.getDataEdit().setRole("X"); // Evitamos UserException
		instance.getDataEdit().setDescription(""); // Provocamos UserException
		instance.objectSaveEdit();
	}

	@Test
	public void testObjectSaveEdit_WhenOk() throws SQLException, UserException {
		System.out.println("roleSaveEdit");
		instance.getDataEdit().setId(1);
		instance.getDataEdit().setRole("X"); // Evitamos UserException
		instance.getDataEdit().setDescription("X"); // Provocamos UserException
		assertEquals("Debería ser igual a 'list'", "list", instance.objectSaveEdit());
	}

	/**
	 * Test of roleDelete method, of class RoleAction.
	 */
	@Test
	public void testObjectDelete_WhenSelec1Null() throws SQLException {
		System.out.println("roleDelete");
		instance.setSelec1(null);
		assertEquals("Debería ser igual a 'list'", "list", instance.objectDelete());
		assertTrue("Debería ser igual a 'NoneSelectedRole'", instance.getActionErrors().contains("NoneSelectedRole"));

	}

	/**
	 * Test of roleDelete method, of class RoleAction.
	 */
	@Test(expected = SQLException.class)
	public void testObjectDelete_WhenSelec1Empty() throws SQLException {
		System.out.println("roleDelete");
		instance.setSelec1("");
		instance.objectDelete();
	}

	/**
	 * Test of roleDelete method, of class RoleAction.
	 */
	@Test
	public void testObjectDelete_WhenNoneDeleted() throws SQLException {
		System.out.println("roleDelete");
		instance.setSelec1("none");
		assertEquals("Debería ser igual a 'list'", "list", instance.objectDelete());
		assertTrue("Debería ser igual a 'NoneDeletedRole'", instance.getActionErrors().contains("NoneDeletedRole"));
	}

	/**
	 * Test of roleDelete method, of class RoleAction.
	 */
	@Test
	public void testObjectDelete_WhenOkDeleted() throws SQLException {
		System.out.println("roleDelete");
		instance.setSelec1("1,2,3");
		assertEquals("Debería ser igual a 'list'", "list", instance.objectDelete());
		assertTrue("Debería ser igual a 'SuccessDeletedRoles'", instance.getActionMessages().contains("SuccessDeletedRoles"));
	}

	/**
	 * Test of roleDelete method, of class RoleAction.
	 */
	@Test(expected = SQLException.class)
	public void testObjectList_WhenSQLException() throws SQLException {
		System.out.println("roleList");
		instance.getControl().setRecChunk(-1);
		instance.objectList();
	}

	/**
	 * Test of roleList method, of class RoleAction.
	 */
	@Test
	public void testObjectList_WhenOk() throws SQLException {
		System.out.println("roleList");
		instance.getControl().setRecCount(0);
		assertEquals("Debería ser igual a 'list'", "list", instance.objectList());
		assertNotNull("DataList debería ser distinto de null", instance.getDataList());
		assertEquals("Debería ser igual a 'List'", "List", instance.getControl().getRunAction());
		assertEquals("Debería ser igual a 'UserType.ADMIN'", UserType.ADMIN, instance.getControl().getUserType());
		assertEquals("roleList() requestAttributes[page] debe ser '/WEB-INF/views/roleList.jsp'", "/WEB-INF/views/roleList.jsp", instance.getRequestAttributes().get("page"));
	}

	/**
	 * Test of roleNavigation method, of class RoleAction.
	 */
	@Test(expected = SQLException.class)
	public void testObjectNavigation_WhenSQLException() throws SQLException {
		System.out.println("roleNavigation");
		instance.getControl().setRecChunk(-1);
		instance.objectNavigation();
	}

	/**
	 * Test of roleNavigation method, of class RoleAction.
	 */
	@Test
	public void testObjectNavigation_WhenOk() throws SQLException {
		System.out.println("roleNavigation");
		instance.getControl().setRecCount(0);
		assertEquals("Debería ser igual a 'list'", "list", instance.objectList());
	}

		/**
	 * Test of getDataEdit method, of class Role.
	 */
	@Test
	public void testGetDataEdit() {
		System.out.println("getDataEdit");
		Role result = instance.getDataEdit();
		assertNotNull("dataEdit NO debería ser null", result);
	}



}
