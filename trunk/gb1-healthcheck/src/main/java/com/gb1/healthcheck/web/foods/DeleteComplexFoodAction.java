package com.gb1.healthcheck.web.foods;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;

@Controller("deleteComplexFoodAction")
@Scope("prototype")
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
