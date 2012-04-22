/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.model;

import com.cpuz.service.RolesModel;
import java.util.ArrayList;
import java.sql.SQLException;
import com.cpuz.domain.Role;
import com.cpuz.domain.UserType;
import com.cpuz.st2.beans.ControlParams;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ESTHER
 */
public class RolesModelTest extends RolesModel {

	Integer rows;

	public RolesModelTest() {
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public List<Role> getRoleList(ControlParams control) throws SQLException {

		List<Role> roles = new ArrayList<>();
		if (rows == null || rows == -1) {
			throw new SQLException("Lanzo excepción de test.");
		}
		for (int i = 0; i < rows; i++) {
			roles.add(new Role());
		}
		return roles;
	}

	/**
	 * 
	 * @return	null si se ha pasado un 0 a la variable rows
	 *			un objeto Role si se ha pasado un entero válido distinto de 0
	 * @throws SQLException Si se ha pasado un null a rows
	 */
	public Role getById(int rolId) throws SQLException {
		Role role = new Role();
		if (rows == null) {
			throw new SQLException("Lanzo excepción de test.");
		} else if (rows == 0) {
			return null;
		}
		return role;
	}

	public int insertRole(Role role) throws SQLException {
		if (rows == null) {
			throw new SQLException("Lanzo excepción de test.");
		} else {
			return rows;
		}
	}

	public int updateRole(Role role) throws SQLException {
		if (rows == null) {
			throw new SQLException("Lanzo excepción de test.");
		} else {
			return rows;
		}
	}

	public boolean keyIdExists(Integer rolId) {
		if (rows == 0) {
			return false;
		}
		return true;
	}

	public int deleteRoleIds(List<String> ids) throws SQLException {
		if (rows == null) {
			throw new SQLException("Lanzo excepción de test.");
		} else {
			return rows;
		}
	}

	public int getCountRows() {
		return rows;
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of keyIdExists method, of class RolesModel.
	 */
	@Test
	public void testKeyIdExists() throws Exception {
		System.out.println("keyIdExists");
		Integer rolId = null;
		RolesModel instance = new RolesModel();
		boolean expResult = false;
		boolean result = instance.keyIdExists(rolId);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getNewsRecords method, of class RolesModel.
	 */
	@Test
	public void testGetNewsRecords_0args() throws Exception {
		System.out.println("getNewsRecords");
		RolesModel instance = new RolesModel();
		List expResult = null;
		List result = instance.getNewsRecords();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getNewsRecords method, of class RolesModel.
	 */
	@Test
	public void testGetNewsRecords_UserType() throws Exception {
		System.out.println("getNewsRecords");
		UserType userType = null;
		RolesModel instance = new RolesModel();
		List expResult = null;
		List result = instance.getNewsRecords(userType);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getRoleList method, of class RolesModel.
	 */
	@Test
	public void testGetRoleList() throws Exception {
		System.out.println("getRoleList");
		ControlParams control = null;
		RolesModel instance = new RolesModel();
		List expResult = null;
		List result = instance.getRoleList(control);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getCountRows method, of class RolesModel.
	 */
	@Test
	public void testGetCountRows() throws SQLException {
		System.out.println("getCountRows");
		RolesModel instance = new RolesModel();
		int expResult = 0;
		int result = instance.getCountRows();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getById method, of class RolesModel.
	 */
	@Test
	public void testGetById() throws Exception {
		System.out.println("getById");
		int rolId = 0;
		RolesModel instance = new RolesModel();
		Role expResult = null;
		Role result = instance.getById(rolId);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of insertRole method, of class RolesModel.
	 */
	@Test
	public void testInsertRole() throws Exception {
		System.out.println("insertRole");
		Role role = null;
		RolesModel instance = new RolesModel();
		int expResult = 0;
		int result = instance.insertRole(role);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of updateRole method, of class RolesModel.
	 */
	@Test
	public void testUpdateRole() throws Exception {
		System.out.println("updateRole");
		Role role = null;
		RolesModel instance = new RolesModel();
		int expResult = 0;
		int result = instance.updateRole(role);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of deleteRole method, of class RolesModel.
	 */
	@Test
	public void testDeleteRole() throws Exception {
		System.out.println("deleteRole");
		Role role = null;
		RolesModel instance = new RolesModel();
		int expResult = 0;
		int result = instance.deleteRole(role);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of deleteRoleIds method, of class RolesModel.
	 */
	@Test
	public void testDeleteRoleIds() throws Exception {
		System.out.println("deleteRoleIds");
		List<String> ids = null;
		RolesModel instance = new RolesModel();
		int expResult = 0;
		int result = instance.deleteRoleIds(ids);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}
}
