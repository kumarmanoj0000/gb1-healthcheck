package com.gb1.healthcheck.domain.metrics;

public enum EnergyLevel {
	LOW("low"), MEDIUM_LOW("medium low"), MEDIUM("medium"), MEDIUM_HIGH("medium high"), HIGH("high");

	private String name;

	EnergyLevel(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
