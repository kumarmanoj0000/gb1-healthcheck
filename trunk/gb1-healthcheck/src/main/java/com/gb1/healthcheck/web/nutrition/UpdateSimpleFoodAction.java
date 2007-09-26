package com.gb1.healthcheck.web.nutrition;

import java.util.Map;

import org.apache.struts2.interceptor.ParameterAware;

import com.gb1.healthcheck.domain.nutrition.FoodException;
import com.gb1.healthcheck.services.nutrition.FoodService;
import com.opensymphony.xwork2.Action;

public class UpdateSimpleFoodAction implements ParameterAware {
	private Long foodId = null;
	private SimpleFoodUpdateRequest model = null;
	private FoodService foodService;

	public String prepareUpdateSimpleFood() {
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

	public SimpleFoodUpdateRequest getModel() {
		return model;
	}

	public void setParameters(Map params) {
		foodId = Long.parseLong((String) params.get("foodId"));
	}

	public void setFoodService(FoodService foodService) {
		this.foodService = foodService;
	}
}
