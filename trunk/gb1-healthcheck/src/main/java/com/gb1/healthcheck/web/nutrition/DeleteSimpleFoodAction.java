package com.gb1.healthcheck.web.nutrition;

import com.opensymphony.xwork2.Action;

public class DeleteSimpleFoodAction extends SimpleFoodActionSupport {
	private Long foodId;

	public DeleteSimpleFoodAction() {
	}

	public String submit() {
		getFoodService().deleteSimpleFood(foodId);
		return Action.SUCCESS;
	}

	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}
}
