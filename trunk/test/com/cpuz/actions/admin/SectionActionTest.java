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
package com.cpuz.actions.admin;

import com.cpuz.domain.Section;
import com.cpuz.dummy.DAOFactoryDummy;
import com.cpuz.dummy.SectionActionDummy;
import com.cpuz.exceptions.UserException;
import java.sql.SQLException;
import com.cpuz.domain.UserType;
import com.cpuz.service.SectionsService;
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
public class SectionActionTest {

	private SectionActionDummy instance;

	public SectionActionTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
		instance = createSectionAction();
	}

	private SectionActionDummy createSectionAction() {
		SectionActionDummy sectionAction = new SectionActionDummy();
		sectionAction.setDataService(new SectionsService());
		sectionAction.getDataService().setDAOFactory(new DAOFactoryDummy());
		return sectionAction;
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of sectionNew method, of class SectionAction.
	 */
	@Test
	public void testObjectNew() {
		System.out.println("sectionNew");
		assertEquals("sectionNew() siempre devuelve 'new'", "new", instance.objectNew());
		assertEquals("sectionNew() control.recCount debe ser 1", 1, instance.getControl().getRecCount());
		assertEquals("sectionNew() control.runAction debe ser 'New'", "New", instance.getControl().getRunAction());
		assertEquals("sectionNew() requestAttributes[page] debe ser '/WEB-INF/views/sectionEdit.jsp'", "/WEB-INF/views/sectionEdit.jsp", instance.getRequestAttributes().get("page"));
	}

	/**
	 * Test of sectionEdit method, of class SectionAction.
	 */
	@Test(expected = SQLException.class)
	public void testObjectEdit_WhenSQLException() throws SQLException {
		System.out.println("sectionEdit");
		instance.getControl().setId(-1); // Provocamos SQLException
		instance.objectEdit();
	}

	/**
	 * Test of sectionEdit method, of class SectionAction.
	 */
	@Test
	public void testObjectEdit_WhenNotFound() throws Exception {
		System.out.println("sectionEdit");
		instance.getControl().setId(0); // Provocamos Not Found Record
		assertEquals("Debería ser 'list' cuando no encuentra registro", "list", instance.objectEdit());
	}

	/**
	 * Test of sectionEdit method, of class SectionAction.
	 */
	@Test
	public void testObjectEdit_WhenOk() throws Exception {
		System.out.println("sectionEdit");
		instance.getControl().setId(1); // Provocamos Not Found Record
		assertEquals("Debería ser 'edit' cuando no encuentra registro", "edit", instance.objectEdit());
		assertEquals("sectionEdit() control.runAction debe ser 'Edit'", "Edit", instance.getControl().getRunAction());
		assertEquals("sectionEdit() requestAttributes[page] debe ser '/WEB-INF/views/sectionEdit.jsp'", "/WEB-INF/views/sectionEdit.jsp", instance.getRequestAttributes().get("page"));
	}

	@Test(expected = SQLException.class)
	public void testObjectSaveNew_WhenSQLException() throws SQLException, UserException {
		System.out.println("sectionSaveNew");
		instance.getDataEdit().setName("X"); // Evitamos UserException
		instance.getDataEdit().setId(-1); // Provocamos SQLException
		instance.objectSaveNew();
	}

	@Test(expected = UserException.class)
	public void testObjectSaveNew_WhenUserException() throws SQLException, UserException {
		System.out.println("sectionSaveNew");
		instance.getDataEdit().setName("X"); // Evitamos UserException
		instance.objectSaveNew();
	}

	@Test
	public void testObjectSaveNew_WhenOk() throws SQLException, UserException {
		System.out.println("sectionSaveNew");
		instance.getDataEdit().setId(1);
		instance.getDataEdit().setName("X"); // Evitamos UserException
		assertEquals("Debería ser igual a 'list'", "list", instance.objectSaveNew());
	}

	@Test(expected = SQLException.class)
	public void testObjectSaveEdit_WhenSQLException() throws SQLException, UserException {
		System.out.println("sectionSaveEdit");
		instance.getDataEdit().setName("X"); // Evitamos UserException
		instance.getDataEdit().setId(-1); // Provocamos SQLException
		instance.objectSaveEdit();
	}

	@Test(expected = UserException.class)
	public void testObjectSaveEdit_WhenUserException() throws SQLException, UserException {
		System.out.println("sectionSaveEdit");
		instance.getDataEdit().setName("X"); // Evitamos UserException
		instance.objectSaveEdit();
	}

	@Test
	public void testObjectSaveEdit_WhenOk() throws SQLException, UserException {
		System.out.println("sectionSaveEdit");
		instance.getDataEdit().setId(1);
		instance.getDataEdit().setName("X"); // Evitamos UserException
		assertEquals("Debería ser igual a 'list'", "list", instance.objectSaveEdit());
	}

	/**
	 * Test of sectionDelete method, of class SectionAction.
	 */
	@Test
	public void testObjectDelete_WhenSelec1Null() throws SQLException {
		System.out.println("sectionDelete");
		instance.setSelec1(null);
		assertEquals("Debería ser igual a 'list'", "list", instance.objectDelete());
		assertTrue("Debería ser igual a 'NoneSelectedSection'", instance.getActionErrors().contains("NoneSelectedSection"));

	}

	/**
	 * Test of sectionDelete method, of class SectionAction.
	 */
	@Test(expected = SQLException.class)
	public void testObjectDelete_WhenSelec1Empty() throws SQLException {
		System.out.println("sectionDelete");
		instance.setSelec1("");
		instance.objectDelete();
	}

	/**
	 * Test of sectionDelete method, of class SectionAction.
	 */
	@Test
	public void testObjectDelete_WhenNoneDeleted() throws SQLException {
		System.out.println("sectionDelete");
		instance.setSelec1("none");
		assertEquals("Debería ser igual a 'list'", "list", instance.objectDelete());
		assertTrue("Debería ser igual a 'NoneDeletedSection'", instance.getActionErrors().contains("NoneDeletedSection"));
	}

	/**
	 * Test of sectionDelete method, of class SectionAction.
	 */
	@Test
	public void testObjectDelete_WhenOkDeleted() throws SQLException {
		System.out.println("sectionDelete");
		instance.setSelec1("1,2,3");
		assertEquals("Debería ser igual a 'list'", "list", instance.objectDelete());
		assertTrue("Debería ser igual a 'SuccessDeletedSections'", instance.getActionMessages().contains("SuccessDeletedSections"));
	}

	/**
	 * Test of sectionDelete method, of class SectionAction.
	 */
	@Test(expected = SQLException.class)
	public void testObjectList_WhenSQLException() throws SQLException {
		System.out.println("sectionList");
		instance.getControl().setRecChunk(-1);
		instance.objectList();
	}

	/**
	 * Test of sectionList method, of class SectionAction.
	 */
	@Test
	public void testObjectList_WhenOk() throws SQLException {
		System.out.println("sectionList");
		instance.getControl().setRecCount(0);
		assertEquals("Debería ser igual a 'list'", "list", instance.objectList());
		assertNotNull("DataList debería ser distinto de null", instance.getDataList());
		assertEquals("Debería ser igual a 'List'", "List", instance.getControl().getRunAction());
		assertEquals("Debería ser igual a 'UserType.ADMIN'", UserType.ADMIN, instance.getControl().getUserType());
		assertEquals("sectionList() requestAttributes[page] debe ser '/WEB-INF/views/sectionList.jsp'", "/WEB-INF/views/sectionList.jsp", instance.getRequestAttributes().get("page"));
	}

	/**
	 * Test of sectionNavigation method, of class SectionAction.
	 */
	@Test(expected = SQLException.class)
	public void testObjectNavigation_WhenSQLException() throws SQLException {
		System.out.println("sectionNavigation");
		instance.getControl().setRecChunk(-1);
		instance.objectNavigation();
	}

	/**
	 * Test of sectionNavigation method, of class SectionAction.
	 */
	@Test
	public void testObjectNavigation_WhenOk() throws SQLException {
		System.out.println("sectionNavigation");
		instance.getControl().setRecCount(0);
		assertEquals("Debería ser igual a 'list'", "list", instance.objectList());
	}

		/**
	 * Test of getDataEdit method, of class Role.
	 */
	@Test
	public void testGetDataEdit() {
		System.out.println("getDataEdit");
		Section result = instance.getDataEdit();
		assertNotNull("dataEdit NO debería ser null", result);
	}



}
