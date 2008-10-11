package com.gb1.healthcheck.core;

public interface Validator<T, E extends Exception> {
	void validate(T t) throws E;
}
