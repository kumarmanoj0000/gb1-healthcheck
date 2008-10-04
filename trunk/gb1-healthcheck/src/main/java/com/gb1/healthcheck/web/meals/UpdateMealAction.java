package com.gb1.healthcheck.web.meals;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.gb1.healthcheck.domain.meals.MealException;
import com.gb1.healthcheck.services.foods.FoodService;
import com.gb1.healthcheck.services.meals.FullMealHydrater;
import com.gb1.struts2.dispatcher.FlashResult;
import com.opensymphony.xwork2.Action;

@ParentPackage("default")
@Results( {
		@Result(name = "input", value = "/views/meals/editMeal.jsp"),
		@Result(type = FlashResult.class, value = "manageMeals", params = { "namespace", "/meals",
				"parse", "true", "actionMessages", "${actionMessages}", "refreshList", "true" }) })
public class UpdateMealAction extends MealActionSupport implements SessionAware {
	protected static final String MODEL_SESSION_KEY = CreateMealAction.class.getName() + ".model";

	@Resource
	protected FoodService foodService;

	private Long mealId;
	private Map<String, Object> sessionMap;

	public UpdateMealAction() {
	}

	@Override
	public String input() {
		MealBuilder model = new MealBuilder(mealService.getMeal(mealId, new FullMealHydrater()));
		sessionMap.put(MODEL_SESSION_KEY, model);

		return Action.INPUT;
	}

	@Override
	public String execute() {
		String result = Action.INPUT;

		try {
			mealService.updateMeal(getModel().build(foodService));

			addActionMessage(getText("meals.edit.success"));
			result = Action.SUCCESS;
		}
		catch (MealException e) {
			addActionError(getText("meals.edit.error", new String[] { e.getMessage() }));
		}

		return result;
	}

	public MealBuilder getModel() {
		return (MealBuilder) sessionMap.get(MODEL_SESSION_KEY);
	}

	public void setMealId(Long mealId) {
		this.mealId = mealId;
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map sessionMap) {
		this.sessionMap = sessionMap;
	}
}
