package com.gb1.healthcheck.domain.nutrition;

import java.util.HashSet;
import java.util.Set;

public class Foods {
	private Foods() {
	}

	public static SimpleFood water() {
		return new ExposedSimpleFood(1L, "water", Group.OTHERS);
	}

	public static SimpleFood apple() {
		return new ExposedSimpleFood(2L, "apple", Group.FRUITS).addNutrient(Nutrient.VITAMIN_C);
	}

	public static SimpleFood sugar() {
		return new ExposedSimpleFood(3L, "sugar", Group.OTHERS);
	}

	public static SimpleFood pasta() {
		return new ExposedSimpleFood(4L, "pasta", Group.GRAINS);
	}

	public static SimpleFood beef() {
		return new ExposedSimpleFood(5L, "beef", Group.MEAT_AND_SUBSTITUTES)
				.addNutrient(Nutrient.PROTEIN);
	}

	public static SimpleFood tomato() {
		return new ExposedSimpleFood(6L, "tomato", Group.FRUITS).addNutrient(Nutrient.VITAMIN_C);
	}

	public static SimpleFood redGrape() {
		return new ExposedSimpleFood(7L, "red grape", Group.FRUITS).addNutrient(Nutrient.VITAMIN_B);
	}

	public static SimpleFood alcohol() {
		return new ExposedSimpleFood(8L, "alcohol", Group.OTHERS).addNutrient(Nutrient.ALCOHOL);
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

	public static Set<SimpleFood> allSimpleFoods() {
		Set<SimpleFood> allSimpleFoods = new HashSet<SimpleFood>();
		allSimpleFoods.add(water());
		allSimpleFoods.add(apple());
		allSimpleFoods.add(sugar());
		allSimpleFoods.add(pasta());
		allSimpleFoods.add(beef());
		allSimpleFoods.add(tomato());
		allSimpleFoods.add(redGrape());
		allSimpleFoods.add(alcohol());

		return allSimpleFoods;
	}

	public static Set<ComplexFood> allComplexFoods() {
		Set<ComplexFood> allComplexFoods = new HashSet<ComplexFood>();
		allComplexFoods.add(redWine());
		allComplexFoods.add(beefStock());
		allComplexFoods.add(spaghetti());

		return allComplexFoods;
	}
}
