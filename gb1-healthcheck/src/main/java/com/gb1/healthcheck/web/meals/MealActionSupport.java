package com.gb1.healthcheck.web.meals;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import com.gb1.commons.dataaccess.IdentityHydrater;
import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.meals.PreparationMethod;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.foods.FoodService;
import com.gb1.healthcheck.services.meals.MealService;
import com.gb1.struts2.interceptor.AuthenticatedUser;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public abstract class MealActionSupport extends ActionSupport implements Preparable {
	@Resource
	protected FoodService foodService;

	@Resource
	protected MealService mealService;

	private User requester;
	private List<Food> availableFoods = new LinkedList<Food>();

	protected MealActionSupport() {
	}

	public void prepare() {
		availableFoods.clear();
		availableFoods.addAll(foodService.getSimpleFoods());
		availableFoods.addAll(foodService.getComplexFoods(new IdentityHydrater<ComplexFood>()));
		Collections.sort(availableFoods, new Food.ByNameComparator());
	}

	public String cancel() {
		return Action.SUCCESS;
	}

	public List<PreparationMethod> getAvailablePreparationMethods() {
		return Arrays.asList(PreparationMethod.values());
	}

	public List<Food> getAvailableFoods() {
		return availableFoods;
	}

	protected User getRequester() {
		return requester;
	}

	@AuthenticatedUser
	public void setRequester(User requester) {
		this.requester = requester;
	}
}
