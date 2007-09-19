package com.gb1.healthcheck.domain.nutrition;

public enum Nutrient {
	VITAMIN_A("vitamin A"), VITAMIN_B("vitamin B"), VITAMIN_C("vitamin C"), FAT("fat"), CAFFEINE(
			"caffeine"), FRUCTOSE("fructose"), PROTEIN("protein"), ALCOHOL("alcohol");

	private String name;

	Nutrient(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
