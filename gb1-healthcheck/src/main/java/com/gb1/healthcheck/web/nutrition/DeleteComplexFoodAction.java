package com.gb1.healthcheck.web.nutrition;

import com.opensymphony.xwork2.Action;

public class DeleteComplexFoodAction extends ComplexFoodActionSupport {
	private Long foodId;

	public DeleteComplexFoodAction() {
	}

	public String submit() {
		getFoodService().deleteFood(foodId);
		return Action.SUCCESS;
	}

	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}
}
