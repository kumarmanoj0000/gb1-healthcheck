package com.gb1.healthcheck.core;

import java.util.UUID;

import org.springframework.stereotype.Component;

/**
 * A token factory that assigns a world-wide unique UUID to each generated token.
 * 
 * @author Guillaume Bilodeau
 */
@Component("tokenFactory")
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
		return new Token(value);
	}
}
