package com.gb1.healthcheck.core;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * A token containing a specific value.
 * 
 * @author Guillaume Bilodeau
 */
public class Token implements Serializable {
	/**
	 * The token's value
	 */
	private String value;

	/**
	 * Package-private constructor for persistence
	 */
	Token() {
	}

	/**
	 * Creates a new token based on the given value.
	 * 
	 * @param value The new token's value
	 */
	public Token(String value) {
		this.value = value;
	}

	/**
	 * Returns the token's value.
	 * 
	 * @return The token's value
	 */
	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Token)) {
			return false;
		}

		Token that = (Token) o;
		EqualsBuilder builder = new EqualsBuilder().append(this.value, that.value);

		return builder.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder().append(this.value);
		return builder.toHashCode();
	}
}
