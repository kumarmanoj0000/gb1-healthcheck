package com.gb1.healthcheck.domain.nutrition;

public class ExposedComplexFood extends ComplexFood {
	public ExposedComplexFood(Long id, String name) {
		super(name);
		setId(id);
	}
}
