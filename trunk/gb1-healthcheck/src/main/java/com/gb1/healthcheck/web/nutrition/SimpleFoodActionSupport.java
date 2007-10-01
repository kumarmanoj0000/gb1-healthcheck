package com.gb1.healthcheck.web.nutrition;

import java.util.Arrays;
import java.util.List;

import com.gb1.healthcheck.domain.nutrition.Group;
import com.gb1.healthcheck.domain.nutrition.Nutrient;
import com.gb1.healthcheck.services.nutrition.FoodService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class SimpleFoodActionSupport extends ActionSupport {
	private FoodService foodService;

	public SimpleFoodActionSupport() {
	}

	public String cancel() {
		return Action.SUCCESS;
	}

	public List<Group> getAvailableGroups() {
		return Arrays.asList(Group.values());
	}

	public List<Nutrient> getAvailableNutrients() {
		return Arrays.asList(Nutrient.values());
	}

	public void setFoodService(FoodService foodService) {
		this.foodService = foodService;
	}

	protected FoodService getFoodService() {
		return foodService;
	}
}
