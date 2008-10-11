package com.gb1.healthcheck.services.meals;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gb1.healthcheck.core.Validator;
import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.MealException;
import com.gb1.healthcheck.domain.meals.MealInactivityNotifier;
import com.gb1.healthcheck.domain.meals.MealRepository;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.Hydrater;

@Service("mealService")
@Transactional(rollbackFor = { RuntimeException.class, MealException.class })
public class MealServiceImpl implements MealService {
	@Resource
	protected MealRepository mealRepo;

	@Resource
	protected Validator<Meal, MealException> mealCreationValidator;

	@Resource
	protected Validator<Meal, MealException> mealUpdateValidator;

	@Resource
	protected MealInactivityNotifier mealInactivityNotifier;

	public MealServiceImpl() {
	}

	@Transactional(readOnly = true)
	public List<Meal> getMealHistory(User eater) {
		return mealRepo.findMealsBy(eater);
	}

	@Transactional(readOnly = true)
	public Meal getMeal(Long mealId, Hydrater<Meal> hydrater) {
		Meal meal = mealRepo.loadMeal(mealId);
		return hydrater.hydrate(meal);
	}

	public void createMeal(Meal meal) throws MealException {
		mealCreationValidator.validate(meal);
		mealRepo.persistMeal(meal);
	}

	public void updateMeal(Meal meal) throws MealException {
		mealUpdateValidator.validate(meal);
		mealRepo.mergeMeal(meal);
	}

	public void deleteMeals(Set<Long> mealIds) {
		for (Long mealId : mealIds) {
			mealRepo.deleteMeal(mealRepo.loadMeal(mealId));
		}
	}

	public void notifyUsersOfMealInactivity() {
		mealInactivityNotifier.notifyUsersOfMealInactivity();
	}
}
