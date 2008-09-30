package com.gb1.healthcheck.web.foods;

import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;

import com.gb1.healthcheck.services.foods.FoodService;
import com.gb1.struts2.dispatcher.FlashResult;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("default")
@Result(type = FlashResult.class, value = "manageFoods", params = { "namespace", "/foods", "parse",
		"true", "actionMessages", "${actionMessages}", "refreshList", "true" })
public class DeleteSimpleFoodsAction extends ActionSupport {
	@Resource
	protected FoodService foodService;

	private Long[] foodIds;
	private String actionMessageKey;

	public DeleteSimpleFoodsAction() {
	}

	@Override
	public String execute() {
		if (foodIds != null) {
			foodService.deleteFoods(Arrays.asList(foodIds));
			addActionMessage(getText("foods.simpleFoods.delete.success"));
		}

		return Action.SUCCESS;
	}

	public void setFoodIds(Long[] foodIds) {
		this.foodIds = foodIds;
	}

	public String getActionMessageKey() {
		return actionMessageKey;
	}
}
