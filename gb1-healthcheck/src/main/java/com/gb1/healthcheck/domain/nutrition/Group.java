package com.gb1.healthcheck.domain.nutrition;

public enum Group {
	FRUITS("fruits"), VEGETABLES("vegetables"), DAIRY_PRODUCTS("dairy products"), MEAT_AND_SUBSTITUTES(
			"meat and substitutes"), GRAINS("grains"), OTHERS("others");

	private String name;

	Group(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
