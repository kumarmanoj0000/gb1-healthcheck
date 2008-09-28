package com.gb1.healthcheck.domain.foods;

import java.util.HashSet;
import java.util.Set;

public class Foods {
	private Foods() {
	}

	public static SimpleFood water() {
		return ((SimpleFood) new SimpleFood().setId(1L).setName("water"))
				.setFoodGroup(FoodGroup.OTHERS);
	}

	public static SimpleFood apple() {
		return ((SimpleFood) new SimpleFood().setId(2L).setName("apple")).setFoodGroup(
				FoodGroup.FRUITS).addNutrient(Nutrient.VITAMIN_C);
	}

	public static SimpleFood sugar() {
		return ((SimpleFood) new SimpleFood().setId(3L).setName("sugar"))
				.setFoodGroup(FoodGroup.OTHERS);
	}

	public static SimpleFood pasta() {
		return ((SimpleFood) new SimpleFood().setId(4L).setName("pasta"))
				.setFoodGroup(FoodGroup.GRAINS);
	}

	public static SimpleFood beef() {
		return ((SimpleFood) new SimpleFood().setId(5L).setName("beef")).setFoodGroup(
				FoodGroup.MEAT_AND_SUBSTITUTES).addNutrient(Nutrient.PROTEIN);
	}

	public static SimpleFood tomato() {
		return ((SimpleFood) new SimpleFood().setId(6L).setName("tomato")).setFoodGroup(
				FoodGroup.FRUITS).addNutrient(Nutrient.VITAMIN_C);
	}

	public static SimpleFood redGrape() {
		return ((SimpleFood) new SimpleFood().setId(7L).setName("red grape")).setFoodGroup(
				FoodGroup.FRUITS).addNutrient(Nutrient.VITAMIN_B);
	}

	public static SimpleFood alcohol() {
		return ((SimpleFood) new SimpleFood().setId(8L).setName("alcohol")).setFoodGroup(
				FoodGroup.OTHERS).addNutrient(Nutrient.ALCOHOL);
	}

	public static ComplexFood redWine() {
		return ((ComplexFood) new ComplexFood().setId(9L).setName("red wine")).addIngredient(
				redGrape()).addIngredient(alcohol());
	}

	public static ComplexFood beefStock() {
		return ((ComplexFood) new ComplexFood().setId(10L).setName("beef stock")).addIngredient(
				water()).addIngredient(beef());
	}

	public static ComplexFood spaghetti() {
		return ((ComplexFood) new ComplexFood().setId(11L).setName("spaghetti")).addIngredient(
				tomato()).addIngredient(beef()).addIngredient(beefStock()).addIngredient(pasta());
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
