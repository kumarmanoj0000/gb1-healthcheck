package com.gb1.healthcheck.web.users;

import java.util.HashSet;
import java.util.Set;

import com.gb1.healthcheck.domain.users.Role;
import com.gb1.healthcheck.domain.users.UserRegistrationRequest;

public class StandardUserRegistrationRequest implements UserRegistrationRequest {
	private String login;
	private String email;
	private String password1;
	private String password2;
	private Set<Role> roles = new HashSet<Role>();

	public StandardUserRegistrationRequest() {
	}

	public StandardUserRegistrationRequest(Role role) {
		roles.add(role);
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
}
