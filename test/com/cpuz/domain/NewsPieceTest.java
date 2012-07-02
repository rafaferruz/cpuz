package com.cpuz.domain;

import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests de la clase NewsPiece.
 */
public class NewsPieceTest {

	public NewsPieceTest() {
	}
	private NewsPiece instance;

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void instantiate() {
		instance = createObject();
	}

	private NewsPiece createObject() {
		NewsPiece newsPiece = new NewsPiece();
		return newsPiece;
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of getAccess method, of class NewsPiece.
	 */
	@Test
	public void testGetAccess() {
		System.out.println("getAccess");
		int expResult = 0;
		int result = instance.getAccess();
		assertEquals("newsPiece.id debería ser 0", 0, result);
	}

	/**
	 * Test of setAccess method, of class NewsPiece.
	 */
	@Test
	public void testSetAccess() {
		System.out.println("setAccess");
		int expResult = 1;
		instance.setAccess(expResult);
		int result = instance.getAccess();
		assertEquals("newsPiece.id debería ser 1", 1, result);
	}

	/**
	 * Test of getDatetime method, of class NewsPiece.
	 */
	@Test
	public void testGetDatetime() {
		System.out.println("getDatetime");
		assertEquals("newsPiece.dateTime debería ser null", null, instance.getDatetime());
	}

	/**
	 * Test of setDatetime method, of class NewsPiece.
	 */
	@Test
	public void testSetDatetime() {
		System.out.println("setDatetime");
		Date datetime = new Date(0);
		instance.setDatetime(datetime);
		assertEquals("newsPiece.dateTime debería ser 01/01/1970", new Date(0), instance.getDatetime());
	}

	/**
	 * Test of getDescription method, of class NewsPiece.
	 */
	@Test
	public void testGetDescription() {
		System.out.println("getDescription");
		assertEquals("newsPiece.description debería ser null", null, instance.getDescription());
	}

	/**
	 * Test of setDescription method, of class NewsPiece.
	 */
	@Test
	public void testSetDescription() {
		System.out.println("setDescription");
		instance.setDescription("description");
		assertEquals("newsPiece.description debería ser 'description'", "description", instance.getDescription());
	}

	/**
	 * Test of getId method, of class NewsPiece.
	 */
	@Test
	public void testGetId() {
		System.out.println("getId");
		assertEquals("role.id debería ser 0", 0,  instance.getId());
	}

	/**
	 * Test of setId method, of class Role.
	 */
	@Test
	public void testSetId() {
		System.out.println("setId");
		int id = 1;
		instance.setId(id);
		assertEquals("role.id debería ser 1", 1, instance.getId());
	}

	/**
	 * Test of isRegistered method, of class NewsPiece.
	 */
	@Test
	public void testIsRegistered() {
		System.out.println("isRegistered");
		assertFalse("newsPiece.registered debería ser false", instance.isRegistered());
	}

	/**
	 * Test of setRegistered method, of class NewsPiece.
	 */
	@Test
	public void testSetRegistered() {
		System.out.println("setRegistered");
		boolean registered = true;
		instance.setRegistered(registered);
		assertTrue("newsPiece.registered debería ser true", instance.isRegistered());
	}

	/**
	 * Test of getScope method, of class NewsPiece.
	 */
	@Test
	public void testGetScope() {
		System.out.println("getScope");
		assertEquals("role.scope debería ser 0", "0", String.valueOf(instance.getScope()));
	}

	/**
	 * Test of setScope method, of class NewsPiece.
	 */
	@Test
	public void testSetScope() {
		System.out.println("setScope");
		instance.setScope(1);
		assertEquals("role.scope debería ser 1", "1", String.valueOf(instance.getScope()));
	}

	/**
	 * Test of getSectionId method, of class NewsPiece.
	 */
	@Test
	public void testGetSectionId() {
		System.out.println("getSectionId");
		assertEquals("role.sectionId debería ser 0", "0", String.valueOf(instance.getSectionId()));
	}

	/**
	 * Test of setSection method, of class NewsPiece.
	 */
	@Test
	public void testSetSection() {
		System.out.println("setStatus");
		instance.setSectionId(1);
		assertEquals("role.sectionId debería ser 1", "1", String.valueOf(instance.getSectionId()));
	}

	/**
	 * Test of getStatus method, of class NewsPiece.
	 */
	@Test
	public void testGetStatus() {
		System.out.println("getStatus");
		assertEquals("role.status debería ser 0", "0", String.valueOf(instance.getStatus()));
	}

	/**
	 * Test of setSection method, of class NewsPiece.
	 */
	@Test
	public void testSetStatus() {
		System.out.println("setStatus");
		instance.setStatus(1);
		assertEquals("role.status debería ser 1", "1", String.valueOf(instance.getStatus()));
	}

	/**
	 * Test of getUser method, of class NewsPiece.
	 */
	@Test
	public void testGetUser() {
		System.out.println("getUser");
		assertEquals("newsPiece.user debería ser null", null, instance.getUser());
	}

	/**
	 * Test of setUser method, of class NewsPiece.
	 */
	@Test
	public void testSetUser() {
		System.out.println("setUser");
		instance.setUser("user");
		assertEquals("newsPiece.user debería ser 'user'", "user", instance.getUser());
	}

	/**
	 * Test of getShowParameters method, of class NewsPiece.
	 */
	@Test
	public void testGetShowParameters() {
		System.out.println("getShowParameters");
		assertEquals("newsPiece.description debería ser null", null, instance.getShowParameters());
	}

	/**
	 * Test of setShowParameters method, of class NewsPiece.
	 */
	@Test
	public void testSetShowParameters() {
		System.out.println("setShowParameters");
		instance.setShowParameters("ShowParameters");
		assertEquals("newsPiece.showParameters debería ser 'ShowParameters'", "ShowParameters", instance.getShowParameters());
	}

	/**
	 * Test of getNewsCompositionList method, of class NewsPiece.
	 */
	@Test
	public void testGetNewsCompositionList() {
		System.out.println("getNewsCompositionList");
		assertTrue("newsPiece.newsCompositionList debería ser empty", instance.getNewsCompositionList().isEmpty());
	}
	/**
	 * Test of setNewsCompositionList method, of class NewsPiece.
	 */
	@Test
	public void testSetNewsCompositionList() {
		System.out.println("setNewsCompositionList");
		List<NewsComposition> newsCompositionList = null;
		instance.setNewsCompositionList(newsCompositionList);
		assertTrue("newsPiece.newsCompositionList debería ser null", instance.getNewsCompositionList()==null);
	}

	/**
	 * Test of equals method, of class NewsPiece.
	 */
	@Test
	public void testEquals() {
		System.out.println("equals");
		Object obj = null;
		boolean result = instance.equals(obj);
		assertEquals("equals debería dar false",false, result);
		Object obj1 = new NewsPiece();
		((NewsPiece)obj1).setId(0);
		boolean result1 = instance.equals((NewsPiece)obj1);
		assertEquals("equals debería dar true",true, result1);
	}

}
