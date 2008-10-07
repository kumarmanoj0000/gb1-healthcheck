package com.gb1.healthcheck.web.foods;

import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.dispatcher.FlashResult;

import com.gb1.healthcheck.services.foods.FoodService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("default")
@Result(type = FlashResult.class, value = "manageFoods", params = { "namespace", "/foods", "parse",
		"true", "actionMessages", "${actionMessages}", "refreshList", "true" })
public class DeleteComplexFoodsAction extends ActionSupport {
	@Resource
	protected FoodService foodService;

	private Long[] foodIds;

	public DeleteComplexFoodsAction() {
	}

	@Override
	public String execute() {
		if (foodIds != null) {
			foodService.deleteFoods(Arrays.asList(foodIds));
			addActionMessage(getText("foods.complexFoods.delete.success"));
		}

		return Action.SUCCESS;
	}

	public void setFoodIds(Long[] foodIds) {
		this.foodIds = foodIds;
	}
}
