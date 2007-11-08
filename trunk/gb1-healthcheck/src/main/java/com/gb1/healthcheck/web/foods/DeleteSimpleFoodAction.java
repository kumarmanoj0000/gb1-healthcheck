package com.gb1.healthcheck.web.foods;

import com.opensymphony.xwork2.Action;

public class DeleteSimpleFoodAction extends SimpleFoodActionSupport {
	private Long foodId;

	public DeleteSimpleFoodAction() {
	}

	public String submit() {
		getFoodService().deleteFood(foodId);
		return Action.SUCCESS;
	}

	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}
}
