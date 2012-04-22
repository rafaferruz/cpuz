/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.st2.actions;

import com.cpuz.actions.admin.RoleAction;
import java.util.ArrayList;
import java.sql.SQLException;
import com.cpuz.model.RolesModelTest;
import com.cpuz.domain.Role;
import com.cpuz.st2.beans.ControlParams;
import java.util.HashMap;
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

	RoleAction instance;
	ControlParams control;

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
		instance = new RoleActionExtended();
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
		String expResult = "error";
		String result = instance.execute();
		assertEquals(expResult, result);
	}

	/**
	 * Test of roleNew method, of class RoleAction.
	 */
	@Test
	public void testRoleNew() {
		System.out.println("roleNew");
		String expResult = "New";
		String result = instance.roleNew();
		assertEquals(expResult, result);
		int recCount = instance.getControl().getRecCount();
		assertEquals(1, recCount);
		assertEquals("new", instance.getControl().getRunAction());
	}

	/**
	 * Test of roleEdit method, of class RoleAction.
	 */
	@Test
	public void testRoleEditNull() throws SQLException {
		System.out.println("roleEdit");
		RolesModelTest rolesModel = new RolesModelTest();
		rolesModel.setRows(0);
		instance.setDataModel(rolesModel);
		String expResult = "Edit";
		String result = instance.roleEdit();
		assertEquals(expResult, result);
		assertNull(instance.getDataEdit());
	}

	/**
	 * Test of roleEdit method, of class RoleAction.
	 */
	@Test
	public void testRoleEditNotNull() throws SQLException {
		System.out.println("roleEdit");
		RolesModelTest rolesModel = new RolesModelTest();
		rolesModel.setRows(1);
		instance.setDataModel(rolesModel);
		String expResult = "Edit";
		String result = instance.roleEdit();
		assertEquals(expResult, result);
		assertNotNull(instance.getDataEdit());
	}

	/**
	 * Test of roleEdit method, of class RoleAction.
	 */
	@Test(expected = SQLException.class)
	public void testRoleEditException() throws SQLException {
		System.out.println("roleEdit");
		RolesModelTest rolesModel = new RolesModelTest();
		rolesModel.setRows(null);
		instance.setDataModel(rolesModel);
		instance.roleEdit();
	}

	/**
	 * Test of roleSaveNew method, of class RoleAction.
	 */
	@Test
	public void testRoleSaveNewOK() throws SQLException {
		System.out.println("roleSaveNew");
		RolesModelTest rolesModel = new RolesModelTest();
		rolesModel.setRows(1);
		instance.setDataModel(rolesModel);
		instance.setDataEdit(new Role());
		String expResult = "List";
		String result = instance.roleSaveNew();
		assertEquals("No hay mensaje de OK insert", 1, instance.getActionMessages().size());
		assertEquals("Debería devolver List para ir a listar", expResult, result);
	}

	/**
	 * Test of roleSaveNew method, of class RoleAction.
	 */
	@Test
	public void testRoleSaveNewFail() throws SQLException {
		System.out.println("roleSaveNew");
		RolesModelTest rolesModel = new RolesModelTest();
		rolesModel.setRows(0);
		instance.setDataModel(rolesModel);
		instance.setDataEdit(new Role());
		String expResult = "Edit";
		String result = instance.roleSaveNew();
		assertEquals("Hay mensaje de OK insert y no debería haber", 0, instance.getActionMessages().size());
		assertEquals("Debería devolver Edit para volver a editar", expResult, result);
	}

	/**
	 * Test of roleSaveNew method, of class RoleAction.
	 */
	@Test(expected = SQLException.class)
	public void testRoleSaveNewException() throws SQLException {
		System.out.println("roleSaveNew");
		RolesModelTest rolesModel = new RolesModelTest();
		rolesModel.setRows(null);
		instance.setDataModel(rolesModel);
		instance.setDataEdit(new Role());
		instance.roleSaveNew();
	}

	/**
	 * Test of roleSaveEdit method, of class RoleAction.
	 */
	@Test
	public void testRoleSaveEditButNoExists() throws Exception {
		System.out.println("roleSaveEdit");
		RolesModelTest rolesModel = new RolesModelTest();
		rolesModel.setRows(0);
		instance.setDataModel(rolesModel);
		String expResult = "New";
		String result = instance.roleSaveEdit();
		assertEquals("Ha encontrado un registro y no debería pasar a New", expResult, result);
	}

	/**
	 * Test of roleSaveEdit method, of class RoleAction.
	 */
	@Test
	public void testRoleSaveEditOK() throws SQLException {
		System.out.println("roleSaveEdit");
		RolesModelTest rolesModel = new RolesModelTest();
		rolesModel.setRows(1);
		instance.setDataModel(rolesModel);
		instance.setDataEdit(new Role());
		String expResult = "List";
		String result = instance.roleSaveEdit();
		assertEquals("No hay mensaje de OK insert", 1, instance.getActionMessages().size());
		assertEquals("Debería devolver List para ir a listar", expResult, result);
	}

	/**
	 * Test of roleSaveNew method, of class RoleAction.
	 * Caso de uso: No existe el registro que queremos guardar
	 */
	@Test
	public void testRoleSaveEditNoOK() throws SQLException {
		System.out.println("roleSaveEdit");
		RolesModelTest rolesModel = new RolesModelTest();
		rolesModel.setRows(0);
		instance.setDataModel(rolesModel);
		instance.setDataEdit(new Role());
		String expResult = "New";
		String result = instance.roleSaveEdit();
		assertEquals("Debería devolver New ", expResult, result);
	}

	/**
	 * Test of roleDeleteIds method, of class RoleAction.
	 * Caso de uso: No recibe parámetros de selección de registros en la solicitud
	 */
	@Test
	public void testRoleDeleteIdsNoSelec() throws SQLException {
		System.out.println("roleDeleteIds");
		RolesModelTest rolesModel = new RolesModelTest();
		rolesModel.setRows(1);
		instance.setDataModel(rolesModel);
		instance.setDataEdit(new Role());
		instance.setSelec1(null);
		String expResult = "List";
		String result = instance.roleDeleteIds();
		assertEquals("No hay mensaje de OK delete", 1, instance.getActionErrors().size());
		assertEquals("Debería devolver List para ir a listar", expResult, result);
	}

	/**
	 * Test of roleDeleteIds method, of class RoleAction.
	 * Caso de uso: Recibe parámetros de selección de registros en la solicitud
	 * pero NO elimina ningún registro
	 */
	@Test
	public void testRoleDeleteIdsSelec_0() throws Exception {
		System.out.println("roleDeleteIds");
		RolesModelTest rolesModel = new RolesModelTest();
		rolesModel.setRows(0);
		instance.setDataModel(rolesModel);
		instance.setDataEdit(new Role());
		instance.setSelec1("1,2,3");
		String expResult = "List";
		String result = instance.roleDeleteIds();
		assertEquals("No hay mensaje de OK delete", 1, instance.getActionErrors().size());
		assertEquals("Debería devolver List para ir a listar", expResult, result);
	}

	/**
	 * Test of roleDeleteIds method, of class RoleAction.
	 * Caso de uso: Recibe parámetros de selección de registros en la solicitud
	 * y los elimina correctamente
	 */
	@Test
	public void testRoleDeleteIdsSelec_N() throws Exception {
		System.out.println("roleDeleteIds");
		RolesModelTest rolesModel = new RolesModelTest();
		rolesModel.setRows(3);
		instance.setDataModel(rolesModel);
		instance.setDataEdit(new Role());
		instance.setSelec1("1,2,3");
		String expResult = "List";
		String result = instance.roleDeleteIds();
		assertEquals("Hay mensaje de Error en delete correcto", 0, instance.getActionErrors().size());
		assertEquals("No Hay mensaje de OK en delete correcto", 1, instance.getActionMessages().size());
		assertEquals("Debería devolver List para ir a listar", expResult, result);
	}

	/**
	 * Test of roleDeleteIds method, of class RoleAction.
	 * Caso de uso: Recibe una SQLException
	 */
	@Test(expected = SQLException.class)
	public void testRoleDeleteIdsException() throws Exception {
		System.out.println("roleDeleteIds");
		RolesModelTest rolesModel = new RolesModelTest();
		rolesModel.setRows(null);
		instance.setDataModel(rolesModel);
		instance.setDataEdit(new Role());
		instance.setSelec1("1,2,3");
		instance.roleDeleteIds();
	}

	/**
	 * Test of roleList method, of class RoleAction.
	 * Caso de uso: Recupera registros
	 */
	@Test
	public void testRoleList() throws SQLException {
		System.out.println("roleList");
		RolesModelTest rolesModel = new RolesModelTest();
		rolesModel.setRows(1);
		instance.setDataModel(rolesModel);
		String expResult = "List";
		String result = instance.roleList();
		assertEquals(expResult, result);
		assertEquals(1, instance.getDataList().size());
	}

	/**
	 * Test of roleList method, of class RoleAction.
	 * Caso de uso: Recibe una SQLException
	 */
	@Test(expected = SQLException.class)
	public void testRoleListException() throws SQLException {
		System.out.println("roleList");
		RolesModelTest rolesModel = new RolesModelTest();
		rolesModel.setRows(-1);
		instance.setDataModel(rolesModel);
		instance.roleList();
	}

	/**
	 * Test of roleNavigation method, of class RoleAction.
	 * Caso de uso: Navegamos al primer registro
	 */
	@Test
	public void testRoleNavigationNavFirst() throws SQLException {
		System.out.println("roleNavigation");
		RolesModelTest rolesModel = new RolesModelTest();
		rolesModel.setRows(1);
		instance.setDataModel(rolesModel);
		instance.getControl().setRunAction("nav_first");
		String expResult = "List";
		String result = instance.roleNavigation();
		assertEquals("Esperábamos una orden de List", expResult, result);
		assertEquals("recStart debería ser 0", 0, instance.getControl().getRecStart().intValue());
	}

	/**
	 * Test of roleNavigation method, of class RoleAction.
	 * Caso de uso: Navegamos al primer registro
	 */
	@Test
	public void testRoleNavigationNavLast() throws SQLException {
		System.out.println("roleNavigation");
		RolesModelTest rolesModel = new RolesModelTest();
		rolesModel.setRows(1);
		instance.setDataModel(rolesModel);
		instance.getControl().setRunAction("nav_last");
		instance.getControl().setRecCount(100);
		instance.getControl().setRecChunk(20);
		String expResult = "List";
		String result = instance.roleNavigation();
		assertEquals("Esperábamos una orden de List", expResult, result);
		assertEquals("recStart debería ser 0", 100 - 20, instance.getControl().getRecStart().intValue());
	}

	/**
	 * Test of getControl method, of class RoleAction.
	 */
	@Test
	public void testGetControl() {
		System.out.println("getControl");
		ControlParams expResult = new ControlParams();
		ControlParams result = instance.getControl();
		assertEquals("Esperábamos recibir un ControlParams object", expResult.getClass(), result.getClass());
	}

	/**
	 * Test of setControl method, of class RoleAction.
	 */
	@Test
	public void testSetControl() {
		System.out.println("setControl");
		ControlParams control = new ControlParams();
		instance.setControl(control);
		assertSame("Esperábamos recibir el mismo objeto", control, instance.getControl());
	}

	/**
	 * Test of getMapStatus method, of class RoleAction.
	 */
	@Test
	public void testGetMapStatus() {
		System.out.println("getMapStatus");
		Map expResult = new HashMap<>();
		assertEquals("Esperábamos recibir un Map<Integer,String>", expResult, instance.getMapStatus());
	}

	/**
	 * Test of setMapStatus method, of class RoleAction.
	 */
	@Test
	public void testSetMapStatus() {
		System.out.println("setMapStatus");
		Map<Integer, String> mapStatus = new HashMap<>();
		mapStatus.put(1, "123");
		instance.setMapStatus(mapStatus);
		assertSame("Esperábamos el mismo mapa", mapStatus, instance.getMapStatus());
	}

	/**
	 * Test of getDataEdit method, of class RoleAction.
	 */
	@Test
	public void testGetDataEdit() {
		System.out.println("getDataEdit");
		Role expResult = new Role();
		Role result = instance.getDataEdit();
		assertEquals("Esperábamos un objeto Role", expResult, result);
	}

	/**
	 * Test of setDataEdit method, of class RoleAction.
	 */
	@Test
	public void testSetDataEdit() {
		System.out.println("setDataEdit");
		Role dataEdit = new Role();
		instance.setDataEdit(dataEdit);
		assertSame("Esperábamos el mismo objeto Role", dataEdit, instance.getDataEdit());
	}

	/**
	 * Test of getDataList method, of class RoleAction.
	 */
	@Test
	public void testGetDataList() {
		System.out.println("getDataList");
		List<Role> expResult = new ArrayList<>();
		List result = instance.getDataList();
		assertEquals("Esperábamos una lista de Roles", expResult, result);
	}

	/**
	 * Test of setDataList method, of class RoleAction.
	 */
	@Test
	public void testSetDataList() {
		System.out.println("setDataList");
		List<Role> dataList = new ArrayList<>();
		instance.setDataList(dataList);
		assertSame("Esperábamos la misma lista de Roles", dataList, instance.getDataList());
	}

	/**
	 * Test of getSelec1 method, of class RoleAction.
	 */
	@Test
	public void testGetSelec1() {
		System.out.println("getSelec1");
		String expResult = "1,2,3";
		instance.setSelec1(expResult);
		assertSame("Esperábamos el mismo string", expResult, instance.getSelec1());
	}

	/**
	 * Test of setSelec1 method, of class RoleAction.
	 */
	@Test
	public void testSetSelec1() {
		System.out.println("setSelec1");
		String expResult = "1,2,3";
		instance.setSelec1(expResult);
		assertSame("Esperábamos el mismo string", expResult, instance.getSelec1());
	}

	/**
	 * Test of initMapStatus method, of class RoleAction.
	 */
	@Test
	public void testInitMapStatus() {
		System.out.println("initMapStatus");
		instance.initMapStatus();
		assertEquals("Esperábamos received", "received", instance.getMapStatus().get(0));
		assertEquals("Esperábamos waiting", "waiting", instance.getMapStatus().get(1));
		assertEquals("Esperábamos authorized", "authorized", instance.getMapStatus().get(2));
	}

	class RoleActionExtended extends RoleAction {

		private String text;
		public Map<Integer, String> mapStatus = new HashMap<>();

		public String getText(String text) {
			this.text = text;
			return text;
		}

		public String getSavedText() {
			return text;
		}

		public void initMapStatus() {
			//Prepara tipos de status para radio element
			mapStatus.put(0, "received");
			mapStatus.put(1, "waiting");
			mapStatus.put(2, "authorized");
		}

		public Map<Integer, String> getMapStatus() {
			return mapStatus;
		}

		public void setMapStatus(Map<Integer, String> mapStatus) {
			this.mapStatus = mapStatus;
		}
	}
}
