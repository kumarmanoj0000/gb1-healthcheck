package com.gb1.healthcheck.web.foods;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
	private FoodService foodService;
	private Long[] foodIds;
	private String actionMessageKey;

	public DeleteSimpleFoodsAction() {
	}

	@Override
	public String execute() {
		if (foodIds != null) {
			Set<Long> idsToDelete = new HashSet<Long>();
			idsToDelete.addAll(Arrays.asList(foodIds));

			foodService.deleteFoods(idsToDelete);
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

	@Resource
	public void setFoodService(FoodService foodService) {
		this.foodService = foodService;
	}
}
