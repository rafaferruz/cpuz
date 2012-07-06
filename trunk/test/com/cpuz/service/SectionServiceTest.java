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
import com.cpuz.domain.Section;
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
public class SectionServiceTest {

	private SectionsService instance;

	public SectionServiceTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
		instance = createSectionService();
	}

	private SectionsService createSectionService() {
		SectionsService sectionsService = new SectionsService();
		sectionsService.setDAOFactory(new DAOFactoryDummy());
		return sectionsService;
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of keyIdExists method, of class SectionsService.
	 */
	@Test(expected = UserException.class)
	public void testKeyIdExists_whenSectionIdNull() throws SQLException, UserException {
		System.out.println("keyIdExists");
		int sectionId = 0;
		boolean result = instance.keyIdExists(sectionId);
	}

	/**
	 * Test of keyIdExists method, of class SectionsService.
	 */
	@Test(expected = SQLException.class)
	public void testKeyIdExists_whenSQLException() throws SQLException, UserException {
		System.out.println("keyIdExists");
		int sectionId = -1;	// Provocamos SQLException
		boolean result = instance.keyIdExists(sectionId);
	}

	/**
	 * Test of keyIdExists method, of class SectionsService.
	 */
	@Test
	public void testKeyIdExists_whenIdNotFound() throws SQLException, UserException {
		System.out.println("keyIdExists");
		int sectionId = -2;	// Provocamos sectionId not found
		assertFalse("sectionId no debería ser encontrado", instance.keyIdExists(sectionId));
	}

	/**
	 * Test of keyIdExists method, of class SectionsService.
	 */
	@Test
	public void testKeyIdExists_whenIdIsFound() throws SQLException, UserException {
		System.out.println("keyIdExists");
		int sectionId = 1;	// Provocamos sectionId is found
		assertTrue("sectionId debería haber sido encontrado", instance.keyIdExists(sectionId));
	}

	/**
	 * Test of getSectionList method, of class SectionsService.
	 */
	@Test(expected = SQLException.class)
	public void testGetSectionList_whenSQLException() throws SQLException {
		System.out.println("getSectionList");
		instance.getControl().setRecChunk(-1);
		instance.getSectionList(instance.getControl());
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getSectionList method, of class SectionsService.
	 */
	@Test
	public void testGetSectionList_whenEmptyList() throws SQLException, UserException {
		System.out.println("getSectionList");
		ControlParams control = new ControlParams();
		control.setRecCount(0);	// Provocamos que devuelva una lista vacía
		assertTrue("Debería devolver una lista vacía", instance.getSectionList(control).isEmpty());
	}

	/**
	 * Test of getSectionList method, of class SectionsService.
	 */
	@Test
	public void testGetSectionList_whenNotEmptyList() throws SQLException, UserException {
		System.out.println("getSectionList");
		ControlParams control = new ControlParams();
		control.setRecCount(2);	// Provocamos que devuelva una lista con 2 objetos
		assertEquals("Debería devolver una lista con 2 objetos", 2, instance.getSectionList(control).size());
	}

	/**
	 * Test of getCountRows method, of class SectionsService.
	 */
	@Test
	public void testGetCountRows() throws SQLException {
		System.out.println("getCountRows");
		assertEquals("Debería devolver el contador de registros igual a 1", 1, instance.getCountRows());
	}

	/**
	 * Test of keyIdExists method, of class SectionsService.
	 */
	@Test(expected = SQLException.class)
	public void testGetById_whenSQLException() throws SQLException, UserException {
		System.out.println("getById");
		int sectionId = -1;	// Provocamos SQLException
		instance.getById(sectionId);
	}

	/**
	 * Test of keyIdExists method, of class SectionsService.
	 */
	@Test
	public void testGetById_whenIdNotFound() throws SQLException, UserException {
		System.out.println("getById");
		int sectionId = 0;	// Provocamos sectionId not found
		assertNull("Debería devolver un objeto null", instance.getById(sectionId));
	}

	/**
	 * Test of keyIdExists method, of class SectionsService.
	 */
	@Test
	public void testGetById_whenIdIsFound() throws SQLException, UserException {
		System.out.println("getById");
		int sectionId = 1;	// Provocamos sectionId is found
		assertTrue("Debería devolver un objeto Section", instance.getById(sectionId).getClass().getName().equals("com.cpuz.domain.Section"));
	}

	/**
	 * Test of insertSection method, of class SectionsService.
	 */
	@Test(expected = UserException.class)
	public void testInsertSectionUserException() throws Exception {
		System.out.println("insertSection");
		Section section = null;
		instance.insertSection(section);
	}

	/**
	 * Test of insertSection method, of class SectionsService.
	 */
	@Test(expected = SQLException.class)
	public void testInsertSectionSQLException() throws Exception {
		System.out.println("insertSection");
		Section section = new Section();
		section.setId(-1);
		section.setName("section");
		instance.insertSection(section);
	}

	/**
	 * Test of insertSection method, of class SectionsService.
	 */
	@Test
	public void testInsertSectionFailInsert() throws Exception {
		System.out.println("insertSection");
		Section section = new Section();
		section.setId(-2);
		section.setName("section");
		assertEquals("Insert no ha persistido ningún registro", 0, instance.insertSection(section));
	}

	/**
	 * Test of insertSection method, of class SectionsService.
	 */
	@Test
	public void testInsertSectionOkInsert() throws Exception {
		System.out.println("insertSection");
		Section section = new Section();
		section.setId(1);
		section.setName("section");
		assertEquals("Insert ha persistido 1 registro", 1, instance.insertSection(section));
	}

	/**
	 * Test of updateSection method, of class SectionsService.
	 */
	@Test(expected = SQLException.class)
	public void testUpdateSectionSQLException() throws Exception {
		System.out.println("updateSection");
		Section section = new Section();
		section.setId(-1);
		section.setName("section");
		instance.updateSection(section);
	}

	/**
	 * Test of updateSection method, of class SectionsService.
	 */
	@Test(expected = UserException.class)
	public void testUpdateSectionUserException() throws Exception {
		System.out.println("updateSection");
		Section section = new Section();
		instance.updateSection(section);
	}

	/**
	 * Test of updateSection method, of class SectionsService.
	 */
	@Test
	public void testUpdateSectionFailUpdate() throws Exception {
		System.out.println("updateSection");
		Section section = new Section();
		section.setId(-2);
		section.setName("section");
		assertEquals("Debería devolver 0 filas actualizadas", 0, instance.updateSection(section));
	}

	/**
	 * Test of updateSection method, of class SectionsService.
	 */
	@Test
	public void testUpdateSectionOkUpdate() throws Exception {
		System.out.println("updateSection");
		Section section = new Section();
		section.setId(1);
		section.setName("section");
		assertEquals("Debería devolver 0 filas actualizadas", 1, instance.updateSection(section));
	}

	/**
	 * Test of deleteSection method, of class SectionsService.
	 */
	@Test(expected = UserException.class)
	public void testDeleteSection_whenUserException() throws SQLException, UserException {
		System.out.println("deleteSection");
		Section section = null; // Provocamos UserException
		instance.deleteSection(section);
	}

	/**
	 * Test of deleteSection method, of class SectionsService.
	 */
	@Test(expected = SQLException.class)
	public void testDeleteSection_whenSQLException() throws SQLException, UserException {
		System.out.println("deleteSection");
		Section section = new Section();
		section.setId(-1);	// Provocamos SQLException
		instance.deleteSection(section);
	}

	/**
	 * Test of deleteSection method, of class SectionsService.
	 */
	@Test
	public void testDeleteSection_FailDelete() throws SQLException, UserException {
		System.out.println("deleteSection");
		Section section = new Section();
		section.setId(0);	// Provocamos fallo en la eliminación
		assertEquals("Debería devolver 0 elementos eliminados", 0, instance.deleteSection(section));
	}

	/**
	 * Test of deleteSection method, of class SectionsService.
	 */
	@Test
	public void testDeleteSection_OkDelete() throws SQLException, UserException {
		System.out.println("deleteSection");
		Section section = new Section();
		section.setId(1);	// Provocamos eliminación correcta
		assertEquals("Debería devolver 1 fila eliminada", 1, instance.deleteSection(section));
	}

	/**
	 * Test of deleteSectionIds method, of class SectionsService.
	 */
	@Test
	public void testDeleteSectionIds_ListNull() throws SQLException {
		System.out.println("deleteSectionIds");
		List<String> ids = null;
		assertEquals("Debería ser 0 filas deleted",0,instance.deleteSectionIds(ids));
	}

	/**
	 * Test of deleteSectionIds method, of class SectionsService.
	 */
	@Test(expected=SQLException.class)
	public void testDeleteSectionIds_whenSQLException() throws SQLException {
		System.out.println("deleteSectionIds");
		List<String> ids = new ArrayList<>();
		ids.add("");
		instance.deleteSectionIds(ids);
	}

	/**
	 * Test of deleteSectionIds method, of class SectionsService.
	 */
	@Test
	public void testDeleteSectionIds_OkDeleting() throws SQLException {
		System.out.println("deleteSectionIds");
		String id="One row";
		List<String> ids = new ArrayList<>();
		ids.add(id);
		assertEquals("Debería ser 1 fila deleted",1,instance.deleteSectionIds(ids));
	}
		/**
	 * Test of getSectionsNoGroup method, of class SectionsService.
	 */
	public void testGetSectionsNoGroup() throws SQLException {
		System.out.println("getSectionsNoGroup");
		assertTrue("Debería devolver una lista vacía", instance.getSectionsNoGroup().isEmpty());
	}


}
