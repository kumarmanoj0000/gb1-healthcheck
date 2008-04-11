package com.gb1.healthcheck.domain.users;

import java.io.Serializable;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component("passwordGenerator")
public class PasswordGeneratorImpl implements PasswordGenerator, Serializable {
	public PasswordGeneratorImpl() {
	}

	public String generatePassword(int length) {
		String pwd = RandomStringUtils.randomAlphanumeric(length);
		return pwd;
	}
}
