package com.gb1.healthcheck.web.foods;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;

@Controller("deleteSimpleFoodAction")
@Scope("prototype")
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
