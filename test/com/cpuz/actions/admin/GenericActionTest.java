package com.cpuz.actions.admin;

import java.util.ArrayList;
import java.util.HashMap;
import com.cpuz.exceptions.UserException;
import com.cpuz.st2.beans.ControlParams;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Clase de test para la clase abstract GenericAction
 * @author RAFAEL FERRUZ
 */
public class GenericActionTest {
	
	private GenericActionImpl instance;

	public GenericActionTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
		instance = createGenericAction();
	}

	private GenericActionImpl createGenericAction() {
		GenericActionImpl genericAction = new GenericActionImpl();
		return genericAction;
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of execute method, of class GenericAction.
	 */
	@Test
	public void testExecute() {
		System.out.println("execute");
		String expResult = "error";
		String result = instance.execute();
		assertEquals(expResult, result);
	}

	/**
	 * Test of validate method, of class GenericAction.
	 */
	@Test
	public void testValidate() {
		System.out.println("validate");
		instance.validate();
	}

		/**
	 * Test of getControl method, of class Role.
	 */
	@Test
	public void testGetControl() {
		System.out.println("getControl");
		ControlParams result = instance.getControl();
		assertNotNull("control NO debería ser null", result);
	}

	/**
	 * Test of setControl method, of class Role.
	 */
	@Test
	public void testSetControl() {
		System.out.println("setControl");
		ControlParams newControl=new ControlParams();
		newControl.setRunAction("SettingControl");
		instance.setControl(newControl);
		assertEquals("control.runAction debería ser 'SettingControl'","SettingControl", instance.getControl().getRunAction());
	}

		/**
	 * Test of getMapStatus method, of class Role.
	 */
	@Test
	public void testGetMapStatus() {
		System.out.println("getMapStatus");
		Map<Integer,String> result = instance.getMapStatus();
		assertTrue("mapStatus debería ser empty", result.isEmpty());
	}

	/**
	 * Test of setControl method, of class Role.
	 */
	@Test
	public void testSetMapStatus() {
		System.out.println("setMapStatus");
		instance.setMapStatus(new HashMap<Integer,String>());
		assertTrue("mapStatus debería ser empty", instance.getMapStatus().isEmpty());
	}
		/**
	 * Test of getDataEdit method, of class Role.
	 */
	@Test
	public void testGetDataEdit() {
		System.out.println("getDataEdit");
		Object result = instance.getDataEdit();
		assertNull("dataEdit debería ser null", result);
	}

	/**
	 * Test of setDataEdit method, of class Role.
	 */
	@Test
	public void testSetDataEdit() {
		System.out.println("setDataEdit");
		instance.setDataEdit(new Object());
		assertEquals("dataEdit debería ser Object", "java.lang.Object",instance.getDataEdit().getClass().getName());
	}

		/**
	 * Test of getDataList method, of class Role.
	 */
	@Test
	public void testGetDataList() {
		System.out.println("getDataList");
		List<Object> result = instance.getDataList();
		assertTrue("dataList debería ser empty", result.isEmpty());
	}

	/**
	 * Test of setDataList method, of class Role.
	 */
	@Test
	public void testSetDataList() {
		System.out.println("setDataList");
		instance.setDataList(new ArrayList<>());
		assertTrue("dataList debería ser empty", instance.getDataList().isEmpty());
	}
		/**
	 * Test of getSelec1 method, of class Role.
	 */
	@Test
	public void testGetSelec1() {
		System.out.println("getSelec1");
		String result = instance.getSelec1();
		assertNull("Selec1 debería ser null", result);
	}

	/**
	 * Test of setSelec1 method, of class Role.
	 */
	@Test
	public void testSetSelec1() {
		System.out.println("setSelec1");
		instance.setSelec1("selec1");
		assertEquals("Selec1 debería ser 'selec1'", "selec1",instance.getSelec1());
	}

	public class GenericActionImpl extends GenericAction {

		public String objectNew() {
			return "";
		}

		public String objectEdit() throws SQLException {
			return "";
		}

		public String objectSaveNew() throws SQLException, UserException {
			return "";
		}

		public String objectSaveEdit() throws SQLException, UserException {
			return "";
		}

		public String objectDelete() throws SQLException {
			return "";
		}

		public String objectList() throws SQLException {
			return "";
		}

		public Object getDataEdit() {
			return dataEdit;
		}

		public Object getDataService() {
			return null;
		}

		public void setDataService(Object dataService) {
		}
	}
}
