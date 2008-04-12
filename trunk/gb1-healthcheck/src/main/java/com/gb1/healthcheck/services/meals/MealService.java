package com.gb1.healthcheck.services.meals;

import java.util.List;
import java.util.Set;

import com.gb1.commons.dataaccess.Hydrater;
import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.MealException;
import com.gb1.healthcheck.domain.users.User;

public interface MealService {
	/**
	 * Returns all recorded meals eaten by a given user, sorted chronologically.
	 * 
	 * @return All meals eaten by a user, sorted chronologically
	 */
	List<Meal> getMealHistory(User eater);

	/**
	 * Loads an existing meal identified by a given ID.
	 * 
	 * @param mealId The ID of the meal to load
	 * @param hydrater The callback to hydrate meal associations
	 * @return The corresponding meal; null if non existent
	 */
	Meal loadMeal(Long mealId, Hydrater<Meal> hydrater);

	/**
	 * Creates a new meal, based on a creation request.
	 * 
	 * @param request The creation request
	 * @throws MealException When creation fails
	 */
	void createMeal(MealCreationRequest request) throws MealException;

	/**
	 * Updates an existing meal, based on an update request.
	 * 
	 * @param request The update request
	 * @throws MealException When update fails
	 */
	void updateMeal(MealUpdateRequest request) throws MealException;

	/**
	 * Deletes the meals identified by the given IDs.
	 * 
	 * @param mealIds The IDs of the meals to delete
	 */
	void deleteMeals(Set<Long> mealIds);

	void notifyInactiveUsers();
}
