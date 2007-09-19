package com.gb1.healthcheck.domain.nutrition;

public class Meals {
	private Meals() {
	}

	public static PreparedFood spaghettiDish() {
		return new PreparedFood(Foods.spaghetti(), PreparationMethod.STEWED);
	}

	public static PreparedFood redWineDrink() {
		return new PreparedFood(Foods.redWine(), PreparationMethod.RAW);
	}
}
