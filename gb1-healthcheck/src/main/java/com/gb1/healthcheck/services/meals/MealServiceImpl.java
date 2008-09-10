package com.gb1.healthcheck.services.meals;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gb1.commons.dataaccess.Hydrater;
import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.MealException;
import com.gb1.healthcheck.domain.meals.MealInactivityNotifier;
import com.gb1.healthcheck.domain.meals.MealRepository;
import com.gb1.healthcheck.domain.meals.MealValidator;
import com.gb1.healthcheck.domain.users.User;

@Service("mealService")
@Transactional(rollbackFor = { RuntimeException.class, MealException.class })
public class MealServiceImpl implements MealService {
	private MealRepository mealRepo;
	private MealAssembler mealAssembler;
	private MealValidator mealCreationValidator;
	private MealValidator mealUpdateValidator;
	private MealInactivityNotifier mealInactivityNotifier;

	public MealServiceImpl() {
	}

	@Transactional(readOnly = true)
	public List<Meal> getMealHistory(User eater) {
		List<Meal> mealHistory = mealRepo.findMealsBy(eater);
		return mealHistory;
	}

	@Transactional(readOnly = true)
	public Meal getMeal(Long mealId, Hydrater<Meal> hydrater) {
		Meal meal = mealRepo.loadMeal(mealId);
		hydrater.hydrate(meal);

		return meal;
	}

	public void createMeal(MealCreationRequest request) throws MealException {
		Meal meal = mealAssembler.createMeal(request);
		mealCreationValidator.validate(meal);
		mealRepo.saveMeal(meal);
	}

	public void updateMeal(MealUpdateRequest request) throws MealException {
		Meal meal = mealRepo.loadMeal(request.getMealId());
		mealAssembler.updateMeal(meal, request);
		mealUpdateValidator.validate(meal);
	}

	public void deleteMeals(Set<Long> mealIds) {
		mealRepo.deleteMeals(mealIds);
	}

	public void notifyUsersOfMealInactivity() {
		mealInactivityNotifier.notifyUsersOfMealInactivity();
	}

	@Resource
	public void setMealRepository(MealRepository mealRepo) {
		this.mealRepo = mealRepo;
	}

	@Resource
	public void setMealAssembler(MealAssembler mealAssembler) {
		this.mealAssembler = mealAssembler;
	}

	@Resource
	public void setMealCreationValidator(MealValidator validator) {
		this.mealCreationValidator = validator;
	}

	@Resource
	public void setMealUpdateValidator(MealValidator validator) {
		this.mealUpdateValidator = validator;
	}

	@Resource
	public void setMealInactivityNotifier(MealInactivityNotifier notifier) {
		this.mealInactivityNotifier = notifier;
	}
}
