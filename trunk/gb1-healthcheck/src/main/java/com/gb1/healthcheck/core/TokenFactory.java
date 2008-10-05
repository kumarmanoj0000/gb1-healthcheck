package com.gb1.healthcheck.core;

/**
 * A factory that creates new tokens.
 * 
 * @author Guillaume Bilodeau
 */
public interface TokenFactory {
	/**
	 * Creates a new token.
	 * 
	 * @return The newly created token.
	 */
	Token newToken();
}
