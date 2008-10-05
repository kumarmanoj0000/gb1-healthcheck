package com.gb1.healthcheck.services;

/**
 * A callback used to explicitly load an object's associations, i.e. to "hydrate" an object. This
 * may be necessary for objects whose assocations have been marked as begin lazy-loaded and need to
 * be used in a context where the database connection (or Hibernate Session or JPA EntityManager)
 * has been closed, such as in a web controller or a view.
 * 
 * @author Guillaume Bilodeau
 */
public interface Hydrater<E> {
	E hydrate(E e);
}
