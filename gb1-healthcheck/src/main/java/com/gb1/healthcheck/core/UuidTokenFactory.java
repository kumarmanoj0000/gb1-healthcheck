package com.gb1.healthcheck.core;

import java.util.UUID;

/**
 * A token factory that assigns a world-wide unique UUID to each generated token.
 * 
 * @author Guillaume Bilodeau
 */
public class UuidTokenFactory implements TokenFactory {
	public UuidTokenFactory() {
	}

	/**
	 * Creates a new token, assigning it a UUID string.
	 * 
	 * @return The newly created token
	 */
	public Token newToken() {
		String value = UUID.randomUUID().toString();
		Token token = new Token(value);
		return token;
	}
}
