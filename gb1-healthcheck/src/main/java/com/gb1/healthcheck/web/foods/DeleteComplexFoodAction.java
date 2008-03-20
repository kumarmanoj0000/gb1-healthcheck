package com.gb1.healthcheck.web.foods;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;

@Controller("deleteComplexFoodAction")
@Scope("prototype")
public class DeleteComplexFoodAction extends ComplexFoodActionSupport {
	public DeleteComplexFoodAction() {
	}

	public String submit() {
		getFoodService().deleteFood(getFoodId());
		setActionMessageKey("foods.complexFoods.delete.success");

		return Action.SUCCESS;
	}
}
