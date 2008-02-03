package com.gb1.healthcheck.services.meals;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gb1.commons.dataaccess.Hydrater;
import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.MealCreationPropertyProviderAdapter;
import com.gb1.healthcheck.domain.meals.MealCreationRequest;
import com.gb1.healthcheck.domain.meals.MealException;
import com.gb1.healthcheck.domain.meals.MealRepository;
import com.gb1.healthcheck.domain.meals.MealUpdatePropertyProviderAdapter;
import com.gb1.healthcheck.domain.meals.MealUpdateRequest;
import com.gb1.healthcheck.domain.meals.MealValidator;
import com.gb1.healthcheck.domain.users.User;

@Transactional(rollbackFor = { RuntimeException.class, MealException.class })
public class MealServiceImpl implements MealService {
	private MealRepository mealRepo;
	private MealValidator mealCreationValidator;
	private MealValidator mealUpdateValidator;

	public MealServiceImpl() {
	}

	@Transactional(readOnly = true)
	public List<Meal> getMealHistory(User eater) {
		List<Meal> mealHistory = mealRepo.findMealsBy(eater);
		return mealHistory;
	}

	@Transactional(readOnly = true)
	public Meal loadMeal(Long mealId, Hydrater<Meal> hydrater) {
		Meal meal = mealRepo.loadMeal(mealId);
		hydrater.hydrate(meal);

		return meal;
	}

	public void createMeal(MealCreationRequest request) throws MealException {
		Meal meal = new Meal(createMealCreationPropertyProviderAdapter(request));
		mealCreationValidator.validate(meal);
		mealRepo.saveMeal(meal);
	}

	protected MealCreationPropertyProviderAdapter createMealCreationPropertyProviderAdapter(
			MealCreationRequest request) {
		return new MealCreationPropertyProviderAdapter(request);
	}

	public void updateMeal(Long mealId, MealUpdateRequest request) throws MealException {
		Meal meal = mealRepo.loadMeal(mealId);
		meal.update(new MealUpdatePropertyProviderAdapter(request));
		mealUpdateValidator.validate(meal);
	}

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
