package com.gb1.healthcheck.domain.nutrition;

public class Foods {
	private Foods() {
	}

	public static SimpleFood water() {
		return new SimpleFood("water", Group.OTHERS);
	}

	public static SimpleFood apple() {
		return new SimpleFood("apple", Group.FRUITS).addNutrient(Nutrient.VITAMIN_C);
	}

	public static SimpleFood sugar() {
		return new SimpleFood("sugar", Group.OTHERS);
	}

	public static SimpleFood pasta() {
		return new SimpleFood("pasta", Group.GRAINS);
	}

	public static SimpleFood beef() {
		return new SimpleFood("beef", Group.MEAT_AND_SUBSTITUTES).addNutrient(Nutrient.PROTEIN);
	}

	public static SimpleFood tomato() {
		return new SimpleFood("tomato", Group.FRUITS).addNutrient(Nutrient.VITAMIN_C);
	}

	public static SimpleFood redGrape() {
		return new SimpleFood("red grapes", Group.FRUITS).addNutrient(Nutrient.VITAMIN_B);
	}

	public static SimpleFood alcohol() {
		return new SimpleFood("alcohol", Group.OTHERS).addNutrient(Nutrient.ALCOHOL);
	}

	public static ComplexFood redWine() {
		return new ComplexFood("red wine").addIngredient(redGrape()).addIngredient(alcohol());
	}

	public static ComplexFood beefStock() {
		return new ComplexFood("beef stock").addIngredient(water()).addIngredient(beef());
	}

	public static ComplexFood spaghetti() {
		return new ComplexFood("spaghetti").addIngredient(tomato()).addIngredient(beef())
				.addIngredient(beefStock()).addIngredient(pasta());
	}
}
