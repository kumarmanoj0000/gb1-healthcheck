package com.gb1.healthcheck.domain.metrics;

public enum IntestinalState {
	CRISIS("crisis"), HIGHLY_BLOATED("highly bloated"), BLOATED("bloated"), SLIGHTLY_BLOATED(
			"slightly bloated"), NORMAL("normal");

	private String name;

	IntestinalState(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
