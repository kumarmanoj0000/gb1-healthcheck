package com.gb1.healthcheck.domain.meals;

import java.util.Date;
import java.util.List;

import com.gb1.healthcheck.domain.users.User;

public interface MealRepository {
	Meal findMeal(Long mealId);

	List<Meal> findMeals(User eater);

	List<Meal> findMeals(User eater, Date instant);

	Meal findLastMeal(User eater);

	void persist(Meal meal);

	void merge(Meal meal);

	void delete(Meal meal);
}
