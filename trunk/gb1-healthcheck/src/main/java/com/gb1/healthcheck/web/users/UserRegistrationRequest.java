package com.gb1.healthcheck.web.users;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.gb1.healthcheck.domain.users.PasswordMismatchException;
import com.gb1.healthcheck.domain.users.Role;
import com.gb1.healthcheck.domain.users.UserPropertyProvider;

/**
 * A user registration request.
 * 
 * @author Guillaume Bilodeau
 */
public class UserRegistrationRequest implements UserPropertyProvider {
	private String login;
	private String email;
	private String password1;
	private String password2;
	private Set<Role> roles = new HashSet<Role>();

	public UserRegistrationRequest() {
	}

	public UserRegistrationRequest(Role role) {
		roles.add(role);
	}

	public Long getId() {
		// no ID to assign yet
		return null;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password1;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void validate() throws PasswordMismatchException {
		if (!StringUtils.equals(password1, password2)) {
			throw new PasswordMismatchException();
		}
	}
}
