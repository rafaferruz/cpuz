package com.cpuz.domain;

import static org.junit.Assert.*;
import org.junit.*;

/**
 * Test de la clase UserRole.
 */
public class UserRoleTest {

	private UserRole userRole;

	public UserRoleTest() {
	}

	@Before
	public void instantiate() {
		userRole = createUserRole();
	}

	private UserRole createUserRole() {
		UserRole userRole = new UserRole();
		return userRole;
	}

	/**
	 * Se prueba el método getStatus() de un objeto UserRole.
	 *
	 */
	@Test
	public void getStatusTest() {
		assertNull("userRole.status debería ser null", userRole.getStatus());
	}

	/**
	 * Se prueba el método setStatus() de un objeto UserRole.
	 *
	 */
	@Test
	public void setStatusTest() {
		userRole.setStatus(123);
		assertEquals("userRole.status debería 123", "123", userRole.getStatus().toString());
	}

	/**
	 * Se prueba el método getId() de un objeto UserRole.
	 *
	 */
	@Test
	public void getIdTest() {
		assertNull("userRole.id debería ser null", userRole.getId());
		userRole.setId(1);
		assertEquals("userRole.id debería 1", "1", userRole.getId().toString());
	}

	/**
	 * Se prueba el método setId() de un objeto UserRole.
	 *
	 */
	@Test
	public void setIdTest() {
		userRole.setId(123);
		assertEquals("userRole.id debería 123", "123", userRole.getId().toString());
	}

	/**
	 * Se prueba el método getDescription() de un objeto UserRole.
	 *
	 */
	@Test
	public void getDescriptionTest() {
		assertNull("userRole.description debería ser null", userRole.getDescription());
	}

	/**
	 * Se prueba el método setDescription() de un objeto UserRole.
	 *
	 */
	@Test
	public void setDescriptionTest() {
		userRole.setDescription(null);
		assertNull("userRole.description debería ser null", userRole.getDescription());
		userRole.setDescription("some name");
		assertEquals("userRole.description debería ser some name", "some name", userRole.getDescription());
	}

	/**
	 * Se prueba el método getUser() de un objeto UserRole.
	 *
	 */
	@Test
	public void getUserTest() {
		assertNull("userRole.user debería ser null", userRole.getUser());
	}

	/**
	 * Se prueba el método setUser() de un objeto UserRole.
	 *
	 */
	@Test
	public void setUserTest() {
		userRole.setUser(null);
		assertNull("userRole.user debería ser null", userRole.getUser());
		userRole.setUser("some user");
		assertEquals("userRole.user debería ser some user", "some user", userRole.getUser());
	}

	/**
	 * Se prueba el método getRole() de un objeto UserRole.
	 *
	 */
	@Test
	public void getRoleTest() {
		assertNull("userRole.role debería ser null", userRole.getRole());
	}

	/**
	 * Se prueba el método setRole() de un objeto UserRole.
	 *
	 */
	@Test
	public void setRoleTest() {
		userRole.setRole(null);
		assertNull("userRole.role debería ser null", userRole.getRole());
		userRole.setRole("some role");
		assertEquals("userRole.role debería ser some role", "some role", userRole.getRole());
	}

	/**
	 * Se prueba el método equals() de un objeto UserRole.
	 *
	 */
	@Test
	public void equals_whenNull() {
		UserRole obj = null;
		assertFalse("método equals debería ser false", userRole.equals(obj));
	}
	@Test
	public void equals_whenOtherClass() {
		Role obj = new Role();
		assertFalse("método equals debería ser false", userRole.equals(obj));
	}
	@Test
	public void equals_whenUserDisTinct() {
		userRole.setUser("user1");
		userRole.setRole("role1");
		UserRole obj = new UserRole();
		obj.setUser("user2");
		obj.setRole("role1");
		assertFalse("método equals debería ser false", userRole.equals(obj));
	}
	@Test
	public void equals_whenRoleDisTinct() {
		userRole.setUser("user1");
		userRole.setRole("role1");
		UserRole obj = new UserRole();
		obj.setUser("user1");
		obj.setRole("role2");
		assertFalse("método equals debería ser false", userRole.equals(obj));
	}
	@Test
	public void equals_whenOK() {
		userRole.setUser("user1");
		userRole.setRole("role1");
		UserRole obj = new UserRole();
		obj.setUser("user1");
		obj.setRole("role1");
		assertTrue("método equals debería ser false", userRole.equals(obj));
	}

}
