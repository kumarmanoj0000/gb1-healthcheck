package com.gb1.healthcheck.domain.nutrition;

public class ExposedSimpleFood extends SimpleFood {
	public ExposedSimpleFood(Long id, String name, Group group) {
		super(name, group);
		setId(id);
	}
}
