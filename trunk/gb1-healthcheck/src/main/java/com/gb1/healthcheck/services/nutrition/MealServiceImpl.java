package com.gb1.healthcheck.services.nutrition;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gb1.healthcheck.domain.nutrition.Meal;
import com.gb1.healthcheck.domain.nutrition.MealException;
import com.gb1.healthcheck.domain.nutrition.MealPropertyProvider;
import com.gb1.healthcheck.domain.nutrition.MealRepository;
import com.gb1.healthcheck.domain.nutrition.MealValidator;

public class MealServiceImpl implements MealService {
	private MealRepository mealRepo;
	private MealValidator mealCreationValidator;

	public MealServiceImpl() {
	}

	@Transactional(readOnly = true)
	public List<Meal> getMealHistory() {
		List<Meal> mealHistory = mealRepo.loadMeals();
		return mealHistory;
	}

	@Transactional(rollbackFor = { RuntimeException.class, MealException.class })
	public void createMeal(MealPropertyProvider propertyProvider) throws MealException {
		Meal meal = new Meal(propertyProvider);
		mealCreationValidator.validate(meal);
		mealRepo.saveMeal(meal);
	}

	public void setMealRepository(MealRepository mealRepo) {
		this.mealRepo = mealRepo;
	}

	public void setMealCreationValidator(MealValidator validator) {
		this.mealCreationValidator = validator;
	}
}
