/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.actions.admin;

import com.cpuz.dummy.RoleDAOdummy;
import com.cpuz.domain.Role;
import com.cpuz.service.RolesService;
import com.cpuz.st2.beans.ControlParams;
import java.util.List;
import java.util.Map;
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
public class RoleActionTest {

	private RoleAction instance;

	public RoleActionTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
		instance = createRoleAction();
	}

	private RoleAction createRoleAction() {
		RoleAction roleAction = new RoleAction();
		roleAction.setDataService(new RolesService());
		roleAction.getDataService().setRoleDAO(new RoleDAOdummy());
		return roleAction;
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of execute method, of class RoleAction.
	 */
	@Test
	public void testExecute() {
		System.out.println("execute");
		assertEquals("Siempre debe devolver 'error'","error",instance.execute());
	}

	/**
	 * Test of roleNew method, of class RoleAction.
	 */
	@Test
	public void testRoleNew() {
		System.out.println("roleNew");
		assertEquals("roleNew() siempre devuelve 'new'","new",instance.roleNew());
		assertEquals("roleNew() control.recCount debe ser 1",1,instance.getControl().getRecCount());
		assertEquals("roleNew() control.runAction debe ser 'New'","New",instance.getControl().getRunAction());
		assertEquals("roleNew() requestAttributes[page] debe ser '/WEB-INF/views/roleEdit.jsp'","/WEB-INF/views/roleEdit.jsp",instance.getRequestAttributes().get("page"));
	}

	/**
	 * Test of roleEdit method, of class RoleAction.
	 */
	@Test
	public void testRoleEdit() throws Exception {
		System.out.println("roleEdit");
		RoleAction instance = new RoleAction();
		String expResult = "";
		String result = instance.roleEdit();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of roleSaveNew method, of class RoleAction.
	 */
	@Test
	public void testRoleSaveNew() throws Exception {
		System.out.println("roleSaveNew");
		RoleAction instance = new RoleAction();
		String expResult = "";
		String result = instance.roleSaveNew();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of roleSaveEdit method, of class RoleAction.
	 */
	@Test
	public void testRoleSaveEdit() throws Exception {
		System.out.println("roleSaveEdit");
		RoleAction instance = new RoleAction();
		String expResult = "";
		String result = instance.roleSaveEdit();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of roleDelete method, of class RoleAction.
	 */
	@Test
	public void testRoleDelete() throws Exception {
		System.out.println("roleDelete");
		RoleAction instance = new RoleAction();
		String expResult = "";
		String result = instance.roleDelete();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of roleList method, of class RoleAction.
	 */
	@Test
	public void testRoleList() throws Exception {
		System.out.println("roleList");
		RoleAction instance = new RoleAction();
		String expResult = "";
		String result = instance.roleList();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of roleNavigation method, of class RoleAction.
	 */
	@Test
	public void testRoleNavigation() throws Exception {
		System.out.println("roleNavigation");
		RoleAction instance = new RoleAction();
		String expResult = "";
		String result = instance.roleNavigation();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of validate method, of class RoleAction.
	 */
	@Test
	public void testValidate() {
		System.out.println("validate");
		RoleAction instance = new RoleAction();
		instance.validate();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getControl method, of class RoleAction.
	 */
	@Test
	public void testGetControl() {
		System.out.println("getControl");
		RoleAction instance = new RoleAction();
		ControlParams expResult = null;
		ControlParams result = instance.getControl();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of setControl method, of class RoleAction.
	 */
	@Test
	public void testSetControl() {
		System.out.println("setControl");
		ControlParams control = null;
		RoleAction instance = new RoleAction();
		instance.setControl(control);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getMapStatus method, of class RoleAction.
	 */
	@Test
	public void testGetMapStatus() {
		System.out.println("getMapStatus");
		RoleAction instance = new RoleAction();
		Map expResult = null;
		Map result = instance.getMapStatus();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of setMapStatus method, of class RoleAction.
	 */
	@Test
	public void testSetMapStatus() {
		System.out.println("setMapStatus");
		Map<Integer, String> mapStatus = null;
		RoleAction instance = new RoleAction();
		instance.setMapStatus(mapStatus);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getDataEdit method, of class RoleAction.
	 */
	@Test
	public void testGetDataEdit() {
		System.out.println("getDataEdit");
		RoleAction instance = new RoleAction();
		Role expResult = null;
		Role result = instance.getDataEdit();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of setDataEdit method, of class RoleAction.
	 */
	@Test
	public void testSetDataEdit() {
		System.out.println("setDataEdit");
		Role dataEdit = null;
		RoleAction instance = new RoleAction();
		instance.setDataEdit(dataEdit);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getDataList method, of class RoleAction.
	 */
	@Test
	public void testGetDataList() {
		System.out.println("getDataList");
		RoleAction instance = new RoleAction();
		List expResult = null;
		List result = instance.getDataList();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of setDataList method, of class RoleAction.
	 */
	@Test
	public void testSetDataList() {
		System.out.println("setDataList");
		List<Role> dataList = null;
		RoleAction instance = new RoleAction();
		instance.setDataList(dataList);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of setDataService method, of class RoleAction.
	 */
	@Test
	public void testSetDataService() {
		System.out.println("setDataService");
		RolesService dataService = null;
		RoleAction instance = new RoleAction();
		instance.setDataService(dataService);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getSelec1 method, of class RoleAction.
	 */
	@Test
	public void testGetSelec1() {
		System.out.println("getSelec1");
		RoleAction instance = new RoleAction();
		String expResult = "";
		String result = instance.getSelec1();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of setSelec1 method, of class RoleAction.
	 */
	@Test
	public void testSetSelec1() {
		System.out.println("setSelec1");
		String selec1 = "";
		RoleAction instance = new RoleAction();
		instance.setSelec1(selec1);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of initMapStatus method, of class RoleAction.
	 */
	@Test
	public void testInitMapStatus() {
		System.out.println("initMapStatus");
		RoleAction instance = new RoleAction();
		instance.initMapStatus();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of setRequest method, of class RoleAction.
	 */
	@Test
	public void testSetRequest() {
		System.out.println("setRequest");
		Map map = null;
		RoleAction instance = new RoleAction();
		instance.setRequest(map);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}
}
