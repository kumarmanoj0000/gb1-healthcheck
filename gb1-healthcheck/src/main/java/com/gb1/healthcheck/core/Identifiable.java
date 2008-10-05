package com.gb1.healthcheck.core;

/**
 * Defines the interface of identifiable objects.
 * 
 * @author Guillaume Bilodeau
 */
public interface Identifiable {
	/**
	 * Returns the object's unique ID.
	 * 
	 * @return The object's unique ID
	 */
	Long getId();
}
