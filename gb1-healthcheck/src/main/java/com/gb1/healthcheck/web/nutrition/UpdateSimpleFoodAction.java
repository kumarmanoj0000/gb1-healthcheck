package com.gb1.healthcheck.web.nutrition;

import com.gb1.healthcheck.domain.nutrition.FoodException;
import com.gb1.healthcheck.services.nutrition.FoodService;
import com.opensymphony.xwork2.Action;

public class UpdateSimpleFoodAction {
	private Long foodId = null;
	private SimpleFoodUpdateRequest model = null;
	private FoodService foodService;

	public String prepareSimpleFoodUpdate() {
		model = new SimpleFoodUpdateRequest(foodService.loadSimpleFood(foodId));
		return Action.SUCCESS;
	}

	public String updateSimpleFood() throws FoodException {
		foodService.updateSimpleFood(foodId, model);
		return Action.SUCCESS;
	}

	public Long getFoodId() {
		return foodId;
	}

	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}

	public SimpleFoodUpdateRequest getModel() {
		return model;
	}

	public void setFoodService(FoodService foodService) {
		this.foodService = foodService;
	}
}
