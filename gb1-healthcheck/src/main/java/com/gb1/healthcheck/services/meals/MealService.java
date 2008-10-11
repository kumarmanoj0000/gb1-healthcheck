package com.gb1.healthcheck.services.meals;

import java.util.List;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.MealException;
import com.gb1.healthcheck.domain.users.User;

public interface MealService {
	List<Meal> getMealHistory(User eater);

	Meal findMeal(Long mealId);

	void createMeal(Meal meal) throws MealException;

	void updateMeal(Meal meal) throws MealException;

	void deleteMeals(List<Long> mealIds);

	void notifyUsersOfMealInactivity();
}
