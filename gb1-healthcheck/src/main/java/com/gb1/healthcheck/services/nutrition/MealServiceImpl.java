package com.gb1.healthcheck.services.nutrition;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gb1.commons.dataaccess.Hydrater;
import com.gb1.healthcheck.domain.nutrition.Meal;
import com.gb1.healthcheck.domain.nutrition.MealCreationRequest;
import com.gb1.healthcheck.domain.nutrition.MealException;
import com.gb1.healthcheck.domain.nutrition.MealMutablePropertyProviderAdapter;
import com.gb1.healthcheck.domain.nutrition.MealPropertyProviderAdapter;
import com.gb1.healthcheck.domain.nutrition.MealRepository;
import com.gb1.healthcheck.domain.nutrition.MealUpdateRequest;
import com.gb1.healthcheck.domain.nutrition.MealValidator;

public class MealServiceImpl implements MealService {
	private MealRepository mealRepo;
	private MealValidator mealCreationValidator;
	private MealValidator mealUpdateValidator;

	public MealServiceImpl() {
	}

	@Transactional(readOnly = true)
	public List<Meal> getMealHistory() {
		List<Meal> mealHistory = mealRepo.loadMeals();
		return mealHistory;
	}

	@Transactional(readOnly = true)
	public Meal loadMeal(Long mealId, Hydrater<Meal> hydrater) {
		Meal meal = mealRepo.loadMeal(mealId);
		hydrater.hydrate(meal);

		return meal;
	}

	@Transactional(rollbackFor = { RuntimeException.class, MealException.class })
	public void createMeal(MealCreationRequest request) throws MealException {
		Meal meal = new Meal(new MealPropertyProviderAdapter(request));
		mealCreationValidator.validate(meal);
		mealRepo.saveMeal(meal);
	}

	@Transactional(rollbackFor = { RuntimeException.class, MealException.class })
	public void updateMeal(Long mealId, MealUpdateRequest request) throws MealException {
		Meal meal = mealRepo.loadMeal(mealId);
		meal.update(new MealMutablePropertyProviderAdapter(request));
		mealUpdateValidator.validate(meal);
	}

	@Transactional(rollbackFor = { RuntimeException.class, MealException.class })
	public void deleteMeal(Long mealId) {
		mealRepo.deleteMeal(mealId);
	}

	public void setMealRepository(MealRepository mealRepo) {
		this.mealRepo = mealRepo;
	}

	public void setMealCreationValidator(MealValidator validator) {
		this.mealCreationValidator = validator;
	}

	public void setMealUpdateValidator(MealValidator validator) {
		this.mealUpdateValidator = validator;
	}
}
