package com.gb1.healthcheck.domain.meals;

import java.util.Date;
import java.util.List;

import com.gb1.healthcheck.domain.users.User;

public interface MealRepository {
	Meal loadMeal(Long mealId);

	List<Meal> findMealsBy(User eater);

	List<Meal> findMealsBy(User eater, Date instant);

	void saveMeal(Meal meal);

	void deleteMeal(Meal meal);

	Meal getLastMealBy(User eater);
}
