package com.gb1.commons.tokens;

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
