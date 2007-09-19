package com.gb1.healthcheck.domain.nutrition;

public enum PreparationMethod {
	RAW("raw"), BOILED("boiled"), STEWED("stewed"), SAUTEED("sauteed"), GRILLED("grilled");

	private String name;

	PreparationMethod(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
