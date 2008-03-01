package com.gb1.healthcheck.web.meals;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gb1.commons.dataaccess.IdentityHydrater;
import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.meals.PreparationMethod;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.foods.FoodService;
import com.gb1.healthcheck.services.meals.MealService;
import com.gb1.healthcheck.web.utils.HttpRequestUtils;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public abstract class MealActionSupport extends ActionSupport {
	private FoodService foodService;
	private MealService mealService;

	private List<Food> availableFoods = new LinkedList<Food>();

	protected MealActionSupport() {
	}

	protected void prepareSupport() {
		availableFoods.addAll(getFoodService().getSimpleFoods());
		availableFoods
				.addAll(getFoodService().getComplexFoods(new IdentityHydrater<ComplexFood>()));
		Collections.sort(availableFoods, new Food.ByNameComparator());
	}

	public String cancel() {
		return Action.SUCCESS;
	}

	public List<PreparationMethod> getAvailablePreparationMethods() {
		List<PreparationMethod> methods = Arrays.asList(PreparationMethod.values());
		return Collections.unmodifiableList(methods);
	}

	public List<Food> getAvailableFoods() {
		return Collections.unmodifiableList(availableFoods);
	}

	protected User getRequester(HttpServletRequest request) {
		return HttpRequestUtils.getUser(request);
	}

	protected FoodService getFoodService() {
		return foodService;
	}

	public void setFoodService(FoodService foodSvc) {
		this.foodService = foodSvc;
	}

	protected MealService getMealService() {
		return mealService;
	}

	public void setMealService(MealService mealSvc) {
		this.mealService = mealSvc;
	}
}
