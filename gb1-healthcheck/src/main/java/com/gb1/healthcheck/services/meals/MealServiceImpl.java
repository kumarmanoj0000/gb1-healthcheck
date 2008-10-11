package com.gb1.healthcheck.services.meals;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gb1.healthcheck.core.Validator;
import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.MealException;
import com.gb1.healthcheck.domain.meals.MealInactivityNotifier;
import com.gb1.healthcheck.domain.meals.MealRepository;
import com.gb1.healthcheck.domain.users.User;

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
		return mealRepo.findMeals(eater);
	}

	@Transactional(readOnly = true)
	public Meal findMeal(Long mealId) {
		return mealRepo.findMeal(mealId);
	}

	public void createMeal(Meal meal) throws MealException {
		mealCreationValidator.validate(meal);
		mealRepo.persist(meal);
	}

	public void updateMeal(Meal meal) throws MealException {
		mealUpdateValidator.validate(meal);
		mealRepo.merge(meal);
	}

	public void deleteMeals(List<Long> mealIds) {
		for (Long mealId : mealIds) {
			mealRepo.delete(mealRepo.findMeal(mealId));
		}
	}

	public void notifyUsersOfMealInactivity() {
		mealInactivityNotifier.notifyUsersOfMealInactivity();
	}
}
