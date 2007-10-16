package com.gb1.healthcheck.services.nutrition;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gb1.healthcheck.domain.nutrition.Meal;
import com.gb1.healthcheck.domain.nutrition.MealRepository;

public class MealServiceImpl implements MealService {
	private MealRepository mealRepo;

	public MealServiceImpl() {
	}

	@Transactional(readOnly = true)
	public List<Meal> getMealHistory() {
		List<Meal> mealHistory = mealRepo.loadMeals();
		return mealHistory;
	}

	public void setMealRepository(MealRepository mealRepo) {
		this.mealRepo = mealRepo;
	}
}
