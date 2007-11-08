package com.gb1.healthcheck.domain.foods;

import java.util.HashSet;
import java.util.Set;

public class Foods {
	private Foods() {
	}

	public static SimpleFood water() {
		return new SimpleFood(1L, "water", FoodGroup.OTHERS);
	}

	public static SimpleFood apple() {
		return new SimpleFood(2L, "apple", FoodGroup.FRUITS).addNutrient(Nutrient.VITAMIN_C);
	}

	public static SimpleFood sugar() {
		return new SimpleFood(3L, "sugar", FoodGroup.OTHERS);
	}

	public static SimpleFood pasta() {
		return new SimpleFood(4L, "pasta", FoodGroup.GRAINS);
	}

	public static SimpleFood beef() {
		return new SimpleFood(5L, "beef", FoodGroup.MEAT_AND_SUBSTITUTES)
				.addNutrient(Nutrient.PROTEIN);
	}

	public static SimpleFood tomato() {
		return new SimpleFood(6L, "tomato", FoodGroup.FRUITS).addNutrient(Nutrient.VITAMIN_C);
	}

	public static SimpleFood redGrape() {
		return new SimpleFood(7L, "red grape", FoodGroup.FRUITS).addNutrient(Nutrient.VITAMIN_B);
	}

	public static SimpleFood alcohol() {
		return new SimpleFood(8L, "alcohol", FoodGroup.OTHERS).addNutrient(Nutrient.ALCOHOL);
	}

	public static ComplexFood redWine() {
		return new ComplexFood(9L, "red wine").addIngredient(redGrape()).addIngredient(alcohol());
	}

	public static ComplexFood beefStock() {
		return new ComplexFood(10L, "beef stock").addIngredient(water()).addIngredient(beef());
	}

	public static ComplexFood spaghetti() {
		return new ComplexFood(11L, "spaghetti").addIngredient(tomato()).addIngredient(beef())
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
