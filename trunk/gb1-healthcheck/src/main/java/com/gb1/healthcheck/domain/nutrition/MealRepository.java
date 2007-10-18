package com.gb1.healthcheck.domain.nutrition;

import java.util.Date;
import java.util.List;

public interface MealRepository {
	Meal loadMeal(Long mealId);

	List<Meal> loadMeals();

	List<Meal> findMealsByDateAndTime(Date dateAndTime);

	void saveMeal(Meal meal);
}
