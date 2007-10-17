package com.gb1.healthcheck.web.nutrition;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.gb1.commons.dataaccess.NullHydrater;
import com.gb1.healthcheck.domain.nutrition.ComplexFood;
import com.gb1.healthcheck.domain.nutrition.Food;
import com.gb1.healthcheck.domain.nutrition.MealAlreadyExistsException;
import com.gb1.healthcheck.domain.nutrition.MealException;
import com.gb1.healthcheck.domain.nutrition.PreparationMethod;
import com.gb1.healthcheck.services.nutrition.FoodService;
import com.gb1.healthcheck.services.nutrition.MealService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class CreateMealAction extends ActionSupport implements Preparable {
	private FoodService foodService;
	private MealService mealService;

	private List<Food> availableFoods = new LinkedList<Food>();
	private MealCreationRequest model = new MealCreationRequest();

	public CreateMealAction() {
	}

	public void prepare() {
		availableFoods.addAll(foodService.getSimpleFoods());
		availableFoods.addAll(foodService.getComplexFoods(new NullHydrater<ComplexFood>()));
		Collections.sort(availableFoods, new Food.ByNameComparator());
	}

	public String input() {
		return Action.SUCCESS;
	}

	public String cancel() {
		return Action.SUCCESS;
	}

	public String submit() {
		String result = Action.INPUT;

		try {
			mealService.createMeal(model);
			result = Action.SUCCESS;
		}
		catch (MealAlreadyExistsException e) {
			addFieldError("model.dateAndTime", "A meal was already recorded for this hour.");
		}
		catch (MealException e) {
			addActionError(e.getMessage());
		}

		return result;
	}

	public MealCreationRequest getModel() {
		return model;
	}

	public List<PreparationMethod> getAvailablePreparationMethods() {
		List<PreparationMethod> methods = Arrays.asList(PreparationMethod.values());
		return Collections.unmodifiableList(methods);
	}

	public List<Food> getAvailableFoods() {
		return Collections.unmodifiableList(availableFoods);
	}

	public void setFoodService(FoodService foodSvc) {
		this.foodService = foodSvc;
	}

	public void setMealService(MealService mealSvc) {
		this.mealService = mealSvc;
	}
}
