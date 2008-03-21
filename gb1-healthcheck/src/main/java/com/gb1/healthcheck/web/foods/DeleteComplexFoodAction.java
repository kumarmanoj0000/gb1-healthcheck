package com.gb1.healthcheck.web.foods;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;

import com.opensymphony.xwork2.Action;

@ParentPackage("default")
@Result(type = ServletActionRedirectResult.class, value = "listFoods", params = { "namespace",
		"/foods", "parse", "true", "actionMessageKey", "${actionMessageKey}" })
public class DeleteComplexFoodAction extends ComplexFoodActionSupport {
	public DeleteComplexFoodAction() {
	}

	@Override
	public String execute() {
		getFoodService().deleteFood(getFoodId());
		setActionMessageKey("foods.complexFoods.delete.success");

		return Action.SUCCESS;
	}
}
