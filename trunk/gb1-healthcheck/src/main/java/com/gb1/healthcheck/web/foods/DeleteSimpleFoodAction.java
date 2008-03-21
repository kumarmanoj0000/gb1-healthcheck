package com.gb1.healthcheck.web.foods;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;

@Controller("deleteSimpleFoodAction")
@Scope("prototype")
public class DeleteSimpleFoodAction extends SimpleFoodActionSupport {
	public DeleteSimpleFoodAction() {
	}

	@Override
	public String execute() {
		getFoodService().deleteFood(getFoodId());
		setActionMessageKey("foods.simpleFoods.delete.success");

		return Action.SUCCESS;
	}
}
