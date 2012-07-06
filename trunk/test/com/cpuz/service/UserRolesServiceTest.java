/*
 * Copyright 2012 Rafael Ferruz
 * 
 * This file is part of CPUZ.
 * 
 * CPUZ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * CPUZ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with CPUZ.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.cpuz.service;

import com.cpuz.domain.UserRole;
import com.cpuz.dummy.DAOFactoryDummy;
import java.util.ArrayList;
import com.cpuz.exceptions.UserException;
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
public class UserRolesServiceTest {

	private UserRolesService instance;

	public UserRolesServiceTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
		instance = createUserRoleService();
	}

	private UserRolesService createUserRoleService() {
		UserRolesService userRolesService = new UserRolesService();
		userRolesService.setDAOFactory(new DAOFactoryDummy());
		return userRolesService;
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of keyIdExists method, of class UserRolesService.
	 */
	@Test(expected = SQLException.class)
	public void testKeyIdExists_whenSQLException() throws SQLException, UserException {
		System.out.println("keyIdExists");
		Integer userRoleId = -1;	// Provocamos SQLException
		boolean result = instance.keyIdExists(userRoleId);
	}

	/**
	 * Test of keyIdExists method, of class UserRolesService.
	 */
	@Test
	public void testKeyIdExists_whenIdNotFound() throws SQLException, UserException {
		System.out.println("keyIdExists");
		Integer userRoleId = 0;	// Provocamos userRoleId not found
		assertFalse("userRoleId no debería ser encontrado", instance.keyIdExists(userRoleId));
	}

	/**
	 * Test of keyIdExists method, of class UserRolesService.
	 */
	@Test
	public void testKeyIdExists_whenIdIsFound() throws SQLException, UserException {
		System.out.println("keyIdExists");
		Integer userRoleId = 1;	// Provocamos userRoleId is found
		assertTrue("userRoleId debería haber sido encontrado", instance.keyIdExists(userRoleId));
	}

	/**
	 * Test of getUserRoleList method, of class UserRolesService.
	 */
	@Test(expected = SQLException.class)
	public void testGetUserRoleList_whenSQLException() throws SQLException, UserException {
		System.out.println("getUserRoleList");
		instance.getUserRoleList("SQLException");
	}

	/**
	 * Test of getUserRoleList method, of class UserRolesService.
	 */
	@Test(expected = UserException.class)
	public void testGetUserRoleList_whenUserException() throws SQLException, UserException {
		System.out.println("getUserRoleList");
		instance.getUserRoleList("");
	}

	/**
	 * Test of getUserRoleList method, of class UserRolesService.
	 */
	@Test
	public void testGetUserRoleList_whenEmptyList() throws SQLException, UserException {
		System.out.println("getUserRoleList");
		assertTrue("Debería devolver una lista vacía", instance.getUserRoleList("EmptyList").isEmpty());
	}

	/**
	 * Test of getUserRoleList method, of class UserRolesService.
	 */
	@Test
	public void testGetUserRoleList_whenNotEmptyList() throws SQLException, UserException {
		System.out.println("getUserRoleList");
		assertTrue("Debería devolver una lista con algún elemento", !instance.getUserRoleList("NoEmpty").isEmpty());
	}

	/**
	 * Test of getById method, of class UserRolesService.
	 */
	@Test(expected = SQLException.class)
	public void testGetById_whenSQLException() throws SQLException, UserException {
		System.out.println("getById");
		int userRoleId = -1;	// Provocamos SQLException
		instance.getById(userRoleId);
	}

	/**
	 * Test of getById method, of class UserRolesService.
	 */
	@Test
	public void testGetById_whenIdNotFound() throws SQLException, UserException {
		System.out.println("getById");
		int userRoleId = 0;	// Provocamos userRoleId not found
		assertNull("Debería devolver un objeto null", instance.getById(userRoleId));
	}

	/**
	 * Test of getById method, of class UserRolesService.
	 */
	@Test
	public void testGetById_whenIdIsFound() throws SQLException, UserException {
		System.out.println("getById");
		int userRoleId = 1;	// Provocamos userRoleId is found
		assertTrue("Debería devolver un objeto Role", instance.getById(userRoleId).getClass().getName().equals("com.cpuz.domain.UserRole"));
	}

	/**
	 * Test of getByUserAndRole(), of class UserRolesService.
	 */
	@Test(expected = SQLException.class)
	public void testGetByUserAndRole_whenSQLException() throws SQLException, UserException {
		System.out.println("getByUserAndRole");
		instance.setRoleName("SQLException");
		instance.setUserCode("SQLException");
		instance.getByUserAndRole();
	}

	/**
	 * Test of getByUserAndRole(), of class UserRolesService.
	 */
	public void testGetByUserAndRole_whenUserException() throws SQLException, UserException {
		System.out.println("getByUserAndRole");
		instance.setRoleName("");
		instance.setUserCode("");
		assertNull("Debería devolver un UserRole null", instance.getByUserAndRole());
	}

	/**
	 * Test of getByUserAndRole(), of class UserRolesService.
	 */
	@Test
	public void testGetByUserAndRole_whenNotFound() throws SQLException, UserException {
		System.out.println("getByUserAndRole");
		instance.setRoleName("notFound");
		instance.setUserCode("user1");
		assertNull("Debería devolver un UserRole null", instance.getByUserAndRole());
	}

	/**
	 * Test of getByUserAndRole(), of class UserRolesService.
	 */
	@Test
	public void testGetByUserAndRole_whenFound() throws SQLException, UserException {
		System.out.println("getByUserAndRole");
		instance.setRoleName("role1");
		instance.setUserCode("user1");
		assertNotNull("Debería devolver un userrole NOT null", instance.getByUserAndRole());
	}

	/**
	 * Test of insertUserRole method, of class UserUserRolesService.
	 */
	@Test(expected = UserException.class)
	public void testInsertUserRoleUserException() throws Exception {
		System.out.println("insertUserRole");
		UserRole userRole = null;
		instance.insertUserRole(userRole);
	}

	/**
	 * Test of insertUserRole method, of class UserUserRolesService.
	 */
	@Test(expected = SQLException.class)
	public void testInsertUserRoleSQLException() throws Exception {
		System.out.println("insertUserRole");
		UserRole userRole = new UserRole();
		userRole.setId(-1);
		userRole.setUser("user");
		userRole.setRole("role");
		instance.insertUserRole(userRole);
	}

	/**
	 * Test of insertUserRole method, of class UserUserRolesService.
	 */
	@Test
	public void testInsertUserRoleFailInsert() throws Exception {
		System.out.println("insertUserRole");
		UserRole userRole = new UserRole();
		userRole.setId(0);
		userRole.setUser("user");
		userRole.setRole("role");
		assertEquals("Insert no ha persistido ningún registro", 0, instance.insertUserRole(userRole));
	}

	/**
	 * Test of insertUserRole method, of class UserUserRolesService.
	 */
	@Test
	public void testInsertUserRoleOkInsert() throws Exception {
		System.out.println("insertUserRole");
		UserRole userRole = new UserRole();
		userRole.setId(1);
		userRole.setUser("user");
		userRole.setRole("role");
		assertEquals("Insert ha persistido 1 registro", 1, instance.insertUserRole(userRole));
	}

	/**
	 * Test of updateUserRole method, of class UserUserRolesService.
	 */
	@Test(expected = SQLException.class)
	public void testUpdateUserRoleSQLException() throws Exception {
		System.out.println("updateUserRole");
		UserRole userRole = new UserRole();
		userRole.setId(-1);
		userRole.setUser("user");
		userRole.setRole("role");
		instance.updateUserRole(userRole);
	}

	/**
	 * Test of updateUserRole method, of class UserUserRolesService.
	 */
	@Test(expected = UserException.class)
	public void testUpdateUserRoleUserException() throws Exception {
		System.out.println("updateUserRole");
		UserRole userRole = new UserRole();
		instance.updateUserRole(userRole);
	}

	/**
	 * Test of updateUserRole method, of class UserUserRolesService.
	 */
	@Test
	public void testUpdateUserRoleFailUpdate() throws Exception {
		System.out.println("updateUserRole");
		UserRole userRole = new UserRole();
		userRole.setId(0);
		userRole.setUser("user");
		userRole.setRole("role");
		assertEquals("Debería devolver 0 filas actualizadas", 0, instance.updateUserRole(userRole));
	}

	/**
	 * Test of updateUserRole method, of class UserUserRolesService.
	 */
	@Test
	public void testUpdateUserRoleOkUpdate() throws Exception {
		System.out.println("updateUserRole");
		UserRole userRole = new UserRole();
		userRole.setId(1);
		userRole.setUser("user");
		userRole.setRole("role");
		assertEquals("Debería devolver 0 filas actualizadas", 1, instance.updateUserRole(userRole));
	}

	/**
	 * Test of deleteUserRole method, of class UserUserRolesService.
	 */
	@Test(expected = SQLException.class)
	public void testDeleteUserRole_whenSQLException() throws SQLException, UserException {
		System.out.println("deleteUserRole");
		UserRole userRole = new UserRole();
		userRole.setId(-1);	// Provocamos SQLException
		instance.deleteUserRole(userRole);
	}

	/**
	 * Test of deleteUserRole method, of class UserUserRolesService.
	 */
	@Test
	public void testDeleteUserRole_FailDelete() throws SQLException, UserException {
		System.out.println("deleteUserRole");
		UserRole userRole = new UserRole();
		userRole.setId(0);	// Provocamos fallo en la eliminación
		assertEquals("Debería devolver 0 elementos eliminados", 0, instance.deleteUserRole(userRole));
	}

	/**
	 * Test of deleteUserRole method, of class UserUserRolesService.
	 */
	@Test
	public void testDeleteUserRole_OkDelete() throws SQLException, UserException {
		System.out.println("deleteUserRole");
		UserRole userRole = new UserRole();
		userRole.setId(1);	// Provocamos eliminación correcta
		assertEquals("Debería devolver 1 fila eliminada", 1, instance.deleteUserRole(userRole));
	}

	/**
	 * Test of deleteUserRoleIds method, of class UserUserRolesService.
	 */
	@Test
	public void testDeleteUserRoleIds_ListNull() throws SQLException {
		System.out.println("deleteUserRoleIds");
		List<Integer> ids = null;
		assertEquals("Debería ser 0 filas deleted",0,instance.deleteUserRoleIds(ids));
	}

	/**
	 * Test of deleteUserRoleIds method, of class UserUserRolesService.
	 */
	@Test(expected=SQLException.class)
	public void testDeleteUserRoleIds_whenSQLException() throws SQLException {
		System.out.println("deleteUserRoleIds");
		List<Integer> ids = new ArrayList<>();
		ids.add(-1);
		instance.deleteUserRoleIds(ids);
	}

	/**
	 * Test of deleteUserRoleIds method, of class UserUserRolesService.
	 */
	@Test
	public void testDeleteUserRoleIds_OkDeleting() throws SQLException {
		System.out.println("deleteUserRoleIds");
		List<Integer> ids = new ArrayList<>();
		ids.add(1);
		assertEquals("Debería ser 1 fila deleted",1,instance.deleteUserRoleIds(ids));
	}
}
