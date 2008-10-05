package com.gb1.healthcheck.services;

public class IdentityHydrater<E> implements Hydrater<E> {
	public IdentityHydrater() {
	}

	public E hydrate(E e) {
		return e;
	}
}
