package com.gb1.healthcheck.domain.foods;

import java.util.HashSet;
import java.util.Set;

public class Foods {
	private Foods() {
	}

	public static SimpleFood water() {
		SimpleFood water = new SimpleFood();
		water.setId(1L);
		water.setName("water");
		water.setFoodGroup(FoodGroup.OTHERS);

		return water;
	}

	public static SimpleFood apple() {
		SimpleFood apple = new SimpleFood();
		apple.setId(2L);
		apple.setName("apple");
		apple.setFoodGroup(FoodGroup.FRUITS);
		apple.addNutrient(Nutrient.VITAMIN_C);

		return apple;
	}

	public static SimpleFood sugar() {
		SimpleFood sugar = new SimpleFood();
		sugar.setId(3L);
		sugar.setName("sugar");
		sugar.setFoodGroup(FoodGroup.OTHERS);

		return sugar;
	}

	public static SimpleFood pasta() {
		SimpleFood pasta = new SimpleFood();
		pasta.setId(4L);
		pasta.setName("pasta");
		pasta.setFoodGroup(FoodGroup.GRAINS);

		return pasta;
	}

	public static SimpleFood beef() {
		SimpleFood beef = new SimpleFood();
		beef.setId(5L);
		beef.setName("beef");
		beef.setFoodGroup(FoodGroup.MEAT_AND_SUBSTITUTES);
		beef.addNutrient(Nutrient.PROTEIN);

		return beef;
	}

	public static SimpleFood tomato() {
		SimpleFood tomato = new SimpleFood();
		tomato.setId(6L);
		tomato.setName("tomato");
		tomato.setFoodGroup(FoodGroup.FRUITS);
		tomato.addNutrient(Nutrient.VITAMIN_C);

		return tomato;
	}

	public static SimpleFood redGrape() {
		SimpleFood grape = new SimpleFood();
		grape.setId(7L);
		grape.setName("red grape");
		grape.setFoodGroup(FoodGroup.FRUITS);
		grape.addNutrient(Nutrient.VITAMIN_B);

		return grape;
	}

	public static SimpleFood alcohol() {
		SimpleFood alcohol = new SimpleFood();
		alcohol.setId(8L);
		alcohol.setName("alcohol");
		alcohol.setFoodGroup(FoodGroup.OTHERS);
		alcohol.addNutrient(Nutrient.ALCOHOL);

		return alcohol;
	}

	public static ComplexFood redWine() {
		ComplexFood wine = new ComplexFood();
		wine.setId(9L);
		wine.setName("red wine");
		wine.addIngredient(alcohol());
		wine.addIngredient(redGrape());

		return wine;
	}

	public static ComplexFood beefStock() {
		ComplexFood stock = new ComplexFood();
		stock.setId(10L);
		stock.setName("beef stock");
		stock.addIngredient(water());
		stock.addIngredient(beef());

		return stock;
	}

	public static ComplexFood spaghetti() {
		ComplexFood spag = new ComplexFood();
		spag.setId(11L);
		spag.setName("spaghetti");
		spag.addIngredient(tomato());
		spag.addIngredient(beef());
		spag.addIngredient(beefStock());
		spag.addIngredient(pasta());

		return spag;
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
