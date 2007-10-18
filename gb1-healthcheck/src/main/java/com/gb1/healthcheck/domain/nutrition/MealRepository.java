package com.gb1.healthcheck.domain.nutrition;

import java.util.Date;
import java.util.List;

public interface MealRepository {
	Meal loadMeal(Long mealId);

	List<Meal> loadMeals();

	List<Meal> findMealsByInstant(Date instant);

	void saveMeal(Meal meal);

	void deleteMeal(Long mealId);
}
