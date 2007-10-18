package com.gb1.healthcheck.services.nutrition;

import java.util.List;

import com.gb1.healthcheck.domain.nutrition.Meal;
import com.gb1.healthcheck.domain.nutrition.MealException;
import com.gb1.healthcheck.domain.nutrition.MealMutablePropertyProvider;
import com.gb1.healthcheck.domain.nutrition.MealPropertyProvider;

public interface MealService {
	/**
	 * Returns all recorded meals, sorted chronologically.
	 * 
	 * @return All meals sorted chronologically
	 */
	List<Meal> getMealHistory();

	/**
	 * Creates a new meal, based on the property provider.
	 * 
	 * @param propertyProvider The provider of meal properties
	 * @throws MealException When creation fails
	 */
	void createMeal(MealPropertyProvider propertyProvider) throws MealException;

	/**
	 * Updates an existing meal, based on the property provider.
	 * 
	 * @param mealId The ID of the meal to modify
	 * @param propertyProvider The provider of new meal properties
	 * @throws MealException When update fails
	 */
	void updateMeal(Long mealId, MealMutablePropertyProvider propertyProvider) throws MealException;

	/**
	 * Deletes the meal identified by the given ID.
	 * 
	 * @param mealId The ID of the meal to delete
	 */
	void deleteMeal(Long mealId);
}
