package com.cpuz.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests de la clase Section.
 */
public class SectionTest {

	private Section instance;

	public SectionTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void instantiate() {
		instance = createInstance();
	}

	private Section createInstance() {
		return new Section();
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of getAuthorizedRoles method, of class Section.
	 */
	@Test
	public void testGetAuthorizedRoles() {
		System.out.println("getAuthorizedRoles");
		assertNull("section.authorizedRoles debería ser null", instance.getAuthorizedRoles());
	}

	/**
	 * Test of setAuthorizedRoles method, of class Section.
	 */
	@Test
	public void testSetAuthorizedRoles() {
		System.out.println("setAuthorizedRoles");
		String authorizedRoles = "abc";
		instance.setAuthorizedRoles(authorizedRoles);
		assertEquals("section.authorizedRoles debería ser abc", "abc", instance.getAuthorizedRoles());
	}

	/**
	 * Test of getId method, of class Section.
	 */
	@Test
	public void testGetId() {
		System.out.println("getId");
		assertEquals("section.id debería ser 0", 0, instance.getId());
	}

	/**
	 * Test of setId method, of class Section.
	 */
	@Test
	public void testSetId() {
		System.out.println("setId");
		int id = 1;
		instance.setId(id);
		assertEquals("section.id debería ser 1", 1, instance.getId());
	}

	/**
	 * Test of getName method, of class Section.
	 */
	@Test
	public void testGetName() {
		System.out.println("getName");
		assertNull("section.name debería ser null", instance.getName());
	}

	/**
	 * Test of setName method, of class Section.
	 */
	@Test
	public void testSetName() {
		System.out.println("setName");
		String name = "abc";
		instance.setName(name);
		assertEquals("section.name debería ser abc", "abc", instance.getName());
	}

	/**
	 * Test of getGroup method, of class Section.
	 */
	@Test
	public void testGetGroup() {
		System.out.println("getGroup");
		assertNull("section.group debería ser null", instance.getGroup());
	}

	/**
	 * Test of setGroup method, of class Section.
	 */
	@Test
	public void testSetGroup() {
		System.out.println("setGroup");
		String group = "abc";
		instance.setGroup(group);
		assertEquals("section.group debería ser abc", "abc", instance.getGroup());
	}

	/**
	 * Test of equals method, of class Section.
	 */
	@Test
	public void testEquals_whenNull() {
		System.out.println("equals when compare with null object");
		Object obj = null;
		assertFalse("comparation with null object debería ser false", instance.equals(obj));
	}

	/**
	 * Test of equals method, of class Section.
	 */
	@Test
	public void testEquals_whenOtherType() {
		System.out.println("equals");
		Object obj = new Object();
		assertFalse("comparation with object de diferente tipo debería ser false", instance.equals(obj));
	}

	/**
	 * Test of equals method, of class Section.
	 */
	@Test
	public void testEquals_whenSameId() {
		System.out.println("equals");
		Section obj = new Section();
		obj.setId(2);
		instance.setId(2);
		assertTrue("Deberían ser objetos iguales", instance.equals(obj));
	}

	/**
	 * Test of equals method, of class Section.
	 */
	@Test
	public void testEquals_whenDiferentId() {
		System.out.println("equals");
		Section obj = new Section();
		obj.setId(3);
		instance.setId(4);
		assertFalse("Deberían ser objetos no iguales", instance.equals(obj));
	}

	/**
	 * Test of hashCode method, of class Section.
	 */
	@Test
	public void testHashCode() {
		System.out.println("hashCode");
		Section otherInstance = new Section();
		instance.setId(5);
		otherInstance.setId(5);
		assertEquals("sections con id iguales deben dar igual hashCode", instance.hashCode(), otherInstance.hashCode());
	}
}
