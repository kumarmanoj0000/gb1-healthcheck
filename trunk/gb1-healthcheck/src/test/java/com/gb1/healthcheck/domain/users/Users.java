package com.gb1.healthcheck.domain.users;

import java.util.HashSet;
import java.util.Set;

public class Users {
	private Users() {
	}

	public static User gb() {
		User gb = new User();
		gb.setId(1L);
		gb.setLogin("gbilodeau");
		gb.setPassword("1");
		gb.setEmail("gbilodeau@yahoo.com");

		return gb;
	}

	public static User lg() {
		User lg = new User();
		lg.setId(2L);
		lg.setLogin("lgolovina");
		lg.setPassword("1");
		lg.setEmail("lgolovina@yahoo.fr");

		return lg;
	}

	public static Set<User> all() {
		Set<User> all = new HashSet<User>();
		all.add(Users.gb());
		all.add(Users.lg());

		return all;
	}
}
