package com.cpuz.domain;

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
/*
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public List<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}
	

* */
}
