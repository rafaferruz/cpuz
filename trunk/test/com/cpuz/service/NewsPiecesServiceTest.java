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
import com.cpuz.domain.NewsPiece;
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
public class NewsPiecesServiceTest {

	private NewsPiecesService instance;

	public NewsPiecesServiceTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
		instance = createNewsPieceService();
	}

	private NewsPiecesService createNewsPieceService() {
		NewsPiecesService newsPiecesService = new NewsPiecesService();
		newsPiecesService.setDAOFactory(new DAOFactoryDummy());
		return newsPiecesService;
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of keyIdExists method, of class NewsPiecesService.
	 */
	@Test(expected = SQLException.class)
	public void testKeyIdExists_whenSQLException() throws SQLException, UserException {
		System.out.println("keyIdExists");
		int newsPieceId = -1;	// Provocamos SQLException
		boolean result = instance.keyIdExists(newsPieceId);
	}

	/**
	 * Test of keyIdExists method, of class NewsPiecesService.
	 */
	@Test
	public void testKeyIdExists_whenIdNotFound() throws SQLException, UserException {
		System.out.println("keyIdExists");
		int newsPieceId = -2;	// Provocamos newsPieceId not found
		assertFalse("newsPieceId no debería ser encontrado", instance.keyIdExists(newsPieceId));
	}

	/**
	 * Test of keyIdExists method, of class NewsPiecesService.
	 */
	@Test
	public void testKeyIdExists_whenIdIsFound() throws SQLException, UserException {
		System.out.println("keyIdExists");
		int newsPieceId = 1;	// Provocamos newsPieceId is found
		assertTrue("newsPieceId debería haber sido encontrado", instance.keyIdExists(newsPieceId));
	}

	/**
	 * Test of getNewsPieceList method, of class NewsPiecesService.
	 */
	@Test(expected = SQLException.class)
	public void testGetNewsPieceList_whenSQLException() throws SQLException, UserException {
		System.out.println("getNewsPieceList");
		instance.getControl().setRecChunk(-1);
		instance.getNewsPieceList(instance.getControl());
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getNewsPieceList method, of class NewsPiecesService.
	 */
	@Test
	public void testGetNewsPieceList_whenEmptyList() throws SQLException, UserException {
		System.out.println("getNewsPieceList");
		ControlParams control = new ControlParams();
		control.setRecCount(0);	// Provocamos que devuelva una lista vacía
		assertTrue("Debería devolver una lista vacía", instance.getNewsPieceList(control).isEmpty());
	}

	/**
	 * Test of getNewsPieceList method, of class NewsPiecesService.
	 */
	@Test
	public void testGetNewsPieceList_whenNotEmptyList() throws SQLException, UserException {
		System.out.println("getNewsPieceList");
		ControlParams control = new ControlParams();
		control.setRecCount(2);	// Provocamos que devuelva una lista con 2 objetos
		assertEquals("Debería devolver una lista con 2 objetos", 2, instance.getNewsPieceList(control).size());
	}

	/**
	 * Test of getCountRows method, of class NewsPiecesService.
	 */
	@Test
	public void testGetCountRows() throws SQLException {
		System.out.println("getCountRows");
		assertEquals("Debería devolver el contador de registros igual a 1", 1, instance.getCountRows());
	}

	/**
	 * Test of keyIdExists method, of class NewsPiecesService.
	 */
	@Test(expected = SQLException.class)
	public void testGetById_whenSQLException() throws SQLException, UserException {
		System.out.println("getById");
		int newsPieceId = -1;	// Provocamos SQLException
		instance.getById(newsPieceId);
	}

	/**
	 * Test of keyIdExists method, of class NewsPiecesService.
	 */
	@Test
	public void testGetById_whenIdNotFound() throws SQLException, UserException {
		System.out.println("getById");
		int newsPieceId = 0;	// Provocamos newsPieceId not found
		assertNull("Debería devolver un objeto null", instance.getById(newsPieceId));
	}

	/**
	 * Test of keyIdExists method, of class NewsPiecesService.
	 */
	@Test
	public void testGetById_whenIdIsFound() throws SQLException, UserException {
		System.out.println("getById");
		int newsPieceId = 1;	// Provocamos newsPieceId is found
		assertTrue("Debería devolver un objeto NewsPiece", instance.getById(newsPieceId).getClass().getName().equals("com.cpuz.domain.NewsPiece"));
	}

	/**
	 * Test of insertNewsPiece method, of class NewsPiecesService.
	 */
	@Test(expected = UserException.class)
	public void testInsertNewsPieceUserException() throws Exception {
		System.out.println("insertNewsPiece");
		NewsPiece newsPiece = null;
		instance.insertNewsPiece(newsPiece);
	}

	/**
	 * Test of insertNewsPiece method, of class NewsPiecesService.
	 */
	@Test(expected = SQLException.class)
	public void testInsertNewsPieceSQLException() throws Exception {
		System.out.println("insertNewsPiece");
		NewsPiece newsPiece = new NewsPiece();
		newsPiece.setId(-1);
		newsPiece.setDescription("newsPiece");
		instance.insertNewsPiece(newsPiece);
	}

	/**
	 * Test of insertNewsPiece method, of class NewsPiecesService.
	 */
	@Test
	public void testInsertNewsPieceFailInsert() throws Exception {
		System.out.println("insertNewsPiece");
		NewsPiece newsPiece = new NewsPiece();
		newsPiece.setId(-2);
		newsPiece.setDescription("newsPiece");
		assertEquals("Insert no ha persistido ningún registro", 0, instance.insertNewsPiece(newsPiece));
	}

	/**
	 * Test of insertNewsPiece method, of class NewsPiecesService.
	 */
	@Test
	public void testInsertNewsPieceOkInsert() throws Exception {
		System.out.println("insertNewsPiece");
		NewsPiece newsPiece = new NewsPiece();
		newsPiece.setId(1);
		newsPiece.setDescription("newsPiece");
		assertEquals("Insert ha persistido 1 registro", 1, instance.insertNewsPiece(newsPiece));
	}

	/**
	 * Test of updateNewsPiece method, of class NewsPiecesService.
	 */
	@Test(expected = SQLException.class)
	public void testUpdateNewsPieceSQLException() throws Exception {
		System.out.println("updateNewsPiece");
		NewsPiece newsPiece = new NewsPiece();
		newsPiece.setId(-1);
		newsPiece.setDescription("newsPiece");
		instance.updateNewsPiece(newsPiece);
	}

	/**
	 * Test of updateNewsPiece method, of class NewsPiecesService.
	 */
	@Test(expected = UserException.class)
	public void testUpdateNewsPieceUserException() throws Exception {
		System.out.println("updateNewsPiece");
		NewsPiece newsPiece = new NewsPiece();
		instance.updateNewsPiece(newsPiece);
	}

	/**
	 * Test of updateNewsPiece method, of class NewsPiecesService.
	 */
	@Test
	public void testUpdateNewsPieceFailUpdate() throws Exception {
		System.out.println("updateNewsPiece");
		NewsPiece newsPiece = new NewsPiece();
		newsPiece.setId(-2);
		newsPiece.setDescription("newsPiece");
		assertEquals("Debería devolver 0 filas actualizadas", 0, instance.updateNewsPiece(newsPiece));
	}

	/**
	 * Test of updateNewsPiece method, of class NewsPiecesService.
	 */
	@Test
	public void testUpdateNewsPieceOkUpdate() throws Exception {
		System.out.println("updateNewsPiece");
		NewsPiece newsPiece = new NewsPiece();
		newsPiece.setId(1);
		newsPiece.setDescription("newsPiece");
		assertEquals("Debería devolver 0 filas actualizadas", 1, instance.updateNewsPiece(newsPiece));
	}

	/**
	 * Test of deleteNewsPiece method, of class NewsPiecesService.
	 */
	@Test(expected = UserException.class)
	public void testDeleteNewsPiece_whenUserException() throws SQLException, UserException {
		System.out.println("deleteNewsPiece");
		NewsPiece newsPiece = null; 	// Provocamos UserException
		instance.deleteNewsPiece(newsPiece);
	}

	/**
	 * Test of deleteNewsPiece method, of class NewsPiecesService.
	 */
	@Test(expected = SQLException.class)
	public void testDeleteNewsPiece_whenSQLException() throws SQLException, UserException {
		System.out.println("deleteNewsPiece");
		NewsPiece newsPiece = new NewsPiece();
		newsPiece.setId(-1);	// Provocamos SQLException
		instance.deleteNewsPiece(newsPiece);
	}

	/**
	 * Test of deleteNewsPiece method, of class NewsPiecesService.
	 */
	@Test
	public void testDeleteNewsPiece_FailDelete() throws SQLException, UserException {
		System.out.println("deleteNewsPiece");
		NewsPiece newsPiece = new NewsPiece();
		newsPiece.setId(0);	// Provocamos fallo en la eliminación
		assertEquals("Debería devolver 0 elementos eliminados", 0, instance.deleteNewsPiece(newsPiece));
	}

	/**
	 * Test of deleteNewsPiece method, of class NewsPiecesService.
	 */
	@Test
	public void testDeleteNewsPiece_OkDelete() throws SQLException, UserException {
		System.out.println("deleteNewsPiece");
		NewsPiece newsPiece = new NewsPiece();
		newsPiece.setId(1);	// Provocamos eliminación correcta
		assertEquals("Debería devolver 1 fila eliminada", 1, instance.deleteNewsPiece(newsPiece));
	}

	/**
	 * Test of deleteNewsPieceIds method, of class NewsPiecesService.
	 */
	@Test
	public void testDeleteNewsPieceIds_ListNull() throws SQLException {
		System.out.println("deleteNewsPieceIds");
		List<Integer> ids = null;
		assertEquals("Debería ser 0 filas deleted", 0, instance.deleteNewsPieceIds(ids));
	}

	/**
	 * Test of deleteNewsPieceIds method, of class NewsPiecesService.
	 */
	@Test(expected = SQLException.class)
	public void testDeleteNewsPieceIds_whenSQLException() throws SQLException {
		System.out.println("deleteNewsPieceIds");
		List<Integer> ids = new ArrayList<>();
		ids.add(-1);
		instance.deleteNewsPieceIds(ids);
	}

	/**
	 * Test of deleteNewsPieceIds method, of class NewsPiecesService.
	 */
	@Test
	public void testDeleteNewsPieceIds_OkDeleting() throws SQLException {
		System.out.println("deleteNewsPieceIds");
		String id = "One row";
		List<Integer> ids = new ArrayList<>();
		ids.add(1);
		assertEquals("Debería ser 1 fila deleted", 1, instance.deleteNewsPieceIds(ids));
	}
}
