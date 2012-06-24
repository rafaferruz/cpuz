/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.service;

import com.cpuz.dummy.DAOFactoryDummy;
import java.util.ArrayList;
import com.cpuz.exceptions.UserException;
import com.cpuz.domain.User;
import com.cpuz.st2.beans.ControlParams;
import java.sql.SQLException;
import java.util.List;
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
public class UserServiceTest {

	private UserService instance;

	public UserServiceTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
		instance = createUserService();
	}

	private UserService createUserService() {
		UserService userService = new UserService();
		userService.setDAOFactory(new DAOFactoryDummy());
		return userService;
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of keyIdExists method, of class UserService.
	 */
	@Test(expected = UserException.class)
	public void testKeyIdExists_whenUserIdNull() throws SQLException, UserException {
		System.out.println("keyIdExists");
		Integer userId = null;
		boolean result = instance.keyIdExists(userId);
	}

	/**
	 * Test of keyIdExists method, of class UserService.
	 */
	@Test(expected = SQLException.class)
	public void testKeyIdExists_whenSQLException() throws SQLException, UserException {
		System.out.println("keyIdExists");
		Integer userId = -1;	// Provocamos SQLException
		boolean result = instance.keyIdExists(userId);
	}

	/**
	 * Test of keyIdExists method, of class UserService.
	 */
	@Test
	public void testKeyIdExists_whenIdNotFound() throws SQLException, UserException {
		System.out.println("keyIdExists");
		Integer userId = 0;	// Provocamos userId not found
		assertFalse("userId no debería ser encontrado", instance.keyIdExists(userId));
	}

	/**
	 * Test of keyIdExists method, of class UserService.
	 */
	@Test
	public void testKeyIdExists_whenIdIsFound() throws SQLException, UserException {
		System.out.println("keyIdExists");
		Integer userId = 1;	// Provocamos userId is found
		assertTrue("userId debería haber sido encontrado", instance.keyIdExists(userId));
	}

	/**
	 * Test of getUserList method, of class UserService.
	 */
	@Test(expected = SQLException.class)
	public void testGetUserList_whenSQLException() throws SQLException {
		System.out.println("getUserList");
		instance.getControl().setRecChunk(-1);
		instance.getUserList(instance.getControl());
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getUserList method, of class UserService.
	 */
	@Test
	public void testGetUserList_whenEmptyList() throws SQLException, UserException {
		System.out.println("getUserList");
		ControlParams control = new ControlParams();
		control.setRecCount(0);	// Provocamos que devuelva una lista vacía
		assertTrue("Debería devolver una lista vacía", instance.getUserList(control).isEmpty());
	}

	/**
	 * Test of getUserList method, of class UserService.
	 */
	@Test
	public void testGetUserList_whenNotEmptyList() throws SQLException, UserException {
		System.out.println("getUserList");
		ControlParams control = new ControlParams();
		control.setRecCount(2);	// Provocamos que devuelva una lista con 2 objetos
		assertEquals("Debería devolver una lista con 2 objetos", 2, instance.getUserList(control).size());
	}

	/**
	 * Test of getCountRows method, of class UserService.
	 */
	@Test
	public void testGetCountRows() throws SQLException, SQLException {
		System.out.println("getCountRows");
		assertEquals("Debería devolver el contador de registros igual a 1", 1, instance.getCountRows());
	}

	/**
	 * Test of keyIdExists method, of class UserService.
	 */
	@Test(expected = SQLException.class)
	public void testGetById_whenSQLException() throws SQLException, UserException {
		System.out.println("getById");
		int userId = -1;	// Provocamos SQLException
		instance.getById(userId);
	}

	/**
	 * Test of keyIdExists method, of class UserService.
	 */
	@Test
	public void testGetById_whenIdNotFound() throws SQLException, UserException {
		System.out.println("getById");
		int userId = 0;	// Provocamos userId not found
		assertNull("Debería devolver un objeto null", instance.getById(userId));
	}

	/**
	 * Test of keyIdExists method, of class UserService.
	 */
	@Test
	public void testGetById_whenIdIsFound() throws SQLException, UserException {
		System.out.println("getById");
		int userId = 1;	// Provocamos userId is found
		assertTrue("Debería devolver un objeto User", instance.getById(userId).getClass().getName().equals("com.cpuz.domain.User"));
	}

	/**
	 * Test of insertUser method, of class UserService.
	 */
	@Test(expected = UserException.class)
	public void testInsertUserUserException() throws Exception {
		System.out.println("insertUser");
		User user = null;
		instance.insertUser(user);
	}

	/**
	 * Test of insertUser method, of class UserService.
	 */
	@Test(expected = SQLException.class)
	public void testInsertUserSQLException() throws Exception {
		System.out.println("insertUser");
		User user = new User();
		user.setId(-1);
		user.setUser("user");
		user.setPassword("X");
		instance.insertUser(user);
	}

	/**
	 * Test of insertUser method, of class UserService.
	 */
	@Test
	public void testInsertUserFailInsert() throws Exception {
		System.out.println("insertUser");
		User user = new User();
		user.setId(0);
		user.setUser("user");
		user.setPassword("X");
		assertEquals("Insert no ha persistido ningún registro", 0, instance.insertUser(user));
	}

	/**
	 * Test of insertUser method, of class UserService.
	 */
	@Test
	public void testInsertUserOkInsert() throws Exception {
		System.out.println("insertUser");
		User user = new User();
		user.setId(1);
		user.setUser("user");
		user.setPassword("X");
		assertEquals("Insert ha persistido 1 registro", 1, instance.insertUser(user));
	}

	/**
	 * Test of updateUser method, of class UserService.
	 */
	@Test(expected = SQLException.class)
	public void testUpdateUserSQLException() throws Exception {
		System.out.println("updateUser");
		User user = new User();
		user.setId(-1);
		user.setUser("user");
		instance.updateUser(user);
	}

	/**
	 * Test of updateUser method, of class UserService.
	 */
	@Test(expected = UserException.class)
	public void testUpdateUserUserException() throws Exception {
		System.out.println("updateUser");
		User user = new User();
		instance.updateUser(user);
	}

	/**
	 * Test of updateUser method, of class UserService.
	 */
	@Test
	public void testUpdateUserFailUpdate() throws Exception {
		System.out.println("updateUser");
		User user = new User();
		user.setId(0);
		user.setUser("user");
		assertEquals("Debería devolver 0 filas actualizadas", 0, instance.updateUser(user));
	}

	/**
	 * Test of updateUser method, of class UserService.
	 */
	@Test
	public void testUpdateUserOkUpdate() throws Exception {
		System.out.println("updateUser");
		User user = new User();
		user.setId(1);
		user.setUser("user");
		assertEquals("Debería devolver 0 filas actualizadas", 1, instance.updateUser(user));
	}

	/**
	 * Test of deleteUser method, of class UserService.
	 */
	@Test(expected = UserException.class)
	public void testDeleteUser_whenUserException() throws SQLException, UserException {
		System.out.println("deleteUser");
		User user = new User();
		user.setId(null);	// Provocamos UserException
		instance.deleteUser(user);
	}

	/**
	 * Test of deleteUser method, of class UserService.
	 */
	@Test(expected = SQLException.class)
	public void testDeleteUser_whenSQLException() throws SQLException, UserException {
		System.out.println("deleteUser");
		User user = new User();
		user.setId(-1);	// Provocamos SQLException
		instance.deleteUser(user);
	}

	/**
	 * Test of deleteUser method, of class UserService.
	 */
	@Test
	public void testDeleteUser_FailDelete() throws SQLException, UserException {
		System.out.println("deleteUser");
		User user = new User();
		user.setId(0);	// Provocamos fallo en la eliminación
		assertEquals("Debería devolver 0 elementos eliminados", 0, instance.deleteUser(user));
	}

	/**
	 * Test of deleteUser method, of class UserService.
	 */
	@Test
	public void testDeleteUser_OkDelete() throws SQLException, UserException {
		System.out.println("deleteUser");
		User user = new User();
		user.setId(1);	// Provocamos eliminación correcta
		assertEquals("Debería devolver 1 fila eliminada", 1, instance.deleteUser(user));
	}

	/**
	 * Test of deleteUserIds method, of class UserService.
	 */
	@Test
	public void testDeleteUserIds_ListNull() throws SQLException {
		System.out.println("deleteUserIds");
		List<String> ids = null;
		assertEquals("Debería ser 0 filas deleted",0,instance.deleteUserIds(ids));
	}

	/**
	 * Test of deleteUserIds method, of class UserService.
	 */
	@Test(expected=SQLException.class)
	public void testDeleteUserIds_whenSQLException() throws SQLException {
		System.out.println("deleteUserIds");
		List<String> ids = new ArrayList<>();
		ids.add("");
		instance.deleteUserIds(ids);
	}

	/**
	 * Test of deleteUserIds method, of class UserService.
	 */
	@Test
	public void testDeleteUserIds_OkDeleting() throws SQLException {
		System.out.println("deleteUserIds");
		String id="One row";
		List<String> ids = new ArrayList<>();
		ids.add(id);
		assertEquals("Debería ser 1 fila deleted",1,instance.deleteUserIds(ids));
	}
}
