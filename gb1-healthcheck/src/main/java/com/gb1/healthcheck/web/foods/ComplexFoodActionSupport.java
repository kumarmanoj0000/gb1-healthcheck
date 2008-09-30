package com.gb1.healthcheck.web.foods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import com.gb1.commons.dataaccess.IdentityHydrater;
import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.services.foods.FoodService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public abstract class ComplexFoodActionSupport extends ActionSupport implements Preparable {
	@Resource
	protected FoodService foodService;

	private Long foodId;
	private List<Food> availableIngredients = new ArrayList<Food>();

	public void prepare() {
		availableIngredients.addAll(foodService.getSimpleFoods());
		availableIngredients.addAll(foodService
				.getComplexFoods(new IdentityHydrater<ComplexFood>()));
		Collections.sort(availableIngredients, new Food.ByNameComparator());
	}

	public String cancel() {
		return Action.SUCCESS;
	}

	public Long getFoodId() {
		return foodId;
	}

	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}

	public List<Food> getAvailableIngredients() {
		return availableIngredients;
	}
}
