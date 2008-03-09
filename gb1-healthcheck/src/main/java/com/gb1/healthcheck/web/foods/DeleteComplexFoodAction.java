package com.gb1.healthcheck.web.foods;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;

@Controller("deleteComplexFoodAction")
@Scope("prototype")
public class DeleteComplexFoodAction extends ComplexFoodActionSupport {
	private Long foodId;
	private String confirmationMessage;

	public DeleteComplexFoodAction() {
	}

	public String submit() {
		getFoodService().deleteFood(foodId);
		confirmationMessage = getText("foods.complexFoods.delete.success");
		return Action.SUCCESS;
	}

	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}

	public String getConfirmationMessage() {
		return confirmationMessage;
	}
}
