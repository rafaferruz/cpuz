package com.cpuz.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.*;

/**
 * Test de la clase User.
 */
public class UserTest {

	private User user;

	public UserTest() {
	}

	@Before
	public void instantiate() {
		user = createUser();
	}

	private User createUser() {
		User user = new User();
		return user;
	}

	/**
	 * Se prueba el método getEmail() de un objeto User.
	 *
	 */
	@Test
	public void getEmailTest() {
		assertNull("user.email debería ser null", user.getEmail());
	}

	/**
	 * Se prueba el método setEmail() de un objeto User.
	 *
	 */
	@Test
	public void setEmailTest() {
		user.setEmail(null);
		assertNull("user.email debería ser null", user.getEmail());
		user.setEmail("something");
		assertEquals("user.email debería ser something", "something", user.getEmail());
	}

	/**
	 * Se prueba el método getStatus() de un objeto User.
	 *
	 */
	@Test
	public void getStatusTest() {
		assertNotNull("user.status debería ser 0", user.getStatus());
		assertEquals("user.status debería 0", "0", user.getStatus().toString());
	}

	/**
	 * Se prueba el método setStatus() de un objeto User.
	 *
	 */
	@Test
	public void setStatusTest() {
	  user.setStatus(123);
		assertEquals("user.status debería 123", "123", user.getStatus().toString());
	}
	/**
	 * Se prueba el método getDate() de un objeto User.
	 *
	 */
	@Test
	public void getDateTest() {
		assertNull("user.date debería ser null", user.getDate());
		user.setDate(new Date());
		assertEquals("user.date debería igual a today", (new Date()).toString(), user.getDate().toString());
	}
	/**
	 * Se prueba el método setDate() de un objeto User.
	 *
	 */
	@Test
	public void setDateTest() {
		user.setDate(new Date(1000000000));
		assertEquals("user.date debería igual a 01/01/1970", (new Date(1000000000)).toString(), user.getDate().toString());
	}


	/**
	 * Se prueba el método getId() de un objeto User.
	 *
	 */
	@Test
	public void getIdTest() {
		assertNull("user.id debería ser null", user.getId());
		user.setId(1);
		assertEquals("user.id debería 1", "1", user.getId().toString());
	}

	/**
	 * Se prueba el método setId() de un objeto User.
	 *
	 */
	@Test
	public void setIdTest() {
	  user.setId(123);
		assertEquals("user.id debería 123", "123", user.getId().toString());
	}
	/**
	 * Se prueba el método getName() de un objeto User.
	 *
	 */
	@Test
	public void getNameTest() {
		assertNull("user.name debería ser null", user.getName());
	}

	/**
	 * Se prueba el método setName() de un objeto User.
	 *
	 */
	@Test
	public void setNameTest() {
		user.setName(null);
		assertNull("user.name debería ser null", user.getName());
		user.setName("some name");
		assertEquals("user.name debería ser some name", "some name", user.getName());
	}

	/**
	 * Se prueba el método getPassword() de un objeto User.
	 *
	 */
	@Test
	public void getPasswordTest() {
		assertNull("user.password debería ser null", user.getPassword());
	}

	/**
	 * Se prueba el método setPassword() de un objeto User.
	 *
	 */
	@Test
	public void setPasswordTest() {
		user.setPassword(null);
		assertNull("user.password debería ser null", user.getPassword());
		user.setPassword("some_pw");
		assertEquals("user.password debería ser some_pw", "some_pw", user.getPassword());
	}
	/**
	 * Se prueba el método getCategory() de un objeto User.
	 *
	 */
	@Test
	public void getCategoryTest() {
		assertNotNull("user.category debería ser 0", user.getCategory());
		assertEquals("user.category debería 0", "0", user.getCategory().toString());
	}

	/**
	 * Se prueba el método setCategory() de un objeto User.
	 *
	 */
	@Test
	public void setCategoryTest() {
	  user.setCategory(123);
		assertEquals("user.category debería 123", "123", user.getCategory().toString());
	}
	/**
	 * Se prueba el método getUser() de un objeto User.
	 *
	 */
	@Test
	public void getUserTest() {
		assertNull("user.user debería ser null", user.getUser());
	}

	/**
	 * Se prueba el método setUser() de un objeto User.
	 *
	 */
	@Test
	public void setUserTest() {
		user.setUser(null);
		assertNull("user.user debería ser null", user.getUser());
		user.setUser("some user");
		assertEquals("user.user debería ser some user", "some user", user.getUser());
	}

	/**
	 * Se prueba el método getRoles() de un objeto User.
	 *
	 */
	@Test
	public void getRolesTest() {
		assertTrue("user.roles debería ser Empty", user.getRoles().isEmpty());
	}

	/**
	 * Se prueba el método setRoles() de un objeto User.
	 *
	 */
	@Test
	public void setRolesTest() {
		List<UserRole> roles=new ArrayList<>();
		roles.add(new UserRole());
		user.setRoles(roles);
		assertEquals("user.roles debería tener 1 UserRole","1", Integer.toString(user.getRoles().size()));
		user.setRoles(null);
		assertNull("user.roles debería ser null",user.getRoles());
	}
}
