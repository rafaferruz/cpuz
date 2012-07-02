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

import com.cpuz.dummy.DAOFactoryDummy;
import java.util.ArrayList;
import com.cpuz.exceptions.UserException;
import com.cpuz.domain.Role;
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
public class RolesServiceTest {

	private RolesService instance;

	public RolesServiceTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
		instance = createRoleService();
	}

	private RolesService createRoleService() {
		RolesService rolesService = new RolesService();
		rolesService.setDAOFactory(new DAOFactoryDummy());
		return rolesService;
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of keyIdExists method, of class RolesService.
	 */
	@Test(expected = UserException.class)
	public void testKeyIdExists_whenRolIdNull() throws SQLException, UserException {
		System.out.println("keyIdExists");
		Integer rolId = null;
		boolean result = instance.keyIdExists(rolId);
	}

	/**
	 * Test of keyIdExists method, of class RolesService.
	 */
	@Test(expected = SQLException.class)
	public void testKeyIdExists_whenSQLException() throws SQLException, UserException {
		System.out.println("keyIdExists");
		Integer rolId = -1;	// Provocamos SQLException
		boolean result = instance.keyIdExists(rolId);
	}

	/**
	 * Test of keyIdExists method, of class RolesService.
	 */
	@Test
	public void testKeyIdExists_whenIdNotFound() throws SQLException, UserException {
		System.out.println("keyIdExists");
		Integer rolId = 0;	// Provocamos rolId not found
		assertFalse("rolId no debería ser encontrado", instance.keyIdExists(rolId));
	}

	/**
	 * Test of keyIdExists method, of class RolesService.
	 */
	@Test
	public void testKeyIdExists_whenIdIsFound() throws SQLException, UserException {
		System.out.println("keyIdExists");
		Integer rolId = 1;	// Provocamos rolId is found
		assertTrue("rolId debería haber sido encontrado", instance.keyIdExists(rolId));
	}

	/**
	 * Test of getRoleList method, of class RolesService.
	 */
	@Test(expected = SQLException.class)
	public void testGetRoleList_whenSQLException() throws SQLException {
		System.out.println("getRoleList");
		instance.getControl().setRecChunk(-1);
		instance.getRoleList(instance.getControl());
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getRoleList method, of class RolesService.
	 */
	@Test
	public void testGetRoleList_whenEmptyList() throws SQLException, UserException {
		System.out.println("getRoleList");
		ControlParams control = new ControlParams();
		control.setRecCount(0);	// Provocamos que devuelva una lista vacía
		assertTrue("Debería devolver una lista vacía", instance.getRoleList(control).isEmpty());
	}

	/**
	 * Test of getRoleList method, of class RolesService.
	 */
	@Test
	public void testGetRoleList_whenNotEmptyList() throws SQLException, UserException {
		System.out.println("getRoleList");
		ControlParams control = new ControlParams();
		control.setRecCount(2);	// Provocamos que devuelva una lista con 2 objetos
		assertEquals("Debería devolver una lista con 2 objetos", 2, instance.getRoleList(control).size());
	}

	/**
	 * Test of getNewsRecords method, of class RolesService.
	 */
	@Test
	public void testGetNewsRecords() throws Exception {
		System.out.println("getNewsRecords");
		ControlParams control = new ControlParams();
		instance.getControl().setRecCount(3);	// Provocamos que devuelva una lista con 3 objetos
		assertEquals("Debería devolver una lista con 3 objetos", 3, instance.getNewsRecords().size());
	}

	/**
	 * Test of getCountRows method, of class RolesService.
	 */
	@Test
	public void testGetCountRows() throws SQLException, SQLException {
		System.out.println("getCountRows");
		assertEquals("Debería devolver el contador de registros igual a 1", 1, instance.getCountRows());
	}

	/**
	 * Test of keyIdExists method, of class RolesService.
	 */
	@Test(expected = SQLException.class)
	public void testGetById_whenSQLException() throws SQLException, UserException {
		System.out.println("getById");
		int rolId = -1;	// Provocamos SQLException
		instance.getById(rolId);
	}

	/**
	 * Test of keyIdExists method, of class RolesService.
	 */
	@Test
	public void testGetById_whenIdNotFound() throws SQLException, UserException {
		System.out.println("getById");
		int rolId = 0;	// Provocamos rolId not found
		assertNull("Debería devolver un objeto null", instance.getById(rolId));
	}

	/**
	 * Test of keyIdExists method, of class RolesService.
	 */
	@Test
	public void testGetById_whenIdIsFound() throws SQLException, UserException {
		System.out.println("getById");
		int rolId = 1;	// Provocamos rolId is found
		assertTrue("Debería devolver un objeto Role", instance.getById(rolId).getClass().getName().equals("com.cpuz.domain.Role"));
	}

	/**
	 * Test of insertRole method, of class RolesService.
	 */
	@Test(expected = UserException.class)
	public void testInsertRoleUserException() throws Exception {
		System.out.println("insertRole");
		Role role = null;
		instance.insertRole(role);
	}

	/**
	 * Test of insertRole method, of class RolesService.
	 */
	@Test(expected = SQLException.class)
	public void testInsertRoleSQLException() throws Exception {
		System.out.println("insertRole");
		Role role = new Role();
		role.setId(-1);
		role.setRole("role");
		role.setDescription("description");
		instance.insertRole(role);
	}

	/**
	 * Test of insertRole method, of class RolesService.
	 */
	@Test
	public void testInsertRoleFailInsert() throws Exception {
		System.out.println("insertRole");
		Role role = new Role();
		role.setId(0);
		role.setRole("role");
		role.setDescription("description");
		assertEquals("Insert no ha persistido ningún registro", 0, instance.insertRole(role));
	}

	/**
	 * Test of insertRole method, of class RolesService.
	 */
	@Test
	public void testInsertRoleOkInsert() throws Exception {
		System.out.println("insertRole");
		Role role = new Role();
		role.setId(1);
		role.setRole("role");
		role.setDescription("description");
		assertEquals("Insert ha persistido 1 registro", 1, instance.insertRole(role));
	}

	/**
	 * Test of updateRole method, of class RolesService.
	 */
	@Test(expected = SQLException.class)
	public void testUpdateRoleSQLException() throws Exception {
		System.out.println("updateRole");
		Role role = new Role();
		role.setId(-1);
		role.setRole("role");
		role.setDescription("description");
		instance.updateRole(role);
	}

	/**
	 * Test of updateRole method, of class RolesService.
	 */
	@Test(expected = UserException.class)
	public void testUpdateRoleUserException() throws Exception {
		System.out.println("updateRole");
		Role role = new Role();
		instance.updateRole(role);
	}

	/**
	 * Test of updateRole method, of class RolesService.
	 */
	@Test
	public void testUpdateRoleFailUpdate() throws Exception {
		System.out.println("updateRole");
		Role role = new Role();
		role.setId(0);
		role.setRole("role");
		role.setDescription("description");
		assertEquals("Debería devolver 0 filas actualizadas", 0, instance.updateRole(role));
	}

	/**
	 * Test of updateRole method, of class RolesService.
	 */
	@Test
	public void testUpdateRoleOkUpdate() throws Exception {
		System.out.println("updateRole");
		Role role = new Role();
		role.setId(1);
		role.setRole("role");
		role.setDescription("description");
		assertEquals("Debería devolver 0 filas actualizadas", 1, instance.updateRole(role));
	}

	/**
	 * Test of deleteRole method, of class RolesService.
	 */
	@Test(expected = UserException.class)
	public void testDeleteRole_whenUserException() throws SQLException, UserException {
		System.out.println("deleteRole");
		Role role = new Role();
		role.setId(null);	// Provocamos UserException
		instance.deleteRole(role);
	}

	/**
	 * Test of deleteRole method, of class RolesService.
	 */
	@Test(expected = SQLException.class)
	public void testDeleteRole_whenSQLException() throws SQLException, UserException {
		System.out.println("deleteRole");
		Role role = new Role();
		role.setId(-1);	// Provocamos SQLException
		instance.deleteRole(role);
	}

	/**
	 * Test of deleteRole method, of class RolesService.
	 */
	@Test
	public void testDeleteRole_FailDelete() throws SQLException, UserException {
		System.out.println("deleteRole");
		Role role = new Role();
		role.setId(0);	// Provocamos fallo en la eliminación
		assertEquals("Debería devolver 0 elementos eliminados", 0, instance.deleteRole(role));
	}

	/**
	 * Test of deleteRole method, of class RolesService.
	 */
	@Test
	public void testDeleteRole_OkDelete() throws SQLException, UserException {
		System.out.println("deleteRole");
		Role role = new Role();
		role.setId(1);	// Provocamos eliminación correcta
		assertEquals("Debería devolver 1 fila eliminada", 1, instance.deleteRole(role));
	}

	/**
	 * Test of deleteRoleIds method, of class RolesService.
	 */
	@Test
	public void testDeleteRoleIds_ListNull() throws SQLException {
		System.out.println("deleteRoleIds");
		List<String> ids = null;
		assertEquals("Debería ser 0 filas deleted",0,instance.deleteRoleIds(ids));
	}

	/**
	 * Test of deleteRoleIds method, of class RolesService.
	 */
	@Test(expected=SQLException.class)
	public void testDeleteRoleIds_whenSQLException() throws SQLException {
		System.out.println("deleteRoleIds");
		List<String> ids = new ArrayList<>();
		ids.add("");
		instance.deleteRoleIds(ids);
	}

	/**
	 * Test of deleteRoleIds method, of class RolesService.
	 */
	@Test
	public void testDeleteRoleIds_OkDeleting() throws SQLException {
		System.out.println("deleteRoleIds");
		String id="One row";
		List<String> ids = new ArrayList<>();
		ids.add(id);
		assertEquals("Debería ser 1 fila deleted",1,instance.deleteRoleIds(ids));
	}
}
