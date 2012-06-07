package com.cpuz.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests de la clase Role.
 */
public class RoleTest {

	private Role instance;

	public RoleTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void instantiate() {
		instance = createRole();
	}

	private Role createRole() {
		Role role = new Role();
		return role;
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of getDescription method, of class Role.
	 */
	@Test
	public void testGetDescription() {
		System.out.println("getDescription");
		String result = instance.getDescription();
		assertEquals("role.description debería ser null",null, result);
	}

	/**
	 * Test of setDescription method, of class Role.
	 */
	@Test
	public void testSetDescription() {
		System.out.println("setDescription");
		String description = "abc";
		instance.setDescription(description);
		assertEquals("role.description debería ser abc","abc", instance.getDescription());
	}

	/**
	 * Test of getId method, of class Role.
	 */
	@Test
	public void testGetId() {
		System.out.println("getId");
		Integer expResult = null;
		Integer result = instance.getId();
		assertEquals("role.id debería ser null",expResult, result);
	}

	/**
	 * Test of setId method, of class Role.
	 */
	@Test
	public void testSetId() {
		System.out.println("setId");
		Integer id = 1;
		instance.setId(id);
		assertEquals("role.id debería ser 1","1", instance.getId().toString());
	}

	/**
	 * Test of getRole method, of class Role.
	 */
	@Test
	public void testGetRole() {
		System.out.println("getRole");
		String result = instance.getRole();
		assertNull("role.role debería ser null",result);
	}

	/**
	 * Test of setRole method, of class Role.
	 */
	@Test
	public void testSetRole() {
		System.out.println("setRole");
		String role = "abc";
		instance.setRole(role);
		assertEquals("role.role debería ser abc","abc", instance.getRole());
	}

}
